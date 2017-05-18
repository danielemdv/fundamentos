package funmatnb;

import java.util.ArrayList;

public class Particion {
    public Subconjunto[] subconjuntos; //Como Particion, yo tengo subconjuntos. Cada subconjunto sabe cual es su id.
    public String idParticion;
    private int cantidadDeSubconjuntosActual;

    public boolean edoAceptacion;
    public ArrayList<String> x0destino;
    public ArrayList<String> x1destino;
    //Las particiones pueden nombrar a sus subconjuntos, esto facilita la identificacion de un estado dentro de aquellos
    //Y la creacion de nuevas Particiones.

    public Particion(String idParticion, int estados){
        //Como dicho en el escrito, el maximo numero de particiones de hecho es la cantidad de estados que tiene este
        subconjuntos = new Subconjunto[estados];

       // Voy a poder nombrar el subconjunto con el array ya que el subconjunto 2 corresponde al subconjunto de  subconjuntos[2]
        this.idParticion = idParticion; //Es publico por el uso que le implementamos luego, para optimizar lo volvemos privado.
        cantidadDeSubconjuntosActual = 1;
        // Le creo un subconjunto vacio?

    }

    public boolean creaSubconjunto(String idSub){
        Subconjunto s = new Subconjunto(idSub);
        boolean res = contieneSubconjunto(s);
        if(!res){
            subconjuntos[cantidadDeSubconjuntosActual] = s;
            cantidadDeSubconjuntosActual ++;

        }
        return res;
    }


    public boolean contieneSubconjunto (Subconjunto a){
        boolean res = false;
        int i =0;
        while (i< cantidadDeSubconjuntosActual && !res){
            if (!subconjuntos[i].equals(a)){
                res = true;
            }else{
            i++;
            }
          }

        return res; //Regresa true si la particion ya contiene a ese subconjunto.
      }
}
