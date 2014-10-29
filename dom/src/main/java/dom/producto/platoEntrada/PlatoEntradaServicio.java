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

package dom.producto.platoEntrada;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;

import dom.producto.plato.CondicionDePlatoEnum;
import dom.producto.plato.Plato;

@DomainService
@Named("Plato de Entrada")
public class PlatoEntradaServicio extends AbstractFactoryAndRepository {

	@Named("Plato de Entrada")
	@MemberOrder(name = "Crear", sequence = "1")
	public Plato crearPlatoEntrada(
			/* Parametros de Entrada */
			@RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 30) @Named("Nombre") final String nombre,
			@Named("Condición") final CondicionDePlatoEnum unaCondicion,
			@Optional @MultiLine(numberOfLines = 3) @Named("Descripción") final String unaDescripcion,
			@Named("Precio") @MaxLength(value = 6) @Digits(integer = 3, fraction = 2) final BigDecimal unPrecio) {
		/* Empieza el metodo */
		return crearUnPlatoEntrada(nombre, unaCondicion, unaDescripcion,
				unPrecio);
	}

	@Programmatic
	public PlatoEntrada crearUnPlatoEntrada(final String nombre,
			final CondicionDePlatoEnum unaCondicion,
			final String unaDescripcion, final BigDecimal unPrecio) {
		/* Empieza el Metodo */
		final PlatoEntrada unPlato = newTransientInstance(PlatoEntrada.class);
		unPlato.setNombre(nombre.substring(0, 1).toUpperCase()
				+ nombre.substring(1));
		unPlato.setCondicionDePlato(unaCondicion);
		if (unaDescripcion != null) {
			unPlato.setDescripcion(unaDescripcion.substring(0, 1).toUpperCase()
					+ unaDescripcion.substring(1));
		}
		unPlato.setPrecio(unPrecio.doubleValue());
		unPlato.setBaja(false);
		persist(unPlato);
		return unPlato;
	}

	@Programmatic
	public List<PlatoEntrada> completarPlatoEntrada(final String nombre) {
		return allMatches(new QueryDefault<PlatoEntrada>(PlatoEntrada.class,
				"platoEntradaQueEmpiezan", "nombre", "(?i).*" + nombre + ".*"));
	}

	@Named("Platos de Entrada")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Listar", sequence = "2")
	public List<PlatoEntrada> listarPLatosEntrada() {
		final List<PlatoEntrada> listaPlatos = allInstances(PlatoEntrada.class);
		return listaPlatos;
	}

	// // Se verifica que el elemento por borrar no este relacionado con ninguna
	// // comanda o menu
	// @Hidden
	// public boolean validaBorrado(final PlatoEntrada _platoEntrada) {
	// return (firstMatch(Menu.class, new Predicate<Menu>() {
	// @Override
	// public boolean apply(Menu _menu) {
	// // TODO Auto-generated method stub
	// return _menu.getPlatoEntrada().equals(_platoEntrada);
	// }
	// }) != null) ? false : (firstMatch(ComandaProducto.class,
	// new Predicate<ComandaProducto>() {
	// @Override
	// public boolean apply(ComandaProducto _comanda) {
	// // TODO Auto-generated method stub
	// for (PlatoEntrada platoEntrada : _comanda
	// .getPlatosEntrada())
	// return platoEntrada.equals(_platoEntrada);
	// return false;
	// }
	// }) != null) ? false : true;
	// }
}
