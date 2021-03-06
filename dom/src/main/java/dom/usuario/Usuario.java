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

package dom.usuario;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;

/**
 * Entidad que representa a cada persona que acceda a la aplicacion
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@Sequence(name = "secuenciaUsuario", strategy = SequenceStrategy.CONTIGUOUS, datastoreSequence = "secuenciaUsuarios")
@DatastoreIdentity(strategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaUsuario")
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Usuario {

	/**
	 * contructor de la clase
	 */
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	// {{ Baja (property)
	private boolean baja;

	/**
	 * Permite obtener el estado de baja de un Usuario
	 * @return baja boolean
	 */
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public boolean getBaja() {
		return baja;
	}

	public void setBaja(final boolean baja) {
		this.baja = baja;
	}

	// {{ Nombre (property)
	private String nombre;

	/**
	 * Permite obtener un nombre al Usuario
	 * @return nombre Strnig
	 */
	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea un nombre para el Usuario
	 * @param nombre String
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ Password (property)
	private String password;

	/**
	 * Permite Obtener un password al Usuario
	 * @return password Strnig
	 */
	@Hidden
	@Column(allowsNull = "false")
	public String getPassword() {
		return password;
	}

	/**
	 * Setea el password al Usuario
	 * @param password String
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	
	// {{ Rol (property)
	private Rol rol;

	/**
	 * Permite obtener un rol al Usuario
	 * @return rol Rol
	 */
	@Column(allowsNull = "false")
	public Rol getRol() {
		return rol;
	}

	/**
	 * Setea el Rol para un Usuario
	 * @param rol Rol
	 */
	public void setRol(final Rol rol) {
		this.rol = rol;
	}

	// }}

	/**
	 * Inyeccion del contenedor
	 */
	@SuppressWarnings("unused")
	@Inject
	private DomainObjectContainer contenedor;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
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
		Usuario other = (Usuario) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
