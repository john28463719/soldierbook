package com.example.administrator.soldierbook;

/**
 * Created by Administrator on 2015/1/19.
 */
public class soldier {
    String thing;
    String time;
    int id;
    public soldier(String thing,int id){
        this.thing = thing;
        this.id = id;
    }
    public soldier(String thing)
    {
        this.thing = thing;
    }
    public soldier(String thing,String time)
    {
        this.time = time;
        this.thing = thing;
    }
    public soldier(){};

    public void setID(int id){
        this.id = id;
    }
    public void setThing(String thing){
        this.thing = thing;
    }
    public void setTime(String time){
        this.time = time;
    }
    public int getID(){
        return this.id;
    }
    public String getThing(){
        return  this.thing;
    }
    public String getTime(){
        return  this.time;
    }
}
