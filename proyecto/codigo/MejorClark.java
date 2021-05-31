
/**
 * Write a description of class MejorClark here.
 * 
 * @author Simon Correa
 * @version 30/05/2021
 */
public class MejorClark
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;
    private double ahorro;

    /**
     * Constructor for objects of class MejorClark
     */
    public MejorClark()
    {
        // initialise instance variables
        x = -1;
        y = -1;
        ahorro = 0;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getAhorro() {
        return ahorro;
    }

    public void setAhorro(double ahorro) {
        this.ahorro = ahorro;
    }
}
