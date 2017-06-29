package com.test.action;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
		
	public static void main(String[] args) {
//		for (int i = 0; i < 10000; i++) {
//			for (int j = 0; j < 10000; j++) {
//				
//			}
//		}
//		int a=0;
//		for (int i = 0; i < 10000; i++) {
//			for (int j = 0; j < 10000; j++) {
//				a++;
//			}
//		}
//		System.out.println(a);
		
		String beginMonth = "201409";
		String endMonth = "201409";
		int beginYear = Integer.valueOf(beginMonth.substring(0, 4));
		int beginM = Integer.valueOf(beginMonth.substring(4, 6));
		int endYear = Integer.valueOf(endMonth.substring(0, 4));
		int endM = Integer.valueOf(endMonth.substring(4, 6));
		List<String> monthList = new ArrayList<String>();
		String str = "";
		if(beginYear == endYear){
			for(int i=beginM;i<=endM;i++){
				if(i<10){
					str = "0";
				}
				monthList.add(beginYear+str+i);
			}
		}
		else{
			for(int i=0;i<=endYear-beginYear;i++){
				int year = beginYear + i;
				if(endYear == year){
					for(int j=1;j<=endM;j++){
						if(j<10){
							str = "0";
						}
						monthList.add(year+str+j);
					}
				}else if(beginYear == year){
					for(int j=beginM;j<=12;j++){
						if(j<10){
							str = "0";
						}
						monthList.add(year+str+j);
					}
				}else{
					for(int j=1;j<=12;j++){
						if(j<10){
							str = "0";
						}
						monthList.add(year+str+j);
					}
				}
				
			}
		}
		System.out.println(monthList);
	}
}
