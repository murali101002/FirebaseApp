package android.com.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddExpense extends AppCompatActivity implements View.OnClickListener {
    EditText name, amount;
    Spinner categoryList;

    Button cancel, addExpense;
    String expenseName, expenseAmount, item, mUserId, fname;
    ExpenseObject expenseObject = null;
    private DatabaseReference mDatabase = null;
    ArrayList<ExpenseObject> expensesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        name = (EditText) findViewById(R.id.expenseName);
        amount = (EditText) findViewById(R.id.editTextAmountAdd);
        categoryList = (Spinner) findViewById(R.id.category_items);
       // fname = (String) getIntent().getExtras().get(CreateAccount.FNAME);
        ArrayAdapter<CharSequence> categories_list = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        categories_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(categories_list);
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        categoryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addExpense = (Button) findViewById(R.id.addExpenseBtn);
        addExpense.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cancel = (Button) findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(this);
    }

    public static interface pushData{
        public void pushList(ArrayList<ExpenseObject> expensesList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addExpenseBtn:
                expenseName = name.getText().toString();
                expenseAmount = amount.getText().toString();
                if (expenseName.equals("") || expenseAmount.equals("")) {
                    Toast.makeText(AddExpense.this, "Add valid values", Toast.LENGTH_SHORT).show();
                } else {
                    expenseObject = new ExpenseObject(  );
                    expenseObject.setExpenseAmount("$" + expenseAmount);
                    expenseObject.setExpenseCategory(item);
                    expenseObject.setExpenseName(expenseName);
                    //expenseObject.setFullName(fname);
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    expenseObject.setExpenseDate(dateFormat.format(date));
                    mDatabase.child("Expenses").child(mUserId).push().setValue(expenseObject);
                    Toast.makeText(AddExpense.this, "New Expense Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.cancelBtn:
                finish();
                break;
        }
    }
}

