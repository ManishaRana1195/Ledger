package com.geekskool.leger.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manisharana on 27/12/16.
 */
public abstract class State implements Parcelable{
    static final String ILLEGAL_STATE = "Illegal State";
    StateOptions stateOptions;

    public State(){}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(stateOptions,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    abstract public Result updateState(StateOptions state);
    public void setStateOptions(StateOptions stateOptions) {
        this.stateOptions = stateOptions;
    }
    abstract public StateOptions getStateOptions();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        return stateOptions == state.stateOptions;

    }

    @Override
    public int hashCode() {
        return stateOptions.hashCode();
    }

    @Override
    public String toString() {
        return "state : '" + stateOptions.name() + "\'";

    }
}
