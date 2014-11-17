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

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

import com.google.common.base.Predicate;

import dom.producto.guarnicion.Guarnicion;
import dom.producto.guarnicion.GuarnicionServicio;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoEntrada.PlatoEntradaServicio;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.platoPrincipal.PlatoPrincipalServicio;
import dom.producto.postre.Postre;
import dom.producto.postre.PostreServicio;

/**
* Clase que da funcionalidad de crear, persistir, calcular precios con o sin descuento,
* listar en forma parcial o total los items de Menu
* @author RestoTesis
* @since 10/08/2014
* @version 1.0.0
*/
@DomainService
@Named("Menu")
public class MenuServicio extends AbstractFactoryAndRepository {

	/**
	 * Obtiene el nombre del icono para el menu 
	 * @return String
	 */
	public String iconName() {
		return "Menu";
	}

	/**
	 * Obtiene los datos validados del Menu desde la interfaz de usuario
	 * @param _nombre String
	 * @param _platoprincipal PlatoPrincipal
	 * @param _platoEntrada PlatoEntrada
	 * @param _guarnicion Guarnicion
	 * @param _postrePostre 
	 * @param _descuento int
	 * @return nuevoMenu Menu
	 */
	@Hidden(where = Where.OBJECT_FORMS)
	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Menu crearMenu(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*") String _nombre,
			@Named("Plato Principal") final PlatoPrincipal _platoPrincipal,
			@Named("Plato de Entrada") @Optional final PlatoEntrada _platoEntrada,
			@Named("Guarnición") @Optional final Guarnicion _guarnicion,
			@Named("Postre") @Optional final Postre _postre,
			@Named("Descuento (%)") final int _descuento) {
		return nuevoMenu(_nombre, _platoPrincipal, _platoEntrada, _guarnicion,
				_postre, _descuento);
	}

	/**
	 * Toma los datos obtenidos en la carga de menu desde el metodo
	 * crearMenu() y persiste los items en el nuevo Menu
	 * @param _nombre String
	 * @param _platoprincipal PlatoPrincipal
	 * @param _platoEntrada PlatoEntrada
	 * @param _guarnicion Guarnicion
	 * @param _postrePostre 
	 * @param _descuento int
	 * @see dom.menu.MenuServicio.crearMenu
	 * @return menu Menu
	 */
	@Programmatic
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
		menu.setBaja(false);
		persist(menu);
		return menu;
	}

	/**
	 * Valida que el descuento aplicado al Menu no sea mayor del 50 %
	 * @param _nombre String
	 * @param _platoprincipal PlatoPrincipal
	 * @param _platoEntrada PlatoEntrada
	 * @param _guarnicion Guarnicion
	 * @param _postrePostre 
	 * @param _descuento int
	 * @return String
	 */
	@Programmatic
	public String validateCrearMenu(final String _nombre,
			final PlatoPrincipal _platoPrincipal,
			final PlatoEntrada _platoEntrada, final Guarnicion _guarnicion,
			final Postre _postre, final int _descuento) {
		return (_descuento > 80) ? "El porcentaje máximo de descuento para los menues es de 80%"
				: null;
	}

	/**
	 * Calcula el Precio de Menu a partir de un descuento aplicado
	 * @see dom.menu.Menu.getDescuento()
	 * @return total double
	 */
	@Programmatic
	public double calcularDescuento(Menu _menu) {
		final double total = calcularTotal(_menu);
		return (total - ((total / 100) * _menu.getDescuento()));
	}

	/**
	 * Calcula el Precio total del Menu
	 * @param _menu Menu
	 * @see dom.producto.Producto.getPrecio()
	 * @return total double
	 */
	@Programmatic
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

	/**
	 * Obtiene una lista de los Platos Principales de Alta
	 * @see dom.producto.platoPrincipal.PlatoPrincipalServicio.listarPLatosPrincipalesAlta()
	 * @return listarPlatosPrincipalesAlta() List<PlatoPrincipal>;
	 */
	@Programmatic
	public List<PlatoPrincipal> choices1CrearMenu() {
		return platoPrincipalServicio.listarPLatosPrincipalesAlta();
	}

	/**
	 * Obtiene una lista de los Platos de Entrada de alta
	 * @see dom.producto.platoEntrada.PlatoEntradaServicio.listarPLatosEntradaAlta()
	 * @return platoEntradaServicio List<PlatoEntrada>
	 */
	@Programmatic
	public List<PlatoEntrada> choices2CrearMenu() {
		return platoEntradaServicio.listarPLatosEntradaAlta();
	}

	/**
	 * Obtiene una lista de las guarniciones de alta
	 * @see dom.producto.guarnicion.GuarnicionServicio.listarGuarnicionesAlta()
	 * @return guarnicionServicio List<Guarnicion>
	 */
	@Programmatic
	public List<Guarnicion> choices3CrearMenu() {
		return guarnicionServicio.listarGuarnicionesAlta();
	}

	/**
	 * Obtiene una lista de Postres de alta
	 * @see dom.producto.postre.PostreServicio.listarPostresAlta()
	 * @return postreServicio List<Postre>
	 */
	@Programmatic
	public List<Postre> choices4CrearMenu() {
		return postreServicio.listarPostresAlta();
	}

	/**
	 * Injección del Servicio de los Platos de Entrada
	 */
	
	@Inject
	private PlatoEntradaServicio platoEntradaServicio;

	/**
	 * Injección del Servicio de los Platos Principales
	 */
	@Inject
	private PlatoPrincipalServicio platoPrincipalServicio;

	/**
	 * Injección del Servicio de los Postres
	 */
	@Inject
	private PostreServicio postreServicio;
	/**
	 * Injección del Servicio de las Guarniciones
	 */
	@Inject
	private GuarnicionServicio guarnicionServicio;

	/**
	 * Obtiene una lista de Menus que se van filtrando a medida
	 * que se van escribiendo los caracteres
	 * @param nombre String
	 * @return List<Menu>
	 */
	@Programmatic
	public List<Menu> completarMenu(final String nombre) {
		return allMatches(new QueryDefault<Menu>(Menu.class, "menuQueEmpiezan",
				"nombre", "(?i).*" + nombre + ".*"));
	}

	/**
	 * Obtiene una lista de todos los Menues de alta
	 * @see dom.menu.Menu.getBaja()
	 * @return List<Menu>
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Menu> listarMenuesAlta() {
		return allMatches(Menu.class, new Predicate<Menu>() {

			@Override
			public boolean apply(Menu input) {
				// TODO Auto-generated method stub
				return input.getBaja() ? false : true;
			}
		});
	}

	/**
	 * Obtiene una lista de todos los Menues en general
	 * @return List<Menu>
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Menu> listarMenuesTodos() {
		return allInstances(Menu.class);
	}
}
