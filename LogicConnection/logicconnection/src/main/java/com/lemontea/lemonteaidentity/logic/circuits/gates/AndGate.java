package com.lemontea.lemonteaidentity.logic.circuits.gates;

import com.lemontea.lemonteaidentity.logic.circuits.Location;

public class AndGate extends Gate {

    public AndGate(int inputChannels, int x, int y) {
        super(inputChannels,new Location(x,y),30,30);
        
    }

    @Override
    public boolean trace() {
        boolean result = true;
        for(boolean b : this.getInput()){
            result &= b;
            if(!result) break;
        }
        return result;
    }

}
