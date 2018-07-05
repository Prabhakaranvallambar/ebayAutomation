package com.eBay.Module;


public class Main{
	
	public static void main(String[] args) {
//		int minVal=1;
//		int maxVal=10;
//		int counterFor1 = 0,counterFor2=0,counterFor3=0,counterFor4=0;
//		for (int i = 0; i < 100; i++) {
//			int randomResult=(int) (Math.random()*(maxVal-minVal))+minVal;
//			System.out.println(randomResult);
//			switch (randomResult) {
//			case 1:
//				counterFor1=counterFor1+1;
//				break;
//			case 2:
//				counterFor2=counterFor2+1;
//			break;
//			case 3:
//				counterFor3=counterFor3+1;
//			break;
//			case 4:
//				counterFor4=counterFor4+1;
//			break;
//			default:
//				break;
//			}
		 int exit=1;
		 boolean a=false;
	    	do {
	    		exit=exit+1;
				if (exit>3) {
					System.out.println("this is ok");
					a=true;
				}
				
			} while (a==false);
	    	System.out.println("exiting do"+a);
			
//		}
//		System.out.println("1 is repeated :"+counterFor1+" times");
//		System.out.println("2 is repeated :"+counterFor2+" times");
//		System.out.println("3 is repeated :"+counterFor3+" times");
//		System.out.println("4 is repeated :"+counterFor4+" times");
		
	}
}
