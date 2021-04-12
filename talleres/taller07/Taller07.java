
import javafx.util.Pair;
import java.util.ArrayList;
class Taller07{
    // determina el camino mas corto desde inicio a todos los demas
    public static Pair<int[], int[]> dijkstra(Digraph g,int inicio ){
        int[] dist = new int[g.size()];     //distancias
        int[] pred = new int[g.size()];     //predecesores
        boolean[] visitados = new boolean[g.size()];    //visitados
        for(int i = 0; i<dist.length; i++){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[inicio] = 0;           //visita el nodo inicial
        for(int i = 0; i<dist.length; i++){     //Repite el algoritmo el número de nodos que haya
            int next = minVertex(dist, visitados);      //Elige el siguiente vertice
            if(next == -1)break;
            visitados[next] = true;
            ArrayList<Integer> vecinos = g.getSuccessors(next);
            if(vecinos == null)continue;
            for(int j = 0; j<vecinos.size(); j++){
                int vecino = vecinos.get(j);
                int distancia = dist[next] + g.getWeight(next, vecino);     //Distancia desde el nodo elegido a uno de los sucesores
                if(distancia<dist[vecino]){         //Si la distancia es menor a la distancia guardada previamente, se guarda
                    dist[vecino] = distancia;
                    pred[vecino] = next;
                }
            }
        }
        return new Pair<>(dist, pred);
    }
    public static int minVertex(int[] dist, boolean[] v){
        int x = Integer.MAX_VALUE;          //distancia guardada
        int y = -1;                         //nodo
        for(int i = 0; i<dist.length; i++){
            if(!v[i] && dist[i]<x){         //Si no está en visitados y es la menor distancia
                y = i;
                x = dist[i];
            }
        }
        return y;
    }
    // encuentra el costo total del subconjunto de aristas de costo m�nimo que conservan el grafo conectado
    public static int prim(Digraph g, int inicio ){
        boolean[] visitados = new boolean[g.size()];
        int[] visit = new int[g.size()];    //Se ponen los visitados en el orden de visita
        int sum = 0;
        visit[0] = inicio;
        visitados[0] = true;
        return primAux(g, visitados, 1, sum, visit);
    }
    public static int primAux(Digraph g, boolean[] v, int num, int sum, int[] vs){
        if(num == g.size()) return sum;
        int pos = -1;
        int menor = Integer.MAX_VALUE;
        for(int i = 0; i<num; i++){
            ArrayList<Integer> vecinos = g.getSuccessors(vs[i]);
            if(vecinos == null)continue;
            for(int j = 0; j<vecinos.size(); j++){
                if(!v[vecinos.get(j)] && menor>g.getWeight(vs[i], vecinos.get(j))){
                    menor = g.getWeight(vs[i], vecinos.get(j));
                    pos = vecinos.get(j);
                }
            }
        }
        if(pos==-1)return sum;
        sum = sum + menor;
        v[pos] = true;
        vs[num] = pos;
        return primAux(g, v, num+1, sum, vs);        
    }
    
}