
public class Algorithm {
    public static boolean DFSColorFC(DigraphAM am) {
        int[] lista = new int[am.size+1];
        return DFSColorFCAux(am, am.getFirst(), lista, 1);
    }
    private static boolean DFSColorFCAux(DigraphAM am, int nodo, int[]lista, int color) {
        boolean cop = true;
        if(lista[nodo] == 0){
            lista[nodo]=color;
            for(Integer s: am.getSuccessors(nodo)){
                if(color==1){
                    cop = DFSColorFCAux(am, s, lista, 2);
                }else{
                    cop = DFSColorFCAux(am, s, lista, 1); //T(n) = T(n-1)  ==> O(N)
                }
                if(!cop) return false;
            }
        }else{
            if(color == lista[nodo]){
                return true;
            }else{
                return false;
            }
        }
        return cop;
    }
}