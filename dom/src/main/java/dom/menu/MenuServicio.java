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

import dom.producto.bebida.Bebida;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;

@DomainService
@Named("Menu")
public class MenuServicio extends AbstractFactoryAndRepository {

	@Hidden(where = Where.OBJECT_FORMS)
	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Menu crearMenu(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") String _nombre,
			@Named("Plato Principal") final PlatoPrincipal _platoPrincipal,
			@Named("Plato de Entrada") @Optional final PlatoEntrada _platoEntrada,
			@Named("Guarnición") @Optional final Guarnicion _guarnicion,
			@Named("Postre") @Optional final Postre _postre,
			@Named("Descuento (%)") final int _descuento) {
		return nuevoMenu(_nombre, _platoPrincipal, _platoEntrada, _guarnicion,
				_postre, _descuento);
	}

	@Hidden
	public Menu nuevoMenu(final String _nombre,
			final PlatoPrincipal _platoPrincipal,
			final PlatoEntrada _platoEntrada, final Guarnicion _guarnicion,
			final Postre _postre, final int _descuento) {
		final Menu menu = newTransientInstance(Menu.class);
		menu.setGuarnicion(_guarnicion);
		menu.setPlatoEntrada(_platoEntrada);
		menu.setPlatoPrincipal(_platoPrincipal);
		menu.setPostre(_postre);
		menu.setDescuento(_descuento);
		menu.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		menu.setEstadoLogico(EstadoLogico.Habilitado);
		persist(menu);
		return menu;
	}

	@Hidden
	public double calcularDescuento(Menu _menu) {
		final double total = calcularTotal(_menu);
		return (total - ((total / 100) * _menu.getDescuento()));
	}

	@Hidden
	public double calcularTotal(Menu _menu) {
		double total = _menu.getPlatoPrincipal().getPrecio();
		total += (_menu.getGuarnicion() == null) ? 0 : _menu.getGuarnicion()
				.getPrecio();
		total += (_menu.getPlatoEntrada() == null) ? 0 : _menu
				.getPlatoEntrada().getPrecio();
		total += (_menu.getPostre() == null) ? 0 : _menu.getPostre()
				.getPrecio();
		return total;
	}

	@Hidden
	public List<PlatoPrincipal> choices1CrearMenu() {
		return allInstances(PlatoPrincipal.class);
	}

	@Hidden
	public List<PlatoEntrada> choices2CrearMenu() {
		return allInstances(PlatoEntrada.class);
	}

	@Hidden
	public List<Guarnicion> choices3CrearMenu() {
		return allInstances(Guarnicion.class);
	}

	@Hidden
	public List<Postre> choices4CrearMenu() {
		return allInstances(Postre.class);
	}

	@Hidden
	public List<Menu> completarMenu(final String nombre) {
		return allMatches(new QueryDefault<Menu>(Menu.class, "menuQueEmpiezan",
				"nombre", "(?i).*" + nombre + ".*"));
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Menu> listarMenues() {
		return allInstances(Menu.class);
	}
}
