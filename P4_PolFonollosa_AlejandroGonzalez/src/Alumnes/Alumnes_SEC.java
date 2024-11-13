package Alumnes;

public class Alumnes_SEC implements Comparable<Alumnes_SEC> {

    private class Node {
        private Assignatura info;
        private Node next;

        public Node(Assignatura info) {
            this.info = info;
            this.next = null;
        }

        public Node(String nom){
            this.info = new Assignatura(nom);
            this.next = null;
        }
    }

    private Node cap;

    public Alumnes_SEC(String nom) {
        this.cap = new Node(nom);
    }

    public void addAssignatura(Assignatura nova) {
        Node aux = this.cap;
        while (aux.next != null) {
            if (aux.next.info.equals(nova)) {
                aux.next.info = nova;
                actualitzarMitja();
                return;
            }
            aux = aux.next;
        }
        aux.next = new Node(nova);
        actualitzarMitja();
    }

    private void actualitzarMitja() {
        Node aux = this.cap.next;
        int pXnca = 0;
        int credits = 0;
        while (aux != null) {
            credits += aux.info.getCredits();
            pXnca += aux.info.getPunts() * aux.info.getCredits();
            aux = aux.next;
        }
        if (credits > 0) {
            this.cap.info.setNota((double) pXnca / credits);
        } else {
            this.cap.info.setNota(0.0);
        }
    }

    public boolean hiHa(int punts) {
        Node aux = this.cap.next;
        while (aux != null) {
            if (aux.info.getPunts() == punts) {
                return true;
            }
            aux = aux.next;
        }
        return false;
    }

    @Override
    public int compareTo(Alumnes_SEC other) {
        if (this.cap.info.getNota() < other.cap.info.getNota()) {
            return -1;
        } else if (this.cap.info.getNota() > other.cap.info.getNota()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alumne: ").append(this.cap.info.toString()).append("\n");
        Node aux = this.cap.next;
        while (aux != null) {
            sb.append(aux.info.toString()).append("\n");
            aux = aux.next;
        }
        return sb.toString();
    }
}