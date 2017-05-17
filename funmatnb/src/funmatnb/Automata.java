package funmatnb;

import java.util.HashMap;
import java.util.ArrayList;

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
/*
determinar metaestados (detMetaestados)
Crear tabla intermedia.
Derivar de esa la tabla final
*/

}

//volver al automata determinista si es que no lo es
public ArrayList<String> detMetaestados(){
  //Checa primero si es o no es determinsta. En caso de que no lo sea, procede con la determinacion
  /*
    if(this.esDeterminista()){
      return;
    }

  */


  Estado[] edos = (Estado[])estados.values().toArray();
  ArrayList<String> metaEstados = new ArrayList();

  metaEstados.add(edos[0].id); //Agregamos el primer estado, pues siempre es el de entrada

  boolean flagVacio = false; //Flag que se debe poner en true para saber si se debe agregar el estado vacio.

  //vemos los metaestados de transicion de cada uno de los estados
  for (int i = 0;i < edos.length; i++) {

    //variables para los metaestados
    String x0 = "";
    String x1 = "";

    for (int j = 0; j < edos[i].x0destino.size() ; j++) {
      x0 += edos[i].x0destino.get(j);
    }

    for (int j = 0; j < edos[i].x1destino.size() ; j++) {
      x1 += edos[i].x1destino.get(j);
    }

    //Si alguna transicion estaba vacia, activar la flag del estado vacio
    if(x0.isEmpty() || x1.isEmpty()){
      flagVacio = true;
    }


    //Asegurar que no se repita un metaestado ya contemplado y que no sean vacios
    if(metaEstados.indexOf(x0) == -1 && !x0.isEmpty()){
      metaEstados.add(x0);
    }

    if(metaEstados.indexOf(x1) == -1 && !x1.isEmpty()){
      metaEstados.add(x1);
    }

  }

  if(flagVacio){
    metaEstados.add("-");
  }

  return metaEstados;

}









}
