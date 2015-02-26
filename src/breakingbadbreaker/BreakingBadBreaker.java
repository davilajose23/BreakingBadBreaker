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
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 *
 * @author JoseFernando y Juan Carlos Guzmán
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
    
    private Image imgBlock; // imagen del block
    private Image imgBall; // imagen de la bola
    private Image imgBarra; // imagen de la barra
    private Image imgTruck; // imagen de la camioneta ( vidas)
    private Image imgGameOver; // imagen game over
    private Image imgInicio; // imagen del inicio
    private Image imgBlockSpecial; // imagen de jessie como bloque
    private Image imgBlock1; // imagen de walter como bloque azul
    private Image imgBlock2; // imagen de walter como bloque verde
    private Image imgBlock3; // imagen de walter como bloque gris
    
    private SoundClip scSonidoWalter;  // Objeto sonido de walter say my name
    private SoundClip scSonidoIntro; // sonido de inicio
    private SoundClip scSonidoDam; // sonido de walter goddam
    private SoundClip scSonidoYo; // sonido de jessie
    
    private int iCantBloques; // cantidad de bloques restantes
    private int iVidas; // cantidad de vidas restantes
    private int iScore; // cantidad de puntos
    private boolean bPause; // boleana cuando esta en pausa
    private int iPosXBall; // posicion de la bola en x
    private int iPosYBall; // posicion de la bola en y
    private int iDireccionX; // direccion en que se mueve la bola en x
    private int iDireccionY; // direccion en que se mueve la bola en y
    private int iTecla; // flecha derecha = 2, flecha izquierda = 1
    private int iPosXBarra; // posicion en x de la barra
    private int iPosYBarra; // posicion en y de la barra
    private int iNivel; //nivel del juego
    
    private Animacion aniHank; // animacion de la bola de hank
    private Animacion aniHector; // animacion de la bola de hector
    private long lTiempo; // variable usada para guardar el tiempo
    private boolean bInicia; // bolean cuando ya inicio el juego
    
    private Animacion aniBarraFring;    //Animacion de la barra de Fring
    private Animacion aniBarraHank; //Animacion de la barra de Hank
    private Animacion aniBarraHector;   //Animacion de la barra de Hector
    
   /**
     * Constructor de la clase <code>BreakingBadBreaker</code>.
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>JFrame</code> y se definen funcionalidades.
     */
	public BreakingBadBreaker() {
       
        iTecla = 0; // se inicializa la variable tecla en 0
        iDireccionX = 0; // se inicializa la direccion en x de la bola en 0
        iDireccionY = -5; // se inicializa la direccion en y de la bola en -5
        iVidas = 5; // cantidad de vidas empieza en 5
        iScore = 0; // el score empieza en 0
        bPause = false; //se inicializa en falso la pausa
        iNivel = 1; // empieza en el nivel 1
        bInicia = true; // Acaba de iniciar el juego
        
        lklBlock = new LinkedList<Base>();   //Creo la lista de meth 
        lklTruck = new LinkedList<Base>();   // creo la lista de trucks
        
        //defino el sonido 1
        scSonidoWalter = new SoundClip("saymyname.wav");
        //defino el sonido 2
        scSonidoIntro = new SoundClip("intro.wav");
        //defino el sonido 3
        scSonidoDam = new SoundClip("goddam.wav");
        //defino el sonido 4
        scSonidoYo = new SoundClip("yo.wav");
        
        //La imagen del inicio
        imgInicio = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("inicio.png"));
        
        
        //la imagen de cada Bloque de walter y jessie
        
        imgBlock1 = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("walter1.png"));
        
        imgBlock2 = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("walter2.png"));
        
        imgBlock3 = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("walter3.png"));
        
        imgBlockSpecial = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("jessie.png"));
        
        //la imagen de cada Truck
        imgTruck = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("truck1.png"));
        
        // la imagen de ball
        imgBall = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("hank.png"));
        // la imagen de la barra
        imgBarra = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("barra.png"));
        // la imagen de game over
        imgGameOver = Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("gameover.png"));
        
        // crea el objeto Ball tipo base
        basBall = new Base(iWidth / 2, 3 * iHeight / 4 , 
             //   iWidth / 10 , iHeight / 7, imgBall);
                 iWidth / 15, iHeight / 12, imgBall);
        
        // reposiciona la variable ball en el centro
        basBall.setX(basBall.getX() - basBall.getAncho() / 2);
        
        // crea el objeto Base tipo base
        basBarra = new Base(iWidth / 2, 9 * iHeight / 10 , 
                iWidth / 3 , iHeight / 10, imgBarra);
        
        // reposiciona la variable barra en el centro
        basBarra.setX(basBarra.getX() - basBarra.getAncho() / 2);
        
        //Creo animacion de la barra de Fring
        aniBarraFring = new Animacion();
        //La lleno de sus imagenes
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Gustavo Fringe/fringA.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Gustavo Fringe/fringB.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Gustavo Fringe/fringC.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Gustavo Fringe/fringD.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Gustavo Fringe/fringE.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Gustavo Fringe/fringD.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Gustavo Fringe/fringC.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Gustavo Fringe/fringB.png")), 100);

        //Creo la barra de Hank
        aniBarraHank = new Animacion();
        //La lleno de sus imagenes
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hank/HankA.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hank/HankB.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hank/HankC.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hank/HankD.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hank/HankE.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hank/HankD.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hank/HankC.png")), 100);
        aniBarraFring.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hank/HankB.png")), 100);
        
        //Creo la barra de Hector
        aniBarraHector = new Animacion();
        //La lleno de sus imagenes
        aniBarraHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hector/HectorA.png")), 100);
        aniBarraHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hector/HectorB.png")), 100);
        aniBarraHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hector/HectorC.png")), 100);
        aniBarraHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hector/HectorD.png")), 100);
        aniBarraHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hector/HectorE.png")), 100);
        aniBarraHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hector/HectorD.png")), 100);
        aniBarraHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hector/HectorC.png")), 100);
        aniBarraHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/Barra/Hector/HectorB.png")), 100);
        
        //Creo animacion de hank
        aniHank = new Animacion();
        
        //Creo animacion de hector
        aniHector = new Animacion();
        long lDuracion = 125;
        
        // agrego cada imagen de hank a la animacion
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
        
        // cada imagen de hector a la animacion
        aniHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/hectorA.png")), lDuracion);
        aniHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/hectorB.png")), lDuracion);
        aniHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/hectorC.png")), lDuracion);
        aniHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/hectorD.png")), lDuracion);
        aniHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/hectorE.png")), lDuracion);
        aniHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/hectorF.png")), lDuracion);
        aniHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/hectorG.png")), lDuracion);
        aniHector.sumaCuadro(Toolkit.getDefaultToolkit().getImage(this.getClass()
                .getResource("Images/hectorH.png")), lDuracion);
        
        // las variables donde se va a posicionar cada bloque
        int iPosX  = 0;
        int iPosY = 80;
        
        // se calcula la cantidad de bloques
        iCantBloques = iMAXANCHO * 3  ;
        
        // para cada bloque se crea un objeto base y se agrega a la lista
        for(int iI = 0; iI < iCantBloques; iI++) {
            
            // se crea una variable random entre 1 y 12
            int iRandom = (int) (Math.random() * 11 + 1); 
            Base basBlock = new Base(iPosX, iPosY,
                     iWidth / iMAXANCHO, iHeight / iMAXALTO,imgBlock);
            
            // si iRandom es menor que 4 dibuja la imagen de walter1
            if ( iRandom < 4){
                basBlock.setImagen(imgBlock1);
            }
            // si iRandom es entre 4 y 7 dibuja la imagen de walter2
            else if (iRandom >= 4 && iRandom < 7){
                basBlock.setImagen(imgBlock2);
            }
            // si iRandom es entre 7 y 9 dibuja walter 3
            else if (iRandom >= 7 && iRandom < 10){
                basBlock.setImagen(imgBlock3);
                
            }
            // si iRandom mas de 10 dibuja a jessie
            else{
                basBlock.setImagen(imgBlockSpecial);
            }
            
            // aumenta las posiciones de x y y para ir acomodando cada bloque
            
            iPosX += iWidth / iMAXANCHO;
            if(iPosX == iWidth){
                iPosX = 0;
                iPosY += iHeight / iMAXALTO;
            }
           
            // agrega los bloques creados a la lista
            lklBlock.add(basBlock);
        }
         
        
        iPosX = 100; // variable para la posicion en x de las imagenes de vidas
        for(int iI = 0; iI < iVidas; iI++) {
            
            // se crea cada objeto de truck
            Base basTruck = new Base(iPosX, 30,
                     iWidth / 12 , iHeight / 13,imgTruck);
            // aumenta la variable para la siguiente imagen
            iPosX += iWidth / 12;
            
            // agrega cada truck a la lista
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
        // se define la variable ltiempo para obtener el tiempo inicial 
        lTiempo = System.currentTimeMillis();
        scSonidoIntro.play(); // reproduce el sonido de intro
        while (true) {
           
            if(!bPause && iCantBloques > 0 && iVidas > 0 && !bInicia) {
                actualiza(); // actualiza las posiciones de cada objeto
                checaColision(); // checa las colisiones entre objetos
                repaint(); // repinta el Jframe
            }
            try	{
            // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                // cuando hubo un error y no se pudo hacer dormir el thread
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
        
        // calcula el tiempo transcurrido desde el inicio del juego 
        long lTiempoTrans = System.currentTimeMillis() - lTiempo;
        // lTiempo se redefine como el tiempo actual
        lTiempo = System.currentTimeMillis();
        aniHank.actualiza(lTiempoTrans); // actualiza la imagen de la animacion
        aniHector.actualiza(lTiempoTrans); // actualiza la imagen de la animacion
        aniBarraFring.actualiza(lTiempoTrans);  //  actualiza imagen de animacion
        aniBarraHank.actualiza(lTiempoTrans);   //  actualiza imagen de animacion
        aniBarraHector.actualiza(lTiempoTrans); //  actualiza imagen de animacion
        
        if(iTecla == 1){ 
            // cuando se presiona la tecla flecha izq se mueve la barra
            basBarra.setX(basBarra.getX() - 10);
        }
        if(iTecla == 2){
            // cuando se presiona la tecla flecha der se mueve la barra
            basBarra.setX(basBarra.getX() + 10);
        }
        
        // mueve la ball en X y Y
        
        basBall.setX(basBall.getX() +  iDireccionX );
        basBall.setY(basBall.getY() +  iDireccionY + 
                (( iNivel * iDireccionY)/5));
        
        // para cada camion de la lista
        for(Base basTruck:lklTruck) {
            
            // mueve cada camion a la derecha
            basTruck.setX(basTruck.getX() + 3);
            
            
        }
        
            
    }
    
    
    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton
     * con las orillas del <code>Applet</code>.
     */
    public void checaColision(){
        
        // checa cuando la barra se sale por la izq
        if(basBarra.getX() < 0) {
            basBarra.setX(0);
        }
        // checa cuando la barra se sale por la derecha
        if(basBarra.getX() + basBarra.getAncho() > iWidth) {
            basBarra.setX(iWidth - basBarra.getAncho());
        }
        
        
        if(basBall.getX() < 0 ) {    //Ball Se sale por la izquierda
            iDireccionX *= -1;
            basBall.setX(0);
        }
        
        //Ball Se sale por la derecha
        if(basBall.getX() + basBall.getAncho() > iWidth) {
            iDireccionX *= -1;
            basBall.setX(iWidth - basBall.getAncho());
        }
        //Ball se sale por arriba
        if(basBall.getY() < 80  ) {
            iDireccionY *= -1;
            basBall.setY(80);
        }
        
        //Ball se sale por abajo
        if( basBall.getY() + basBall.getAlto() > iHeight) {
            
            // vuelve a aparecer la bola
            basBall.setX(iWidth / 2 - basBall.getAncho() / 2);
            basBall.setY(3 * iHeight /4 );
            iDireccionX = 0;
            iDireccionY = -5;
            
            //Reposiciono la barra
            basBarra.setX(iWidth / 2 - basBarra.getAncho() /2);

            iVidas--; //Decremento vidas
            scSonidoWalter.play(); //Reproduzco sonido

            if (iVidas == 0) {   //Checo si se acabaron las vidas
                iNivel = 1;
            }
            
            //Hago que desaparezca unade las vidas
            lklTruck.pop();
        }
        
        //Variables para verificar que hubo choques
        boolean bChocoVertical = false;  
        boolean bChocoHorizontal = false;
        
        //Variables para guardar posicion a la que se debe de mover BALL
        //en caso de un choque
        int iAuxY = 0;
        int iAuxX = 0;
        
        for(Base basBlock:lklBlock) {
            if(basBlock.intersecta(basBall)) {
                
                //Velocidad en Y
                int iAuxDirY = iDireccionY + (iNivel * iDireccionY / 5);
                
                // intersecta por abajo o por arriba
                if(basBlock.intersectapor(basBall, iDireccionX, iAuxDirY) == 1) {
                    bChocoVertical = true;  //Indico que hubo un choque
                    if(basBlock.getY() < basBall.getY()) { //Choca por arriba
                        iAuxY = basBlock.getY() + basBlock.getAlto();
                    }
                    else{   //Choca por abajo
                        iAuxY = basBlock.getY() - basBall.getAlto();
                    }
                }
                
                // intersecta por izquierda o derecha
                else if(basBlock.intersectapor(basBall,
                        iDireccionX, iAuxDirY) == 2) {
                    bChocoHorizontal = true;
                    if(basBlock.getX() < basBall.getX()) {   //Choca por derecha
                        iAuxX = basBlock.getX() + basBlock.getAncho();
                    }
                    else {   //Choca por la izquierda
                        iAuxX = basBlock.getX() - basBall.getAncho();
                    }
                }
                
                //Si era un bloque de tipo azul
                if (basBlock.getImagen() == imgBlock1) {
                    basBlock.setX(basBlock.getX()-iWidth);  //Desaparecer bloque
                    iCantBloques--; //Disminuyuo cantidad de bloques
                    iScore += 10;   //Aumento el puntaje
                    scSonidoDam.play(); //Reproduzco sonido 
                }
                //Si era un bloque de tipo verde
                else if (basBlock.getImagen() == imgBlock2) {
                    basBlock.setImagen(imgBlock1);  //Cambio a tipo azul
                    iScore += 20;   //Aumento el puntaje
                    scSonidoDam.play(); //Reproduzco sonido 
                }
                //Si era un bloque de tipo morado
                else if (basBlock.getImagen() == imgBlock3) {
                    basBlock.setImagen(imgBlock2);  //Cambio a tipo verde
                    iScore += 30;   //Aumento puntaje 
                    scSonidoDam.play(); //Reproduzco sonido 
                }
                //Si era un bloque de tipo Jessie
                else if(basBlock.getImagen() == imgBlockSpecial) {
                    basBlock.setX(basBlock.getX()-iWidth);  //Desaparezco bloque
                    iCantBloques--; //Disminuyo cantidad de bloques
                    iScore += 100;  //Aumento puntaje  
                    scSonidoYo.play(); //Reproduzco sonido 
                }
                
            } 
        }
        
        if (bChocoVertical) {
            iDireccionY *= -1;  //Cambio dirección
            basBall.setY(iAuxY);    //Cambio posicion
        } 
        if(bChocoHorizontal) {
            iDireccionX *= -1;  //Cambio direccion
            basBall.setX(iAuxX);    //Cambio posicion
        } 
        
        //Si ya se rompieron todos los bloques
        if(iCantBloques == 0) {
            iNivel++;   //Incremento nivel de dificultad
            int iAux = iScore;  //Guardo puntaje
            int iAux2 = iVidas; //Guardo vidas
            vuelveAEmpezar();   //Reinicio todas las variables
            iScore = iAux;      //Reestablezco el puntaje
            iVidas = iAux2;     //Reestablezco las vidas
            
            //Vacio el arreglo de camiones (vidas)
            while (!lklTruck.isEmpty()) {
                lklTruck.pop();
            }
            
            //Vuelvo a llenar arreglo hasta que llego a la cantidad real de vidas
            int iPosX = 100;
            for(int iI = 0; iI < iVidas; iI++) {
                Base basTruck = new Base(iPosX, 30,
                         iWidth / 12 , iHeight / 13,imgTruck);  //Creo camion
                iPosX += iWidth / 12;   //Aumento posicion del camion
                lklTruck.add(basTruck); //Agrego al camion
            }
        }
        
        if(basBarra.intersecta(basBall)) {//Checo intersección entre barra y bola
            
            //Guardo distancia entre mitad de barra y mitad de bola
            int iPosX1 = ((basBall.getX() + basBall.getAncho() / 2  ) - 
                    (basBarra.getX() + basBarra.getAncho() / 2));
            iPosX1 *= 10;   //Multiplico por 10
            double iPorcentaje = //Ajusto para que sea proporcional
                    ( iPosX1 / (basBarra.getAncho()-basBall.getAncho() ) );
            
            //Modifico direcciones
            iDireccionX = (int) (iPorcentaje);
            iDireccionY *= -1; 
            
            //Muevo a la pelota  la parte superior de la barra
            basBall.setY(basBarra.getY() - basBall.getAlto());
        }
        
        //Checo colision entre los camiones y el espacio delimitado para ellos
        for(Base basTruck:lklTruck) {
            if(basTruck.getX() > 600){
                basTruck.setX(30);  //Modifico posicion
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
        // mientras no haya iniciado el juego
        if (!bInicia) {
            
            Image imgFondo = Toolkit.getDefaultToolkit().getImage(this.getClass().
                    getResource("fondo.png"));

            graGrafica.drawImage(imgFondo, 0,0,iWidth,iHeight, this);


            // Actualiza el Foreground.
            graGrafica.setColor (getForeground());



            paint1(graGrafica);
            
            Image imgPausa = Toolkit.getDefaultToolkit().getImage(this.getClass().
                    getResource("pausa.png"));
            if(bPause){
                graGrafica.drawImage(imgPausa, (iWidth / 2) - 320 ,
                    (iHeight / 2) - 180, this);
            }
         }
         else{   
             // dibuja la imagen del menu
             graGrafica.drawImage(imgInicio, 0, 0, iWidth, iHeight, this);
        }
         
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
        
        // para cuando algun objeto no se ha pintado
        if (lklBlock != null && basBall != null && basBarra != null
                && lklTruck != null ) {
            
            //Dibuja la imagen de las trucks
            for (Base basTruck:lklTruck) {

                basTruck.paint(graDibujo, this);
            }

            
            graDibujo.setColor(Color.white); //Escribo en color blanco
            
            // dibuja la imagen de fondo recortada para la animacion de las trucks
            Image imgFondo1 = Toolkit.getDefaultToolkit().getImage(this.getClass().
                getResource("fondo1.png"));
            
            graDibujo.drawImage(imgFondo1, 0,0,iWidth,iHeight, this);
            
            // dibuja la imagen de la linea para dividir el tablero
            graDibujo.drawLine(0, 80, iWidth, 80);
            
            int iCantidadBall = 3; // la cantidad de balls que se tienen
            
            if(iNivel % iCantidadBall == 0) { 
                
                //dibuja la ball y barra de hank
                graDibujo.drawImage(aniHank.getImagen(),basBall.getX(),
                        basBall.getY(),iWidth/15, iHeight/12, this);
                graDibujo.drawImage(aniBarraHank.getImagen(), basBarra.getX()
                        , basBarra.getY(), iWidth / 3, iHeight / 10, this);
                
            }else if(iNivel % iCantidadBall == 1) {
                
                //dibuja la ball y barra de Hector
                graDibujo.drawImage(aniHector.getImagen(),basBall.getX(),
                        basBall.getY(),iWidth/15, iHeight/12, this);
                graDibujo.drawImage(aniBarraHector.getImagen(), basBarra.getX()
                        , basBarra.getY(), iWidth / 3, iHeight / 10, this);
                
            }else if(iNivel % iCantidadBall == 2) {
                
                //dibuja la ball y barra de Fring
                graDibujo.drawImage(aniHank.getImagen(),basBall.getX(),
                        basBall.getY(),iWidth/15, iHeight/12, this);
                graDibujo.drawImage(aniBarraFring.getImagen(), basBarra.getX()
                        , basBarra.getY(), iWidth / 3, iHeight / 10, this);
                
            }
            
            //Dibuja la imagen de los bloques
            for (Base basBlock:lklBlock) {
                
               
                basBlock.paint(graDibujo, this);
            }
        }
        else {
                //Da un mensaje mientras se carga el dibujo	
                graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
        
        //Escribo en color rojo
        graDibujo.setColor(Color.white); 
        //Escribo vidas
        graDibujo.drawString("Vidas: ", 10, 60);   
        // escribo el nivel
        graDibujo.drawString("Nivel: "+ iNivel,iWidth - 350 ,60); 
        // escribo score
        graDibujo.drawString("Puntos: " + iScore, iWidth - 200, 60); 
        // establece el tamaño de la fuente en 30
        graDibujo.setFont(graDibujo.getFont().deriveFont(30.0f));
        
        // cuando se ha perdido dibuja la imagen de gameover
        if(iVidas == 0){
            graDibujo.drawImage(imgGameOver, (iWidth / 2 ) - 320,
                    (iHeight / 2) - 180, this);
        }
        
       
        
    }
    
    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
     * @param keyEvent es el <code>evento</code> generado al presionar las teclas.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        
        // cuando se presiona la tecla flecha derecha
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) { 
            iTecla = 2; 
        }
        // cuando se presiona la tecla flecha izquierda
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) { 
            iTecla = 1;
        }
        // cuando se presiona la tecla P 
        if(keyEvent.getKeyCode() == KeyEvent.VK_P){
            
            bPause = !bPause; // cambia el estado de la boleana pausa
            
        }
        // cuando se presiona la tecla enter
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            
            // cuando se pierde y se quiere reiniciar el juego
            if (iVidas == 0 || iCantBloques == 0){
                vuelveAEmpezar(); // reinicializa las variables principales 
            }
            // establece inicia en falso para saber que ya inicio el juego
            bInicia = false; 
        }
        // cuando se presiona la tecla escape
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
            iVidas = 0; // establece vidas en 0
            iCantBloques = 0; // elimina todos los bloques
            
            // para cada bloque lo quita de la pantalla
            for (Base basBloque : lklBlock){
                if (basBloque.getX() >= 0){
                    basBloque.setX(basBloque.getX() - iWidth);
                }
            }
            // quita todas las truck de la lista
            while (!lklTruck.isEmpty()){
                lklTruck.pop();
            }
        }
        
        // repinta el juego
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
        
        iTecla = 0; // cuando se suelta las flechas izq o derecha
    }
    
    /**
     * Metodo <I>vuelveAEmpezar</I>.
     * En este metodo reinicializa las variables principales para un nuevo juego.
     */
    public void vuelveAEmpezar(){
        // quita todas las truck de la lista
        while (!lklTruck.isEmpty()){
                lklTruck.pop();
        }
        
        iVidas = 5; // vuelve a iniciar vidas en 5
        iScore = 0; // reestablece el score en 0
        
         // las variables donde se va a posicionar cada bloque
        int iPosX  = 0;
        int iPosY = 80;
        
        // para cada bloque
        for (Base basBlock : lklBlock){
            // se crea una variable random entre 1 y 12
            int iRandom = (int) (Math.random() * 11 + 1); 
            
            // reposiciona los bloques en su posicion
            basBlock.setX(iPosX);
            basBlock.setY(iPosY);
            
            // si iRandom es menor que 4 dibuja la imagen de walter1
            if ( iRandom < 4){
                basBlock.setImagen(imgBlock1);
            }
            // si iRandom es entre 4 y 7 dibuja la imagen de walter2
            else if (iRandom >= 4 && iRandom < 7){
                basBlock.setImagen(imgBlock2);
            }
            // si iRandom es entre 7 y 9 dibuja walter 3
            else if (iRandom >= 7 && iRandom < 10){
                basBlock.setImagen(imgBlock3);
                
            }
            // si iRandom mas de 10 dibuja a jessie
            else{
                basBlock.setImagen(imgBlockSpecial);
            }
            
            // calcula la posicon en X y Y para cada bloque
            iPosX += iWidth / iMAXANCHO;
            if(iPosX == iWidth){
                iPosX = 0;
                iPosY += iHeight / iMAXALTO;
            }
           
        }
        // se calcula la cantidad de bloques
        iCantBloques = iMAXANCHO * 3  ;
        // reposiciona la ball en el centro
        basBall.setX(iWidth / 2 - basBall.getAncho() / 2);
        basBall.setY(3 * iHeight / 4);
        
         iPosX = 100; // variable para la posicion de las truck
        for(int iI = 0; iI < iVidas; iI++) {
            // vuelve a crear cada objeto de truck
            Base basTruck = new Base(iPosX, 30,
                     iWidth / 12 , iHeight / 13,imgTruck);
           // aumenta la posicion para la siguiente truck
            iPosX += iWidth / 12;
            // agrega las truck a la lista
            lklTruck.add(basTruck);
        }
        // reposiciona la barra en el centro
        basBarra.setX(iWidth / 2 - basBarra.getAncho() / 2);
        
        // reinicia la direccion de la bola
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
    	bbbJuego.setVisible(true); // hace visible la ventana

    }
    
    
    
}
