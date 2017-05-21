package funmatnb;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;


public class Main{

//Alfabeto para poder ir asignando letras a los estados conforme los vayamos leyendo
//La razon por la cual usamos strings es porque son mucho mas manejables que chars en java
//(no uso acentos al programar...)
public static String[] alfabeto = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};



public static void main(String[] args){

  //ArrayList de arreglo de strings, para ir guardando lo que leemos, pues tendremos que releerlo.
  ArrayList<Object[]> alestados = new ArrayList();

  //HashMap (diccionario, hashtable, whatever...) de String a objeto Estad, para poder
  //Acceder directo al objeto estado correspondiente a la letra de su id.s
  HashMap<String, Estado> mapestados = new HashMap();

  System.out.println("Leyendo datos:");

  //bloque try catch porque java es fan de las ioExceptions (can't blame them tho, they DO suck)
  try{

    //File file = new File ("/Users/danielespinosa/gitsome/fmnb/funmatnb/src/data/ejemplo.txt");
      File file = new File ("/Users/danielespinosa/gitsome/fmnb/funmatnb/src/data/ejemploPaloDomingo.txt");
    //File file = new File("C:/Users/Paloma/Desktop/ITAM/6to Semestre/FMATC/funmat/data/ejemplo.txt"); //Esta ruta debe ser modificada para windows, pues es estilo unix
    Scanner fileScanner = new Scanner(file); //Scanner para el archivo
    Scanner scanLine; //Scanner para las lineas individuales
    Scanner scanStates; //Scanner para los estados

    //mientras haya lineas en el archivo
    while(fileScanner.hasNext()){
      scanLine = new Scanner(fileScanner.nextLine());
      scanLine.useDelimiter("\t");

      scanStates = new Scanner(scanLine.next());
      scanStates.useDelimiter(",");



      ArrayList<String> estados0 = new ArrayList(); //Son los múltiples estados que puede tener (A,B) 1 a n
      ArrayList<String> estados1 = new ArrayList();

      //leer estados0
      while(scanStates.hasNext()){
        estados0.add(scanStates.next());
      }

      //leer el tipo de estados0
      String transicion0 = scanLine.next();

      scanStates = new Scanner(scanLine.next()); //Reinicializo en ScanStates para leer los siguientes estados que igual pueden ser multiples.
      scanStates.useDelimiter(",");

      //leer estados1
      while(scanStates.hasNext()){
        estados1.add(scanStates.next());
      }

      String transicion1 = scanLine.next();

      System.out.println(estados0 + " " + transicion0 + " " + estados1 + " " + transicion1);

      //Arreglo de tipo Object para poder pasarle objetos de diferente tipo, hay que desempaquetarlo bien, con cuidado.
      Object[] arrEstado = {estados0, transicion0, estados1, transicion1};
      alestados.add(arrEstado);

    }

  }
  catch(Exception e){
    //Rete mal manejo de excepcion pero que flojera hacerlo bien
    System.out.println("Algun tipo de exception estilo ioException sucedio...: " + e.getMessage());

  }

  //imprimpiendo los estados para checar
  for (int i = 0; i < alestados.size() ; i++) {
    for (int j = 0; j < 4 ; j++) {
      System.out.print(alestados.get(i)[j]);
    }
    System.out.println();
  }

  //Ir creando objetos estado con los estados a los que pueden transicionar.
  for (int i = 0;i < alestados.size() ;i++ ) {
    Estado e = new Estado(alfabeto[i]);
    e.x0destino = (ArrayList)alestados.get(i)[0];
    e.x1destino = (ArrayList)alestados.get(i)[2];

    mapestados.put(e.id,e); //Agregar el estado al HashMap
  }

  /*Ir checando los estados que son de aceptacion.
    En caso de que uno sea, con el HashMap lo encontramos
    y vamos y le decimos que SI es de aceptacion
  */
  for (int i = 0;i < alestados.size();i++) {
    if(alestados.get(i)[1].equals("1")){
      //Iterar para cada estado que encuentre en cada lista
      for (int j = 0; j < ((ArrayList)alestados.get(i)[0]).size(); j++) {
        mapestados.get(((ArrayList)alestados.get(i)[0]).get(j)).edoAceptacion = true;
      }
    }

    if(alestados.get(i)[3].equals("1")){
      //Iterar para cada estado que encuentre en cada lista
      for (int j = 0; j < ((ArrayList)alestados.get(i)[2]).size(); j++) {
        mapestados.get(((ArrayList)alestados.get(i)[2]).get(j)).edoAceptacion = true;
      }
    }

  }

  //checando que todo bien....
  for (int i = 0; i < alestados.size() ;i++ ) {
    System.out.println(mapestados.get(alfabeto[i]).toString());
  }

  Automata a = new Automata(mapestados);

  //Seccion de pruebas para ver como vamos
  a.pruebaMeta();





}




}
