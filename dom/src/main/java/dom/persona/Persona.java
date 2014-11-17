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
import org.apache.isis.applib.annotation.Where;

import dom.usuario.Usuario;
/**
 * Clase abstracta que representa a una persona, de la cual extenderan todos 
 * los empleados y clientes
 * @author RestoTesis
 * @since 10/09/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public abstract class Persona {

	// {{ Apellido (property)
	private String apellido;

	/**
	 * Pemite obtener un apellido a la Persona
	 * @return apellido String
	 */
	@Hidden(where = Where.ALL_TABLES)
	@Title(sequence = "1.0")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public String getApellido() {
		return apellido;
	}

	/**
	 * Setea el apellido de la Persona
	 * @param apellido String
	 */
	public void setApellido(final String apellido) {
		this.apellido = apellido;
	}

	// {{ Nombre (property)
	private String nombre;

	/**
	 * Pemite obtener un nombre a la Persona
	 * @return nombre String
	 */
	@Hidden(where = Where.ALL_TABLES)
	@Title(sequence = "1.5", prepend = ", ")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea el nombre de la Persona
	 * @param nombre String
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	
	// {{ Documento (property)
	private long documento;

	/**
	 * Pemite obtener un numero de documento a la Persona
	 * @return documento long
	 */
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public long getDocumento() {
		return documento;
	}

	/**
	 * Setea el numero de documento de la Persona
	 * @param documento long
	 */
	public void setDocumento(final long documento) {
		this.documento = documento;
	}

	// {{ Telefono (property)
	private String telefono;

	/**
	 * Pemite obtener un numero de telefono fijo a la Persona
	 * @return telefono String
	 */
	@RegEx(validation = "\\d{7,11}")
	@Optional
	@MemberOrder(sequence = "4")
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Setea el numero de telefono fijo de la Persona
	 * @param telefono String
	 */
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	// {{ Celular (property)
	private String celular;

	/**
	 * Pemite obtener un numero de telefono celular a la Persona
	 * @return celular String
	 */
	@RegEx(validation = "\\d{3,7}(-)?\\d{6}")
	@Optional
	@MemberOrder(sequence = "5")
	public String getCelular() {
		return celular;
	}

	/**
	 * Setea el numero de telefono celular de la Persona
	 * @param celular String
	 */
	public void setCelular(final String celular) {
		this.celular = celular;
	}

	// }}

	// {{ Correo (property)
	private String correo;

	/**
	 * Pemite obtener un correo electronico a la Persona
	 * @return correo String
	 */
	@Hidden(where = Where.ALL_TABLES)
	@Optional
	@MemberOrder(sequence = "6")
	public String getCorreo() {
		return correo;
	}

	/**
	 * Setea el correo electronico de la Persona
	 * @param correo String
	 */
	public void setCorreo(final String correo) {
		this.correo = correo;
	}

	// {{ Direccion (property)
	private String direccion;

	/**
	 * Pemite obtener una direccion de la Persona
	 * @return direccion String
	 */
	@Hidden(where = Where.ALL_TABLES)
	@Optional
	@MultiLine(numberOfLines = 2)
	@Named("Dirección")
	@Column(allowsNull = "true")
	@MemberOrder(sequence = "7")
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Setea la direccion de la Persona
	 * @param correo String
	 */
	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	// {{ Usuario (property)
	private Usuario usuario;

	/**
	 * Permite obtener un nombre de usuario
	 * @return
	 */
	@Hidden
	@Persistent(dependent = "true")
	@Column(allowsNull = "false")
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Setea el usuario 
	 * @param usuario Usuario
	 */
	public void setUsuario(final Usuario usuario) {
		this.usuario = usuario;
	}

	// {{ Baja (property)
	private boolean baja;

	/**
	 * Permite realizar la baja de una persona 
	 * @return baja boolean
	 */
	@Hidden
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public boolean getBaja() {
		return baja;
	}

	/**
	 * Setea la baja de una persona 
	 * @param baja boolean
	 */
	public void setBaja(final boolean baja) {
		this.baja = baja;
	}

	/**
	 * Asigna el estado de alta
	 * @return this Persona
	 */
	public Persona alta() {
		setBaja(false);
		return this;
	}

	/**
	 * Verifica si su estado es alta
	 * @return String
	 */
	public String disableAlta() {
		return getBaja() ? null : "Ya dado de Alta!";
	}

	/**
	 * Asigna el estado de baja
	 * @return this Persona
	 */
	public Persona baja() {
		setBaja(true);
		return this;
	}

	/**
	 * Verifica si su estado es baja
	 * @return String
	 */
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
