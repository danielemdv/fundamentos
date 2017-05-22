package funmatnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Conjunto{

//Estructura interna para guardar los id de los metaestados que se vayan identificando
private ArrayList<String> stateKeys;

//Estructura interna para guardar los metaestados de acuerdo a su nombre
private HashMap<String, Estado> states;

public Conjunto(){
  stateKeys = new ArrayList();
  states = new HashMap();
}


//solamente agregar un estado si no se contiene ya
public void add(Estado e){
  String s = e.id;
  s = ordenarLex(s);
  s = eliminaRepeticiones(s);

  if(!contains(e)){
    stateKeys.add(s);
    states.put(s, e);
  }
}

//Para saber si ya contiene un metaestado igual
public boolean contains(Estado e){
  String s = e.id;
  boolean res = false;

  s = ordenarLex(s);
  s = eliminaRepeticiones(s);

  if(stateKeys.contains(s))
    res = true;

  return res;
}

public String toString(){
  StringBuilder res = new StringBuilder ();

  for (int i = 0; i < stateKeys.size() ; i++) {
    res.append(stateKeys.get(i) + "\n");
  }
  return res.toString();
}

public int size(){
  return stateKeys.size();
}

public Estado getState(String s){
  return states.get(s);
}

public ArrayList<String> getKeys(){
  return stateKeys;
}


//---------HELPERS-------------------------


//Método para regresar un String ordenado de manera lexicografica.
public String ordenarLex(String s){
  char[] chars = s.toCharArray();
  Arrays.sort(chars);
  String sorted = new String(chars);
  return sorted;
}

private String eliminaRepeticiones(String s){
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



}
