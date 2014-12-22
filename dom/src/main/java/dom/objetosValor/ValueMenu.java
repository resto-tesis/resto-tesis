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

package dom.objetosValor;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.menu.Menu;

/**
 * Objeto de Valor que se creará cuando se desee agregar una cantidad o alguna nota
 * al momento de tomar un pedido con mas de un menu en alguna de las mesas.
 * @author RestoTesis
 * @since 15/10/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ValueMenu {

	/**
	 * Obtiene el nombre del icono para un Menu
	 * @return String
	 */
	public String iconName(){
		return getMenu().iconName();
	}
	/**
	 * Constructor de la clase
	 */

	public ValueMenu() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene el nombre y cantidad del menu
	 * @see dom.menu.Menu.getNombre()
	 * @return String
	 */
	public String title() {
		return getMenu().getNombre() + " (x " + getCantidad() + ")";
	}

	// {{ Menu (property)
	private Menu menu;

	/**
	 * Obtiene un Menu
	 * @return menu Menu
	 */
	@MemberOrder(sequence = "5")
	@Column(allowsNull = "false")
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Setea un Menu
	 * @param menu Menu
	 */
	public void setMenu(final Menu menu) {
		this.menu = menu;
	}

	// {{ Nota (property)
	private String nota;

	/**
	 * Obtiene una nota o comentario del Menu
	 * @return nota String
	 */
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "true")
	public String getNota() {
		return nota;
	}

	/**
	 * Setea una nota o comentario del Menu
	 * @param nota String
	 */
	public void setNota(final String nota) {
		this.nota = nota;
	}

	// {{ Cantidad (property)
	private int cantidad;

	/**
	 * Obtiene la cantidad de Menues iguales
	 * @return cantidad int
	 */
	@TypicalLength(2)
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Setea la cantidad de Menues iguales
	 * @param cantidad int
	 */
	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Inyeccion del contenedor
	 */
	@SuppressWarnings("unused")
	@Inject
	private DomainObjectContainer contenedor;
}
