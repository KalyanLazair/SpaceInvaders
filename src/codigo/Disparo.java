/*
 * La clase que sirve para dibujar los disparos de la nave en la pantalla.
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Marta Márquez Olalla
 */
public class Disparo {
    public Image imagen=null;
    private int x=0;
    private int y=0;
    
    private int altoPantalla;
    //Comprobamos si el disparo se ha disparado.
    private boolean disparado=false;
    
    public Disparo(int _altoPantalla){
       try{
         imagen=ImageIO.read(getClass().getResource("/codigo/Imagenes/disparo.png"));
         _altoPantalla=altoPantalla;
       }
       catch(IOException e){}
        
        
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDisparado() {
        return disparado;
    }

    public void setDisparado(boolean disparado) {
        this.disparado = disparado;
    }
    
     public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public void mueve(){
     if(y>0){
       y--;
       y--;
       y--;
     }
    }
    
    public void posicionaDisparo(Nave _nave){
        //La x del disparo es igual a la posición x de la nave + ancho de la nave dividido entre 2 menos el ancho del disparo.
        x=_nave.x+_nave.imagen.getWidth(null)/2 - imagen.getWidth(null)/2;
        y=_nave.y;
    }
    
}
