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

package dom.persona;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;

import dom.usuario.Usuario;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public abstract class Persona {

	// {{ Apellido (property)
	private String apellido;

	@Title(sequence = "1.0")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public String getApellido() {
		return apellido;
	}

	public void setApellido(final String apellido) {
		this.apellido = apellido;
	}

	// }}

	// {{ Nombre (property)
	private String nombre;

	@Title(sequence = "1.5", prepend = ", ")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ Documento (property)
	private long documento;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public long getDocumento() {
		return documento;
	}

	public void setDocumento(final long documento) {
		this.documento = documento;
	}

	// }}

	// {{ Telefono (property)
	private String telefono;

	@RegEx(validation = "\\d{7,11}")
	@Optional
	@MemberOrder(sequence = "4")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	// }}

	// {{ Celular (property)
	private String celular;

	@RegEx(validation = "\\d{3,7}(-)?\\d{6}")
	@Optional
	@MemberOrder(sequence = "5")
	public String getCelular() {
		return celular;
	}

	public void setCelular(final String celular) {
		this.celular = celular;
	}

	// }}

	// {{ Correo (property)
	private String correo;

	@Optional
	@MemberOrder(sequence = "6")
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(final String correo) {
		this.correo = correo;
	}

	// }}

	// {{ Direccion (property)
	private String direccion;

	@Optional
	@MultiLine(numberOfLines = 2)
	@Named("Dirección")
	@Column(allowsNull = "true")
	@MemberOrder(sequence = "7")
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(final String direccion) {
		this.direccion = direccion;
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

	// {{ Baja (property)
	private boolean baja;

	@Hidden
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public boolean getBaja() {
		return baja;
	}

	public void setBaja(final boolean baja) {
		this.baja = baja;
	}

	// }}

	public Persona alta() {
		setBaja(false);
		return this;
	}

	public String disableAlta() {
		return getBaja() ? null : "Ya dado de Alta!";
	}

	public Persona baja() {
		setBaja(true);
		return this;
	}

	public String disableBaja() {
		return getBaja() ? "Ya dado de Baja!" : null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		result = prime * result
				+ ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + (int) (documento ^ (documento >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (documento != other.documento)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

}
