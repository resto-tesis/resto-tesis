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

package dom.comandaProducto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Render.Type;

import dom.absComanda.AbsComanda;
import dom.comandaProducto.estado.EnEspera;
import dom.comandaProducto.estado.EnPreparacion;
import dom.comandaProducto.estado.IEstadoComanda;
import dom.comandaProducto.estado.NoConfirmada;
import dom.comandaProducto.estado.Preparada;
import dom.comestibles.guarnicion.Guarnicion;
import dom.comestibles.platoEntrada.PlatoEntrada;
import dom.comestibles.platoPrincipal.PlatoPrincipal;
import dom.comestibles.postre.Postre;
import dom.menu.Menu;
import dom.mesa.Mesa;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroComanda", strategy = SequenceStrategy.CONTIGUOUS)
public class ComandaProducto extends AbsComanda {

	public ComandaProducto() {
		noConfirmada = new NoConfirmada(this);
		enEspera = new EnEspera(this);
		enPreparacion = new EnPreparacion(this);
		preparada = new Preparada(this);
		estado = noConfirmada;
	}

	// {{ Estado (property)
	private IEstadoComanda estado;

	@Disabled
	@Persistent(extensions = {
			@Extension(vendorName = "datanucleus", key = "mapping-strategy", value = "per-implementation"),
			@Extension(vendorName = "datanucleus", key = "implementation-classes", value = "dom.comandaProducto.estado.NoConfirmada"
					+ ",dom.comandaProducto.estado.EnEspera"
					+ ",dom.comandaProducto.estado.EnPreparacion"
					+ ",dom.comandaProducto.estado.Preparada") }, columns = {
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

	// {{ Mesa (property)
	private Mesa mesa;

	@Disabled
	@Title(prepend = "Comanda ")
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(final Mesa mesa) {
		this.mesa = mesa;
	}

	// }}

	/*
	 * Inyección del servicio
	 */
	@Inject
	private ComandaProductoServicio comandaServicio;

	// {{ injected: DomainObjectContainer
	@Inject
	private DomainObjectContainer contenedor;

	@MemberOrder(sequence = "4")
	public ComandaProducto enviar() {
		cambiarEstado();
		return this;
	}

	public String disableEnviar() {
		return estado.Enviar();
	}

	@MemberOrder(sequence = "2")
	public ComandaProducto preparar() {
		cambiarEstado();
		return this;
	}

	public String disablePreparar() {
		return estado.Preparar();
	}

	@MemberOrder(sequence = "3")
	public ComandaProducto comandaLista() {
		cambiarEstado();
		return this;
	}

	public String disableComandaLista() {
		return estado.ComandaFinalizada();
	}

	@Programmatic
	public void cambiarEstado() {
		getEstado().cambiarEstado();
	}

	@Bulk
	@MemberOrder(sequence = "1")
	public List<ComandaProducto> borrar() {
		contenedor.removeIfNotAlready(this);
		return comandaServicio.listarComanda();
	}

	// {{ Guarniciones (Collection)
	private List<Guarnicion> guarniciones = new ArrayList<Guarnicion>();

	@Render(Type.EAGERLY)
	public List<Guarnicion> getGuarniciones() {
		return guarniciones;
	}

	public void setGuarniciones(final List<Guarnicion> guarniciones) {
		this.guarniciones = guarniciones;
	}

	// }}

	@MemberOrder(name = "guarniciones", sequence = "1")
	public ComandaProducto agregarGuarnicion(
			@Named("Guarnición") final Guarnicion _guarnicion) {
		getGuarniciones().add(_guarnicion);
		return this;
	}

	public String validateAgregarGuarnicion(final Guarnicion _guarnicion) {
		return getEstado().validarAgregarGuarnicion();
	}

	@MemberOrder(name = "guarniciones", sequence = "2")
	public ComandaProducto quitarGuarnicion(
			@Named("Guarnición") final Guarnicion _guarnicion) {
		getGuarniciones().remove(_guarnicion);
		return this;
	}

	public List<Guarnicion> choices0QuitarGuarnicion() {
		return getGuarniciones();
	}

	public String validateQuitarGuarnicion(final Guarnicion _guarnicion) {
		return getEstado().validarQuitarGuarnicion();
	}

	// {{ Postres (Collection)
	private List<Postre> postres = new ArrayList<Postre>();

	@Render(Type.EAGERLY)
	public List<Postre> getPostres() {
		return postres;
	}

	public void setPostres(final List<Postre> postres) {
		this.postres = postres;
	}

	// }}

	@MemberOrder(name = "postres", sequence = "1")
	public ComandaProducto agregarPostre(final Postre postre) {
		getPostres().add(postre);
		return this;
	}

	public String validateAgregarPostre(final Postre postre) {
		return getEstado().validarAgregarPostre();
	}

	@MemberOrder(name = "postres", sequence = "2")
	public ComandaProducto quitarPostre(final Postre postre) {
		getPostres().remove(postre);
		return this;
	}

	public List<Postre> choices0QuitarPostre() {
		return getPostres();
	}

	public String validateQuitarPostre(final Postre postre) {
		return getEstado().validarQuitarPostre();
	}

	// {{ PlatosPrincipales (Collection)
	private List<PlatoPrincipal> platosPrincipales = new ArrayList<PlatoPrincipal>();

	@Render(Type.EAGERLY)
	public List<PlatoPrincipal> getPlatosPrincipales() {
		return platosPrincipales;
	}

	public void setPlatosPrincipales(
			final List<PlatoPrincipal> platosPrincipales) {
		this.platosPrincipales = platosPrincipales;
	}

	// }}

	@MemberOrder(name = "platosPrincipales", sequence = "1")
	public ComandaProducto agregarPlatoPrincipal(
			final PlatoPrincipal platoPrincipal) {
		getPlatosPrincipales().add(platoPrincipal);
		return this;
	}

	public String validateAgregarPlatoPrincipal(
			final PlatoPrincipal platoPrincipal) {
		return getEstado().validarAgregarPlatoPrincipal();
	}

	@MemberOrder(name = "platosPrincipales", sequence = "2")
	public ComandaProducto quitarPlatoPrincipal(
			final PlatoPrincipal platoPrincipal) {
		getPlatosPrincipales().remove(platoPrincipal);
		return this;
	}

	public List<PlatoPrincipal> choices0QuitarPlatoPrincipal() {
		return getPlatosPrincipales();
	}

	public String validateQuitarPlatoPrincipal(
			final PlatoPrincipal platoPrincipal) {
		return getEstado().validarQuitarPlatoPrincipal();
	}

	// {{ PlatosEntrada (Collection)
	private List<PlatoEntrada> platosEntrada = new ArrayList<PlatoEntrada>();

	@Render(Type.EAGERLY)
	public List<PlatoEntrada> getPlatosEntrada() {
		return platosEntrada;
	}

	public void setPlatosEntrada(final List<PlatoEntrada> platosEntrada) {
		this.platosEntrada = platosEntrada;
	}

	// }}

	@MemberOrder(name = "platosEntrada", sequence = "1")
	public ComandaProducto agregarPlatoEntrada(final PlatoEntrada platoEntrada) {
		getPlatosEntrada().add(platoEntrada);
		return this;
	}

	public String validateAgregarPlatoEntrada(final PlatoEntrada platoEntrada) {
		return getEstado().validarAgregarPlatoEntrada();
	}

	@MemberOrder(name = "platosEntrada", sequence = "2")
	public ComandaProducto quitarPlatoEntrada(final PlatoEntrada platoEntrada) {
		getPlatosEntrada().remove(platoEntrada);
		return this;
	}

	public List<PlatoEntrada> choices0QuitarPlatoEntrada() {
		return getPlatosEntrada();
	}

	public String validateQuitarPlatoEntrada(final PlatoEntrada platoEntrada) {
		return getEstado().validarQuitarPlatoEntrada();
	}

	// {{ Menues (property)
	private List<Menu> menues;

	@Render(Type.EAGERLY)
	public List<Menu> getMenues() {
		return menues;
	}

	public void setMenues(final List<Menu> menues) {
		this.menues = menues;
	}

	// }}

	@MemberOrder(name = "menues", sequence = "1")
	public ComandaProducto agregarMenu(final Menu menu) {
		getMenues().add(menu);
		return this;
	}

	public String validateAgregarMenu(final Menu menu) {
		return getEstado().validarAgregarMenu();
	}

	@MemberOrder(name = "menues", sequence = "2")
	public ComandaProducto quitarMenu(final Menu menu) {
		getMenues().remove(menu);
		return this;
	}

	public List<Menu> choices0QuitarMenu() {
		return getMenues();
	}

	public String validateQuitarMenu(final Menu menu) {
		return getEstado().validarQuitarMenu();
	}

}