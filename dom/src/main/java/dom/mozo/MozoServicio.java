package dom.mozo;

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
import dom.mesa.EstadoAsignacionMesaEnum;
import dom.mesa.Mesa;

@Named("Mozo")
public class MozoServicio extends AbstractFactoryAndRepository implements
		IMozoServicio {

	/*
	 * Atributo Extra para las validaciones de las fechas
	 */
	final LocalDate fecha_actual = LocalDate.now();

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mozo crearMozo(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _nombre,
			@Named("Documento") @RegEx(validation = "[0-9*") @MaxLength(value = 8) @MinLength(value = 7) final long _dni,
			@Named("Fecha de Nacimiento") final LocalDate fechadeNacimiento,
			@Named("Fecha de Ingreso") final LocalDate fechadeIngreso) {
		return crearNuevoMozo(_apellido, _nombre, _dni, fechadeIngreso,
				fechadeNacimiento);
	}

	@Hidden
	public Mozo crearNuevoMozo(final String _apellido, final String _nombre,
			final long _dni, final LocalDate fechadeIngreso,
			final LocalDate fechadeNacimiento) {
		final Mozo mozo = newTransientInstance(Mozo.class);
		mozo.setApellido(_apellido.substring(0, 1).toUpperCase()
				+ _apellido.substring(1));
		mozo.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		mozo.setDocumento(_dni);
		mozo.setFechaDeIngreso(fechadeIngreso.toDate());
		mozo.setFechaDeNacimiento(fechadeNacimiento.toDate());
		persist(mozo);
		return mozo;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
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
	public Mozo asignarMesas(Mozo _mozo) {
		List<Mesa> listaMesas = listaMesasSeleccionadas();
		if (!listaMesas.isEmpty()) {
			for (Mesa _mesa : listaMesas) {
				_mesa.setEstadoAsignacion(EstadoAsignacionMesaEnum.Asignada);
				_mesa.setEstadoSeleccion(false);
				_mozo.addMesa(_mesa);
			}
			getContainer().informUser("Mesas asignadas.");
		} else {
			getContainer().informUser(
					"No hay Mesas seleccionadas para su asignación");
		}
		return _mozo;
	}

	/*
	 * Validacion del ingreso de fechas por el UI
	 */
	@Override
	public String validateCrearMozo(String _nombre, String _apellido,
			long _dni, LocalDate fechadeNacimiento, LocalDate fechadeIngreso) {
		// TODO Auto-generated method stub
		for (Empleado _empleado : listarEmpleados()) {
			if (_dni == _empleado.getDocumento()) {
				return "Ya existe el número de documento ingresado.";
			}
		}
		if (fechadeNacimiento.isAfter(fechadeIngreso)
				|| fechadeNacimiento.isEqual(fechadeIngreso)) {
			return "La fecha de nacimiento no debe ser mayor o igual a la fecha de ingreso de los empleados.";
		} else {
			if (fechadeIngreso.isAfter(fecha_actual)) {
				return "La fecha de ingreso debe ser menor o igual a la fecha actual.";
			} else {
				if (validaMayorEdad(fechadeNacimiento) == false) {
					return "El empleado es menor de edad.";
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
