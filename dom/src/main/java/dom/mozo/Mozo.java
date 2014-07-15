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
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import dom.empleado.Empleado;
import dom.mesa.Mesa;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Queries({
	@Query(name = "todosLosMozos", language = "JDOQL", value = "SELECT FROM dom.mozo.Mozo")
	})
public class Mozo extends Empleado {

	// {{ Lista De Mesas (Collection)

	private List<Mesa> listaMesas = new ArrayList<Mesa>();

	@Named("Mesas Asignadas")
	@MemberOrder(sequence = "1")
	public List<Mesa> getListamesas() {
		return listaMesas;
	}

	public void setListaMesas(final List<Mesa> listaMesas) {
		this.listaMesas = listaMesas;
	}

	@Hidden
	public void addMesa(final Mesa _mesa) {
		listaMesas.add(_mesa);
	}

	@Hidden
	public void removeMesa(final Mesa _mesa) {
		listaMesas.remove(_mesa);
	}

	// }}
	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public void injectDomainObjectContainer(
			final DomainObjectContainer container) {
		this.setContainer(container);
	}

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

	@Named("Borrar")
	@Bulk
	@MemberOrder(name = "accionMozo", sequence = "1")
	public List<Mozo> borrar() {

		contenedor.removeIfNotAlready(this);

		return mozoServicio.listarMozos();
	}

	@Named("Asignar Mesas")
	@MemberOrder(name = "accionMesaMozo", sequence = "2")
	public Mozo asignarMesasMozo() {
		return mozoServicio.asignarMesas(this);

	}
	
	@Named("Seleccionar Mesas a Mozos")	
	@MemberOrder(name = "accionMesaMozo", sequence = "2")
	public List<Mesa> MesasSinAsignar(){
		return mozoServicio.listarMesaSinAsignar();
	}
	

}
