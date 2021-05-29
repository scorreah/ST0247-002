import java.util.Stack;
public class AgenteViajero {
    public void ruta(int[][] matriz){
        Stack<Integer> stack = new Stack<Integer>();
        int cantNodos = matriz[1].length-1;
        int[] visitados = new int[cantNodos+1];
        visitados[1] = 1;
        stack.push(1);
        int element;
        int actual = 0;
        int i = 1;
        int min = Integer.MAX_VALUE;
        boolean punto = false;
        System.out.println("respuesta: "+1);
        while(!stack.isEmpty()){
            element = stack.peek();
            i = 1;
            min = Integer.MAX_VALUE;
            while(i<=cantNodos){
                if(matriz[element][i]>0 && visitados[i]==0){
                    if(matriz[element][i]<min){
                        min = matriz[element][i];
                        actual = i;
                        punto = true;
                    }
                }
                i++;
            }
            if(punto){
                visitados[actual] = 1;
                stack.push(actual);
                System.out.println(actual);
                punto = false;
                continue;
            }
            stack.pop();
        }
    }
}
