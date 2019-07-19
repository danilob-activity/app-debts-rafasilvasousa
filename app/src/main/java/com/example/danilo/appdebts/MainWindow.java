package com.example.danilo.appdebts;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.danilo.appdebts.DAO.DebtsDAO;
import com.example.danilo.appdebts.adapters.DebtsAdapter;
import com.example.danilo.appdebts.database.DatabaseHelper;

public class MainWindow extends AppCompatActivity {

    RecyclerView mListDebts;
    DebtsAdapter mDebtsAdapter;
    DebtsDAO mDebtsDAO;
    private SQLiteDatabase mConnection;
    private DatabaseHelper mDataHelper;
    private ConstraintLayout mLayout;

    private TextView mTextViewValor;
    private TextView mTextViewValorPendente;

    private MainWindow mThisContext = this;

    final String[] mOptionsFilter = {
            "Todas as Dívidas",
            "Dívidas em Aberto",
            "Dividas Pagas",
            "Dívidas por Categoria",
            "Dívidas em Atraso"
    };

    private Spinner mSpinnerFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cad = new Intent(MainWindow.this,InsertDebts.class);
                startActivity(cad);
            }
        });

        mListDebts = findViewById(R.id.recycler_view_debts);
        mLayout = findViewById(R.id. layout);
        createConnection();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this);
        mListDebts.setLayoutManager(linearLayoutManager);
        mDebtsAdapter = new DebtsAdapter(mDebtsDAO.listDebts(), mThisContext, 0);
        mListDebts.setAdapter(mDebtsAdapter);
        mListDebts.setHasFixedSize(true);

        mTextViewValor = findViewById(R.id.textViewValor);
        mTextViewValorPendente = findViewById(R.id.textViewValorPendente);

        mSpinnerFilter = findViewById(R.id. spinnerFilter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                mOptionsFilter
        );
        mSpinnerFilter.setAdapter(adapter);

        mSpinnerFilter.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:{
                        mDebtsAdapter = new DebtsAdapter(mDebtsDAO.listDebts(), mThisContext, position);
                        mListDebts.setAdapter(mDebtsAdapter);
                    }
                    case 1:{

                    }
                    case 2:{

                    }
                    case 3:{
                        mDebtsAdapter = new DebtsAdapter(mDebtsDAO.listDebtsByCategory(), mThisContext, position);
                        mListDebts.setAdapter(mDebtsAdapter);
                    }

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) { // your code here
            }
        });
    }


    private void createConnection(){
        try{
            mDataHelper = new DatabaseHelper(this);
            mConnection = mDataHelper.getWritableDatabase();
            mDebtsDAO = new DebtsDAO(mConnection);
            Snackbar.make(mLayout, R.string.sucess_connection, Snackbar.LENGTH_LONG).show();
        }catch (SQLException e){
            Snackbar.make(mLayout, e.toString(), Snackbar.LENGTH_LONG).show();
        }
    }
    public void updateUI(double val_to_pay, double val_payed){
        mTextViewValor.setText(String.valueOf(val_payed));
        mTextViewValorPendente.setText(String.valueOf(val_to_pay));
    }

}
