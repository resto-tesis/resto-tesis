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

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

import dom.menu.Menu;
import dom.menu.MenuServicio;
import dom.producto.bebida.Bebida;
import dom.producto.bebida.BebidaServicio;
import dom.producto.guarnicion.Guarnicion;
import dom.producto.guarnicion.GuarnicionServicio;
import dom.producto.platoEntrada.PlatoEntrada;
import dom.producto.platoEntrada.PlatoEntradaServicio;
import dom.producto.platoPrincipal.PlatoPrincipal;
import dom.producto.platoPrincipal.PlatoPrincipalServicio;
import dom.producto.postre.Postre;
import dom.producto.postre.PostreServicio;

/**
 * Da la funcionalidad a la clase Carta de Obtener y mostras en ella,
 * los diferentes productos que la componen
 * @author RestoTesis
 * @since 10/08/2014
 * @version 1.0.0
 */
@DomainService
@Named("Carta")
public class CartaServicio extends AbstractFactoryAndRepository {

	/**
	 * Retorna una vista de la instancia de la Clase Carta
	 * @return Carta
	 */
	@Named("Mostrar Carta")
	public Carta mostrarCarta() {
		return newViewModelInstance(Carta.class, "carta");
	}

	/**
	 * Obtiene  una lista de bebidas
	 * @return List<Bebidas>
	 */
	@Programmatic
	public List<Bebida> listarBebidas() {
		return bebidaServicio.listarBebidasAlta();
	}

	/**
	 * Obtiene  una lista de Guarniciones
	 * @return List<Guarnicion>
	 */
	@Programmatic
	public List<Guarnicion> listarGuarnicion() {
		return guarnicionServicio.listarGuarnicionesAlta();
	}

	/**
	 * Obtiene la lista de menues 
	 * @return List<Menu>
	 */
	@Programmatic
	public List<Menu> listarMenu() {
		return menuServicio.listarMenuesAlta();
	}

	/**
	 * Obtiene la lista de platos de entradas 
	 * @return List<PlatoEntrada>
	 */
	@Programmatic
	public List<PlatoEntrada> listarPlatosEntradas() {
		return platoEntradaServicio.listarPLatosEntradaAlta();
	}

	/**
	 * Obtiene la lista de platos principales
	 * @return List<PlatoPrincipal>
	 */
	@Programmatic
	public List<PlatoPrincipal> listarPlatosPricipales() {
		return platoPrincipalServicio.listarPLatosPrincipalesAlta();
	}

	/**
	 * Obtiene la lista de postres
	 * @return List<Postre>
	 */
	@Programmatic
	public List<Postre> listarPostres() {
		return postreServicio.listarPostresAlta();
	}

	/**
	 * Inyecta el servicio del menu	
	 */
	@Inject
	private MenuServicio menuServicio;

	/**
	 * Inyecta el servicio del plato principal	
	 */
	@Inject
	private PlatoPrincipalServicio platoPrincipalServicio;

	/**
	 * Inyecta el servicio del plato de entrada	
	 */
	@Inject
	private PlatoEntradaServicio platoEntradaServicio;

	/**
	 * Inyecta el servicio del postre	
	 */
	@Inject
	private PostreServicio postreServicio;

	/**
	 * Inyecta el servicio de la guarnicion	
	 */
	@Inject
	private GuarnicionServicio guarnicionServicio;

	/**
	 * Inyecta el servicio de la bebida	
	 */
	@Inject
	private BebidaServicio bebidaServicio;
}
