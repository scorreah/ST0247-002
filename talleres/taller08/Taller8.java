
/**
 * Clase en la cual se implementan los metodos del Taller 8
 * 
 * @author Simón Correa, David Gomez, Mauricio Toro, Andres Paez
 */
public class Taller8 {
    /**
     * Metodo que pretende implementar el funcionamiento del algoritmo MergeSort
     * de un conjunto de elementos
     * @param a un arreglo con elementos
     * 
     * para mas informacion ver
     * @see <a href="https://www.youtube.com/watch?v=JSceec-wEyw">
     *
     */
    public static void mergesort(int[] a) {
        mergesort(a, 0, a.length-1);

    }

    private static void mergesort(int[] a, int i, int f) {
        if (i < f) {
            int mitad = (f+i)/2;
            mergesort(a, i, mitad);     //T(n/2)
            mergesort(a, mitad+1, f);   //T(n/2)
            merge(a, i, mitad, f); //pegar 2 que ya están ordenados O(n)
        }
    }

    private static void merge(int[] a, int i, int m, int f) {
        // Encontrar los tamaños de los dos subarreglos por hacer merge
        int n1 = m - i + 1;
        int n2 = f - m;
 
        // Crear dos arreglos temporales
        int L[] = new int[n1];      //Arreglo parte izquierda
        int R[] = new int[n2];      //Arreglo parte derecha
 
        // Llenar los respectivos arreglos temporales
        for (int l = 0; l < n1; ++l)
            L[l] = a[i + l];
        for (int j = 0; j < n2; ++j)
            R[j] = a[m + 1 + j];
 
        // Juntar los arreglos temporales
 
        // Indices iniciales de cada arreglo temporal
        int index1 = 0, index2 = 0;
 
        // Indice inicial del arreglo combinado
        int k = i;
        while (index1 < n1 && index2 < n2) {
            if (L[index1] <= R[index2]) {
                a[k] = L[index1];
                index1++;
            }
            else {
                a[k] = R[index2];
                index2++;
            }
            k++;
        }
 
        // Copiar los elementos restantes de L si queda alguno
        while (index1 < n1) {
            a[k] = L[index1];
            index1++;
            k++;
        }
 
        // Copiar los elementos restantes de R si queda alguno
        while (index2 < n2) {
            a[k] = R[index2];
            index2++;
            k++;
        }
    }

    /**
     * Metodo que pretende implementar el funcionamiento del algoritmo MergeSort
     * de un conjunto de elementos
     * @param a un arreglo con elementos
     * 
     * para mas informacion ver
     * @see <a href="https://www.youtube.com/watch?v=PgBzjlCcFvc">
     *
     */
    public static void quicksort(int[] a) {
        quicksort(a, 0, a.length -1);

    }

    private static void quicksort(int[] a, int i, int f) {
        if (i < f) {
            int piv = partition(a, i, f); //mueve pivote, deja menores izq, mayores der. y retorna pivote 
            quicksort(a, i, piv-1); // Mejor caso está la mitad, en el peor nada
            quicksort(a, piv+1, f); // Mejor caso está la mitad, en el peor todo
        }
    }

    private static int partition(int[] a, int i, int f){
        int pivote = a[f];
        int pivoteIndice = i;
        for (int index = i; index < f; index++) {
            if (a[index] <= pivote) {
                swap(a, index, pivoteIndice);
                pivoteIndice++;
            }
        }
        swap(a, pivoteIndice, f);

        return pivoteIndice;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;       
    }

    public static void main(String[] args){
        int[] a = {7,2,1,6,8,5,3,4};
        //quicksort(a);
        mergesort(a);
        for(int i = 0; i<a.length;i++){
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
