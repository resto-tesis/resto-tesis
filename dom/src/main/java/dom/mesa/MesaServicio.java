package dom.mesa;

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

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

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
		mesa.setCapacidadMesa(capacidadMesa);
		mesa.setNumeroMesa(numero);
		mesa.setEstadoHabilitacionMesa(EstadoHabilitacionMesaEnum.Desocupada);
		mesa.setEstadoAsignacionMesa(EstadoAsignacionMesaEnum.No_Asignada);
		mesa.setEstadoSeleccion(false);
		persist(mesa);
		return mesa;
	}

	public String validateCrearMesa(final int numero, final int capacidadMesa) {
		if (capacidadMesa > 20) {
			return "La capacidad debe ser menor o igual a 20";
		}
		return existeMesa(numero) ? "Ya existe la mesa "
				+ Integer.toString(numero) : null;
	}

	@Hidden
	public boolean existeMesa(int numero) {
		for (Mesa _mesa : allInstances(Mesa.class)) {
			if (_mesa.getNumeroMesa() == numero) {
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

}
