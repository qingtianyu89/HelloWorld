package com.test.action.decrator;

public abstract class Concrete implements Component{

	
	public Concrete(){
//		this.clue = clue;
	}
	
	public void insertClue(Object clue){
		this.method1(clue);
		this.doSomething(clue);
		this.method2(clue);
	}
	
	private void doSomething(Object clue){
		System.out.println("doSomething");
	}
	
	public abstract void method1(Object clue);
	
	public abstract void method2(Object clue);

	
}
