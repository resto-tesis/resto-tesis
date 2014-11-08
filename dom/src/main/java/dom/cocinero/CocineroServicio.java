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

package dom.cocinero;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Password;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.google.common.base.Predicate;

import dom.empleado.Empleado;
import dom.empleado.IValidacionEmpleado;
import dom.persona.Persona;
import dom.usuario.Rol;
import dom.usuario.Usuario;

@Named("Cocinero")
@DomainService()
public class CocineroServicio extends AbstractFactoryAndRepository implements
		IValidacionEmpleado {

	public String iconName() {
		return "Cocinero";
	}

	/*
	 * Atributo Extra para las validaciones de las fechas
	 */
	final LocalDate fecha_actual = LocalDate.now();

	@Named("Nuevo Cocinero")
	@MemberOrder(name = "Empleados", sequence = "10.5")
	public Cocinero crear(
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
		return crearNuevoCocinero(crearUsuario(_nombreUsuario, _password),
				_nombre, _apellido, _dni, _direccion, _telefono, _celular,
				_correo, fechadeNacimiento, fechadeIngreso);
	}

	@Programmatic
	public Usuario crearUsuario(final String _nombreUsuario,
			final Password _password) {
		final Usuario usuario = newTransientInstance(Usuario.class);
		usuario.setNombre(_nombreUsuario);
		usuario.setPassword(_password.getPassword());
		usuario.setRol(uniqueMatch(new QueryDefault<Rol>(Rol.class,
				"cocina-role")));
		persistIfNotAlready(usuario);
		return usuario;
	}

	@Programmatic
	public Cocinero crearNuevoCocinero(final Usuario _usuario,
			final String _nombre, final String _apellido, final long _dni,
			final String _direccion, final String _telefono,
			final String _celular, final String _correo,
			final LocalDate fechadeNacimiento, final LocalDate fechadeIngreso) {
		final Cocinero cocineroNuevo = newTransientInstance(Cocinero.class);
		cocineroNuevo.setApellido(_apellido.substring(0, 1).toUpperCase()
				+ _apellido.substring(1));
		cocineroNuevo.setDocumento(_dni);
		cocineroNuevo.setDireccion(_direccion);
		cocineroNuevo.setTelefono(_telefono);
		cocineroNuevo.setCelular(_celular);
		cocineroNuevo.setCorreo(_correo);
		cocineroNuevo.setFechaDeIngreso(fechadeIngreso.toDate());
		cocineroNuevo.setFechaDeNacimiento(fechadeNacimiento.toDate());
		cocineroNuevo.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		cocineroNuevo.setUsuario(_usuario);
		cocineroNuevo.setBaja(false);
		persist(cocineroNuevo);
		return cocineroNuevo;
	}

	@Named("Cocineros")
	@MemberOrder(name = "Empleados", sequence = "10.2")
	@ActionSemantics(Of.SAFE)
	public List<Cocinero> listarCocinerosAlta() {
		return allMatches(Cocinero.class, new Predicate<Cocinero>() {

			@Override
			public boolean apply(Cocinero input) {
				// TODO Auto-generated method stub
				return input.getBaja() ? false : true;
			}
		});
	}

	@Named("Cocineros")
	@MemberOrder(name = "Empleados", sequence = "10.2")
	@ActionSemantics(Of.SAFE)
	public List<Cocinero> listarCocinerosTodos() {
		final List<Cocinero> listaCocinero = allInstances(Cocinero.class);
		return listaCocinero;
	}

	/*
	 * Validacion del ingreso de fechas por el UI
	 */
	@Override
	public String validateCrear(String _nombre, String _apellido,
			final long _dni, String _direccion, String _telefono,
			String _celular, String _correo, LocalDate fechadeNacimiento,
			LocalDate fechadeIngreso, final String _nombreUsuario,
			Password _password) {
		// TODO Auto-generated method stub
		if (firstMatch(Empleado.class, new Predicate<Empleado>() {

			@Override
			public boolean apply(Empleado _empleado) {
				// TODO Auto-generated method stub
				return _empleado.getDocumento() == _dni ? true : false;
			}
		}) != null) {
			return "Ya existe el número de documento ingresado.";
		}
		if (fechadeNacimiento.isAfter(fechadeIngreso)
				|| fechadeNacimiento.isEqual(fechadeIngreso))
			return "La fecha de nacimiento no debe ser mayor o igual a la fecha de ingreso de los empleados";
		if (fechadeIngreso.isAfter(fecha_actual))
			return "La fecha de ingreso debe ser menor o igual a la fecha actual";
		if (validaMayorEdad(fechadeNacimiento) == false)
			return "El empleado es menor de edad";
		return firstMatch(Persona.class, new Predicate<Persona>() {

			@Override
			public boolean apply(Persona _persona) {
				// TODO Auto-generated method stub
				return _persona.getUsuario().getNombre().equals(_nombreUsuario);
			}
		}) != null ? "Ya existe el usuario!" : null;
	}

	/*
	 * Validacion de la mayoria de edad de los empleados ingresados 6575 son la
	 * cantidad de dias que tiene una persona de 18 años
	 */
	@Override
	@Programmatic
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
	@Programmatic
	public int getDiasNacimiento_Hoy(LocalDate fechadeNacimiento) {
		// TODO Auto-generated method stub
		Days meses = Days.daysBetween(fechadeNacimiento, fecha_actual);
		return meses.getDays();
	}

}
