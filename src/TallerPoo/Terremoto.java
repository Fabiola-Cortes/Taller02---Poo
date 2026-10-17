//Fabiola Cortés Acuña - 21.822.463-6 ICCI
package TallerPoo;
public class Terremoto {

	private String consumidor;
	private double litros;
	private String fecha;

	public Terremoto(String elConsumidor, double losLitros, String laFecha) {
		consumidor = elConsumidor;
		litros = losLitros;
		fecha = laFecha;
	}

	public String getConsumidor() {
		return consumidor;
	}

	public double getLitros() {
		return litros;
	}

	public String getFecha() {
		return fecha;
	}

	public void setConsumidor(String elConsumidor) {
		consumidor = elConsumidor;
	}

	public void setLitros(double losLitros) {
		litros = losLitros;
	}

	public void setFecha(String laFecha) {
		fecha = laFecha;
	}


	public double getKcal() {
		return (litros*10)/3;
	}
}