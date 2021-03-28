
/**
 *
 * @author Simon Correa, David Gomez, ljpalaciom
 */
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
public class RuteoVehiculosElectricos {
    ArrayList<ArrayList<Integer>> rutas;
    int noRutas;
    int n, m, u, breaks;
    public static double r, speed, Tmax, Smax, st_customer, Q;
    Digraph mapa;
    short tipoEstacion[];
    float pendienteFuncionCarga[];
    String filename;
    ArrayList<Pair<Float, Float>> coordenadas;
    double tiempoSolucion;
    public RuteoVehiculosElectricos(String filename) {
        this.filename = filename;
        BufferedReader lector;
        String linea;
        String lineaPartida[];
        try {
            lector = new BufferedReader(new FileReader(filename));
            double[] valores = new double[10];
            for (int i = 0; i < 10; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                valores[i] = Float.parseFloat(lineaPartida[2]);
            }
            n = (int) valores[0];
            m = (int) valores[1];
            u = (int) valores[2];
            breaks = (int) valores[3];
            r = valores[4];
            speed = valores[5];
            Tmax = valores[6];
            Smax = valores[7];
            st_customer = valores[8];
            Q = valores[9];
            lector.readLine();
            lector.readLine();
            lector.readLine();
            coordenadas = new ArrayList<Pair<Float, Float>>();
            mapa = new DigraphAM(m);
            for (int i = 0; i <= m; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                coordenadas.add(new Pair(Float.parseFloat(lineaPartida[2]), Float.parseFloat(lineaPartida[3])));
            }
            tipoEstacion = new short[u];
            for (int i = 0; i < u; i++) {
                linea = lector.readLine();
                //lineaPartida = linea.split(" ");
                //coordenadas.add(new Pair(Float.parseFloat(lineaPartida[2]), Float.parseFloat(lineaPartida[3])));
                //tipoEstacion[i] = Short.parseShort(lineaPartida[5]);
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    double tiempo = (Math.sqrt(Math.pow(coordenadas.get(i).first - coordenadas.get(j).first,2)+ 
                                Math.pow(coordenadas.get(i).second - coordenadas.get(j).second, 2)))/speed;
                    mapa.addArc(i, j, tiempo);
                }
            }
            pendienteFuncionCarga = new float[3];
            lector.readLine();
            lector.readLine();
            lector.readLine();
            for (int i = 0; i < 3; ++i) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                pendienteFuncionCarga[i] = Float.parseFloat(lineaPartida[3]);
            }
            lector.readLine();
            lector.readLine();
            lector.readLine();
            for (int i = 0; i < 3; ++i) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                pendienteFuncionCarga[i] = Float.parseFloat(lineaPartida[3]) / pendienteFuncionCarga[i];
            }
            tiempoSolucion = Double.MAX_VALUE;
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String toString() {
        return "RuteoVehiculosElectricos{" + "r=" + r + ", speed=" + speed + ", Tmax=" + Tmax + ", Smax=" + Smax + ", st_customer=" + st_customer + ", Q=" + Q + ", tiempoSolucion=" + tiempoSolucion + '}';
    }

    public void exportarPuntosCSV() {
        try {
            PrintStream escribirCoordenadas = new PrintStream(new File("ArchivosGenerados\\Coordenadas.csv"));
            escribirCoordenadas.println("X,Y");
            for (Pair<Float, Float> coordenada : coordenadas) {
                escribirCoordenadas.println(coordenada.first + "," + coordenada.second);
            }
            escribirCoordenadas.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /*public void exportarRutasCSV(ArrayList<ArrayList<Integer>> rutas) {
    try {
    int numRuta = 0;
    for (ArrayList<Integer> ruta : rutas) {
    PrintStream escribirCoordenadas = new PrintStream(new File("ArchivosGenerados\\ruta" + numRuta + ".csv"));
    escribirCoordenadas.println("X,Y");
    for (Integer verticeActual : ruta) {
    escribirCoordenadas.println(coordenadas[verticeActual].first + "," + coordenadas[verticeActual].second);
    }
    escribirCoordenadas.close();
    numRuta++;
    }
    } catch (FileNotFoundException ex) {
    System.out.println(ex);
    }
    }*/
    public void solucionar(boolean test) {

        calculos solucion = new calculos();
        solucion.ahorros(this.mapa, this.mapa.size());
        solucion.calculadorRuta(this.mapa);
        if (test) {
            this.rutas = solucion.obtenerRuta(); 
        } else {
            this.rutas = solucion.rutas;
        }
        this.rutas.trimToSize();
        this.noRutas = this.rutas.size();

        System.out.println();
    }

    /**
     * Este metodo es un test para verificar que la solucion es correcta. 
     * @param rutas Es un contenedor de rutas representadas por un arraylist de parejas donde el primer elemento indica el nodo
     * y el segundo elemento el tiempo que se quedo en ese nodo
     * @return Verdadero si el tiempo de solucion expresado concuerda y si la bateria nunca esta por debajo de 0.
     */
    public boolean comprobarSolucion( ArrayList<ArrayList<Pair<Integer, Integer>>> rutas){
        return false;
    }

    public static String[][] test(){
        File f = new File("../DataSets");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));
        String[][] analisis = new String[names.size()][5];
        int cont = 0;
        for(String file: names) {
            System.gc();
            Runtime runtime = Runtime.getRuntime();
            long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
            RuteoVehiculosElectricos problema1 = new RuteoVehiculosElectricos("../DataSets/"+file);
            analisis[cont][2] = ""+problema1.m;
            long mejor, peor, prom;
            mejor = Long.MAX_VALUE;
            peor = 0;
            prom = 0;
            for (int i = 0; i < 100; i++) {
                long ti = System.currentTimeMillis();
                problema1.solucionar(true);
                long tf = System.currentTimeMillis();
                long total = tf - ti;
                mejor = total < mejor ? total : mejor;
                peor = total > peor ? total : peor;
            }
            problema1.solucionar(false);
            analisis[cont][3] = "" + problema1.noRutas;
            analisis[cont][4] = "" + problema1.tiempoTotalRutas();
   
            prom = (peor + mejor) /2;
            System.gc();
            long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsed = ((memoryAfter - memoryBefore)/1024); //Memoria en KB
            analisis[cont][0] = "" + memoryUsed;
            analisis[cont][1] = "" + peor;
            cont++;
        }

        return analisis;
    }
    
    public double tiempoTotalRutas() {
        double tiempo = 0;
        for (ArrayList<Integer> ruta: this.rutas) {
            tiempo += this.mapa.getWeight(0, ruta.get(0));
            for (int i = 0; i < ruta.size()-1; i++) {
                tiempo += this.mapa.getWeight(ruta.get(i), ruta.get(i+1));
            }
            tiempo += this.mapa.getWeight(0, ruta.get(ruta.size()-1));
        }
        return tiempo;
    }
    public static void clearScreen() {  
        System.out.printf("\033[H\033[2J");  
        System.out.flush();  
        System.out.println();
    } 

    private static void printResults(String[][] analisis) {
        clearScreen();
        for(int i = 0; i < analisis.length; i++ ) {
            System.out.printf("%s KB, %s ms, %s clientes, %s camiones, %s tiempo total de todas las rutas\n",
                analisis[i][0], analisis[i][1], analisis[i][2], analisis[i][3], analisis[i][4]);
        }
    }

    public static void main(String[] args) {
        File f = new File("../DataSets");
        ArrayList<String> names = new ArrayList<>(Arrays.asList(f.list()));
        System.gc();

        String[][] analisis = test();
        printResults(analisis);
        System.out.println("Bien hecho");
        //DibujarRuta bueno = new DibujarRuta(
        //problema1.exportarPuntosCSV();
    }

}