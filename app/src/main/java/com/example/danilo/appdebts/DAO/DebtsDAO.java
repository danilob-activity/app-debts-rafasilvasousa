package com.example.danilo.appdebts.DAO;

import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.database.ScriptDLL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Sousa on 27/06/19.
 */

public class DebtsDAO {
    private SQLiteDatabase mConnection;
    public DebtsDAO(SQLiteDatabase connection){
        mConnection = connection;
    }

    public void insert(Debts deb){
        ContentValues contentValues=new ContentValues();
        contentValues.put("cod_cat", deb.getCategory().getId());
        contentValues.put("valor", deb.getValor());
        contentValues.put("descricao", deb.getDescription());
        contentValues.put("data_vencimento", String.valueOf(deb.getExpire_date()));
        contentValues.put("data_pagamento", String.valueOf(deb.getPayment_date()));

        mConnection.insertOrThrow("dividas", null, contentValues);
        Log.d("DebtsDAO", "Inserção realizada com sucesso!");
    }


    public void remove(long id){
        String[] params = new String[1];
        params[0]=String.valueOf(id);
        mConnection.delete("dividas","id=?", params);

    }
    public void alter(Debts deb){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cod_cat", deb.getCategory().getId());
        contentValues.put("valor", deb.getValor());
        contentValues.put("descricao", deb.getDescription());
        contentValues.put("data_vencimento", String.valueOf(deb.getExpire_date()));
        contentValues.put("data_pagamento", String.valueOf(deb.getPayment_date()));

        String[] params = new String[1];
        params[0] = String.valueOf(deb.getId());

        mConnection.update("dividas", contentValues, "id=?", params);

    }

    public List<Debts> listDebts(){
        List<Debts> debies = new ArrayList<Debts>();
        Cursor result = mConnection.rawQuery(ScriptDLL.getDebies(), null);
        if (result.getCount()>0){
            result.moveToFirst();
            do{
                Debts deb = new Debts();
                deb.setId(result.getLong(result.getColumnIndexOrThrow("id")));
                deb.setCategory(result.getLong());

                //cat.setType(result.getString(result.getColumnIndexOrThrow("tipo")));
                //categories.add(cat);
                //Log.d("CategoryDAO", "Listando: "+cat.getId()+" - "+cat.getType());
            }while(result.moveToNext());
            result.close();
        }
        return debies;
    }

    public Debts getDebts (long id){}
}
