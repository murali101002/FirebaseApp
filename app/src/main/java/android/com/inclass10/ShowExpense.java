package android.com.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowExpense extends AppCompatActivity implements View.OnClickListener {
    TextView name, category, date, amount;
    Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);
        ExpenseObject expenseObject = (ExpenseObject) getIntent().getExtras().get(Expense.EXPENSE);
        name = (TextView) findViewById(R.id.nameShowId);
        name.setText(expenseObject.getExpenseName());
        category = (TextView) findViewById(R.id.categoryShowId);
        category.setText(expenseObject.getExpenseCategory());
        date = (TextView) findViewById(R.id.dateShowId);
        date.setText(expenseObject.getExpenseDate());
        amount = (TextView) findViewById(R.id.amountShowId);
        amount.setText(expenseObject.getExpenseAmount());
        close = (Button) findViewById(R.id.closeBtn);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
