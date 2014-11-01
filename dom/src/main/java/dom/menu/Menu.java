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
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;

import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroMenu", strategy = SequenceStrategy.CONTIGUOUS)
@Query(name = "menuQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.menu.Menu WHERE nombre.matches(:nombre)")
@AutoComplete(repository = MenuServicio.class, action = "completarMenu")
public class Menu {

	public String iconName() {
		return getBaja() ? "MenuDes" : "Menu";
	}

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

	public Menu baja() {
		setBaja(true);
		return this;
	}

	public Menu alta() {
		setBaja(false);
		return this;
	}

	public String disableBaja() {
		return getBaja() ? "Menu dado de Baja!" : null;
	}

	public String disableAlta() {
		return getBaja() ? null : "Menu dado de Alta!";
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
		result = prime * result + (baja ? 1231 : 1237);
		result = prime * result
				+ ((contenedor == null) ? 0 : contenedor.hashCode());
		result = prime * result + descuento;
		result = prime * result
				+ ((guarnicion == null) ? 0 : guarnicion.hashCode());
		result = prime * result
				+ ((menuServicio == null) ? 0 : menuServicio.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + numero;
		result = prime * result
				+ ((platoEntrada == null) ? 0 : platoEntrada.hashCode());
		result = prime * result
				+ ((platoPrincipal == null) ? 0 : platoPrincipal.hashCode());
		result = prime * result + ((postre == null) ? 0 : postre.hashCode());
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
		if (baja != other.baja)
			return false;
		if (contenedor == null) {
			if (other.contenedor != null)
				return false;
		} else if (!contenedor.equals(other.contenedor))
			return false;
		if (descuento != other.descuento)
			return false;
		if (guarnicion == null) {
			if (other.guarnicion != null)
				return false;
		} else if (!guarnicion.equals(other.guarnicion))
			return false;
		if (menuServicio == null) {
			if (other.menuServicio != null)
				return false;
		} else if (!menuServicio.equals(other.menuServicio))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numero != other.numero)
			return false;
		if (platoEntrada == null) {
			if (other.platoEntrada != null)
				return false;
		} else if (!platoEntrada.equals(other.platoEntrada))
			return false;
		if (platoPrincipal == null) {
			if (other.platoPrincipal != null)
				return false;
		} else if (!platoPrincipal.equals(other.platoPrincipal))
			return false;
		if (postre == null) {
			if (other.postre != null)
				return false;
		} else if (!postre.equals(other.postre))
			return false;
		return true;
	}

}
