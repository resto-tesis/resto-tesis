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
	
	// {{ NumeroDePlato (property)
	private int numeroDePLato;
	
	@TypicalLength(3)
	@Disabled
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroPlato")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumeroDePlato() {
		return numeroDePLato;
	}

	public void setNumeroDePlato(final int numeroDePLato) {
		this.numeroDePLato = numeroDePLato;
	}
	// }}
	
	// {{ Nombre (property)
	private String nombrePlato;
	
	@Title
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getNombre() {
		return nombrePlato;
	}

	public void setNombre(final String nombrePlato) {
		this.nombrePlato = nombrePlato;
	}
	// }}
	
	// {{ DescripcionPlato (property)
	private String descripcionDePlato;
	
	@MultiLine(numberOfLines = 3)
	@Optional
	@MemberOrder(sequence = "3")
	public String getDescripcionPlato() {
		return descripcionDePlato;
	}

	public void setDescripcionPlato(final String descripcionDePlato) {
		this.descripcionDePlato = descripcionDePlato;
	}
	// }}
	
	// {{ TipoDePlato (property)
	private TipoDePlato tipoDePlato;
	
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public TipoDePlato getTipoDePlato() {
		return tipoDePlato;
	}

	public void setTipoDePlato(final TipoDePlato tipoDePlato) {
		this.tipoDePlato = tipoDePlato;
	}
	// }}

	// {{ CondicionDePlato (property)
	private CondicionDePlato condicionDePLato;
	
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	public CondicionDePlato getCondicionDePlato() {
		return condicionDePLato;
	}

	public void setCondicionDePlato(final CondicionDePlato condicionDePLato) {
		this.condicionDePLato = condicionDePLato;
	}
	// }}
	
	// {{ PrecioPlato (property)
	private double precioPlato;
	
	@TypicalLength(5)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public double getPrecioPlato() {
		return precioPlato;
	}

	public void setPrecioPlato(final double precioPlato) {
		this.precioPlato = precioPlato;
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

	public void injectarPlatoServicio(final PlatoServicio servicioPlato) {
		this.platoServicio = servicioPlato;
	}

	

}
