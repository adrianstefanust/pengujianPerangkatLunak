package com.mscheduler.controller;

import com.mscheduler.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractController {

    protected User session;
//    protected String role;
    protected ObjectMapper mapper;

    public AbstractController() {
        mapper = new ObjectMapper();
    }

    public User getSession() {
        return session;
    }

    public void setSession(User session) {
        this.session = session;
    }

    
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
}
