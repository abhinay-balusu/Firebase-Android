package com.abhinaybalusu.inclass10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhinaybalusu on 11/7/16.
 */
public class CustomAdapter extends ArrayAdapter<Expense> {

    List<Expense> mData;
    Context mContext;
    int mResource;

    public CustomAdapter(Context context, int resource, List<Expense> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }

        TextView expenseNameTV = (TextView)convertView.findViewById(R.id.expenseNameTV);
        TextView expenseAmountTV = (TextView)convertView.findViewById(R.id.expenseAmountTV);

        Expense expense = mData.get(position);

        expenseNameTV.setText(expense.getExpenseName());
        expenseAmountTV.setText("$"+expense.getExpenseAmount());

        return convertView;
    }
}