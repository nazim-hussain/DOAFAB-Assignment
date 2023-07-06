package com.example.demo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChildDataWrapper {
    private List<ChildInstallment> data;

    // Getters and setters

    public List<ChildInstallment> getData() {
        return data;
    }

    public void setData(List<ChildInstallment> data) {
        this.data = data;
    }

    public ChildDataWrapper(){
        super();
    }
}