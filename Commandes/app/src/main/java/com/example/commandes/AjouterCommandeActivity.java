package com.example.commandes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AjouterCommandeActivity extends AppCompatActivity {
    ListView listArticle,listCommande;
    View commandeLayout,MenuLayout;
    Button menu_btn,commande_btn;
    SQLiteDatabase sqlDb;
    CustomArrayAdapter commandeArrayAdapter, articleArrayAdapter;
    ArrayList<Article> alCommandes, alArticle;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlDb = openOrCreateDatabase("app.db", SQLiteDatabase.OPEN_READWRITE, null);
        commandeLayout =getLayoutInflater().inflate(R.layout.activity_ajouter_commande,null);
        MenuLayout = getLayoutInflater().inflate(R.layout.activity_ajouter_commande_menu,null);
        menu_btn = commandeLayout.findViewById(R.id.btnAfficherMenu);
        commande_btn = MenuLayout.findViewById(R.id.btnAfficherCommande);
        setContentView(commandeLayout);
        listArticle =MenuLayout.findViewById(R.id.lvArticlesMenu);
        listCommande =commandeLayout.findViewById(R.id.lvArticlesCommande);
        alArticle = new ArrayList<Article>();
        alCommandes = new ArrayList<Article>();
        readData();
        articleArrayAdapter = new CustomArrayAdapter(this, alArticle);
        commandeArrayAdapter = new CustomArrayAdapter(this, alCommandes);
        listArticle.setAdapter(articleArrayAdapter);

        listArticle.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(),AjoutArticleActivity.class);
            intent.putExtra("tarif", alArticle.get(i).getTarif());
            intent.putExtra("categorie", alArticle.get(i).getCategorie());
            intent.putExtra("description", alArticle.get(i).getDescription());
            startActivityForResult(intent,0);
        });


        commande_btn.setOnClickListener(view -> setContentView(commandeLayout));
        menu_btn.setOnClickListener(view -> setContentView(MenuLayout));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            if (resultCode==RESULT_OK){
                setContentView(commandeLayout);

                int tarif =data.getIntExtra("tarif",0);
                String description = data.getStringExtra("description");
                String categorie = data.getStringExtra("categorie");

                switch (categorie)
                {
                    case Produits.cafe :
                       alCommandes.add(new Article(description,categorie,tarif,R.drawable.cafe));
                       break;
                    case Produits.the :
                        alCommandes.add(new Article(description,categorie,tarif,R.drawable.the));
                        break;
                    case Produits.jus :
                        alCommandes.add(new Article(description,categorie,tarif,R.drawable.jus));
                        break;
                    case Produits.soda :
                        alCommandes.add(new Article(description,categorie,tarif,R.drawable.soda));
                        break;
                    default :
                        alCommandes.add(new Article(description,categorie,tarif,R.drawable.eau));
                        break;
                }

                commandeArrayAdapter.notify();
                listCommande.setAdapter(commandeArrayAdapter);
            }
        }
    }

    public void readData(){
        alArticle.clear();
        Cursor cursor = sqlDb.rawQuery("SELECT * FROM articles", null);
        if (cursor.moveToFirst()) {
            do {
                String categorie = cursor.getString(1);
                String description = cursor.getString(2);
                int tarif = Integer.parseInt(cursor.getString(3));

                switch (categorie)
                {
                    case  Produits.cafe:
                        alArticle.add(new Article(description,categorie,tarif,R.drawable.cafe));
                        break;
                    case  Produits.the:
                        alArticle.add(new Article(description,categorie,tarif,R.drawable.the));
                        break;
                    case  Produits.jus:
                        alArticle.add(new Article(description,categorie,tarif,R.drawable.jus));
                        break;
                    case  Produits.soda:
                        alArticle.add(new Article(description,categorie,tarif,R.drawable.soda));
                        break;
                    default:
                        alArticle.add(new Article(description,categorie,tarif,R.drawable.eau));
                        break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void ajoutCmd(View view) {
        String description = "";
        int Total = 0;
        for (int i = 0; i < alCommandes.size(); i++) {
            description = description + alCommandes.get(i).description +" : "+alCommandes.get(i).tarif;
            description += " \n";
            Total += alCommandes.get(i).tarif;
        }
        description = description + "\n ----------- \n montant : ";
        description += Total;
        ContentValues values = new ContentValues();
        values.put("description", description);
        long id = sqlDb.insert("commandes", null, values);
        if (id == -1)
            Toast.makeText(this, "not able to insert item in Database", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void annulerCommandes(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
