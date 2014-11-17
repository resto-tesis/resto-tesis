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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
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
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;

import dom.comanda.Comanda;
import dom.menu.Menu;
import dom.mesa.Mesa;
import dom.objetosValor.ValueMenu;
import dom.objetosValor.ValueProductoElaborado;
import dom.objetosValor.ValueProductoNoElaborado;
import dom.producto.bebida.Bebida;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;
/**
 * 
 * @author RestoTesis
 * @since 10/09/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroPedido", strategy = SequenceStrategy.CONTIGUOUS)
public class Pedido {

	public String iconName() {
		if (pedidoServicio.soloBebidas(this)) {
			if (getBebidas().isEmpty())
				return "PedidoVacio";
			return "Pedido";
		}
		return getComanda().iconName();
	}

	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	public String title() {
		if (pedidoServicio.soloBebidas(this) && getBebidas().isEmpty())
			return "Pedido " + getNumero() + " (Vacío)";
		return "Pedido " + getNumero();
	}

	// {{ Numero (property)
	private int numero;

	@Hidden(where = Where.ALL_TABLES)
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
	public List<ValueProductoElaborado> getProductosComanda() {
		return getComanda().getProductos();
	}

	@MemberOrder(name = "productosComanda", sequence = "10")
	@Named("Eliminar...")
	public Pedido removeFromComanda(
			@Named("Producto") final ValueProductoElaborado _producto) {
		getComanda().removeFromProductos(_producto);
		return this;
	}

	public String disableRemoveFromComanda(
			final ValueProductoElaborado _producto) {
		if (getComanda().getProductos().isEmpty())
			return "No se pidieron productos";
		return getComanda().getEstado().validarModificacion();
	}

	public List<ValueProductoElaborado> choices0RemoveFromComanda() {
		return getComanda().getProductos();
	}

	@Named("Menues de la Comanda")
	@Render(Type.EAGERLY)
	@MemberOrder(name = "menuesComanda", sequence = "21")
	public List<ValueMenu> getMenuesComanda() {
		return getComanda().getMenues();
	}

	@Named("Eliminar...")
	@MemberOrder(name = "menuesComanda", sequence = "20")
	public Pedido removeFromMenues(@Named("Menu") final ValueMenu _menu) {
		getComanda().removeFromMenues(_menu);
		return this;
	}

	public String disableRemoveFromMenues(final ValueMenu _menu) {
		if (getComanda().getMenues().isEmpty())
			return "No se pidieron menues";
		return getComanda().getEstado().validarModificacion();
	}

	public List<ValueMenu> choices0RemoveFromMenues() {
		return getComanda().getMenues();
	}

	// {{ Productos (Collection)
	@Persistent(dependentElement = "true")
	@Join(deleteAction = ForeignKeyAction.CASCADE)
	private List<ValueProductoNoElaborado> bebidas = new ArrayList<ValueProductoNoElaborado>();

	@Render(Type.EAGERLY)
	@MemberOrder(name = "bebidas", sequence = "13")
	@Named("Bebidas del Pedido")
	public List<ValueProductoNoElaborado> getBebidas() {
		return bebidas;
	}

	public void setBebidas(final List<ValueProductoNoElaborado> bebidas) {
		this.bebidas = bebidas;
	}

	// }}

	public void addToBebidas(final ValueProductoNoElaborado _bebida) {
		getBebidas().add(_bebida);
	}

	@MemberOrder(name = "bebidas", sequence = "2")
	@Named("Eliminar...")
	public Pedido removeFromBebidas(
			@Named("Bebida") final ValueProductoNoElaborado _bebida) {
		getBebidas().remove(_bebida);
		return this;
	}

	public List<ValueProductoNoElaborado> choices0RemoveFromBebidas() {
		return getBebidas();
	}

	public String disableRemoveFromBebidas(
			final ValueProductoNoElaborado _bebida) {
		return getBebidas().isEmpty() ? "No se pidieron bebidas" : null;
	}

	@MemberOrder(name = "bebidas", sequence = "1")
	@Named("Bebidas")
	public Pedido pedirBebidas(final Bebida _bebida1,
			@Optional @Named("Cantidad") final Integer _cantidad1,
			@Optional @Named("Nota") final String _nota1,
			@Optional final Bebida _bebida2,
			@Optional @Named("Cantidad") final Integer _cantidad2,
			@Optional @Named("Nota") final String _nota2,
			@Optional final Bebida _bebida3,
			@Optional @Named("Cantidad") final Integer _cantidad3,
			@Optional @Named("Nota") final String _nota3,
			@Optional final Bebida _bebida4,
			@Optional @Named("Cantidad") final Integer _cantidad4,
			@Optional @Named("Nota") final String _nota4) {
		pedidoServicio.agregarBebidas(_bebida1, _cantidad1, _nota1, _bebida2,
				_cantidad2, _nota2, _bebida3, _cantidad3, _nota3, _bebida4,
				_cantidad4, _nota4, this);
		return this;
	}

	public List<Bebida> choices0PedirBebidas() {
		return pedidoServicio.listarBebidas();
	}

	public List<Bebida> choices3PedirBebidas() {
		return pedidoServicio.listarBebidas();
	}

	public List<Bebida> choices6PedirBebidas() {
		return pedidoServicio.listarBebidas();
	}

	public List<Bebida> choices9PedirBebidas() {
		return pedidoServicio.listarBebidas();
	}

	public String disablePedirBebidas(final Bebida _bebida1,
			final Integer _cantidad1, final String _nota1,
			final Bebida _bebida2, final Integer _cantidad2,
			final String _nota2, final Bebida _bebida3,
			final Integer _cantidad3, final String _nota3,
			final Bebida _bebida4, final Integer _cantidad4, final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	@MemberOrder(name = "productosComanda", sequence = "2")
	@Named("Platos Principales")
	public Pedido pedirPlatosPrincipales(final PlatoPrincipal _plato1,
			@Optional @Named("Cantidad") final Integer _cantidad1,
			@Optional @Named("Nota") final String _nota1,
			@Optional final PlatoPrincipal _plato2,
			@Optional @Named("Cantidad") final Integer _cantidad2,
			@Optional @Named("Nota") final String _nota2,
			@Optional final PlatoPrincipal _plato3,
			@Optional @Named("Cantidad") final Integer _cantidad3,
			@Optional @Named("Nota") final String _nota3,
			@Optional final PlatoPrincipal _plato4,
			@Optional @Named("Cantidad") final Integer _cantidad4,
			@Optional @Named("Nota") final String _nota4) {
		pedidoServicio.llenarComanda(_plato1, _cantidad1, _nota1, _plato2,
				_cantidad2, _nota2, _plato3, _cantidad3, _nota3, _plato4,
				_cantidad4, _nota4, this);
		return this;
	}

	public List<PlatoPrincipal> choices0PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	public List<PlatoPrincipal> choices3PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	public List<PlatoPrincipal> choices6PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	public List<PlatoPrincipal> choices9PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	public String disablePedirPlatosPrincipales(final PlatoPrincipal _plato1,
			final Integer _cantidad1, final String _nota1,
			final PlatoPrincipal _plato2, final Integer _cantidad2,
			final String _nota2, final PlatoPrincipal _plato3,
			final Integer _cantidad3, final String _nota3,
			final PlatoPrincipal _plato4, final Integer _cantidad4,
			final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	@MemberOrder(name = "productosComanda", sequence = "1")
	@Named("Platos Entrada")
	public Pedido pedirPlatosEntrada(final PlatoEntrada _plato1,
			@Optional @Named("Cantidad") final Integer _cantidad1,
			@Optional @Named("Nota") final String _nota1,
			@Optional final PlatoEntrada _plato2,
			@Optional @Named("Cantidad") final Integer _cantidad2,
			@Optional @Named("Nota") final String _nota2,
			@Optional final PlatoEntrada _plato3,
			@Optional @Named("Cantidad") final Integer _cantidad3,
			@Optional @Named("Nota") final String _nota3,
			@Optional final PlatoEntrada _plato4,
			@Optional @Named("Cantidad") final Integer _cantidad4,
			@Optional @Named("Nota") final String _nota4) {
		pedidoServicio.llenarComanda(_plato1, _cantidad1, _nota1, _plato2,
				_cantidad2, _nota2, _plato3, _cantidad3, _nota3, _plato4,
				_cantidad4, _nota4, this);
		return this;
	}

	public List<PlatoEntrada> choices0PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	public List<PlatoEntrada> choices3PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	public List<PlatoEntrada> choices6PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	public List<PlatoEntrada> choices9PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	public String disablePedirPlatosEntrada(final PlatoEntrada _plato1,
			final Integer _cantidad1, final String _nota1,
			final PlatoEntrada _plato2, final Integer _cantidad2,
			final String _nota2, final PlatoEntrada _plato3,
			final Integer _cantidad3, final String _nota3,
			final PlatoEntrada _plato4, final Integer _cantidad4,
			final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	@MemberOrder(name = "productosComanda", sequence = "4")
	@Named("Postres")
	public Pedido pedirPostres(final Postre _postre1,
			@Optional @Named("Cantidad") final Integer _cantidad1,
			@Optional @Named("Nota") final String _nota1,
			@Optional final Postre _postre2,
			@Optional @Named("Cantidad") final Integer _cantidad2,
			@Optional @Named("Nota") final String _nota2,
			@Optional final Postre _postre3,
			@Optional @Named("Cantidad") final Integer _cantidad3,
			@Optional @Named("Nota") final String _nota3,
			@Optional final Postre _postre4,
			@Optional @Named("Cantidad") final Integer _cantidad4,
			@Optional @Named("Nota") final String _nota4) {
		pedidoServicio.llenarComanda(_postre1, _cantidad1, _nota1, _postre2,
				_cantidad2, _nota2, _postre3, _cantidad3, _nota3, _postre4,
				_cantidad4, _nota4, this);
		return this;
	}

	public List<Postre> choices0PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	public List<Postre> choices3PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	public List<Postre> choices6PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	public List<Postre> choices9PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	public String disablePedirPostres(final Postre _postre1,
			final Integer _cantidad1, final String _nota1,
			final Postre _postre2, final Integer _cantidad2,
			final String _nota2, final Postre _postre3,
			final Integer _cantidad3, final String _nota3,
			final Postre _postre4, final Integer _cantidad4, final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	@MemberOrder(name = "productosComanda", sequence = "3")
	@Named("Guarniciones")
	public Pedido pedirGuarniciones(
			@Named("Guarnición 1") final Guarnicion _guarnicion1,
			@Optional @Named("Cantidad") final Integer _cantidad1,
			@Optional @Named("Nota") final String _nota1,
			@Optional @Named("Guarnición 2") final Guarnicion _guarnicion2,
			@Optional @Named("Cantidad") final Integer _cantidad2,
			@Optional @Named("Nota") final String _nota2,
			@Optional @Named("Guarnición 3") final Guarnicion _guarnicion3,
			@Optional @Named("Cantidad") final Integer _cantidad3,
			@Optional @Named("Nota") final String _nota3,
			@Optional @Named("Guarnición 4") final Guarnicion _guarnicion4,
			@Optional @Named("Cantidad") final Integer _cantidad4,
			@Optional @Named("Nota") final String _nota4) {
		pedidoServicio.llenarComanda(_guarnicion1, _cantidad1, _nota1,
				_guarnicion2, _cantidad2, _nota2, _guarnicion3, _cantidad3,
				_nota3, _guarnicion4, _cantidad4, _nota4, this);
		return this;
	}

	public List<Guarnicion> choices0PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	public List<Guarnicion> choices3PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	public List<Guarnicion> choices6PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	public List<Guarnicion> choices9PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	public String disablePedirGuarniciones(final Guarnicion _guarnicion1,
			final Integer _cantidad1, final String _nota1,
			final Guarnicion _guarnicion2, final Integer _cantidad2,
			final String _nota2, final Guarnicion _guarnicion3,
			final Integer _cantidad3, final String _nota3,
			final Guarnicion _guarnicion4, final Integer _cantidad4,
			final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	@MemberOrder(name = "menuesComanda", sequence = "1")
	public Pedido tomarMenues(final Menu _menu1,
			@Optional @Named("Cantidad") final Integer _cantidad1,
			@Optional @Named("Nota") final String _nota1,
			@Optional final Menu _menu2,
			@Optional @Named("Cantidad") final Integer _cantidad2,
			@Optional @Named("Nota") final String _nota2) {
		pedidoServicio.agregarMenu(_menu1, _cantidad1, _nota1, _menu2,
				_cantidad2, _nota2, this);
		return this;
	}

	public List<Menu> choices0TomarMenues() {
		return pedidoServicio.listarMenues();
	}

	public List<Menu> choices3TomarMenues() {
		return pedidoServicio.listarMenues();
	}

	public String disableTomarMenues(final Menu _menu1,
			final Integer _cantidad1, final String _nota1, final Menu _menu2,
			final Integer _cantidad2, final String _nota2) {
		return getComanda().getEstado().validarModificacion();
	}

	// {{ Mesa (property)
	private Mesa mesa;

	@Hidden
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(final Mesa mesa) {
		this.mesa = mesa;
	}

	// }}

	// /////////////////////////////////////////////////////--Acciones//Comanda--///////////////////////////////////////////////////////

	@MemberOrder(name = "comanda", sequence = "4")
	public Pedido enviar() {
		cambiarEstado();
		return this;
	}

	public String disableEnviar() {
		return getComanda().getEstado().Enviar();
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

	public Mesa volver() {
		return pedidoServicio.volver(this);
	}

}
