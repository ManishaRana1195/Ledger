package com.geekskool.leger.Models;

/**
 * Created by manisharana on 27/12/16.
 */
public class FraudState extends State {

    public FraudState() {
        this.state = StateOptions.FRAUD;
    }

    @Override
    public StateOptions getState() {
        return StateOptions.FRAUD;
    }

    @Override
    public Result updateState(StateOptions state) {
        if(state == StateOptions.UNVERIFIED) {
            this.state = StateOptions.FRAUD;
            return new Result(true,null);
        }
        return new Result(false,ILLEGAL_STATE);
    }
}
