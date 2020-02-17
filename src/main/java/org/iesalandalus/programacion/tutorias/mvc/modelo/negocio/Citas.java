package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;


public class Citas {

	// Declaración de atributos

	private int capacidad;
	private Cita[] coleccionCitas;
	private int tamano;

	// Constructor

	public Citas(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		this.capacidad = capacidad;
		coleccionCitas = new Cita[capacidad];
		tamano = 0;
	}

	// Get

	public Cita[] get() {
		return copiaProfundaCitas();
	}

	// Copia profunda

	private Cita[] copiaProfundaCitas() {
		Cita[] copiaCitas = new Cita[capacidad];
		for (int i = 0; !tamanoSuperado(i); i++) {
			copiaCitas[i] = new Cita(coleccionCitas[i]);
		}
		return copiaCitas;
	}

	// Get

	public Cita[] get(Sesion sesion) {
		if (sesion == null) {
			throw new NullPointerException("ERROR: La sesión no puede ser nula.");
		}
		Cita[] citasSesion = new Cita[capacidad];
		int j = 0;
		for (int i = 0; !tamanoSuperado(i); i++) {
			if (coleccionCitas[i].getSesion().equals(sesion)) {
				citasSesion[j++] = new Cita(coleccionCitas[i]);
			}
		}
		return citasSesion;
	}

	public Cita[] get(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		Cita[] citasAlumno = new Cita[capacidad];
		int j = 0;
		for (int i = 0; !tamanoSuperado(i); i++) {
			if (coleccionCitas[i].getAlumno().equals(alumno)) {
				citasAlumno[j++] = new Cita(coleccionCitas[i]);
			}
		}
		return citasAlumno;
	}

	// Get

	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}

	// Insertar

	public void insertar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}
		int indice = buscarIndice(cita);
		if (capacidadSuperada(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más citas.");
		}
		if (tamanoSuperado(indice)) {
			coleccionCitas[indice] = new Cita(cita);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una cita con esa hora.");
		}

	}

	// Buscar índice

	private int buscarIndice(Cita cita) {
		int indice = 0;
		boolean citaEncontrado = false;
		while (!tamanoSuperado(indice) && !citaEncontrado) {
			if (coleccionCitas[indice].equals(cita)) {
				citaEncontrado = true;
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

	public Cita buscar(Cita cita) {
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una cita nula.");
		}
		int indice = buscarIndice(cita);
		if (tamanoSuperado(indice)) {
			return null;
		} else {
			return new Cita(coleccionCitas[indice]);
		}
	}

	// Borrar

	public void borrar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		}
		int indice = buscarIndice(cita);
		if (tamanoSuperado(indice)) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita con esa hora.");
		} else {
			desplazarUnaPosicionHaciaIzquierda(indice);
		}
	}

	// Desplazar una posición hacia la izquierda

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i = indice; !tamanoSuperado(i); i++) {
			coleccionCitas[i] = coleccionCitas[i + 1];
		}
		coleccionCitas[i] = null;
		tamano--;
	}
}
