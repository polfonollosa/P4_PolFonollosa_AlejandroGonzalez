import Alumnes.Alumnes_SEC;
import Alumnes.Assignatura;
import EstructuraArbre.AcbEnll;
import EstructuraArbre.ArbreException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Beca {

    private AcbEnll<Alumnes_SEC> arbreACB;
    private Queue<Alumnes_SEC> llistaDescendent;
    private static Scanner scanner;

    public Beca() throws ArbreException {
        this.arbreACB = new AcbEnll<>();
        this.arbreACB.inserir(this.exempleRosa());
        this.arbreACB.inserir(this.exempleEnric());
        this.arbreACB.inserir(this.exempleRandom("Pep"));
        this.arbreACB.inserir(this.exempleRandom("Maria"));
        this.arbreACB.inserir(this.exempleRandom("Joan"));
        this.llistaDescendent = this.arbreACB.getDescendentList();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws ArbreException {
        Beca beca = new Beca();
        boolean sortir = false;

        while (!sortir) {
            System.out.println("ALUMNES POSIBLES PER A LA BECA");
            System.out.println("-------------------------------");
            System.out.println("Menu: ");
            System.out.println("1. Afegir alumne");
            System.out.println("2. Esborrar alumne a partir del seu nom");
            System.out.println("3. Mostrar alumnes amb descendent");
            System.out.println("4. Esborrar alumne sense matricula d'honor");
            System.out.println("5. Sortir");
            System.out.println("-------------------------------");
            System.out.println();

            String opcio = scanner.nextLine();

            switch (opcio) {
                case "1":
                    beca.afegirAlumne();
                    System.out.println("Alumne afegit");
                    break;
                case "2":
                    System.out.print("Introdueix el nom de l'alumne a esborrar: ");
                    String nom = scanner.nextLine();
                    Alumnes_SEC alumneAEsborrar = null;
                    Queue<Alumnes_SEC> novaLlistaDescendent = new LinkedList<>();
                    for (Alumnes_SEC alumne : beca.llistaDescendent) {
                        if (alumne.toString().contains(nom)) {
                            alumneAEsborrar = alumne;
                        } else {
                            novaLlistaDescendent.add(alumne);
                        }
                    }
                    if (alumneAEsborrar != null) {
                        beca.arbreACB.esborrar(alumneAEsborrar);
                        beca.llistaDescendent = novaLlistaDescendent;
                        System.out.println("Alumne " + nom + " esborrat");
                    } else {
                        System.err.println("Alumne no trobat");
                    }
                    break;
                case "3":
                    System.out.println("Llista d'alumnes amb descendent:");
                    System.out.println(beca);
                    break;
                case "4":
                    beca.esborrarAlumnesSenseMatricula();
                    System.out.println("Alumnes sense una matricula d'honor esborrats");
                    break;
                case "5":
                    System.out.println("Sortint...");
                    sortir = true;
                    break;
                default:
                    System.err.println("Opció incorrecta");
            }
        }
        System.out.println("Fi del programa");
    }

    private Alumnes_SEC exempleRosa(){
        Alumnes_SEC rosa = new Alumnes_SEC("Rosa");
        rosa.addAssignatura(new Assignatura("Matematiques", 6, true, 9));
        rosa.addAssignatura(new Assignatura("Llengua", 4, false, 8));
        rosa.addAssignatura(new Assignatura("Fisica", 4, false, 7));
        rosa.addAssignatura(new Assignatura("Quimica", 4, false, 6));
        return rosa;
    }

    private Alumnes_SEC exempleEnric(){
        Alumnes_SEC enric = new Alumnes_SEC("Enric");
        enric.addAssignatura(new Assignatura("Dibuix", 6, false, 9));
        enric.addAssignatura(new Assignatura("Llengua", 4, true, 10));
        enric.addAssignatura(new Assignatura("Fisica2", 4, false, 7));
        enric.addAssignatura(new Assignatura("Quimica2", 4, false, 8.9));
        return enric;
    }

    private Alumnes_SEC exempleRandom(String nom){
        Alumnes_SEC alumne = new Alumnes_SEC(nom);
        double random = (Math.random() * 10);
        alumne.addAssignatura(new Assignatura("Matematiques", 6, true, random));
        random = (Math.random() * 10);
        alumne.addAssignatura(new Assignatura("Llengua", 4, false, random));
        random = (Math.random() * 10);
        alumne.addAssignatura(new Assignatura("Fisica", 4, false, random));
        random = (Math.random() * 10);
        alumne.addAssignatura(new Assignatura("Quimica", 4, false, random));
        return alumne;
    }

    private boolean finalRecorregut() {
        return this.llistaDescendent.isEmpty();
    }

    private Alumnes_SEC segRecorregut(){
        if (this.finalRecorregut()) {
            System.err.println("No queden més elements a la llista");
        }
        return this.llistaDescendent.poll();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<Alumnes_SEC> novaLlistaDescendent = new LinkedList<>();
        while (!finalRecorregut()) {
            Alumnes_SEC alumne = segRecorregut();
            sb.append(alumne.toString()).append("\n");
            novaLlistaDescendent.add(alumne);
        }
        this.llistaDescendent = novaLlistaDescendent;
        return sb.toString();
    }

    public void esborrarAlumnesSenseMatricula() {
        Queue<Alumnes_SEC> novaLlistaDescendent = new LinkedList<>();
        while (!finalRecorregut()) {
            Alumnes_SEC alumne = segRecorregut();
            if (!alumne.hiHa(4)) {
                try {
                    this.arbreACB.esborrar(alumne);
                } catch (ArbreException e) {
                    System.err.println("Error al esborrar l'alumne: " + alumne);
                }
            } else {
                novaLlistaDescendent.add(alumne);
            }
        }
        this.llistaDescendent = novaLlistaDescendent;
    }

    public void afegirAlumne() throws ArbreException {
        System.out.print("Introdueix el nom de l'alumne: ");
        String nom = scanner.nextLine();
        Alumnes_SEC nouAlumne = new Alumnes_SEC(nom);
        boolean estat = true;

        while (estat) {
            try {
                System.out.print("Introdueix el nom de l'assignatura o 'fi' per acabar: ");
                String nomAssignatura = scanner.nextLine();
                if (nomAssignatura.equals("fi")) {
                    break;
                }
                System.out.print("Introdueix el nombre de crèdits: ");
                int credits = Integer.parseInt(scanner.nextLine());
                System.out.print("És obligatòria (true/false): ");
                boolean mHonor = Boolean.parseBoolean(scanner.nextLine());
                System.out.print("Introdueix la nota: ");
                double nota = Double.parseDouble(scanner.nextLine());

                Assignatura assignatura = new Assignatura(nomAssignatura, credits, mHonor, nota);
                nouAlumne.addAssignatura(assignatura);
            } catch (NumberFormatException e) {
                System.err.println("Error en el format dels valors introduïts. Torna a intentar-ho.");
                return; // Tornar al menú principal
            }
        }

        this.arbreACB.inserir(nouAlumne);
        this.llistaDescendent = this.arbreACB.getDescendentList();
    }
}