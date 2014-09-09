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

package dom.carta;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

import dom.comestibles.bebida.Bebida;
import dom.comestibles.guarnicion.Guarnicion;
import dom.comestibles.platoEntrada.PlatoEntrada;
import dom.comestibles.platoPrincipal.PlatoPrincipal;
import dom.comestibles.postre.Postre;
import dom.menu.Menu;

@MemberGroupLayout(columnSpans = { 6, 0, 6 }, left = { "izquierda" }, right = { "derecha" })
public class Carta extends AbstractViewModel {

	public String title() {
		return "Carta";
	}

	private String memento;

	@Disabled
	@MemberOrder(name = "izquierda", sequence = "1")
	@Render(Type.EAGERLY)
	public List<Bebida> getBebida() {
		return cartaServicio.listarBebidas();
	}

	@Disabled
	@MemberOrder(name = "izquierda", sequence = "2")
	@Render(Type.EAGERLY)
	public List<Guarnicion> getGuarnicion() {
		return cartaServicio.listarGuarnicion();
	}

	@Render(Type.EAGERLY)
	@MemberOrder(name = "izquierda", sequence = "3")
	@Disabled
	public List<Postre> getPostre() {
		return cartaServicio.listarPostres();
	}

	@Render(Type.EAGERLY)
	@MemberOrder(name = "derecha", sequence = "1")
	public List<Menu> getMenu() {
		return cartaServicio.listarMenu();
	}

	@Render(Type.EAGERLY)
	@MemberOrder(name = "derecha", sequence = "2")
	@Disabled
	public List<PlatoEntrada> getPlatoEntrada() {
		return cartaServicio.listarPlatosEntradas();
	}

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
