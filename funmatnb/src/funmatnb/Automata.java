package funmatnb;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Automata{


/*
Como atributos debe tener:

-Diccionario de objetos estado, la llave (para la entrada del diccionaro) sera la letra del estado
-Estado actual


Como funciones creo que debe tener:

-funciones para cambiar de estado dependiendo del input.
-funciones fuzzy para que indique en qué estados podría estar dependiendo de
 la cantidad de input que se le de.

*/

public String estadoActual;
public HashMap<String, Estado> estados;

public Automata(HashMap<String, Estado> estados){
  this.estados = estados;
  estadoActual = "A"; //Segun el prof esto siempre sera asi.

}

//Funcion que determina si este automata es determinista
public boolean esDeterminista(){
  return true;

}


public void determinar(){
  //Checa primero si es o no es determinsta. En caso de que no lo sea, procede con la determinacion
  /*
    if(this.esDeterminista()){
      return;
    }

  */


/*
determinar metaestados (detMetaestados)
Crear tabla intermedia.
Derivar de esa la tabla final
*/

}

//volver al automata determinista si es que no lo es
public ArrayList<String[]> detMetaestados(){
/*Regresa un ArrayList de tipo String[2]. La primera entrada es el string del metaestado y la
  segunda es un String que es "1" si el metaestado es de aceptacion, si no es "0"
*/


  Estado[] edos = (Estado[])estados.values().toArray();
  ArrayList<String[]> metaEstados = new ArrayList();

  //Agregamos el primer estado, pues siempre es el de entrada. Agregamos si es o no de aceptacion (en este proyecto no se puede)
  String[] s = {edos[0].id,((edos[0].edoAceptacion) ? ("1"):("0"))};
  metaEstados.add(s);

  boolean flagVacio = false; //Flag que se debe poner en true para saber si se debe agregar el estado vacio.

  //vemos los metaestados de transicion de cada uno de los estados
  for (int i = 0;i < edos.length; i++) {

    //variables para los metaestados
    String x0 = "";
    String x0Aceptation = "0";
    String x1 = "";
    String x1Aceptation = "0";

    for (int j = 0; j < edos[i].x0destino.size() ; j++) {
      x0 += edos[i].x0destino.get(j);

      //Si alguno de los estados agregados es de aceptacion, todo el metaestado es de aceptacion tambien
      if(estados.get(edos[i].x0destino.get(j)).edoAceptacion){
        x0Aceptation = "1";
      }

    }

    for (int j = 0; j < edos[i].x1destino.size() ; j++) {
      x1 += edos[i].x1destino.get(j);

      if(estados.get(edos[i].x1destino.get(j)).edoAceptacion){
        x1Aceptation = "1";
      }
    }

    //Si alguna transicion estaba vacia, activar la flag del estado vacio
    if(x0.isEmpty() || x1.isEmpty()){
      flagVacio = true;
    }

    //Hacer un ordenamiento lexicografico del metaestado (e.g. ACB -> ABC) para asegurar no repeticiones
    x0 = ordenarLex(x0);
    x1 = ordenarLex(x1);

    //Asegurar que no se repita un metaestado ya contemplado y que no sean vacios
    if(metaEstados.indexOf(x0) == -1 && !x0.isEmpty()){
      String[] m0 = {x0, x0Aceptation};
      metaEstados.add(m0);
    }

    if(metaEstados.indexOf(x1) == -1 && !x1.isEmpty()){
      String[] m1 = {x1, x1Aceptation};
      metaEstados.add(m1);
    }

  }//end for

  //Agregar el estado vacio solamente si es necesario
  if(flagVacio){
    if(metaEstados.indexOf("-") == -1){
      String[] v = {"-", "0"};
      metaEstados.add(v);
    }
  }


  return metaEstados;

}


//Método para regresar un String ordenado de manera lexicografica.
public String ordenarLex(String s){
  char[] chars = s.toCharArray();
  Arrays.sort(chars);
  String sorted = new String(chars);
  return sorted;
}





}
