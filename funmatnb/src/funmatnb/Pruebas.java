package funmatnb;

import java.util.ArrayList;


public class Pruebas {

    public static void main(String[] args) {

      ArrayList<String> al = new ArrayList();

      al.add("Hola");

      for (int i = 0; i < 10 ; i++) {
        al.add("a");
      }

      al.add("prueba");

      System.out.println("El normal: " + al.indexOf("Hola"));

      String h = "a";
      System.out.println("El compuesto: " + al.indexOf(h));

      System.out.println("El otro: " + al.indexOf("prueba"));

      System.out.println("El otro: " + al.indexOf("oo"));



    }


}
