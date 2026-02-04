package org.example;

import org.example.enums.TypeDistribution;
import org.example.enums.TypeReplication;

public class ETF {
    private double tailleFondsM;
    private double pourcentageFrais;
    private TypeDistribution typeDistribution;
    private TypeReplication typeReplication;

    public ETF(double taille, double frais, TypeDistribution dist, TypeReplication repli) {
        this.tailleFondsM = taille;
        this.pourcentageFrais = frais;
        this.typeDistribution = dist;
        this.typeReplication = repli;
    }

}
