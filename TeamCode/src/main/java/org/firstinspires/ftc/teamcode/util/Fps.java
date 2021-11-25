package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Fps {
    static ElapsedTime runtime = new ElapsedTime();
    public static int fps = 0;
    static int lastSec = 0;
    static int currSec = 0;
    static int loopCount = 0;

    public static void init(){
        fps = 0;
        lastSec = 0;
        currSec = 0;
        loopCount = 0;    }

    public static boolean count(){
        boolean result = false;
        currSec = (int)runtime.seconds();

        if(currSec> lastSec) {
            fps = loopCount;
            lastSec = currSec;
            loopCount = 0;
            result = true;
        }
        loopCount++;
        return result;
    }



}
