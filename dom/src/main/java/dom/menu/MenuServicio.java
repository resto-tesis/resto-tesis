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

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.platoEntrada.PlatoEntrada;
import dom.platoPrincipal.PlatoPrincipal;
import dom.postre.Postre;

@DomainService
@Named("Menu")
public class MenuServicio extends AbstractFactoryAndRepository {

	@Hidden(where = Where.OBJECT_FORMS)
	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Menu crearMenu(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") String _nombre,
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
		menu.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		persist(menu);
		return menu;
	}

	@Hidden
	public double calcularDescuento(Menu _menu) {
		double total = _menu.getBebida().getPrecio()
				+ _menu.getPlatoPrincipal().getPrecio();
		total += (_menu.getGuarnicion() == null) ? 0 : _menu.getGuarnicion()
				.getPrecio();
		total += (_menu.getPlatoEntrada() == null) ? 0 : _menu
				.getPlatoEntrada().getPrecio();
		total += (_menu.getPostre() == null) ? 0 : _menu.getPostre()
				.getPrecio();
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
	
	@Hidden
	public List<Menu> completarMenu(final String nombre) {
		return allMatches(new QueryDefault<Menu>(
				Menu.class, "menuQueEmpiezan", "nombre",
				"(?i).*" + nombre + ".*"));
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Menu> listarMenues() {
		final List<Menu> listaMenues = allInstances(Menu.class);
		return listaMenues;
	}
}
