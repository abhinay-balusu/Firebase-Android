package com.abhinaybalusu.inclass10;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abhinaybalusu on 11/7/16.
 */
public class Expense implements Parcelable{

    String expenseName, expenseCategory, expenseAmount, expenseDate;

    public Expense(String expenseName, String expenseCategory, String expenseAmount, String expenseDate) {
        this.expenseName = expenseName;
        this.expenseCategory = expenseCategory;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
    }
    public Expense()
    {

    }

    protected Expense(Parcel in) {
        expenseName = in.readString();
        expenseCategory = in.readString();
        expenseAmount = in.readString();
        expenseDate = in.readString();
    }

    public static final Parcelable.Creator<Expense> CREATOR = new Parcelable.Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }


    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expenseName);
        dest.writeString(expenseCategory);
        dest.writeString(expenseAmount);
        dest.writeString(expenseDate);
    }
}
