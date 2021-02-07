
/**
 * Write a description of class Ubicacion here.
 * 
 * @author Simon Correa, David Gomez 
 * @version 7/02/2021
 */
public class Ubicacion
{
    // instance variables - replace the example below with your own
    private double x;
    private double y;
    private String nombre;
    private double distancia;
    

    /**
     * Constructor for objects of class Ubicacion
     */
    public Ubicacion(double x, double y, String nombre)
    {
        // initialise instance variables
        this.x = x;
        this.y = y;
        this.nombre = nombre;
    }
    
    public Ubicacion(double distancia, String nombre) {
        this.distancia = distancia;
        this.nombre = nombre;
    }
    
    
}
