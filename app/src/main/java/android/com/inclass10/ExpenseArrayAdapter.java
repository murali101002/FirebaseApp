package android.com.inclass10;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by murali101002 on 11/8/2016.
 */
public class ExpenseArrayAdapter extends ArrayAdapter<ExpenseObject> {
    int res;
    Context context;
    List<ExpenseObject> objects;
    public ExpenseArrayAdapter(Context context, int resource, List<ExpenseObject> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res,parent,false);
        }
        ExpenseObject expense = objects.get(position);
        TextView name = (TextView) convertView.findViewById(R.id.expenseName_cust);
        TextView amount = (TextView) convertView.findViewById(R.id.expenseAMount_cust);
        convertView.setBackgroundColor(Color.GRAY);
        name.setText(expense.getExpenseName());
        amount.setText(expense.getExpenseAmount());
        return convertView;
    }
}
