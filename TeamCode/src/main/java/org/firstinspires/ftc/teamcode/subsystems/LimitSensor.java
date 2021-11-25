package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimitSensor {
    DigitalChannel digitalTouch = null;  // Hardware Device Object

    public LimitSensor(){}

    public LimitSensor(HardwareMap hm, String name){
        // get a reference to our digitalTouch object.
        digitalTouch = hm.get(DigitalChannel.class, name);
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean isPressed(){
        return digitalTouch!=null && digitalTouch.getState() != true;

    }


}
