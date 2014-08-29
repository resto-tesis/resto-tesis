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
import java.util.List;

import javax.inject.Inject;
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
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Render.Type;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.mesa.Mesa;
import dom.platoEntrada.PlatoEntrada;
import dom.platoPrincipal.PlatoPrincipal;
import dom.postre.Postre;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroComanda", strategy = SequenceStrategy.CONTIGUOUS)
@Queries({
		@Query(name = "comandasSeleccionadas", language = "JDOQL", value = "SELECT FROM dom.comanda.Comanda where estadoSeleccion == true"),
		@Query(name = "comandasSinPreparacion", language = "JDOQL", value = "SELECT FROM dom.comanda.Comanda where estadoPreparacion == 'Enviada'"),
		@Query(name = "comandasEnPreparacion", language = "JDOQL", value = "SELECT FROM dom.comanda.Comanda where estadoPreparacion == 'En_Preparacion'"),
		@Query(name = "comandasFinalizadas", language = "JDOQL", value = "SELECT FROM dom.comanda.Comanda where estadoPreparacion == 'Finalizada'")

})
public class Comanda {

	// {{ Numero (property)
	private int numero;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroComanda")
	@Disabled
	@TypicalLength(3)
	@Named("Número")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numero) {
		this.numero = numero;
	}

	// }}

	// {{ EstadoPreparacion (property)
	private EstadoComandaEnum estadoPreparacion;

	@Column(allowsNull = "false")
	@Disabled
	@MemberOrder(sequence = "3")
	public EstadoComandaEnum getEstadoPreparacion() {
		return estadoPreparacion;
	}

	public void setEstadoPreparacion(final EstadoComandaEnum estadoPreparacion) {
		this.estadoPreparacion = estadoPreparacion;
	}

	// }}

	// {{ Mesa (property)
	private Mesa mesa;

	@Disabled
	@Title(prepend = "Comanda ")
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(final Mesa mesa) {
		this.mesa = mesa;
	}

	/*
	 * Inyección del servicio
	 */
	@Inject
	private ComandaServicio comandaServicio;

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	@Bulk
	@MemberOrder(sequence = "1")
	public List<Comanda> borrar() {
		contenedor.removeIfNotAlready(this);
		return comandaServicio.listarComanda();
	}

	@Bulk
	@MemberOrder(sequence = "2")
	public List<Comanda> preparar() {
		if (getEstadoPreparacion() == EstadoComandaEnum.En_Espera)
			setEstadoPreparacion(EstadoComandaEnum.En_Preparacion);
		return comandaServicio.listarComanda();

	}

	@Bulk
	@MemberOrder(sequence = "3")
	public List<Comanda> comandaLista() {
		if (getEstadoPreparacion() == EstadoComandaEnum.En_Preparacion
				|| getEstadoPreparacion() == EstadoComandaEnum.En_Espera)
			setEstadoPreparacion(EstadoComandaEnum.Preparada);
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
	public Comanda agregarGuarnicion(
			@Named("Guarnición") final Guarnicion _guarnicion) {
		getGuarniciones().add(_guarnicion);
		return this;
	}
	
	@MemberOrder(name = "guarniciones", sequence = "2")
	public Comanda quitarGuarnicion(
			@Named("Guarnición") final Guarnicion _guarnicion) {
		getGuarniciones().remove(_guarnicion);
		return this;
	}

	public List<Guarnicion> choices0QuitarGuarnicion() {
		return getGuarniciones();
	}

	// {{ Bebidas (Collection)
	private List<Bebida> bebidas = new ArrayList<Bebida>();

	@Render(Type.EAGERLY)
	public List<Bebida> getBebidas() {
		return bebidas;
	}

	public void setBebidas(final List<Bebida> bebidas) {
		this.bebidas = bebidas;
	}

	// }}

	@MemberOrder(name = "bebidas", sequence = "1")
	public Comanda agregarBebida(final Bebida bebida) {
		getBebidas().add(bebida);
		return this;
	}

	@MemberOrder(name = "bebidas", sequence = "2")
	public Comanda quitarBebida(final Bebida bebida) {
		getBebidas().remove(bebida);
		return this;
	}

	public List<Bebida> choices0QuitarBebida() {
		return getBebidas();
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
	public Comanda agregarPostre(final Postre postre) {
		getPostres().add(postre);
		return this;
	}

	@MemberOrder(name = "postres", sequence = "2")
	public Comanda quitarPostre(final Postre postre) {
		getPostres().remove(postre);
		return this;
	}

	public List<Postre> choices0QuitarPostre() {
		return getPostres();
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
	public Comanda agregarPlatoPrincipal(final PlatoPrincipal platoPrincipal) {
		getPlatosPrincipales().add(platoPrincipal);
		return this;
	}

	@MemberOrder(name = "platosPrincipales", sequence = "2")
	public Comanda quitarPlatoPrincipal(final PlatoPrincipal platoPrincipal) {
		getPlatosPrincipales().remove(platoPrincipal);
		return this;
	}

	public List<PlatoPrincipal> choices0QuitarPlatoPrincipal() {
		return getPlatosPrincipales();
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
	public Comanda agregarPlatoEntrada(final PlatoEntrada platoEntrada) {
		getPlatosEntrada().add(platoEntrada);
		return this;
	}

	@MemberOrder(name = "platosEntrada", sequence = "2")
	public Comanda quitarPlatoEntrada(final PlatoEntrada platoEntrada) {
		getPlatosEntrada().remove(platoEntrada);
		return this;
	}

	public List<PlatoEntrada> choices0QuitarPlatoEntrada() {
		return getPlatosEntrada();
	}
}
