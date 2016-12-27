package com.geekskool.leger.Models;

/**
 * Created by manisharana on 27/12/16.
 */
public abstract class State {
    static final String ILLEGAL_STATE = "Illegal State";
    StateOptions state;

    State(StateOptions state){
        this.state = state;
    }

    public void setState(StateOptions state) {
        this.state = state;
    }

    abstract public Result updateState(StateOptions state);
    abstract public StateOptions getState();
}
