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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ItemFactura {

	/**
	 * Retorna el nombre del icono del item de la factura 
	 * @return String
	 */
	public String iconName() {
		return "ItemFactura";
	}

	/**
	 * Constructor de la clase ItemFactura 
	 * @return String
	 */
	public ItemFactura() {
		// TODO Auto-generated constructor stub
	}

	// {{ Nombre (property)
	private String nombre;

	/**
	 * Obtiene el nombre de una nueva Factura
	 * @return nombre String
	 */
	@Title
	@Hidden(where = Where.ALL_TABLES)
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea el nombre de una factura
	 * @param nombre String
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// {{ Precio (property)
	private double precio;

	/**
	 * Obtiene el precio de una nueva Factura
	 * @return precio double
	 */
	@Named("Precio ($)")
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public double getPrecio() {
		return precio;
	}

	/**
	 * Setea el precio de una nueva Factura
	 * @param precio double
	 */
	public void setPrecio(final double precio) {
		this.precio = precio;
	}

	// {{ Cantidad (property)
	private int cantidad;

	/**
	 * Obtiene la cantidad de una nueva Factura
	 * @return cantidad int
	 */
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getCantidad() {
		return cantidad;
	}
	/**
	 * Setea la cantidad de una nueva Factura
	 * @param cantidad int
	 */

	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad;
	}

	// {{ Descuento (property)
	private int descuento;

	/**
	 * Obtiene el descuento de una nueva Factura
	 * @return descuento int
	 */
	@Named("Descuento (%)")
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "false")
	public int getDescuento() {
		return descuento;
	}

	/**
	 * Setea el descuento de una nueva Factura
	 * @param descuent int
	 */
	public void setDescuento(final int descuento) {
		this.descuento = descuento;
	}

	/**
	 * Realiza los calculos para obtener el precio final de una nueva Factura
	 * @return precio double
	 */
	@Named("Precio Final ($)")
	@MemberOrder(sequence = "5")
	public double getPrecioFinal() {
		return (precio - (precio / 100 * descuento)) * cantidad;
	}

}
