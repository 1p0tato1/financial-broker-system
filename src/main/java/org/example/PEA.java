package org.example;

import org.example.exceptions.TransactionFractionneeInvalideException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PEA extends Enveloppe {

    public PEA(String numero, double soldeEspeces) {
        super(numero, soldeEspeces, 0.0);
    }

    // Exception
    @Override
    public void passerUnOrdre(Ordre o) throws TransactionFractionneeInvalideException {
        if (o.getQuantite() % 1 != 0) {
            throw new TransactionFractionneeInvalideException("Erreur : Impossible d'acheter une fraction d'actif sur un PEA !");
        }
        super.passerUnOrdre(o);
    }

    @Override
    public boolean estActifEligible(Actif actif) {
        return true;
    }

    @Override
    public double calculerFiscalite() {
        long anneesDetention = ChronoUnit.YEARS.between(getDateOuverte(), LocalDateTime.now());
        double taux;
        if (anneesDetention >= 5) {
            taux = 0.186;
        } else {
            taux = 0.314;
        }
        return getValeurTotaleBrute() * taux;
    }
}