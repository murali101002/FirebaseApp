package android.com.inclass10;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by murali101002 on 11/7/2016.
 */
public class ExpenseObject implements Parcelable {
    String expenseName;
    String expenseCategory;
    String expenseDate;
    String fullName;

    protected ExpenseObject(Parcel in) {
        expenseName = in.readString();
        expenseCategory = in.readString();
        expenseDate = in.readString();
        fullName = in.readString();
        expenseAmount = in.readString();
    }

    public ExpenseObject() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expenseName);
        dest.writeString(expenseCategory);
        dest.writeString(expenseDate);
        dest.writeString(fullName);
        dest.writeString(expenseAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExpenseObject> CREATOR = new Creator<ExpenseObject>() {
        @Override
        public ExpenseObject createFromParcel(Parcel in) {
            return new ExpenseObject(in);
        }

        @Override
        public ExpenseObject[] newArray(int size) {
            return new ExpenseObject[size];
        }
    };

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    String expenseAmount;
}
