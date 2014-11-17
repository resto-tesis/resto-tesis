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

package dom.mozo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

import dom.empleado.Empleado;
import dom.mesa.EstadoAsignacionMesaEnum;
import dom.mesa.Mesa;
/**
 * Entidad Mozo, la cual representa un empleado del comercio, que desarrollara
 * funciones especificas en el salon de ventas, tomando los pedidos o comandas
 * a los clientes, extiende de la clase Empleado
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Mozo extends Empleado {

	/**
	 * Retorna el nombre del icono segun el Mozo este dado de baja/alta
	 * @see dom.persona.Persona.getBaja();
	 * @return String
	 */
	public String iconName() {
		return getBaja() ? "MozoDes" : "Mozo";
	}

	// {{ Lista De Mesas (Collection)

	private List<Mesa> listaMesas = new ArrayList<Mesa>();

	/**
	 * Atributo del tipo List<Mesa> que le permite al mozo obtener una lista de mesas
	 * @return listaMesas List<Mesa>
	 */
	@Render(Type.EAGERLY)
	@Named("Mesas Asignadas")
	public List<Mesa> getListamesas() {
		return listaMesas;
	}

	/**
	 * Permite al mozo setear una lista de mesas
	 * @param listaMesas List<Mesa>
	 */
	public void setListaMesas(final List<Mesa> listaMesas) {
		this.listaMesas = listaMesas;
	}

	// }}

	/**
	 * Permite que el mozo listar mesas con estado: sin asignar
	 * @return List<Mesa>
	 */
	@MemberOrder(name = "listaMesas", sequence = "1")
	public List<Mesa> seleccionarMesas() {
		for (Mesa _mesa : contenedor.allInstances(Mesa.class))
			_mesa.setEstadoSeleccion(false);
		return mozoServicio.listarMesasSinAsignar();
	}

	/**
	 * Validar si existen mesas sin asignar
	 * @return String
	 */
	public String disableSeleccionarMesas() {
		return mozoServicio.listarMesasSinAsignar().isEmpty() ? "No Existen Mesas Sin Asignar"
				: null;
	}

	/**
	 * Permite asignar una mesa al Mozo
	 * @return this
	 */
	@MemberOrder(name = "listaMesas", sequence = "2")
	public Mozo asignar() {
		return mozoServicio.asignarMesas(this);
	}

	/**
	 * Permite ver si existen mesas seleccionadas
	 * @return String
	 */
	public String disableAsignar() {
		return mozoServicio.listaMesasSeleccionadas().isEmpty() ? "No Hay Mesas Seleccionadas"
				: null;
	}

	/**
	 * Permite desasignar una Mesa al Mozo y le cambia el estdo de asignacion
	 * @param _mesa Mesa
	 * @see dom.mesa.Mesa.getPedidos()
	 * @return this
	 */
	@Named("Quitar")
	@MemberOrder(name = "listaMesas", sequence = "3")
	public Mozo desasignarMesa(final Mesa _mesa) {
		if (!_mesa.getPedidos().isEmpty()) {
			contenedor.informUser("Mesa con Pedidos");
			return this;
		}
		listaMesas.remove(_mesa);
		_mesa.setEstadoAsignacion(EstadoAsignacionMesaEnum.No_Asignada);
		return this;
	}

	/**
	 * Permite verificar si no existen Mesas asignadas
	 * @param _mesa Mesa
	 * @return String
	 */
	public String disableDesasignarMesa(final Mesa _mesa) {
		return listaMesas.isEmpty() ? "No Existen Mesas Asignadas" : null;
	}

	/**
	 * Permite obtener una lista de mesas
	 * @return List<Mesas>
	 */
	public List<Mesa> choices0DesasignarMesa() {
		return getListamesas();
	}

	/**
	 * Inyección del contenedor
	 */
	@Inject
	private DomainObjectContainer contenedor;

	/**
	 * Inyección del servicio del Mozo
	 */
	@Inject
	private MozoServicio mozoServicio;

}
