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

import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;
import dom.comandaEstadoFactura.Facturada;
import dom.comandaEstadoFactura.IEstadoFactura;
import dom.comandaEstadoFactura.NoFacturada;
import dom.mesa.Mesa;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Sequence(name = "secuenciaNumeroComanda", strategy = SequenceStrategy.CONTIGUOUS)
public abstract class Comanda {

	public Comanda() {
		facturada = new Facturada(this);
		noFacturada = new NoFacturada(this);
		estadoFactura = noFacturada;
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

	@MemberOrder(sequence = "1")
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
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Date getFechaDePedido() {
		return fechaDePedido;
	}

	public void setFechaDePedido(final Date fechaDePedido) {
		this.fechaDePedido = fechaDePedido;
	}

	// }}

	// {{ Estado (property)
	private IEstadoFactura estadoFactura;

	@Disabled
	@Persistent(extensions = {
			@Extension(vendorName = "datanucleus", key = "mapping-strategy", value = "per-implementation"),
			@Extension(vendorName = "datanucleus", key = "implementation-classes", value = "dom.comandaEstadoFactura.Facturada"
					+ ",dom.comandaEstadoFactura.NoFacturada") }, columns = {
			@Column(name = "idFacturada"), @Column(name = "idNoFacturada") })
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public IEstadoFactura getEstadoFactura() {
		return estadoFactura;
	}

	public void setEstadoFactura(final IEstadoFactura estado) {
		this.estadoFactura = estado;

	}

	// {{ Facturada (property)
	private Facturada facturada;

	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Facturada getFacturada() {
		return facturada;
	}

	public void setFacturada(final Facturada facturada) {
		this.facturada = facturada;
	}

	// }}

	// {{ Nofactura (property)
	private NoFacturada noFacturada;

	@Hidden
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public NoFacturada getNofactura() {
		return noFacturada;
	}

	public void setNofactura(final NoFacturada noFacturada) {
		this.noFacturada = noFacturada;
	}
	// }}

}