package com.geekskool.leger.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manisharana on 27/12/16.
 */
public class UnverifiedState extends State {

    public UnverifiedState() {
        this.state = StateOptions.UNVERIFIED;
    }

    private UnverifiedState(Parcel in){
        this.state = in.readParcelable(StateOptions.class.getClassLoader());
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

    public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() {

        @Override
        public State createFromParcel(Parcel source) {
            return new UnverifiedState(source);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };


}
