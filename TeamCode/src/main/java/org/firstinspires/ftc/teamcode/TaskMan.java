package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.List;

public class TaskMan {
    public List<Task> tasks = new ArrayList<Task>();
    double taskTime = 0;
    Task stop = new Task("TASK_STOP",30);
    Task currTask = null;
    int currIndex = 0;
    public List<String> cmdRun = new ArrayList<>();
    public List<String> cmds = new ArrayList<>();


    /**
     * 添加新的任务到任务列表
     * @param task 需要添加的任务
     * */
    public void addTask(Task task){
        task.startTime = taskTime;
        task.endTime = taskTime+task.duration;

        taskTime = task.endTime;
        tasks.add(task);
        if(tasks.size()==1){
            currTask = task;
        }
    }

    /**
     *
     * @param seconds 当前执行的时间
     * */
    public void loop(double seconds){
        refreshTask(seconds);
    }

    /**
     * 按给定时间获取任务
     * @param seconds 当前执行的时间
     * */
    public void refreshTask(double seconds){
        if(getEndTime() > seconds)
            return;
        currIndex++;
        if(currIndex>=tasks.size()){
            currTask = stop;
            return;
        }

        currTask = tasks.get(currIndex);
        cmds.add(getName());
    }
    /**
     * 按给定时间获取任务

     * */
    public Task getTask(){
        if(currTask==null || currIndex>=tasks.size()){
            return stop;
        }
        return currTask;
    }
    public String getName (){
        if(currTask==null) return "";
        return currTask.name;
    }

    public void reset() {
        tasks.clear();
        currIndex = 0;
        taskTime = 0;
        currTask = null;
    }

    public int count(){
        return tasks.size();
    }


    /**
     * 获取当前任务指定绝对位置的数据，转换为integer类型
     * @param index 指定索引，从0开始
     * */
    public int getInt(int index){
        if(currTask==null) return 0;
        return currTask.paras.get(index).intValue();
    }

    /**
     * 获取当前任务指定绝对位置的数据，转换为double类型
     * @param index 指定索引，从0开始
     * */
    public double getDouble(int index){
        if(currTask==null) return 0;
        return currTask.paras.get(index);
    }

    /**
     * 获取当前任务指定绝对位置的数据，转换为double类型
     * @param index 指定索引，从0开始
     * */
    public double getDouble(ArrayList arr,int index){
        return Double.parseDouble((String)arr.get(index));
    }

    /**
     * 获取当前任务开始时间
    * */
    public double getStartTime(){
        if(currTask==null) return 0;
        return currTask.startTime;
    }

    /**
     * 获取当前任务持续时间
     * */
    public double getDurationTime(){
        if(currTask==null) return 0;
        return currTask.duration;
    }

    /**
     * 获取当前任务结束时间
     * */
    public double getEndTime(){
        if(currTask==null) return 0;
        return currTask.endTime;
    }


    /**
     * 指定位置的参数数据，转换为double类型
     * @param index 指定索引，从0开始
     * */
    public double getD(int index){
        return getDouble(index);
    }

    /**
     * 指定位置的参数数据，转换为integer类型
     * @param index 指定索引，从0开始
     * */
    public int getI(int index){
        return getInt(index);
    }

    /**
     * 添加停止命令，该命令直接持续到自动阶段结束
     * */
    public void addStop(){
        Task a = new Task("TASK_STOP",300);
        addTask(a);
    }

    /**
     * 添加停止并等待命令，指定时间内停止当前设备运作
     * @param seconds 等待时长，单位秒
     * */
    public void addStopAndWait(double seconds){
        Task a = new Task("TASK_STOP_WAIT",seconds);
        addTask(a);
    }

    /**
     * 添加等待命令，指定时间内不对当前设备做任何设置
     * @param seconds 等待时长，单位秒
     * */
    public void addWait(double seconds){
        Task a = new Task("TASK_WAIT",seconds);
        addTask(a);
    }
}
