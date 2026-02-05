package org.example;

import java.util.Objects;

public class Position {
    private double quantite;

    // Constructor

    public Position(double quantite) {
        this.quantite = quantite;
    }

    // Setters and Getters

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
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
