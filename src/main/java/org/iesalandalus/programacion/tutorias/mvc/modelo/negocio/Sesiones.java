package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Sesiones {

	// Declaración de atributos

	private int capacidad;
	private Sesion[] coleccionSesiones;
	private int tamano;

	// Constructor

	public Sesiones(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		this.capacidad = capacidad;
		coleccionSesiones = new Sesion[capacidad];
		tamano = 0;
	}

	// Get

	public Sesion[] get() {
		return copiaProfundaSesiones();
	}

	// Copia profunda

	private Sesion[] copiaProfundaSesiones() {
		Sesion[] copiaSesiones = new Sesion[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++) {
			copiaSesiones[i] = new Sesion(coleccionSesiones[i]);
		}
		return copiaSesiones;
	}

	// Get

	public Sesion[] get(Tutoria tutoria) {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: La tutoría no puede ser nulo.");
		}
		Sesion[] sesionesTutoria = new Sesion[capacidad];
		int j = 0;
		for (int i = 0; !tamanoSuperado(i); i++) {
			if (coleccionSesiones[i].getTutoria().equals(tutoria)) {
				sesionesTutoria[j++] = new Sesion(coleccionSesiones[i]);
			}
		}
		return sesionesTutoria;
	}

	// Get

	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}

	// Insertar

	public void insertar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}
		int indice = buscarIndice(sesion);
		if (capacidadSuperada(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más sesiones.");
		}
		if (tamanoSuperado(indice)) {
			coleccionSesiones[indice] = new Sesion(sesion);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una sesión con esa fecha.");
		}

	}

	// Buscar índice

	private int buscarIndice(Sesion sesion) {
		int indice = 0;
		boolean sesionEncontrado = false;
		while (!tamanoSuperado(indice) && !sesionEncontrado) {
			if (coleccionSesiones[indice].equals(sesion)) {
				sesionEncontrado = true;
			} else {
				indice++;
			}
		}
		return indice;
	}

	// Tamaño superado

	private boolean tamanoSuperado(int indice) {
		return indice >= tamano;
	}

	// Capacidad superada

	private boolean capacidadSuperada(int indice) {
		return indice >= capacidad;
	}

	// Buscar

	public Sesion buscar(Sesion sesion) {
		if (sesion == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una sesión nula.");
		}
		int indice = buscarIndice(sesion);
		if (tamanoSuperado(indice)) {
			return null;
		} else {
			return new Sesion(coleccionSesiones[indice]);
		}
	}

	// Borrar

	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una sesión nula.");
		}
		int indice = buscarIndice(sesion);
		if (tamanoSuperado(indice)) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna sesión con esa fecha.");
		} else {
			desplazarUnaPosicionHaciaIzquierda(indice);
		}
	}

	// Desplazar una posición hacia la izquierda

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i = indice; !tamanoSuperado(i); i++) {
			coleccionSesiones[i] = coleccionSesiones[i + 1];
		}
		coleccionSesiones[i] = null;
		tamano--;
	}
}
