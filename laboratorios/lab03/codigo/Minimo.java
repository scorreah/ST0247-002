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
    public static double caminoHamiltoniano(Digraph g, int i, int f) {
        boolean[] unvisited = new boolean[g.size()];
        return caminoHamiltoniano(g, i, f, unvisited, 0, Double.MAX_VALUE);
    }

    private static double caminoHamiltoniano(Digraph g, int v, int f, boolean[] unvisited, double max, double maximo) {
        if(max>=maximo)return Double.MAX_VALUE;
        if(v == f)return max;
        unvisited[v]=true;
        ArrayList<Integer> sucesores = g.getSuccessors(v);
        for(int i = 0; i < sucesores.size(); i++){
            if(unvisited[sucesores.get(i)] == true){
                continue;
            }else{
                double suma = max +  g.getWeight(v, sucesores.get(i)); 
                maximo = Math.min(maximo, caminoHamiltoniano(g, sucesores.get(i), f, unvisited, suma, maximo));
                unvisited[sucesores.get(i)] = false; //Para que lo desvisite
            }
        }
        return maximo;
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
        System.out.println(caminoHamiltoniano(g, 3, 0));
        DigraphAM g1 = new DigraphAM(5);
        g1.addArc(0, 1, 2);
        g1.addArc(0, 2, 2);
        g1.addArc(0, 3, 1);
        g1.addArc(0, 4, 4);
        g1.addArc(1, 0, 2);
        g1.addArc(1, 2, 3);
        g1.addArc(1, 3, 2);
        g1.addArc(1, 4, 3);
        g1.addArc(2, 0, 2);
        g1.addArc(2, 1, 3);
        g1.addArc(2, 3, 2);
        g1.addArc(2, 4, 2);
        g1.addArc(3, 0, 1);
        g1.addArc(3, 1, 2);
        g1.addArc(3, 2, 2);
        g1.addArc(3, 4, 4);
        g1.addArc(4, 0, 4);
        g1.addArc(4, 1, 3);
        g1.addArc(4, 2, 2);
        g1.addArc(4, 3, 4);
        if (caminoHamiltoniano(g1, 0, 0) != 0){
            System.out.println("Malo");		    
        }else{
            System.out.println("Bueno");	
        }
    }
}
/*
private static boolean listoRecorrido(boolean[] unvisited) {
for(int i = 0; i < unvisited.length; i++) {
if(!unvisited[i]) return false;
}
return true;
} */