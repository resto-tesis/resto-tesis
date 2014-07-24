package dom.cocinero;

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
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.comanda.Comanda;
import dom.empleado.Empleado;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Queries({ @Query(name = "todosLosCocineros", language = "JDOQL", value = "SELECT FROM dom.cocinero.Cocinero") })
public class Cocinero extends Empleado {
	
	// {{ Contenedor (property)
	private DomainObjectContainer contenedor;

	public void injectDomainObjectContainer(
			final DomainObjectContainer _contenedor) {
		contenedor = _contenedor;
	}

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContainer(DomainObjectContainer container) {
		contenedor = container;
	}

	private CocineroServicio servicioCocinero;

	public void injectarCocineroServicio(CocineroServicio _servicioCocinero) {
		servicioCocinero = _servicioCocinero;
	}

	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Cocinero> borrarCocinero() {
		contenedor.removeIfNotAlready(this);
		return servicioCocinero.listarCocineros();
	}
	
	@Named("Tomar Comanda/as")
	@MemberOrder(sequence = "2")
	public List<Comanda> listarComandasParaCocina(){
		return servicioCocinero.listarComandasSinPreparar();
	}
	
	@Named("Notificar Comandas Finalizadas")
	@MemberOrder(sequence = "3")
	public List<Comanda> listarComandasEnCocina() {
		return servicioCocinero.listarComandasEnPreparacion();
	}
	
}
