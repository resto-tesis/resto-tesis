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

package dom.factura;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

/**
 * Entidad Factura, la cual representa un comprobante donde figura un detalle de 
 * lo que ha sido consumido por un un cliente y cuanto se debe abonar por ello 
 * @author RestoTesis
 * @since 15/10/2014
 * @version 1.0.0
 */
@Sequence(name = "secuenciaNumeroFactura", strategy = SequenceStrategy.CONTIGUOUS)
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Factura {
	/**
	 * Retorna el nombre del icono de una nueva Factura 
	 * @return String
	 */
	public String iconName(){
		return "Factura";
	}
	/**
	 * Constructor de la clase Factura
	 */
	public Factura() {
		// TODO Auto-generated constructor stub
	}

	// {{ Numero (property)
	private long numero;

	/**
	 * Obtiene el numero de una nueva Factura, el cual se genera en forma automatica
	 * @return numero long
	 */
	@Title(prepend = "Factura Nº ")
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroFactura")
	@Named("Número")
	@TypicalLength(3)
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public long getNumero() {
		return numero;
	}

	/**
	 * Setea el numero de una Factura
	 * @param numero long
	 */
	public void setNumero(final long numero) {
		this.numero = numero;
	}


	// {{ Total (property)
	private double total;

	/**
	 * Obtiene el total de la Factura que se va a crear.
	 * @return total double
	 */
	@Named("Total ($)")
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public double getTotal() {
		return total;
	}

	/**
	 * Setea el total de la Factura
	 * @param total double
	 */
	public void setTotal(final double total) {
		this.total = total;
	}

	// }}

	// {{ Items (Collection)
	private List<ItemFactura> items = new ArrayList<ItemFactura>();

	/**
	 * Obtiene una lista de los items de la factura
	 * @return items List<Itemfactura>
	 */
	@Join
	@Named("Detalle")
	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "1")
	public List<ItemFactura> getItems() {
		return items;
	}

	/**
	 * Setea la lista de los items de la factura
	 * @param items List<Itemfactura>
	 */
	public void setItems(final List<ItemFactura> items) {
		this.items = items;
	}

	/**
	 * Agrega un item a la lista de items de la factura
	 * @param _item List<Itemfactura>
	 */
	public void addToItems(final ItemFactura _item) {
		items.add(_item);
	}

	/**
	 * remueve un item de la lista de items de la factura
	 * @param _item List<Itemfactura>
	 */
	public void removeFromItems(final ItemFactura _item) {
		items.remove(_item);
	}

	@Inject
	private DomainObjectContainer contenedor;
}
