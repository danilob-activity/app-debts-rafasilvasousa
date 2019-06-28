package com.example.danilo.appdebts;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.danilo.appdebts.DAO.CategoryDAO;
import com.example.danilo.appdebts.DAO.DebtsDAO;
import com.example.danilo.appdebts.classes.Category;
import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.database.DatabaseHelper;

public class TelaInicial extends AppCompatActivity {


    private SQLiteDatabase mConnection;
    private DatabaseHelper mDataHelper;
    private ConstraintLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        mLayout = findViewById(R.id.layout);

        createConnection();
        CategoryDAO catDAO = new CategoryDAO(mConnection);
        DebtsDAO debtDAO = new DebtsDAO(mConnection);

        Category cat1 = new Category();
        cat1.setType("Casa");
        Category cat2= new Category();
        cat2.setType("Comida");
        Category cat3 = new Category();
        cat3.setType("Trasporte");

        catDAO.insert(cat1);
        catDAO.insert(cat2);
        catDAO.insert(cat3);

        Debts deb1 = new Debts();
        deb1.setDescription("Conta de Luz");
        deb1.setCategory(catDAO.getCategory(1));
        deb1.setValor(115.00);
        deb1.setExpire_date("15/06/2019");
        deb1.setPayment_date("");

        Debts deb2 = new Debts();
        deb2.setDescription("Quentinha da Val");
        deb2.setCategory(catDAO.getCategory(2));
        deb2.setValor(130.00);
        deb2.setExpire_date("30/06/2019");
        deb2.setPayment_date("30/06/2019");

        Debts deb3 = new Debts();
        deb3.setDescription("Seu Gilberto");
        deb3.setCategory(catDAO.getCategory(3));
        deb3.setValor(150.00);
        deb3.setExpire_date("30/06/2019");
        deb3.setPayment_date("30/06/2019");

        Debts deb4 = new Debts();
        deb4.setDescription("Conta de Agua");
        deb4.setCategory(catDAO.getCategory(1));
        deb4.setValor(40.00);
        deb4.setExpire_date("15/06/2019");
        deb4.setPayment_date("");

        Debts deb5 = new Debts();
        deb5.setDescription("Cartao de Credito");
        deb5.setCategory(catDAO.getCategory(1));
        deb5.setValor(9000.00);
        deb5.setExpire_date("15/06/2019");
        deb5.setPayment_date("");



        mConnection.close();
    }


    private void createConnection(){
        try{
            mDataHelper = new DatabaseHelper(this);
            mConnection = mDataHelper.getWritableDatabase();
            Snackbar.make(mLayout, R.string.sucess_connection, Snackbar.LENGTH_LONG).show();
        }catch (SQLException e){
            Snackbar.make(mLayout, e.toString(), Snackbar.LENGTH_LONG).show();
        }
    }
}
