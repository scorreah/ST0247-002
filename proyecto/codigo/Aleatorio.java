
/**
 * Write a description of class Aleatorio here.
 * 
 * @author Simon Correa 
 * @version 30/05/2021
 */
import java.util.Random;
public class Aleatorio
{
    // instance variables - replace the example below with your own
    private long seed;
    private Random generador;

    /**
     * Constructor for objects of class Aleatorio
     */
    public Aleatorio()
    {
        // initialise instance variables
        this.generador = new Random();
        this.generador.setSeed(0);
        this.seed = 0;
    }
    
    public Aleatorio(long seed) {
        this.seed = seed;
        this.generador = new Random(seed);
    }
    
    public int next() {
        int aleatorio = generador.nextInt(3) + 1;
        return aleatorio;
    }
    
    public static void main(String args[]) {
        /*Random generador = new Random();
        generador.setSeed(1);
        
        for(int i = 0; i < 10; i++) {
            int aleatorio = generador.nextInt(3) + 1;
            System.out.println(aleatorio);
        }
        System.out.println("----");
        */
       Aleatorio a1 = new Aleatorio(123456);
       for(int i = 0; i < 10; i++) {
            System.out.println(a1.next());
        }
    }
}
