import java.util.ArrayList;
/**
 * Clase en la cual se implementan los metodos del Taller 4
 * 
 * @author David Gómez, Simón Correa, Mauricio Toro, Andres Paez
 */
public class Minimo {
    /**
     * Metodo auxiliar que llama al metodo recorrido posterior
     * con cada uno de los vertices
     * @param g grafo dado 
     * @return cual es el costo que tiene
     */
    public static double caminoHamiltoniano(Digraph g) {
        boolean[] unvisited = new boolean[g.size()];
        return caminoHamiltoniano(g, 0, unvisited,0);
    }

    private static double caminoHamiltoniano(Digraph g, int v, boolean[] unvisited, double max) {
        double minimo = Double.MAX_VALUE;
        if(ok(unvisited)&& v == 0)return max;
        if(ok(unvisited)&& v != 0)return Double.MAX_VALUE;
        unvisited[v]=true;
        ArrayList<Integer> sucesores = g.getSuccessors(v);
        if (ok(unvisited)){
            for(int i = 0; i < sucesores.size(); i++){
                double suma = max +  g.getWeight(v, sucesores.get(i)); 
                minimo = Math.min(minimo, caminoHamiltoniano(g, sucesores.get(i), unvisited, suma));
    
            }
            return minimo;
        }
        
        for(int i = 0; i < sucesores.size(); i++){
            if(unvisited[sucesores.get(i)] == true){
                continue;
            }else{
                double suma = max +  g.getWeight(v, sucesores.get(i)); 
                minimo = Math.min(minimo, caminoHamiltoniano(g, sucesores.get(i), unvisited, suma));
                unvisited[sucesores.get(i)] = false; //Para que lo desvisite
            }
        }
        return minimo;
    }
    /**
     * Metodo para saber si ya se visitaron todos los nodos
     */
    private static boolean ok(boolean[] szs){ 
        for(int i = 0; i <szs.length; i++){
            if(!szs[i])return false;
        }
        return true;
    }

    public static void main(String args[]){
        Digraph g = new DigraphAM(4);
        g.addArc(0, 1, 7);
        g.addArc(0, 3, 6);
        g.addArc(0, 2, 15);
        g.addArc(1, 0, 2);
        g.addArc(1, 2, 7);
        g.addArc(1, 3, 3);
        g.addArc(3, 0, 10);
        g.addArc(3, 2, 8);
        g.addArc(3, 1, 4);
        g.addArc(2, 0, 9);
        g.addArc(2, 1, 6);
        g.addArc(2, 3, 12);
        System.out.println(caminoHamiltoniano(g));
    }
}
/*
private static boolean listoRecorrido(boolean[] unvisited) {
for(int i = 0; i < unvisited.length; i++) {
if(!unvisited[i]) return false;
}
return true;
} */

