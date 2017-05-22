package funmatnb;

import java.util.ArrayList;
import java.util.Arrays;


public class Pruebas {

    public static void main(String[] args) {

      Conjunto c = new Conjunto();
      String s = "AB";
      Estado e = new Estado(s);
      c.add(e);

      System.out.println(c.contains(e));
      System.out.println(c.contains(new Estado("BA")));
      System.out.println(c.contains(new Estado("ABCC")));

      c.add(new Estado("BBDDCA"));

      System.out.println(c.toString());

      System.out.println("-------");

      Estado e1 = c.getState(c.getKeys().get(0));


      //e1.setEstadosDet("ABC", "D");

      e1.x0destino.add("A");
      e1.x0destino.add("B");
      e1.x0destino.add("C");

      e1.x1destino.add("D");
      e1.x1destino.add("T");


      System.out.println(e1.toStringMeta());
      System.out.println(e1.toString());

      System.out.println("----------Probando eliminacion de estados vacios");

      String a = "-a";

      System.out.println(a);
      System.out.println(eliminaEstadosVacios(a));




    }

    //Método para regresar un String ordenado de manera lexicografica.
    public static String ordenarLex(String s){
      char[] chars = s.toCharArray();
      Arrays.sort(chars);
      String sorted = new String(chars);
      return sorted;
    }

    public static String eliminaRepeticiones(String s){
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

    public static String eliminaEstadosVacios(String s){
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
