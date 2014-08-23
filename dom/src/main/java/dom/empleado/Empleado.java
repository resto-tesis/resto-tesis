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
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.SequenceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Sequence;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;

import dom.persona.Persona;
import dom.usuario.Usuario;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaLegajo", strategy = SequenceStrategy.CONTIGUOUS)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public abstract class Empleado extends Persona{

	private SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

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
