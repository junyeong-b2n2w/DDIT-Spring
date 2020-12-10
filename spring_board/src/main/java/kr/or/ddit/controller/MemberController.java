package kr.or.ddit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.command.MemberModifyCommand;
import kr.or.ddit.command.MemberRegistCommand;
import kr.or.ddit.command.SearchCriteria;
import kr.or.ddit.dto.MemberVO;
import kr.or.ddit.service.MemberService;
import kr.or.ddit.utils.ExceptionLoggerHelper;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/main")
	public String main() {
		String url="member/main.open";
		return url;
	}

	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv) throws SQLException {

		String url = "member/list.open";

		Map<String, Object> dataMap = memberService.getSearchMemberList(cri);

		mnv.addAllObjects(dataMap);
		mnv.setViewName(url);

		return mnv;
	}

	// 회원등록
	@RequestMapping("/registForm")
	public String registForm() {
		String url = "member/regist.open";
		return url;
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public void regist(MemberRegistCommand memberReq, HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		MemberVO member = memberReq.toMemberVO();
		memberService.regist(member);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('회원등록이 정상적으로 되었습니다.');");
		out.println("window.opener.location.href='" + request.getContextPath() + "/member/list.do';");
		out.println("window.close();");
		out.println("</script>");

		if (out != null)
			out.close();
	}

	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> idCheck(String id, HttpServletRequest request) {
		ResponseEntity<String> entity = null;

		try {
			MemberVO member = memberService.getMember(id);
			entity = new ResponseEntity<String>(member == null ? id : "", HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			ExceptionLoggerHelper.write(request, e, memberService);
		}

		return entity;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(String id, Model model) throws SQLException {

		String url = "member/detail.open";

		MemberVO member = memberService.getMember(id);
		model.addAttribute("member", member);

		return url;
	}

	@RequestMapping(value = "/modifyForm", method = RequestMethod.GET)
	public String modifyForm(String id, Model model) throws SQLException {

		String url = "member/modify.open";

		MemberVO member = memberService.getMember(id);
		model.addAttribute("member", member);

		return url;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public void modify(MemberModifyCommand modifyReq, HttpSession session, HttpServletResponse response)
			throws Exception {

		MemberVO member = modifyReq.toParseMember();

		String fileName = savePicture(modifyReq.getOldPicture(), modifyReq.getPicture());
		member.setPicture(fileName);

		if (modifyReq.getPicture().isEmpty()) {
			member.setPicture(modifyReq.getOldPicture());
		}
		memberService.modify(member);

		// 로그인한 사용자의 경우 수정된 정보로 session 업로드
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser != null && member.getId().equals(loginUser.getId())) {
			session.setAttribute("loginUser", member);
		}

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String output = "" + "<script>" + "alert('수정되었습니다.');" + "location.href='detail?id=" + member.getId() + "';"
				+ "window.opener.parent.location.reload();" + "</script>";
		out.println(output);
		out.close();

	}

	@Resource(name = "picturePath")
	private String picturePath;

	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> picture(@RequestParam("pictureFile") MultipartFile multi, String oldPicture)
			throws Exception {
		ResponseEntity<String> entity = null;

		String result = "";
		HttpStatus status = null;

		/* 파일저장확인 */
		if ((result = savePicture(oldPicture, multi)) == null) {
			result = "업로드 실패했습니다.!";
			status = HttpStatus.BAD_REQUEST;
		} else {
			status = HttpStatus.OK;

		}

		entity = new ResponseEntity<String>(result, status);

		return entity;

	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String remove(String id, HttpSession session, Model model) throws SQLException {
		String url = "member/removeSuccess";

		MemberVO member;

		// 이미지 파일을 삭제
		member = memberService.getMember(id);

		String savePath = this.picturePath;
		File imageFile = new File(savePath, member.getPicture());
		if (imageFile.exists()) {
			imageFile.delete();
		}
		
		memberService.remove(id);

		// 삭제되는 회원이 로그인 회원인경우 로그아웃 해야함.
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser.getId().equals(member.getId())) {
			session.invalidate();
		}

		model.addAttribute("member", member);

		return url;
	}
	
	@RequestMapping(value="/stop",method=RequestMethod.GET)
	public String stop(String id, HttpSession session,Model model)throws SQLException{
		String url="member/stopSuccess";
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(id.equals(loginUser.getId())) {
			 url="member/stopFail";
		}else{
			memberService.disabled(id);
		}	
		
		model.addAttribute("id", id);
		
		return url;
	}
	
	
	
	
	@RequestMapping("/getPicture")
	@ResponseBody
	public ResponseEntity<byte[]> getPicture(String picture) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		String imgPath = this.picturePath;
		try {

			// in=new FileInputStream(imgPath+File.separator+picture);
			in = new FileInputStream(new File(imgPath, picture));

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			in.close();
		}
		return entity;
	}

	private String savePicture(String oldPicture, MultipartFile multi) throws Exception {
		String fileName = null;

		/* 파일유무확인 */
		if (!(multi == null || multi.isEmpty() || multi.getSize() > 1024 * 1024 * 5)) {

			/* 파일저장폴더설정 */
			String uploadPath = picturePath;
			fileName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
			File storeFile = new File(uploadPath, fileName);

			storeFile.mkdirs();

			// local HDD 에 저장.
			multi.transferTo(storeFile);

			if (!oldPicture.isEmpty()) {
				File oldFile = new File(uploadPath, oldPicture);
				if (oldFile.exists()) {
					oldFile.delete();
				}
			}

		}

		return fileName;
	}

	
}
