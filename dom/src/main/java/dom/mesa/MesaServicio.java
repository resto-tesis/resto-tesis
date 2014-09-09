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

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;

import com.google.common.base.Predicate;

import dom.comandaProducto.ComandaProducto;
import dom.mozo.Mozo;

@DomainService
@Named("Mesa")
public class MesaServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mesa crearMesa(@Named("Número") final int numero,
			@Named("Capacidad") final int capacidadMesa) {
		return crearMesaNueva(numero, capacidadMesa);
	}

	// }}
	@Hidden
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

	@Hidden
	public String validateCrearMesa(final int numero, final int capacidadMesa) {
		if (capacidadMesa > 20 || capacidadMesa < 1) {
			return "La capacidad debe ser menor o igual a 20 y mayor a 0.";
		}
		if (numero > 99 || numero < 1) {
			return "El número de Mesa debe ser mayor a 0 y menor a 100.";
		} else {
			return existeMesa(numero) ? "Ya existe la mesa."
					+ Integer.toString(numero) : null;
		}
	}

	@Hidden
	public boolean existeMesa(int numero) {
		for (Mesa _mesa : allInstances(Mesa.class)) {
			if (_mesa.getNumero() == numero) {
				return true;
			}
		}
		return false;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Mesa> listarMesas() {
		final List<Mesa> listamesas = allInstances(Mesa.class);
		return listamesas;
	}

	@Hidden
	public List<Mozo> listaDeMozos() {
		return allInstances(Mozo.class);
	}

	@Hidden
	public List<ComandaProducto> listarComandasPorDia() {
		return allMatches(new QueryDefault<ComandaProducto>(ComandaProducto.class,
				"comandasPorMesaPorDia"));
	}

	@Hidden
	public List<ComandaProducto> listarComandasPorSemana() {
		return allMatches(new QueryDefault<ComandaProducto>(ComandaProducto.class,
				"comandasPorMesaPorSemana"));
	}

	// Se verifica que el elemento por borrar no este relacionado con ninguna
	// comanda
	@Hidden
	public boolean validaBorrado(final Mesa _mesa) {
		return firstMatch(ComandaProducto.class, new Predicate<ComandaProducto>() {
			@Override
			public boolean apply(ComandaProducto _comanda) {
				// TODO Auto-generated method stub
				return _comanda.getMesa().equals(_mesa);
			}
		}) != null ? false : true;
	}

	// Retorna la lista de comandas pertenecientes a la mesa en la que se está
	// posicionado
	@Hidden
	public List<ComandaProducto> comandasPertenecientes(final Mesa mesa) {
		return allMatches(ComandaProducto.class, new Predicate<ComandaProducto>() {
			@Override
			public boolean apply(ComandaProducto input) {
				// TODO Auto-generated method stub
				return (input.getMesa().equals(mesa)) ? true : false;
			}
		});
	}
}
