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
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;

import dom.guarnicion.GuarnicionServicio;
import dom.mozo.Mozo;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Queries({
		@Query(name = "mesasSeleccionadas", language = "JDOQL", value = "SELECT FROM dom.mesa.Mesa where estadoSeleccion == true"),
		@Query(name = "mesasSinAsignar", language = "JDOQL", value = "SELECT FROM dom.mesa.Mesa where estadoAsignacion == 'No_Asignada'"),
		@Query(name = "mesasAsignadas", language = "JDOQL", value = "SELECT FROM dom.mesa.Mesa where estadoAsignacion == 'Asignada'")
})

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

	@Bulk
	@MemberOrder(sequence = "1")
	public List<Mesa> borrar() {
		if (mesaServicio.validaBorrado(this))
			contenedor.removeIfNotAlready(this);
		else
			contenedor.informUser("Existe una Comanda dependiente!!");
		return mesaServicio.listarMesas();
	}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

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
