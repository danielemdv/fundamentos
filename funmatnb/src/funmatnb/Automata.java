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
  boolean res = true;

  Estado[] edos = estados.values().toArray(new Estado[estados.size()]);

  for (int i = 0; i< edos.length ; i++) {
    if(edos[i].x0destino.size() > 1 || edos[i].x0destino.get(0).equals("-")  || edos[i].x1destino.size() > 1  || edos[i].x1destino.get(0).equals("-")){
      return false;
    }
  }
  return res;
}

public void pruebaMeta(){

  HashMap<String, Estado> metaEstados = detMetaestados();
  Estado[] edos = metaEstados.values().toArray(new Estado[metaEstados.size()]);

  for (int i = 0; i < edos.length ;i++ ) {
    System.out.println("Edo: " + edos[i].id + "    Out: " + edos[i].edoAceptacion);
  }



}


public void determinar(){


  //Checa primero si es o no es determinsta. En caso de que no lo sea, procede con la determinacion
  if(this.esDeterminista()){
    return;
  }


  //Identificar metaestados
  //ArrayList<String[]> metaEstados = detMetaestados();

  //Crear tabla intermedia

  //derivar tabla final


}

//volver al automata determinista si es que no lo es
public HashMap<String, Estado> detMetaestados(){


  Estado[] edos = estados.values().toArray(new Estado[estados.size()]);

  ArrayList<String[]> metaEstados = new ArrayList(); //phasing out this old fella

  HashMap<String, Estado> metaEstadosHM = new HashMap();

  //Agregamos el primer estado, pues siempre es el de entrada. Agregamos si es o no de aceptacion (en este proyecto no se puede)
  metaEstadosHM.put(edos[0].id,edos[0]);

  boolean flagVacio = false; //Flag que se debe poner en true para saber si se debe agregar el estado vacio.

  //vemos los metaestados de transicion de cada uno de los estados
  for (int i = 0;i < edos.length; i++) {

    //variables para los metaestados
    String x0 = "";
    String x0Aceptacion = "0";
    String x1 = "";
    String x1Aceptacion = "0";

    for (int j = 0; j < edos[i].x0destino.size() ; j++) {
      x0 += edos[i].x0destino.get(j);

      //Si alguno de los estados agregados es de aceptacion, todo el metaestado es de aceptacion tambien
      if(estados.get(edos[i].x0destino.get(j)).edoAceptacion){
        x0Aceptacion = "1";
      }

    }

    for (int j = 0; j < edos[i].x1destino.size() ; j++) {
      x1 += edos[i].x1destino.get(j);

      if(estados.get(edos[i].x1destino.get(j)).edoAceptacion){
        x1Aceptacion = "1";
      }
    }

    //Si alguna transicion estaba vacia, activar la flag del estado vacio
    if(x0.isEmpty() || x1.isEmpty()){
      flagVacio = true;
    }

    //Hacer un ordenamiento lexicografico del metaestado (e.g. ACB -> ABC) para asegurar no repeticiones
    x0 = ordenarLex(x0);
    x1 = ordenarLex(x1);


    //creo que no se necesita esto
    boolean repetidos = false;
    if(x0.equals(x1)){
      repetidos = true;
    }

    //Para el primer estado, settear sus metaestados de transicion
    if(i == 0){
      metaEstadosHM.get(edos[i].id).setEstadosDet(x0,x1);
    }

    if(!metaEstadosHM.containsValue(x0) && !x0.isEmpty()){
      Estado e = new Estado(x0);
      if(x0Aceptacion.equals("1")){
        e.edoAceptacion = true;
      }
      metaEstadosHM.put(x0, e);
    }

    if(!metaEstadosHM.containsValue(x1) && !x1.isEmpty()){
      Estado e = new Estado(x1);
      if(x1Aceptacion.equals("1")){
        e.edoAceptacion = true;
      }
      metaEstadosHM.put(x1, e);
    }

    /*
    //Asegurar que no se repita un metaestado ya contemplado y que no sean vacios
    String[] m0 = {x0, x0Aceptacion};
    if(!metaEstados.contains(m0) && !x0.isEmpty()){
      metaEstados.add(m0);
    }

    //Si son iguales no se agrega este
    if(!repetidos){
      String[] m1 = {x1, x1Aceptacion};
      if(!metaEstados.contains(m1) && !x1.isEmpty()){
        metaEstados.add(m1);
      }
    }
    */

  }//end for

  //Agregar el estado vacio solamente si es necesario
  if(flagVacio){
    if(!metaEstadosHM.containsValue("-")){
      Estado e = new Estado("-");
      metaEstadosHM.put("-", e);
    }
  }

  return metaEstadosHM;

}


//Método para regresar un String ordenado de manera lexicografica.
public String ordenarLex(String s){
  char[] chars = s.toCharArray();
  Arrays.sort(chars);
  String sorted = new String(chars);
  return sorted;
}





}
