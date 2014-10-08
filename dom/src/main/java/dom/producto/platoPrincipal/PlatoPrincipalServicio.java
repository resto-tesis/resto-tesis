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

package dom.producto.platoPrincipal;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;

import dom.producto.EstadoLogico;
import dom.producto.plato.CondicionDePlatoEnum;
import dom.producto.plato.Plato;
import dom.producto.platoPrincipal.PlatoPrincipal;

@DomainService
public class PlatoPrincipalServicio extends AbstractFactoryAndRepository {

	@Named("Plato Principal")
	@MemberOrder(name = "Crear", sequence = "1")
	public Plato crearPlatoPrincipal(
			/* Parametros de Entrada */
			@RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 30) @Named("Nombre") final String nombre,
			@Named("Condición") final CondicionDePlatoEnum unaCondicion,
			@Optional @MultiLine(numberOfLines = 3) @Named("Descripción") final String unaDescripcion,
			@Named("Precio") @MaxLength(value = 6) @Digits(integer = 3, fraction = 2) final BigDecimal unPrecio,
			@Named("Habilitado") final EstadoLogico _estadoLogico) {
		/* Empieza el metodo */
		return crearUnPlatoPrincipal(nombre, unaCondicion, unaDescripcion,
				unPrecio, _estadoLogico);
	}

	@Hidden
	public PlatoPrincipal crearUnPlatoPrincipal(final String nombre,
			final CondicionDePlatoEnum unaCondicion,
			final String unaDescripcion, final BigDecimal unPrecio,
			final EstadoLogico _estadoLogico) {
		/* Empieza el Metodo */
		final PlatoPrincipal unPlato = newTransientInstance(PlatoPrincipal.class);
		unPlato.setNombre(nombre.substring(0, 1).toUpperCase()
				+ nombre.substring(1));
		unPlato.setCondicionDePlato(unaCondicion);
		if (unaDescripcion != null) {
			unPlato.setDescripcion(unaDescripcion.substring(0, 1).toUpperCase()
					+ unaDescripcion.substring(1));
		}
		unPlato.setPrecio(unPrecio.doubleValue());
		unPlato.setEstadoLogico(_estadoLogico);
		persist(unPlato);
		return unPlato;
	}

	public EstadoLogico default4CrearPlatoPrincipal() {
		// TODO Auto-generated method stub
		return EstadoLogico.Habilitado;
	}

	@Hidden
	public List<PlatoPrincipal> completarPlatoPrincipal(final String nombre) {
		return allMatches(new QueryDefault<PlatoPrincipal>(
				PlatoPrincipal.class, "platoPrincipalQueEmpiezan", "nombre",
				"(?i).*" + nombre + ".*"));
	}

	@Named("Platos Principales")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Listar", sequence = "2")
	public List<PlatoPrincipal> listarPLatosPrincipales() {
		final List<PlatoPrincipal> listaPlatos = allInstances(PlatoPrincipal.class);
		return listaPlatos;
	}

	// // Se verifica que el elemento por borrar no este relacionado con ninguna
	// // comanda o menu
	// @Hidden
	// public boolean validaBorrado(final PlatoPrincipal _platoPrincipal) {
	// return (firstMatch(Menu.class, new Predicate<Menu>() {
	// @Override
	// public boolean apply(Menu _menu) {
	// // TODO Auto-generated method stub
	// return _menu.getPlatoPrincipal().equals(_platoPrincipal);
	// }
	// }) != null) ? false : (firstMatch(ComandaProducto.class,
	// new Predicate<ComandaProducto>() {
	// @Override
	// public boolean apply(ComandaProducto _comanda) {
	// // TODO Auto-generated method stub
	// for (PlatoPrincipal platoPrincipal : _comanda
	// .getPlatosPrincipales())
	// return platoPrincipal.equals(_platoPrincipal);
	// return false;
	// }
	// }) != null) ? false : true;
	// }
}
