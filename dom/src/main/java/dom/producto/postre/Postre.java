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

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;

import dom.producto.ProductoElaborado;
import dom.producto.postre.Postre;
import dom.producto.postre.PostreServicio;

/**
 * Entidad que representa un alimento elaborado que se sirve de postre,   
 * extiende de ProductoElaborado
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Query(name = "postresQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.postre.Postre WHERE nombre.matches(:nombre)")
@AutoComplete(repository = PostreServicio.class, action = "completarPostres")
public class Postre extends ProductoElaborado {

	/**
	 * Obtiene el nombre del icono de un Postre
	 * @return String
	 */
	public String iconName() {
		return getBaja() ? "PostreDes" : "Postre";
	}
	
	/**
	 * Inyeccion del contenedor
	 */
	@SuppressWarnings("unused")
	@Inject
	private DomainObjectContainer contenedor;

}
