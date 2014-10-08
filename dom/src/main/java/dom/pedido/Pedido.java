package dom.pedido;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;

import dom.comanda.Comanda;
import dom.menu.Menu;
import dom.producto.Producto;
import dom.producto.bebida.Bebida;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroPedido", strategy = SequenceStrategy.CONTIGUOUS)
public class Pedido {

	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	// {{ Numero (property)
	private int numero;

	@Hidden(where = Where.ALL_TABLES)
	@Title(prepend = "Pedido ")
	@Named("Número")
	@TypicalLength(3)
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroPedido")
	@Disabled
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumero() {
		return numero;
	}

	public void setNumero(final int numero) {
		this.numero = numero;
	}

	// }}
	// {{ Comanda (property)
	private Comanda comanda;

	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(final Comanda comanda) {
		this.comanda = comanda;
	}

	// }}

	@Named("Productos de la Comanda")
	@Render(Type.EAGERLY)
	@MemberOrder(name = "productosComanda", sequence = "1")
	public List<Producto> getProductosComanda() {
		return getComanda().getProductos();
	}

	@MemberOrder(name = "productosComanda", sequence = "1")
	@Named("Eliminar...")
	public Pedido removeFromComanda(final Producto _producto) {
		getComanda().removeFromProductos(_producto);
		return this;
	}

	public List<Producto> choices0RemoveFromComanda() {
		return getComanda().getProductos();
	}

	@Named("Menues de la Comanda")
	@Render(Type.EAGERLY)
	@MemberOrder(name = "menuesComanda", sequence = "2")
	public List<Menu> getMenuesComanda() {
		return getComanda().getMenues();
	}

	@Named("Eliminar...")
	@MemberOrder(name = "menuesComanda", sequence = "2")
	public Pedido removeFromMenues(final Menu _menu) {
		getComanda().removeFromMenues(_menu);
		return this;
	}

	public List<Menu> choices0RemoveFromMenues() {
		return getComanda().getMenues();
	}

	// {{ Productos (Collection)
	private List<Bebida> bebidas = new ArrayList<Bebida>();

	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "1")
	public List<Bebida> getBebidas() {
		return bebidas;
	}

	public void setBebidas(final List<Bebida> bebidas) {
		this.bebidas = bebidas;
	}

	// }}

	public void addToBebidas(final Bebida _bebida) {
		getBebidas().add(_bebida);
	}

	@MemberOrder(name = "bebidas", sequence = "1")
	@Named("Eliminar...")
	public Pedido removeFromBebidas(final Bebida _bebida) {
		getBebidas().remove(_bebida);
		return this;
	}

	public List<Bebida> choices0RemoveFromBebidas() {
		return getBebidas();
	}

	public Pedido bebidas(final Bebida _bebida1,
			@Optional final Bebida _bebida2, @Optional final Bebida _bebida3,
			@Optional final Bebida _bebida4) {
		pedidoServicio.agregarBebidas(_bebida1, _bebida2, _bebida3, _bebida4,
				this);
		return this;
	}

	public List<Bebida> choices0Bebidas() {
		return pedidoServicio.listarBebidas();
	}

	public List<Bebida> choices1Bebidas() {
		return pedidoServicio.listarBebidas();
	}

	public List<Bebida> choices2Bebidas() {
		return pedidoServicio.listarBebidas();
	}

	public List<Bebida> choices3Bebidas() {
		return pedidoServicio.listarBebidas();
	}

	@Named("Platos Principales")
	public Pedido pedirPlatosPrincipales(final PlatoPrincipal _plato1,
			@Optional final PlatoPrincipal _plato2,
			@Optional final PlatoPrincipal _plato3,
			@Optional final PlatoPrincipal _plato4) {
		pedidoServicio.llenarComanda(_plato1, _plato2, _plato3, _plato4, this);
		return this;
	}

	public List<PlatoPrincipal> choices0PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	public List<PlatoPrincipal> choices1PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	public List<PlatoPrincipal> choices2PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	public List<PlatoPrincipal> choices3PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	@Named("Platos Entrada")
	public Pedido pedirPlatosEntrada(final PlatoEntrada _plato1,
			@Optional final PlatoEntrada _plato2,
			@Optional final PlatoEntrada _plato3,
			@Optional final PlatoEntrada _plato4) {
		pedidoServicio.llenarComanda(_plato1, _plato2, _plato3, _plato4, this);
		return this;
	}

	public List<PlatoEntrada> choices0PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	public List<PlatoEntrada> choices1PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	public List<PlatoEntrada> choices2PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	public List<PlatoEntrada> choices3PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	@Named("Postres")
	public Pedido pedirPostres(final Postre _postre1,
			@Optional final Postre _postre2, @Optional final Postre _postre3,
			@Optional final Postre _postre4) {
		pedidoServicio.llenarComanda(_postre1, _postre2, _postre3, _postre4,
				this);
		return this;
	}

	public List<Postre> choices0PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	public List<Postre> choices1PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	public List<Postre> choices2PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	public List<Postre> choices3PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	@Named("Guarniciones")
	public Pedido pedirGuarniciones(
			@Named("Guarnición 1") final Guarnicion _guarnicion1,
			@Optional @Named("Guarnición 2") final Guarnicion _guarnicion2,
			@Optional @Named("Guarnición 3") final Guarnicion _guarnicion3,
			@Optional @Named("Guarnición 4") final Guarnicion _guarnicion4) {
		pedidoServicio.llenarComanda(_guarnicion1, _guarnicion2, _guarnicion3,
				_guarnicion4, this);
		return this;
	}

	public List<Guarnicion> choices0PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	public List<Guarnicion> choices1PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	public List<Guarnicion> choices2PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	public List<Guarnicion> choices3PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	public Pedido tomarMenues(final Menu _menu) {
		pedidoServicio.agregarMenu(_menu, this);
		return this;
	}

	public List<Menu> choices0TomarMenues() {
		return pedidoServicio.listarMenues();
	}

	// /////////////////////////////////////////////////////--Acciones//Comanda--///////////////////////////////////////////////////////

	@MemberOrder(name = "comanda", sequence = "4")
	public Pedido enviar() {
		cambiarEstado();
		return this;
	}

	public String disableEnviar() {
		return getComanda().getEstado().Enviar();
	}

	@MemberOrder(name = "comanda", sequence = "2")
	public Pedido preparar() {
		cambiarEstado();
		return this;
	}

	public String disablePreparar() {
		return getComanda().getEstado().Preparar();
	}

	@MemberOrder(name = "comanda", sequence = "3")
	public Pedido comandaLista() {
		cambiarEstado();
		return this;
	}

	public String disableComandaLista() {
		return getComanda().getEstado().ComandaLista();
	}

	@Programmatic
	public void cambiarEstado() {
		getComanda().getEstado().cambiarEstado();
	}

	// /////////////////////////////////////////////////////--Acciones//Comanda--///////////////////////////////////////////////////////

	@Inject
	private DomainObjectContainer contenedor;

	@Inject
	private PedidoServicio pedidoServicio;

}
