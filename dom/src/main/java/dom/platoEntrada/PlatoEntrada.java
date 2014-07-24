package dom.platoEntrada;

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
	@Query(name = "todosLosPlatosEntrada", language = "JDOQL", value = "SELECT FROM dom.platoEntrada.PlatoEntrada")})

public class PlatoEntrada extends Plato {

	public PlatoEntrada() {
		// TODO Auto-generated constructor stub
	}

	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<PlatoEntrada> borrar() {

		contenedor.removeIfNotAlready(this);

		return platoEntradaServicio.listarPLatosEntrada();
	}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	private PlatoEntradaServicio platoEntradaServicio;

	public void injectPlatoServicio(
			final PlatoEntradaServicio _platoEntradaServicio) {
		platoEntradaServicio = _platoEntradaServicio;
	}
}
