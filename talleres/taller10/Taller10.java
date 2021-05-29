class Taller10{
    public static String lcsDP(String X, String y){
        int nX = X.length();
        int nY = y.length();
       return lcsDP(X, y, nX, nY);
    }
    private static String lcsDP(String X, String y, int nX, int nY){
       // Crear la tabla
       String solucion = "";
       int[][] l = new int[nX+1][nY+1];
       // Llenar de ceros
       for (int j = 0; j <= nY; j++)
         l[0][j] = 0;
       for (int i = 0; i <= nX; i++)
         l[i][0] = 0;
       // Llenar la parte recursiva
       for (int i = 1; i <= nX; i++)
         for (int j = 1; j <= nY; j++)
            if (X.charAt(i-1) == y.charAt(j-1))
              l[i][j] = l[i-1][j-1] + 1;
            else
              l[i][j] = Math.max(l[i-1][j],l[i][j-1]);
       // Retornar la solucion
       //return l[nX][nY];
       solucion = "";
       int i = nX, j = nY;
       while (i != 0 && j!= 0){
          if (X.charAt(i-1) == y.charAt(j-1)){
             solucion = solucion + X.charAt(i-1);
             i = i-1;
             j = j-1;
          }
          else {
             if (l[i-1][j] >= l[i][j-1]) 
               i = i-1;                  
             else
               j = j-1;
          }
       }
       return solucion;
    }
    public static void main(String args[]){
        
        String solucion = lcsDP("AYZX", "AXYT");
        System.out.println(solucion);
        }
 }