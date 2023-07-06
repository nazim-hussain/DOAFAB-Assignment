package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class ParentNewModel {
   private int totalRows ;
   private List<ParentTransaction> parentTransaction;

    public ParentNewModel(int totalRows, List<ParentTransaction> parentTransaction) {
        super();
        this.totalRows = totalRows;
        this.parentTransaction = parentTransaction;
    }

    public ParentNewModel() {
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<ParentTransaction> getParentTransaction() {
        return parentTransaction;
    }

    public void setParentTransaction(ArrayList<ParentTransaction> parentTransaction) {
        this.parentTransaction = parentTransaction;
    }
}
