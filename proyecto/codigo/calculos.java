/**
 * Write a description of class C here.
 * 
 * @author David Gomez, Simon Correa
 * @version 28/03/2021
 */
import java.util.ArrayList;
import java.util.Random;
public class calculos{
    double[][] ahorro;
    double[][] ahorros;
    int ultimo;
    int contador;
    int[] limpiados;
    int metodo;
    Aleatorio random;
    public static ArrayList<ArrayList<Integer>> rutas = new ArrayList<ArrayList<Integer>>();
    
    public calculos() {
        rutas.clear();
        this.metodo = 1;
    }

    public calculos(int metodo) {
        rutas.clear();
        this.metodo = (metodo != 1 && metodo != 2)? 1: metodo;
        this.random = new Aleatorio();
    }

    public calculos(long seed) {
        rutas.clear();
        //this.metodo = (metodo != 1 && metodo != 2)? 2: metodo;
        this.metodo = 2;
        this.random = new Aleatorio(seed);
    }

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
        ahorros = new double[ahorro.length][];
        for(int i = 0; i < ahorro.length; i++)
            ahorros[i] = ahorro[i].clone();
        double mayor = 0;
        MejorClark mejor1, mejor2, mejor3;
        mejor1 = new MejorClark();
        mejor2 = new MejorClark();
        mejor3 = new MejorClark();
        int x = 0;      //Cliente 1
        int y = 0;      //Cliente 2
        for(int i = 1; i < RuteoVehiculosElectricos.m +1; i++){
            for(int j = i; j < RuteoVehiculosElectricos.m +1; j++){
                if(metodo == 1) {
                    if(mayor < ahorros[i][j]){ //Encuentra el mayor ahorro
                        mayor = ahorros[i][j];
                        x = i;
                        y = j;
                    }
                } else if (metodo == 2) {   //Busqueda Local
                    if(mejor1.getAhorro() < ahorros[i][j]){ //Encuentra el mayor ahorro
                        // 3er mejor ahorro
                        mejor3.setAhorro(mejor2.getAhorro());
                        mejor3.setX(mejor2.getX());
                        mejor3.setY(mejor2.getY());
                        // 2ndo mejor ahorro
                        mejor2.setAhorro(mejor1.getAhorro());
                        mejor2.setX(mejor1.getX());
                        mejor2.setY(mejor1.getY());
                        // 1er mejor ahorro
                        mejor1.setAhorro(ahorros[i][j]);
                        mejor1.setX(i);
                        mejor1.setY(j);
                        if (contador > 3) {
                            switch (random.next()) {
                                case 1:
                                    mayor = mejor1.getAhorro();
                                    x = mejor1.getX();
                                    y = mejor1.getY();
                                    break;
                                case 2:
                                    mayor = mejor2.getAhorro();
                                    x = mejor2.getX();
                                    y = mejor2.getY();
                                    break;
                                case 3: 
                                    mayor = mejor3.getAhorro();
                                    x = mejor3.getX();
                                    y = mejor3.getY();
                                    break;                        
                                default:
                                    break;
                            }
                        } else {
                            mayor = ahorros[i][j];
                            x = i;
                            y = j;
                        }
                    }
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
            registro = DemoraCamino(registro, x, y); //Agrega el cliente 1 o cliente 2 a la ruta
            if(!PodemosAgregarlo(registro, mapa)){      // Si no se puede agregar, quita el ultimo agregado
                if(ultimo == 1)registro.remove(0);
                if(ultimo == 2)registro.remove(registro.size()-1);
                ArrayList<Integer> registro1 = Electricidad.confirmacionBateria(registro, mapa,16000);
                rutas.add(registro1);
                limpiar1(registro1, RuteoVehiculosElectricos.m +1);
                //registro.clear();
                limpiarResto1(registro1, RuteoVehiculosElectricos.m +1);      //Limpia los bordes
                break;
            }
            limpiar(registro, RuteoVehiculosElectricos.m +1);       //Limpia en medio
            ahorros[x][y]=0;
            ahorros[y][x]=0;
            mayor= 0;
            if(contador - registro.size()==0){      //Condición de parada
                rutas.add(registro);
                break;
            }
            x = registro.get(0);
            y= registro.get(registro.size()-1);
            int posx, posy;
            posx = posy = 0;
            for(int i = 1; i<RuteoVehiculosElectricos.m +1; i++){       //Elige el siguiente
                if(limpiados[i] == 1){      //Si ya lo utilizó, continue
                    continue;
                }
                double temp = 0;
                int pos = 0;
                if(ahorro[x][i]>ahorro[y][i]){      //Elige el mejor ahorro, que esté conectado a los bordes
                    temp = ahorro[x][i];
                    pos = x;
                }else{
                    temp = ahorro[y][i];
                    pos = y;
                }
                if (i == x || i == y) continue;
                if(mayor < temp){
                    mayor = temp;       //Mayor ahorro
                    posx = i;
                    posy = pos;
                }    
            }
            x = posx;
            y = posy;
        }
        contador = contador - (rutas.get(rutas.size()-1)).size();   // Toma el tamaño de la ultima ruta, y se lo resta al contador
        calculadorRuta(mapa);       //Una vez calculada una ruta, calcula la siguiente
    }

    public void limpiar (ArrayList<Integer> registro, int tam) {        //Limpia los del medio
        if (registro.size() <= 2) return;
        for (int i = 1; i <= registro.size()-2; i++) {
            if(limpiados[registro.get(i)] == 1)continue;

            limpiados[registro.get(i)] = 1;
            for (int j = 0; j < tam; j++) {
                ahorros[j][registro.get(i)] = 0;
                ahorros[registro.get(i)][j] = 0;
            }
        }
    }

    public void limpiar1 (ArrayList<Integer> registro, int tam) {        //Limpia los del medio
        if (registro.size() <= 2) return;
        for (int i = 1; i <= registro.size()-2; i++) {
            if(registro.get(i) >= tam) continue;
            if(limpiados[registro.get(i)] == 1)continue;

            limpiados[registro.get(i)] = 1;
            for (int j = 0; j < tam; j++) {
                ahorro[j][registro.get(i)] = 0;
                ahorro[registro.get(i)][j] = 0;
            }
        }
    }

    public void limpiarResto (ArrayList<Integer> registro, int tam) {       // Limpia los bordes de la ruta en la 
        for (int j = 0; j < tam; j++) {
            ahorros[j][registro.get(0)] = 0;
            ahorros[registro.get(0)][j] = 0;
            ahorros[j][registro.get(registro.size()-1)] = 0;
            ahorros[registro.get(registro.size()-1)][j] = 0;
        }
        limpiados[registro.get(0)] = 1;
        limpiados[registro.get(registro.size()-1)] = 1;
    }

    public void limpiarResto1 (ArrayList<Integer> registro, int tam) {       // Limpia los bordes de la ruta en la 
        for (int j = 0; j < tam; j++) {
            if(registro.get(0) < tam) {
                ahorros[j][registro.get(0)] = 0;
                ahorros[registro.get(0)][j] = 0;
            }
            ahorros[j][registro.get(registro.size()-1)] = 0;
            ahorros[registro.get(registro.size()-1)][j] = 0;
        }
        if(registro.get(0) < tam) 
            limpiados[registro.get(0)] = 1;
        limpiados[registro.get(registro.size()-1)] = 1;
    }

    public boolean PodemosAgregarlo(ArrayList<Integer> registro, Digraph mapa){     //Comprueba que cumpla con la restricción de tiempo
        double tiempoMax = 10;
        double tiempoVisita = RuteoVehiculosElectricos.st_customer*registro.size();  //Lo que se demora con cada cliente
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