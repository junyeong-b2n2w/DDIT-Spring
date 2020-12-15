package com.spring.behavior;

public class 행동하기 implements 행동 {

	@Override
	public void 잠자기() {
		System.out.println("자");
	}

	@Override
	public void 공부하기() {
		System.out.println("공부");
		
	}

	@Override
	public void 밥먹기() {
		System.out.println("밥먹");		
	}

	@Override
	public void 데이트() {
		System.out.println("데이트");		
	}

	@Override
	public void 운동() {
		System.out.println("운동");		
	}

	@Override
	public void 놀기() {
		System.out.println("놀아");		
	}

	@Override
	public void 정신수양() {
		System.out.println("수양");		
	}

	
}
