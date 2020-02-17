package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Tutorias {

	// Declaración de atributos

	private int capacidad;
	private int tamano;
	private Tutoria[] coleccionTutorias;

	// Constructor

	public Tutorias(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		this.capacidad = capacidad;
		coleccionTutorias = new Tutoria[capacidad];
		tamano = 0;
	}

	// Get

	public Tutoria[] get() {
		return copiaProfundaTutorias();
	}

	// Copia profunda

	private Tutoria[] copiaProfundaTutorias() {
		Tutoria[] copiaTutorias = new Tutoria[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++) {
			copiaTutorias[i] = new Tutoria(coleccionTutorias[i]);
		}
		return copiaTutorias;
	}

	// Get

	public Tutoria[] get(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		Tutoria[] tutoriasProfesor = new Tutoria[capacidad];
		int j = 0;
		for (int i = 0; !tamanoSuperado(i); i++) {
			if (coleccionTutorias[i].getProfesor().equals(profesor)) {
				tutoriasProfesor[j++] = new Tutoria(coleccionTutorias[i]);
			}
		}
		return tutoriasProfesor;
	}

	// Get de los atributos

	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}

	// Insertar

	public void insertar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}
		int indice = buscarIndice(tutoria);
		if (capacidadSuperada(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más tutorías.");
		}
		if (tamanoSuperado(indice)) {
			coleccionTutorias[indice] = new Tutoria(tutoria);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una tutoría con ese identificador.");
		}

	}

	// Buscar índice

	private int buscarIndice(Tutoria tutoria) {
		int indice = 0;
		boolean tutoriaEncontrado = false;
		while (!tamanoSuperado(indice) && !tutoriaEncontrado) {
			if (coleccionTutorias[indice].equals(tutoria)) {
				tutoriaEncontrado = true;
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

	public Tutoria buscar(Tutoria tutoria) {
		if (tutoria == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una tutoría nula.");
		}
		int indice = buscarIndice(tutoria);
		if (tamanoSuperado(indice)) {
			return null;
		} else {
			return new Tutoria(coleccionTutorias[indice]);
		}
	}

	// Borrar

	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una tutoría nula.");
		}
		int indice = buscarIndice(tutoria);
		if (tamanoSuperado(indice)) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna tutoría con ese identificador.");
		} else {
			desplazarUnaPosicionHaciaIzquierda(indice);
		}
	}

	// Desplazar una posición hacia la izquierda

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i = indice; !tamanoSuperado(i); i++) {
			coleccionTutorias[i] = coleccionTutorias[i + 1];
		}
		coleccionTutorias[i] = null;
		tamano--;
	}
}
