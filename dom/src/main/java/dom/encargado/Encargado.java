package dom.encargado;

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

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.empleado.Empleado;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Encargado extends Empleado {
	
	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Encargado> borrar() {

		contenedor.removeIfNotAlready(this);

		return encargadoServicio.listarEncargados();
	}

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

	private EncargadoServicio encargadoServicio;

	public void injectarEncargadoServicio(
			final EncargadoServicio servicioencargado) {
		this.encargadoServicio = servicioencargado;
	}
}
