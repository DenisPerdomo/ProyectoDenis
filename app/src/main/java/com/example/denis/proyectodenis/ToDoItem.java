package com.example.denis.proyectodenis;

import java.util.Date;

/**
 * Created by Denis on 18/03/2017.
 */

public class ToDoItem {
    public enum Priority{
        LOW,MED,HIGH
    };

    public enum Status{
        NOTDONE, DONE
    };

    private String mTitle = new String();
    private Priority mPriority = Priority.LOW;
    private Status mStatus = Status.NOTDONE;
    private String mDate = new String();
    private String mTime = new String();

    public ToDoItem(String mTitle, Priority mPriority, Status mStatus, String date, String mTime) {
        this.mTitle = mTitle;
        this.mPriority = mPriority;
        this.mStatus = mStatus;
        this.mDate = date;
        this.mTime = mTime;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {

        this.mTitle = mTitle;
    }

    public Priority getmPriority() {

        return mPriority;
    }

    public void setmPriority(Priority mPriority) {

        this.mPriority = mPriority;
    }

    public Status getmStatus() {

        return mStatus;
    }

    public void setmStatus(Status mStatus) {

        this.mStatus = mStatus;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
