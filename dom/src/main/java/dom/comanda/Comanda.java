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

package dom.comanda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;
import javax.jdo.annotations.Extension;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.comanda.estado.*;
import dom.menu.Menu;
import dom.producto.Producto;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroComanda", strategy = SequenceStrategy.CONTIGUOUS)
public class Comanda {

	public Comanda() {
		enEspera = new EnEspera(this);
		enPreparacion = new EnPreparacion(this);
		noConfirmada = new NoConfirmada(this);
		preparada = new Preparada(this);
		setEstado(noConfirmada);
	}

	// {{ Numero (property)
	private int numero;

	@Named("Número")
	@TypicalLength(3)
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroComanda")
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numeroComanda) {
		this.numero = numeroComanda;
	}

	// }}

	// {{ Mozo (property)
	private String mozo;

	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public String getMozo() {
		return mozo;
	}

	public void setMozo(final String mozo) {
		this.mozo = mozo;
	}

	// }}

	// {{ FechaDePedido (property)
	private Date fechaDePedido;

	@Named("Fecha y Hora")
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public Date getFechaDePedido() {
		return fechaDePedido;
	}

	public void setFechaDePedido(final Date fechaDePedido) {
		this.fechaDePedido = fechaDePedido;
	}

	// }}

	// {{ Productos (Collection)
	private List<Producto> productos = new ArrayList<Producto>();

	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "1")
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(final List<Producto> productos) {
		this.productos = productos;
	}

	// }}

	public void addToProductos(final Producto _producto) {
		productos.add(_producto);
	}

	public void removeFromProductos(final Producto _producto) {
		productos.remove(_producto);
	}

	// {{ Menues (Collection)
	private List<Menu> menues = new ArrayList<Menu>();

	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "2")
	public List<Menu> getMenues() {
		return menues;
	}

	public void setMenues(final List<Menu> menues) {
		this.menues = menues;
	}

	// }}

	public void addToMenues(final Menu _menu) {
		menues.add(_menu);
	}

	public void removeFromMenues(final Menu _menu) {
		menues.remove(_menu);
	}

	// {{ Estado (property)
	private IEstadoComanda estado;

	@Hidden
	@Title(prepend = "Comanda ")
	@Persistent(extensions = {
			@Extension(vendorName = "datanucleus", key = "mapping-strategy", value = "per-implementation"),
			@Extension(vendorName = "datanucleus", key = "implementation-classes", value = "dom.comanda.estado.NoConfirmada"
					+ ",dom.comanda.estado.EnEspera"
					+ ",dom.comanda.estado.EnPreparacion"
					+ ",dom.comanda.estado.Preparada") }, columns = {
			@Column(name = "idNoConfirmada"), @Column(name = "idEnEspera"),
			@Column(name = "idEnPreparacion"), @Column(name = "idPreparada") })
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public IEstadoComanda getEstado() {
		return estado;
	}

	public void setEstado(final IEstadoComanda estado) {
		this.estado = estado;
	}

	// }}

	// ////////////////////////////////////////////Estados//////////////////////////////////////////////

	// {{ NoConfirmada (property)
	private NoConfirmada noConfirmada;

	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public NoConfirmada getNoConfirmada() {
		return noConfirmada;
	}

	public void setNoConfirmada(final NoConfirmada noConfirmada) {
		this.noConfirmada = noConfirmada;
	}

	// }}

	// {{ EnEspera (property)
	private EnEspera enEspera;

	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public EnEspera getEnEspera() {
		return enEspera;
	}

	public void setEnEspera(final EnEspera enEspera) {
		this.enEspera = enEspera;
	}

	// }}

	// {{ EnPreparacion (property)
	private EnPreparacion enPreparacion;

	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public EnPreparacion getEnPreparacion() {
		return enPreparacion;
	}

	public void setEnPreparacion(final EnPreparacion enPreparacion) {
		this.enPreparacion = enPreparacion;
	}

	// }}

	// {{ Preparada (property)
	private Preparada preparada;

	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Preparada getPreparada() {
		return preparada;
	}

	public void setPreparada(final Preparada preparada) {
		this.preparada = preparada;
	}

	// }}

	// ////////////////////////////////////////////Estados//////////////////////////////////////////////

	// /////////////////////////////////////////////////////--Acciones//Comanda--///////////////////////////////////////////////////////

	@MemberOrder(sequence = "4")
	public Comanda enviar() {
		cambiarEstado();
		return this;
	}

	public String disableEnviar() {
		return estado.Enviar();
	}

	@MemberOrder(sequence = "2")
	public Comanda preparar() {
		cambiarEstado();
		return this;
	}

	public String disablePreparar() {
		return estado.Preparar();
	}

	@MemberOrder(sequence = "3")
	public Comanda comandaLista() {
		cambiarEstado();
		return this;
	}

	public String disableComandaLista() {
		return estado.ComandaLista();
	}

	@Programmatic
	public void cambiarEstado() {
		getEstado().cambiarEstado();
	}

	// /////////////////////////////////////////////////////--Acciones//Comanda--///////////////////////////////////////////////////////

}