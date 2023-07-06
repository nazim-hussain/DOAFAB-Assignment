package com.example.demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChildInstallment {


    @SerializedName("data")
    @Expose
    private ArrayList<Data> data;

    public ChildInstallment(){

    }

    public ChildInstallment(ArrayList<Data> data){
        super();
        this.data = data;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }


    public static class Data {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("parentId")
        @Expose
        private Integer parentId;
        @SerializedName("paidAmount")
        @Expose
        private Integer paidAmount;

        /**
         * No args constructor for use in serialization
         */
        public Data() {
        }

        /**
         * @param id
         * @param paidAmount
         * @param parentId
         */
        public Data(Integer id, Integer parentId, Integer paidAmount) {
            super();
            this.id = id;
            this.parentId = parentId;
            this.paidAmount = paidAmount;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public Integer getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(Integer paidAmount) {
            this.paidAmount = paidAmount;
        }
    }
}