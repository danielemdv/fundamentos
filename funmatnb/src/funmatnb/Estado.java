package funmatnb;

import java.util.ArrayList;
import java.util.Arrays;

public class Estado{

public String id;
public boolean edoAceptacion;
public ArrayList<String> x0destino;
public ArrayList<String> x1destino;

//Constructor mas simple (ideologia de ir armando el estado poco a poco)
public Estado(String id){
  this.id = id;
  edoAceptacion = false;
  x0destino = new ArrayList();
  x1destino = new ArrayList();
}

//Constructor para utilizar cuando estamos armando estados deterministas (i.e. destinos de 1 solo estado)
public Estado(String id, String x0, String x1){
  this.id = id;
  edoAceptacion = false;
  x0destino = new ArrayList();
  x1destino = new ArrayList();

  x0destino.add(x0);
  x1destino.add(x1);
}


//chance no se acaba usando este constructor. Lo dejo porque pueque
public Estado(String id,boolean edoAceptacion, ArrayList<String> x0destino, ArrayList<String> x1destino){
  this.id = id;
  this.edoAceptacion = edoAceptacion;
  this.x0destino = x0destino;
  this.x1destino = x1destino;
}

public String toString(){
  return "id: " + id + ", edoAceptacion: " + edoAceptacion + ", x0destino: " + x0destino + ", x1destino: " + x1destino;
}

//Metodo para agregar solo un estado a los arrayLists de estados a los que puede hacer transicion
public void setEstadosDet(String x0, String x1){
  //borron de los estados anteriores, solamente queremos los agregados
  x0destino = new ArrayList();
  x1destino = new ArrayList();
  x0destino.add(x0);
  x1destino.add(x1);
}


//Este par de funciones regresan un string concatenado (de manera que Palo dice que no hay que hacerlo)
//de los estados de transicion conglomerados y ordenados lexicograficamente.
public String getx0Meta(){
  String x0 = "";
  for (int i = 0; i < x0destino.size(); i++) {
    x0 += x0destino.get(i);
  }
  x0 = ordenarLex(x0);
  return x0;
}

public String getx1Meta(){
  String x1 = "";
  for (int i = 0; i < x1destino.size(); i++) {
    x1 += x1destino.get(i);
  }
  x1 = ordenarLex(x1);
  return x1;
}

//Método para regresar un String ordenado de manera lexicografica.
public String ordenarLex(String s){
  char[] chars = s.toCharArray();
  Arrays.sort(chars);
  String sorted = new String(chars);
  return sorted;
}


}
