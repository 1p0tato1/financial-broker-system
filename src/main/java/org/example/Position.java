package org.example;

import java.util.Objects;

public class Position {
    private double quantite;
    private Actif actif;

    // Constructor

    public Position(Actif actif, double quantite) {
        this.actif = actif;
        this.quantite = quantite;
    }

    // Setters and Getters
    public Actif getActif() {
        return actif;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getValeur() {
        return actif.getPrixCourant() * quantite;
    }
    // Basic Methods

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(quantite, position.quantite) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(quantite);
    }

    @Override
    public String toString() {
        return "Position{" +
                "quantite=" + quantite +
                '}';
    }
}
