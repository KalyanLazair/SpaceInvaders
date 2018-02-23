/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Thomas
 */
public class Nave {
    
    public Image imagen=null;
    //Coordenadas de la nave.
    public int x=0;
    public int y=0;
    //Declaramos dos variables booleanes para evitar el retardo del teclado.
    private boolean pulsadoIzquierda=false;
    private boolean pulsadoDerecha=false;
    
    //Bean; Es una propiedad de una clase. Estas variables son privadas pero podemos acceder a ellas desde fuera con una propiedad
    //que son un Set y un Get. Para modificar la variable hay que usar un Set y para leer desde fuera hay que usar un Get.
    //Por ejemplo; miNave.set(x) o miNave.setPulsadoIzquierda(true); Ponemos el Pulsado en mayúsculas (la primera letra).
    
    /*Para poder aprovechar el código tendríamos que pasarle el ancho de la pantalla directamente en esta clase. No obstante
    también podemos llamar desde la clase principal al ancho de la pantalla con VentanaPrincipal.ANCHOPANTALLA.*/
    
    private int anchoPantalla;
    
    public Nave(int _anchoPantalla){
      try{
        imagen=ImageIO.read(getClass().getResource("/codigo/Imagenes/nave.png"));
      }
      catch(IOException e){}
      anchoPantalla=_anchoPantalla;
    }
//Todas las variables son get por defecto menos los booleanos, que son is. NetBeans nos crea automáticamente el código cuando
    //Le damos click derecho sobre la variable, get code y getter&setter.
    public boolean isPulsadoIzquierda() {
        return pulsadoIzquierda;
    }

    public void setPulsadoIzquierda(boolean pulsadoIzquierda) {
        this.pulsadoIzquierda = pulsadoIzquierda;
    }

    public boolean isPulsadoDerecha() {
        return pulsadoDerecha;
    }

    public void setPulsadoDerecha(boolean pulsadoDerecha) {
        this.pulsadoDerecha = pulsadoDerecha;
    }
    //Método para mover la nave. Si pulsado a la izquierda está a true, se mueve uno. Si pulsado a la derecha es true, se mueve uno.
    public void mueve(){
        if(pulsadoIzquierda && x>0){
          x--;
          x--;
          x--;
        }
        if(pulsadoDerecha && x<anchoPantalla-imagen.getWidth(null)){
          x++;
          x++;
          x++;
        }
    }
}
