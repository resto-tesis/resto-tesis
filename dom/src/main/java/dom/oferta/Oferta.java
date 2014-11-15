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

import java.util.ArrayList;
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
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;

import dom.cliente.Cliente;
import dom.menu.Menu;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroOferta", strategy = SequenceStrategy.CONTIGUOUS)
@Query(name = "ofertasQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.oferta.Oferta WHERE nombre.matches(:nombre)")
@AutoComplete(repository = OfertaServicio.class, action = "completarOfertas")
public class Oferta extends Observado {

	public String iconName() {
		return getBaja() ? "OfertaDes" : "Oferta";
	}

	// {{ Numero (property)
	private int numero;

	@Hidden(where = Where.ALL_TABLES)
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

	@Hidden(where = Where.ALL_TABLES)
	@RegEx(validation = "[0-9a-zA-ZñÑáéíóúÁÉÍÓÚñÑ\\s]*")
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

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	public int getCantidadPersonas() {
		return cantidadPersonas;
	}

	public void setCantidadPersonas(final int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	// }}

	// {{ Descripcion (property)
	private String descripcion;

	@Hidden(where = Where.ALL_TABLES)
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

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(final Menu menu) {
		this.menu = menu;
	}

	// }}

	public List<Menu> choicesMenu() {
		return ofertaServicio.listarMenues();
	}

	// {{ FechaInicio (property)
	private java.sql.Date fechaInicio;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "6")
	public java.sql.Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(final java.sql.Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	// }}

	// {{ Caducidad (property)
	private java.sql.Date caducidad;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "7")
	public java.sql.Date getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(final java.sql.Date caducidad) {
		this.caducidad = caducidad;
	}

	// }}

	// {{ Descuento (property)
	private int descuento;

	@Named("Descuento (%)")
	@MemberOrder(sequence = "10")
	@Column(allowsNull = "false")
	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(final int descuento) {
		this.descuento = descuento;
	}

	// }}

	@Named("Precio Final ($)")
	@Disabled
	@MemberOrder(sequence = "12")
	public double getPrecioFinal() {
		// return precioFinal;
		return ofertaServicio.calcularDescuento(this);
	}

	@Named("Precio Sin Descuento ($)")
	@Disabled
	@MemberOrder(sequence = "11")
	public double getPrecioSinDescuento() {
		return ofertaServicio.calcularTotal(this);
	}

	// {{ Baja (property)
	private boolean baja;

	@Hidden
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public boolean getBaja() {
		return baja;
	}

	public void setBaja(final boolean baja) {
		this.baja = baja;
	}

	// }}

	public Oferta baja() {
		setBaja(true);
		return this;
	}

	public Oferta alta() {
		setBaja(false);
		return this;
	}

	public String disableBaja() {
		return getBaja() ? "Oferta dada de Baja!" : null;
	}

	public String disableAlta() {
		return getBaja() ? null : "Oferta dada de Alta!";
	}

	// {{ injected: DomainObjectContainer
	@Inject
	private DomainObjectContainer contenedor;

	@Inject
	private OfertaServicio ofertaServicio;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((caducidad == null) ? 0 : caducidad.hashCode());
		result = prime * result + cantidadPersonas;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + descuento;
		result = prime * result
				+ ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + numero;
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
		Oferta other = (Oferta) obj;
		if (caducidad == null) {
			if (other.caducidad != null)
				return false;
		} else if (!caducidad.equals(other.caducidad))
			return false;
		if (cantidadPersonas != other.cantidadPersonas)
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (descuento != other.descuento)
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numero != other.numero)
			return false;
		return true;
	}

	// {{ ListaClientes (Collection)
	private List<Cliente> listaClientes = new ArrayList<Cliente>();

	@Hidden
	public List<Cliente> getListaClientes() {
		return contenedor.allInstances(Cliente.class);
	}

	public void setListaClientes(final List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	// }}

	@Hidden
	@MemberOrder(name = "listaClientes", sequence = "2")
	public Oferta quitarCliente(final Cliente cliente) {
		getListaClientes().remove(cliente);
		return this;
	}

	@Hidden
	@Override
	public void registrarCliente(Cliente _cliente) {
		// TODO Auto-generated method stub
		listaClientes.add(_cliente);
	}

	@Hidden
	@Override
	public void removerCliente(Cliente _cliente) {
		// TODO Auto-generated method stub
		int i = listaClientes.indexOf(_cliente);
		if (i >= 0) {
			listaClientes.remove(i);
		}
	}

	@Override
	public void notificarClientes() {
		// TODO Auto-generated method stub
		listaClientes = this.getListaClientes();
		for (Cliente _cliente : listaClientes) {
			_cliente.actualizar(this);
		}
		contenedor.informUser("Se ha notificado a todos los Clientes");
	}

	@Hidden
	public void datosModificados() {
		notificarClientes();
	}

	@Hidden
	public void setDatos(String _nombre, int _cantidad_personas,
			String _descripcion, Menu _menu, java.sql.Date _fechaInicio,
			java.sql.Date _caducidad, int _descuento) {
		this.nombre = _nombre;
		this.cantidadPersonas = _cantidad_personas;
		this.descripcion = _descripcion;
		this.menu = _menu;
		this.fechaInicio = _fechaInicio;
		this.caducidad = _caducidad;
		this.descuento = _descuento;
		datosModificados();
	}

	@Override
	public String toString() {
		return "Oferta [nombre=" + nombre + ", cantidadPersonas="
				+ cantidadPersonas + ", descripcion=" + descripcion + ", menu="
				+ menu + ", fechaInicio=" + fechaInicio + ", caducidad="
				+ caducidad + ", descuento=" + descuento + ", ofertaServicio="
				+ ofertaServicio + "]";
	}
}
