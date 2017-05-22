

package funmatnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Determinator {

  //Regresa el conjunto de estados ya listito
  public static Conjunto determinarAutomata(HashMap<String, Estado> hm){

    int i = 0;

    //Creamos un conjunto para ir agregando los estados que vayamos identificando
    Conjunto c = new Conjunto();

    //Agregamos el estado incicial del automata al conjunto
    //(sabemos que este siempre es A)
    c.add(new Estado("A")); //creamos uno nuevo para no estar modificando al del HashMap original.

    //tenemos que recorrer los estados del conjunto hasta que hayamos revisado todos.
    //En el proceso de ir revisando a donde transicionan los estados que contiene, iremos agregandole mas
    while(i < c.size()){

      Estado e = c.getState(c.getKeys().get(i));
      identificarEstado(e, hm); //construimos bien el metaestado como objeto.

      //Checamos si los metaestados a los que transiciona ya estan en el conjunto
      //En caso de que no, los agregamos para que este proceso se repita para todos.
      if(!c.contains(new Estado(e.getx0Meta()))){ //realmente no necesitamos esto...
        c.add(new Estado(e.getx0Meta()));
      }
      i++;
    }

    return c;
  }






  //En esta funcion identificamos las posibles transiciones del estado y las setteamos
  //tambien si el mismo estado es de aceptacion o no. se settea.
  //(no regresamos nada, pues estamos modificando al objeto directamente)
  public static void identificarEstado(Estado e, HashMap<String, Estado> hm){
    //Strings para ir formando sus transiciones
    String x0 = "";
    String x1 = "";
    boolean aceptacion = false;

    String id = e.id;

    //para cada estado individual en su id, recopilar sus transiciones x0
    for (int i = 0; i < id.length() ; i++) {
      //Aqui DEBE tronar si no existe el estado en el hashmap... debe de existir o algo esta mal en otro lado
      x0 += hm.get(id.charAt(i) + "").getx0Meta();
    }

    //para cada estado individual en su id, recopilar sus transiciones x1
    for (int i = 0; i < id.length() ; i++) {
      //Aqui DEBE tronar si no existe el estado en el hashmap... debe de existir o algo esta mal en otro lado
      x1 += hm.get(id.charAt(i) + "").getx1Meta();
    }

    //Checamos si este estado es de aceptacion, dependiendo de que otros estados este compuesto
    for (int i = 0; i < id.length() ; i++) {
      if(hm.get(id.charAt(i) + "").edoAceptacion){
        aceptacion = true;
      }
    }

    x0 = ordenarLex(x0);
    x0 = eliminaRepeticiones(x0);
    x0 = eliminaEstadosVacios(x0);

    x1 = ordenarLex(x1);
    x1 = eliminaRepeticiones(x1);
    x1 = eliminaEstadosVacios(x1);

    e.setEstadosDet(x0, x1);
    e.edoAceptacion = aceptacion;
  }










  //---------HELPERS-------------------------


  //Método para regresar un String ordenado de manera lexicografica.
  public static String ordenarLex(String s){
    char[] chars = s.toCharArray();
    Arrays.sort(chars);
    String sorted = new String(chars);
    return sorted;
  }

  private static String eliminaRepeticiones(String s){
    if(s.length() == 0){
      return "";
    }
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

  private static String eliminaEstadosVacios(String s){
    if(s.length() <= 1){
      return "";
    }
    String res = "";
    int i;

    for (i = 0; i < s.length() ; i++) {
      if(s.charAt(i) != '-'){
        res += s.charAt(i) + "";
      }
    }
    return res;
  }
}
