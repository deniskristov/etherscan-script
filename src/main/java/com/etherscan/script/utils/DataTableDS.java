package com.etherscan.script.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// Do not use lombok!
// Do not replace getters and setters.
public class DataTableDS<T>
{
    private int sEcho;
    private int iTotalRecords;
    private int iTotalDisplayRecords;
    private List<T> aaData = new ArrayList<>();

    public DataTableDS()
    {
    }

    public DataTableDS(int sEcho)
    {
        this.sEcho = sEcho;
    }

    public DataTableDS(List<T> aaData)
    {
        this.aaData = aaData;
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public int getsEcho() {
        return sEcho;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }
}
