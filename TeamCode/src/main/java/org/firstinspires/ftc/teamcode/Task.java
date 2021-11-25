package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

public class Task {
    public String name = "";
    public double startTime= 0;
    public double duration = 0;
    public double endTime = 0;
    public ArrayList<Double> paras = new ArrayList<>();

    public Task(){}
    public Task(String name,double duration){
        this.name = name;
        this.duration = duration;
    }

    public Task(String name,double duration,double val1){
        this.name = name;
        this.duration = duration;
        this.paras.add(val1);
    }

    public Task(String name,double duration,double val1,double val2){
        this.name = name;
        this.duration = duration;
        this.paras.add(val1);
        this.paras.add(val2);
    }
    public Task(String name,double duration,double val1,double val2,double val3){
        this.name = name;
        this.duration = duration;
        this.paras.add(val1);
        this.paras.add(val2);
        this.paras.add(val3);
    }

    @Override
    public String toString(){
        String res = String.format("%s %.2f %.2f %.2f [",name,startTime,duration,endTime) ;
        for(Double d : paras){
            res+=d.toString();
        }
        return res+"]";
    }

}
