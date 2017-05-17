package funmatnb;

import java.util.HashMap;

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









}
