package com.test.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		try {
			User user = new User();
			user.setName("123");
			User user2 = new User();
			user2.setName("adfa");
			user2.setAge(12);
			copyProperties(user, user2);
			System.out.println(user.getName()+"_"+user.getAge());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> void copyProperties(T t, T t2) throws Exception {
		Class clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Class clazz2 = t2.getClass();
		Field[] fields2 = clazz2.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			for (Field field2 : fields2) {
				String name2 = field2.getName();
				if (name.equals(name2)
						&& field.getGenericType().equals(
								field2.getGenericType())) {
					Method mSet = clazz.getDeclaredMethod("set"
							+ toFirstUpper(name),getClassFromType(field.getGenericType()));
					Method mGet = clazz.getDeclaredMethod("get"
							+ toFirstUpper(name));
					Object obj = mGet.invoke(t);
					if(obj != null && !obj.equals(0)){
						break;
					}
					Method m2Get = clazz2.getDeclaredMethod("get"
							+ toFirstUpper(name2));
					Object reObj = m2Get.invoke(t2);
					if(reObj != null){
						mSet.invoke(t, reObj);
//						switchToType(t, mSet, reObj, field.getGenericType());
					}
					break;
				}
			}
		}
	}

	public static <T> void switchToType(T t, Method method, Object param,
			Type type) throws Exception {
		if (type.toString().equals("class java.lang.String")) {
			method.invoke(t, (String) param);
		} else if (type.toString().equals("class java.util.Date")) {
			method.invoke(t, (Date) param);
		} else if (type.toString().equals("int")) {
			method.invoke(t, new Integer((String) param));
		}
	}
	
	public static Class getClassFromType(Type type){
		if (type.toString().equals("class java.lang.String")) {
			return String.class;
		} else if (type.toString().equals("Date java.util.Date")) {
			return Date.class;
		} else if (type.toString().equals("int")) {
			return int.class;
		}
		return null;
	}

	/**
	 * 将字符串首字母转为大写
	 * @param str
	 * @return
	 */
	public static String toFirstUpper(String str) {
		StringBuffer sb = new StringBuffer(str);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		str = sb.toString();
		return str;
	}
}
class User{
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
