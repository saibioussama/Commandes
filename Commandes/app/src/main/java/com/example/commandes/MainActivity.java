package com.example.commandes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.commandes.AfficherCommandeActivity;
import com.example.commandes.AjouterCommandeActivity;
import com.example.commandes.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewCommande;
    ArrayList<Integer> arrayList;
    Cursor cursor;
    SQLiteDatabase sqlDb;
    ArrayAdapter arrayAdapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewCommande = findViewById(R.id.lvListeCommandes);
        arrayList = new ArrayList();

        createDb();
        readFromDb();
        InitArticles();


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listViewCommande.setAdapter(arrayAdapter);


        listViewCommande.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AfficherCommandeActivity.class);
                intent.putExtra("id",i+1);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("WrongConstant")
    public  void createDb(){
        sqlDb = openOrCreateDatabase("app.db", SQLiteDatabase.OPEN_READWRITE, null);
        sqlDb.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER "
                + " PRIMARY KEY AUTOINCREMENT, categorie VARCHAR, description VARCHAR"
                + ", tarif INTEGER);");
        sqlDb.execSQL("CREATE TABLE IF NOT EXISTS commandes (id INTEGER "
                + " PRIMARY KEY AUTOINCREMENT, description VARCHAR);");
    }

    public void readFromDb(){
        cursor = sqlDb.rawQuery("SELECT * FROM commandes", null);
        if (cursor.moveToFirst()) {
            do {
                arrayList.add( Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void InitArticles(){
        if (DatabaseUtils.queryNumEntries(sqlDb,"articles")== 0){
            AddItem("cafe","café",1500);
            AddItem("cafe","cappucin",2500);
            AddItem("the","thé",1000);
            AddItem("jus","jus d'orange",2300);
            AddItem("jus","limonade",2300);
            AddItem("soda","coca-cola",1800);
            AddItem("soda","fanta",1800);
            AddItem("eau","eau 1/2 litre",1500);
            AddItem("eau","eau 1.5 litre",2500);
        }
    }

    public  void AddItem(String cat,String desc,int tar){
        ContentValues vals = new ContentValues();
        vals.put("categorie", cat);
        vals.put("description", desc);
        vals.put("tarif", tar);
        long id = sqlDb.insert("articles", null, vals);
        if (id == -1)
            Toast.makeText(this, "not able to insert item in Database" , Toast.LENGTH_LONG).show();
    }

    public void nouvelleCommande(View view) {
        Intent intent = new Intent(getApplicationContext(), AjouterCommandeActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                arrayList.clear();
                readFromDb();
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

} 