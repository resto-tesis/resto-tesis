package dom.platoPrincipal;

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

import dom.plato.Plato;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Queries({
	@Query(name = "todosLosPlatosPricipales", language = "JDOQL", value = "SELECT FROM dom.platoPrincipal.PlatoPrincipal")})

public class PlatoPrincipal extends Plato {

	public PlatoPrincipal() {
		// TODO Auto-generated constructor stub
	}

	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<PlatoPrincipal> borrar() {
		if (platoPrincipalServicio.validaBorrado(this))
			contenedor.removeIfNotAlready(this);
		else
			contenedor.informUser("Existe un Menu o Comanda dependiente!!");
		return platoPrincipalServicio.listarPLatosPrincipales();
	}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	private PlatoPrincipalServicio platoPrincipalServicio;

	public void injectPlatoServicio(
			final PlatoPrincipalServicio _platoPrincipalServicio) {
		platoPrincipalServicio = _platoPrincipalServicio;
	}
}
