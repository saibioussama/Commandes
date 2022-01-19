package com.example.commandes;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AfficherCommandeActivity  extends AppCompatActivity {
    TextView detail;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_commande);
        detail = findViewById(R.id.tvAfficherCommande);
        Intent intent = getIntent();
        int id =intent.getIntExtra("id",0);
        detail.setText( getCmdDetails(id));
    }
    @SuppressLint("WrongConstant")
    public String getCmdDetails(int id){
        SQLiteDatabase sqlDb = openOrCreateDatabase("app.db", SQLiteDatabase.OPEN_READWRITE, null);
        Cursor cursor = sqlDb.rawQuery("SELECT * FROM commandes where id="+id, null);
        if (!cursor.moveToFirst())
            return "";
        String desc = cursor.getString(1);
        return desc;
    }
}
