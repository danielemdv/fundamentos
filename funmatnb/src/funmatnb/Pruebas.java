package funmatnb;

import java.util.ArrayList;
import java.util.Arrays;


public class Pruebas {

    public static void main(String[] args) {

      String s = "CBAAAFFFRRTOOIUFIABBBCCD";

      s = ordenarLex(s);
      System.out.println("Ordenado : " + s);

      s = eliminaRepeticiones(s);

      System.out.println("Sin repeticiones: " + s);


    }

    //Método para regresar un String ordenado de manera lexicografica.
    public static String ordenarLex(String s){
      char[] chars = s.toCharArray();
      Arrays.sort(chars);
      String sorted = new String(chars);
      return sorted;
    }

    public static String eliminaRepeticiones(String s){
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
