package EstructuraArbre;

import java.util.LinkedList;
import java.util.Queue;

public class AcbEnll<E extends Comparable<E>> implements Acb<E> {

    private class NodeA implements Cloneable {
        private E inf;
        private NodeA left;
        private NodeA right;

        public NodeA(NodeA esq, NodeA dre, E inf){
            this.inf = inf;
            this.left = esq;
            this.right = dre;
        }

        public NodeA(E inf){
            this(null, null, inf);
        }

        public void inserirRecursive(Comparable<E> element){

            if(element.compareTo((E) this.inf) > 0){
                if(this.right == null){
                    this.right = new NodeA((E) element);
                }
                else {
                    this.right.inserirRecursive(element);
                }
            }
            else {
                if(this.left == null){
                    this.left = new NodeA((E) element);
                }
                else {
                    this.left.inserirRecursive(element);
                }
            }

        }

        public boolean membreRecursive(Comparable<E> element) {
            if (this.inf == null) {
                return false;
            }
            if (this.inf.equals(element)) {
                return true;
            } else if (element.compareTo(this.inf) < 0) {
                return this.left != null && this.left.membreRecursive(element);
            } else {
                return this.right != null && this.right.membreRecursive(element);
            }
        }

        private Queue<E> omplirInordre(Queue<E> list) {
            if (this.left != null) {
                this.left.omplirInordre(list);
            }

            list.add(this.inf);

            if (this.right != null) {
                this.right.omplirInordre(list);
            }

            return list;
        }

        private Queue<E> omplirReverseInordre(Queue<E> list) {
            if (this.right != null) {
                this.right.omplirReverseInordre(list);
            }

            list.add(this.inf);

            if (this.left != null) {
                this.left.omplirReverseInordre(list);
            }

            return list;
        }

        private int cardinalitatRecursive() {
            if (this.inf == null) {
                return 0;
            }
            int leftCount = 0;
            if (this.left != null) {
                leftCount = this.left.cardinalitatRecursive();
            }
            int rightCount = 0;
            if (this.right != null) {
                rightCount = this.right.cardinalitatRecursive();
            }
            return 1 + leftCount + rightCount;
        }

        private NodeA esborrarRecursive(E element) throws ArbreException {
            int cmp = element.compareTo(this.inf);
            if (cmp == 0) {
                // Case 1: No children
                if (this.left == null && this.right == null) {
                    return null;
                }
                // Case 2: Only right child
                if (this.left == null) {
                    return this.right;
                }
                // Case 3: Only left child
                if (this.right == null) {
                    return this.left;
                }
                // Case 4: Both children
                this.inf = esborrarMinim(this.right);
                this.right = this.right.esborrarRecursive(this.inf);
            } else if (cmp < 0) {
                if (this.left != null) {
                    this.left = this.left.esborrarRecursive(element);
                } else {
                    throw new ArbreException("Element not found");
                }
            } else {
                if (this.right != null) {
                    this.right = this.right.esborrarRecursive(element);
                } else {
                    throw new ArbreException("Element not found");
                }
            }
            return this;
        }

        private E esborrarMinim(NodeA node) {
            NodeA parent = null;
            while (node.left != null) {
                parent = node;
                node = node.left;
            }
            if (parent != null) {
                parent.left = node.right;
            }
            return node.inf;
        }



        @Override
        public NodeA clone() throws CloneNotSupportedException {
            NodeA leftClone = null;
            if (this.left != null) {
                leftClone = this.left.clone();
            }

            NodeA rightClone = null;
            if (this.right != null) {
                rightClone = this.right.clone();
            }

            return new NodeA(leftClone, rightClone, this.inf);
        }
    }

    private NodeA arrel;

    public AcbEnll(NodeA arrel){
        this.arrel = arrel;
    }

    public AcbEnll(){
        this(null);
    }

    @Override
    public void inserir(Comparable element) throws ArbreException {

        if(this.membre(element)){
            throw new ArbreException("L'element ja existeix");
        }
        if(this.arrel == null){
            this.arrel = new NodeA((E) element);
        }
        else {
            this.arrel.inserirRecursive(element);
        }

    }

    @Override
    public void esborrar(E element) throws ArbreException {
        if (this.arrel == null) {
            throw new ArbreException("L'arbre està buit");
        }
        this.arrel = this.arrel.esborrarRecursive(element);
    }

    @Override
    public boolean membre(Comparable element) {
        if(this.arrel == null || element == null){
            return false;
        }
        return this.arrel.membreRecursive(element);
    }

    @Override
    public E arrel() throws ArbreException {
        if(this.arrel == null){
            throw new ArbreException("Arbre buit");
        }
        return this.arrel.inf;
    }

    @Override
    public Acb fillEsquerre() throws CloneNotSupportedException {
        if(this.arrel == null || this.arrel.left == null){
            return new AcbEnll();
        }
        try{
            return (Acb) this.arrel.left.clone();
        }
        catch (CloneNotSupportedException e){
            throw new CloneNotSupportedException("Error al clonar l'arbre esquerre");
        }
    }

    @Override
    public AcbEnll<E> fillDret() throws CloneNotSupportedException {
        if (this.arrel == null || this.arrel.right == null) {
            return new AcbEnll<>();
        }
        try {
            return new AcbEnll<>(this.arrel.right.clone());
        } catch (CloneNotSupportedException e) {
            throw new CloneNotSupportedException("Error al clonar l'arbre dret");
        }
    }

    @Override
    public boolean arbreBuit() {
        return this.arrel == null;
    }

    @Override
    public void buidar() {
        this.arrel = null;
    }

    public Queue<E> getAscendentList(){
        Queue<E> list = new LinkedList<>();
        if(this.arrel == null){
            return null;
        }
        return this.arrel.omplirInordre(list);

    }

    public Queue<E> getDescendentList(){
        Queue<E> list = new LinkedList<>();
        if(this.arrel == null){
            return null;
        }
        return this.arrel.omplirReverseInordre(list);
    }

    public int cardinalitat(){
        if(this.arrel == null){
            return 0;
        }
        return this.arrel.cardinalitatRecursive();
    }
}
