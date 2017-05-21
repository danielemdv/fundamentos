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

  System.out.println("Es determinista?: " + this.esDeterminista());
}


public void determinar(){


  //Checa primero si es o no es determinsta. En caso de que no lo sea, procede con la determinacion
  if(this.esDeterminista()){
    return;
  }


  //Identificar metaestados
  HashMap<String, Estado> metaEstados = detMetaestados();

  //Crear tabla intermedia

  /*
    Con todos los metaestados obtenidos, debemos crear la tabla 'intermedia'.
    Esta tabla dira a donde mappean o transicionan todos los metaestados

    Debemos saber si se dejaron de agregar estados nuevos para no tener que reprocesar toda la cosa.

    HashMap<String, Estado> metaEstadosFinal = tablaMetaestados(metaEstados);


  */


  //derivar tabla final


}

//volver al automata determinista si es que no lo es
public HashMap<String, Estado> detMetaestados(){


  Estado[] edos = estados.values().toArray(new Estado[estados.size()]);

  //ArrayList<String[]> metaEstados = new ArrayList(); //phasing out this old fella

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

    //Aqui iteramos los estados a los que transiciona el estado i con x = 0. Los concatenamos en la variable x0
    //Tambien guardamos x0Aceptacion = 1 en caso de que el estado que acabamos de concatenar es de aceptacion
    for (int j = 0; j < edos[i].x0destino.size() ; j++) {
      x0 += edos[i].x0destino.get(j);

      //Si alguno de los estados agregados es de aceptacion, todo el metaestado es de aceptacion tambien
      if(estados.get(edos[i].x0destino.get(j)).edoAceptacion){
        x0Aceptacion = "1";
      }
    }


    //Hacemos lo mismo que lo anterior pero para las transiciones a x1
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


public HashMap<String, Estado> tablaMetaestados(HashMap<String, Estado> hmIn){

  //tenemos estados. El hashmap del automata en crudo
  ArrayList<Estado> metaEstados1 = (ArrayList<Estado>)Arrays.asList(hmIn.values().toArray(new Estado[hmIn.size()]));
  HashMap<String, Estado> metaEstadosHM = new HashMap();

  //variables para los metaestados
  String x0 = "";
  String x0Aceptacion = "0";
  String x1 = "";
  String x1Aceptacion = "0";

  //boolean newFlag = false; //Bandera para saber si se agrego un metaEstado nuevo y debemos seguir procesando la tabla

  //iterar sobre todos los metaestados con longitud mayor a 1 (Pues los demas ya los hicimos)
  int numMetaEstados = metaEstados1.size();
  for (int i = 0; i < numMetaEstados ; i++) {
    if(metaEstados1.get(i).id.length() > 1){

      //Conseguir a donde puede ir el estado, tanto para 0 como para 1 y checar si ya existe.
      x0 = metaEstados1.get(i).getx0Meta();
      x1 = metaEstados1.get(i).getx1Meta();

      //revisamos si ya existen los metaestados, si no, los instanciamos y lo agregamos.
      if(!hmIn.containsKey(x0)){
        Estado e = new Estado(x0);
        String[] trans = encontrarTransicionesParaMetaestados(x0);
        e.setEstadosDet(trans[0], trans[1]);
        e.edoAceptacion = esEstadoAceptacion(e.id);
        metaEstados1.add(e);
        numMetaEstados++; //Decimos que ya hay un nuevo metaestado
        //newFlag = true;
      }

      //igual pero para x1
      if(!hmIn.containsKey(x1)){
        Estado e = new Estado(x1);
        String[] trans = encontrarTransicionesParaMetaestados(x1);
        e.setEstadosDet(trans[0], trans[1]);
        e.edoAceptacion = esEstadoAceptacion(e.id);
        metaEstados1.add(e);
        numMetaEstados++; //Decimos que ya hay un nuevo metaestado
        //newFlag = true;
      }



    }
  }

  //falta llenar el nuevo hashmap
  return metaEstadosHM;
}



//Método para regresar un String ordenado de manera lexicografica.
public String ordenarLex(String s){
  char[] chars = s.toCharArray();
  Arrays.sort(chars);
  String sorted = new String(chars);
  return sorted;
}

//regresa un String[2] donde String[0] es el de x0 y String[1] es el de x1
//(basicamente hace una union entre los estados a los que transicionan los estados que componen al metaestado)
private String[] encontrarTransicionesParaMetaestados(String id){
  String x0 = "";
  String x1 = "";


  for (int i = 0; i < id.length() ; i++) {
    Estado e = estados.get(id.charAt(i) + "");
    x0 += e.getx0Meta();
    x1 += e.getx1Meta();
  }

  x0 = ordenarLex(x0);
  x1 = ordenarLex(x1);

  //Eliminar repetidos de los metaestados
  x0 = eliminaRepeticiones(x0);
  x1 = eliminaRepeticiones(x1);

  return new String[] {x0, x1};
}

//dado el id de un estado o metaestado, regresar si es de aceptacion
private boolean esEstadoAceptacion(String id){
  boolean res = false;
  for (int i = 0; i < id.length() ; i++) {
    if(estados.get(id.charAt(i) + "").edoAceptacion){
      res = true;
    }
  }
  return res;
}

private String eliminaRepeticiones(String s){
  String res = "";
  int i = 0;
  int j;

  res+= "" + s.charAt(0);

  for (j = 1; j < s.length() ;j++ ) {
    if(s.charAt(i) != s.charAt(j)){
      res += "" + s.charAt(j);
      i = j;
    }
  }
  return res;
}




}
