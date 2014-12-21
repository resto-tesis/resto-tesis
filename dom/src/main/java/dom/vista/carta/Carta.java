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

package dom.vista.carta;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

import dom.menu.Menu;
import dom.producto.bebida.Bebida;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;

/**
 * Entidad en la cual representa la carta de local, donde se puede ver el conjunto de la totalidad 
 * de los productos que ofrece el negocio a sus clientes   
 * @author RestoTesis
 * @since 10/08/2014
 * @version 1.0.0
 */
@MemberGroupLayout(columnSpans = { 6, 0, 6 }, left = { "izquierda" }, right = { "derecha" })
public class Carta extends AbstractViewModel {

	/**
	 * Obtiene el titulo para la carta
	 * @return String
	 */
	public String title() {
		return "Carta";
	}

	private String memento;

	/**
	 * Obtiene la lista de Bebidas para la carta
	 * @return List<Bebida>
	 */
	@Disabled
	@MemberOrder(name = "izquierda", sequence = "1")
	@Render(Type.EAGERLY)
	public List<Bebida> getBebida() {
		return cartaServicio.listarBebidas();
	}

	/**
	 * Obtiene la lista de Guarniciones para la carta
	 * @return List<Guarnicion>
	 */
	@Disabled
	@MemberOrder(name = "izquierda", sequence = "2")
	@Render(Type.EAGERLY)
	public List<Guarnicion> getGuarnicion() {
		return cartaServicio.listarGuarnicion();
	}

	/**
	 * Obtiene la lista de Postres para la carta
	 * @return List<Postre>
	 */
	@Render(Type.EAGERLY)
	@MemberOrder(name = "izquierda", sequence = "3")
	@Disabled
	public List<Postre> getPostre() {
		return cartaServicio.listarPostres();
	}

	/**
	 * Obtiene la lista de menues para la carta
	 * @return List<Menu>
	 */
	@Render(Type.EAGERLY)
	@MemberOrder(name = "derecha", sequence = "1")
	public List<Menu> getMenu() {
		return cartaServicio.listarMenu();
	}

	/**
	 * Obtiene la lista de platos de entradas para la carta
	 * @return List<PlatoEntrada>
	 */
	@Render(Type.EAGERLY)
	@MemberOrder(name = "derecha", sequence = "2")
	@Disabled
	public List<PlatoEntrada> getPlatoEntrada() {
		return cartaServicio.listarPlatosEntradas();
	}

	/**
	 * Obtiene la lista de platos principales para la carta
	 * @return List<PlatoPrincipal>
	 */
	@Render(Type.EAGERLY)
	@MemberOrder(name = "derecha", sequence = "3")
	@Disabled
	public List<PlatoPrincipal> getPlatoPrincipal() {
		return cartaServicio.listarPlatosPricipales();
	}

	@Inject
	private CartaServicio cartaServicio;

	@Override
	public String viewModelMemento() {
		// TODO Auto-generated method stub
		return memento;
	}

	@Override
	public void viewModelInit(String memento) {
		// TODO Auto-generated method stub
		this.memento = memento;
	}
}
