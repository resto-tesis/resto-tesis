package dom.mesa;

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
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Query(name = "mesasSeleccionadas", language = "JDOQL", value = "SELECT FROM dom.mesa.Mesa where estadoSeleccion == true")
//@Query(name ="existeMesa", language = "JDOQL", value = "SELECT FROM dom.mesa.Mesa where 
public class Mesa {

	// {{ EstadoSeleccion (property)
	private boolean estadoSeleccion;

	@Hidden(where = Where.ALL_TABLES)
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
	@MemberOrder(name = "accionMesa", sequence = "2")
	@Named("Seleccionar")
	public List<Mesa> seleccionar() {
		if (estadoAsignacionMesa == EstadoAsignacionMesaEnum.No_Asignada)
			setEstadoSeleccion(true);
		return mesaServicio.listarMesas();
	}
	// {{ Numero (property)
	private int numeroMesa;

	@Disabled
	@Title(prepend = "Mesa Nº ")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public int getNumero() {
		return numeroMesa;
	}

	public void setNumero(final int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}
	// }}
	// {{ Capacidad (property)
	private int capacidadMesa;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public int getCapacidad() {
		return capacidadMesa;
	}

	public void setCapacidad(final int capacidadMesa) {
		this.capacidadMesa = capacidadMesa;
	}
	// }}
	// {{ EstadoHabilitacion (property)
	private EstadoHabilitacionMesaEnum estadoHabilitacionMesa;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public EstadoHabilitacionMesaEnum getEstadoHabilitacion() {
		return estadoHabilitacionMesa;
	}

	public void setEstadoHabilitacion(final EstadoHabilitacionMesaEnum estadoHabilitacionMesa) {
		this.estadoHabilitacionMesa = estadoHabilitacionMesa;
	}
	// }}
	// {{ EstadoAsignacion (property)
	private EstadoAsignacionMesaEnum estadoAsignacionMesa;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	@Disabled
	public EstadoAsignacionMesaEnum getEstadoAsignacion() {
		return estadoAsignacionMesa;
	}

	public void setEstadoAsignacion(final EstadoAsignacionMesaEnum estadoAsignacionMesa) {
		this.estadoAsignacionMesa = estadoAsignacionMesa;
	}
	// }}

	@Named("Borrar")
	@Bulk
	@MemberOrder(name = "accionMesa", sequence = "1")
	public List<Mesa> borrar() {

		contenedor.removeIfNotAlready(this);

		return mesaServicio.listarMesas();
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

	/*
	 * Inyección del servicio
	 */
	private MesaServicio mesaServicio;

	public void injectarMesaServicio(final MesaServicio serviciomesa) {
		this.mesaServicio = serviciomesa;
	}

}
