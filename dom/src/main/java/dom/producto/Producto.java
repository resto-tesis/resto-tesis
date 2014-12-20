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
 * 
 */

package dom.producto;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;

/**
 * Clase asbtarcta que representa todos los productos que se serviran en el local,
 * la cual contiene los datos comunes a cualquier tipo de ellos
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Sequence(name = "secuenciaNumeroProducto", strategy = SequenceStrategy.CONTIGUOUS)
public abstract class Producto {

	/**
	 * Permite implementar la obtencio el nombre del icono de los productos.
	 */
	public abstract String iconName();
	
	/**
	 * Constructor de la clase
	 */
	public Producto() {
		// TODO Auto-generated constructor stub
	}

	
	// {{ Numero (property)
	private int numero;

	/**
	 * Obtiene un numero para el Producto
	 * @return numero int
	 */
	@Hidden(where = Where.ALL_TABLES)
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroProducto")
	@Named("Número")
	@TypicalLength(3)
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	/**
	 * Setea el numero para el Producto
	 * @param numero int
	 */
	public void setNumero(final int numero) {
		this.numero = numero;
	}

	// {{ Nombre (property)
	private String nombre;

	/**
	 * Obtiene un nombre para el Producto
	 * @return nombre String
	 */
	@Hidden(where = Where.ALL_TABLES)
	@RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*")
	@MaxLength(value = 30)
	@Title
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea el nombre del Producto
	 * @param nombre String
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// {{ Descripcion (property)
	private String descripcion;

	/**
	 * Obtiene una descripcion para el Producto
	 * @return descripcion String
	 */
	@Named("Descripción")
	@MultiLine(numberOfLines = 3)
	@Optional
	@MemberOrder(sequence = "3")
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Setea la descripcion del Producto
	 * @param descripcion String
	 */
	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	// {{ Precio (property)
	private double precio;

	/**
	 * Obtiene un precio para el Producto
	 * @return precio double
	 */
	@TypicalLength(5)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public double getPrecio() {
		return precio;
	}

	/**
	 * Setea el precio del Producto
	 * @param precio double
	 */
	public void setPrecio(final double precio) {
		this.precio = precio;
	}

	// {{ Baja (property)
	private boolean baja;

	/**
	 * Obtiene el estado de baja/alta para el Producto
	 * @return baja boolean
	 */
	@Disabled
	@Hidden
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public boolean getBaja() {
		return baja;
	}

	/**
	 * Setea el el estado de baja/alta para el Producto
	 * @param baja boolean
	 */
	public void setBaja(final boolean baja) {
		this.baja = baja;
	}

	/**
	 * Asigna true al estado baja del producto
	 * @return this (Producto)
	 */
	public Producto Baja() {
		setBaja(true);
		return this;
	}

	/**
	 * Asigna el texto a mostrar del estado de baja
	 * @return String
	 */
	public String disableBaja() {
		return getBaja() ? "Producto dado de Baja!" : null;
	}

	/**
	 * Asigna false al estado Alta del producto
	 * @return this (Producto)
	 */
	public Producto Alta() {
		setBaja(false);
		return this;
	}

	/**
	 * Asigna el texto a mostrar del estado de alta
	 * @return String
	 */
	public String disableAlta() {
		return getBaja() ? null : "Producto dado de Alta!";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (baja ? 1231 : 1237);
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + numero;
		long temp;
		temp = Double.doubleToLongBits(precio);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (baja != other.baja)
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numero != other.numero)
			return false;
		if (Double.doubleToLongBits(precio) != Double
				.doubleToLongBits(other.precio))
			return false;
		return true;
	}

}
