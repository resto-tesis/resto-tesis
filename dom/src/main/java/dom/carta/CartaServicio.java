package dom.carta;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.query.QueryDefault;
import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.menu.Menu;
import dom.platoEntrada.PlatoEntrada;
import dom.platoPrincipal.PlatoPrincipal;
import dom.postre.Postre;

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

@Named("Carta")
public class CartaServicio extends AbstractFactoryAndRepository{
	
	@Named("Bebidas")
	@MemberOrder(sequence = "1")
	public List<Bebida> listarBebidas() {
		return allMatches(new QueryDefault<Bebida>(Bebida.class, "todasLasBebidas"));
	}
	
	@Named("Guarnicion")
	@MemberOrder(sequence = "2")
	public List<Guarnicion> listarGuarnicion() {
		return allMatches(new QueryDefault<Guarnicion>(Guarnicion.class, "todasLasGuarniciones"));
	}
	
	@Named("Menues")
	@MemberOrder(sequence = "3")
	public List<Menu> listarMenu() {
		return allMatches(new QueryDefault<Menu>(Menu.class, "todosLosMenues"));
	}
	
	@Named("Plato de Entrada")
	@MemberOrder(sequence = "4")
	public List<PlatoEntrada> listarPlatosEntradas() {
		return allMatches(new QueryDefault<PlatoEntrada>(PlatoEntrada.class, "todosLosPlatosEntrada"));
	}
	
	@Named("Platos Principales")
	@MemberOrder(sequence = "5")
	public List<PlatoPrincipal> listarPlatosPricipales() {
		return allMatches(new QueryDefault<PlatoPrincipal>(PlatoPrincipal.class, "todosLosPlatosPricipales"));
	}
	
	@Named("Postres")
	@MemberOrder(sequence = "6")
	public List<Postre> listarPostres() {
		return allMatches(new QueryDefault<Postre>(Postre.class, "todosLosPostres"));
	}
	
	
	@Hidden
	public List<Guarnicion> listarGuarniciones() {
		return allMatches(new QueryDefault<Guarnicion>(Guarnicion.class, "todasLasGuarniciones"));
	}
	@Hidden
	public List<Menu> listarMenues() {
		return allMatches(new QueryDefault<Menu>(Menu.class, "todosLosMenues"));
	}
	@Hidden
	public List<PlatoEntrada> listarPlatoEntrada() {
		return allMatches(new QueryDefault<PlatoEntrada>(PlatoEntrada.class, "todosLosPlatosEntrada"));
	}
	@Hidden
	public List<PlatoPrincipal> listarPlatoPrincipal() {
		return allMatches(new QueryDefault<PlatoPrincipal>(PlatoPrincipal.class, "todosLosPlatosPricipales"));
	}
	@Hidden
	public List<Postre> listarPostre() {
		return allMatches(new QueryDefault<Postre>(Postre.class, "todosLosPostres"));
	}
	
}
