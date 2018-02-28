/*
 * La clase para guardar los marcianos.
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * Autor; Marta Márquez Olalla
 */
public class Marciano {
    
    //Declaramos las imágenes de los marcianos. Una para cada marciano porque de cada tipo de marciano tiene dos estados distintos.
    public Image imagen=null;
    public Image imagen2=null;
    //Coordenadas de la nave.
    public int x=0;
    public int y=0;
    
    
    //Bean; Es una propiedad de una clase. Estas variables son privadas pero podemos acceder a ellas desde fuera con una propiedad
    //que son un Set y un Get. Para modificar la variable hay que usar un Set y para leer desde fuera hay que usar un Get.
    //Por ejemplo; miNave.set(x) o miNave.setPulsadoIzquierda(true); Ponemos el Pulsado en mayúsculas (la primera letra).
    
    /*Para poder aprovechar el código tendríamos que pasarle el ancho de la pantalla directamente en esta clase. No obstante
    también podemos llamar desde la clase principal al ancho de la pantalla con VentanaPrincipal.ANCHOPANTALLA.*/
    
    private int anchoPantalla;   
    
    
    public Marciano(int _anchoPantalla){
      try{
        imagen=ImageIO.read(getClass().getResource("/codigo/Imagenes/marcianito1.png"));
        imagen2=ImageIO.read(getClass().getResource("/codigo/Imagenes/marcianito2.png"));
      }
      catch(IOException e){}
      anchoPantalla=_anchoPantalla;
    }

    //Método para mover la nave. Si pulsado a la izquierda está a true, se mueve uno. Si pulsado a la derecha es true, se mueve uno.
    public void mueve(boolean direccion){
       if(direccion==true){
        if(x<anchoPantalla-imagen.getWidth(null)){
          x++;
        }
       }else{
        if(x>0){
          x--;
        }
       }
        
    }
}
