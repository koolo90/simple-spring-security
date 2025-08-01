package com.example.algorythms.elevator;

import java.text.MessageFormat;

public class Elevator {
    public enum ElevatorState {
        STOPPED, MOVING_UP, MOVING_DOWN
    }

    private int currentFlor;
    private ElevatorState state;
    private int identificator;

    public void setCurrentFlor(int currentFlor) {
        this.currentFlor = currentFlor;
    }

    public int getCurrentFlor() {
        return currentFlor;
    }


    public void setState(ElevatorState state) {
        this.state = state;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setIdentificator(int identificator) {
        this.identificator = identificator;
    }

    public int getIdentificator() {
        return identificator;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Elevator[#{0,number,00}][fl:{1}, dir:{2}]", identificator, currentFlor, state);
    }
}
