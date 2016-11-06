package edu.kvcc.cis298.cis298assignment3.models;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by doc on 10/23/16.
 */

public class Beverage {
    private String mItemNumber;
    private String mItemDescription;
    private String mPackSize;
    private double mCasePrice;
    private Boolean mActive;

    public Beverage(String itemNumber, String itemDescription, String packSize, double casePrice, Boolean active) {
        mItemNumber = itemNumber;
        mItemDescription = itemDescription;
        mPackSize = packSize;
        mCasePrice = casePrice;
        mActive = active;
    }

    public static Beverage fromTokens(String[] tokens) {
        if (tokens.length == 5) {
            return new Beverage(tokens[0],
                    tokens[1],
                    tokens[2],
                    Double.parseDouble(tokens[3]),
                    Boolean.valueOf(tokens[4]));
        } else {
            Log.d("Beverage", "Bad tokens!");
            return null;
        }
    }

    public Boolean isActive() {
        return mActive;
    }

    public void setActive(Boolean active) {
        mActive = active;
    }

    public double getCasePrice() {
        // Get the price in X.XX format
        return Math.floor(mCasePrice * 100) / 100;
    }

    public void setCasePrice(double casePrice) {
        mCasePrice = casePrice;
    }

    public String getPackSize() {
        return mPackSize;
    }

    public void setPackSize(String packSize) {
        mPackSize = packSize;
    }

    public String getItemDescription() {
        return mItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        mItemDescription = itemDescription;
    }

    public String getItemNumber() {
        return mItemNumber;
    }
}