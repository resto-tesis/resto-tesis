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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
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
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;

import dom.usuario.Usuario;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaLegajo", strategy = SequenceStrategy.CONTIGUOUS)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public abstract class Empleado {

	private SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

	// {{ legajo (property)
	private int legajo;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaLegajo")
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getLegajo() {
		return legajo;
	}

	public void setLegajo(final int lejago) {
		this.legajo = lejago;
	}

	// }}

	// {{ apellido (property)
	private String apellido;

	@RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*")
	@MaxLength(value = 20)
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

	@RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*")
	@MaxLength(value = 20)
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

	@RegEx(validation = "[0-9*")
	@MaxLength(value = 8)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public long getDocumento() {
		return documento;
	}

	public void setDocumento(final long documento) {
		this.documento = documento;
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

	// {{ Usuario (property)
	private Usuario usuario;

	@Hidden
	@Persistent(dependent = "true")
	@Column(allowsNull = "false")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(final Usuario usuario) {
		this.usuario = usuario;
	}

	// }}

	public String validateDocumento(long documento) {
		return empleadoServicio.validarDocumento(documento);
	}

	@Inject
	private EmpleadoServicio empleadoServicio;
}
