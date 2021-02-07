 

import java.util.ArrayList;
//CODIGO BASE DADO POR EL PROFESOR
public abstract class Graph1
{
    public int size;

    public Graph1(int vertices)
    {
        size = vertices;
    }
    public  abstract void addArc(int source, int destination, Ubicacion calle);
    public abstract ArrayList getSuccessors(int vertice);
    public abstract Ubicacion getWeight(int source, int destination);
    public  int size() {
        return size;
    }
}
