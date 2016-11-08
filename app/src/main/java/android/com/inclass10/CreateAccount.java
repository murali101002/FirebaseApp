package android.com.inclass10;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {
    public static final String FNAME = "fName";
    EditText email, pwd, fname;
    FirebaseAuth mAuth;
    Button login, create;
    String emailId, paswd;
    private DatabaseReference mDatabase;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference fbDatabase = null;
    String userId = null;
    FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        email = (EditText) findViewById(R.id.emailSIgnUP);
        pwd = (EditText) findViewById(R.id.pwd_signup);
        fname = (EditText) findViewById(R.id.fname);
        login = (Button) findViewById(R.id.cancel);
        create = (Button) findViewById(R.id.signIn);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(this);
        create.setOnClickListener(this);
        fbDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.signIn:
                if (email.getText().toString().length() < 1 || pwd.getText().toString().length() < 1 || fname.getText().toString().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Enter valid values", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    if (!task.isSuccessful()) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
                                        builder.setMessage(task.getException().getMessage())
                                                .setTitle("Email exists")
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        Log.d("TAG", "Login biscuit");
                                }
                                    else
                                    {

                                        Log.d("Task", String.valueOf(task.getResult().getUser().getUid()));
                                        fbDatabase.child("Expenses").child(String.valueOf(task.getResult().getUser().getUid())).setValue(fname.getText().toString());
                                        Intent expenseActivity = new Intent(CreateAccount.this, Expense.class);
                                        //expenseActivity.putExtra(FNAME, fname.getText().toString());
                                        startActivity(expenseActivity);
                                    }
                            }
                });
        }
        break;
        case R.id.cancel:
            Intent mainActivity = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(mainActivity);
        break;
    }
}
}
