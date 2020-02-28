package org.iesalandalus.programacion.tutorias.mvc.modelo;

import java.util.List;

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

	private Alumnos alumnos;
	private Profesores profesores;
	private Tutorias tutorias;
	private Sesiones sesiones;
	private Citas citas;

	public Modelo() {

		alumnos = new Alumnos();
		profesores = new Profesores();
		tutorias = new Tutorias();
		sesiones = new Sesiones();
		citas = new Citas();
	}

	// INSERTAR

	public void insertar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException {
		alumnos.insertar(alumno);
	}

	public void insertar(Profesor profesor) throws OperationNotSupportedException, IllegalArgumentException {
		profesores.insertar(profesor);
	}
	
	// Al insertar una tutoría deberemos comprobar que el profesor de la tutoría existe e insertar la tutoría para el profesor encontrado.

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
	
	// Al insertar una sesión deberemos comprobar que la tutoría de la sesión existe e insertar la sesión para la tutoría encontrada.

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
	
	// Al insertar una cita deberemos comprobar que la sesión de la cita existe y que el alumno de la cita también existe e insertar la cita para la sesión y el alumno encontrados.

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

	// Al borrar un alumno deberemos borrar todas las citas asociadas al mismo.

	public void borrar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException {
		List<Cita> citasAlumno = citas.get(alumno);
		for (Cita cita : citasAlumno) {
			citas.borrar(cita);
		}
		alumnos.borrar(alumno);
	}

	// Al borrar un profesor deberemos borrar todas las tutorías (en cascada)
	// asociadas al mismo.

	public void borrar(Profesor profesor) throws OperationNotSupportedException, IllegalArgumentException {
		List<Tutoria> tutoriasProfesor = tutorias.get(profesor);
		for (Tutoria tutoria : tutoriasProfesor) {
			tutorias.borrar(tutoria);
		}
		profesores.borrar(profesor);
	}

	// Al borrar una tutoría deberemos borrar todas las sesiones (en cascada)
	// asociadas a la misma.

	public void borrar(Tutoria tutoria) throws OperationNotSupportedException, IllegalArgumentException {
		List<Sesion> sesionesTutoria = sesiones.get(tutoria);
		for (Sesion sesion : sesionesTutoria) {
			sesiones.borrar(sesion);
		}
		tutorias.borrar(tutoria);
	}

	// Al borrar una sesión deberemos borrar todas las citas asociadas a la misma.

	public void borrar(Sesion sesion) throws OperationNotSupportedException, IllegalArgumentException {
		List<Cita> citaSesion = citas.get(sesion);
		for (Cita cita : citaSesion) {
			citas.borrar(cita);
		}
		sesiones.borrar(sesion);
	}

	public void borrar(Cita cita) throws OperationNotSupportedException, IllegalArgumentException {
		citas.borrar(cita);
	}

	// GET

	public List<Alumno> getAlumnos() {
		return alumnos.get();
	}

	public List<Profesor> getProfesores() {
		return profesores.get();
	}

	public List<Tutoria> getTutorias() {
		return tutorias.get();
	}

	public List<Tutoria> getTutorias(Profesor profesor) {
		return tutorias.get(profesor);
	}

	public List<Sesion> getSesiones() {
		return sesiones.get();
	}

	public List<Sesion> getSesiones(Tutoria tutoria) {
		return sesiones.get(tutoria);
	}

	public List<Cita> getCitas() {
		return citas.get();
	}

	public List<Cita> getCitas(Sesion sesion) {
		return citas.get(sesion);
	}

	public List<Cita> getCitas(Alumno alumno) {
		return citas.get(alumno);
	}

}
