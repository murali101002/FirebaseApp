package android.com.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class Expense extends AppCompatActivity implements View.OnClickListener {
    public static String EXPENSE = "expense";
    TextView errorMsg;
    ListView expensesListView;
    ImageView plusImage;
    ArrayList<ExpenseObject> expensesList = new ArrayList<>();
    ArrayList<String> keyList = new ArrayList<>();
    private DatabaseReference mDatabase = null;
    String mUserId =null;
    ExpenseArrayAdapter adapter = null;
// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        errorMsg = (TextView) findViewById(R.id.noExpenses_lbl);
        expensesListView = (ListView) findViewById(R.id.listView);
        plusImage = (ImageView) findViewById(R.id.addExpenseImg);
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        showList(expensesList);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Expenses").child(mUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expensesList = new ArrayList<>();
                for(DataSnapshot expense:dataSnapshot.getChildren()){
                    ExpenseObject expenseObject = expense.getValue(ExpenseObject.class);
                    String key = expense.getKey();
                    keyList.add(key);
                    expensesList.add(expenseObject);
                }
                showList(expensesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        expensesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showExpense = new Intent(Expense.this,ShowExpense.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(EXPENSE,expensesList.get(position));
                showExpense.putExtras(bundle);
                startActivity(showExpense);
            }
        });
        expensesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                mDatabase.child("Expenses").child(mUserId).child(keyList.get(position)).push().removeValue();
                Toast.makeText(Expense.this,"Expense Deleted", Toast.LENGTH_SHORT).show();
                adapter.setNotifyOnChange(true);
                expensesList.remove(position);
                showList(expensesList);
                return true;
            }
        });
        plusImage.setOnClickListener(this);
        showList(expensesList);
    }

    private void showList(ArrayList<ExpenseObject> expensesList) {
        if(expensesList==null || expensesList.size()<1){
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText(R.string.errorMsg);
            expensesListView.setVisibility(View.INVISIBLE);
        }else{
            errorMsg.setVisibility(View.GONE);
            expensesListView.setVisibility(View.VISIBLE);
            adapter = new ExpenseArrayAdapter(this,R.layout.custom_layout,expensesList);
            expensesListView.setAdapter(adapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        showList(expensesList);
    }



    @Override
    public void onClick(View v) {
        Intent addNewExpense = new Intent(Expense.this,AddExpense.class);
        startActivity(addNewExpense);
    }

    public void onClickLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Expense.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(Expense.this,"You are successfully Logged out", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
