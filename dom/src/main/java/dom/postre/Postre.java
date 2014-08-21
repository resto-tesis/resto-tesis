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

package dom.postre;

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.postre.Postre;
import dom.postre.PostreServicio;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroPostre", strategy = SequenceStrategy.CONTIGUOUS)
@Queries({
		@Query(name = "todosLosPostres", language = "JDOQL", value = "SELECT FROM dom.postre.Postre"),
		@Query(name = "postresQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.postre.Postre WHERE nombre.matches(:nombre)") })
@AutoComplete(repository = PostreServicio.class, action = "completarPostres")
public class Postre {

	// {{ Numero (property)
	private int numero;

	@Named("Número")
	@TypicalLength(3)
	@Disabled
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroPostre")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numeroPostre) {
		this.numero = numeroPostre;
	}

	// }}

	// {{ Nombre (property)
	private String nombre;

	@RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*")
	@MaxLength(value = 30)
	@Title
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombrePostre) {
		this.nombre = nombrePostre;
	}

	// }}

	// {{ Descripcion (property)
	private String descripcion;

	@Named("Descripción")
	@MultiLine(numberOfLines = 3)
	@Optional
	@MemberOrder(sequence = "3")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcionPostre) {
		this.descripcion = descripcionPostre;
	}

	// }}

	// {{ Precio (property)
	private double precio;

	@TypicalLength(5)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(final double precioPostre) {
		this.precio = precioPostre;
	}

	// }}

	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Postre> borrar() {
		if (postreServicio.validaBorrado(this))
			contenedor.removeIfNotAlready(this);
		else
			contenedor.informUser("Existe un Menu o Comanda dependiente!!");
		return postreServicio.listarPostres();
	}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	/*
	 * Inyección del servicio
	 */
	private PostreServicio postreServicio;

	public void injectarPostreServicio(final PostreServicio serviciopostre) {
		this.postreServicio = serviciopostre;
	}

}
