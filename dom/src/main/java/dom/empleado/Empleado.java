package dom.empleado;

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

import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.SequenceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Sequence;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaLegajo", strategy = SequenceStrategy.CONTIGUOUS)
@Inheritance(strategy = InheritanceStrategy.COMPLETE_TABLE)
public abstract class Empleado {

	// {{ legajo (property)
	private int lejago;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaLegajo")
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getLegajo() {
		return lejago;
	}

	public void setLegajo(final int lejago) {
		this.lejago = lejago;
	}

	// }}

	// {{ apellido (property)
	private String apellido;

	@Title(sequence = "1.0")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getApellido() {
		return apellido;
	}

	public void setApellido(final String apellido) {
		this.apellido = apellido;
	}

	// }}

	// {{ nombre (property)
	private String nombre;

	@Title(sequence = "1.5", prepend = ", ")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ documento (property)
	private long documento;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public long getDocumento() {
		return documento;
	}

	public void setDocumento(final long documento) {
		this.documento = documento;
	}

	// }}

	// {{ fechadeNacimiento (property)
	private Date fechadeNacimiento;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	public Date getFechadeNacimiento() {
		return fechadeNacimiento;
	}

	public void setFechadeNacimiento(final Date fechadeNacimiento) {
		this.fechadeNacimiento = fechadeNacimiento;
	}

	// }}

	// {{ fechadeIngreso (property)
	private Date fechadeIngreso;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "6")
	public Date getFechadeIngreso() {
		return fechadeIngreso;
	}

	public void setFechadeIngreso(final Date fechadeIngreso) {
		this.fechadeIngreso = fechadeIngreso;
	}
	// }}

}
