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

package dom.mesa;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import com.google.common.base.Predicate;

import dom.factura.Factura;
import dom.factura.FacturaServicio;
import dom.mozo.Mozo;
import dom.mozo.MozoServicio;
import dom.pedido.Pedido;
import dom.pedido.PedidoServicio;
/**
 * Clase que implementa la funcionalidad de crear,listar y facturar una mesa
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@DomainService
@Named("Mesa")
public class MesaServicio extends AbstractFactoryAndRepository {

	/**
	 * Obtiene el nombre del icono de una mesa a crear
	 * @return String
	 */
	public String iconName() {
		return "Mesa";
	}

	/**
	 * Permite la creacion de una nueva Mesa
	 * @param numero int
	 * @param capacidadMesa int
	 * @return mesa Mesa
	 */
	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mesa crearMesa(@Named("Número") final int numero,
			@Named("Capacidad") final int capacidadMesa) {
		return crearMesaNueva(numero, capacidadMesa);
	}

	/**
	 * Toma los datos Obtenidos en el metodo crearMesa(), setea los estados
	 * y persiste una nueva Mesa
	 * @param numero int
	 * @param capacidadMesa int
	 * @return mesa Mesa
	 */
	@Programmatic
	public Mesa crearMesaNueva(final int numero, final int capacidadMesa) {
		final Mesa mesa = newTransientInstance(Mesa.class);
		mesa.setCapacidad(capacidadMesa);
		mesa.setNumero(numero);
		mesa.setEstadoHabilitacion(EstadoHabilitacionMesaEnum.Desocupada);
		mesa.setEstadoAsignacion(EstadoAsignacionMesaEnum.No_Asignada);
		mesa.setEstadoSeleccion(false);
		persist(mesa);
		return mesa;
	}

	/**
	 * Valida la capacidad,numero y existencia de una mesa
	 * @param numero int
	 * @param capacidadMesa int
	 * @return String
	 */
	@Programmatic
	public String validateCrearMesa(final int numero, final int capacidadMesa) {
		if (capacidadMesa > 20 || capacidadMesa < 1) {
			return "La capacidad debe ser menor o igual a 20 y mayor a 0.";
		}
		if (numero > 99 || numero < 1) {
			return "El número de Mesa debe ser mayor a 0 y menor a 100.";
		} else {
			return existeMesa(numero) ? "Ya existe la mesa "
					+ Integer.toString(numero) : null;
		}
	}

	/**
	 * Verifica en una lista de Mesas si existe la mesa a crear
	 * @param numero int
	 * @return boolean
	 */
	@Programmatic
	public boolean existeMesa(int numero) {
		for (Mesa _mesa : listarMesasTodas()) {
			if (_mesa.getNumero() == numero) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Permite listar mozos de alta en una Mesa
	 * @return List<Mozo>
	 */
	@Programmatic
	public List<Mozo> listarMozos() {
		return mozoServicio.listarMozosAlta();
	}

	/**
	 * Permite listar mesas asignadas a los mozos, si es que estos poseen
	 * @see dom.persona.Persona.getUsuario()
	 * @see dom.usuario.Usuario.getNombre()
	 * @return List<Mesa>
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Mesa> listarMesasAsignadas() {
		final Mozo mozo = uniqueMatch(Mozo.class, new Predicate<Mozo>() {
			@Override
			public boolean apply(Mozo _mozo) {
				// TODO Auto-generated method stub
				return _mozo.getUsuario().getNombre()
						.equals(getUser().getName());
			}

		});
		if (mozo == null) {
			informUser("No tiene mesas asignadas.");
			return null;
		}
		return mozo.getListamesas();
	}

	/**
	 * Permite listar todas las Mesas sin asignar a cualquier Mozo
	 * @return List<Mesa>
	 */
	@Programmatic
	public List<Mesa> listarMesasSinAsignar() {
		return allMatches(Mesa.class, new Predicate<Mesa>() {

			@Override
			public boolean apply(Mesa input) {
				// TODO Auto-generated method stub
				return input.getEstadoAsignacion() == EstadoAsignacionMesaEnum.No_Asignada ? true
						: false;
			}
		});
	}

	/**
	 * Permite listar todas las Mesas seleccionadas
	 * @return List<Mesa>
	 */
	@Programmatic
	public List<Mesa> listarMesasSeleccionadas() {
		return allMatches(Mesa.class, new Predicate<Mesa>() {

			@Override
			public boolean apply(Mesa input) {
				// TODO Auto-generated method stub
				return input.getEstadoSeleccion() ? true : false;
			}
		});
	}

	/**
	 * Permite listar todas las Mesas
	 * @return List<Mesa>
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Mesa> listarMesasTodas() {
		return allInstances(Mesa.class);
	}

	/**
	 * Relaliza toma de un pedido en la Mesa y le setea el estado de habilitacion
	 * @param _mesa Mesa
	 * @see dom.pedido.PedidoServicio.crearPedido()
	 * @return pedido Pedido
	 */
	@Programmatic
	public Pedido tomarPedido(Mesa _mesa) {
		final Pedido pedido = pedidoServicio.crearPedido();
		_mesa.addToPedidos(pedido);
		_mesa.setEstadoHabilitacion(EstadoHabilitacionMesaEnum.Ocupada);
		return pedido;
	}

	/**
	 * Relaliza la validacion para ver si existen pedidos para facturar
	 * @param _mesa Mesa
	 * @see dom.pedido.Pedido.getBebidas()
	 * @see dom.pedido.Pedido.getComanda()
	 * @see dom.comanda.Comanda.getMenues()
	 * @see dom.comanda.Comanda.getProductos()
	 * @see dom.pedido.Pedido.getMenuesComanda()
	 * @see dom.comanda.Comanda.getPreparada()
	 * @return String
	 */
	@Programmatic
	public String validarFactturado(final Mesa _mesa) {
		if (_mesa.getPedidos().isEmpty())
			return "No hay Pedidos para facturar";
		for (Pedido _pedido : _mesa.getPedidos()) {
			if (_pedido.getBebidas().isEmpty()
					&& (_pedido.getComanda().getMenues().isEmpty() && _pedido
							.getComanda().getProductos().isEmpty()&&_pedido.getComanda().getOfertas().isEmpty()))
				return "Existe Pedido vacío!";
			if (!_pedido.getProductosComanda().isEmpty()
					|| !_pedido.getMenuesComanda().isEmpty())
				if (_pedido.getComanda().getEstado() != _pedido.getComanda()
						.getPreparada())
					return "Existen Pedidos en Cocina";
		}
		return null;
	}

	/**
	 * Crea la factura con todos los pedidos de la Mesa
	 * @param _mesa Mesa
	 * @return factura Factura
	 */
	@Programmatic
	public Factura facturar(Mesa _mesa) {
		final Factura factura = facturaServicio
				.crearFactura(_mesa.getPedidos());
		limpiarMesa(_mesa);
		return factura;
	}

	/**
	 * Remueve un pedido de la mesa/comanda y cambia el estado de habilitacion
	 * @param _mesa Mesa
	 */
	@Programmatic
	public void limpiarMesa(Mesa _mesa) {
		for (Pedido _pedido : _mesa.getPedidos()) {
			remove(_pedido.getComanda());
			remove(_pedido);
		}
		_mesa.setEstadoHabilitacion(EstadoHabilitacionMesaEnum.Desocupada);
	}

	/**
	 * Inyecta el servicio del Mozo
	 */
	@Inject
	private MozoServicio mozoServicio;

	/**
	 * Inyecta el servicio de la Factura
	 */
	@Inject
	private FacturaServicio facturaServicio;

	/**
	 * Inyecta el servicio del Pedido
	 */
	@Inject
	private PedidoServicio pedidoServicio;
	
}
