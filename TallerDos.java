//Fabiola Cortés Acuña - 21.822.463-6 ICCI
package TallerPoo;
import java.util.Scanner;

public class TallerDos{

	static Fonda fonda = new Fonda();


	static Scanner input = new Scanner(System.in);

	static int entradaTerminada = 0;

	public static String leerLinea() {

		if (input.hasNextLine()) {
			return input.nextLine();
		}
		entradaTerminada = 1;
		return "";
	}
	public static int leerEntero(String pregunta, int menor, int mayor, int alTerminar, String mensajeFuera) {

		int numero = alTerminar;
		int valido = 0;

		while (valido == 0 && entradaTerminada == 0) {
			System.out.println(pregunta);
			String linea = leerLinea();

			if (entradaTerminada == 0) {
				if (linea.equals("")) {
					System.out.println(">> Error: no ingreso ninguna opcion. Intente nuevamente.");
				} else {
					try {
						numero = Integer.valueOf(linea);

						if (numero < menor || numero > mayor) {
							System.out.println(mensajeFuera);
						} else {
							valido = 1;
						}
					} catch (Exception e) {
						System.out.println(">> Error: debe ingresar un numero entero entre " + menor + " y " + mayor + ". Intente nuevamente.");
					}
				}
			}
		}

		if (valido == 0) {
			numero = alTerminar;
		}

		return numero;
	}
	public static int leerOpcion(int mayor) {
		return leerEntero("Seleccione una opcion: ", 1, mayor, mayor, ">> Error: opcion fuera de rango. Intente nuevamente.");
	}
	public static int leerNumero(String pregunta, int mayor) {
		return leerEntero(pregunta, 0, mayor, 0, ">> Error: no existe un registro con ese numero. Intente nuevamente.");
	}
	public static double leerDecimal(String pregunta, String mensajeError) {

		double valor = -1;
		int valido = 0;

		while (valido == 0 && entradaTerminada == 0) {
			System.out.println(pregunta);
			String linea = leerLinea();

			if (entradaTerminada == 0) {
				try {
					double leido = Double.valueOf(linea);

					if (fonda.valorValido(leido) == 1) {
						valor = leido;
						valido = 1;
					} else {
						System.out.println(mensajeError);
					}
				} catch (Exception e) {
					System.out.println(mensajeError);
				}
			}
		}

		return valor;
	}
	public static String leerFecha(String pregunta) {

		String fecha = "";
		int valida = 0;

		while (valida == 0 && entradaTerminada == 0) {
			System.out.println(pregunta);
			String linea = leerLinea();

			if (entradaTerminada == 0) {
				String revisada = fonda.validarFecha(linea);

				if (revisada.equals("")) {
					System.out.println(">> Error: fecha invalida. Use el formato YYYY-MM-DD, por ejemplo 2026-09-16. Intente nuevamente.");
				} else {
					fecha = revisada;
					valida = 1;
				}
			}
		}
		return fecha;
	}
	public static int leerSiNo(String pregunta) {

		int respuesta = 0;
		int listo = 0;

		while (listo == 0 && entradaTerminada == 0) {
			System.out.println(pregunta);
			String linea = leerLinea();

			if (entradaTerminada == 0) {
				if (linea.equalsIgnoreCase("s")) {
					respuesta = 1;
					listo = 1;
				} else if (linea.equalsIgnoreCase("n")) {
					respuesta = 0;
					listo = 1;
				} else {
					System.out.println(">> Error: responda s o n. Intente nuevamente.");
				}
			}
		}
		return respuesta;
	}

	public static void esperarEnter() {

		System.out.println();
		System.out.println("Presione ENTER para continuar...");
		leerLinea();
	}
	public static int contarLetras(String texto) {

		String[] letras = texto.split("");
		return letras.length;
	}
	public static String rellenar(String texto, int ancho) {

		String resultado = texto + " ";
		int largoTexto = contarLetras(resultado);

		while (largoTexto < ancho) {
			resultado = resultado + " ";
			largoTexto = largoTexto + 1;
		}

		return resultado;
	}
	public static String dosDecimales(double valor) {

		int centesimas = (int) (valor * 100 + 0.5);
		int enteros = centesimas / 100;
		int resto = centesimas % 100;

		String textoResto = "" + resto;
		if (resto < 10) {
			textoResto = "0" + resto;
		}
		return enteros + "." + textoResto;
	}

	public static String textoChoripan(Choripan actual) {
		return "Choripan | " + actual.getConsumidor() + " | " + fonda.numero(actual.getLargo()) + " cm x " + fonda.numero(actual.getAncho()) + " cm | " + actual.getFecha();
	}

	public static String textoTerremoto(Terremoto actual) {
		return "Terremoto | " + actual.getConsumidor() + " | " + dosDecimales(actual.getLitros()) + " L | " + actual.getFecha();
	}
	public static void guardarYAvisar(String mensajeBueno) {

		int guardado = fonda.guardar();

		if (guardado == 1) {
			System.out.println(">> " + mensajeBueno);
			if (fonda.getSobraronLineas() == 1) {
				System.out.println(">> ADVERTENCIA: el archivo tenia mas de " + fonda.getCapacidad() + " lineas. Las que no se cargaron ya no estan en Consumido.txt.");
			}
		} else {
			System.out.println(">> ADVERTENCIA: el cambio quedo en memoria, pero no se pudo guardar Consumido.txt.");
		}
	} public static void verChoripanes() {

		int cantidad = fonda.getCantChoripanes();
		System.out.println();
		System.out.println("=============== CHORIPANES =================");

		if (cantidad == 0) {
			System.out.println("  No hay choripanes registrados.");
		} else {
			System.out.println("  " + rellenar("#", 5) + rellenar("Consumidor", 14) + rellenar("Largo", 9) + rellenar("Ancho", 9) + rellenar("Fecha", 13) + "KCal");

			for (int i = 0; i < cantidad; i++) {
				Choripan actual = fonda.getChoripan(i);
				String largo = fonda.numero(actual.getLargo()) + " cm";
				String ancho = fonda.numero(actual.getAncho()) + " cm";
				String kcal = dosDecimales(actual.getKcal());

				System.out.println("  " + rellenar("" + (i + 1), 5) + rellenar(actual.getConsumidor(), 14) + rellenar(largo, 9) + rellenar(ancho, 9) + rellenar(actual.getFecha(), 13) + kcal);
			}
		}

		System.out.println("");
		System.out.println("  Total: " + cantidad + " choripanes registrados");
		System.out.println("==================================================");
		esperarEnter();
	}

	public static void verTerremotos() {
		int cantidad = fonda.getCantTerremotos();
		double litros = fonda.litrosTotales();
		System.out.println();
		System.out.println("=============== TERREMOTOS =================");

		if (cantidad == 0) {
			System.out.println("  No hay terremotos registrados.");
		} else {
			System.out.println("  " + rellenar("#", 5) + rellenar("Consumidor", 14) + rellenar("Litros", 10) + rellenar("Fecha", 13) + "KCal");

			for (int i = 0; i < cantidad; i++) {
				Terremoto actual = fonda.getTerremoto(i);

				String litrosTexto = dosDecimales(actual.getLitros()) + " L";
				String kcal = dosDecimales(actual.getKcal());

				System.out.println("  " + rellenar("" + (i + 1), 5) + rellenar(actual.getConsumidor(), 14) + rellenar(litrosTexto, 10) + rellenar(actual.getFecha(), 13) + kcal);
			}
		}

		System.out.println("");
		System.out.println("  Total: " + cantidad + " terremotos registrados (" + dosDecimales(litros) + " L)");
		System.out.println("==================================================");
		esperarEnter();
	}
	public static void listaChoripanes() {

		System.out.println();
		System.out.println("  " + rellenar("#", 5) + rellenar("Consumidor", 14) + rellenar("Largo", 9) + rellenar("Ancho", 9) + "Fecha");

		for (int i = 0; i < fonda.getCantChoripanes(); i++) {
			Choripan actual = fonda.getChoripan(i);
			String largo = fonda.numero(actual.getLargo()) + " cm";
			String ancho = fonda.numero(actual.getAncho()) + " cm";
			System.out.println("  " + rellenar("" + (i + 1), 5) + rellenar(actual.getConsumidor(), 14) + rellenar(largo, 9) + rellenar(ancho, 9) + actual.getFecha());
		}
		System.out.println();
	}

	public static void listaTerremotos() {

		System.out.println();
		System.out.println("  " + rellenar("#", 5) + rellenar("Consumidor", 14) + rellenar("Litros", 10) + "Fecha");

		for (int i = 0; i < fonda.getCantTerremotos(); i++) {
			Terremoto actual = fonda.getTerremoto(i);
			String litrosTexto = dosDecimales(actual.getLitros()) + " L";
			System.out.println("  " + rellenar("" + (i + 1), 5) + rellenar(actual.getConsumidor(), 14) + rellenar(litrosTexto, 10) + actual.getFecha());
		}
		System.out.println();
	}
	public static String elegirConsumidor() {

		int cantidad = fonda.getCantParticipantes();

		if (cantidad == 0) {
			System.out.println(">> Error: no hay participantes registrados.");
			return "";
		}

		System.out.println();
		System.out.println("Consumidores disponibles:");
		for (int i = 0; i < cantidad; i++) {
			System.out.println("  [" + (i + 1) + "] " + fonda.getParticipante(i));
		}

		int numero = leerEntero("Seleccione el consumidor: ", 1, cantidad, 0, ">> Error: opcion fuera de rango. Intente nuevamente.");

		if (numero == 0) {
			return "";
		}
		return fonda.getParticipante(numero - 1);
	}

	public static void agregar() {

		System.out.println();
		System.out.println("Tipo de registro:");
		System.out.println("  [1] Choripan");
		System.out.println("  [2] Terremoto");
		System.out.println("  [3] Cancelar");
		int tipo = leerOpcion(3);

		if (tipo == 3) {
			return;
		}

		if (fonda.totalRegistros() >= fonda.getCapacidad()) {
			System.out.println(">> Error: se alcanzo la capacidad maxima de registros (" + fonda.getCapacidad() + ").");
			System.out.println("   No es posible agregar mas consumos.");
			return;
		}

		String consumidor = elegirConsumidor();
		if (consumidor.equals("")) {
			return;
		}

		if (tipo == 1) {
			String errorLargo = ">> Error: el largo debe ser un numero mayor a 0 y menor o igual a 500. Intente nuevamente.";
			String errorAncho = ">> Error: el ancho debe ser un numero mayor a 0 y menor o igual a 500. Intente nuevamente.";

			double largo = leerDecimal("Largo del choripan (cm): ", errorLargo);
			if (largo == -1) {
				return;
			}
			double ancho = leerDecimal("Ancho del choripan (cm): ", errorAncho);
			if (ancho == -1) {
				return;
			}
			String fecha = leerFecha("Fecha (YYYY-MM-DD): ");
			if (fecha.equals("")) {
				return;
			}

			int agregado = fonda.agregarChoripan(consumidor, largo, ancho, fecha);
			if (agregado == 0) {
				System.out.println(">> Error: no hay espacio para agregar mas consumos.");
				return;
			}

			Choripan nuevo = fonda.getChoripan(fonda.getCantChoripanes() - 1);
			guardarYAvisar("Registro agregado correctamente y guardado en Consumido.txt");
			System.out.println("   " + textoChoripan(nuevo) + " | " + dosDecimales(nuevo.getKcal()) + " kcal");

		} else {
			String errorLitros = ">> Error: los litros deben ser un numero mayor a 0 y menor o igual a 500. Intente nuevamente.";

			double litros = leerDecimal("Litros del terremoto: ", errorLitros);
			if (litros == -1) {
				return;
			}
			String fecha = leerFecha("Fecha (YYYY-MM-DD): ");
			if (fecha.equals("")) {
				return;
			}

			int agregado = fonda.agregarTerremoto(consumidor, litros, fecha);
			if (agregado == 0) {
				System.out.println(">> Error: no hay espacio para agregar mas consumos.");
				return;
			}

			Terremoto nuevo = fonda.getTerremoto(fonda.getCantTerremotos() - 1);
			guardarYAvisar("Registro agregado correctamente y guardado en Consumido.txt");
			System.out.println("   " + textoTerremoto(nuevo) + " | " + dosDecimales(nuevo.getKcal()) + " kcal");
		}
	}
	public static String pedirConsumidor(String actual) {

		String resultado = actual;
		int listo = 0;

		String lista = "";
		for (int i = 0; i < fonda.getCantParticipantes(); i++) {
			if (i > 0) {
				lista = lista + ", ";
			}
			lista = lista + fonda.getParticipante(i);
		}
		System.out.println("Participantes: " + lista);

		while (listo == 0 && entradaTerminada == 0) {
			System.out.println("Consumidor [" + actual + "]: ");
			String linea = leerLinea();

			if (entradaTerminada == 0) {
				if (linea.equals("")) {
					listo = 1;
				} else {
					int indice = fonda.buscarParticipante(linea);

					if (indice == -1) {
						System.out.println(">> Error: ese consumidor no esta en la lista de participantes. Intente nuevamente.");
					} else {
						resultado = fonda.getParticipante(indice);
						listo = 1;
					}
				}
			}
		}
		return resultado;
	}
	public static double pedirDecimal(String campo, double actual) {

		double resultado = actual;
		int listo = 0;
		String mensajeError = ">> Error: el valor ingresado debe ser un numero mayor a 0 y menor o igual a 500. Intente nuevamente.";

		while (listo == 0 && entradaTerminada == 0) {
			System.out.println(campo + " [" + fonda.numero(actual) + "]: ");
			String linea = leerLinea();

			if (entradaTerminada == 0) {
				if (linea.equals("")) {
					listo = 1;
				} else {
					try {
						double leido = Double.valueOf(linea);

						if (fonda.valorValido(leido) == 1) {
							resultado = leido;
							listo = 1;
						} else {
							System.out.println(mensajeError);
						}
					} catch (Exception e) {
						System.out.println(mensajeError);
					}
				}
			}
		}
		return resultado;
	}
	public static String pedirFecha(String actual) {

		String resultado = actual;
		int listo = 0;

		while (listo == 0 && entradaTerminada == 0) {
			System.out.println("Fecha [" + actual + "]: ");
			String linea = leerLinea();

			if (entradaTerminada == 0) {
				if (linea.equals("")) {
					listo = 1;
				} else {
					String revisada = fonda.validarFecha(linea);

					if (revisada.equals("")) {
						System.out.println(">> Error: fecha invalida. Use el formato YYYY-MM-DD, por ejemplo 2026-09-16. Intente nuevamente.");
					} else {
						resultado = revisada;
						listo = 1;
					}
				}
			}
		}
		return resultado;
	}

	public static void editar() {

		System.out.println();
		System.out.println("Tipo de registro a editar:");
		System.out.println("  [1] Choripan");
		System.out.println("  [2] Terremoto");
		System.out.println("  [3] Cancelar");
		int tipo = leerOpcion(3);

		if (tipo == 3) {
			return;
		}

		if (tipo == 1) {
			int cantidad = fonda.getCantChoripanes();
			if (cantidad == 0) {
				System.out.println(">> No hay choripanes registrados para editar.");
				return;
			}

			listaChoripanes();
			int numero = leerNumero("Seleccione el registro a editar (0 para cancelar): ", cantidad);
			if (numero == 0) {
				return;
			}

			Choripan elegido = fonda.getChoripan(numero - 1);
			System.out.println();
			System.out.println("Registro actual -> " + textoChoripan(elegido));
			System.out.println("Deje el campo vacio para mantener el valor actual.");
			System.out.println();

			String consumidor = pedirConsumidor(elegido.getConsumidor());
			double largo = pedirDecimal("Largo", elegido.getLargo());
			double ancho = pedirDecimal("Ancho", elegido.getAncho());
			String fecha = pedirFecha(elegido.getFecha());

			elegido.setConsumidor(consumidor);
			elegido.setLargo(largo);
			elegido.setAncho(ancho);
			elegido.setFecha(fecha);

			guardarYAvisar("Registro actualizado correctamente y guardado en Consumido.txt");
			System.out.println("   " + textoChoripan(elegido) + " | " + dosDecimales(elegido.getKcal()) + " kcal");

		} else {
			int cantidad = fonda.getCantTerremotos();
			if (cantidad == 0) {
				System.out.println(">> No hay terremotos registrados para editar.");
				return;
			}

			listaTerremotos();
			int numero = leerNumero("Seleccione el registro a editar (0 para cancelar): ", cantidad);
			if (numero == 0) {
				return;
			}

			Terremoto elegido = fonda.getTerremoto(numero - 1);
			System.out.println();
			System.out.println("Registro actual -> " + textoTerremoto(elegido));
			System.out.println("Deje el campo vacio para mantener el valor actual.");
			System.out.println();

			String consumidor = pedirConsumidor(elegido.getConsumidor());
			double litros = pedirDecimal("Litros", elegido.getLitros());
			String fecha = pedirFecha(elegido.getFecha());

			elegido.setConsumidor(consumidor);
			elegido.setLitros(litros);
			elegido.setFecha(fecha);

			guardarYAvisar("Registro actualizado correctamente y guardado en Consumido.txt");
			System.out.println("   " + textoTerremoto(elegido) + " | " + dosDecimales(elegido.getKcal()) + " kcal");
		}
	}
	public static void eliminar() {

		System.out.println();
		System.out.println("Tipo de registro a eliminar:");
		System.out.println("  [1] Choripan");
		System.out.println("  [2] Terremoto");
		System.out.println("  [3] Cancelar");
		int tipo = leerOpcion(3);

		if (tipo == 3) {
			return;
		}

		if (tipo == 1) {
			int cantidad = fonda.getCantChoripanes();
			if (cantidad == 0) {
				System.out.println(">> No hay choripanes registrados para eliminar.");
				return;
			}

			listaChoripanes();
			int numero = leerNumero("Seleccione el registro a eliminar (0 para cancelar): ", cantidad);
			if (numero == 0) {
				return;
			}

			Choripan elegido = fonda.getChoripan(numero - 1);
			int confirmado = leerSiNo("Confirma la eliminacion de \"" + textoChoripan(elegido) + "\"? (s/n): ");

			if (confirmado == 1) {
				fonda.eliminarChoripan(numero - 1);
				guardarYAvisar("Registro eliminado correctamente. Cambios guardados en Consumido.txt");
			} else {
				System.out.println(">> Eliminacion cancelada.");
			}

		} else {
			int cantidad = fonda.getCantTerremotos();
			if (cantidad == 0) {
				System.out.println(">> No hay terremotos registrados para eliminar.");
				return;
			}

			listaTerremotos();
			int numero = leerNumero("Seleccione el registro a eliminar (0 para cancelar): ", cantidad);
			if (numero == 0) {
				return;
			}

			Terremoto elegido = fonda.getTerremoto(numero - 1);
			int confirmado = leerSiNo("Confirma la eliminacion de \"" + textoTerremoto(elegido) + "\"? (s/n): ");

			if (confirmado == 1) {
				fonda.eliminarTerremoto(numero - 1);
				guardarYAvisar("Registro eliminado correctamente. Cambios guardados en Consumido.txt");
			} else {
				System.out.println(">> Eliminacion cancelada.");
			}
		}
	}
	public static void totalChoripanes() {

		int total = fonda.getCantChoripanes();
		System.out.println();
		System.out.println(">> Total de choripanes comidos: " + total);
	}

	public static void totalTerremotos() {
		int total = fonda.getCantTerremotos();
		double litros = fonda.litrosTotales();
		System.out.println();
		System.out.println(">> Total de terremotos tomados: " + total + "  (" + dosDecimales(litros) + " litros en total)");
	}

	public static void choripanesUsuario() {

		System.out.println();
		System.out.println("======= CHORIPANES POR USUARIO =============");

		int cantidad = fonda.getCantParticipantes();
		if (cantidad == 0) {
			System.out.println("  No hay participantes registrados.");
		} else {
			System.out.println("  " + rellenar("Usuario", 14) + "Cantidad");

			for (int i = 0; i < cantidad; i++) {
				String nombre = fonda.getParticipante(i);
				int choripanes = fonda.contarChoripanes(nombre);

				System.out.println("  " + rellenar(nombre, 14) + choripanes);
			}
		}
		System.out.println("============================================");
	}

	public static void terremotosUsuario() {

		System.out.println();
		System.out.println("======= TERREMOTOS POR USUARIO =============");

		int cantidad = fonda.getCantParticipantes();
		if (cantidad == 0) {
			System.out.println("  No hay participantes registrados.");
		} else {
			System.out.println("  " + rellenar("Usuario", 14) + rellenar("Cantidad", 10) + "Litros");

			for (int i = 0; i < cantidad; i++) {
				String nombre = fonda.getParticipante(i);
				int terremotos = fonda.contarTerremotos(nombre);
				double litros = fonda.litrosUsuario(nombre);

				System.out.println("  " + rellenar(nombre, 14) + rellenar("" + terremotos, 10) + dosDecimales(litros) + " L");
			}
		}
		System.out.println("============================================");
	}

	public static void kcalUsuario() {

		System.out.println();
		System.out.println("============ KCAL POR USUARIO ==============");

		int cantidad = fonda.getCantParticipantes();
		if (cantidad == 0) {
			System.out.println("  No hay participantes registrados.");
		} else {
			System.out.println("  " + rellenar("Usuario", 14) + rellenar("Choripan", 12) + rellenar("Terremoto", 12) + "Total");

			for (int i = 0; i < cantidad; i++) {
				String nombre = fonda.getParticipante(i);

				double kcalChoripan = fonda.kcalChoripanes(nombre);
				double kcalTerremoto = fonda.kcalTerremotos(nombre);
				double kcalSuma = kcalChoripan + kcalTerremoto;

				System.out.println("  " + rellenar(nombre, 14) + rellenar(dosDecimales(kcalChoripan), 12) + rellenar(dosDecimales(kcalTerremoto), 12) + dosDecimales(kcalSuma));
			}
		}
		System.out.println("");
		System.out.println("  Consumo total de la fonda: " + dosDecimales(fonda.kcalFonda()) + " kcal");
		System.out.println("============================================");
	}

	public static void diasEbrio() {

		System.out.println();
		System.out.println("========= DIAS EBRIO POR USUARIO ===========");
		System.out.println("  (se cuenta un dia ebrio con 2 L o mas en la misma fecha)");
		System.out.println();

		int cantidad = fonda.getCantParticipantes();
		if (cantidad == 0) {
			System.out.println("  No hay participantes registrados.");
		} else {
			System.out.println("  " + rellenar("Usuario", 14) + rellenar("Dias ebrio", 12) + "Detalle");

			for (int i = 0; i < cantidad; i++) {
				String nombre = fonda.getParticipante(i);
				int dias = fonda.diasEbrio(nombre);
				String detalle = "-";
				if (dias > 0) {
					detalle = "";
					for (int n = 1; n <= dias; n++) {
						String fecha = fonda.fechaEbria(nombre, n);
						double litros = fonda.litrosDia(nombre, fecha);
						if (n > 1) {
							detalle = detalle + ", ";
						}
						detalle = detalle + fecha + " (" + dosDecimales(litros) + " L)";
					}
				}

				System.out.println("  " + rellenar(nombre, 14) + rellenar("" + dias, 12) + detalle);
			}
		}
		System.out.println("============================================");
	} public static void menuVer() {

		int volver = 0;

		while (volver == 0) {
			System.out.println();
			System.out.println("============ VER REGISTROS =================");
			System.out.println("  [1] Choripanes");
			System.out.println("  [2] Terremotos");
			System.out.println("  [3] Volver al menu principal");
			System.out.println("============================================");

			int escogido = leerOpcion(3);

			if (escogido == 1) {
				verChoripanes();
			} else if (escogido == 2) {
				verTerremotos();
			} else if (escogido == 3) {
				volver = 1;
			}
		}
	}

	public static void menuActualizar() {

		int volver = 0;

		while (volver == 0) {
			System.out.println();
			System.out.println("========== ACTUALIZAR REGISTROS ============");
			System.out.println("  [1] Agregar");
			System.out.println("  [2] Editar");
			System.out.println("  [3] Eliminar");
			System.out.println("  [4] Volver al menu principal");
			System.out.println("============================================");

			int escogido = leerOpcion(4);

			if (escogido == 1) {
				agregar();
			} else if (escogido == 2) {
				editar();
			} else if (escogido == 3) {
				eliminar();
			} else if (escogido == 4) {
				volver = 1;
			}
		}
	}

	public static void menuMetricas() {

		int volver = 0;

		while (volver == 0) {
			System.out.println();
			System.out.println("============ CALCULAR METRICAS =============");
			System.out.println("  [1] Choripanes comidos en total");
			System.out.println("  [2] Terremotos tomados en total");
			System.out.println("  [3] Choripanes comidos por usuario");
			System.out.println("  [4] Terremotos tomados por usuario");
			System.out.println("  [5] KCal consumidas por usuario");
			System.out.println("  [6] Dias ebrio por usuario");
			System.out.println("  [7] Volver al menu principal");
			System.out.println("============================================");

			int escogido = leerOpcion(7);

			if (escogido == 1) {
				totalChoripanes();
			} else if (escogido == 2) {
				totalTerremotos();
			} else if (escogido == 3) {
				choripanesUsuario();
			} else if (escogido == 4) {
				terremotosUsuario();
			} else if (escogido == 5) {
				kcalUsuario();
			} else if (escogido == 6) {
				diasEbrio();
			} else if (escogido == 7) {
				volver = 1;
			}
		}
	}
	public static void cargarDatos() {

		int cantidadParticipantes = fonda.cargarParticipantes("Participantes.txt");
		if (cantidadParticipantes == -1) {
			System.out.println(">> ADVERTENCIA: no se encontro el archivo \"Participantes.txt\".");
			System.out.println("   Se iniciara el programa sin participantes previos.");
		}

		int cantidadRegistros = fonda.cargarConsumido("Consumido.txt");
		if (cantidadRegistros == -1) {
			System.out.println(">> ADVERTENCIA: no se encontro el archivo \"Consumido.txt\".");
			System.out.println("   Se iniciara el programa sin registros previos.");
		}
	}

	public static int opcion() {

		System.out.println();
		System.out.println("============================================");
		System.out.println("           LA FONDA DEL PAPU");
		System.out.println("============================================");
		System.out.println("  Registros cargados: " + fonda.totalRegistros());
		System.out.println("--------------------------------------------");
		System.out.println("  [1] Ver registros");
		System.out.println("  [2] Actualizar registros");
		System.out.println("  [3] Calcular metricas");
		System.out.println("  [4] Salir");
		System.out.println("============================================");

		return leerOpcion(4);
	}

	public static void main(String[] args) {

		cargarDatos();

		int escogdo = opcion();

		while (escogdo != 4) {

			if (escogdo == 1) {
				menuVer();
			} else if (escogdo == 2) {
				menuActualizar();
			} else if (escogdo == 3) {
				menuMetricas();
			}

			escogdo = opcion();
		}

		System.out.println(">> Hasta luego...");
		input.close();
	}
}