package org.example;

import org.example.enums.TypeOrdre;

import java.time.LocalDateTime;
import java.util.*;

public abstract class Enveloppe {
    private String numero;
    private LocalDateTime dateOuverte;
    private double soldeEspeces;
    private double frais;
    private double fraisDepot;

    protected Map<String, Position> portefeuille = new HashMap<>();
    protected List<Ordre> ordreEnCours = new ArrayList<>();
    protected List<Transaction> transactions = new ArrayList<>();

    // Function Methods
    public abstract boolean estActifEligible(Actif actif);

    public abstract double calculerFiscalite();

    public void passerUnOrdre(Ordre o) {
        this.ordreEnCours.add(o);

        Actif actifConcerne = o.getActif();
        double montantTotal = o.getQuantite() * o.getPrixMinMax();
        String ticker = actifConcerne.getTicker();

        if (o.getType() == TypeOrdre.ACHAT) {
            // Achat

            // Vérifications
            if (!this.estActifEligible(actifConcerne)) {
                System.out.println("ERREUR : L'actif " + actifConcerne.getNom() + " n'est pas éligible pour cette enveloppe.");
                return;
            }

            if (this.soldeEspeces < montantTotal) {
                System.out.println("ERREUR : Fonds insuffisants (Solde: " + this.soldeEspeces + " / Requis: " + montantTotal + ")");
                return;
            }

            // Exécution financière
            this.soldeEspeces -= montantTotal;

            // Mise à jour du portefeuille
            if (portefeuille.containsKey(ticker)) {
                Position positionExistante = portefeuille.get(ticker);
                double nouvelleQuantite = positionExistante.getQuantite() + o.getQuantite();
                positionExistante.setQuantite(nouvelleQuantite);
            } else {
                Position nouvellePosition = new Position(actifConcerne, o.getQuantite());
                portefeuille.put(ticker, nouvellePosition);
            }

            // Création de la Transaction
            Transaction t = new Transaction(montantTotal, o.getPrixMinMax());
            this.transactions.add(t);

            System.out.println("ACHAT réussi : " + o.getQuantite() + " x " + actifConcerne.getNom());

        } else if (o.getType() == TypeOrdre.VENTE) {
            // VENTE

            // Vérifications
            if (!portefeuille.containsKey(ticker)) {
                System.out.println("ERREUR : Vous ne possédez pas l'actif " + actifConcerne.getNom());
                return;
            }

            Position positionActuelle = portefeuille.get(ticker);

            if (positionActuelle.getQuantite() < o.getQuantite()) {
                System.out.println("ERREUR : Quantité insuffisante en portefeuille.");
                return;
            }

            // Exécution financière
            this.soldeEspeces += montantTotal;

            // Mise à jour du portefeuille
            double nouvelleQuantite = positionActuelle.getQuantite() - o.getQuantite();
            if (nouvelleQuantite == 0) {
                portefeuille.remove(ticker);
            } else {
                positionActuelle.setQuantite(nouvelleQuantite);
            }

            // Création de la Transaction
            Transaction t = new Transaction(montantTotal, o.getPrixMinMax());
            this.transactions.add(t);

            System.out.println("VENTE réussie : " + o.getQuantite() + " x " + actifConcerne.getNom());
        }
    }

    public Actif rechercherActif(String ticker) {
        Position p = portefeuille.get(ticker);
        if (p != null) {
            return p.getActif();
        }
        return null;
    }

    public double getValeurTotaleBrute() {
        double valeurPositions = 0.0;
        for (Position p : portefeuille.values()) {
            valeurPositions += p.getValeur();
        }
        return this.soldeEspeces + valeurPositions;
    }

    public double getValeurTotaleNet() {
        return getValeurTotaleBrute() - calculerFiscalite();
    }

    public void afficherOrdreEnCours(){
        System.out.println("Ordres en cours :");
        for (Ordre o : ordreEnCours) {
            System.out.println(o.toString());
        }
    }

    public void afficherTransactions(){
        System.out.println("Transactions :");
        for (Transaction t : transactions ) {
            System.out.println(t.toString());
        }
    }
    // Constructor

    public Enveloppe(String numero, double soldeEspeces, double fraisDepot) {
        this.numero = numero;
        this.soldeEspeces = soldeEspeces;
        this.fraisDepot = fraisDepot;
        this.dateOuverte = LocalDateTime.now();
    }


    // Setters and Getters

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getDateOuverte() {
        return dateOuverte;
    }

    public void setDateOuverte(LocalDateTime dateOuverte) {
        this.dateOuverte = dateOuverte;
    }

    public double getSoldeEspeces() {
        return soldeEspeces;
    }

    public void setSoldeEspeces(double soldeEspeces) {
        this.soldeEspeces = soldeEspeces;
    }

    public double getFrais() {
        return frais;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public double getFraisDepot() {
        return fraisDepot;
    }

    public void setFraisDepot(double fraisDepot) {
        this.fraisDepot = fraisDepot;
    }

    public Map<String, Position> getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(Map<String, Position> portefeuille) {
        this.portefeuille = portefeuille;
    }

    public List<Ordre> getOrdreEnCours() {
        return ordreEnCours;
    }

    public void setOrdreEnCours(List<Ordre> ordreEnCours) {
        this.ordreEnCours = ordreEnCours;
    }

    // Basic Methods

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enveloppe enveloppe = (Enveloppe) o;
        return Double.compare(soldeEspeces, enveloppe.soldeEspeces) == 0 && Double.compare(frais, enveloppe.frais) == 0 && Double.compare(fraisDepot, enveloppe.fraisDepot) == 0 && Objects.equals(numero, enveloppe.numero) && Objects.equals(dateOuverte, enveloppe.dateOuverte) && Objects.equals(portefeuille, enveloppe.portefeuille) && Objects.equals(ordreEnCours, enveloppe.ordreEnCours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, dateOuverte, soldeEspeces, frais, fraisDepot, portefeuille, ordreEnCours);
    }

    @Override
    public String toString() {
        return "Enveloppe{" +
                "numero='" + numero + '\'' +
                ", dateOuverte=" + dateOuverte +
                ", soldeEspeces=" + soldeEspeces +
                ", frais=" + frais +
                ", fraisDepot=" + fraisDepot +
                ", portefeuille=" + portefeuille +
                ", ordreEnCours=" + ordreEnCours +
                '}';
    }
}
