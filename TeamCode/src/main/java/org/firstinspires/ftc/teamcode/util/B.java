package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.HashMap;
import java.util.Map;

public class B {
    static class ButtonTimer{
        ElapsedTime runtime = new ElapsedTime();
        int no=0;
        int delayMs=0;
        int lastMs=0;
        public ButtonTimer(int no,int delayMs){this.no = no;this.delayMs=delayMs;}
        public boolean ok(){return lastMs+delayMs < runtime.milliseconds();}
        public void update(){lastMs= (int) runtime.milliseconds();}
    }
    static Map<Integer, ButtonTimer> bts = new HashMap<>();
    {
        for (int i = 0; i < 20; i++) {
            bts.put(i,new ButtonTimer(i,400));
        }
    }
    static boolean ok(int id){
        return ok(id,200);
    }
    static boolean ok(int id,int delay){
        if(!bts.containsKey(id)) {
            bts.put(id,new ButtonTimer(id,delay));
        }
        ButtonTimer t = bts.get(id);
        if(t.delayMs != delay) t.delayMs = delay;
        boolean b = t.ok();
        if(b) t.update();
        return b;
    }

}
