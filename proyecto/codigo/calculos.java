import java.util.ArrayList;
public class calculos{
    double[][] ahorro;
    int ultimo;
    int contador;
    ArrayList<ArrayList<Integer>> rutas;

    public void ahorros(Digraph mapa, int size){
        contador = size;
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
            }
        }
    }

    public void calculadorRuta(Digraph mapa){
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

        if(contador == 0)return;

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
                registro.clear();
                break;
            }

            ahorro[x][y]=0;
            ahorro[y][x]=0;
            
            mayor= 0;
            x = registro.get(0);
            y= registro.get(registro.size()-1);

            for(int i = 0; i<mapa.size; i++){
                double temp = 0;
                int pos = 0;

                if(ahorro[x][i]>ahorro[y][i]){
                    temp = ahorro[x][i];
                    pos = x;
                }else{
                    temp = ahorro[y][i];
                    pos = y;
                }

                if(mayor < temp){
                    mayor = temp;
                    x = i;
                    y = pos;
                }    
            }
        }
        contador = contador - (rutas.get(rutas.size()-1)).size();
        calculadorRuta(mapa);
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
        if(registro.isEmpty()){
            registro.add(x);
            registro.add(y);
            return registro;
        }
        
        int tam = registro.size();

        if(x==registro.get(0)){
            registro.add(0, y);
            ultimo = 1;
        }else if(y==registro.get(0)){
            registro.add(0, x);
            ultimo = 1;
        }else if(registro.get(tam-1)==x){
            registro.add(y);
            ultimo = 2;
        }else  if(registro.get(tam-1)==y){
            registro.add(x);
            ultimo = 2;
        }
        return registro;

    }
}