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
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

import dom.empleado.Empleado;
import dom.mesa.EstadoAsignacionMesaEnum;
import dom.mesa.Mesa;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Mozo extends Empleado {

	// {{ Lista De Mesas (Collection)

	private List<Mesa> listaMesas = new ArrayList<Mesa>();

	@Render(Type.EAGERLY)
	@Named("Mesas Asignadas")
	public List<Mesa> getListamesas() {
		return listaMesas;
	}

	public void setListaMesas(final List<Mesa> listaMesas) {
		this.listaMesas = listaMesas;
	}

	// }}

	// {{ injected: DomainObjectContainer
	@Inject
	private DomainObjectContainer contenedor;
	/*
	 * Inyección del servicio
	 */

	@Inject
	private MozoServicio mozoServicio;

	@Bulk
	@MemberOrder(sequence = "1")
	public List<Mozo> borrar() {
		contenedor.removeIfNotAlready(this);
		return mozoServicio.listarMozos();
	}

	@MemberOrder(sequence = "2")
	public List<Mesa> seleccionarMesas() {
		for (Mesa _mesa : contenedor.allInstances(Mesa.class))
			_mesa.setEstadoSeleccion(false);
		return mozoServicio.listarMesaSinAsignar();
	}

	public String disableSeleccionarMesas() {
		return mozoServicio.listaMesas().isEmpty() ? "No Existen Mesas" : null;
	}

	@MemberOrder(sequence = "3")
	public Mozo asignar() {
		return mozoServicio.asignarMesas(this);
	}

	public String disableAsignar() {
		return mozoServicio.listaMesasSeleccionadas().isEmpty() ? "No Hay Mesas Seleccionadas"
				: null;
	}

	@Named("Quitar")
	@MemberOrder(sequence = "4")
	public Mozo desasignarMesa(final Mesa _mesa) {
		listaMesas.remove(_mesa);
		_mesa.setEstadoAsignacion(EstadoAsignacionMesaEnum.No_Asignada);
		return this;
	}

	public String disableDesasignarMesa(final Mesa _mesa) {
		return listaMesas.isEmpty() ? "No Existen Mesas Asignadas" : null;
	}

	public List<Mesa> choices0DesasignarMesa() {
		return getListamesas();
	}

}
