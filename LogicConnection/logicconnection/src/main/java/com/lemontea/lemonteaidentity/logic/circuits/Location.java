package com.lemontea.lemonteaidentity.logic.circuits;

public class Location {
    private int x;
    private int y;
    public Location(int x,int y){
        setX(x);
        setY(y);
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
