package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;

public class Profesores {

	// Declaración 

	private List<Profesor> coleccionProfesores;
	

	// Constructor

	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}

	// Get

	public List<Profesor> get() {
		List<Profesor> profesoresOrdenados = copiaProfundaProfesores();
		profesoresOrdenados.sort(Comparator.comparing(Profesor::getDni));
		return profesoresOrdenados;
	}

	// Copia profunda

	private List<Profesor> copiaProfundaProfesores() {
		List<Profesor> copiaProfesores = new ArrayList<>();
		for (Profesor profesor : coleccionProfesores) {
			copiaProfesores.add(new Profesor(profesor));
		}
		return copiaProfesores;
	}

	// GetTamano

	public int getTamano() {
		return coleccionProfesores.size();
	}

	// Insertar

	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		int indice = coleccionProfesores.indexOf(profesor);
		if (indice == -1) {
			coleccionProfesores.add(new Profesor(profesor));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese DNI.");
		}		
		
	}

	// Buscar índice

	private int buscarIndice(Profesor profesor) {
		int indice = 0;
		boolean profesorEncontrado = false;
		while (!tamanoSuperado(indice) && !profesorEncontrado) {
			if (coleccionProfesores[indice].equals(profesor)) {
				profesorEncontrado = true;
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

	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un profesor nulo.");
		}
		int indice = buscarIndice(profesor);
		if (tamanoSuperado(indice)) {
			return null;
		} else {
			return new Profesor(coleccionProfesores[indice]);
		}
	}

	// Borrar

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		int indice = buscarIndice(profesor);
		if (tamanoSuperado(indice)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese DNI.");
		} else {
			desplazarUnaPosicionHaciaIzquierda(indice);
		}
	}

	// Desplazar una posición hacia izquierda

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i = indice; !tamanoSuperado(i); i++) {
			coleccionProfesores[i] = coleccionProfesores[i + 1];
		}
		coleccionProfesores[i] = null;
		tamano--;
	}
}
