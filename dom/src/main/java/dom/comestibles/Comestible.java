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

package dom.comestibles;

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
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Sequence(name = "secuenciaNumeroComestible", strategy = SequenceStrategy.CONTIGUOUS)
public abstract class Comestible {

	public Comestible() {
		// TODO Auto-generated constructor stub
	}

	// {{ Numero (property)
	private int numero;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroComestible")
	@Named("Número")
	@TypicalLength(3)
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numeroGuarnicion) {
		this.numero = numeroGuarnicion;
	}

	// }}

	// {{ Nombre (property)
	private String nombre;

	@RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*")
	@MaxLength(value = 30)
	@Title
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombreGuarnicion) {
		this.nombre = nombreGuarnicion;
	}

	// }}

	// {{ Descripcion (property)
	private String descripcion;

	@Named("Descripción")
	@MultiLine(numberOfLines = 3)
	@Optional
	@MemberOrder(sequence = "3")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcionGuarnicion) {
		this.descripcion = descripcionGuarnicion;
	}

	// }}

	// {{ Precio (property)
	private double precio;

	@TypicalLength(5)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(final double precioGuarnicion) {
		this.precio = precioGuarnicion;
	}

	// }}

	// {{ EstadoLogico (property)
	private EstadoLogico estadoLogico;

	@Named("Estado de Alta")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "7")
	public EstadoLogico getEstadoLogico() {
		return estadoLogico;
	}

	public void setEstadoLogico(final EstadoLogico estadoLogico) {
		this.estadoLogico = estadoLogico;
	}

	// }}

	public Comestible deshabilitar() {

		setEstadoLogico(EstadoLogico.Deshabilitado);
		return this;

	}

	public Comestible habilitar() {

		setEstadoLogico(EstadoLogico.Habilitado);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((estadoLogico == null) ? 0 : estadoLogico.hashCode());
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
		Comestible other = (Comestible) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (estadoLogico != other.estadoLogico)
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
