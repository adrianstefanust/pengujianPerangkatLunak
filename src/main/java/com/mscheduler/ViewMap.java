/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mscheduler;

import com.mscheduler.view.View;

public class ViewMap {
    public String trigger;
    public View view;

    public ViewMap(String trigger, View view) {
        this.trigger = trigger;
        this.view = view;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}

