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

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optional;
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

	@Column(allowsNull = "false")
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
}
