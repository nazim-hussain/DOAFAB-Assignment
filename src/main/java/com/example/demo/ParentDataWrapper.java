package com.example.demo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ParentDataWrapper {
    private List<ParentTransaction> data;

    // Getters and setters

    public List<ParentTransaction> getData() {
        return data;
    }

    public void setData(List<ParentTransaction> data) {
        this.data = data;
    }

    public ParentDataWrapper(){
        super();
    }
}