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

    
    private final int iMAXANCHO = 10; // maximo numero de personajes por ancho
    private final int iMAXALTO = 7;  // maxuimo numero de personajes por alto
    private final int iHeight = 700; // alto del JFrame
    private final int iWidth = 1000; // ancho del JFrame
    
    private Base basBall;         // Objeto Ball
    private Base basBarra;         // Objeto Barra
    
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaDBImage;   // Imagen a proyectar en Applet	
    private Graphics graGrafica;  // Objeto grafico de la Imagen
    
    //private SoundClip scSonidoChimpy1;  // Objeto sonido de Chimpy 
    //private SoundClip scSonidoChimpy2;  //Objeto sonido de Chimpy
    
    
    // Se declaran las variables a usar
    private LinkedList<Base> lklBlock; //Lista de meths
    private LinkedList<Base> lklTruck; // Lista de trucks
    
    private Image imgBlock;
    private Image imgBall;
    private Image imgBarra;
    private Image imgTruck;
    
    private Image imgBlock1;
    private Image imgBlock2;
    private Image imgBlock3;
    
    private int iCantBloques;
    private int iVidas;
    private int iScore;
    private boolean bPause;
    private int iPosXBall;
    private int iPosYBall;
    private int iDireccionX;
    private int iDireccionY;
    private int iTecla;
    private int iPosXBarra;
    private int iPosYBarra;
    private int iNivel;
    
    private Animacion aniHank;
    private long lTiempo;
    
   /**
     * Constructor de la clase <code>JuegoJFrame</code>.
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>JFrame</code> y se definen funcionalidades.
     */
	public BreakingBadBreaker() {
        
       
        iTecla = 0;
        iDireccionX = 0;
        iDireccionY = -5;
        iVidas = 5;
        iScore = 0;
        bPause = false;
        iNivel = 6;
        
        lklBlock = new LinkedList<Base>();   //Creo la lista de meth 
        lklTruck = new LinkedList<Base>();   // creo la lista de trucks
        
        //la imagen de cada Block
        
        imgBlock1 = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("walter1.png"));
        
        imgBlock2 = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("walter2.png"));
        
        imgBlock3 = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("walter3.png"));
        
        //la imagen de cada Truck
        imgTruck = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("truck.png"));
        
        // la imagen de ball
        imgBall = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("hank.png"));
        // la imagen de la barra
        imgBarra = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("barra.png"));
        
        
        basBall = new Base(iWidth / 2, 3 * iHeight / 4 , 
             //   iWidth / 10 , iHeight / 7, imgBall);
                 iWidth / 15, iHeight / 12, imgBall);
        
        basBall.setX(basBall.getX() - basBall.getAncho() / 2);
        
        basBarra = new Base(iWidth / 2, 9 * iHeight / 10 , 
                iWidth / 3 , iHeight / 10, imgBarra);
        
        basBarra.setX(basBarra.getX() - basBarra.getAncho() / 2);
        
        //Creo animacion
        aniHank = new Animacion();
        long lDuracion = 125;
        aniHank.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/HankA.png")), lDuracion);
        aniHank.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/HankB.png")), lDuracion);
        aniHank.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/HankC.png")), lDuracion);
        aniHank.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/HankD.png")), lDuracion);
        aniHank.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/HankE.png")), lDuracion);
        aniHank.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/HankF.png")), lDuracion);
        aniHank.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/HankG.png")), lDuracion);
        aniHank.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/HankH.png")), lDuracion);
        
        int iPosX  = 0;
        int iPosY = 80;
        
        iCantBloques = iMAXANCHO * 3  ;
        
        for(int iI = 0; iI < iCantBloques; iI++) {
            
            int iRandom = (int) (Math.random() * 3 + 1);
            Base basBlock = new Base(iPosX, iPosY,
                     iWidth / iMAXANCHO, iHeight / iMAXALTO,imgBlock);
            
            if (iRandom == 1){
                basBlock.setImagen(imgBlock1);
            }
            else if (iRandom == 2){
                basBlock.setImagen(imgBlock2);
            }
            else if (iRandom == 3){
                basBlock.setImagen(imgBlock3);
            }
           
            iPosX += iWidth / iMAXANCHO;
            if(iPosX == iWidth){
                iPosX = 0;
                iPosY += iHeight / iMAXALTO;
            }
           
            
            lklBlock.add(basBlock);
        }
         
        iPosX = 100;
        for(int iI = 0; iI < iVidas; iI++) {
            
         
            Base basTruck = new Base(iPosX, 30,
                     iWidth / 12 , iHeight / 13,imgTruck);
           
            iPosX += iWidth / 12;

            lklTruck.add(basTruck);
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
        lTiempo = System.currentTimeMillis();
        while (true){
           
            if(!bPause && iCantBloques > 0 && iVidas > 0){
                actualiza();
                checaColision();
                repaint();
            }
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
        
        long lTiempoTrans = System.currentTimeMillis() - lTiempo;
        lTiempo = System.currentTimeMillis();
        aniHank.actualiza(lTiempoTrans);
        
        if(iTecla == 1){
            basBarra.setX(basBarra.getX() - 10);
        }
        if(iTecla == 2){
            basBarra.setX(basBarra.getX() + 10);
        }
        
        basBall.setX(basBall.getX() +  iDireccionX );
        basBall.setY(basBall.getY() +  iDireccionY + 
                (( iNivel * iDireccionY)/5));
        
        
        for(Base basTruck:lklTruck) {
        
            basTruck.setX(basTruck.getX() + 3);
            
            
        }
        
            
    }
    
    
    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton
     * con las orillas del <code>Applet</code>.
     */
    public void checaColision(){
        
        // checa cuando la barra se sale por la izq
        if(basBarra.getX() < 0){
            basBarra.setX(0);
        }
        // checa cuando la barra se sale por la derecha
        if(basBarra.getX() + basBarra.getAncho() > iWidth){
            basBarra.setX(iWidth - basBarra.getAncho());
        }
        
        
        if(basBall.getX() < 0 ){    //Ball Se sale por la izquierda
            iDireccionX *= -1;
            basBall.setX(0);
        }
        
        //Ball Se sale por la derecha
        if(basBall.getX() + basBall.getAncho() > iWidth){
            iDireccionX *= -1;
            basBall.setX(iWidth - basBall.getAncho());
        }
        //Ball se sale por arriba
        if(basBall.getY() < 80  ){
            iDireccionY *= -1;
            basBall.setY(80);
        }
        if( basBall.getY() + basBall.getAlto() > iHeight){
            
            // vuelve a aparecer la bola
            basBall.setX(iWidth / 2 - basBall.getAncho() / 2);
            basBall.setY(3 * iHeight /4 );
            iDireccionX = 0;
            iDireccionY = -5;
            
            basBarra.setX(iWidth / 2 - basBarra.getAncho() /2);

            iVidas--;
            // 1 vida menos
            if (iVidas == 0){
                iNivel = 1;
            }
            //Hago que desaparezca unade las vidas
            lklTruck.pop();
        }
        
        boolean bChoco = false;
        for(Base basBlock:lklBlock) {
           
            
            if(basBlock.intersecta(basBall)){
                
                int iAuxDirY = iDireccionY + (iNivel * iDireccionY / 5);
                // intersecta por abajo o por arriba
                if(basBlock.intersectapor(basBall, iDireccionX, iAuxDirY) == 1){
                    if (basBlock.getY() < basBall.getY()){
                        basBall.setY(basBlock.getY() + basBlock.getAlto());
                    }
                    else {
                        basBall.setY(basBlock.getY() - basBall.getAlto());                    
                    }
                    if (!bChoco){
                        iDireccionY *= -1;
                        bChoco = true;
                    }
                }
                // intersecta por izquierda o derecha
                else if(basBlock.intersectapor(basBall, iDireccionX, iAuxDirY) == 2){
                    
                    if(!bChoco){
                        iDireccionX *= -1;
                        bChoco = true;
                    }
                }
                
                if (basBlock.getImagen() == imgBlock1){
                    basBlock.setX(basBlock.getX()-iWidth);
                    iCantBloques--;
                    iScore += 10;
                }
                else if (basBlock.getImagen() == imgBlock2){
                    basBlock.setImagen(imgBlock1);
                    iScore += 20;
                }
                else if (basBlock.getImagen() == imgBlock3){
                    basBlock.setImagen(imgBlock2);
                    iScore += 30;
                }
                
                if(iCantBloques == 0){
                    iNivel++;
                    int iAux = iScore;
                    int iAux2 = iVidas;
                    vuelveAEmpezar();
                    iScore = iAux;
                    iVidas = iAux2;
                    int iPosX = 100;
                    while (!lklTruck.isEmpty()){
                        lklTruck.pop();
                    }
                    for(int iI = 0; iI < iVidas; iI++) {
                        Base basTruck = new Base(iPosX, 30,
                                 iWidth / 12 , iHeight / 13,imgTruck);

                        iPosX += iWidth / 12;

                        lklTruck.add(basTruck);
                    }
                }
                
            }
                
          
           
        }
        
        
        if(basBarra.intersecta(basBall)){
            
            
            
            int iPosX1 = ((basBall.getX() + basBall.getAncho() / 2  ) - 
                    (basBarra.getX() + basBarra.getAncho() / 2));
            
            iPosX1 *= 10;
            double iPorcentaje = 
                    ( iPosX1 / (basBarra.getAncho()-basBall.getAncho() ) );
                    
         
            iDireccionX = (int) (iPorcentaje);
            iDireccionY *= -1; 
            
            basBall.setY(basBarra.getY() - basBall.getAlto());
        }
        
        for(Base basTruck:lklTruck) {
        
            if(basTruck.getX() > 600){
                basTruck.setX(30);
            }
            
        }
        
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
        
        if (lklBlock != null && basBall != null && basBarra != null
                && lklTruck != null) {
            
            //Dibuja la imagen de las trucks
            for (Base basTruck:lklTruck) {

                basTruck.paint(graDibujo, this);
            }

            
            graDibujo.setColor(Color.white); //Escribo en color blanco
            
            Image imgFondo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().
                getResource("fondo1.png"));
            
            graDibujo.drawImage(imgFondo1, 0,0,iWidth,iHeight, this);
           
            graDibujo.drawLine(0, 80, iWidth, 80);
            
            //dibuja la ball
            graDibujo.drawImage(aniHank.getImagen(),basBall.getX(),
                    basBall.getY(),iWidth/15, iHeight/12, this);
            //dibuja la barra
            basBarra.paint(graDibujo, this);
            
            //Dibuja la imagen de los bloques
            for (Base basBlock:lklBlock) {
                
               
                basBlock.paint(graDibujo, this);
            }
        }
        else {
                //Da un mensaje mientras se carga el dibujo	
                graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
        
        graDibujo.setColor(Color.white); //Escribo en color rojo
        graDibujo.drawString("Vidas: ", 10, 60);   //Escribo vidas
        graDibujo.drawString("Puntos: " + iScore, iWidth - 250, 60);  // escribo score
        graDibujo.setFont(graDibujo.getFont().deriveFont(30.0f));
    }
    
    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
     * @param keyEvent es el <code>evento</code> generado al presionar las teclas.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        
        
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) { 
            iTecla = 2;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) { 
            iTecla = 1;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_P){
            
            bPause = !bPause;
            
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            if (iVidas == 0 || iCantBloques == 0){
                vuelveAEmpezar();
            }
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
            iVidas = 0;
            iCantBloques = 0;
            for (Base basBloque : lklBlock){
                if (basBloque.getX() >= 0){
                    basBloque.setX(basBloque.getX() - iWidth);
                }
            }
            while (!lklTruck.isEmpty()){
                lklTruck.pop();
            }
        }
        
        repaint();
        
    }
    
    
    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
     * @param keyEvent es el <code>evento</code> que se genera en al presionar las teclas.
     */
    
    @Override
    public void keyTyped(KeyEvent keyEvent) {
        
    }
    
    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla presionada.
     * @param keyEvent es el <code>evento</code> que se genera en al soltar las teclas.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        
        iTecla = 0;
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
    
    public void vuelveAEmpezar(){
        while (!lklTruck.isEmpty()){
                lklTruck.pop();
        }
        iVidas = 5;
        iScore = 0;
        for (Base basBlock : lklBlock){
            int iRandom = (int) (Math.random() * 3 + 1);
            if(basBlock.getX() < 0){
                basBlock.setX(basBlock.getX() + iWidth);
            }
            if (iRandom == 1){
                basBlock.setImagen(imgBlock1);
            }
            else if (iRandom == 2){
                basBlock.setImagen(imgBlock2);
            }
            else if (iRandom == 3){
                basBlock.setImagen(imgBlock3);
            }
        }
        iCantBloques = 30;
        basBall.setX(iWidth / 2 - basBall.getAncho() / 2);
        basBall.setY(3 * iHeight / 4);
        
        int iPosX = 100;
        for(int iI = 0; iI < iVidas; iI++) {
            Base basTruck = new Base(iPosX, 30,
                     iWidth / 12 , iHeight / 13,imgTruck);
           
            iPosX += iWidth / 12;

            lklTruck.add(basTruck);
        }
        basBarra.setX(iWidth / 2 - basBarra.getAncho() / 2);
        
        iDireccionX = 0;
        iDireccionY = -5;
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
