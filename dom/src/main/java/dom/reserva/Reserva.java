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

package dom.reserva;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;

import dom.mesa.Mesa;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Reserva {

	public Reserva() {
		// TODO Auto-generated constructor stub
	}

	// {{ Numero (property)
	private int numero;

	@Hidden
	@Named("Número")
	@Disabled
	@Title(prepend = "Reserva Nº ")
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numero) {
		this.numero = numero;
	}

	// }}

	// {{ Comensales (property)
	private int comensales;

	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public int getComensales() {
		return comensales;
	}

	public void setComensales(final int comensales) {
		this.comensales = comensales;
	}

	// }}

	// {{ Mesa (property)
	private Mesa mesa;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(final Mesa mesa) {
		this.mesa = mesa;
	}

	// }}

	// {{ FechaHora (property)
	private Date fechaHora;

	@Disabled
	@Named("Fecha y Hora")
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "false")
	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(final Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	// }}

	// {{ Cliente (property)
	private String cliente;

	@Disabled
	@MemberOrder(sequence = "5")
	@Column(allowsNull = "false")
	public String getCliente() {
		return cliente;
	}

	public void setCliente(final String cliente) {
		this.cliente = cliente;
	}

	// }}

	@Bulk
	public List<Reserva> borrar() {
		contenedor.removeIfNotAlready(this);
		return reservaServicio.listarReservas();
	}

	private DomainObjectContainer contenedor;

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer _contenedor) {
		contenedor = _contenedor;
	}

	@Inject
	private ReservaServicio reservaServicio;

}
