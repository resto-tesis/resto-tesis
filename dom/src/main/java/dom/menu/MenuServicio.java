package dom.menu;

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

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.plato.PlatoEntrada;
import dom.plato.PlatoPrincipal;
import dom.postre.Postre;

@Named("Menu")
public class MenuServicio extends AbstractFactoryAndRepository {

	public MenuServicio() {
		// TODO Auto-generated constructor stub
	}

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Menu crearMenu(
			@Named("Nombre") String _nombre,
			@Named("Plato Principal") final PlatoPrincipal _platoPrincipal,
			@Named("Bebida") final Bebida _bebida,
			@Named("Plato de Entrada") @Optional final PlatoEntrada _platoEntrada,
			@Named("Guarnición") @Optional final Guarnicion _guarnicion,
			@Named("Postre") @Optional final Postre _postre,
			@Named("Descuento (%)") final int _descuento) {
		return nuevoMenu(_nombre, _platoPrincipal, _bebida, _platoEntrada,
				_guarnicion, _postre, _descuento);
	}

	@Hidden
	public Menu nuevoMenu(final String _nombre,
			final PlatoPrincipal _platoPrincipal, final Bebida _bebida,
			final PlatoEntrada _platoEntrada, final Guarnicion _guarnicion,
			final Postre _postre, final int _descuento) {
		final Menu menu = newTransientInstance(Menu.class);
		menu.setBebida(_bebida);
		menu.setGuarnicion(_guarnicion);
		menu.setPlatoEntrada(_platoEntrada);
		menu.setPlatoPrincipal(_platoPrincipal);
		menu.setPostre(_postre);
		menu.setDescuento(_descuento);
		menu.setNombre(_nombre);
		persist(menu);
		return menu;
	}

	@Hidden
	public double calcularDescuento(Menu _menu) {
		final Double total = _menu.getBebida().getPrecio()
				+ _menu.getGuarnicion().getPrecio()
				+ _menu.getPlatoEntrada().getPrecio()
				+ _menu.getPlatoPrincipal().getPrecio()
				+ _menu.getPostre().getPrecio();
		final double calculo = total - ((total / 100) * _menu.getDescuento());
		return calculo;
	}

	@Hidden
	public List<PlatoPrincipal> choices1CrearMenu() {
		return allInstances(PlatoPrincipal.class);
	}

	@Hidden
	public List<Bebida> choices2CrearMenu() {
		return allInstances(Bebida.class);
	}

	@Hidden
	public List<PlatoEntrada> choices3CrearMenu() {
		return allInstances(PlatoEntrada.class);
	}

	@Hidden
	public List<Guarnicion> choices4CrearMenu() {
		return allInstances(Guarnicion.class);
	}

	@Hidden
	public List<Postre> choices5CrearMenu() {
		return allInstances(Postre.class);
	}

	@Named("Listrar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Menu> listarMenues() {
		final List<Menu> listaMenues = allInstances(Menu.class);
		return listaMenues;
	}
}
