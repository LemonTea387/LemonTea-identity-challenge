package com.lemontea.lemonteaidentity.logic.circuits.gates;

import com.lemontea.lemonteaidentity.logic.circuits.Location;

public abstract class Gate implements Traceable {
    private boolean[] input;
    private boolean output;
    private final int SIZE_X,SIZE_Y;
    private Location loc;
    public Gate(int inputChannels,Location loc,int sizeX,int sizeY){
        input = new boolean[inputChannels];
        this.loc = loc;
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
        setOutput(false);
    }
    public int getInputSize(){
        return input.length;
    }
    public boolean[] getInput(){
        return input;
    }

    public void setInput(boolean[] inputs){
        this.input = inputs;
    }
    private void setOutput(boolean output){
        this.output = output;
    }
    public boolean getOutput(){
        return output;
    }

    public boolean isOutput() {
        return this.output;
    }

    public int getSIZE_X() {
        return this.SIZE_X;
    }


    public int getSIZE_Y() {
        return this.SIZE_Y;
    }


    public Location getLoc() {
        return this.loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
}
