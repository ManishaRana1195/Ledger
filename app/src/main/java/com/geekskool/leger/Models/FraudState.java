package com.geekskool.leger.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manisharana on 27/12/16.
 */
public class FraudState extends State {

    public FraudState() {
        this.stateOptions = StateOptions.FRAUD;
    }

    private FraudState(Parcel in){
        this.stateOptions = in.readParcelable(StateOptions.class.getClassLoader());
    }


    @Override
    public StateOptions getStateOptions() {
        return StateOptions.FRAUD;
    }

    @Override
    public Result updateState(StateOptions state) {
        if(state == StateOptions.UNVERIFIED) {
            this.stateOptions = StateOptions.FRAUD;
            return new Result(true,null);
        }
        return new Result(false,ILLEGAL_STATE);
    }

    public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() {

        @Override
        public State createFromParcel(Parcel source) {
            return new FraudState(source);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

}
