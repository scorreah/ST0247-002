
/**
 * Write a description of class Ficheros here.
 * 
 * @author Simon Correa 
 * @version 30/05/2021
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
public class Ficheros
{
    // instance variables - replace the example below with your own
    private String files[];
    private double tiempos[];
    private long semillas[];
    private int noFiles;
    private int indice = 0;

    /**
     * Constructor for objects of class Ficheros
     */
    public Ficheros(int noFiles)
    {
        // initialise instance variables
        this.noFiles = noFiles;
        files = new String[noFiles];
        tiempos = new double[noFiles];
        semillas = new long[noFiles];
    }

    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Resultados.txt"));
            String linea;
            int cont = 0;
            reader.readLine();
            while((linea =reader.readLine())!=null){    // Nombre archivo - 404.1 - 000000
                String[] splitted = linea.split(" - ");
                files[cont] = splitted[0];
                tiempos[cont] = Double.parseDouble(splitted[1]);
                semillas[cont] = Long.parseLong(splitted[2]);
                cont++;
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Archivo inexistente");
            e.printStackTrace();
        }
    }

    public void write() throws FileNotFoundException {
        PrintStream write = new PrintStream(new File("Resultados.txt"));
        write.println("Nombre_Fichero.txt - Horas - Semilla");
        for(int i = 0; i < noFiles; i++) {
            String fichero = files[i];
            double tiempo = tiempos[i];
            long seed = semillas[i];
            write.printf("%s - %s - %d%n", fichero,""+ tiempo, seed);
        }
        write.close();

    }

    public int contains(String fileName) {
        int index = -1;
        if (Arrays.asList(this.files).contains(fileName)) { //Si el archivo está guardado
            for(int i = 0; i < noFiles; i++) {  //Encuentre su indice
                if (files[i].equals(fileName)) {
                    index = i;
                    break;
                }
            }
        }
        return Arrays.asList(this.files).contains(fileName)? index : -1; //Si no lo contiene retorna -1
    }

    public void addNew(String fileName, double tiempoTotal, long seed) {
        files[indice] = fileName;
        tiempos[indice] = tiempoTotal;
        semillas[indice] = seed;
        indice++;
    }

    public void update(String fileName, double tiempoTotal, long seed) {
        int index = contains(fileName);
        tiempos[index] = tiempoTotal;
        semillas[index] = seed;

    }

    public double getTime(String fileName) {
        int index = contains(fileName);
        double tiempo = tiempos[index];
        return tiempo;
    }
    
    public long getSeed(String fileName) {
        int index = contains(fileName);
        long seed = semillas[index];
        return seed;
    }

    public void main(String[] args) {

    }
}
