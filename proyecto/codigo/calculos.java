/**
 * Write a description of class C here.
 * 
 * @author David Gomez, Simon Correa
 * @version 28/03/2021
 */
import java.util.ArrayList;
public class calculos{
    double[][] ahorro;
    int ultimo;
    int contador;
    int[] limpiados;
    public static ArrayList<ArrayList<Integer>> rutas = new ArrayList<ArrayList<Integer>>();
    public void ahorros(Digraph mapa, int size){
        contador = size-1;
        limpiados = new int[size];
        limpiados[0] = 1;
        ahorro = new double[size][size];
        for(int i = 1; i<size; i++){
            double primero = mapa.getWeight(0,i)*2;
            ahorro[0][i]= primero;
        }
        for(int i = 1; i<size;i++){
            for(int j = 1; j<size; j++){
                if(i==j){
                    ahorro[i][j]=0;
                    continue;
                }
                double suma = ahorro[0][i]+ahorro[0][j];
                double demora = mapa.getWeight(0,i) +mapa.getWeight(0,j)+mapa.getWeight(i, j);
                ahorro[i][j] = suma-demora;
                ahorro[j][i] = suma-demora;
            }
        }
        for(int i = 1; i<size; i++){
            ahorro[0][i]= 0;
            ahorro[i][0]= 0;
        }
    }
    public void calculadorRuta(Digraph mapa){
        if(contador <= 0)return;
        ArrayList<Integer> registro = new ArrayList<Integer>();
        double mayor = 0;
        int x = 0;
        int y = 0;
        for(int i = 1; i < mapa.size; i++){
            for(int j = i; j < mapa.size; j++){
                if(mayor < ahorro[i][j]){
                    mayor = ahorro[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        if(mayor == 0) return;
        while(true){  
            /*
            if(registro.contains(x)){
            for(int i= 0; i<mapa.size; i++){
            ahorro[x][i]=0;
            ahorro[i][x]= 0;
            }
            }
            if(registro.contains(y)){
            for(int i= 0; i<mapa.size; i++){
            ahorro[y][i]=0;
            ahorro[i][y]= 0;
            }
            }
             */
            registro = DemoraCamino(registro, x, y);
            if(!PodemosAgregarlo(registro, mapa)){
                if(ultimo == 1)registro.remove(0);
                if(ultimo == 2)registro.remove(registro.size()-1);
                rutas.add(registro);
                //registro.clear();
                limpiarResto(registro, mapa.size);
                break;
            }
            limpiar(registro, mapa.size);
            ahorro[x][y]=0;
            ahorro[y][x]=0;
            mayor= 0;
            if(contador - registro.size()==0){
                rutas.add(registro);
                break;
            }
            x = registro.get(0);
            y= registro.get(registro.size()-1);
            int posx, posy;
            posx = posy = 0;
            for(int i = 1; i<mapa.size; i++){
                if(limpiados[i] == 1){
                    continue;
                }
                double temp = 0;
                int pos = 0;
                if(ahorro[x][i]>ahorro[y][i]){
                    temp = ahorro[x][i];
                    pos = x;
                }else{
                    temp = ahorro[y][i];
                    pos = y;
                }
                if (i == x || i == y) continue;
                if(mayor < temp){
                    mayor = temp;
                    posx = i;
                    posy = pos;
                }    
            }
            x = posx;
            y = posy;
        }
        contador = contador - (rutas.get(rutas.size()-1)).size();
        calculadorRuta(mapa);
    }
    public void limpiar (ArrayList<Integer> registro, int tam) {
        if (registro.size() <= 2) return;
        for (int i = 1; i <= registro.size()-2; i++) {
            if(limpiados[registro.get(i)] == 1)continue;
            limpiados[registro.get(i)] = 1;
            for (int j = 0; j < tam; j++) {
                ahorro[j][registro.get(i)] = 0;
                ahorro[registro.get(i)][j] = 0;
            }
        }
    }
    public void limpiarResto (ArrayList<Integer> registro, int tam) {
        for (int j = 0; j < tam; j++) {
            ahorro[j][registro.get(0)] = 0;
            ahorro[registro.get(0)][j] = 0;
            ahorro[j][registro.get(registro.size()-1)] = 0;
            ahorro[registro.get(registro.size()-1)][j] = 0;
        }
        limpiados[registro.get(0)] = 1;
        limpiados[registro.get(registro.size()-1)] = 1;
    }
    public boolean PodemosAgregarlo(ArrayList<Integer> registro, Digraph mapa){
        double tiempoMax = 10;
        double tiempoVisita = RuteoVehiculosElectricos.st_customer*registro.size();
        for(int i = 0; i<registro.size(); i++){
            if(i == registro.size()-1){
                tiempoMax = tiempoMax-(mapa.getWeight(0,registro.get(i)));
                break;
            }
            if(i == 0){
                tiempoMax = tiempoMax-(mapa.getWeight(0,registro.get(i)));
            }
            tiempoMax = tiempoMax-(mapa.getWeight(registro.get(i), registro.get(i+1)));
        }
        return (tiempoMax-tiempoVisita)>=0; 
    }
    public ArrayList<Integer> DemoraCamino(ArrayList<Integer> registro, int x, int y){
        ArrayList<Integer> registro1 = registro;
        if(registro1.isEmpty()){
            registro1.add(x);
            registro1.add(y);
            return registro1;
        }
        int tam = registro1.size();
        if(x==registro1.get(0)){
            registro1.add(0, y);
            ultimo = 1;
        }else if(y==registro1.get(0)){
            registro1.add(0, x);
            ultimo = 1;
        }else if(registro1.get(tam-1)==x){
            registro1.add(y);
            ultimo = 2;
        }else  if(registro1.get(tam-1)==y){
            registro1.add(x);
            ultimo = 2;
        }
        return registro1;
    }
    public static ArrayList<ArrayList<Integer>> obtenerRuta() {
        ArrayList<ArrayList<Integer>> solucion = rutas;
        rutas.clear();
        return solucion;
    }
}