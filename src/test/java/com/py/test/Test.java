package com.py.test;

public class Test {


        public static void main(String[] arg){

            String[] aa = {"1","2","3"};
            String b = "";
            for (int i = 0;i<aa.length;i++){
                b+=aa[i]+",";
            }

            String a = "a,b,c,d";
            String[] strings = a.split(",");
            for (String s:strings) {
                System.out.println(s);
            }
        }



}
