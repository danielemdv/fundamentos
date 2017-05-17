package funmatnb;

public class Estado{

public String id;
public boolean edoAceptacion;
public String x0destino;
public String x1destino;

//Constructor mas simple (ideologia de ir armando el estado poco a poco)
public Estado(String id){
  this.id = id;
  edoAceptacion = false;
}


//chance no se acaba usando este constructor. Lo dejo porque pueque
public Estado(String id,boolean edoAceptacion, String x0destino, String x1destino){
  this.id = id;
  this.edoAceptacion = edoAceptacion;
  this.x0destino = x0destino;
  this.x1destino = x1destino;
}

public String toString(){
  return "id: " + id + ", edoAceptacion: " + edoAceptacion + ", x0destino: " + x0destino + ", x1destino: " + x1destino;
}


}
