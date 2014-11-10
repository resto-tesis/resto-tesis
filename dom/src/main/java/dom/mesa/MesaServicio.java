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

@DomainService
@Named("Mesa")
public class MesaServicio extends AbstractFactoryAndRepository {

	public String iconName() {
		return "Mesa";
	}

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mesa crearMesa(@Named("Número") final int numero,
			@Named("Capacidad") final int capacidadMesa) {
		return crearMesaNueva(numero, capacidadMesa);
	}

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

	@Programmatic
	public boolean existeMesa(int numero) {
		for (Mesa _mesa : listarMesasTodas()) {
			if (_mesa.getNumero() == numero) {
				return true;
			}
		}
		return false;
	}

	@Programmatic
	public List<Mozo> listarMozos() {
		return mozoServicio.listarMozosAlta();
	}

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

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Mesa> listarMesasTodas() {
		return allInstances(Mesa.class);
	}

	@Programmatic
	public Pedido tomarPedido(Mesa _mesa) {
		final Pedido pedido = pedidoServicio.crearPedido();
		_mesa.addToPedidos(pedido);
		_mesa.setEstadoHabilitacion(EstadoHabilitacionMesaEnum.Ocupada);
		return pedido;
	}

	@Programmatic
	public String validarFactturado(final Mesa _mesa) {
		if (_mesa.getPedidos().isEmpty())
			return "No hay Pedidos para facturar";
		for (Pedido _pedido : _mesa.getPedidos()) {
			if (_pedido.getBebidas().isEmpty()
					&& (_pedido.getComanda().getMenues().isEmpty() && _pedido
							.getComanda().getProductos().isEmpty()))
				return "Existe Pedido vacío!";
			if (!_pedido.getProductosComanda().isEmpty()
					|| !_pedido.getMenuesComanda().isEmpty())
				if (_pedido.getComanda().getEstado() != _pedido.getComanda()
						.getPreparada())
					return "Existen Pedidos en Cocina";
		}
		return null;
	}

	@Programmatic
	public Factura facturar(Mesa _mesa) {
		final Factura factura = facturaServicio
				.crearFactura(_mesa.getPedidos());
		limpiarMesa(_mesa);
		return factura;
	}

	@Programmatic
	public void limpiarMesa(Mesa _mesa) {
		for (Pedido _pedido : _mesa.getPedidos()) {
			remove(_pedido.getComanda());
			remove(_pedido);
		}
		_mesa.setEstadoHabilitacion(EstadoHabilitacionMesaEnum.Desocupada);
	}

	@Inject
	private MozoServicio mozoServicio;

	@Inject
	private FacturaServicio facturaServicio;

	@Inject
	private PedidoServicio pedidoServicio;
	
}
