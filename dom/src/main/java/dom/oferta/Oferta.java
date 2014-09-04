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

package dom.oferta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.menu.Menu;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroOferta", strategy = SequenceStrategy.CONTIGUOUS)
@Query(name = "ofertasQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.oferta.Oferta WHERE nombre.matches(:nombre)")
@AutoComplete(repository = OfertaServicio.class, action = "completarOfertas")
public class Oferta {

	// {{ Numero (property)
	private int numero;

	@Named("Número")
	@TypicalLength(3)
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroOferta")
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numero) {
		this.numero = numero;
	}

	// }}

	// {{ Nombre (property)
	private String nombre;

	@RegEx(validation = "[0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*")
	@Title
	@TypicalLength(30)
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ CantidadPersonas (property)
	private int cantidadPersonas;

	@Optional
	@MemberOrder(sequence = "3")
	public int getCantidadPersonas() {
		return cantidadPersonas;
	}

	public void setCantidadPersonas(final int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	// }}

	// {{ Descripcion (property)
	private String descripcion;

	@Named("Descripción")
	@MultiLine(numberOfLines = 3)
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "false")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	// }}

	// {{ Menu (property)
	private Menu menu;

	@Optional
	@MemberOrder(sequence = "5")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(final Menu menu) {
		this.menu = menu;
	}

	// }}

	private SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

	// {{ FechaInicio (property)
	private Date fechaInicio;

	@MemberOrder(sequence = "6")
	public String getFechaInicio() {
		return formato.format(fechaInicio);
	}

	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	// }}

	// {{ Caducidad (property)
	private Date caducidad;

	@MemberOrder(sequence = "7")
	public String getCaducidad() {
		return formato.format(caducidad);
	}

	public void setCaducidad(final Date caducidad) {
		this.caducidad = caducidad;
	}

	// }}
	
	// {{ Descuento (property)
	private int descuento;

	@Named("Descuento (%)")
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(final int descuento) {
		this.descuento = descuento;
	}
	// }}

	@Bulk
	@MemberOrder(sequence = "1")
	public List<Oferta> borrar() {
		contenedor.removeIfNotAlready(this);
		return ofertaServicio.listarOfertas();
	}

	@Named("Precio Final ($)")
	@Disabled
	@MemberOrder(sequence = "11")
	public double getPrecioFinal() {
		// return precioFinal;
		return ofertaServicio.calcularDescuento(this);
	}

	@Named("Precio Sin Descuento ($)")
	@Disabled
	@MemberOrder(sequence="10")
	public double getPrecioSinDescuento(){
		return ofertaServicio.calcularTotal(this);
	}
	
	// {{ injected: DomainObjectContainer
	@Inject
	private DomainObjectContainer contenedor;

	@Inject
	private OfertaServicio ofertaServicio;
}
