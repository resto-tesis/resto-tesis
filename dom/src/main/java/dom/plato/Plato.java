package dom.plato;

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

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

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroPlato", strategy = SequenceStrategy.CONTIGUOUS)

public class Plato {
	
	// {{ Numero (property)
	private int numero;

	@TypicalLength(3)
	@Disabled
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroPlato")
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
	// {{ TipoDePlato (property)
	private TipoDePlatoEnum tipoDePlato;
	
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public TipoDePlatoEnum getTipoDePlato() {
		return tipoDePlato;
	}

	public void setTipoDePlato(final TipoDePlatoEnum tipoDePlato) {
		this.tipoDePlato = tipoDePlato;
	}
	// }}
	// {{ CondicionDePlato (property)
	private CondicionDePlatoEnum condicionDePlato;
	
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

	@TypicalLength(5)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "6")
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(final double precioPlato) {
		this.precio = precioPlato;
	}
	// }}

	@Named("Borrar")
	@Bulk
	@MemberOrder(name = "accionPlato", sequence = "1")
	public List<Plato> borrar() {

		contenedor.removeIfNotAlready(this);

		return platoServicio.listarPLatos();
	}
	
	// {{ injected: DomainObjectContainer
		private DomainObjectContainer contenedor;
	
	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	private PlatoServicio platoServicio;

	public void injectarPlatoServicio(final PlatoServicio servicioplato) {
		this.platoServicio = servicioplato;
	}

	

}
