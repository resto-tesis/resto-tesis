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
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

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

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Queries({
		@Query(name = "mesasSeleccionadas", language = "JDOQL", value = "SELECT FROM dom.mesa.Mesa where estadoSeleccion == true"),
		@Query(name = "mesasSinAsignar", language = "JDOQL", value = "SELECT FROM dom.mesa.Mesa where estadoAsignacion == 'No_Asignada'"),
		@Query(name = "mesasAsignadas", language = "JDOQL", value = "SELECT FROM dom.mesa.Mesa where estadoAsignacion == 'Asignada'") })
public class Mesa {

	// {{ EstadoSeleccion (property)
	private boolean estadoSeleccion;

	@Hidden
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public boolean getEstadoSeleccion() {
		return estadoSeleccion;
	}

	public void setEstadoSeleccion(final boolean estadoSeleccion) {
		this.estadoSeleccion = estadoSeleccion;
	}

	// }}

	@Bulk
	@MemberOrder(sequence = "2")
	public List<Mozo> seleccionar() {
		if (estadoAsignacion == EstadoAsignacionMesaEnum.No_Asignada)
			setEstadoSeleccion(true);
		return mesaServicio.listaDeMozos();
	}

	public String disableSeleccionar() {
		return (estadoAsignacion == EstadoAsignacionMesaEnum.Asignada) ? "Mesa Ya Asignada."
				: null;
	}

	// {{ Numero (property)
	private int numero;

	@Named("Número")
	@Disabled
	@Title(prepend = "Mesa Nº ")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numeroMesa) {
		this.numero = numeroMesa;
	}

	// }}

	// {{ Capacidad (property)
	private int capacidad;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(final int capacidadMesa) {
		this.capacidad = capacidadMesa;
	}

	// }}

	// {{ EstadoHabilitacion (property)
	private EstadoHabilitacionMesaEnum estadoHabilitacion;

	@Disabled
	@Named("Ocupación")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public EstadoHabilitacionMesaEnum getEstadoHabilitacion() {
		return estadoHabilitacion;
	}

	public void setEstadoHabilitacion(
			final EstadoHabilitacionMesaEnum estadoHabilitacionMesa) {
		this.estadoHabilitacion = estadoHabilitacionMesa;
	}

	// }}

	// {{ EstadoAsignacion (property)
	private EstadoAsignacionMesaEnum estadoAsignacion;

	@Named("Asignación")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	@Disabled
	public EstadoAsignacionMesaEnum getEstadoAsignacion() {
		return estadoAsignacion;
	}

	public void setEstadoAsignacion(
			final EstadoAsignacionMesaEnum estadoAsignacionMesa) {
		this.estadoAsignacion = estadoAsignacionMesa;
	}

	// }}

	// {{ Pedidos (Collection)
	private List<Pedido> pedidos = new ArrayList<Pedido>();

	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "1")
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(final List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	// }}

	@Programmatic
	public void addToPedidos(final Pedido _pedido) {
		getPedidos().add(_pedido);
	}

	@Programmatic
	public void removeFromPedidos(final Pedido _pedido) {
		getPedidos().remove(_pedido);
	}

	@MemberOrder(name = "pedidos", sequence = "1")
	public Pedido tomarPedido() {
		return mesaServicio.tomarPedido(this);
	}

	public String disableTomarPedido() {
		return (getEstadoAsignacion() == EstadoAsignacionMesaEnum.Asignada) ? null
				: "Mesa sin Asignar!";
	}

	@MemberOrder(name = "pedidos", sequence = "2")
	public Mesa borrarPedido(@Named("Pedido") Pedido _pedido) {
		if (!_pedido.getBebidas().isEmpty()
				|| !_pedido.getComanda().getMenues().isEmpty()
				|| !_pedido.getComanda().getProductos().isEmpty()) {
			contenedor.informUser("El Pedido seleccionado no está vacío!");
		} else {
			removeFromPedidos(_pedido);
		}
		return this;
	}

	public List<Pedido> choices0BorrarPedido() {
		return getPedidos();
	}

	public String disableBorrarPedido(Pedido _pedido) {
		return getPedidos().isEmpty() ? "No Existen Pedidos" : null;
	}

	public Factura facturar() {
		return mesaServicio.facturar(this);
	}

	public String disableFacturar() {
		if (getPedidos().isEmpty())
			return "Mesa sin Pedidos";
		for (Pedido _pedido : getPedidos()) {
			if (_pedido.getBebidas().isEmpty()
					&& (_pedido.getComanda().getMenues().isEmpty() && _pedido
							.getComanda().getProductos().isEmpty())) {
				return "Existe Pedido vacío!";
			}
		}
		return null;
	}

	// {{ injected: DomainObjectContainer
	@Inject
	private DomainObjectContainer contenedor;

	/*
	 * Inyección del servicio
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
