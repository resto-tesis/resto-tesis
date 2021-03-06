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
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
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
import dom.empleado.Empleado;
import dom.objetosValor.ValueMenu;
import dom.objetosValor.ValueOferta;
import dom.objetosValor.ValueProductoElaborado;

/**
 * Entidada que representa lo que se consumira en cada mesa,
 * conteniendo una cantidad de pedidos que se realicen en la misma.-
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroComanda", strategy = SequenceStrategy.CONTIGUOUS)
public class Comanda {

	/**
	 * Retorna el nombre del icono segun el estado
	 * @return String
	 */
	public String iconName() {
		return getEstado().iconName();
	}

	/**
	 * Constructor de la Entidad Comanda, donde se le agrega el estado
	 */
	public Comanda() {
		enEspera = new EnEspera(this);
		enPreparacion = new EnPreparacion(this);
		noConfirmada = new NoConfirmada(this);
		preparada = new Preparada(this);
		setEstado(noConfirmada);
	}

	// {{ Numero (property)
	private int numero;

	/**
	 * Obtiene el numero de una nueva comanda, el cual se genera en forma automatica
	 * @return numero int
	 */
	@Named("Número")
	@TypicalLength(3)
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroComanda")
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	/**
	 * Setea el numero de la Comanda que se va a crear.
	 * @param numeroComanda int
	 */
	public void setNumero(final int numeroComanda) {
		this.numero = numeroComanda;
	}

	// }}

	// {{ Mozo (property)
	private Empleado mozo;

	/**
	 * Obtiene un Mozo
	 * @return mozo Empleado
	 */
	@Disabled
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public Empleado getMozo() {
		return mozo;
	}

	/**
	 * Setea el mozo
	 * @param mozo Empleado
	 */
	public void setMozo(final Empleado mozo) {
		this.mozo = mozo;
	}

	// {{ FechaDePedido (property)
	private Date fechaDePedido;

	/**
	 * Obtiene la fecha de un pedido
	 * @return fechaDePedido Date
	 */
	@Disabled
	@Named("Fecha y Hora")
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public Date getFechaDePedido() {
		return fechaDePedido;
	}

	/**
	 * Setea la fecha de un pedido
	 * @param fechaDePedido Date
	 */
	public void setFechaDePedido(final Date fechaDePedido) {
		this.fechaDePedido = fechaDePedido;
	}

	// {{ Productos (Collection)
	private List<ValueProductoElaborado> productos = new ArrayList<ValueProductoElaborado>();

	/**
	 * Obtiene una lista de Productos Elaborados
	 * @return productos List<ValueProductosElaborados>
	 */
	@Persistent(dependentElement = "true")
	@Join(deleteAction = ForeignKeyAction.CASCADE)
	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "1")
	public List<ValueProductoElaborado> getProductos() {
		return productos;
	}

	/**
	 * Setea una lista de productos elaborados
	 * @param productos List<ValueProductosElaborados>
	 */
	public void setProductos(final List<ValueProductoElaborado> productos) {
		this.productos = productos;
	}

	/**
	 * Agrega un elemento a la lista de productos elaborados
	 * @param _productos ValueProductosElaborados
	 */
	public void addToProductos(final ValueProductoElaborado _producto) {
		getProductos().add(_producto);
	}

	/**
	 * Remueve un elemento a la lista de productos elaborados
	 * @param _productos ValueProductosElaborados
	 */
	public void removeFromProductos(final ValueProductoElaborado _producto) {
		getProductos().remove(_producto);
	}

	// {{ Menues (Collection)
	private List<ValueMenu> menues = new ArrayList<ValueMenu>();

	/**
	 * Obtiene una lista de menues
	 * @return menues List<ValueMenu>
	 */
	@Persistent(dependentElement = "true")
	@Join(deleteAction = ForeignKeyAction.CASCADE)
	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "2")
	public List<ValueMenu> getMenues() {
		return menues;
	}

	/**
	 * Setea una lista de menues
	 * @param menues List<ValueMenu>
	 */
	public void setMenues(final List<ValueMenu> menues) {
		this.menues = menues;
	}

	/**
	 * Agrega un elemento a la lista de menues
	 * @param _menu ValueMenu 
	 */
	public void addToMenues(final ValueMenu _menu) {
		getMenues().add(_menu);
	}

	/**
	 * Remueve un elemento de la lista de menues
	 * @param _menu ValueMenu 
	 */
	public void removeFromMenues(final ValueMenu _menu) {
		getMenues().remove(_menu);
	}

	// {{ Ofertas (Collection)
	private List<ValueOferta> ofertas = new ArrayList<ValueOferta>();

	/**
	 * Permite obtener Ofertas para la comanda
	 * @return list<Oferta>
	 */
	@Persistent(dependentElement = "true")
	@Join(deleteAction = ForeignKeyAction.CASCADE)
	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "3")
	public List<ValueOferta> getOfertas() {
		return ofertas;
	}

	/**
	 * Setea las ofertas en la Comanda
	 * @param ofertas List<Oferta>
	 */
	public void setOfertas(final List<ValueOferta> ofertas) {
		this.ofertas = ofertas;
	}

	/**
	 * Agrega Ofertas a la Comanda
	 * @param _oferta ValueOferta
	 */
	public void addToOfertas(final ValueOferta _oferta) {
		getOfertas().add(_oferta);
	}

	/**
	 * Elimina una Oferta de la Comanda
	 * @param _oferta ValueOferta
	 */
	public void removeFromOfertas(final ValueOferta _oferta) {
		getOfertas().remove(_oferta);
	}

	// {{ Estado (property)
	private IEstadoComanda estado;

	/**
	 * Obtiene el estado de una comanda
	 * @return estado IEstadoComanda
	 */
	@Hidden
	@Title(prepend = "Comanda ")
	@Persistent(extensions = {
			@Extension(vendorName = "datanucleus", key = "mapping-strategy", value = "per-implementation"),
			@Extension(vendorName = "datanucleus", key = "implementation-classes", value = "dom.comanda.estado.NoConfirmada"
					+ ",dom.comanda.estado.EnEspera"
					+ ",dom.comanda.estado.EnPreparacion"
					+ ",dom.comanda.estado.Preparada") }, columns = {
			@Column(name = "idNoConfirmada"), @Column(name = "idEnEspera"),
			@Column(name = "idEnPreparacion"), @Column(name = "idPreparada") }, dependent = "true")
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public IEstadoComanda getEstado() {
		return estado;
	}

	/**
	 * Setea el estado de una comanda
	 * @param estado IEstadoComanda 
	 */
	public void setEstado(final IEstadoComanda estado) {
		this.estado = estado;
	}

	// ////////////////////////////////////////////Estados//////////////////////////////////////////////

	// {{ NoConfirmada (property)
	private NoConfirmada noConfirmada;

	/**
	 * Obtiene el estado particular NoConfirmada para la Comanda
	 * @return noConfirmada NoConfirmada 
	 */
	@Persistent(dependent = "true")
	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public NoConfirmada getNoConfirmada() {
		return noConfirmada;
	}

	/**
	 * Setea el estado particular NoConfirmada
	 * @param noConformada NoConfirmada
	 */
	public void setNoConfirmada(final NoConfirmada noConfirmada) {
		this.noConfirmada = noConfirmada;
	}

	// {{ EnEspera (property)
	private EnEspera enEspera;

	/**
	 * Obtiene el estado particular EnEspera para la Comanda
	 * @return enEspera EnEspera
	 */
	@Persistent(dependent = "true")
	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public EnEspera getEnEspera() {
		return enEspera;
	}

	/**
     * Setea el estado particular EnEspera 
	 * @param enEspera EnEspera
	 */
	public void setEnEspera(final EnEspera enEspera) {
		this.enEspera = enEspera;
	}

	// {{ EnPreparacion (property)
	private EnPreparacion enPreparacion;

	/**
	 * Obtiene el estado particular EnPreparacion para la Comanda
	 * @return enPreparacion EnPreparacion
	 */
	@Persistent(dependent = "true")
	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public EnPreparacion getEnPreparacion() {
		return enPreparacion;
	}

	/**
	 * Setea el estado particular EnPreparacion 
	 * @param enPreparacion EnPreparacion
	 */
	public void setEnPreparacion(final EnPreparacion enPreparacion) {
		this.enPreparacion = enPreparacion;
	}

	// {{ Preparada (property)
	private Preparada preparada;

	/**
	 * Obtiene el estado particular Preparada para la Comanda
	 * @return preparada Preparada
	 */
	@Persistent(dependent = "true")
	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Preparada getPreparada() {
		return preparada;
	}

	/**
	 * Setea el estado particular Preparada
	 * @param preparada Preparada
	 */
	public void setPreparada(final Preparada preparada) {
		this.preparada = preparada;
	}

	/**
	 * Cambia al estado de la Comanda
	 * 
	 * @return this
	 */
	@MemberOrder(sequence = "2")
	public Comanda preparar() {
		cambiarEstado();
		return this;
	}

	/**
	 * Cambia al estado de la Comanda
	 * 
	 * @return String
	 */
	public String disablePreparar() {
		return estado.Preparar();
	}

	/**
	 * Cambia al estado de la Comanda
	 * 
	 * @return this
	 */
	@MemberOrder(sequence = "3")
	public Comanda comandaLista() {
		cambiarEstado();
		return this;
	}

	/**
	 * Cambia al estado de la Comanda
	 * 
	 * @return String
	 */
	public String disableComandaLista() {
		return estado.ComandaLista();
	}

	/**
	 * Cambia e estado de una Comanda
	 */
	@Programmatic
	public void cambiarEstado() {
		getEstado().cambiarEstado();
	}

	
}