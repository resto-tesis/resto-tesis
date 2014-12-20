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

import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import org.apache.isis.applib.annotation.MemberOrder;

/**
 * Crea un Rol para cada Usuario, el que determinara el nivel de accesibilidad que cada uno
 * tendra en la aplicacion
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Queries({
		@Query(name = "mozo-role", language = "JDOQL", value = "SELECT FROM dom.usuario.Rol where nombre=='mozo-role'"),
		@Query(name = "cocina-role", language = "JDOQL", value = "SELECT FROM dom.usuario.Rol where nombre=='cocina-role'"),
		@Query(name = "encargado-role", language = "JDOQL", value = "SELECT FROM dom.usuario.Rol where nombre=='encargado-role'"),
		@Query(name = "cliente-role", language = "JDOQL", value = "SELECT FROM dom.usuario.Rol where nombre=='cliente-role'") })
public class Rol {

	/**
	 * Constructor de la clase
	 */
	public Rol() {
		// TODO Auto-generated constructor stub
	}

	// {{ Nombre (property)
	private String nombre;

	/**
	 * Permite obtener un nombre al Rol
	 * @return nombre String
	 */
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea el nombre de Rol
	 * @param nombre String 
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	
	// {{ ListaPermiso (Collection)
	private List<Permiso> listaPermiso = new ArrayList<Permiso>();

	/**
	 * Obtiene una lista de Permisos para el Rol 
	 * @return listaPermiso Permiso
	 */
	public List<Permiso> getListaPermiso() {
		return listaPermiso;
	}

	/**
	 * Setea la lista de Permisos de Rol
	 * @param listaPermiso Permiso
	 */
	public void setListaPermiso(final List<Permiso> listaPermiso) {
		this.listaPermiso = listaPermiso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Rol other = (Rol) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
}
