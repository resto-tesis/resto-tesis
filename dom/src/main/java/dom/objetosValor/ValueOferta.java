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

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ValueOferta {

	public String iconName() {
		return getOferta().iconName();
	}

	public ValueOferta() {
		// TODO Auto-generated constructor stub
	}

	public String title() {
		return getOferta().getNombre() + " (x " + getCantidad() + ")";
	}

	// {{ Oferta (property)
	private Oferta oferta;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(final Oferta oferta) {
		this.oferta = oferta;
	}

	// }}

	// {{ Nota (property)
	private String nota;

	@MemberOrder(sequence = "4")
	@Column(allowsNull = "true")
	public String getNota() {
		return nota;
	}

	public void setNota(final String nota) {
		this.nota = nota;
	}

	// }}

	// {{ Cantidad (property)
	private int cantidad;

	@TypicalLength(2)
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad;
	}

	// }}

	@Inject
	private DomainObjectContainer contenedor;
}
