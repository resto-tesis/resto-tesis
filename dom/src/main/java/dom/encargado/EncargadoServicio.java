package dom.encargado;

/*
 * Copyright 2014 resto-tesis
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import dom.empleado.Empleado;
import dom.usuario.Rol;
import dom.usuario.Usuario;

@Named("Encargado")
public class EncargadoServicio extends AbstractFactoryAndRepository implements
		IEncargadoServicio {

	/*
	 * Atributo Extra para las validaciones de las fechas
	 */
	final LocalDate fecha_actual = LocalDate.now();

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Encargado crearEncargado(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _nombre,
			@Named("Documento") @RegEx(validation = "[0-9*") @MaxLength(value = 8) @MinLength(value = 7) final long _dni,
			@Named("Fecha de Nacimiento") final LocalDate fechadeNacimiento,
			@Named("Fecha de Ingreso") final LocalDate fechadeIngreso,
			@Named("Usuario") final String _nombreUsuario,
			@Named("Contraseña") final String _password) {
		crearUsuario(_nombreUsuario, _password);
		return crearEncargadoNuevo(_apellido, _nombre, _dni, fechadeNacimiento,
				fechadeIngreso);
	}

	@Hidden
	public void crearUsuario(final String _nombreUsuario, final String _password) {
		final Usuario usuario = newTransientInstance(Usuario.class);
		usuario.setNombre(_nombreUsuario);
		usuario.setPassword(_password);
		usuario.setRol(uniqueMatch(new QueryDefault<Rol>(Rol.class,
				"encargado-role")));
		persistIfNotAlready(usuario);
	}

	@Hidden
	public Encargado crearEncargadoNuevo(final String _apellido,
			final String _nombre, final long _dni,
			final LocalDate fechadeNacimiento, final LocalDate fechadeIngreso) {
		final Encargado encargado = newTransientInstance(Encargado.class);
		encargado.setApellido(_apellido.substring(0, 1).toUpperCase()
				+ _apellido.substring(1));
		encargado.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		encargado.setDocumento(_dni);
		encargado.setFechaDeNacimiento(fechadeNacimiento.toDate());
		encargado.setFechaDeIngreso(fechadeIngreso.toDate());
		persist(encargado);
		return encargado;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Encargado> listarEncargados() {
		final List<Encargado> listaencargados = allInstances(Encargado.class);
		return listaencargados;
	}

	@Hidden
	public List<Empleado> listarEmpleados() {
		return allInstances(Empleado.class);
	}

	/*
	 * Validacion del ingreso de fechas por el UI
	 */
	@Override
	public String validateCrearEncargado(String _nombre, String _apellido,
			long _dni, LocalDate fechadeNacimiento, LocalDate fechadeIngreso,
			String _nombreUsuario, String _password) {
		// TODO Auto-generated method stub
		for (Empleado _empleado : listarEmpleados()) {
			if (_dni == _empleado.getDocumento()) {
				return "Ya existe el número de documento ingresado.";
			}
		}
		if (fechadeNacimiento.isAfter(fechadeIngreso)
				|| fechadeNacimiento.isEqual(fechadeIngreso)) {
			return "La fecha de nacimiento no debe ser mayor o igual a la fecha de ingreso de los empleados";
		} else {
			if (fechadeIngreso.isAfter(fecha_actual)) {
				return "La fecha de ingreso debe ser menor o igual a la fecha actual";
			} else {
				if (validaMayorEdad(fechadeNacimiento) == false) {
					return "El empleado es menor de edad";
				} else {
					return null;
				}
			}
		}
	}

	/*
	 * Validacion de la mayoria de edad de los empleados ingresados 6575 son la
	 * cantidad de dias que tiene una persona de 18 años
	 */
	@Override
	@Hidden
	public boolean validaMayorEdad(LocalDate fechadeNacimiento) {
		// TODO Auto-generated method stub
		if (getDiasNacimiento_Hoy(fechadeNacimiento) >= 6575) {
			return true;
		}
		return false;
	}

	/*
	 * Obtiene la cantidad de dias entre la fecha de nacimiento y la fecha
	 * actual
	 */
	@Override
	@Hidden
	public int getDiasNacimiento_Hoy(LocalDate fechadeNacimiento) {
		// TODO Auto-generated method stub
		Days meses = Days.daysBetween(fechadeNacimiento, fecha_actual);
		return meses.getDays();
	}
}
