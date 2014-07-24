package dom.bebida;

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
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

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

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroBebida", strategy = SequenceStrategy.CONTIGUOUS)
@Queries({
	@Query(name = "todasLasBebidas", language = "JDOQL", value = "SELECT FROM dom.bebida.Bebida")})

public class Bebida {

	// {{ Numero (property)
	private int numero;

	@Named("Número")
	@TypicalLength(3)
	@Disabled
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroBebida")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numeroBebida) {
		this.numero = numeroBebida;
	}

	// }}

	// {{ Tipo (property)
	private TipoBebidaEnum tipo;

	@TypicalLength(10)
	@Optional
	@MemberOrder(sequence = "2")
	public TipoBebidaEnum getTipo() {
		return tipo;
	}

	public void setTipo(final TipoBebidaEnum tipo) {
		this.tipo = tipo;
	}
	// }}


	
	// {{ Nombre (property)
	private String nombre;

	@Title
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombreBebida) {
		this.nombre = nombreBebida;
	}

	// }}

	// {{ Volumen (property)
	private VolumenBebidaEnum volumen;

	@TypicalLength(15)
	@Optional
	@MemberOrder(sequence = "4")
	public VolumenBebidaEnum getVolumen() {
		return volumen;
	}

	public void setVolumen(final VolumenBebidaEnum volumen) {
		this.volumen = volumen;
	}
	// }}


	
	// {{ Descripcion (property)
	private String descripcion;

	@Named("Descripción")
	@MultiLine(numberOfLines = 3)
	@Optional
	@MemberOrder(sequence = "5")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcionBebida) {
		this.descripcion = descripcionBebida;
	}

	// }}

	// {{ Precio (property)
	private double precio;

	@TypicalLength(5)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "6")
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(final double precioBebida) {
		this.precio = precioBebida;
	}

	// }}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public void injectDomainObjectContainer(
			final DomainObjectContainer container) {
		this.setContenedor(container);
	}

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	/*
	 * Inyección del servicio
	 */
	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Bebida> borrarBebida() {
		contenedor.removeIfNotAlready(this);
		return bebidaServicio.listarBebidas();
	}

	private BebidaServicio bebidaServicio;

	public void injectBebidaServicio(final BebidaServicio _bebidaServicio) {
		bebidaServicio = _bebidaServicio;
	}
}
