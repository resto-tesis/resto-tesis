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

import dom.oferta.Oferta;

/**
 * Objeto de Valor que se creará cuando se desee agregar una cantidad o alguna nota
 * al momento de crear una Oferta.
 * @author RestoTesis
 * @since 15/10/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ValueOferta {

	/**
	 * Obtiene el nombre del icono para una Oferta
	 * @return String
	 */
	public String iconName() {
		return getOferta().iconName();
	}

	/**
	 * Constructor de la clase
	 */
	public ValueOferta() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene el nombre y cantidad de la Oferta
	 * @return String
	 */
	public String title() {
		return getOferta().getNombre() + " (x " + getCantidad() + ")";
	}

	// {{ Oferta (property)
	private Oferta oferta;

	/**
	 * Obtiene una Oferta
	 * @return oferta Oferta
	 */
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Oferta getOferta() {
		return oferta;
	}

	/**
	 * Setea una Oferta
	 * @param oferta Oferta
	 */
	public void setOferta(final Oferta oferta) {
		this.oferta = oferta;
	}

	// }}

	// {{ Nota (property)
	private String nota;

	/**
	 * Obtiene una nota o comentario de la Oferta
	 * @return nota String
	 */
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "true")
	public String getNota() {
		return nota;
	}

	/**
	 * Setea una nota o comentario de la Oferta
	 * @param nota String
	 */
	public void setNota(final String nota) {
		this.nota = nota;
	}
	
	private int cantidad;

	/**
	 * Obtiene la cantidad de Ofertas iguales
	 * @return cantidad int
	 */
	@TypicalLength(2)
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Setea la cantidad de Ofertas iguales
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
