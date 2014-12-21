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
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;
import dom.cliente.Cliente;
import dom.mesa.Mesa;

/**
 * Crea una reserva que podra realizar cualquier cliente
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroReserva", strategy = SequenceStrategy.CONTIGUOUS)
public class Reserva {

	/**
	 * Asigna el nombre la Reserva
	 * @return String
	 */
	public String iconName() {
		return "Reserva";
	}

	/**
	 * Constructor de la clase
	 */
	public Reserva() {
		// TODO Auto-generated constructor stub
	}

	// {{ Numero (property)
	private int numero;

	/**
	 * Obtiene un numero para la reserva
	 * @return numero int
	 */
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroReserva")
	@Hidden
	@Named("Número")
	@Disabled
	@Title(prepend = "Reserva Nº ")
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getNumero() {
		return numero;
	}

	/**
	 * Setea el numero de una Reserva
	 * @param numero int
	 */
	public void setNumero(final int numero) {
		this.numero = numero;
	}

	
	// {{ Comensales (property)
	private int comensales;

	/**
	 * Permite cargar la cantidad de personas que consumiran en la reserva
	 * @return comensales int
	 */
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public int getComensales() {
		return comensales;
	}

	/**
	 * Setea la cantidad de comensales de la Reserva
	 * @param comensales int
	 */
	public void setComensales(final int comensales) {
		this.comensales = comensales;
	}

	// {{ Mesa (property)
	private Mesa mesa;

	/**
	 * Obtiene una mesa para la Reserva
	 * @return mesa Mesa
	 */
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public Mesa getMesa() {
		return mesa;
	}

	/**
	 * Setea una mesa para la reserva
	 * @param mesa Mesa
	 */
	public void setMesa(final Mesa mesa) {
		this.mesa = mesa;
	}

	// {{ FechaHora (property)
	private Date fechaHora;

	/**
	 * Permite cargar una hora a la Reserva
	 * @return fechaHora Date
	 */
	@Disabled
	@Named("Fecha y Hora")
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "false")
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * Setea la hora de una Reserva
	 * @param fechaHora Date
	 */
	public void setFechaHora(final Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	// {{ Cliente (property)
	private Cliente cliente;

	/**
	 * Permite Obtener un Cliente para la Reserva
	 * @return cliente Cliente
	 */
	@MemberOrder(sequence = "5")
	@Column(allowsNull = "false")
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Setea un Cliente para la Reserva
	 * @param cliente Cliente
	 */
	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Remueve una lista de Reservas
	 * @return List<Reserva>
	 */
	@Bulk
	public List<Reserva> cancelar() {
		contenedor.removeIfNotAlready(this);
		return reservaServicio.listarReservas();
	}

	/**
	 * Inyeccion del contenedor
	 */
	@Inject
	private DomainObjectContainer contenedor;

	/**
	 * inyeccion del servicio de la Reserva
	 */
	@Inject
	private ReservaServicio reservaServicio;

}
