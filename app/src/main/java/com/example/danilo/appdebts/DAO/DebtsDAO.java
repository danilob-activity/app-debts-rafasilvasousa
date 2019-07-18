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

    public long insert(Debts deb){
        ContentValues contentValues=new ContentValues();
        contentValues.put("cod_cat", deb.getCategory().getId());
        contentValues.put("valor", deb.getValor());
        contentValues.put("descricao", deb.getDescription());
        contentValues.put("data_vencimento", String.valueOf(deb.getExpire_date()));
        contentValues.put("data_pagamento", String.valueOf(deb.getPayment_date()));

        long id = mConnection.insertOrThrow("dividas", null, contentValues);
        Log.d("DebtsDAO", "Inserção realizada com sucesso!");
        return id;
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
                deb.setCategory(new CategoryDAO(mConnection).getCategory(result.getLong(result.getColumnIndexOrThrow("cod_cat"))));
                deb.setDescription(result.getString(result.getColumnIndexOrThrow("descricao")));
                deb.setValor(result.getDouble(result.getColumnIndexOrThrow("valor")));
                deb.setExpire_date(result.getString(result.getColumnIndexOrThrow("data_vencimento")));
                deb.setPayment_date(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
                debies.add(deb);
                Log.d("DebtsDAO","Listando: "+deb.getId()+" - "+deb.getDescription());
                //cat.setType(result.getString(result.getColumnIndexOrThrow("tipo")));
                //categories.add(cat);
                //Log.d("CategoryDAO", "Listando: "+cat.getId()+" - "+cat.getType());
            }while(result.moveToNext());
            result.close();
        }
        return debies;
    }

    public Debts getDebts (long id){
        Debts deb = new Debts();
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = mConnection.rawQuery(ScriptDLL.getDebt(), params);
        if (result.getCount()>0){
            result.moveToFirst();
            deb.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            deb.setCategory(new CategoryDAO(mConnection).getCategory(result.getInt(result.getColumnIndexOrThrow("cod_cat"))));
            deb.setDescription(result.getString(result.getColumnIndexOrThrow("descricao")));
            deb.setValor(result.getDouble(result.getColumnIndexOrThrow("valor")));
            deb.setExpire_date(result.getString(result.getColumnIndexOrThrow("data_vencimento")));
            deb.setPayment_date(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
            result.close();
            return deb;
        }
        return null;
    }
    public List<Debts> listDebtsByCategory(){
        List<Debts> debies = new ArrayList<Debts>();
        Cursor result = mConnection.rawQuery(ScriptDLL.getDebtsByCategory(), null);

        if (result.getCount()>0){
            result.moveToFirst();
            do{
                Debts deb = new Debts();
                deb.setId(result.getLong(result.getColumnIndexOrThrow("id")));
                deb.setCategory(new CategoryDAO(mConnection).getCategory(result.getLong(result.getColumnIndexOrThrow("cod_cat"))));
                deb.setDescription(result.getString(result.getColumnIndexOrThrow("descricao")));
                deb.setValor(result.getDouble(result.getColumnIndexOrThrow("valor")));
                deb.setExpire_date(result.getString(result.getColumnIndexOrThrow("data_vencimento")));
                deb.setPayment_date(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
                debies.add(deb);
                Log.d("DebtsDAO","Listando: "+deb.getId()+" - "+deb.getDescription());
                //cat.setType(result.getString(result.getColumnIndexOrThrow("tipo")));
                //categories.add(cat);
                //Log.d("CategoryDAO", "Listando: "+cat.getId()+" - "+cat.getType());
            }while(result.moveToNext());
            result.close();
        }
        return debies;
    }

}
