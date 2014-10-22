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
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import dom.objetosValor.ValueMenu;
import dom.objetosValor.ValueProductoElaborado;
import dom.objetosValor.ValueProductoNoElaborado;
import dom.pedido.Pedido;

@DomainService
public class FacturaServicio extends AbstractFactoryAndRepository {

	public FacturaServicio() {
		// TODO Auto-generated constructor stub
	}

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
					itemMenu.setNombre(menu.getMenu().getNombre().toUpperCase());
					itemMenu.setCantidad(menu.getCantidad());
					itemMenu.setDescuento(menu.getMenu().getDescuento());
					itemMenu.setPrecio(menu.getMenu().getPrecioSinDescuento());
					persist(itemMenu);
					precioTotal += itemMenu.getPrecioFinal();
					factura.addToItems(itemMenu);

					ItemFactura item1 = newTransientInstance(ItemFactura.class);
					item1.setNombre("--"
							+ menu.getMenu().getPlatoPrincipal().getNombre());
					item1.setCantidad(menu.getCantidad());
					item1.setDescuento(menu.getMenu().getDescuento());
					item1.setPrecio(menu.getMenu().getPlatoPrincipal()
							.getPrecio());
					persist(item1);
					factura.addToItems(item1);
					if (menu.getMenu().getPlatoEntrada() != null) {
						ItemFactura item2 = newTransientInstance(ItemFactura.class);
						item2.setNombre("--"
								+ menu.getMenu().getPlatoEntrada().getNombre());
						item2.setCantidad(menu.getCantidad());
						item2.setDescuento(menu.getMenu().getDescuento());
						item2.setPrecio(menu.getMenu().getPlatoEntrada()
								.getPrecio());
						persist(item2);
						factura.addToItems(item2);
					}
					if (menu.getMenu().getPostre() != null) {
						ItemFactura item3 = newTransientInstance(ItemFactura.class);
						item3.setNombre("--"
								+ menu.getMenu().getPostre().getNombre());
						item3.setCantidad(menu.getCantidad());
						item3.setDescuento(menu.getMenu().getDescuento());
						item3.setPrecio(menu.getMenu().getPostre().getPrecio());
						persist(item3);
						factura.addToItems(item3);
					}
					if (menu.getMenu().getGuarnicion() != null) {
						ItemFactura item4 = newTransientInstance(ItemFactura.class);
						item4.setNombre("--"
								+ menu.getMenu().getGuarnicion().getNombre());
						item4.setCantidad(menu.getCantidad());
						item4.setDescuento(menu.getMenu().getDescuento());
						item4.setPrecio(menu.getMenu().getGuarnicion()
								.getPrecio());
						persist(item4);
						factura.addToItems(item4);
					}
				}
			}
		}
		factura.setTotal(precioTotal);
		persist(factura);
		return factura;
	}
}
