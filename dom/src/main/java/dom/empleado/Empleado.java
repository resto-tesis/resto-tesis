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

package dom.empleado;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.annotation.MemberOrder;

import dom.persona.Persona;

/**
 * Entidad Empleado la cual extiende de Persona
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Sequence(name = "secuenciaLegajo", strategy = SequenceStrategy.CONTIGUOUS)
public abstract class Empleado extends Persona {

	private SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

	// {{ Legajo (property)
	private long legajo;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaLegajo")
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public long getLegajo() {
		return legajo;
	}

	public void setLegajo(final long legajo) {
		this.legajo = legajo;
	}

	// }}

	// {{ fechaDeNacimiento (property)
	private Date fechaDeNacimiento;

	@MemberOrder(sequence = "5")
	public String getFechaDeNacimiento() {
		return formato.format(fechaDeNacimiento);
	}

	public void setFechaDeNacimiento(final Date fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	// }}

	// {{ fechaDeIngreso (property)
	private Date fechaDeIngreso;

	@MemberOrder(sequence = "6")
	public String getFechaDeIngreso() {
		return formato.format(fechaDeIngreso);
	}

	public void setFechaDeIngreso(final Date fechaDeIngreso) {
		this.fechaDeIngreso = fechaDeIngreso;
	}

	// }}

	public String validateDocumento(long documento) {
		return empleadoServicio.validarDocumento(documento);
	}

	@Inject
	private EmpleadoServicio empleadoServicio;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((fechaDeIngreso == null) ? 0 : fechaDeIngreso.hashCode());
		result = prime
				* result
				+ ((fechaDeNacimiento == null) ? 0 : fechaDeNacimiento
						.hashCode());
		result = prime * result + (int) (legajo ^ (legajo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		if (fechaDeIngreso == null) {
			if (other.fechaDeIngreso != null)
				return false;
		} else if (!fechaDeIngreso.equals(other.fechaDeIngreso))
			return false;
		if (fechaDeNacimiento == null) {
			if (other.fechaDeNacimiento != null)
				return false;
		} else if (!fechaDeNacimiento.equals(other.fechaDeNacimiento))
			return false;
		if (legajo != other.legajo)
			return false;
		return true;
	}

}
