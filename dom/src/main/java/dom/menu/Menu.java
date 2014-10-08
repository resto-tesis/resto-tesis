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

package dom.menu;

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
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;

import dom.producto.bebida.Bebida;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroMenu", strategy = SequenceStrategy.CONTIGUOUS)
@Query(name = "menuQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.menu.Menu WHERE nombre.matches(:nombre)")
@AutoComplete(repository = MenuServicio.class, action = "completarMenu")
public class Menu {
	// {{ Numero (property)
	private int numero;

	@Hidden(where = Where.ALL_TABLES)
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroMenu")
	@TypicalLength(3)
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

	// {{ EstadoLogico (property)
	private EstadoLogico estadoLogico;

	@Hidden(where = Where.ALL_TABLES)
	@Named("Estado de Alta")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "11")
	public EstadoLogico getEstadoLogico() {
		return estadoLogico;
	}

	public void setEstadoLogico(final EstadoLogico estadoLogico) {
		this.estadoLogico = estadoLogico;
	}

	// }}

	// {{ Nombre (property)
	private String nombre;

	@Hidden(where = Where.ALL_TABLES)
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	@Title
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ PlatoPrincipal (property)
	private PlatoPrincipal platoPrincipal;

	@Hidden(where = Where.ALL_TABLES)
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public PlatoPrincipal getPlatoPrincipal() {
		return platoPrincipal;
	}

	public void setPlatoPrincipal(final PlatoPrincipal platoPrincipal) {
		this.platoPrincipal = platoPrincipal;
	}

	// }}

	// {{ Guarnicion (property)
	private Guarnicion guarnicion;

	@Hidden(where = Where.ALL_TABLES)
	@Optional
	@MemberOrder(sequence = "5")
	public Guarnicion getGuarnicion() {
		return guarnicion;
	}

	public void setGuarnicion(final Guarnicion guarnicion) {
		this.guarnicion = guarnicion;
	}

	// }}

	// {{ PlatoEntrada (property)
	private PlatoEntrada platoEntrada;

	@Hidden(where = Where.ALL_TABLES)
	@Optional
	@MemberOrder(sequence = "6")
	public PlatoEntrada getPlatoEntrada() {
		return platoEntrada;
	}

	public void setPlatoEntrada(final PlatoEntrada platoEntrada) {
		this.platoEntrada = platoEntrada;
	}

	// }}

	// {{ Postre (property)
	private Postre postre;

	@Hidden(where = Where.ALL_TABLES)
	@Optional
	@MemberOrder(sequence = "7")
	public Postre getPostre() {
		return postre;
	}

	public void setPostre(final Postre postre) {
		this.postre = postre;
	}

	// }}

	// {{ Descuento (property)
	private int descuento;

	@Named("Descuento (%)")
	@MemberOrder(sequence = "8")
	@Column(allowsNull = "false")
	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(final int descuento) {
		this.descuento = descuento;
	}

	// }}

	public List<PlatoEntrada> choicesPlatoEntrada() {
		return menuServicio.choices2CrearMenu();
	}

	public List<PlatoPrincipal> choicesPlatoPrincipal() {
		return menuServicio.choices1CrearMenu();
	}

	public List<Postre> choicesPostre() {
		return menuServicio.choices4CrearMenu();
	}

	public List<Guarnicion> choicesGuarnicion() {
		return menuServicio.choices3CrearMenu();
	}

	@Named("Precio Final ($)")
	@Disabled
	@MemberOrder(sequence = "11")
	public double getPrecioFinal() {
		// return precioFinal;
		return menuServicio.calcularDescuento(this);
	}

	@Named("Precio Sin Descuento ($)")
	@Disabled
	@MemberOrder(sequence = "10")
	public double getPrecioSinDescuento() {
		return menuServicio.calcularTotal(this);
	}

	// }}

	public Menu deshabilitarMenu() {
		setEstadoLogico(EstadoLogico.Deshabilitado);
		return this;
	}

	public Menu habilitarMenu() {
		setEstadoLogico(EstadoLogico.Habilitado);
		return this;
	}

	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Menu> borrar() {
		contenedor.removeIfNotAlready(this);
		return menuServicio.listarMenues();
	}

	// {{ injected: DomainObjectContainer
	@Inject
	private DomainObjectContainer contenedor;

	@Inject
	private MenuServicio menuServicio;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + descuento;
		result = prime * result
				+ ((estadoLogico == null) ? 0 : estadoLogico.hashCode());
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
		Menu other = (Menu) obj;
		if (descuento != other.descuento)
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
		return true;
	}

}
