package dom.usuario;

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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.Hidden;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Usuario {

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	// {{ Nombre (property)
	private String nombre;

	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ Password (property)
	private String password;
	@Hidden 

	@Column(allowsNull = "false")
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	// }}

	// {{ Rol (property)
	private Rol rol;

	@Column(allowsNull = "false")
	public Rol getRol() {
		return rol;
	}

	public void setRol(final Rol rol) {
		this.rol = rol;
	}
	// }}

}
