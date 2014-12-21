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
import dom.objetosValor.ValueOferta;
import dom.objetosValor.ValueProductoElaborado;
import dom.objetosValor.ValueProductoNoElaborado;
import dom.oferta.Oferta;
import dom.producto.bebida.Bebida;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;
/**
 * Entidad Comanda la cual representa cada producto que un Cliente desee consumir
 * @author RestoTesis
 * @since 10/09/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroPedido", strategy = SequenceStrategy.CONTIGUOUS)
public class Pedido {

	/**
	 * Asigna el texto al icono del nombre del pedido, segun este compuesto solo de bebidas o no.-
	 * @return String
	 */
	public String iconName() {
		if (pedidoServicio.soloBebidas(this)) {
			if (getBebidas().isEmpty())
				return "PedidoVacio";
			return "Pedido";
		}
		return getComanda().iconName();
	}

	/**
	 * Constructor de la clase
	 */
	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtine el titulo y numero del pedido, si este esta compuesto solo de bebidas
	 * @return String
	 */
	public String title() {
		if (pedidoServicio.soloBebidas(this) && getBebidas().isEmpty())
			return "Pedido " + getNumero() + " (Vacio)";
		return "Pedido " + getNumero();
	}

	// {{ Numero (property)
	private int numero;

	/**
	 * Obtiene un numero para el Pedido
	 * @return numero int
	 */
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

	/**
	 * Setea el numero del Pedido
	 * @param numero int
	 */
	public void setNumero(final int numero) {
		this.numero = numero;
	}

	// {{ Comanda (property)
	private Comanda comanda;

	/**
	 * Obtiene una comanda para el pedido
	 * @return comanda Comanda
	 */
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public Comanda getComanda() {
		return comanda;
	}

	/**
	 * Setea una Comanda para el Pedido
	 * @param comanda Comanda
	 */
	public void setComanda(final Comanda comanda) {
		this.comanda = comanda;
	}

	/**
	 * Obtiene una lista de productos elaborados para agreagar
	 * @return comanda Comanda
	 */
	@Named("Productos")
	@Render(Type.EAGERLY)
	@MemberOrder(name = "productosComanda", sequence = "1")
	public List<ValueProductoElaborado> getProductosComanda() {
		return getComanda().getProductos();
	}

	/**
	 * Elimina un producto elaborado de la comanda
	 * @param _producto ValueProductoElaborado
	 * @return this
	 */
	@MemberOrder(name = "productosComanda", sequence = "10")
	@Named("Eliminar...")
	public Pedido removeFromComanda(
			@Named("Producto") final ValueProductoElaborado _producto) {
		getComanda().removeFromProductos(_producto);
		return this;
	}

	/**
	 * Permite si se pidio algun producto elaborado y su estado,
	 * @param _producto ValueProductoElaborado
	 * @return String
	 */
	public String disableRemoveFromComanda(
			final ValueProductoElaborado _producto) {
		if (getComanda().getProductos().isEmpty())
			return "No se pidieron productos";
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Obtiene una lista de productos elaborados
	 * @return List<ProductosElaborados>
	 */
	public List<ValueProductoElaborado> choices0RemoveFromComanda() {
		return getComanda().getProductos();
	}

	/**
	 * Obtiene una lista de menues
	 * @return List<Menu>
	 */
	@Named("Menues")
	@Render(Type.EAGERLY)
	@MemberOrder(name = "menuesComanda", sequence = "21")
	public List<ValueMenu> getMenuesComanda() {
		return getComanda().getMenues();
	}

	/**
	 * Permite remover un Menu de la Comanda
	 * @param _menu ValueMenu
	 * @return this
	 */
	@Named("Eliminar...")
	@MemberOrder(name = "menuesComanda", sequence = "20")
	public Pedido removeFromMenues(@Named("Menu") final ValueMenu _menu) {
		getComanda().removeFromMenues(_menu);
		return this;
	}

	/**
	 * Verifica si se pidieron menues dentro de la comanda 
	 * @param _menu ValueMenu
	 * @return String
	 */
	public String disableRemoveFromMenues(final ValueMenu _menu) {
		if (getComanda().getMenues().isEmpty())
			return "No se pidieron menues";
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Obtiene una lista de menues
	 * @return List<Menu>
	 */
	public List<ValueMenu> choices0RemoveFromMenues() {
		return getComanda().getMenues();
	}

	/**
	 * Obtiene una lista de ofertas para agregar a la comanda
	 * @return List<Oferta>
	 */
	@Named("Ofertas")
	@Render(Type.EAGERLY)
	@MemberOrder(name = "ofertasComanda", sequence = "30")
	public List<ValueOferta> getOfertasComanda() {
		return getComanda().getOfertas();
	}

	/**
	 * Segun el pedido, elimina una oferta de la comanda
	 * @param _oferta ValueOferta
	 * @return this
	 */
	@Named("Eliminar...")
	@MemberOrder(name = "ofertasComanda", sequence = "31")
	public Pedido removeFromOfertas(@Named("Oferta") final ValueOferta _oferta) {
		getComanda().removeFromOfertas(_oferta);
		return this;
	}

	/**
	 * Verifica si se solicitaron Ofertas
	 * @param _oferta ValueOferta
	 * @return String
	 */
	public String disableRemoveFromOfertas(final ValueOferta _oferta) {
		if (getComanda().getOfertas().isEmpty())
			return "No se pidieron ofertas";
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Obtiene una lista de Ofertas
	 * @return List<Oferta>
	 */
	public List<ValueOferta> choices0RemoveFromOfertas() {
		return getComanda().getOfertas();
	}

	@Persistent(dependentElement = "true")
	@Join(deleteAction = ForeignKeyAction.CASCADE)
	private List<ValueProductoNoElaborado> bebidas = new ArrayList<ValueProductoNoElaborado>();

	/**
	 * Obtiene las Bebidas de un pedido
	 * @return List<Bebidas>
	 */
	@Render(Type.EAGERLY)
	@MemberOrder(name = "bebidas", sequence = "13")
	@Named("Bebidas del Pedido")
	public List<ValueProductoNoElaborado> getBebidas() {
		return bebidas;
	}

	/**
	 * Setea las Bedidas de un Pedido
	 * @param bebidas List<Bebidas>
	 */
	public void setBebidas(final List<ValueProductoNoElaborado> bebidas) {
		this.bebidas = bebidas;
	}

	/**
	 * Agrega una Bebida al pedido
	 * @param _bebida ValueProductoNoElaborado
	 */
	public void addToBebidas(final ValueProductoNoElaborado _bebida) {
		getBebidas().add(_bebida);
	}

	/**
	 * Elimina una Bebida del pedido
	 * @param _bebida ValueProductoNoElaborado
	 * @return this
	 */
	@MemberOrder(name = "bebidas", sequence = "2")
	@Named("Eliminar...")
	public Pedido removeFromBebidas(
			@Named("Bebida") final ValueProductoNoElaborado _bebida) {
		getBebidas().remove(_bebida);
		return this;
	}

	/**
	 * Obtiene  una lista de bebidas
	 * @return List<Bebidas>
	 */
	public List<ValueProductoNoElaborado> choices0RemoveFromBebidas() {
		return getBebidas();
	}

	/**
	 * Verifica si se pidieron Bebidas
	 * @param _bebida ValueProductoNoElaborado
	 * @return String
	 */
	public String disableRemoveFromBebidas(
			final ValueProductoNoElaborado _bebida) {
		return getBebidas().isEmpty() ? "No se pidieron bebidas" : null;
	}

	/**
	 * Agrega las Bebias al Pedido
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
	 * @return this Pedido
	 */
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

	/**
	 * Obtiene  una lista de bebidas
	 * @return List<Bebidas>
	 */
	public List<Bebida> choices0PedirBebidas() {
		return pedidoServicio.listarBebidas();
	}

	/**
	 * Obtiene  una lista de bebidas
	 * @return List<Bebidas>
	 */
	public List<Bebida> choices3PedirBebidas() {
		return pedidoServicio.listarBebidas();
	}

	/**
	 * Obtiene  una lista de bebidas
	 * @return List<Bebidas>
	 */
	public List<Bebida> choices6PedirBebidas() {
		return pedidoServicio.listarBebidas();
	}

	/**
	 * Obtiene  una lista de bebidas
	 * @return List<Bebidas>
	 */
	public List<Bebida> choices9PedirBebidas() {
		return pedidoServicio.listarBebidas();
	}

	/**
	 * Deshabita el metodo pedirBebidas()
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
	 * @see getComanda().getEstado().validarModificacion()
	 * @return getComanda().getEstado().validarModificacion() String 
	 */
	public String disablePedirBebidas(final Bebida _bebida1,
			final Integer _cantidad1, final String _nota1,
			final Bebida _bebida2, final Integer _cantidad2,
			final String _nota2, final Bebida _bebida3,
			final Integer _cantidad3, final String _nota3,
			final Bebida _bebida4, final Integer _cantidad4, final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Agrega un plato principal al Pedido
	 * @param _plato1 PlatoPrincipal
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _plato2 PlatoPrincipal
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _plato3 PlatoPrincipal
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _plato4  PlatoPrincipal
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @return this Pedido
	 */
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

	/**
	 * Obtiene  una lista de platos principales
	 * @return List<PlatoPrincipal>
	 */
	public List<PlatoPrincipal> choices0PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	/**
	 * Obtiene  una lista de platos principales
	 * @return List<PlatoPrincipal>
	 */
	public List<PlatoPrincipal> choices3PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}
	/**
	 * Obtiene  una lista de platos principales
	 * @return List<PlatoPrincipal>
	 */
	public List<PlatoPrincipal> choices6PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	/**
	 * Obtiene  una lista de platos principales
	 * @return List<PlatoPrincipal>
	 */
	public List<PlatoPrincipal> choices9PedirPlatosPrincipales() {
		return pedidoServicio.listarPlatosPrincipales();
	}

	/**
	 * Deshabilita el metodo pedirPlatosPrincipales()
	 * @param _plato1 PlatoPrincipal
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _plato2 PlatoPrincipal
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _plato3 PlatoPrincipal
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _plato4  PlatoPrincipal
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @see getComanda().getEstado().validarModificacion()
	 * @return getComanda().getEstado().validarModificacion() String
	 */
	public String disablePedirPlatosPrincipales(final PlatoPrincipal _plato1,
			final Integer _cantidad1, final String _nota1,
			final PlatoPrincipal _plato2, final Integer _cantidad2,
			final String _nota2, final PlatoPrincipal _plato3,
			final Integer _cantidad3, final String _nota3,
			final PlatoPrincipal _plato4, final Integer _cantidad4,
			final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Agrega un plato de entrada al Pedido
	 * @param _plato1 PlatoEntrada
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _plato2 PlatoEntrada
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _plato3 PlatoEntrada
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _plato4  PlatoEntrada
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @return this Pedido
	 */
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

	/**
	 * Obtiene  una lista de platos de entrada
	 * @return List<PlatoEntrada>
	 */
	public List<PlatoEntrada> choices0PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	/**
	 * Obtiene  una lista de platos de entrada
	 * @return List<PlatoEntrada>
	 */
	public List<PlatoEntrada> choices3PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	/**
	 * Obtiene  una lista de platos de entrada
	 * @return List<PlatoEntrada>
	 */
	public List<PlatoEntrada> choices6PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	/**
	 * Obtiene  una lista de platos de entrada
	 * @return List<PlatoEntrada>
	 */
	public List<PlatoEntrada> choices9PedirPlatosEntrada() {
		return pedidoServicio.listarPlatosEntrada();
	}

	/**
	 * Deshabilita el metodo pedirPlatosEntrada()
	 * @param _plato1 PlatoEntrada
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _plato2 PlatoEntrada
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _plato3 PlatoEntrada
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _plato4  PlatoEntrada
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @see getComanda().getEstado().validarModificacion()
	 * @return getComanda().getEstado().validarModificacion() String
	 */
	public String disablePedirPlatosEntrada(final PlatoEntrada _plato1,
			final Integer _cantidad1, final String _nota1,
			final PlatoEntrada _plato2, final Integer _cantidad2,
			final String _nota2, final PlatoEntrada _plato3,
			final Integer _cantidad3, final String _nota3,
			final PlatoEntrada _plato4, final Integer _cantidad4,
			final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Agrega un Postre al Pedido
	 * @param _postre1 Postre
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _postre2 Postre
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _postre3 Postre
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _postre4 Postre
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @return this Pedido
	 */
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

	/**
	 * Obtiene  una lista de Postres
	 * @return List<Postre>
	 */
	public List<Postre> choices0PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	/**
	 * Obtiene  una lista de Postres
	 * @return List<Postre>
	 */
	public List<Postre> choices3PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	/**
	 * Obtiene  una lista de Postres
	 * @return List<Postre>
	 */
	public List<Postre> choices6PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	/**
	 * Obtiene  una lista de Postres
	 * @return List<Postre>
	 */
	public List<Postre> choices9PedirPostres() {
		return pedidoServicio.listarPostres();
	}

	/**
	 * Deshabilita el metodo pedirPostres()
	 * @param _postre1 Postre
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _postre2 Postre
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _postre3 Postre
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _postre4 Postre
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @see getComanda().getEstado().validarModificacion()
	 * @return getComanda().getEstado().validarModificacion() String
	 */
	public String disablePedirPostres(final Postre _postre1,
			final Integer _cantidad1, final String _nota1,
			final Postre _postre2, final Integer _cantidad2,
			final String _nota2, final Postre _postre3,
			final Integer _cantidad3, final String _nota3,
			final Postre _postre4, final Integer _cantidad4, final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Agrega una Guarnicion al Pedido
	 * @param _guarnicion1 Guarnicion
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _guarnicion2 Guarnicion
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @param _guarnicion3 Guarnicion
	 * @param _cantidad3 Integer
	 * @param _nota3 String
	 * @param _guarnicion4 Guarnicion
	 * @param _cantidad4 Integer
	 * @param _nota4 String
	 * @return this Pedido
	 */
	@MemberOrder(name = "productosComanda", sequence = "3")
	@Named("Guarniciones")
	public Pedido pedirGuarniciones(
			@Named("Guarnicion 1") final Guarnicion _guarnicion1,
			@Optional @Named("Cantidad") final Integer _cantidad1,
			@Optional @Named("Nota") final String _nota1,
			@Optional @Named("Guarnicion 2") final Guarnicion _guarnicion2,
			@Optional @Named("Cantidad") final Integer _cantidad2,
			@Optional @Named("Nota") final String _nota2,
			@Optional @Named("Guarnicion 3") final Guarnicion _guarnicion3,
			@Optional @Named("Cantidad") final Integer _cantidad3,
			@Optional @Named("Nota") final String _nota3,
			@Optional @Named("Guarnicion 4") final Guarnicion _guarnicion4,
			@Optional @Named("Cantidad") final Integer _cantidad4,
			@Optional @Named("Nota") final String _nota4) {
		pedidoServicio.llenarComanda(_guarnicion1, _cantidad1, _nota1,
				_guarnicion2, _cantidad2, _nota2, _guarnicion3, _cantidad3,
				_nota3, _guarnicion4, _cantidad4, _nota4, this);
		return this;
	}

	/**
	 * Obtiene  una lista de Guarniciones
	 * @return List<Guarnicion>
	 */
	public List<Guarnicion> choices0PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	/**
	 * Obtiene  una lista de Guarniciones
	 * @return List<Guarnicion>
	 */
	public List<Guarnicion> choices3PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	/**
	 * Obtiene  una lista de Guarniciones
	 * @return List<Guarnicion>
	 */
	public List<Guarnicion> choices6PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	/**
	 * Obtiene  una lista de Guarniciones
	 * @return List<Guarnicion>
	 */
	public List<Guarnicion> choices9PedirGuarniciones() {
		return pedidoServicio.listarGuarniciones();
	}

	/**
	 * Deshabilita el metodo pedirGuarniciones
	 * @param _guarnicion1
	 * @param _cantidad1
	 * @param _nota1
	 * @param _guarnicion2
	 * @param _cantidad2
	 * @param _nota2
	 * @param _guarnicion3
	 * @param _cantidad3
	 * @param _nota3
	 * @param _guarnicion4
	 * @param _cantidad4
	 * @param _nota4
	 * @see getComanda().getEstado().validarModificacion()
	 * @return getComanda().getEstado().validarModificacion() String
	 */
	public String disablePedirGuarniciones(final Guarnicion _guarnicion1,
			final Integer _cantidad1, final String _nota1,
			final Guarnicion _guarnicion2, final Integer _cantidad2,
			final String _nota2, final Guarnicion _guarnicion3,
			final Integer _cantidad3, final String _nota3,
			final Guarnicion _guarnicion4, final Integer _cantidad4,
			final String _nota4) {
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Agrega un menu al Pedido
	 * @param _menu1 Menu
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _menu2 Menu
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @return this Pedido
	 */
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

	/**
	 * Obtiene  una lista de menues
	 * @return List<Menu>
	 */
	public List<Menu> choices0TomarMenues() {
		return pedidoServicio.listarMenues();
	}

	/**
	 * Obtiene  una lista de menues
	 * @return List<Menu>
	 */
	public List<Menu> choices3TomarMenues() {
		return pedidoServicio.listarMenues();
	}

	/**
	 * Deshabilita el metodo tomarMenues()
	 * @param _menu1
	 * @param _cantidad1
	 * @param _nota1
	 * @param _menu2
	 * @param _cantidad2
	 * @param _nota2
	 * @see getComanda().getEstado().validarModificacion()
	 * @return getComanda().getEstado().validarModificacion() String
	 */
	public String disableTomarMenues(final Menu _menu1,
			final Integer _cantidad1, final String _nota1, final Menu _menu2,
			final Integer _cantidad2, final String _nota2) {
		return getComanda().getEstado().validarModificacion();
	}

	/**
	 * Agrega una Oferta al Pedido
	 * @param _oferta1 Oferta
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _oferta2 Oferta
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @return this Pedido
	 */
	@MemberOrder(name = "ofertasComanda", sequence = "1")
	public Pedido pedirOfertas(final Oferta _oferta1,
			@Optional @Named("Cantidad") final Integer _cantidad1,
			@Optional @Named("Nota") final String _nota1,
			@Optional final Oferta _oferta2,
			@Optional @Named("Cantidad") final Integer _cantidad2,
			@Optional @Named("Nota") final String _nota2) {
		pedidoServicio.agregarOferta(_oferta1, _cantidad1, _nota1, _oferta2,
				_cantidad2, _nota2, this);
		return this;
	}

	/**
	 * Obtiene  una lista de Ofertas
	 * @return List<Oferta>
	 */
	public List<Oferta> choices0PedirOfertas() {
		return pedidoServicio.listarOfertas();
	}

	/**
	 * Obtiene  una lista de Ofertas
	 * @return List<Oferta>
	 */
	public List<Oferta> choices3PedirOfertas() {
		return pedidoServicio.listarOfertas();
	}

	/**
	 * Deshabilita el metodo pedirOfertas()
	  * @param _oferta1 Oferta
	 * @param _cantidad1 Integer
	 * @param _nota1 String
	 * @param _oferta2 Oferta
	 * @param _cantidad2 Integer
	 * @param _nota2 String
	 * @see getComanda().getEstado().validarModificacion();
	 * @return getComanda().getEstado().validarModificacion() String
	 */
	public String disablePedirOfertas(final Oferta _oferta1,
			final Integer _cantidad1, final String _nota1,
			final Oferta _oferta2, final Integer _cantidad2, final String _nota2) {
		return getComanda().getEstado().validarModificacion();
	}

	// {{ Mesa (property)
	private Mesa mesa;

	/**
	 * Obtiene una Mesa para el Pedido
	 * @return mesa Mesa
	 */
	@Hidden
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Mesa getMesa() {
		return mesa;
	}

	/**
	 * Setea una Mesa al Pedido
	 * @param mesa Mesa
	 */
	public void setMesa(final Mesa mesa) {
		this.mesa = mesa;
	}

	/**
	 * 
	 * @return this Pedido
	 */
	@MemberOrder(name = "comanda", sequence = "4")
	public Pedido enviar() {
		cambiarEstado();
		return this;
	}

	/**
	 * Desahilita el metodo 
	 * @see getComanda().getEstado().Enviar()
	 * @return getComanda().getEstado().Enviar() String
	 */
	public String disableEnviar() {
		return getComanda().getEstado().Enviar();
	}

	/**
	 * Cambia el estado de la comanda
	 */
	@Programmatic
	public void cambiarEstado() {
		getComanda().getEstado().cambiarEstado();
	}

	/**
	 * Inyeccion de contenedor
	 */
	@Inject
	private DomainObjectContainer contenedor;

	/**
	 * Inyeccion del servicio de Pedido
	 */
	@Inject
	private PedidoServicio pedidoServicio;

	public Mesa volver() {
		return pedidoServicio.volver(this);
	}

}
