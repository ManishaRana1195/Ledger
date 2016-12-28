package com.geekskool.leger.Models;

/**
 * Created by manisharana on 27/12/16.
 */
public class UnverifiedState extends State {

    public UnverifiedState() {
        this.state = StateOptions.UNVERIFIED;
    }

    @Override
    public StateOptions getState() {
        return StateOptions.UNVERIFIED;
    }

    @Override
    public Result updateState(StateOptions state) {
        if(state == StateOptions.VERIFIED || state == StateOptions.FRAUD) {
            this.state = StateOptions.UNVERIFIED;
            return new Result(true,null);
        }
        return new Result(false,ILLEGAL_STATE);
    }
}
