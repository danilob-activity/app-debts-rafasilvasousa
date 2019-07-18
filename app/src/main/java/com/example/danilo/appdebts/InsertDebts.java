package com.example.danilo.appdebts;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.danilo.appdebts.adapters.DebtsAdapter;
import com.example.danilo.appdebts.classes.Category;
import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.DAO.CategoryDAO;
import com.example.danilo.appdebts.DAO.DebtsDAO;
import com.example.danilo.appdebts.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rafael Sousa (github.com/rafasilvasousa) on 11/07/19.
 */

public class InsertDebts extends AppCompatActivity{
    EditText mEditTextDataPay;
    Spinner mSpinnerCategory;
    final Calendar mCalendar = Calendar.getInstance();

    TextView mTextViewPayment;
    TextView mTextViewPay;
    EditText mEditTextPay;
    Debts mDebt=null;
    EditText mEditTextDescription;
    EditText mEditTextValue;

    boolean mFlag=true;
    Switch mSwitchPay;

    CategoryDAO mCategoryDAO;
    DebtsDAO mDebtsDAO;
    private SQLiteDatabase mConection;
    private DatabaseHelper mDatabaseHelper;

    ArrayAdapter<String> adp1;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_debts);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.titleInsert);

        mSpinnerCategory = findViewById(R.id.spinnerCategories);

        mEditTextDataPay = findViewById(R.id.editTextDate);
        final DatePickerDialog.OnDateSetListener date =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();

            }
        };

        mEditTextDataPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new DatePickerDialog(InsertDebts.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButtonAddCategory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InsertDebts.this);
                builder.setTitle(R.string.newCategoryTitle);

                final EditText input = new EditText(InsertDebts.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCategoryDAO.insert(new Category(input.getText().toString()));
                        updateSpinnerCategory();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        createConnection();
        updateSpinnerCategory();
        mTextViewPayment = findViewById(R.id. textViewPaymentorPay);
        mTextViewPay = findViewById(R.id. textViewPay);
        mEditTextPay = findViewById(R.id. editTextPay);
        checkParameters();
        updateUI();
        mEditTextPay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(InsertDebts. this, date, mCalendar
                        .get(Calendar. YEAR), mCalendar.get(Calendar. MONTH),
                        mCalendar.get(Calendar. DAY_OF_MONTH)).show();
            }
        });

        mEditTextDescription = findViewById(R.id.editTextDescription);
        mEditTextValue = findViewById(R.id.editTextValue);
        mSwitchPay=findViewById(R.id.switchPay);



    }
    private void updateUI(){
        if(mFlag){ //inserção
            mTextViewPay.setVisibility(View.GONE);
            mEditTextPay.setVisibility(View.GONE);
        }else{
            mTextViewPay.setVisibility(View.VISIBLE);
            mEditTextPay.setVisibility(View.VISIBLE);
            mSwitchPay.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Change Debt");
            mTextViewPayment.setText("Payment");
        }
    }
    private void checkParameters(){
        Bundle bundle = getIntent().getExtras();
        if((bundle!=null) && bundle.containsKey( "DEBT")){
            mDebt = (Debts) bundle.getSerializable( "DEBT");
            mEditTextDescription.setText(mDebt.getDescription());
            mEditTextDataPay.setText(mDebt.getExpire_date());
            mEditTextPay.setText(mDebt.getPayment_date());
            mEditTextValue.setText(String. valueOf(mDebt.getValor()));
            mSpinnerCategory.setSelection( adp1.getPosition( mDebt.getCategory().getType()));
            mFlag = false;
        }
    }

    public void updateSpinnerCategory(){
        List<Category> categories = mCategoryDAO.listCategories();
        mSpinnerCategory.setAdapter(null);

        final List<String> list = new ArrayList<String>();

        for (int i=0; i<categories.size(); i++){
            Category cat = categories.get(i);
            list.add(cat.getType());
        }

        adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adp1);
    }

    private void createConnection(){
        try{
            mDatabaseHelper = new DatabaseHelper(this);
            mConection = mDatabaseHelper.getWritableDatabase();
            mDebtsDAO = new DebtsDAO(mConection);
            mCategoryDAO = new CategoryDAO(mConection);
        }catch (SQLException e){

        }
    }

    private void updateLabel(){
        String mFormat= "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(mFormat, Locale.US);

        mEditTextDataPay.setText(sdf.format(mCalendar.getTime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_debts,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private Debts checkData(){
        Debts debt = null;
        String msg = "";
        if(mEditTextDescription.getText().toString().isEmpty())
            msg = "*Informe a descrição do débito.\n";

        if(mEditTextValue.getText().toString().isEmpty())
            msg += "*Informe o valor do débito.\n";
        else if(Float.parseFloat(mEditTextValue.getText().toString())<=0)
            msg += "*Informe um valor válido (>0) para o débito.\n";

        if(mEditTextDataPay.getText().toString().isEmpty())
            msg += "*Informe a data do débito.\n";

        if(!msg.isEmpty())
            createAlertDialog(msg);
        else{
            //criar uma instância do débito
            debt = new Debts();
            debt.setCategory(mCategoryDAO.getCategory((Long) mSpinnerCategory.getSelectedItem()));
            debt.setExpire_date(mEditTextDataPay.getText().toString());
            debt.setDescription(mEditTextDescription.getText().toString());
            debt.setValor((double) Float.parseFloat(mEditTextValue.getText().toString()));
            if(mSwitchPay.isChecked()){
                debt.setExpire_date(debt.getExpire_date());
            }else{
                debt.setExpire_date("");
            }
        }
        if(!mFlag){
            debt.setId(mDebt.getId());
            debt.setExpire_date(mEditTextPay.getText().toString());
        }else{
            if(mSwitchPay.isChecked()){
                debt.setExpire_date(mEditTextDataPay.getText().toString());
            }else{
                debt.setExpire_date("");
            }
        }
        return debt;
    }

    private void createAlertDialog(String msg) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Erro");
        //define a mensagem
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.create();
        //Exibe
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainWindow.class));
                finishAffinity();
                break;
            case R.id.okMenu:
                Debts debt = checkData();
                if (debt!=null){
                    mDebtsDAO.insert(debt);
                    startActivity(new Intent(this, MainWindow.class));
                    finishAffinity();
                }
//                Log.d("Item Menu", "Menu"+R.string.okMenu);
                break;
            default:break;
        }
        return true;
    }


}