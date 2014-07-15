package dom.menu;

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

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.plato.PlatoEntrada;
import dom.plato.PlatoPrincipal;
import dom.postre.Postre;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroMenu", strategy = SequenceStrategy.CONTIGUOUS)
public class Menu {
	// {{ Numero (property)
	private int numero;

	@TypicalLength(3)
	@Disabled
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroGuarnicion")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numero) {
		this.numero = numero;
	}

	// }}

	// {{ Nombre (property)
	private String nombre;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	@Title
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ PlatoPrincipal (property)
	private PlatoPrincipal platoPrincipal;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public PlatoPrincipal getPlatoPrincipal() {
		return platoPrincipal;
	}

	public void setPlatoPrincipal(final PlatoPrincipal platoPrincipal) {
		this.platoPrincipal = platoPrincipal;
	}

	// }}

	// {{ Bebida (property)
	private Bebida bebida;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(final Bebida bebida) {
		this.bebida = bebida;
	}

	// }}

	// {{ Guarnicion (property)
	private Guarnicion guarnicion;

	@Optional
	@MemberOrder(sequence = "1")
	public Guarnicion getGuarnicion() {
		return guarnicion;
	}

	public void setGuarnicion(final Guarnicion guarnicion) {
		this.guarnicion = guarnicion;
	}

	// }}

	// {{ PlatoEntrada (property)
	private PlatoEntrada platoEntrada;

	@Optional
	@MemberOrder(sequence = "1")
	public PlatoEntrada getPlatoEntrada() {
		return platoEntrada;
	}

	public void setPlatoEntrada(final PlatoEntrada platoEntrada) {
		this.platoEntrada = platoEntrada;
	}

	// }}

	// {{ Postre (property)
	private Postre postre;

	@Optional
	@MemberOrder(sequence = "1")
	public Postre getPostre() {
		return postre;
	}

	public void setPostre(final Postre postre) {
		this.postre = postre;
	}

	// }}

	// {{ Descuento (property)
	private int descuento;

	@Named("Descuento (%)")
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(final int descuento) {
		this.descuento = descuento;
	}

	// }}

	// {{ PrecioFinal (property)
//	private double precioFinal;

	@Disabled
	@MemberOrder(sequence = "1")
	public double getPrecioFinal() {
		// return precioFinal;
		return menuServicio.calcularDescuento(this);
	}

//	public void setPrecioFinal(final double precioFinal) {
//		this.precioFinal = precioFinal;
//	}

	// }}

	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Menu> borrar() {
		contenedor.removeIfNotAlready(this);
		return menuServicio.listarMenues();
	}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	private MenuServicio menuServicio;

	public void injectMenuServicio(final MenuServicio _menuServicio) {
		menuServicio = _menuServicio;
	}
}
