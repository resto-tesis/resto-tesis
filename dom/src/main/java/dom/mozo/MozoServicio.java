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

package dom.mozo;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Password;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import dom.empleado.Empleado;
import dom.empleado.IValidacionEmpleado;
import dom.mesa.EstadoAsignacionMesaEnum;
import dom.mesa.Mesa;
import dom.usuario.Rol;
import dom.usuario.Usuario;

@DomainService
public class MozoServicio extends AbstractFactoryAndRepository implements
		IValidacionEmpleado {

	/*
	 * Atributo Extra para las validaciones de las fechas
	 */
	final LocalDate fecha_actual = LocalDate.now();

	@Named("Nuevo Mozo")
	@MemberOrder(name = "Empleados", sequence = "10.4")
	public Mozo crear(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _nombre,
			@Named("Documento") @RegEx(validation = "[0-9*") @MaxLength(value = 8) @MinLength(value = 7) final long _dni,
			@Named("Direccion") @MultiLine(numberOfLines = 2) final String _direccion,
			@Named("Telefono") @RegEx(validation = "\\d{7,10}") @Optional @MaxLength(value = 15) final String _telefono,
			@Named("Celular") @RegEx(validation = "\\d{3,7}(-)?\\d{6}") @Optional @MaxLength(value = 15) final String _celular,
			@Named("Correo Electronico") @RegEx(validation = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+") @Optional final String _correo,
			@Named("Fecha de Nacimiento") final LocalDate fechadeNacimiento,
			@Named("Fecha de Ingreso") final LocalDate fechadeIngreso,
			@Named("Usuario") final String _nombreUsuario,
			@Named("Contraseña") final Password _password) {
		return crearNuevoMozo(crearUsuario(_nombreUsuario, _password),
				_apellido, _nombre, _dni, _direccion, _telefono, _celular,
				_correo, fechadeIngreso, fechadeNacimiento);
	}

	@Hidden
	public Usuario crearUsuario(final String _nombreUsuario,
			final Password _password) {
		final Usuario usuario = newTransientInstance(Usuario.class);
		usuario.setNombre(_nombreUsuario);
		usuario.setPassword(_password.getPassword());
		usuario.setRol(uniqueMatch(new QueryDefault<Rol>(Rol.class, "mozo-role")));
		persist(usuario);
		return usuario;
	}

	@Hidden
	public Mozo crearNuevoMozo(final Usuario _usuario, final String _apellido,
			final String _nombre, final long _dni, final String _direccion,
			final String _telefono, final String _celular,
			final String _correo, final LocalDate fechadeIngreso,
			final LocalDate fechadeNacimiento) {
		final Mozo mozo = newTransientInstance(Mozo.class);
		mozo.setApellido(_apellido.substring(0, 1).toUpperCase()
				+ _apellido.substring(1));
		mozo.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		mozo.setDocumento(_dni);
		mozo.setDireccion(_direccion);
		mozo.setTelefono(_telefono);
		mozo.setCelular(_celular);
		mozo.setCorreo(_correo);
		mozo.setFechaDeIngreso(fechadeIngreso.toDate());
		mozo.setFechaDeNacimiento(fechadeNacimiento.toDate());
		mozo.setUsuario(_usuario);
		persist(mozo);
		return mozo;
	}

	@Named("Mozos")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Empleados", sequence = "10.1")
	public List<Mozo> listarMozos() {
		return allInstances(Mozo.class);
	}

	@Hidden
	public List<Empleado> listarEmpleados() {
		return allInstances(Empleado.class);
	}

	@Hidden
	public List<Mesa> listarMesaSinAsignar() {
		return allMatches(new QueryDefault<Mesa>(Mesa.class, "mesasSinAsignar"));
	}

	@Hidden
	public List<Mesa> listaMesasSeleccionadas() {
		return allMatches(new QueryDefault<Mesa>(Mesa.class,
				"mesasSeleccionadas"));
	}

	@Hidden
	public List<Mesa> listaMesas() {
		return allInstances(Mesa.class);
	}

	/*
	 * Validacion del ingreso de fechas por el UI
	 */
	@Override
	public String validateCrear(String _nombre, String _apellido, long _dni,
			String _direccion, String _telefono, String _celular,
			String _correo, LocalDate fechadeNacimiento,
			LocalDate fechadeIngreso, String _nombreUsuario, Password _password) {
		// TODO Auto-generated method stub
		for (Empleado _empleado : listarEmpleados())
			if (_dni == _empleado.getDocumento())
				return "Ya existe el número de documento ingresado.";
		if (fechadeNacimiento.isAfter(fechadeIngreso)
				|| fechadeNacimiento.isEqual(fechadeIngreso))
			return "La fecha de nacimiento no debe ser mayor o igual a la fecha de ingreso de los empleados";
		if (fechadeIngreso.isAfter(fecha_actual))
			return "La fecha de ingreso debe ser menor o igual a la fecha actual";
		if (validaMayorEdad(fechadeNacimiento) == false)
			return "El empleado es menor de edad";
		else
			return null;
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

	@Hidden
	public Mozo asignarMesas(Mozo _mozo) {
		for (Mesa _mesa : listaMesasSeleccionadas()) {
			_mozo.getListamesas().add(_mesa);
			_mesa.setEstadoAsignacion(EstadoAsignacionMesaEnum.Asignada);
			_mesa.setEstadoSeleccion(false);
		}
		getContainer().informUser("Mesas asignadas.");
		return _mozo;
	}
}
