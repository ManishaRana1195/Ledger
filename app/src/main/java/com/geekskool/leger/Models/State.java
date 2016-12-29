package com.geekskool.leger.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manisharana on 27/12/16.
 */
public abstract class State implements Parcelable{
    static final String ILLEGAL_STATE = "Illegal State";
    StateOptions state;

    public State(){}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(state,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    abstract public Result updateState(StateOptions state);
    public void setState(StateOptions state) {
        this.state = state;
    }
    abstract public StateOptions getState();


}
