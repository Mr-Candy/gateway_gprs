package com.gateway.tool;

public class LatLonUtil {

    //秒转度
    public static double M2D(int jwd){

        int du = (jwd/1000)/3600;
        int fen = ((jwd/1000)%3600)/60;
        double m = (jwd/1000.0)-du*3600-fen*60;
        double d = du + (fen + m/60)*0.01;
        return d;
    }

    //测试方法
    public static void main(String [] src){
        M2D(203538222);
        System.out.println(M2D(203538222));
    }
}
