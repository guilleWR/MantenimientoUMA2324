package clubdeportivo;

import java.util.StringJoiner;

public class ClubDeportivo {
	private String nombre;
	private int ngrupos;
	private Grupo[] grupos;
	private static final int TAM = 10;

	public ClubDeportivo(String nombre) throws ClubException {
		this(nombre, TAM);
	}

	public ClubDeportivo(String nombre, int n) throws ClubException {
		if (nombre == null || nombre.trim().isEmpty() ) { //La comparacion nombre == null se ha  agregado gracias a los Test
			throw new ClubException("ERROR: el club no puede crearse con un nombre nulo o vacio");
		}else if(n <= 0){
			throw new ClubException("ERROR: el club no puede crearse con un número de grupos 0 o negativo");
		}
		this.nombre = nombre;
		grupos = new Grupo[n];
	}

	private int buscar(Grupo g) {
		int i = 0;
		while (i < ngrupos && !g.equals(grupos[i])) {
			i++;
		}
		if (i == ngrupos) {
			i = -1;
		}
		return i;
	}

	public void anyadirActividad(String[] datos) throws ClubException {
		try {
			int plazas = Integer.parseInt(datos[2]);
			int matriculados = Integer.parseInt(datos[3]);
			double tarifa = Double.parseDouble(datos[4]);
			Grupo g = new Grupo(datos[0], datos[1], plazas, matriculados, tarifa);
			anyadirActividad(g);
		} catch (NumberFormatException e) {
			throw new ClubException("ERROR: formato de número incorrecto");
		}
	}

	public void anyadirActividad(Grupo g) throws ClubException {
		if (g==null){ // ADDME: anaydido para comprobar los grupos nulos
			throw new ClubException("ERROR: el grupo es nulo");
		}
		int pos = buscar(g);
		if (pos == -1) { // El grupo es nuevo
			if (ngrupos >= grupos.length) { // Hemos añadido este fragmento de codigo para evitar que el array se salga de su capacidad
				throw new ClubException("ERROR: no se pueden añadir más grupos, el club ha alcanzado su capacidad máxima.");
			}
			grupos[ngrupos] = g;
			ngrupos++;
		} else { // El grupo ya existe --> modificamos las plazas
			grupos[pos].actualizarPlazas(g.getPlazas());
		}
	}

	public int plazasLibres(String actividad) throws ClubException {
		if (actividad == null) {
			throw new ClubException("La actividad no puede ser null");// La actividad no puede ser null
		}
		int p = -1;//Inicializo en -1 para que no devulva 0 en caso de que la actividad no exista
		int i = 0;
		while (i < ngrupos) {
			if (grupos[i].getActividad().equals(actividad)) {
				p = grupos[i].plazasLibres();
			}
			i++;
		}
		return p;
	}

	public void matricular(String actividad, int npersonas) throws ClubException {
		//falta la comprobacion de no meter un numero de personas cero o negatio

		if (npersonas < 0) {
			throw new ClubException("No se pueden matricular un numero negativo de personas");
		}

		int plazas = plazasLibres(actividad);
		if (plazas < npersonas) {
			throw new ClubException("ERROR: no hay suficientes plazas libres para esa actividad en el club.");
		}
		int i = 0;
		while (i < ngrupos && npersonas > 0) {
			if (actividad.equals(grupos[i].getActividad())) {
				int plazasGrupo = grupos[i].plazasLibres();
				if (npersonas >= plazasGrupo) {
					grupos[i].matricular(plazasGrupo);
					npersonas -= plazasGrupo;
				} else {
					grupos[i].matricular(npersonas);
				}
			}
			i++;
		}
	}

	public double ingresos() {
		double cantidad = 0.0;
		int i = 0;
		while (i < ngrupos) {
			cantidad += grupos[i].getTarifa() * grupos[i].getMatriculados();
			i++;
		}
		return cantidad;
	}

	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "[ ", " ]");
		int i = 0;
		while (i < ngrupos) {
			sj.add(grupos[i].toString());
			i++;
		}
		return nombre + " --> " + sj.toString();
	}
}
