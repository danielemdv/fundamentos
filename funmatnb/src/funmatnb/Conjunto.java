package funmatnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Conjunto{

//Estructura interna para guardar los id de los metaestados que se vayan identificando
private ArrayList<String> stateKeys;

//Estructura interna para guardar los metaestados de acuerdo a su nombre
private HashMap<String, Estado> states;

public String[] alfabeto = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

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

public String toStringFull(){
  StringBuilder res = new StringBuilder ();
  for (int i = 0; i < stateKeys.size() ; i++) {
    res.append(states.get(stateKeys.get(i)).toString() + "\n");
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

public String tablaParaSebas(){

  HashMap<String, String> hmrt = new HashMap();
  for (int i = 0; i < stateKeys.size() ; i++) {
    hmrt.put(stateKeys.get(i),alfabeto[i]);
  }

  StringBuilder s = new StringBuilder();
  for (int i = 0; i < stateKeys.size() ; i++) {
    Estado e = states.get(stateKeys.get(i));
    String x0 = e.getx0Meta();
    String x1 = e.getx1Meta();
    String x0A = "0";
    String x1A = "0";

    if(states.get(x0).edoAceptacion){
      x0A = "1";
    }

    if(states.get(x1).edoAceptacion){
      x1A = "1";
    }
    s.append(hmrt.get(x0) + "\t" + x0A + "\t" +  hmrt.get(x1) + "\t" + x1A + "\n");
  }
  return s.toString();
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
