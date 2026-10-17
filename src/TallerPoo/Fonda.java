//Fabiola Cortés Acuña - 21.822.463-6 ICCI
package TallerPoo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Fonda {

	private int cantChoripanes;
	private String[] participantes;
	private Terremoto[] terremotos;
	private int doscientos = 200;
	private int cantParticpantes;
	private Choripan[] choripanes;
	private double umbral = 2;
	private int cantTerremotos;

	private int sobraronLineas = 0;
	private int[] orden;

	public Fonda() {
		participantes = new String[doscientos];
		choripanes = new Choripan[doscientos];
		terremotos = new Terremoto[doscientos];
		orden = new int[doscientos];
		cantParticpantes = 0;
		cantChoripanes = 0;
		cantTerremotos = 0;
	}
	public int getCapacidad() {
		return doscientos;
	}

	public int getCantParticipantes() {
		return cantParticpantes;
	}

	public int getCantChoripanes() {
		return cantChoripanes;
	}

	public int getCantTerremotos() {
		return cantTerremotos;
	}

	public int totalRegistros() {
		return cantChoripanes + cantTerremotos;
	}

	public int getSobraronLineas() {
		return sobraronLineas;
	}

	public String getParticipante(int indice) {
		if (indice < 0 || indice >= cantParticpantes) {
			return "";
		}
		return participantes[indice];
	}

	public Choripan getChoripan(int indice) {
		if (indice < 0 || indice >= cantChoripanes) {
			return null;
		}
		return choripanes[indice];
	}

	public Terremoto getTerremoto(int indice) {
		if (indice < 0 || indice >= cantTerremotos) {
			return null;
		}
		return terremotos[indice];
	}
	public int buscarParticipante(String nombre) {

		for (int i = 0; i < cantParticpantes; i++) {
			if (participantes[i].equalsIgnoreCase(nombre)) {
				return i;
			}
		}
		return -1;
	}
	public int agregarParticipante(String nombre) {

		int indice = buscarParticipante(nombre);
		if (indice != -1) {
			return indice;
		}

		if (cantParticpantes >= doscientos) {
			return -1;
		}

		participantes[cantParticpantes] = nombre;
		cantParticpantes = cantParticpantes + 1;
		return cantParticpantes - 1;
	}
	public int registrarConsumidor(String consumidor) {

		int antes = cantParticpantes;
		int indice = agregarParticipante(consumidor);

		if (indice != -1 && cantParticpantes > antes) {
			System.out.println("Participante nuevo encontrado en Consumido.txt: " + participantes[indice]);
		}
		return indice;
	}
	public int esBisiesto(int anio) {

		int resto4 = anio % 4;
		int resto100 = anio % 100;
		int resto400 = anio % 400;
		int bisiesto = 0;

		if (resto4 == 0) {
			if (resto100 != 0) {
				bisiesto = 1;
			}
			if (resto400 == 0) {
				bisiesto = 1;
			}
		}
		return bisiesto;
	}

	public int diasDelMes(int mes, int anio) {

		int dias = 31;

		if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
			dias = 30;
		}
		if (mes == 2) {
			dias = 28;
			if (esBisiesto(anio) == 1) {
				dias = 29;
			}
		}
		return dias;
	} public String conCero(int numero) {

		if (numero < 10) {
			return "0" + numero;
		}
		return "" + numero;
	}
	public String validarFecha(String texto) {

		String[] partes = texto.split("-");

		if (partes.length != 3) {
			return "";
		}

		try {
			int anio = Integer.valueOf(partes[0]);
			int mes = Integer.valueOf(partes[1]);
			int dia = Integer.valueOf(partes[2]);

			if (anio < 1000 || anio > 9999) {
				return "";
			}
			if (mes < 1 || mes > 12) {
				return "";
			}

			int diasMes = diasDelMes(mes, anio);
			if (dia < 1 || dia > diasMes) {
				return "";
			}

			return anio + "-" + conCero(mes) + "-" + conCero(dia);

		} catch (Exception e) {
			return "";
		}
	}

	public int valorValido(double valor) {

		if (valor > 0 && valor <= 500) {
			return 1;
		}
		return 0;
	}
	public String numero(double valor) {

		int entero = (int) valor;
		if (valor == entero) {
			return "" + entero;
		}
		return "" + valor;
	}
	public int cargarParticipantes(String nombreArchivo) {

		File archivo = new File(nombreArchivo);

		try {
			Scanner arch = new Scanner(archivo);

			int hayLinea = 0;
			if (arch.hasNextLine()) {
				hayLinea = 1;
			}

			while (hayLinea == 1) {
				String linea = arch.nextLine();

				if (!linea.equals("")) {
					int indice = agregarParticipante(linea);
					if (indice == -1) {
						System.out.println("Se alcanzo la capacidad maxima de participantes (" + doscientos + "). Se ignora: " + linea);
					}
				}

				hayLinea = 0;
				if (arch.hasNextLine()) {
					hayLinea = 1;
				}
			}

			arch.close();
			return cantParticpantes;

		} catch (IOException e) {
			return -1;
		}
	}
	public int cargarConsumido(String nombreArchivo) {

		File archivo = new File(nombreArchivo);

		try {
			Scanner arch = new Scanner(archivo);
			int lleno = 0;

			int hayLinea = 0;
			if (arch.hasNextLine()) {
				hayLinea = 1;
			}

			while (hayLinea == 1 && lleno == 0) {
				String linea = arch.nextLine();

				if (!linea.equals("")) {
					if (totalRegistros() >= doscientos) {
						System.out.println("Se alcanzo la capacidad maxima de registros (" + doscientos + "). El resto del archivo no se cargo.");
						lleno = 1;
						sobraronLineas = 1;
					} else {
						int resultado = procesarLinea(linea);
						if (resultado == 0) {
							System.out.println("Linea invalida ignorada en " + nombreArchivo + ": " + linea);
						}
					}
				}

				hayLinea = 0;
				if (arch.hasNextLine()) {
					hayLinea = 1;
				}
			}

			arch.close();
			return totalRegistros();

		} catch (IOException e) {
			return -1;
		}
	}
	public int procesarLinea(String linea) {

		String[] partes = linea.split(";");

		if (partes.length < 4) {
			return 0;
		}

		String tipo = partes[0];

		if (tipo.equalsIgnoreCase("Choripan") && partes.length == 5) {
			return leerChoripan(partes);
		}
		if (tipo.equalsIgnoreCase("Terremoto") && partes.length == 4) {
			return leerTerremoto(partes);
		}
		return 0;
	}
	public int leerChoripan(String[] partes) {

		try {
			String consumidor = partes[1];
			double largo = Double.valueOf(partes[2]);
			double ancho = Double.valueOf(partes[3]);
			String fecha = validarFecha(partes[4]);

			if (consumidor.equals("") || fecha.equals("")) {
				return 0;
			}
			if (valorValido(largo) == 0 || valorValido(ancho) == 0) {
				return 0;
			}

			int indice = registrarConsumidor(consumidor);
			if (indice == -1) {
				return 0;
			}

			orden[totalRegistros()] = 1;
			choripanes[cantChoripanes] = new Choripan(participantes[indice], largo, ancho, fecha);
			cantChoripanes = cantChoripanes + 1;
			return 1;

		} catch (Exception e) {
			return 0;
		}
	}
	public int leerTerremoto(String[] partes) {

		try {
			String consumidor = partes[1];
			double litros = Double.valueOf(partes[2]);
			String fecha = validarFecha(partes[3]);

			if (consumidor.equals("") || fecha.equals("")) {
				return 0;
			}
			if (valorValido(litros) == 0) {
				return 0;
			}

			int indice = registrarConsumidor(consumidor);
			if (indice == -1) {
				return 0;
			}

			orden[totalRegistros()] = 2;
			terremotos[cantTerremotos] = new Terremoto(participantes[indice], litros, fecha);
			cantTerremotos = cantTerremotos + 1;
			return 1;

		} catch (Exception e) {
			return 0;
		}
	}
	public int agregarChoripan(String consumidor, double largo, double ancho, String fecha) {

		if (totalRegistros() >= doscientos) {
			return 0;
		}
		orden[totalRegistros()] = 1;
		choripanes[cantChoripanes] = new Choripan(consumidor, largo, ancho, fecha);
		cantChoripanes = cantChoripanes + 1;
		return 1;
	}
	public int agregarTerremoto(String consumidor, double litros, String fecha) {

		if (totalRegistros() >= doscientos) {
			return 0;
		}
		orden[totalRegistros()] = 2;
		terremotos[cantTerremotos] = new Terremoto(consumidor, litros, fecha);
		cantTerremotos = cantTerremotos + 1;
		return 1;
	}
	public int lugarEnOrden(int tipo, int indice) {

		int vistos = -1;
		for (int k = 0; k < totalRegistros(); k++) {
			if (orden[k] == tipo) {
				vistos = vistos + 1;
				if (vistos == indice) {
					return k;
				}
			}
		}
		return -1;
	}
	public void sacarDelOrden(int lugar) {

		if (lugar == -1) {
			return;
		}
		int total = totalRegistros();
		for (int k = lugar; k < total - 1; k++) {
			orden[k] = orden[k + 1];
		}
	}
	public void eliminarChoripan(int indice) {

		if (indice < 0 || indice >= cantChoripanes) {
			return;
		}

		int lugar = lugarEnOrden(1, indice);
		sacarDelOrden(lugar);

		for (int i = indice; i < cantChoripanes - 1; i++) {
			choripanes[i] = choripanes[i + 1];
		}
		choripanes[cantChoripanes - 1] = null;
		cantChoripanes = cantChoripanes - 1;
	}

	public void eliminarTerremoto(int indice) {

		if (indice < 0 || indice >= cantTerremotos) {
			return;
		}

		int lugar = lugarEnOrden(2, indice);
		sacarDelOrden(lugar);

		for (int i = indice; i < cantTerremotos - 1; i++) {
			terremotos[i] = terremotos[i + 1];
		}
		terremotos[cantTerremotos - 1] = null;
		cantTerremotos = cantTerremotos - 1;
	} public int guardar() {

		try {
			FileWriter archivoEscritura = new FileWriter("Consumido.txt");
			BufferedWriter escritor = new BufferedWriter(archivoEscritura);

			int siguienteChoripan = 0;
			int siguienteTerremoto = 0;

			for (int k = 0; k < totalRegistros(); k++) {

				if (orden[k] == 1) {
					Choripan actual = choripanes[siguienteChoripan];
					escritor.write("Choripan;" + actual.getConsumidor() + ";" + numero(actual.getLargo()) + ";" + numero(actual.getAncho()) + ";" + actual.getFecha());
					siguienteChoripan = siguienteChoripan + 1;
				} else {
					Terremoto actual = terremotos[siguienteTerremoto];
					escritor.write("Terremoto;" + actual.getConsumidor() + ";" + numero(actual.getLitros()) + ";" + actual.getFecha());
					siguienteTerremoto = siguienteTerremoto + 1;
				}
				escritor.newLine();
			}

			escritor.close();
			return 1;

		} catch (IOException e) {
			return 0;
		}
	}

	public int contarChoripanes(String consumidor) {

		int cantidad = 0;
		for (int i = 0; i < cantChoripanes; i++) {
			if (choripanes[i].getConsumidor().equals(consumidor)) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int contarTerremotos(String consumidor) {

		int cantidad = 0;
		for (int i = 0; i < cantTerremotos; i++) {
			if (terremotos[i].getConsumidor().equals(consumidor)) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public double litrosTotales() {

		double suma = 0;
		for (int i = 0; i < cantTerremotos; i++) {
			suma = suma + terremotos[i].getLitros();
		}
		return suma;
	}

	public double litrosUsuario(String consumidor) {

		double suma = 0;
		for (int i = 0; i < cantTerremotos; i++) {
			if (terremotos[i].getConsumidor().equals(consumidor)) {
				suma = suma + terremotos[i].getLitros();
			}
		}
		return suma;
	} public double kcalChoripanes(String consumidor) {

		double suma = 0;
		for (int i = 0; i < cantChoripanes; i++) {
			if (choripanes[i].getConsumidor().equals(consumidor)) {
				suma = suma + choripanes[i].getKcal();
			}
		}
		return suma;
	} public double kcalTerremotos(String consumidor) {

		double suma = 0;
		for (int i = 0; i < cantTerremotos; i++) {
			if (terremotos[i].getConsumidor().equals(consumidor)) {
				suma = suma + terremotos[i].getKcal();
			}
		}
		return suma;
	} public double kcalFonda() {

		double suma = 0;
		for (int i = 0; i < cantChoripanes; i++) {
			suma = suma + choripanes[i].getKcal();
		}
		for (int i = 0; i < cantTerremotos; i++) {
			suma = suma + terremotos[i].getKcal();
		}
		return suma;
	}


	public double litrosDia(String consumidor, String fecha) {

		double suma = 0;
		for (int i = 0; i < cantTerremotos; i++) {
			Terremoto actual = terremotos[i];
			if (actual.getConsumidor().equals(consumidor) && actual.getFecha().equals(fecha)) {
				suma = suma + actual.getLitros();
			}
		}

		return suma;
	} public int esDiaEbrio(String consumidor, int indice) {

		Terremoto actual = terremotos[indice];
		if (!actual.getConsumidor().equals(consumidor)) {
			return 0;
		}

		for (int j = 0; j < indice; j++) {
			Terremoto anterior = terremotos[j];
			if (anterior.getConsumidor().equals(consumidor) && anterior.getFecha().equals(actual.getFecha())) {
				return 0;
			}
		}

		double litros = litrosDia(consumidor, actual.getFecha());
		double limite = umbral - 0.000001;

		if (litros >= limite) {
			return 1;
		}
		return 0;
	}

	public int diasEbrio(String consumidor) {

		int dias = 0;
		for (int i = 0; i < cantTerremotos; i++) {
			dias = dias + esDiaEbrio(consumidor, i);
		}
		return dias;
	}
	public String fechaEbria(String consumidor, int numero) {

		int contados = 0;
		for (int i = 0; i < cantTerremotos; i++) {
			int ebrio = esDiaEbrio(consumidor, i);
			if (ebrio == 1) {
				contados = contados + 1;
				if (contados == numero) {
					return terremotos[i].getFecha();
				}
			}
		}
		return "";
	}
}