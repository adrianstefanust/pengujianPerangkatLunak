package com.mscheduler.model;

import java.util.Objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.codehaus.jackson.map.JsonMappingException;
//import com.codehaus.jackson.map.ObjectMapper;

public class LastIndex {

    public int lastNo;

    public LastIndex(int lastNo) {
        this.lastNo = lastNo;
    }

    public LastIndex() {
    
    }
    
    public int getLastNo() {
        return lastNo;
    }

    public void setLastNo(int lastNo) {
        this.lastNo = lastNo;
    }

}
