package com.example.danilo.appdebts.database;

/**
 * Created by Rafael Sousa on 26/06/19.
 */

public class ScriptDLL {
    public static String createTableCategory() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE categoria (");
        sql.append("id INTEGER NOT NULL PRIMARY KEY,");
        sql.append("tipo TEXT NOT NULL");
        sql.append(");");
        return sql.toString();
    }

    public static String createTableDebts(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE dividas (");
        sql.append(" id integer PRIMARY KEY,");
        sql.append(" cod_cat integer NOT NULL,");
        sql.append(" valor Real not NULL,");
        sql.append(" descricao text not NULL,");
        sql.append(" data_vencimento date NOT NULL,");
        sql.append(" data_pagamento date,");
        sql.append(" FOREIGN KEY (cod_cat) REFERENCES categoria(id)");
        sql.append(");");
        return sql.toString();

    }

    public static String getCategories(){
        return "SELECT * from categoria;";
    }


    public static String getCategory(){
        return "SELECT * from categoria where id=?;";
    }

    public static String getDebies(){return "SELECT * from dividas;";}

    public static String getDebt(){
        return "SELECT * from dividas where id=?;";
    }

    public static String getDebtsByCategory(){
        return "select * from dividas order by cod_cat asc";
    }
}
