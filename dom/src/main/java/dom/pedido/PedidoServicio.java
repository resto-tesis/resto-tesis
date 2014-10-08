package dom.pedido;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import dom.comanda.Comanda;
import dom.comanda.ComandaServicio;
import dom.menu.Menu;
import dom.producto.Producto;
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
	public void agregarMenu(final Menu _menu, Pedido _pedido) {
		_pedido.getComanda().addToMenues(_menu);
	}

	@Programmatic
	public void agregarBebidas(final Bebida _bebida1, final Bebida _bebida2,
			final Bebida _bebida3, final Bebida _bebida4, Pedido _pedido) {
		_pedido.addToBebidas(_bebida1);
		if (_bebida2 != null)
			_pedido.addToBebidas(_bebida2);
		if (_bebida3 != null)
			_pedido.addToBebidas(_bebida3);
		if (_bebida4 != null)
			_pedido.addToBebidas(_bebida4);

	}

	@Programmatic
	public void llenarComanda(final Producto _producto1,
			final Producto _producto2, final Producto _producto3,
			final Producto _producto4, Pedido _pedido) {
		_pedido.getComanda().addToProductos(_producto1);
		if (_producto2 != null)
			_pedido.getComanda().addToProductos(_producto2);
		if (_producto3 != null)
			_pedido.getComanda().addToProductos(_producto3);
		if (_producto4 != null)
			_pedido.getComanda().addToProductos(_producto4);
	}

	@Inject
	private ComandaServicio comandaServicio;

}
