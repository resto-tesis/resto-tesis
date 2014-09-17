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

package dom.comandaBebida;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import dom.comestibles.bebida.Bebida;
import dom.mesa.Mesa;

@DomainService
public class ComandaBebidaServicio extends AbstractFactoryAndRepository {

	@Named("Tomar Bebidas")
	@MemberOrder(name = "Comanda", sequence = "6")
	public ComandaBebida crear(final Mesa mesa) {
		final ComandaBebida comanda = newTransientInstance(ComandaBebida.class);
		comanda.setFechaDePedido(new Date());
		comanda.setMozo(getUser().getName());
		persist(comanda);
		mesa.addToComandas(comanda);
		return comanda;
	}

	@Named("Listar Bebidas")
	@MemberOrder(name = "Comanda", sequence = "6")
	@ActionSemantics(Of.SAFE)
	public List<ComandaBebida> listarBebida() {
		return allInstances(ComandaBebida.class);
	}

	@Hidden
	public List<Mesa> choices0Crear() {
		return allInstances(Mesa.class);
	}

	@Hidden
	public List<Bebida> listaBebidas() {
		return allInstances(Bebida.class);
	}

}
