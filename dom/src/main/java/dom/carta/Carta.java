package dom.carta;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optional;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.menu.Menu;
import dom.platoEntrada.PlatoEntrada;
import dom.platoPrincipal.PlatoPrincipal;
import dom.postre.Postre;

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

public class Carta {

	// {{ Bebida (Collection)
	private List<Bebida> listaBebidas = new ArrayList<Bebida>();

	@Disabled
	@Named("Lista de bebidas")
	@MemberOrder(sequence = "1")
	public List<Bebida> getBebida() {
		return listaBebidas;
	}

	public void setBebida(final List<Bebida> listaBebida) {
		this.listaBebidas = listaBebida;
	}
	// }}

// {{ Guarnicion (Collection)
private List<Guarnicion> listaGuarniciones = new ArrayList<Guarnicion>();

@Disabled
@MemberOrder(sequence = "2")
public List<Guarnicion> getGuarnicion() {
	return listaGuarniciones;
}

public void setGuarnicion(final List<Guarnicion> listaguarnicion) {
	this.listaGuarniciones = listaguarnicion;
}
// }}

// {{ Menu (Collection)
private List<Menu> listaMenues = new ArrayList<Menu>();

@Optional
@Disabled
@MemberOrder(sequence = "3")
public List<Menu> getMenu() {
	return listaMenues;
}

public void setMenu(final List<Menu> listaMenues) {
	this.listaMenues = listaMenues;
}
// }}

// {{ PlatoEntrada (Collection)
private List<PlatoEntrada> listaPlatosEntrada = new ArrayList<PlatoEntrada>();

@Disabled
@MemberOrder(sequence = "4")
public List<PlatoEntrada> getPlatoEntrada() {
	return listaPlatosEntrada;
}

public void setPlatoEntrada(final List<PlatoEntrada> listaPlatoEntrada) {
	this.listaPlatosEntrada = listaPlatoEntrada;
}
// }}

// {{ PlatoPrincipal (Collection)
private List<PlatoPrincipal> listaPlatosPrincipales = new ArrayList<PlatoPrincipal>();

@Disabled
@MemberOrder(sequence = "5")
public List<PlatoPrincipal> getPlatoPrincipal() {
	return listaPlatosPrincipales;
}

public void setPlatoPrincipal(final List<PlatoPrincipal> listaPlatoPrincipal) {
	this.listaPlatosPrincipales = listaPlatoPrincipal;
}
// }}

// {{ Postre (Collection)
private List<Postre> listaPostres = new ArrayList<Postre>();

@Disabled
@MemberOrder(sequence = "6")
public List<Postre> getPostre() {
	return listaPostres;
}

public void setPostre(final List<Postre> listaPostres) {
	this.listaPostres = listaPostres;
}
// }}

// {{ injected: DomainObjectContainer
private DomainObjectContainer contenedor;

public DomainObjectContainer getContenedor() {
	return contenedor;
}

public void setContenedor(DomainObjectContainer contenedor) {
	this.contenedor = contenedor;
}

private CartaServicio cartaServicio;

public void injectCartaServicio(final CartaServicio _cartaServicio) {
	cartaServicio = _cartaServicio;
}
	public List<Bebida> MostrarBebidas(){
		return cartaServicio.listarBebidas();
	}
}
