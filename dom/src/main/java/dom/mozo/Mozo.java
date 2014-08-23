package dom.mozo;

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

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

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
@Queries({ @Query(name = "todosLosMozos", language = "JDOQL", value = "SELECT FROM dom.mozo.Mozo") })
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
	
	@MemberOrder(name = "listaMesas", sequence = "1")
	public Mozo agregarMesa(
			@Named("Mesa") final Mesa unaMesa) {
		unaMesa.setEstadoAsignacion(EstadoAsignacionMesaEnum.Asignada);
		getListamesas().add(unaMesa);
		return this;
	}
	
	@MemberOrder(name = "listaMesas", sequence = "2")
	public Mozo quitarMesa(
			@Named("Mesa") final Mesa unaMesa) {
		unaMesa.setEstadoAsignacion(EstadoAsignacionMesaEnum.No_Asignada);
		getListamesas().remove(unaMesa);
		return this;
	}
	
	public List<Mesa> choices0AgregarMesa() {
		return mozoServicio.listarMesaSinAsignar();
	}
	
	public List<Mesa> choices0QuitarMesa() {
		return getListamesas();
	}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public DomainObjectContainer getContainer() {
		return contenedor;
	}

	public void setContainer(DomainObjectContainer container) {
		this.contenedor = container;
	}

	/*
	 * Inyección del servicio
	 */

	private MozoServicio mozoServicio;

	public void injectarMozoServicio(final MozoServicio serviciomozo) {
		this.mozoServicio = serviciomozo;
	}

	@Bulk
	@MemberOrder(sequence = "1")
	public List<Mozo> borrar() {
		contenedor.removeIfNotAlready(this);
		return mozoServicio.listarMozos();
	}
	
}
