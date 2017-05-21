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


}
