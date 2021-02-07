import java.io.*;
import java.net.*;
import java.util.*;
import javafx.util.Pair;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lectura { 
    public static Estructura mapa;
    
    public Lectura(String s) throws Exception {
        BufferedReader l1= new BufferedReader(new FileReader("puentesColgantes.txt"));
        String linea;
        Pattern pat = Pattern.compile("^(\\d+) (\\d+\\.\\d+) (\\d+\\.\\d+) (.*)$");
        int cont = 0;
        while((linea = l1.readLine()) != null) {
            Matcher matcher = pat.matcher(linea);
            if (matcher.find()) {
                cont++; 
            }
            if (linea.contains("Arcos")) break;
        }
        
        mapa = new Estructura(cont);
        pat = Pattern.compile("^(\\d+) (\\d+) (\\d+\\.\\d+) (.*)$");
        while((linea = l1.readLine()) != null) {
            Matcher matcher = pat.matcher(linea);
            if (matcher.find()) {
                if (matcher.group(1).equals("10000"))
                    mapa.addArc(0+1, Integer.parseInt(matcher.group(2))+1, new Ubicacion(Double.parseDouble(matcher.group(3)), matcher.group(4)));
                else if (matcher.group(2).equals("10000"))
                    mapa.addArc(Integer.parseInt(matcher.group(1))+1,0+1, new Ubicacion(Double.parseDouble(matcher.group(3)), matcher.group(4)));
                else
                    mapa.addArc(Integer.parseInt(matcher.group(1))+1,Integer.parseInt(matcher.group(2))+1, new Ubicacion(Double.parseDouble(matcher.group(3)), matcher.group(4)));
            }
             
        }
        l1.close();
        
        BufferedReader l2= new BufferedReader(new FileReader("puentesColgantes.txt"));
        pat = Pattern.compile("^(\\d+) (\\d+\\.\\d+) (\\d+\\.\\d+) (.*)$");
        int i = 1;
        while((linea = l2.readLine()) != null) {
            Matcher matcher = pat.matcher(linea);
            if (matcher.find()) {
                mapa.addArc(0, i, new Ubicacion(Double.parseDouble(matcher.group(2)), Double.parseDouble(matcher.group(3)), matcher.group(4)));
                i++;
            } 
            
            if (linea.contains("Arcos")) break; 
        }
        l2.close();
    }

    public static void main(String[] args) throws Exception{
        
    }
}
