package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Sesiones {

	// Declaración de atributos

	private List<Sesion> coleccionSesiones;

	// Constructor

	public Sesiones() {
		coleccionSesiones = new ArrayList<>();
	}

	// Copia profunda

	private List<Sesion> copiaProfundaSesiones() {
		List<Sesion> copiaSesiones = new ArrayList<>();
		for (Sesion sesion : coleccionSesiones) {
			copiaSesiones.add(new Sesion(sesion));
		}
		return copiaSesiones;
	}

	// SESIONES PROFESOR TUTORIA Y FECHA DE LA SESION ¿? Profesor ¿?

	// Get
	//Las sesiones se ordenarán por tutoría y por fecha.

	public List<Sesion> get() {
		List<Sesion> sesionesOrdenadas = copiaProfundaSesiones();
		Comparator<Tutoria> comparadorTutoria = Comparator.comparing(Tutoria::getNombre);
		Comparator<Sesion> comparadorSesion = Comparator.comparing(Sesion::getFecha);
		sesionesOrdenadas.sort(Comparator.comparing(Sesion::getTutoria, comparadorTutoria).thenComparing(Sesion::getSesion, comparadorSesion));
		return sesionesOrdenadas;
	}

	// Get
	// Cuando se listen las sesiones de una tutoría se mostrarán ordenadas por fecha
	// de la sesión

	public List<Sesion> get(Tutoria tutoria) {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: La tutoría no puede ser nulo.");
		}
		List<Sesion> sesionesTutoria = new ArrayList<>();
		for (Sesion sesion : coleccionSesiones) {
			if (sesion.getTutoria().equals(tutoria)) {
				sesionesTutoria.add(new Sesion(sesion));
			}
		}
		Comparator<Sesion> comparadorSesion = Comparator.comparing(Sesion::getFecha);
		sesionesTutoria.sort(Comparator.comparing(Sesion::getFecha));
		return sesionesTutoria;
		
	}

	// Get Tamaño

	public int getTamano() {
		return coleccionSesiones.size();
	}

	// Insertar

	public void insertar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}
		int indice = coleccionSesiones.indexOf(sesion);
		if (indice == -1) {
			coleccionSesiones.add(new Sesion(sesion));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una sesión con esa fecha.");
		}

	}

	// Buscar Sesion

	public Sesion buscar(Sesion sesion) {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No se puede buscar una sesión nula.");
		}
		int indice = coleccionSesiones.indexOf(sesion);
		if (indice == -1) {
			return null;
		} else {
			return new Sesion(coleccionSesiones.get(indice));

		}

	}

	// Borrar

	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una sesión nula.");
		}
		int indice = coleccionSesiones.indexOf(sesion);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna sesión con esa fecha.");
		} else {
			coleccionSesiones.remove(indice);
		}
	}

}
