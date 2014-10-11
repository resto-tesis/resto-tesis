package dom.factura;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import dom.menu.Menu;
import dom.pedido.Pedido;
import dom.producto.Producto;
import dom.producto.bebida.Bebida;

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
			for (Bebida bebida : pedido.getBebidas()) {
				final ItemFactura item = newTransientInstance(ItemFactura.class);
				item.setNombre(bebida.getNombre());
				item.setDescuento(0);
				item.setPrecio(bebida.getPrecio());
				persist(item);
				precioTotal += item.getPrecioFinal();
				factura.addToItems(item);
			}
			if (!pedido.getComanda().getProductos().isEmpty()) {
				for (Producto producto : pedido.getComanda().getProductos()) {
					final ItemFactura itemProducto = newTransientInstance(ItemFactura.class);
					itemProducto.setNombre(producto.getNombre());
					itemProducto.setDescuento(0);
					itemProducto.setPrecio(producto.getPrecio());
					persist(itemProducto);
					precioTotal += itemProducto.getPrecioFinal();
					factura.addToItems(itemProducto);
				}
			}
			if (!pedido.getComanda().getMenues().isEmpty()) {
				for (Menu menu : pedido.getComanda().getMenues()) {
					ItemFactura itemMenu = newTransientInstance(ItemFactura.class);
					itemMenu.setNombre(menu.getNombre().toUpperCase());
					itemMenu.setDescuento(menu.getDescuento());
					itemMenu.setPrecio(menu.getPrecioSinDescuento());
					persist(itemMenu);
					precioTotal += itemMenu.getPrecioFinal();
					factura.addToItems(itemMenu);

					ItemFactura item1 = newTransientInstance(ItemFactura.class);
					item1.setNombre("--" + menu.getPlatoPrincipal().getNombre());
					item1.setDescuento(menu.getDescuento());
					item1.setPrecio(menu.getPlatoPrincipal().getPrecio());
					persist(item1);
					factura.addToItems(item1);
					if (menu.getPlatoEntrada() != null) {
						ItemFactura item2 = newTransientInstance(ItemFactura.class);
						item2.setNombre("--"
								+ menu.getPlatoEntrada().getNombre());
						item2.setDescuento(menu.getDescuento());
						item2.setPrecio(menu.getPlatoEntrada().getPrecio());
						persist(item2);
						factura.addToItems(item2);
					}
					if (menu.getPostre() != null) {
						ItemFactura item3 = newTransientInstance(ItemFactura.class);
						item3.setNombre("--" + menu.getPostre().getNombre());
						item3.setDescuento(menu.getDescuento());
						item3.setPrecio(menu.getPostre().getPrecio());
						persist(item3);
						factura.addToItems(item3);
					}
					if (menu.getGuarnicion() != null) {
						ItemFactura item4 = newTransientInstance(ItemFactura.class);
						item4.setNombre("--" + menu.getGuarnicion().getNombre());
						item4.setDescuento(menu.getDescuento());
						item4.setPrecio(menu.getGuarnicion().getPrecio());
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
