package funmatnb;

import java.util.ArrayList;

public class Subconjunto {
    //Creamos una clase de subconjuntos que sabe a cuales estados pertenecen. Estos subconjuntos
    //Son parte de las particiones.
    public String id;
    public ArrayList<String> estados;

    public Subconjunto(String id){
        this.id = id;
        estados = new ArrayList();

    }
    public void agregaEstadoAlSubconjunto(Estado est){
        if(!contieneEstado(est)){
            estados.add(est.id);
        }
    }
    public boolean contieneEstado(Estado a){
        /*Revisa en su grupo de estados si esta el actual, para no repetir. */
        boolean res = true;
        if (!estados.contains(a.id)){
            res = false;
        }
        return res;
    }
    public boolean equals (Subconjunto a){
        return (this.id == a.id);
    }
}
