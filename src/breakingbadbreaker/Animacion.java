package breakingbadbreaker;

import java.awt.Image;
import java.util.ArrayList;

/**
 * Animacion
 *
 * La clase Animacion maneja una serie de imágenes (cuadros)
 * y la cantidad de tiempo que se muestra cada cuadro.
 * <code>Animacion</code>
 *
 * @author José Fernando Davila A00999281 y Juan Carlos Guzmán A01175826  
 * @version 1
 * @date 25/Feb/2015
 */

public class Animacion{
	
	private ArrayList arrLCuadros;
	private int iIndiceCuadroActual;
	private long lTiempoDeAnimacion;
	private long lDuracionTotal;
	
	/**
         * Animacion
         * 
         * Metodo constructor usado para crear el objeto animacion
         * Crea una lista vacia
         * 
         */
        
	public Animacion() {
		arrLCuadros = new ArrayList();
		lDuracionTotal = 0;
		iniciar();
	}
	
	/**
	*Añade una cuadro a la animación con la duración
		indicada (tiempo que se muestra la imagen).
	*/
        /**
         * sumaCuadro
         * 
         * Metodo para añadir un cuadro a la animacion con la duraccion
         * indicada (tiempo que se muestra la imagen).
         * @param imaImage es la <code>imagen a agregar</code> del objeto.
         * @param lDuracion es la <code>cantidad de duracion</code> de la imagen.
         * 
         */
	public synchronized void sumaCuadro(Image imaImage, long lDuracion) {
		lDuracionTotal += lDuracion;
		arrLCuadros.add(new cuadroDeAnimacion(imaImage, lDuracionTotal));
	}
	
	/**
         * iniciar
         * 
         * Metodo para iniciar la animacion desde el principio
         * 
         */
	public synchronized void iniciar() {
		lTiempoDeAnimacion = 0;
		iIndiceCuadroActual = 0;
	}
	
	
        /**
         * actualiza
         * 
         * Metodo para actualizar la imagen (cuadro) actual de la animacion
         * si es necesario
         * @param lTiempoTranscurrido es el <code>tiempo</code> que ha transcurrido.
         * 
         */
	public synchronized void actualiza(long lTiempoTranscurrido) {
		if (arrLCuadros.size() > 1) {
			lTiempoDeAnimacion += lTiempoTranscurrido;
			
			if (lTiempoDeAnimacion >= lDuracionTotal) {
				lTiempoDeAnimacion = lTiempoDeAnimacion % lDuracionTotal;
				iIndiceCuadroActual = 0; 
			}
			
			while (lTiempoDeAnimacion > 
                                getCuadro(iIndiceCuadroActual).tiempoFinal) {
				iIndiceCuadroActual++;
			}
		}
	}
	
	
        /**
         * getImagen
         * 
         * Metodo de acceso que regresa la imagen actual de la animacion 
         * Regeresa null si la animación no tiene imágenes.
         * @return la imagen actual de la animacion
         * 
         */
	public synchronized Image getImagen() {
		if (arrLCuadros.size() == 0) {
			return null;
		}
		else {
			return getCuadro(iIndiceCuadroActual).imagen;
		}
	}
	/**
         * getCuadro
         * 
         * Metodo de acceso para obtener el cuadro de tipo cuadroDeAnimacion
         * @param iN es el <code>cuadro</code> que desea regresar.
         * 
         */
	private cuadroDeAnimacion getCuadro(int iN) {
		return (cuadroDeAnimacion)arrLCuadros.get(iN);
	}
	/**
         * cuadroDeAnimacion
         * 
         * Deficinion de la clase cuadroDeAnimacion
         * 
         */
	public class cuadroDeAnimacion{
		
		Image imagen;
		long tiempoFinal;
		
		public cuadroDeAnimacion() {
			this.imagen = null;
			this.tiempoFinal = 0;
		}
		
		public cuadroDeAnimacion(Image imagen, long tiempoFinal) {
			this.imagen = imagen;
			this.tiempoFinal = tiempoFinal;
		}
		
		public Image getImagen() {
			return imagen;
		}
		
		public long getTiempoFinal() {
			return tiempoFinal;
		}
		
		public void setImagen (Image imagen) {
			this.imagen = imagen;
		}
		
		public void setTiempoFinal(long tiempoFinal) {
			this.tiempoFinal = tiempoFinal;
		}
	}
		
}