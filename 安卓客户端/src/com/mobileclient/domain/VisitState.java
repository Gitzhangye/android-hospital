package com.mobileclient.domain;

import java.io.Serializable;

public class VisitState implements Serializable {
    /*×´Ì¬id*/
    private int visitStateId;
    public int getVisitStateId() {
        return visitStateId;
    }
    public void setVisitStateId(int visitStateId) {
        this.visitStateId = visitStateId;
    }

    /*³öÕï×´Ì¬*/
    private String visitStateName;
    public String getVisitStateName() {
        return visitStateName;
    }
    public void setVisitStateName(String visitStateName) {
        this.visitStateName = visitStateName;
    }

}