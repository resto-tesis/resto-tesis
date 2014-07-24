package dom.comanda;

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
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.bebida.Bebida;
import dom.cocinero.Cocinero;
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

	@Disabled
	@TypicalLength(3)
	@Named("Número")
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroComanda")
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
	@MemberOrder(sequence = "2")
	public EstadoComandaEnum getEstadoPreparacion() {
		return estadoPreparacion;
	}

	public void setEstadoPreparacion(final EstadoComandaEnum estadoPreparacion) {
		this.estadoPreparacion = estadoPreparacion;
	}
	// }}

	// {{ EstadoSeleccion (property)
	private boolean estadoSeleccion;

	@Hidden
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public boolean getEstadoSeleccion() {
		return estadoSeleccion;
	}

	public void setEstadoSeleccion(final boolean estadoSeleccion) {
		this.estadoSeleccion = estadoSeleccion;
	}
	// }}

	// {{ Mesa (property)
	private Mesa mesa;

	@Title(prepend = "Comanda ")
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "false")
	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(final Mesa mesa) {
		this.mesa = mesa;
	}

	// }}

	// {{ PlatoEntrada (property)
	private PlatoEntrada platoEntrada;

	@MemberOrder(sequence = "5")
	@Optional
	public PlatoEntrada getPlatoEntrada() {
		return platoEntrada;
	}

	public void setPlatoEntrada(final PlatoEntrada platoEntrada) {
		this.platoEntrada = platoEntrada;
	}

	// }}

	// {{ PlatoPrincipal (property)
	private PlatoPrincipal platoPrincipal;

	@MemberOrder(sequence = "6")
	@Optional
	public PlatoPrincipal getPlatoPrincipal() {
		return platoPrincipal;
	}

	public void setPlatoPrincipal(final PlatoPrincipal platoPrincipal) {
		this.platoPrincipal = platoPrincipal;
	}

	// }}

	// {{ Bebida (property)
	private Bebida bebida;

	@MemberOrder(sequence = "7")
	@Optional
	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(final Bebida bebida) {
		this.bebida = bebida;
	}

	// }}

	// {{ Postre (property)
	private Postre postre;

	@MemberOrder(sequence = "8")
	@Optional
	public Postre getPostre() {
		return postre;
	}

	public void setPostre(final Postre postre) {
		this.postre = postre;
	}

	// }}

	// {{ Guarnicion (property)
	private Guarnicion guarnicion;

	@Named("Guarnición")
	@MemberOrder(sequence = "9")
	@Optional
	public Guarnicion getGuarnicion() {
		return guarnicion;
	}

	public void setGuarnicion(final Guarnicion guarnicion) {
		this.guarnicion = guarnicion;
	}

	// }}

	public List<Mesa> choicesMesa() {
		return comandaServicio.choices0CrearComanda();
	}

	public List<PlatoEntrada> choicesPlatoEntrada() {
		return comandaServicio.choices1CrearComanda();
	}

	public List<PlatoPrincipal> choicesPlatoPrincipal() {
		return comandaServicio.choices2CrearComanda();
	}

	public List<Bebida> choicesBebida() {
		return comandaServicio.choices5CrearComanda();
	}

	public List<Postre> choicesPostre() {
		return comandaServicio.choices4CrearComanda();
	}

	public List<Guarnicion> choicesGuarnicion() {
		return comandaServicio.choices3CrearComanda();
	}

	/*
	 * Inyección del servicio
	 */
	private ComandaServicio comandaServicio;

	public void injectarComandaServicio(final ComandaServicio _comandaServicio) {
		this.comandaServicio = _comandaServicio;
	}
	
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
	
	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Comanda> borrar() {
		contenedor.removeIfNotAlready(this);
		return comandaServicio.listarComanda();
	}
			
	@Named("Enviar a Cocina")	
	@Bulk
	@MemberOrder(sequence = "2")
	public List<Cocinero> enviarComandaCocina() {
		comandaServicio.enviarComandaACocina(this);
		return comandaServicio.listarCocineros();
		
	}
	
	@Named("Finalizar")
	@Bulk
	@MemberOrder(sequence = "3")
	public List<Cocinero> finalizarComandas() {
		comandaServicio.FinalizarComanda(this);
		return comandaServicio.listarCocineros();
		
	}
}
