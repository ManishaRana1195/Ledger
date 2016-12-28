package com.geekskool.leger.Models;

/**
 * Created by manisharana on 27/12/16.
 */
public class VerifiedState extends State {

    public VerifiedState() {
        this.state = StateOptions.VERIFIED;
    }

    @Override
    public StateOptions getState() {
        return StateOptions.VERIFIED;
    }
    @Override
    public Result updateState(StateOptions state) {
        if(state == StateOptions.UNVERIFIED) {
            this.state = StateOptions.VERIFIED;
            return new Result(true,null);
        }
        return new Result(false,ILLEGAL_STATE);
    }
}
