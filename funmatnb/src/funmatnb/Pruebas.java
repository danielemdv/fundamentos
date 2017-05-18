package funmatnb;

import java.util.ArrayList;
import java.util.Arrays;


public class Pruebas {

    public static void main(String[] args) {

      String s = "CBA";

      s = ordenarLex(s);

      System.out.println(s);


    }

    //Método para regresar un String ordenado de manera lexicografica.
    public static String ordenarLex(String s){
      char[] chars = s.toCharArray();
      Arrays.sort(chars);
      String sorted = new String(chars);
      return sorted;
    }


}
