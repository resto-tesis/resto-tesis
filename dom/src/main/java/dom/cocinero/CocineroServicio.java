package dom.cocinero;

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

import dom.comanda.Comanda;
import dom.comanda.EstadoComandaEnum;
import dom.empleado.Empleado;

@Named("Cocinero")
public class CocineroServicio extends AbstractFactoryAndRepository implements ICocineroServicio {

	/*
	 * Atributo Extra para las validaciones de las fechas
	 */
	final LocalDate fecha_actual = LocalDate.now();

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Cocinero crearCocinero(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _nombre,
			@Named("Documento") @RegEx(validation = "[0-9*") @MaxLength(value = 8) @MinLength(value = 7) final long _dni,
			@Named("Fecha de Nacimiento") final LocalDate fechadeNacimiento,
			@Named("Fecha de Ingreso") final LocalDate fechadeIngreso) {
		return crearNuevoCocinero(_nombre, _apellido, _dni, fechadeNacimiento,
				fechadeIngreso);
	}

	@Hidden
	public Cocinero crearNuevoCocinero(final String _nombre,
			final String _apellido, final long _dni,
			final LocalDate fechadeNacimiento, final LocalDate fechadeIngreso) {
		final Cocinero cocineroNuevo = newTransientInstance(Cocinero.class);
		cocineroNuevo.setApellido(_apellido.substring(0, 1).toUpperCase() + _apellido.substring(1));
		cocineroNuevo.setDocumento(_dni);
		cocineroNuevo.setFechaDeIngreso(fechadeIngreso.toDate());
		cocineroNuevo.setFechaDeNacimiento(fechadeNacimiento.toDate());
		cocineroNuevo.setNombre(_nombre.substring(0, 1).toUpperCase() + _nombre.substring(1));
		persist(cocineroNuevo);
		return cocineroNuevo;
	}

	@Named("Listar")
	@MemberOrder(sequence = "2")
	@ActionSemantics(Of.SAFE)
	public List<Cocinero> listarCocineros() {
		final List<Cocinero> listaCocinero = allInstances(Cocinero.class);
		return listaCocinero;
	}

	@Hidden
	public List<Empleado> listarEmpleados() {
		return allInstances(Empleado.class);
	}
	
	@Hidden
	public List<Comanda> listarComandas() {
		return allMatches(new QueryDefault<Comanda>(Comanda.class, "comandas"));
	}
	
	@Hidden
	public List<Comanda> listarComandasSeleccionadas() {
		return allMatches(new QueryDefault<Comanda>(Comanda.class, "comandasSeleccionadas"));
	}
	
	@Hidden
	public Cocinero cambiarEstadoComandas(Cocinero _cocinero) {
		List<Comanda> listaComandas = listarComandasSeleccionadas();
		if (!listaComandas.isEmpty()) {
			for (Comanda _comanda : listaComandas) {
				if(_comanda.getEstadoPreparacion() == EstadoComandaEnum.Enviada){
					_comanda.setEstadoPreparacion(EstadoComandaEnum.En_Preparacion);
					_comanda.setEstadoSeleccion(true);
				}else{
					if(_comanda.getEstadoPreparacion() == EstadoComandaEnum.En_Preparacion){
						_comanda.setEstadoPreparacion(EstadoComandaEnum.Finalizada);
						_comanda.setEstadoSeleccion(true);
					}else{
						getContainer().informUser("La comanda seleccionada nº: " + _comanda.getNumero() + " que ya ha sido finalizada");
						_comanda.setEstadoSeleccion(false);
					}
				}
			}
			getContainer().informUser("Cambió el estado de la comanda");
		} else {
			getContainer().informUser("Debe seleccionar al menos una comanda para cambiar el estado");
		}
		return _cocinero;
	}

	/*
	 * Validacion del ingreso de fechas por el UI
	 */
	@Override
	public String validateCrearCocinero(String _nombre, String _apellido,
			long _dni, LocalDate fechadeNacimiento, LocalDate fechadeIngreso) {
		// TODO Auto-generated method stub
		for (Empleado _empleado : listarEmpleados()) {
			if (_dni == _empleado.getDocumento()) {
				return "Ya existe el número de documento ingresado.";
			}
		}
		if (fechadeNacimiento.isAfter(fechadeIngreso) || fechadeNacimiento.isEqual(fechadeIngreso)) {
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
