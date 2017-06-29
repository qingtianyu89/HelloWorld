package com.test.action.decrator;

public class Decrator {
	
	public void insert(){
		Object clue = new Object();
//		Component com = new C
//		component.insertClue(new Object());
		Component con = new Concrete() {
			
			@Override
			public void method2(Object clue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void method1(Object clue) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
