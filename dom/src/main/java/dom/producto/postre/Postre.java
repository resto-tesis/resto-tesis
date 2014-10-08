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

package dom.producto.postre;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.producto.Producto;
import dom.producto.postre.Postre;
import dom.producto.postre.PostreServicio;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Query(name = "postresQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.postre.Postre WHERE nombre.matches(:nombre)")
@AutoComplete(repository = PostreServicio.class, action = "completarPostres")
public class Postre extends Producto {

	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Postre> borrar() {
//		if (postreServicio.validaBorrado(this))
			contenedor.removeIfNotAlready(this);
//		else
			contenedor.informUser("Existe un Menu o Comanda dependiente!!");
		return postreServicio.listarPostres();
	}

	// {{ injected: DomainObjectContainer
	@Inject
	private DomainObjectContainer contenedor;

	/*
	 * Inyección del servicio
	 */
	@Inject
	private PostreServicio postreServicio;

}
