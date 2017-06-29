package com.test.action.enumTest;

public enum TestEnum {

	TEST1("A001", "测试1"), TEST2("A002", "测试2");
	private String name;
	private String desc;

	private TestEnum(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	
	private TestEnum(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
};