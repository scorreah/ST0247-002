import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;
//import jdk.internal.net.http.common.MinimalFuture;
/**
 * Clase en la cual se implementan los metodos del Taller 4
 * 
 * @author Simon Correa, David Gomez, Mauricio Toro, Andres Paez
 */
public class Taller4 {
    /**
     * Metodo auxiliar para llamar el metodo hayCaminoDFS posterior
     * @param g grafo dado 
     * @param v vertices 
     * @param w vertice
     * @return true si hay camino, false de lo contrario
     */
    public static boolean hayCaminoDFS(Digraph g, int v, int w) {
        boolean[] visitados = new boolean[g.size()];
        return hayCaminoDFS(g, v, w, visitados);
    }

    /**
     * Metodo que recorre el grafo por medio de dfs 
     * @param g grafo dado 
     * @param v vertices 
     * @param w vertice
     * @param visitados ayuda a tener un conteo acerca de que nodos han sido
     * o no visitados
     * @return true si hay camino, false de lo contrario
     */
    private static boolean hayCaminoDFS(Digraph g, int v, int w, boolean[] visitados) {
        visitados[v] = true;
        if(v == w)return true;
        ArrayList<Integer> hola = g.getSuccessors(v);
        if(hola == null) return false;
        if(hola.contains(w))return true;
        for(int i = 0; i < hola.size(); i++){
            if(!(visitados[hola.get(i)] == true)){ //Si es un sucesor y no lo ha visitado
                if(hayCaminoDFS(g, hola.get(i), w, visitados))return true;
            }
        }
        return false;
    }
    
    /**
     * Metodo que recorre el grafo por medio de dfs teniendo en cuenta que
     * se quiere encontrar el de menor costo
     * @param g grafo dado 
     * @param inicio nodo desde el cual empieza el recorrido 
     * @param fin nodo donde termina el recorrido
     * @return cual es el costo que tiene ir desde inicio a fin
     */
    public static int costoMinimo(Digraph g, int inicio, int fin) {
        boolean[] visitados = new boolean[g.size()];
        int suma = 0;
        Arrays.fill(visitados, false);
        if(!(hayCaminoDFS(g, inicio, fin, visitados)))return -1;
        Arrays.fill(visitados, false);
        //Stack<Integer> visit = new Stack<Integer>(); 
        return minimoCaminoDFS(g, inicio, fin, visitados);
    }
    
    private static int minimoCaminoDFS(Digraph g, int v, int w, boolean[] visitados) {
        int min = Integer.MAX_VALUE;
        if(v == w)return 0;
        visitados[v] = true;
        ArrayList<Integer> hola = g.getSuccessors(v);
        if (hola == null) return Integer.MAX_VALUE;
        for(int i = 0; i < hola.size(); i++){
            if(!(visitados[hola.get(i)] == true)){ 
                int valorMinimo = minimoCaminoDFS(g, hola.get(i), w, visitados);
                if (valorMinimo == Integer.MAX_VALUE) continue;
                int c = g.getWeight(v, hola.get(i)) + valorMinimo;
                min = Math.min(min, c);
                visitados[v] = false;
            }
        }
        return min;
    }
    
    public static void main(String args[]){
        Digraph g = new DigraphAM(8);
        g.addArc(0, 1, 20);
        g.addArc(0, 6, 90);
        g.addArc(0, 3, 80);
        g.addArc(1, 5, 10);
        g.addArc(2, 5, 50);
        g.addArc(2, 7, 20);
        g.addArc(2, 3, 10);
        g.addArc(3, 6, 20);
        g.addArc(4, 6, 30);
        g.addArc(5, 2, 10);
        g.addArc(5, 3, 40);
        g.addArc(6, 0, 20);
        System.out.println(costoMinimo(g, 0, 6));
        
        
    }
}
/*
/**
 * Metodo auxiliar que llama al metodo recorrido posterior
 * con cada uno de los vertices
 * @param g grafo dado 
 * @return cual es el costo que tiene
 */ /*
public static int caminoHamiltoniano(Digraph g) {

}
/**
 * Metodo que recorre todo el grafo con la intencion de buscar un
 * camino que represente el menor costo pasando por todos los vertices exactamente
 * una vez y vuelva al nodo inicial
 * @param g grafo dado 
 * @param v vertice inicial
 * @param unvisited arreglo de nodos aun no visitados
 * @return cual es el costo que tiene
 */ /*
private static int caminoHamiltoniano(Digraph g, int v, int[] unvisited) {

}

}
 */