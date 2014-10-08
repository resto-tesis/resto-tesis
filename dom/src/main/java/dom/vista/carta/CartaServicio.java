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

package dom.vista.carta;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

import dom.menu.Menu;
import dom.producto.bebida.Bebida;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.postre.Postre;

@DomainService
@Named("Carta")
public class CartaServicio extends AbstractFactoryAndRepository {

	@Named("Mostrar Carta")
	public Carta mostrarCarta() {
		return newViewModelInstance(Carta.class, "carta");
	}

	@Programmatic
	public List<Bebida> listarBebidas() {
		return allInstances(Bebida.class);
	}

	@Programmatic
	public List<Guarnicion> listarGuarnicion() {
		return allInstances(Guarnicion.class);
	}

	@Programmatic
	public List<Menu> listarMenu() {
		return allInstances(Menu.class);
	}

	@Programmatic
	public List<PlatoEntrada> listarPlatosEntradas() {
		return allInstances(PlatoEntrada.class);
	}

	@Programmatic
	public List<PlatoPrincipal> listarPlatosPricipales() {
		return allInstances(PlatoPrincipal.class);
	}

	@Programmatic
	public List<Postre> listarPostres() {
		return allInstances(Postre.class);
	}
}
