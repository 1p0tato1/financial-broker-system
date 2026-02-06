package org.example;

public class CTO extends Enveloppe {

    public CTO(String numero, double soldeEspeces) {
        super(numero, soldeEspeces, 0.0);
    }

    @Override
    public boolean estActifEligible(Actif actif) {
        return true;
    }

    @Override
    public double calculerFiscalite() {
         return getValeurTotaleBrute() * 0.314;
    }
}