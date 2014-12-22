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

package dom.mesa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Title;

import dom.factura.Factura;
import dom.mozo.Mozo;
import dom.pedido.Pedido;

/**
 * Entidad que representa una Mesa donde se sentaran los clientes
 * para realizar las consumisiones
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Mesa {

	/**
	 * Obtiene el nombre del icono segun su estado de asignacion y ocupacion
	 * @return String
	 */
	public String iconName() {
		if (getEstadoAsignacion() == EstadoAsignacionMesaEnum.No_Asignada)
			return "MesaNoAsig";
		if (getEstadoHabilitacion() == EstadoHabilitacionMesaEnum.Ocupada)
			return "MesaOcupada";
		return "Mesa";
	}

	// {{ EstadoSeleccion (property)
	private boolean estadoSeleccion;

	/**
	 * Obtiene el estado de seleccion de una mesa
	 * @return estadoSeleccion boolean
	 */
	@Hidden
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public boolean getEstadoSeleccion() {
		return estadoSeleccion;
	}
	
	/**
	 * Setea el estado de seleccion de una mesa
	 * @param estadoSeleccion boolean
	 */ 
	public void setEstadoSeleccion(final boolean estadoSeleccion) {
		this.estadoSeleccion = estadoSeleccion;
	}

	/**
	 * Obtiene una lista de mozos para asignar mesa
	 * @return list<Mozo>
	 */
	@Bulk
	@MemberOrder(sequence = "2")
	public List<Mozo> seleccionar() {
		if (estadoAsignacion == EstadoAsignacionMesaEnum.No_Asignada)
			setEstadoSeleccion(true);
		return mesaServicio.listarMozos();
	}
	
	/**
	 * Obtiene e informa el estado de asignacion de la mesa (Asignada) 
	 * @return String 
	 */
	public String disableSeleccionar() {
		return (estadoAsignacion == EstadoAsignacionMesaEnum.Asignada) ? "Mesa Ya Asignada."
				: null;
	}

	// {{ Numero (property)
	private int numero;

	/**
	 * Obtiene el numero de la Mesa que se va a crear.
	 * @return numero int
	 */
	@Named("Número")
	@Disabled
	@Title(prepend = "Mesa Nro. ")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public int getNumero() {
		return numero;
	}
	
	/**
	 * Setea el numero de la Mesa que se va a crear.
	 * @param numeroMesa int
	 */
	public void setNumero(final int numeroMesa) {
		this.numero = numeroMesa;
	}

	// {{ Capacidad (property)
	private int capacidad;

	/**
	 * Obtiene la capacidad de la Mesa que se va a crear.
	 * @return capacidad int
	 */
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * Setea la capacidad de la Mesa.
	 * @param capacidadMesa int
	 */
	public void setCapacidad(final int capacidadMesa) {
		this.capacidad = capacidadMesa;
	}

	// {{ EstadoHabilitacion (property)
	private EstadoHabilitacionMesaEnum estadoHabilitacion;

	/**
	 * Obtiene el estado de habilitacion de una Mesa
	 * @return estadoHabilitacionMesa Enum 
	 */
	@Disabled
	@Named("Ocupación")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public EstadoHabilitacionMesaEnum getEstadoHabilitacion() {
		return estadoHabilitacion;
	}
	/**
	 * Setea el estado de habilitacion de una mesa
	 * @param estadoHabilitacionMesa Enum
	 */
	public void setEstadoHabilitacion(
			final EstadoHabilitacionMesaEnum estadoHabilitacionMesa) {
		this.estadoHabilitacion = estadoHabilitacionMesa;
	}

	// {{ EstadoAsignacion (property)
	private EstadoAsignacionMesaEnum estadoAsignacion;

	/**
	 * Obtiene el estado de asignacon de una Mesa
	 * @return estadoAsignacion Enum
	 */
	@Named("Asignación")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	@Disabled
	public EstadoAsignacionMesaEnum getEstadoAsignacion() {
		return estadoAsignacion;
	}

	/**
	 * Setea el estado de asignacion de una mesa
	 * @param estadoAsignacion Enum
	 */
	public void setEstadoAsignacion(
			final EstadoAsignacionMesaEnum estadoAsignacionMesa) {
		this.estadoAsignacion = estadoAsignacionMesa;
	}

	// {{ Pedidos (Collection)
	@Persistent(mappedBy = "mesa")
	@Join
	private List<Pedido> pedidos = new ArrayList<Pedido>();

	/**
	 * Obtine una lista de pedidos 
	 * @return List<Pedido> pedidos
	 */
	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "1")
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	/**
	 * Setea una lista de pedidos 
	 * @param pedidos Pedido
	 */
	public void setPedidos(final List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	/**
	 * Agrega un pedido a la lista de pedidos 
	 * @param _pedido Pedido
	 */
	@Programmatic
	public void addToPedidos(final Pedido _pedido) {
		getPedidos().add(_pedido);
	}

	/**
	 * Remueve un pedido a la lista de pedidos 
	 * @param _pedido Pedido
	 */
	@Programmatic
	public void removeFromPedidos(final Pedido _pedido) {
		getPedidos().remove(_pedido);
	}

	/**
	 * Permite tomar un pedido 
	 * @param _pedido Pedido
	 */
	@MemberOrder(name = "pedidos", sequence = "1")
	public Pedido tomarPedido() {
		return mesaServicio.tomarPedido(this);
	}

	/**
	 * Define el estado de asignacion de una mesa 
	 * @return String
	 */
	public String disableTomarPedido() {
		return (getEstadoAsignacion() == EstadoAsignacionMesaEnum.Asignada) ? null
				: "Mesa sin Asignar!";
	}

	/**
	 * Borra un pedido efectuado
	 * @param _pedido Pedido
	 * @see dom.pedido.Pedido.getBebidas()
	 * @see dom.pedido.Pedido.getComanda()
	 * @see dom.comanda.Comanda.getMenues()
	 * @see dom.comanda.Comanda.getProductos()
	 * @return this
	 */
	@MemberOrder(name = "pedidos", sequence = "2")
	public Mesa borrarPedido(@Named("Pedido") Pedido _pedido) {
		if (!_pedido.getBebidas().isEmpty()
				|| !_pedido.getComanda().getMenues().isEmpty()
				|| !_pedido.getComanda().getProductos().isEmpty()
				|| !_pedido.getComanda().getOfertas().isEmpty()) {
			contenedor.informUser("El Pedido seleccionado no está vacío!");
		} else {
			removeFromPedidos(_pedido);
		}
		if(getPedidos().isEmpty())
			setEstadoHabilitacion(EstadoHabilitacionMesaEnum.Desocupada);
		return this;
	}

	/**
	 * Obtine una lista de padidos para su borrado
	 * @return List<Pedido>
	 */
	public List<Pedido> choices0BorrarPedido() {
		return getPedidos();
	}

	/**
	 * Verifica si existen pedidos realizados
	 * @param _pedido Pedido
	 * @return String
	 */
	public String disableBorrarPedido(Pedido _pedido) {
		return getPedidos().isEmpty() ? "No Existen Pedidos" : null;
	}
	
	/**
	 * Permite Facturar una mesa
	 * @see dom.mesa.MesaServicio.facturar
	 * @return factura Factura
	 */
	public Factura facturar() {
		return mesaServicio.facturar(this);
	}

	/**
	 * Permite validar un pedido antes de ser facturado
	 * @return String
	 */
	public String disableFacturar() {
		return mesaServicio.validarFactturado(this);
	}

	/**
	 * Inyeccion del Contenedor
	 */
	@Inject
	private DomainObjectContainer contenedor;

	/**
	 * Inyección del servicio de la clase MesaServicio
	 */
	@Inject
	private MesaServicio mesaServicio;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estadoAsignacion == null) ? 0 : estadoAsignacion.hashCode());
		result = prime
				* result
				+ ((estadoHabilitacion == null) ? 0 : estadoHabilitacion
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mesa other = (Mesa) obj;
		if (estadoAsignacion != other.estadoAsignacion)
			return false;
		if (estadoHabilitacion != other.estadoHabilitacion)
			return false;
		return true;
	}

}
