import java.util.ArrayList;
/**
 * Write a description of class Electricidad here.
 * 
 * @author Simon Correa, David Gomez
 * @version 02/05/2021
 */
public class Electricidad
{
    // instance variables - replace the example below with your own
    private int x;
    /**
     * Constructor for objects of class Electricidad
     */
    public Electricidad()
    {
        // initialise instance variables
        x = 0;
    }

    public static int cargaCercana(ArrayList<Integer> registro, Digraph mapa, int ultimo){
        int clienteAnt = registro.get(ultimo);
        int estacion = 0;
        double menor = Double.MAX_VALUE;
        for(int i = RuteoVehiculosElectricos.m+1; i< RuteoVehiculosElectricos.n;i++){
            if(menor>=mapa.getWeight(clienteAnt,i)){
                menor = mapa.getWeight(clienteAnt,i);
                estacion = i;
            }
        }
        return estacion;
    }

    public static Pair<Boolean, Double> bateria(ArrayList<Integer> registro, Digraph mapa, double bateria){
        double tiempoMax = bateria;
        double consumoHora = RuteoVehiculosElectricos.speed * RuteoVehiculosElectricos.r;
        for(int i = 0; i<registro.size(); i++){
            if(i == registro.size()-1 || i==0){
                double consumo = mapa.getWeight(0,registro.get(i))*consumoHora;
                tiempoMax = tiempoMax-consumo;
                break;
            }
            tiempoMax = tiempoMax-(mapa.getWeight(registro.get(i), registro.get(i+1))*consumoHora);
        }
        return new Pair<Boolean, Double>(tiempoMax>=0,tiempoMax);
    }

    public static ArrayList<Integer> confirmacionBateria(ArrayList<Integer> registro, Digraph mapa, double bateria){
        double bateriaMax = bateria;
        int dondeQuede  = 0;
        double tiempoMax = 10;
        ArrayList<Integer> registros = new ArrayList<Integer>();
        double consumoHora = RuteoVehiculosElectricos.speed * RuteoVehiculosElectricos.r;
        int cantEsta = 0;
        double tiempoCarga = 0;
        for(int i = 0; i<registro.size(); i++){
            if(i==0){
                double consumo = mapa.getWeight(0,registro.get(i))*consumoHora;
                if (bateriaMax - consumo < 5000) {
                    int estacion = Electricidad.cargaCercana(registro, mapa, i);
                    registros.add(estacion);
                    registros.add(registro.get(i));
                    consumo = mapa.getWeight(0, estacion)*consumoHora;
                    double demora = tiempoCarga(bateriaMax-consumo, estacion, 100, 16000);
                    consumo = mapa.getWeight(estacion, registro.get(i))*consumoHora;
                    bateriaMax = bateriaMax-consumo;
                    tiempoMax = tiempoMax-(mapa.getWeight(0,estacion))-demora-(mapa.getWeight(estacion, registro.get(1))); 
                    registros.add(estacion);
                    registros.add(registro.get(i));
                } else {
                bateriaMax = bateriaMax-consumo;
                tiempoMax = tiempoMax-(mapa.getWeight(0,registro.get(i))); 
                registros.add(registro.get(i));
                }
            } else if (i == registro.size()-1) {
                double consumo = mapa.getWeight(i, 0)*consumoHora;
                bateriaMax = bateriaMax-consumo;
                if (bateriaMax > 0) {
                    return registros;
                } else {
                    registros.remove(registro.size()-1);
                    return registros;
                }
            }
            if(tiempoMax<=8 && bateriaMax<=7000){
                int estacion = Electricidad.cargaCercana(registro, mapa, i);
                registros.add(estacion);
                registros.add(registro.get(i+1));
                double consumo = mapa.getWeight(registro.get(i), estacion)*consumoHora;
                double demora = tiempoCarga(bateriaMax-consumo, estacion, 100, 16000);  
                Pair<Boolean, Double> resultado = PodemosAgregarlo(registros, mapa, cantEsta+1, tiempoCarga+demora);
                if(resultado.first){
                    cantEsta = cantEsta+1;
                    tiempoCarga = tiempoCarga+demora;
                    tiempoMax = tiempoMax-(mapa.getWeight(registro.get(i), estacion))-demora;
                    tiempoMax = tiempoMax-(mapa.getWeight(estacion, registro.get(i+1)))-0.5;
                    bateriaMax = 16000-mapa.getWeight(estacion, registro.get(i+1))*consumoHora;
                }else{
                    registros.remove(registros.size()-1);
                    registros.remove(registros.size()-1);
                    registros.add(registro.get(i+1));
                    Pair<Boolean, Double> resultado2 = PodemosAgregarlo(registros, mapa, cantEsta, tiempoCarga);
                    bateriaMax = bateriaMax-(mapa.getWeight(registro.get(i), registro.get(i+1))*consumoHora)-(mapa.getWeight(registro.get(i+1), 0)*consumoHora);
                    if(resultado2.first && bateriaMax>=0){
                        dondeQuede = i;
                        return registros;
                    }else{
                        registros.remove(registros.size()-1);
                        dondeQuede = i;
                        return registros;
                    }
                }
            }else if(tiempoMax<=5|| bateriaMax<=6000){
                int estacion = Electricidad.cargaCercana(registro, mapa, i);
                registros.add(estacion);
                registros.add(registro.get(i+1));
                double consumo = mapa.getWeight(registro.get(i), estacion)*consumoHora;
                double demora = tiempoCarga(bateriaMax-consumo, estacion, 100, 16000);  
                Pair<Boolean, Double> resultado = PodemosAgregarlo(registros, mapa, cantEsta+1, tiempoCarga+demora);
                if(resultado.first){
                    cantEsta = cantEsta+1;
                    tiempoCarga = tiempoCarga+demora;
                    tiempoMax = tiempoMax-(mapa.getWeight(registro.get(i), estacion))-demora;
                    tiempoMax = tiempoMax-(mapa.getWeight(estacion, registro.get(i+1)))-0.5;
                    bateriaMax = 16000-mapa.getWeight(estacion, registro.get(i+1))*consumoHora;
                }else{
                    registros.remove(registros.size()-1);
                    registros.remove(registros.size()-1);
                    registros.add(registro.get(i+1));
                    Pair<Boolean, Double> resultado2 = PodemosAgregarlo(registros, mapa, cantEsta, tiempoCarga);
                    bateriaMax = bateriaMax-(mapa.getWeight(registro.get(i), registro.get(i+1))*consumoHora)-(mapa.getWeight(registro.get(i+1), 0)*consumoHora);
                    if(resultado2.first && bateriaMax>=0){
                        dondeQuede = i;
                        return registros;
                    }else{
                        registros.remove(registros.size()-1);
                        dondeQuede = i;
                        return registros;
                    }
                }
            }else{
                bateriaMax = bateriaMax-(mapa.getWeight(registro.get(i), registro.get(i+1))*consumoHora);
                tiempoMax = tiempoMax-(mapa.getWeight(registro.get(i), registro.get(i+1)))-0.5;
                registros.add(registro.get(i+1));
            }
            dondeQuede = dondeQuede+1;
        }
        return registros;
    }

    public static Pair<Boolean, Double> PodemosAgregarlo(ArrayList<Integer> registro, Digraph mapa, int cantEsta, double tiempoCarga){     //Comprueba que cumpla con la restricción de tiempo
        double tiempoMax = 10;
        double tiempoVisita = RuteoVehiculosElectricos.st_customer*(registro.size()-cantEsta);  //Lo que se demora con cada cliente
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
        return new Pair<Boolean, Double>((tiempoMax-tiempoVisita-tiempoCarga)>=0,tiempoMax-tiempoVisita-tiempoCarga);
    }

    public static double tiempoCarga(double Q, int estacion, double porcentaje,double bateriaCompleta) {
        short tipo = RuteoVehiculosElectricos.tipoEstacion[estacion - RuteoVehiculosElectricos.m - 1];
        double m = RuteoVehiculosElectricos.pendienteFuncionCarga[tipo];
        double bateriaMeta = (porcentaje*bateriaCompleta)/100;
        double bateriaPorCargar = bateriaMeta - (bateriaCompleta-Q);
        double tiempoCarga = bateriaPorCargar/m;
        return tiempoCarga;
    }
}