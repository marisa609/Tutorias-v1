package org.iesalandalus.programacion.tutorias.mvc.modelo;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Citas;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Profesores;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Sesiones;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.Tutorias;

public class Modelo {

	private static final int CAPACIDAD = 50;

	private Alumnos alumnos;
	private Profesores profesores;
	private Tutorias tutorias;
	private Sesiones sesiones;
	private Citas citas;

	public Modelo() {

		this.alumnos = new Alumnos(CAPACIDAD);
		this.profesores = new Profesores(CAPACIDAD);
		this.tutorias = new Tutorias(CAPACIDAD);
		this.sesiones = new Sesiones(CAPACIDAD);
		this.citas = new Citas(CAPACIDAD);
	}

	// INSERTAR

	public void insertar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException {
		alumnos.insertar(alumno);
	}

	public void insertar(Profesor profesor) throws OperationNotSupportedException, IllegalArgumentException {
		profesores.insertar(profesor);
	}

	public void insertar(Tutoria tutoria) throws OperationNotSupportedException, IllegalArgumentException {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: No es posible copiar una tutoría nula.");
		}

		Profesor profesor = profesores.buscar(tutoria.getProfesor());

		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}

		tutorias.insertar(new Tutoria(profesor, tutoria.getNombre()));
	}

	public void insertar(Sesion sesion) throws OperationNotSupportedException, IllegalArgumentException {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No es posible copiar una cita nula.");
		}

		Tutoria tutoria = tutorias.buscar(sesion.getTutoria());

		if (tutoria == null) {
			throw new NullPointerException("ERROR: No es posible copiar una tutoría nula.");
		}

		sesiones.insertar(new Sesion(tutoria, sesion.getFecha(), sesion.getHoraInicio(), sesion.getHoraFin(),
				sesion.getMinutosDuracion()));
	}

	public void insertar(Cita cita) throws OperationNotSupportedException, IllegalArgumentException {
		if (cita == null) {
			throw new NullPointerException("ERROR: No es posible copiar una cita nula.");
		}

		Alumno alumno = alumnos.buscar(cita.getAlumno());

		if (alumno == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}

		Sesion sesion = sesiones.buscar(cita.getSesion());

		if (sesion == null) {
			throw new NullPointerException("ERROR: No es posible copiar una cita nula.");
		}

		citas.insertar(new Cita(alumno, sesion, cita.getHora()));
	}

	// BUSCAR

	public Alumno buscar(Alumno alumno) throws IllegalArgumentException {
		return alumnos.buscar(alumno);
	}

	public Profesor buscar(Profesor profesor) throws IllegalArgumentException {
		return profesores.buscar(profesor);
	}

	public Tutoria buscar(Tutoria tutoria) throws IllegalArgumentException {
		return tutorias.buscar(tutoria);
	}

	public Sesion buscar(Sesion sesion) throws IllegalArgumentException {
		return sesiones.buscar(sesion);
	}

	public Cita buscar(Cita cita) throws IllegalArgumentException {
		return citas.buscar(cita);
	}

	// BORRAR

	public void borrar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException {
		alumnos.borrar(alumno);
	}

	public void borrar(Profesor profesor) throws OperationNotSupportedException, IllegalArgumentException {
		profesores.borrar(profesor);
	}

	public void borrar(Tutoria tutoria) throws OperationNotSupportedException, IllegalArgumentException {
		tutorias.borrar(tutoria);
	}

	public void borrar(Sesion sesion) throws OperationNotSupportedException, IllegalArgumentException {
		sesiones.borrar(sesion);
	}

	public void borrar(Cita cita) throws OperationNotSupportedException, IllegalArgumentException {
		citas.borrar(cita);
	}

	// GET

	public Alumno[] getAlumnos() {
		return alumnos.get();
	}

	public Profesor[] getProfesores() {
		return profesores.get();
	}

	public Tutoria[] getTutorias() {
		return tutorias.get();
	}

	public Tutoria[] getTutorias(Profesor profesor) {
		return tutorias.get(profesor);
	}

	public Sesion[] getSesiones() {
		return sesiones.get();
	}

	public Sesion[] getSesiones(Tutoria tutoria) {
		return sesiones.get(tutoria);
	}

	public Cita[] getCitas() {
		return citas.get();
	}

	public Cita[] getCitas(Sesion sesion) {
		return citas.get(sesion);
	}

	public Cita[] getCitas(Alumno alumno) {
		return citas.get(alumno);
	}

}
