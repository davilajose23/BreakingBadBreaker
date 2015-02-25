/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingbadbreaker;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 *
 * @author JoseFernando
 */
public class BreakingBadBreaker extends JFrame implements Runnable, KeyListener {

    
    private final int iMAXANCHO = 8; // maximo numero de personajes por ancho
    private final int iMAXALTO = 5;  // maxuimo numero de personajes por alto
    private final int iHeight = 700; // alto del JFrame
    private final int iWidth = 1000; // ancho del JFrame
    
    private Base basBall;         // Objeto Ball
    
    
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaDBImage;   // Imagen a proyectar en Applet	
    private Graphics graGrafica;  // Objeto grafico de la Imagen
    
    //private SoundClip scSonidoChimpy1;  // Objeto sonido de Chimpy 
    //private SoundClip scSonidoChimpy2;  //Objeto sonido de Chimpy
    
    
    // Se declaran las variables a usar
    private LinkedList<Base> lklMeth; //Lista de meths
    
    private Image imgMeth;
    private Image imgBall;
    
    
    
   /**
     * Constructor de la clase <code>JuegoJFrame</code>.
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>JFrame</code> y se definen funcionalidades.
     */
	public BreakingBadBreaker() {
        
       
        
        
        lklMeth = new LinkedList<Base>();   //Creo la lista de meth
        
        //la imagen de cada Meth
        imgMeth = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("walter.png"));
        
        // la imagen de ball
        imgBall = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("hank.png"));
        
        basBall = new Base(iWidth / 2, 3 * iHeight / 4 , 
                iWidth / 10 , iHeight / 7, imgBall);
        
        int iPosX  = 0;
        int iPosY = 0;
        
        for(int iI = 0; iI < iMAXANCHO * 3 ; iI++) {
            
         
            Base basMeth = new Base(iPosX, iPosY,
                     iWidth / iMAXANCHO, iHeight / iMAXALTO,imgMeth);
           
            iPosX += iWidth / iMAXANCHO;
            if(iPosX == iWidth){
                iPosX = 0;
                iPosY += iHeight / iMAXALTO;
            }
           
            
            lklMeth.add(basMeth);
        }
            
        //Hago que se active con teclado
        addKeyListener(this); 
        
        // Declaras un hilo
        Thread t = new Thread (this);
	// Empieza el hilo
	t.start ();
            
        }
    
    /** 
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
     * la posicion en x o y dependiendo de la direccion, finalmente 
     * se repinta el <code>JFrame</code> y luego manda a dormir el hilo.
     * 
     */
    @Override
    public void run() {
        /* mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */ 
        while (true) {
            actualiza();
            checaColision();
            repaint();
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
	}
    }
        
    
    
    /**
     * Metodo usado para actualizar la posicion de los objetos
     * 
     */
    public void actualiza(){
            
            
            
    }
    
    
    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton
     * con las orillas del <code>Applet</code>.
     */
    public void checaColision(){
        
        
        
        
        
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * @param graphics es el <code>objeto grafico</code> usado para dibujar.
     */
    
    public void paint(Graphics graGrafico) {
        
        // Inicializan el DoubleBuffer
        if (imaDBImage == null){
            imaDBImage = createImage (this.getSize().width, this.getSize().height);
            graGrafica = imaDBImage.getGraphics ();
	}
        
        Image imgFondo = Toolkit.getDefaultToolkit().getImage(this.getClass().
                getResource("fondo.png"));
        
        graGrafica.drawImage(imgFondo, 0,0,iWidth,iHeight, this);
        
        // Actualiza el Foreground.
        graGrafica.setColor (getForeground());
        paint1(graGrafica);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaDBImage, 0, 0, this);
        
        
        
    }
    
   /**
     * Metodo <I>paint1</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * @param graphics es el <code>objeto grafico</code> usado para dibujar.
     */
    
    public void paint1 (Graphics graDibujo){
        
        if (lklMeth != null && basBall != null) {
            
            //dibuja la ball
            basBall.paint(graDibujo, this);
            
            //Dibuja la imagen de los bloques
            for (Base basMeth:lklMeth) {
                
               
                basMeth.paint(graDibujo, this);
            }
        }
    }
    
    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
     * @param keyevent es el <code>evento</code> generado al presionar las teclas.
     */
    @Override
    public void keyPressed(KeyEvent keyevent) {
        
        
        repaint();
        
    }
    
    
    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
     * @param keyevent es el <code>evento</code> que se genera en al presionar las teclas.
     */
    
    @Override
    public void keyTyped(KeyEvent keyevent) {
        
    }
    
    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla presionada.
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    @Override
    public void keyReleased(KeyEvent keyevent) {
        
    }
    /**
     * Metodo que lee a informacion de un archivo.
     *
     * @throws IOException
     */
    public void leeArchivo() throws IOException{
    	
    }
	
    /**
     * Metodo que escribe en el archivo
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException{
    	
        
        
    }

    
    /**
     * Metodo principal
     *
     * @param args es un arreglo de tipo <code>String</code> de linea de comandos
     */
    public static void main(String[] args) {
        
    	// TODO code application logic here
    	BreakingBadBreaker bbbJuego = new BreakingBadBreaker();
    	bbbJuego.setSize(1000, 700); // crea la ventana de 800x500
    	bbbJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	bbbJuego.setVisible(true);

    }
    
    
    
}
