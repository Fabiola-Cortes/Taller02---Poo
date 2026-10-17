//Fabiola Cortés Acuña - 21.822.463-6 ICCI
package TallerPoo;
public class Choripan {

	private String consumidor;
	private double largo;
	private double ancho;
	private String fecha;

	public Choripan(String elConsumidor, double elLargo, double elAncho, String laFecha) {
		consumidor = elConsumidor;
		largo = elLargo;
		ancho = elAncho;
		fecha = laFecha;
	}

	public String getConsumidor() {
		return consumidor;
	}

	public double getLargo() {
		return largo;
	}

	public double getAncho() {
		return ancho;
	}

	public String getFecha() {
		return fecha;
	}

	public void setConsumidor(String elConsumidor) {
		consumidor = elConsumidor;
	}

	public void setLargo(double elLargo) {
		largo = elLargo;
	}

	public void setAncho(double elAncho) {
		ancho = elAncho;
	}

	public void setFecha(String laFecha) {
		fecha = laFecha;
	}

	public double getKcal() {
		return (largo*ancho)/3;
	}
}