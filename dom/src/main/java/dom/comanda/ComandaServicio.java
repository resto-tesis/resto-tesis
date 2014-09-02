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

package dom.comanda;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.menu.Menu;
import dom.mesa.Mesa;
import dom.platoEntrada.PlatoEntrada;
import dom.platoPrincipal.PlatoPrincipal;
import dom.postre.Postre;

@DomainService
@Named("Comanda")
public class ComandaServicio extends AbstractFactoryAndRepository {
	
	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Comanda crear(final Mesa mesa) {
		final Comanda comanda = newTransientInstance(Comanda.class);
		comanda.setMesa(mesa);
		persist(comanda);
		return comanda;
	}

	@Named("Listar")
	@MemberOrder(sequence = "2")
	@ActionSemantics(Of.SAFE)
	public List<Comanda> listarComanda() {
		return allInstances(Comanda.class);
	}

	@Hidden
	public List<Mesa> choices0Crear() {
		return allInstances(Mesa.class);
	}

	@Hidden
	public List<Bebida> listaBebidas() {
		return allInstances(Bebida.class);
	}

	@Hidden
	public List<Guarnicion> listaGuarnicion() {
		return allInstances(Guarnicion.class);
	}

	@Hidden
	public List<Postre> listarPostres() {
		return allInstances(Postre.class);
	}

	@Hidden
	public List<PlatoEntrada> listarPlatosEntrada() {
		return allInstances(PlatoEntrada.class);
	}
	
	@Hidden
	public List<PlatoPrincipal> listarPlatosPrincipales() {
		return allInstances(PlatoPrincipal.class);
	}
	@Hidden
	public List<Menu> listarMenues() {
		return allInstances(Menu.class);
	}
}
