
/**
 * Write a description of class Estructura here.
 * 
 * @author David Gomez, Simon Correa 
 * @version 07/02/2021
 */
import java.util.ArrayList;
import java.util.Arrays; 


public class Estructura extends Graph1 { 

    //Grafo con matriz
    private Ubicacion[][] mat;
 
    public Estructura(int size){
        super(size);
        mat = new Ubicacion[size+1][size+1];

        /*for (int i = 1; i <= size; ++i) {
            mat[i][0] = i;
            mat[0][i] = i;
        }*/

    }

    public void addArc(int source, int destination, Ubicacion calle){
        if (!(source == 0 && destination == 0)) {
            mat[source][destination] = calle;
        }
    }

    public Ubicacion getWeight(int source, int destination){
        return mat[source][destination];
    }

    public ArrayList<Integer> getSuccessors(int vertex) 
    { 
        ArrayList<Integer> np= new ArrayList<>();
        for (int i = 1; i < mat.length; i++) {
            if (mat[vertex][i] != null) {
                np.add(i);
            }
        }
        return np;
    }
} 
