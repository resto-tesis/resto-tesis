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

package dom.plato;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;
import javax.validation.constraints.Digits;

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
@Sequence(name = "secuenciaNumeroPlato", strategy = SequenceStrategy.CONTIGUOUS)
public abstract class Plato {

	// {{ Numero (property)
	private int numero;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroPlato")
	@Named("Número")
	@TypicalLength(3)
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numeroPlato) {
		this.numero = numeroPlato;
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

	public void setNombre(final String nombrePlato) {
		this.nombre = nombrePlato;
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

	public void setDescripcion(final String descripcionPlato) {
		this.descripcion = descripcionPlato;
	}

	// }}

	// {{ CondicionDePlato (property)
	private CondicionDePlatoEnum condicionDePlato;

	@Named("Condición")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	public CondicionDePlatoEnum getCondicionDePlato() {
		return condicionDePlato;
	}

	public void setCondicionDePlato(final CondicionDePlatoEnum condicionDePLato) {
		this.condicionDePlato = condicionDePLato;
	}

	// }}

	// {{ Precio (property)
	private double precio;

	@MaxLength(value = 6)
	@Digits(integer = 3, fraction = 2)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "6")
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(final double precioPlato) {
		this.precio = precioPlato;
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

	public Plato deshabilitarPlato(){
		
		setEstadoLogico(EstadoLogico.Deshabilitado);
		return this;
		
	}
	
	public Plato habilitarPlato() {
		
		setEstadoLogico(EstadoLogico.Habilitado);
		return this;
	}

}
