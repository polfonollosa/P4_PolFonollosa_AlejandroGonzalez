package Alumnes;

public class Assignatura {

    private boolean mHonor;
    private double nota;
    private final int credits;
    private final String nom;

    public static final int EXCELENT = 9;
    public static final int NOTABLE = 7;
    public static final int APROVAT = 5;

    public Assignatura(String nom, int credits, boolean mHonor, double nota) {
        if (credits < 0 || nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Credits no poden ser negatius o nota");
        }

        this.nom = nom;
        this.credits = credits;
        this.nota = nota;

        if (nota >= Assignatura.EXCELENT && mHonor) {
            this.mHonor = true;
        } else {
            this.mHonor = false;
        }
    }

    public Assignatura(String nom){
        this(nom, 0, false, 0.0);
    }

    public void setNota(double novaNota) {
        if(novaNota < 0 || novaNota > 10) {
            throw new IllegalArgumentException("Nota no pot ser negativa o superior a 10");
        }
        this.nota = novaNota;
    }

    public double getNota(){
        return this.nota;
    }

    public int getPunts(){
        if(nota >= 9 && mHonor){
            return 4;
        }
        else if(nota >= 9){
            return 3;
        }
        else if(nota >= 7){
            return 2;
        }
        else if(nota >= 5){
            return 1;
        }
        return 0;
    }

    public int getCredits(){
        return this.credits;
    }

    @Override
    public String toString(){
        return "Nom: " + this.nom + " , " + "Nota: " + this.nota;
    }

    @Override
    public boolean equals(Object obj){

        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(obj instanceof Assignatura){
            Assignatura a = (Assignatura) obj;
            return this.nom.equals(a.nom);
        }
        return false;
    }

}
