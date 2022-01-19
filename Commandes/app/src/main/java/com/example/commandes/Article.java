package com.example.commandes;

class Article {
    String description;
    String categorie;
    int tarif;
    int Image;

    public Article(String description, String categorie, int tarif, int image) {
        this.description = description;
        this.tarif = tarif;
        this.Image = image;
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getDescription() {
        return description;
    }

    public int getTarif() {
        return tarif;
    }

    public int getImage() {
        return this.Image;
    }
}
