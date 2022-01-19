package com.example.commandes;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AjoutArticleActivity extends AppCompatActivity {
    String categorie;
    String description;
    int unites = 1;
    int Total;
    int tarifUnitaire;
    TextView tvNombreUnites;
    TextView tvTarifTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_article);
        Intent intent = getIntent();
        categorie = intent.getStringExtra("categorie");
        description = intent.getStringExtra("description");
        tarifUnitaire = intent.getIntExtra("tarif", 0);
        ImageView ivCategorieArticle = findViewById(R.id.ivCategorieArticle);

        switch (categorie)
        {
            case Produits.cafe :
                ivCategorieArticle.setImageResource(R.drawable.cafe);
                break;

            case Produits.the :
                ivCategorieArticle.setImageResource(R.drawable.the);
                break;

            case Produits.jus:
                ivCategorieArticle.setImageResource(R.drawable.jus);
                break;

            case Produits.soda :
                ivCategorieArticle.setImageResource(R.drawable.soda);
                break;
            default:
                break;
        }
        TextView tvDescription = findViewById(R.id.tvDescription);
        tvDescription.setText(description);
        TextView tvTarifUnitaire =findViewById(R.id.tvTarifUnitaire);
        tvTarifUnitaire.setText("" + tarifUnitaire);
        tvNombreUnites = findViewById(R.id.tvNombreUnites);
        tvNombreUnites.setText("" + unites);
        Button btnArticleMoins = findViewById(R.id.btnArticleMoins);
        Button btnArticlePlus = findViewById(R.id.btnArticlePlus);

        btnArticleMoins.setOnClickListener(v -> {
            if (unites > 1)
                unites -= 1;
            tvNombreUnites.setText("" + unites);
            Total = unites * tarifUnitaire;
            tvTarifTotal.setText("" + Total);
        });
        btnArticlePlus.setOnClickListener(v -> {
            unites += 1;
            tvNombreUnites.setText("" + unites);
            Total = unites * tarifUnitaire;
            tvTarifTotal.setText("" + Total);
        });
        Total = tarifUnitaire;
        tvTarifTotal = findViewById(R.id.tvTarifTotal);
        tvTarifTotal.setText("" + Total);
        Button btnAjouterArticle = findViewById(R.id.btnAjouterArticle);
        btnAjouterArticle.setOnClickListener(v -> ajouterArticle());
        Button btnAnnulerArticle = findViewById(R.id.btnAnnulerArticle);
        btnAnnulerArticle.setOnClickListener(v -> annulerArticle());
    }

    public void annulerArticle() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void ajouterArticle() {
        Intent intent = new Intent();
        intent.putExtra("categorie", categorie);
        intent.putExtra("description", unites + " * " + description);
        intent.putExtra("tarif", Total);
        setResult(RESULT_OK, intent);
        finish();
    }
}