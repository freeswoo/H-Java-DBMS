package com.biz.hello;

public class HelloEx_04 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		add();
		add(30,40);
		add(50,60);
		add(70,80);
	}
	
	public static void add(int n1, int n2) {
		int sum = n1 + n2;
		System.out.println(sum);
	}
	
	public static void add() {
		
		int num1 = 40;
		int num2 = 50;
		int sum = num1 + num2;
		System.out.println(sum);
	}

}
