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
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Render.Type;

import dom.comanda.Comanda;
import dom.comandaBebida.ComandaBebida;
import dom.comandaProducto.ComandaProducto;
import dom.menu.Menu;
import dom.mozo.Mozo;

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

	// {{ Comandas (Collection)
	private List<Comanda> comandas = new ArrayList<Comanda>();

	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "1")
	public List<Comanda> getComandas() {
		return comandas;
	}

	public void setComandas(final List<Comanda> comandas) {
		this.comandas = comandas;
	}

	// }}

	public void addToComandas(final Comanda unaComanda) {
		// check for no-op
		if (unaComanda == null || getComandas().contains(unaComanda)) {
			return;
		}
		// associate new
		getComandas().add(unaComanda);
	}

	public void removeFromComandas(final Comanda unaComanda) {
		// check for no-op
		if (unaComanda == null || !getComandas().contains(unaComanda)) {
			return;
		}
		// dissociate existing
		getComandas().remove(unaComanda);
	}

	@MemberOrder(name = "comandas", sequence = "1")
	public ComandaBebida tomarBebidas() {
		return mesaServicio.crearComandaBebida(this);
	}

	@MemberOrder(name = "comandas", sequence = "2")
	public ComandaProducto tomarPlatos() {
		return mesaServicio.crearComandaProducto(this);
	}

	@Named("Eliminar...")
	@MemberOrder(name = "comandas", sequence = "3")
	public Mesa eliminarComanda(Comanda _comanda) {
		removeFromComandas(_comanda);
		return this;
	}

	public List<Comanda> choices0EliminarComanda() {
		return getComandas();
	}

	public Mesa tomarMenu(final Menu _menu) {
		mesaServicio.crearComandasMenu(this, _menu);
		return this;
	}

	@Bulk
	@MemberOrder(sequence = "1")
	public List<Mesa> borrar() {
		if (comandas.isEmpty())
			contenedor.removeIfNotAlready(this);
		else
			contenedor.informUser("Existe Comanda/s dependiente/s!!");
		return mesaServicio.listarMesas();
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
