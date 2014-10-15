package dom.pedido;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import dom.comanda.ComandaServicio;
import dom.menu.Menu;
import dom.objetosValor.ValueMenu;
import dom.objetosValor.ValueProductoElaborado;
import dom.objetosValor.ValueProductoNoElaborado;
import dom.producto.ProductoElaborado;
import dom.producto.bebida.Bebida;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;

@DomainService
public class PedidoServicio extends AbstractFactoryAndRepository {

	public PedidoServicio() {
		// TODO Auto-generated constructor stub
	}

	@Programmatic
	public Pedido crearPedido() {
		final Pedido pedido = newTransientInstance(Pedido.class);
		pedido.setComanda(comandaServicio.crearComanda());
		persist(pedido);
		return pedido;
	}

	@Programmatic
	public List<Bebida> listarBebidas() {
		return allInstances(Bebida.class);
	}

	@Programmatic
	public List<Postre> listarPostres() {
		return allInstances(Postre.class);
	}

	@Programmatic
	public List<PlatoPrincipal> listarPlatosPrincipales() {
		return allInstances(PlatoPrincipal.class);
	}

	@Programmatic
	public List<PlatoEntrada> listarPlatosEntrada() {
		return allInstances(PlatoEntrada.class);
	}

	@Programmatic
	public List<Guarnicion> listarGuarniciones() {
		return allInstances(Guarnicion.class);
	}

	@Programmatic
	public List<Menu> listarMenues() {
		return allInstances(Menu.class);
	}

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
			producto2.setProducto(_producto1);
			producto2.setCantidad(_cantidad2 == null ? 1 : _cantidad2);
			producto2.setNota(_nota1);
			persist(producto2);
			_pedido.getComanda().addToProductos(producto2);
		}
		if (_producto3 != null) {
			final ValueProductoElaborado producto3 = newTransientInstance(ValueProductoElaborado.class);
			producto3.setProducto(_producto1);
			producto3.setCantidad(_cantidad3 == null ? 1 : _cantidad3);
			producto3.setNota(_nota1);
			persist(producto3);
			_pedido.getComanda().addToProductos(producto3);
		}
		if (_producto4 != null) {
			final ValueProductoElaborado producto4 = newTransientInstance(ValueProductoElaborado.class);
			producto4.setProducto(_producto1);
			producto4.setCantidad(_cantidad4 == null ? 1 : _cantidad4);
			producto4.setNota(_nota1);
			persist(producto4);
			_pedido.getComanda().addToProductos(producto4);
		}
	}

	@Inject
	private ComandaServicio comandaServicio;

}
