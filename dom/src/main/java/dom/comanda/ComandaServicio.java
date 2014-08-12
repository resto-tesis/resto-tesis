package dom.comanda;

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
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

import dom.bebida.Bebida;
import dom.guarnicion.Guarnicion;
import dom.mesa.Mesa;
import dom.platoEntrada.PlatoEntrada;
import dom.platoPrincipal.PlatoPrincipal;
import dom.postre.Postre;

@Named("Comanda")
public class ComandaServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@Hidden(where = Where.OBJECT_FORMS)
	@MemberOrder(sequence = "1")
	public Comanda crearComanda(
			@Named("Mesa") final Mesa _mesa,
			@Named("Plato Entrada") @Optional final PlatoEntrada _platoEntrada,
			@Named("Plato Principal") @Optional final PlatoPrincipal _platoPrincipal,
			@Named("Guarnición") @Optional final Guarnicion _guarnicion,
			@Named("Postre") @Optional final Postre _postre,
			@Named("Bebida") @Optional final Bebida _bebida) {
		return nuevaComanda(_mesa, _platoEntrada, _platoPrincipal, _guarnicion,
				_postre, _bebida);
	}

	@Hidden
	public Comanda nuevaComanda(final Mesa _mesa,
			final PlatoEntrada _platoEntrada,
			final PlatoPrincipal _platoPrincipal, final Guarnicion _guarnicion,
			final Postre _postre, final Bebida _bebida) {
		final Comanda comanda = newTransientInstance(Comanda.class);
		comanda.setEstadoPreparacion(EstadoComandaEnum.En_Espera);
		comanda.setMesa(_mesa);
		comanda.setBebida(_bebida);
		comanda.setGuarnicion(_guarnicion);
		comanda.setPlatoEntrada(_platoEntrada);
		comanda.setPlatoPrincipal(_platoPrincipal);
		comanda.setPostre(_postre);
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
	public List<Mesa> choices0CrearComanda() {
		return allMatches(new QueryDefault<Mesa>(Mesa.class, "mesasAsignadas"));
	}

	@Hidden
	public List<PlatoEntrada> choices1CrearComanda() {
		return allInstances(PlatoEntrada.class);
	}

	@Hidden
	public List<PlatoPrincipal> choices2CrearComanda() {
		return allInstances(PlatoPrincipal.class);
	}

	@Hidden
	public List<Guarnicion> choices3CrearComanda() {
		return allInstances(Guarnicion.class);
	}

	@Hidden
	public List<Postre> choices4CrearComanda() {
		return allInstances(Postre.class);
	}

	@Hidden
	public List<Bebida> choices5CrearComanda() {
		return allInstances(Bebida.class);
	}
	
	public String validateCrearComanda(final Mesa _mesa,
			final PlatoEntrada _platoEntrada,
			final PlatoPrincipal _platoPrincipal, final Guarnicion _guarnicion,
			final Postre _postre, final Bebida _bebida) {
		return _platoEntrada == null & _platoPrincipal == null
				& _guarnicion == null & _postre == null & _bebida == null ? "Ingrese al menos un pedido."
				: null;
	}
	
}
