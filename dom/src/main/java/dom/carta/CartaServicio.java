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

package dom.carta;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Named;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.menu.Menu;
import dom.platoEntrada.PlatoEntrada;
import dom.platoPrincipal.PlatoPrincipal;
import dom.postre.Postre;

@Named("Carta")
public class CartaServicio extends AbstractFactoryAndRepository {

	@HomePage
	@Named("Mostrar Carta")
	public Carta mostrarCarta() {
		return newViewModelInstance(Carta.class, "carta");
	}

	@Hidden
	public List<Bebida> listarBebidas() {
		return allInstances(Bebida.class);
	}

	@Hidden
	public List<Guarnicion> listarGuarnicion() {
		return allInstances(Guarnicion.class);
	}

	@Hidden
	public List<Menu> listarMenu() {
		return allInstances(Menu.class);
	}

	@Hidden
	public List<PlatoEntrada> listarPlatosEntradas() {
		return allInstances(PlatoEntrada.class);
	}

	@Hidden
	public List<PlatoPrincipal> listarPlatosPricipales() {
		return allInstances(PlatoPrincipal.class);
	}

	@Hidden
	public List<Postre> listarPostres() {
		return allInstances(Postre.class);
	}
}
