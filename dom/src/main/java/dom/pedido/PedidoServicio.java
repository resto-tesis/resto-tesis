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

package dom.pedido;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import dom.comanda.ComandaServicio;
import dom.menu.Menu;
import dom.menu.MenuServicio;
import dom.mesa.Mesa;
import dom.objetosValor.ValueMenu;
import dom.objetosValor.ValueOferta;
import dom.objetosValor.ValueProductoElaborado;
import dom.objetosValor.ValueProductoNoElaborado;
import dom.oferta.Oferta;
import dom.oferta.OfertaServicio;
import dom.producto.ProductoElaborado;
import dom.producto.bebida.Bebida;
import dom.producto.bebida.BebidaServicio;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.guarnicion.GuarnicionServicio;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoEntrada.PlatoEntradaServicio;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.platoPrincipal.PlatoPrincipalServicio;
import dom.producto.postre.Postre;
import dom.producto.postre.PostreServicio;
/**
 * Otorga la funcionalidad de crear un pedido, agregar menues y ofertas al pedido,
 * crear pedidos solo de bebidas y cargarlos en la comanda.-
 * @author RestoTesis
 * @since 10/09/2014
 * @version 1.0.0
 */
@DomainService
public class PedidoServicio extends AbstractFactoryAndRepository {

	/**
	 * Constructor de la clase
	 */
	public PedidoServicio() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene y persiste un Pedido
	 * @return pedido Pedido
	 */
	@Programmatic
	public Pedido crearPedido() {
		final Pedido pedido = newTransientInstance(Pedido.class);
		pedido.setComanda(comandaServicio.crearComanda());
		persist(pedido);
		return pedido;
	}

	/**
	 * Verifica si un Pedido esta compuesto de Bebidas solamente
	 * @param _pedido Pedido
	 * @return Boolean
	 */
	@Programmatic
	public boolean soloBebidas(final Pedido _pedido) {
		return (_pedido.getComanda().getProductos().isEmpty() && _pedido
				.getComanda().getMenues().isEmpty()&&_pedido.getComanda().getOfertas().isEmpty()) ? true : false;
	}

	/**
	 * Obtiene una lista de Bebidas
	 * @return List<Bebida>
	 */
	@Programmatic
	public List<Bebida> listarBebidas() {
		return bebidaServicio.listarBebidasAlta();
	}

	/**
	 * Obtiene una lista de Postres
	 * @return List<Postre>
	 */
	@Programmatic
	public List<Postre> listarPostres() {
		return postreServicio.listarPostresAlta();
	}

	/**
	 * Obtiene una lista de Platos Principales
	 * @return List<PlatoPrincipal>
	 */
	@Programmatic
	public List<PlatoPrincipal> listarPlatosPrincipales() {
		return platoPrincipalServicio.listarPLatosPrincipalesAlta();
	}

	/**
	 * Obtiene una lista de platos de entrada
	 * @return List<PlatoEntrada>
	 */
	@Programmatic
	public List<PlatoEntrada> listarPlatosEntrada() {
		return platoEntradaServicio.listarPLatosEntradaAlta();
	}

	/**
	 * Obtiene una lista de Guarniciones
	 * @return List<Guarnicion>
	 */
	@Programmatic
	public List<Guarnicion> listarGuarniciones() {
		return guarnicionServicio.listarGuarnicionesAlta();
	}

	/**
	 * Obtiene una lista de menues
	 * @return List<Menu>
	 */
	@Programmatic
	public List<Menu> listarMenues() {
		return menuServicio.listarMenuesAlta();
	}

	/**
	 * Obtiene una lista de Ofertas
	 * @return List<Oferta>
	 */
	@Programmatic
	public List<Oferta> listarOfertas() {
		return ofertaServicio.listarOfertasAlta();
	}

	/**
	 * Agrega y persiste los menues al pedido
	 * @param _menu1 Menu
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _menu2 Menu
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _pedido Pedido
	 */
	@Programmatic
	public void agregarMenu(final Menu _menu1, final Integer _cantidad1,
			final String _nota1, final Menu _menu2, final Integer _cantidad2,
			final String _nota2, Pedido _pedido) {
		final ValueMenu menu = newTransientInstance(ValueMenu.class);
		menu.setMenu(_menu1);
		menu.setCantidad(_cantidad1 == null ? 1 : _cantidad1);
		menu.setNota(_nota1);
		persist(menu);
		_pedido.getComanda().addToMenues(menu);
		if (_menu2 != null) {
			final ValueMenu menu2 = newTransientInstance(ValueMenu.class);
			menu2.setMenu(_menu2);
			menu2.setCantidad(_cantidad2 == null ? 1 : _cantidad2);
			menu2.setNota(_nota2);
			persist(menu2);
			_pedido.getComanda().addToMenues(menu2);
		}
	}

	/**
	 * Agrega y persiste las Ofertas del Pedido
	 * @param _oferta1 Oferta
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _oferta2 Oferta
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _pedido Pedido
	 */
	@Programmatic
	public void agregarOferta(final Oferta _oferta1, final Integer _cantidad1,
			final String _nota1, final Oferta _oferta2,
			final Integer _cantidad2, final String _nota2, Pedido _pedido) {
		final ValueOferta oferta = newTransientInstance(ValueOferta.class);
		oferta.setOferta(_oferta1);
		oferta.setCantidad(_cantidad1 == null ? 1 : _cantidad1);
		oferta.setNota(_nota1);
		persist(oferta);
		_pedido.getComanda().addToOfertas(oferta);
		if (_oferta2 != null) {
			final ValueOferta oferta2 = newTransientInstance(ValueOferta.class);
			oferta2.setOferta(_oferta2);
			oferta2.setCantidad(_cantidad2 == null ? 1 : _cantidad2);
			oferta2.setNota(_nota2);
			persist(oferta2);
			_pedido.getComanda().addToOfertas(oferta2);
		}
	}

	/**
	 * Agrega y persiste las Bebidas del Pedido
	 * @param _bebida1 Bebida
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _bebida2 Bebida
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _bebida3 Bebida
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _bebida4 Bebida
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @param _pedido Pedido
	 */
	@Programmatic
	public void agregarBebidas(final Bebida _bebida1, final Integer _cantidad1,
			final String _nota1, final Bebida _bebida2,
			final Integer _cantidad2, final String _nota2,
			final Bebida _bebida3, final Integer _cantidad3,
			final String _nota3, final Bebida _bebida4,
			final Integer _cantidad4, final String _nota4, Pedido _pedido) {
		final ValueProductoNoElaborado bebida1 = newTransientInstance(ValueProductoNoElaborado.class);
		bebida1.setProducto(_bebida1);
		bebida1.setCantidad(_cantidad1 == null ? 1 : _cantidad1);
		bebida1.setNota(_nota1);
		persist(bebida1);
		_pedido.addToBebidas(bebida1);
		if (_bebida2 != null) {
			final ValueProductoNoElaborado bebida2 = newTransientInstance(ValueProductoNoElaborado.class);
			bebida2.setProducto(_bebida2);
			bebida2.setCantidad(_cantidad2 == null ? 1 : _cantidad2);
			bebida2.setNota(_nota2);
			persist(bebida2);
			_pedido.addToBebidas(bebida2);
		}
		if (_bebida3 != null) {
			final ValueProductoNoElaborado bebida3 = newTransientInstance(ValueProductoNoElaborado.class);
			bebida3.setProducto(_bebida3);
			bebida3.setCantidad(_cantidad3 == null ? 1 : _cantidad3);
			bebida3.setNota(_nota3);
			persist(bebida3);
			_pedido.addToBebidas(bebida3);
		}
		if (_bebida4 != null) {
			final ValueProductoNoElaborado bebida4 = newTransientInstance(ValueProductoNoElaborado.class);
			bebida4.setProducto(_bebida4);
			bebida4.setCantidad(_cantidad4 == null ? 1 : _cantidad4);
			bebida4.setNota(_nota4);
			persist(bebida4);
			_pedido.addToBebidas(bebida4);
		}
	}

	/**
	 * Agrega y presisite un producto elaborado del pedido
	 * @param _producto1 ProductoElaborado
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _producto2 ProductoElaborado
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _producto3 ProductoElaborado
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _producto4 ProductoElaborado
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @param _pedido Pedido
	 */
	@Programmatic
	public void llenarComanda(final ProductoElaborado _producto1,
			final Integer _cantidad1, final String _nota1,
			final ProductoElaborado _producto2, final Integer _cantidad2,
			final String _nota2, final ProductoElaborado _producto3,
			final Integer _cantidad3, final String _nota3,
			final ProductoElaborado _producto4, final Integer _cantidad4,
			final String _nota4, Pedido _pedido) {
		final ValueProductoElaborado producto1 = newTransientInstance(ValueProductoElaborado.class);
		producto1.setProducto(_producto1);
		producto1.setCantidad(_cantidad1 == null ? 1 : _cantidad1);
		producto1.setNota(_nota1);
		persist(producto1);
		_pedido.getComanda().addToProductos(producto1);
		if (_producto2 != null) {
			final ValueProductoElaborado producto2 = newTransientInstance(ValueProductoElaborado.class);
			producto2.setProducto(_producto2);
			producto2.setCantidad(_cantidad2 == null ? 1 : _cantidad2);
			producto2.setNota(_nota2);
			persist(producto2);
			_pedido.getComanda().addToProductos(producto2);
		}
		if (_producto3 != null) {
			final ValueProductoElaborado producto3 = newTransientInstance(ValueProductoElaborado.class);
			producto3.setProducto(_producto3);
			producto3.setCantidad(_cantidad3 == null ? 1 : _cantidad3);
			producto3.setNota(_nota3);
			persist(producto3);
			_pedido.getComanda().addToProductos(producto3);
		}
		if (_producto4 != null) {
			final ValueProductoElaborado producto4 = newTransientInstance(ValueProductoElaborado.class);
			producto4.setProducto(_producto4);
			producto4.setCantidad(_cantidad4 == null ? 1 : _cantidad4);
			producto4.setNota(_nota4);
			persist(producto4);
			_pedido.getComanda().addToProductos(producto4);
		}
	}

	@Inject
	private ComandaServicio comandaServicio;

	@Inject
	private MenuServicio menuServicio;

	@Inject
	private PlatoPrincipalServicio platoPrincipalServicio;

	@Inject
	private PlatoEntradaServicio platoEntradaServicio;

	@Inject
	private PostreServicio postreServicio;

	@Inject
	private GuarnicionServicio guarnicionServicio;

	@Inject
	private BebidaServicio bebidaServicio;

	@Inject
	private OfertaServicio ofertaServicio;

	/**
	 * Obtiene la Mesa para el Pedido
	 * @param pedido Pedido
	 * @return mesa Mesa
	 */
	@Programmatic
	public Mesa volver(Pedido pedido) {
		// TODO Auto-generated method stub
		return pedido.getMesa();
	}

}
