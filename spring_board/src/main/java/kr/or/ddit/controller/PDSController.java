package kr.or.ddit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.command.PdsModifyCommand;
import kr.or.ddit.command.PdsRegistCommand;
import kr.or.ddit.command.SearchCriteria;
import kr.or.ddit.dao.AttachDAO;
import kr.or.ddit.dto.AttachVO;
import kr.or.ddit.dto.PdsVO;
import kr.or.ddit.service.PdsService;

@Controller
@RequestMapping("/pds")
public class PDSController {

	@Autowired
	private PdsService service;

	@Autowired
	private AttachDAO attachDAO;

	public void setAttachDAO(AttachDAO attachDAO) {
		this.attachDAO = attachDAO;
	}

	@Resource(name = "fileUploadPath")
	private String fileUploadPath;

	@RequestMapping("/main")
	public String main() throws Exception {
		return "pds/main.open";
	}

	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv) throws Exception {
		String url = "pds/list.open";

		Map<String, Object> dataMap = service.getList(cri);

		mnv.addAllObjects(dataMap);
		mnv.setViewName(url);

		return mnv;
	}

	@RequestMapping("/registForm")
	public ModelAndView registForm(ModelAndView mnv) throws Exception {
		String url = "pds/regist.open";
		mnv.setViewName(url);
		return mnv;
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public void regist(PdsRegistCommand registReq,Model model, 
					   HttpServletRequest request,HttpServletResponse response)
			throws Exception {

		List<AttachVO> attachList = saveFile(registReq);	
		
		PdsVO pds = registReq.toPdsVO();
		pds.setAttachList(attachList);
		pds.setTitle((String)request.getAttribute("XSStitle"));
		service.regist(pds);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('정상적으로 등록되었습니다.');");
		out.println("window.opener.location.href='" + request.getContextPath() + "/pds/list';");
		out.println("window.close();");
		out.println("</script>");

		out.close();
		
		model.addAttribute("attachList",attachList);

	}

	private List<AttachVO> saveFile(PdsRegistCommand registReq) throws Exception {
		String fileUploadPath = this.fileUploadPath;

		List<AttachVO> attachList = new ArrayList<AttachVO>();

		if (registReq.getUploadFile() != null) {
			for (MultipartFile multi : registReq.getUploadFile()) {
				String fileName = UUID.randomUUID().toString().replace("-", "") + "$$" + multi.getOriginalFilename();
				File target = new File(fileUploadPath, fileName);

				if (!target.exists()) {
					target.mkdirs();
				}

				multi.transferTo(target);

				AttachVO attach = new AttachVO();
				attach.setUploadPath(fileUploadPath);
				attach.setFileName(fileName);
				attach.setFileType(fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase());

				attachList.add(attach);
			}
		}
		return attachList;
	}

	@RequestMapping("/detail")
	public ModelAndView detail(ModelAndView mnv, int pno, String from) throws Exception {
		String url = "pds/detail.open";

		PdsVO pds = null;
		if (from != null && from.equals("modify")) {
			pds = service.getPdsForModify(pno);
		} else {
			pds = service.getPds(pno);
		}

		// 파일명 재정의
		List<AttachVO> attachList = pds.getAttachList();
		if (attachList != null) {
			for (AttachVO attach : attachList) {
				String fileName = attach.getFileName().split("\\$\\$")[1];
				attach.setFileName(fileName);
			}
		}

		mnv.addObject("pds", pds);
		mnv.setViewName(url);

		return mnv;
	}

	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(ModelAndView mnv, int pno) throws Exception {
		String url = "pds/modify.open";

		PdsVO pds = service.getPdsForModify(pno);

		// 파일명 재정의
		List<AttachVO> attachList = pds.getAttachList();
		if (attachList != null) {
			for (AttachVO attach : attachList) {
				String fileName = attach.getFileName().split("\\$\\$")[1];
				attach.setFileName(fileName);
			}
		}

		mnv.addObject("pds", pds);
		mnv.setViewName(url);

		return mnv;
	}

	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public void modifyPOST(PdsModifyCommand modifyReq, Model model, 
						   HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileUploadPath = this.fileUploadPath;

		// 삭제된 파일 삭제
		if (modifyReq.getDeleteFile() != null && modifyReq.getDeleteFile().length > 0) {
			for (int ano : modifyReq.getDeleteFile()) {
				String fileName = attachDAO.selectAttachByAno(ano).getFileName();
				File deleteFile = new File(fileUploadPath, fileName);
				attachDAO.deleteAttach(ano); // DB 삭제
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
			}
		}

		// 추가 혹은 변경된 파일 저장
		List<AttachVO> attachList = saveFile(modifyReq);

		// PdsVO settting
		PdsVO pds = modifyReq.toPdsVO();
		pds.setAttachList(attachList);
		pds.setTitle((String)request.getAttribute("XSStitle"));

		// DB에 해당 데이터 추가
		service.modify(pds);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('수정되었습니다.');");
		out.println("location.href='detail?pno=" + pds.getPno() + "&from=modify';");
		out.println("</script>");

		out.close();
		
		model.addAttribute("attachList",attachList);

	}

	@RequestMapping("/remove")
	public void remove(int pno, HttpServletResponse response) throws Exception {

		//첨부파일 삭제
		List<AttachVO> attachList = attachDAO.selectAttachesByPno(pno);		
		if (attachList != null) {
			for (AttachVO attach : attachList) {
				File target = new File(attach.getUploadPath(), attach.getFileName());
				if (target.exists()) {
					target.delete();
				}
			}
		}
		
		//DB삭제
		service.remove(pno);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('삭제되었습니다.');");
		out.println("window.close();");
		out.println("window.opener.location.reload(true);");
		out.println("</script>");
		out.close();

	}
	
	@RequestMapping("/getFile")
	public ResponseEntity<byte[]> getFile(int ano) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		AttachVO attach = attachDAO.selectAttachByAno(ano);

		String fileUploadPath = this.fileUploadPath;
		String fileName = attach.getFileName();

		try {
			in = new FileInputStream(fileUploadPath +File.separator+ fileName);

			fileName = fileName.substring(fileName.indexOf("$$") + 2);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition",
					"attachment;filename=\"" + new String(fileName.getBytes("utf-8"), "ISO-8859-1") + "\"");

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);

		} catch (IOException e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			in.close();
		}
		
		return entity;
	}

}
