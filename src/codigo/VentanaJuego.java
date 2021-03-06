/*
 * Autor: Marta Márquez Olalla

ejercicio creado para explicar los siguientes conceptos;
- Hilos de ejecución paralela.
- Arraylist.
 */
package codigo;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author Thomas
 */
public class VentanaJuego extends javax.swing.JFrame {
    //Vamos a declarar dos constantes. Las constantes se ponen en mayúsculas.
    static int ANCHOPANTALLA=600;
    static int ALTOPANTALLA=600;
    //Cuantos marcianos van a salir en la pantalla.
    int filaMarcianos=5;
    int columnaMarcianos=10;
    
    BufferedImage buffer=null;
    int contador=0;
    //Declaramos la nave.
    Nave miNave = new Nave(ANCHOPANTALLA);
    //Declaramos el disparo.
    Disparo miDisparo=new Disparo(ALTOPANTALLA);
    //Array de dos dimensiones que guarda la lista de marcianos.
    Marciano[][] listaMarcianos= new Marciano[filaMarcianos][columnaMarcianos];
    //La dirección en la que se mueve el grupo de marcianos.
    boolean direccionMarcianos=false;
    //Declaramos una variable de tipo imagen para cargar todas las imágenes del juego en un único archivo y luego usar las imágenes
    //usando coordenadas dentro de ese archivo.
    BufferedImage plantilla=null;
    Image[] imagenes=new Image[30]; 
    
    
    //bucle de animación del juego. En este caso es un hilo de ejecución nuevo que se encarga de refrescar el contenido de la pantalla.
    Timer temporizador= new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Código de la animación. Llamamos a un método.
            bucleDelJuego();
        }
    });
    //10 son los milisegundos que va a tardar en ejecutarse la siguiente vez.
    //El ActionListener está en busca de cambios. Si encuentra un cambio modifica la pantalla.
    
    public VentanaJuego() {
        initComponents();
        //Inicializamos la plantilla con todos los sprites de los marcianos.
        try{           
        plantilla=ImageIO.read(getClass().getResource("/codigo/Imagenes/invaders2.png"));
      }
      catch(IOException e){}
        
        //Guardo cada sprite en un image individual. De esta forma es más fácil dibujarlos dependiendo de lo que se necesite.
        for(int i=0;i<5;i++){
          for(int j=0;j<4;j++){
              //Estamos pasando un array de 2 dimensiones a lineal, por eso el i*5+j.
              //Corto el trozo de 64-64 que corresponde a cada marciano.
            imagenes[i*4+j]=plantilla.getSubimage(j*64, i*64, 64, 64); //64 son los píxeles, las medidas de cada imagen.
            //Redimensionamos la imagen para que adopte las medidas de 32X32.
            imagenes[i*4 + j] = imagenes[i*4 + j].getScaledInstance(32, 32, Image.SCALE_SMOOTH);
          }
        }
        
         //Usamos la imagen de la plantilla en la nave.
        imagenes[21]=plantilla.getSubimage(64+1,320, 64, 32);
       
        
        setSize(ANCHOPANTALLA,ALTOPANTALLA );
        buffer=(BufferedImage) jPanel1.createImage(ANCHOPANTALLA,ALTOPANTALLA);
        buffer.createGraphics();
        //Inicializamos la nave.
        miNave.imagen=imagenes[21];
        //Inicializamos la nave. Las x y las y son las que hemos declarado en la clase Nave.
        miNave.x=ANCHOPANTALLA/2-miNave.imagen.getWidth(this)/2;
        miNave.y=ALTOPANTALLA-miNave.imagen.getHeight(this)-40;
        
        
        //Inicializamos el array de marcianos con un bucle for anidado.  
        for(int i=0; i<filaMarcianos;i++){
          for(int j=0;j<columnaMarcianos;j++){
              /*Declaramos los marcianos. Los marcianos no son tipos primitivos, son objetos, y estos hay que inicializarlos
              independientemente. Los tipos primitivos, como los array, int o chars, ´Java sí los inicializa por sí mismo, pero
              los objetos no.*/
             listaMarcianos[i][j]=new Marciano(ANCHOPANTALLA);
             //En vez de crear los marcianos en la clase marciano vamos a crearlos aquí (las imágenes) usando la plantilla.
              listaMarcianos[i][j].imagen=imagenes[2*i];
              listaMarcianos[i][j].imagen2=imagenes[2*i+1];
              //Colocamos los marcianos en su respectiva posición.
              listaMarcianos[i][j].x=j*(15+listaMarcianos[i][j].imagen.getWidth(null));
              listaMarcianos[i][j].y=i*(10+listaMarcianos[i][j].imagen.getHeight(null));
          }
        }
        
        //Hay que inicializar el temporizador.
        temporizador.start();
        
    }
    
    private void pintaMarcianos(Graphics2D g2){
       
        for (int i = 0; i < filaMarcianos; i++) {
            for (int j = 0; j < columnaMarcianos; j++) {
                if (listaMarcianos[i][j].vida == true) {
                    //vamos a mover los marcianos. El booleano es para saber en qué dirección se mueve el grupo de marcianos.
                    listaMarcianos[i][j].mueve(direccionMarcianos);

                    //Pintamos el marciano.
                    if (contador < 50) {
                        g2.drawImage(listaMarcianos[i][j].imagen, listaMarcianos[i][j].x, listaMarcianos[i][j].y, null);
                    } else if (contador < 100) {
                        g2.drawImage(listaMarcianos[i][j].imagen2, listaMarcianos[i][j].x, listaMarcianos[i][j].y, null);
                    } else {
                        contador = 0;
                    }
                    //Aquí hacemos comprobaciones del booleano para que el marciano cambie de dirección.
                    if (listaMarcianos[i][j].x == ANCHOPANTALLA - listaMarcianos[i][j].imagen.getWidth(null) || listaMarcianos[i][j].x == 0) {
                        direccionMarcianos = !direccionMarcianos;
                        //Esta parte hace que baje. Usamos un bucle for anidado para que baje todo el grupo.
                        for (int k = 0; k < filaMarcianos; k++) {
                            for (int m = 0; m < columnaMarcianos; m++) {
                                listaMarcianos[k][m].y += listaMarcianos[i][j].imagen.getHeight(null);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void chequeaColision(){
      //Vamos a crear dos rectángulos que cuando entran en contacto hagan que el marciano desaparezca.
      Rectangle2D.Double rectanguloMarciano=new Rectangle2D.Double();
      Rectangle2D.Double rectanguloDisparo=new Rectangle2D.Double();
      //setFrame le va a dar un marco a ese rectángulo.
      rectanguloDisparo.setFrame(miDisparo.getX(),miDisparo.getY(),miDisparo.imagen.getWidth(null), miDisparo.imagen.getHeight(null));
      
        
        for (int i = 0; i < filaMarcianos; i++) {
            for (int j = 0; j < columnaMarcianos; j++) {
                if (listaMarcianos[i][j].vida == true) {
                    //Reposicionamos el rectángulo del marciano en cada uno de los elementos del array.
                    rectanguloMarciano.setFrame(listaMarcianos[i][j].x, listaMarcianos[i][j].y,
                            listaMarcianos[i][j].imagen.getWidth(null), listaMarcianos[i][j].imagen.getHeight(null));
                    if (rectanguloDisparo.intersects(rectanguloMarciano)) {
                        //Si da true es que los rectángulos han chocado en algún punto.
                        //Recolocamos al marciano y al disparo muy por debajo de la pantalla. La función booleana de disparo la ponemos a false.
                        listaMarcianos[i][j].vida = false;
                        miDisparo.setY(2000);
                        miDisparo.setDisparado(false);
                    }
                }
            }
        }
    }
    
    private void bucleDelJuego(){
        //El bucle de animación gobierna el redibujado de los objetos en el jPanel1.
        Graphics2D g2= (Graphics2D) buffer.getGraphics();
        g2.setColor(Color.black);
        g2.fillRect(0,0,ANCHOPANTALLA,ALTOPANTALLA);
        /////////////////////////////////////////////////////////////////////////
           //redibujamos cada elemento en su nueva posición en el buffer.
        
        ////////////////////////////////////////////////////////////////////////
        

        contador++;
        //Llamamos a la función de movimiento en la clase Nave.
        miNave.mueve();        
        //Movimiento del disparo.
        if(miDisparo.isDisparado()){
          miDisparo.mueve();
        }
        //pinto el disparo.
        g2.drawImage(miDisparo.imagen,miDisparo.getX(),miDisparo.getY(),null);
        //Pinto la nave.
        g2.drawImage(miNave.imagen,miNave.x,miNave.y, null);
        
        pintaMarcianos(g2);
        
        chequeaColision();
        
        //Dibujo de golpe el buffer sobre el jPanel1.
        g2=(Graphics2D) jPanel1.getGraphics();
        g2.drawImage(buffer, 0,0, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 756, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        //Cada una de las teclas tiene un código. VK_LEFT/RIGHT son las flechas de movimiento.
        switch(evt.getKeyCode()){
            case KeyEvent.VK_LEFT : miNave.setPulsadoIzquierda(true); break;
            case KeyEvent.VK_RIGHT : miNave.setPulsadoDerecha(true); break;
            case KeyEvent.VK_SPACE : miDisparo.setDisparado(true); 
                                     miDisparo.posicionaDisparo(miNave); break;
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        switch(evt.getKeyCode()){
            case KeyEvent.VK_LEFT : miNave.setPulsadoIzquierda(false); break;
            case KeyEvent.VK_RIGHT : miNave.setPulsadoDerecha(false); break;
            
        }
    }//GEN-LAST:event_formKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
