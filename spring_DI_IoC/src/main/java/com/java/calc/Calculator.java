package com.java.calc;

public class Calculator {

	private Sumation sum;
	private Minus minus;
	private Multiplex multi;
	private Divide div;
	
	public int sum (int a, int b) {
		return sum.sum(a, b);
	}
	
	public int minus (int a, int b) {
		return minus.minus(a, b);
	}
	
	
	public long multi (int a, int b) {
		return multi.multi(a, b);
	}
	public int divide (int a, int b) {
		return div.divide (a, b);
	}

	public void setSum(Sumation sum) {
		this.sum = sum;
	}

	public void setMinus(Minus minus) {
		this.minus = minus;
	}

	public void setMulti(Multiplex multi) {
		this.multi = multi;
	}

	public void setDiv(Divide div) {
		this.div = div;
	}
	
}