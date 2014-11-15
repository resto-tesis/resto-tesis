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

package dom.factura;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import dom.objetosValor.ValueMenu;
import dom.objetosValor.ValueOferta;
import dom.objetosValor.ValueProductoElaborado;
import dom.objetosValor.ValueProductoNoElaborado;
import dom.pedido.Pedido;

/**
 * Clase que da funcionalidad de crear y persistir la Factura
 * 
 * @author RestoTesis
 * @since 15/10/2014
 * @version 1.0.0
 */
@DomainService(menuOrder = "90")
@Named("Facturas")
public class FacturaServicio extends AbstractFactoryAndRepository {

	/**
	 * Constructor de la clase FacturaServicio
	 */
	public FacturaServicio() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo que crea y persiste una nueva Factura, cargando todos sus items
	 * con precio
	 * 
	 * @param List
	 *            <Pedido> _pedidos
	 * @see dom.objetosValor.ValueProductoNoElaborado.getProducto()
	 * @see dom.objetosValor.ValueProductoNoElaborado.getCantidad()
	 * @see dom.objetosValor.ValueMenu.getMenu()
	 * @see dom.producto.Producto.getNombre()
	 * @see dom.producto.Producto.getPrecio()
	 * @see dom.factura.ItemFactura.getPrecioFinal()
	 * @see dom.pedido.Pedido.getComanda()
	 * @see dom.pedido.Pedido.getBebidas()
	 * @see dom.comanda.Comanda.getMenues()
	 * @see dom.menu.Menu.getNombre()
	 * @see dom.menu.Menu.getDescuento()
	 * @see dom.menu.Menu.getPrecioSinDescuento()
	 * @see dom.menu.Menu.getPlatoPrincipal()
	 * @see dom.menu.Menu.getPlatoEntrada()
	 * @see dom.menu.Menu.getPostre()
	 * @see dom.menu.Menu.getGuarnicion()
	 * @return Factura factura
	 */
	@Programmatic
	public Factura crearFactura(final List<Pedido> _pedidos) {
		double precioTotal = 0;
		final Factura factura = newTransientInstance(Factura.class);
		for (Pedido pedido : _pedidos) {
			for (ValueProductoNoElaborado bebida : pedido.getBebidas()) {
				final ItemFactura item = newTransientInstance(ItemFactura.class);
				item.setNombre(bebida.getProducto().getNombre());
				item.setCantidad(bebida.getCantidad());
				item.setDescuento(0);
				item.setPrecio(bebida.getProducto().getPrecio());
				persist(item);
				precioTotal += item.getPrecioFinal();
				factura.addToItems(item);
			}
			if (!pedido.getComanda().getProductos().isEmpty()) {
				for (ValueProductoElaborado producto : pedido.getComanda()
						.getProductos()) {
					final ItemFactura itemProducto = newTransientInstance(ItemFactura.class);
					itemProducto.setNombre(producto.getProducto().getNombre());
					itemProducto.setCantidad(producto.getCantidad());
					itemProducto.setDescuento(0);
					itemProducto.setPrecio(producto.getProducto().getPrecio());
					persist(itemProducto);
					precioTotal += itemProducto.getPrecioFinal();
					factura.addToItems(itemProducto);
				}
			}
			if (!pedido.getComanda().getMenues().isEmpty()) {
				for (ValueMenu menu : pedido.getComanda().getMenues()) {
					ItemFactura itemMenu = newTransientInstance(ItemFactura.class);
					itemMenu.setNombre("Menú - " + menu.getMenu().getNombre());
					itemMenu.setCantidad(menu.getCantidad());
					itemMenu.setDescuento(menu.getMenu().getDescuento());
					itemMenu.setPrecio(menu.getMenu().getPrecioSinDescuento());
					persist(itemMenu);
					precioTotal += itemMenu.getPrecioFinal();
					factura.addToItems(itemMenu);
				}
			}
			if (!pedido.getComanda().getMenues().isEmpty()) {
				for (ValueOferta oferta : pedido.getComanda().getOfertas()) {
					ItemFactura itemOferta = newTransientInstance(ItemFactura.class);
					itemOferta.setNombre("Oferta - "
							+ oferta.getOferta().getNombre());
					itemOferta.setCantidad(oferta.getCantidad());
					itemOferta.setDescuento(oferta.getOferta().getDescuento());
					itemOferta.setPrecio(oferta.getOferta()
							.getPrecioSinDescuento());
					persist(itemOferta);
					precioTotal += itemOferta.getPrecioFinal();
					factura.addToItems(itemOferta);
				}
			}
		}
		factura.setTotal(precioTotal);
		persist(factura);
		return factura;
	}

	/**
	 * Obtiene una lista de todas las facturas
	 * 
	 * @return List<Factura> lista
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "60")
	public List<Factura> listar() {
		final List<Factura> lista = allInstances(Factura.class);
		return lista;
	}
}
