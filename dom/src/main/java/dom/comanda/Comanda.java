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
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.mesa.Mesa;
import dom.plato.PlatoEntrada;
import dom.plato.PlatoPrincipal;
import dom.postre.Postre;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroComanda", strategy = SequenceStrategy.CONTIGUOUS)
public class Comanda {

	public Comanda() {
		// TODO Auto-generated constructor stub
	}

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

	// {{ Mesa (property)
	private Mesa mesa;

	@Title(prepend = "Comanda ")
	@MemberOrder(sequence = "2")
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

	@MemberOrder(sequence = "3")
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

	@MemberOrder(sequence = "4")
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

	@MemberOrder(sequence = "6")
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
	@MemberOrder(sequence = "5")
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
}
