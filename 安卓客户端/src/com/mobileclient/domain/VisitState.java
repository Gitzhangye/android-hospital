package com.mobileclient.domain;

import java.io.Serializable;

public class VisitState implements Serializable {
    /*״̬id*/
    private int visitStateId;
    public int getVisitStateId() {
        return visitStateId;
    }
    public void setVisitStateId(int visitStateId) {
        this.visitStateId = visitStateId;
    }

    /*����״̬*/
    private String visitStateName;
    public String getVisitStateName() {
        return visitStateName;
    }
    public void setVisitStateName(String visitStateName) {
        this.visitStateName = visitStateName;
    }

}