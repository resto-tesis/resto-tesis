package dom.platoPrincipal;

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

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import com.google.common.base.Predicate;

import dom.comanda.Comanda;
import dom.menu.Menu;
import dom.plato.CondicionDePlatoEnum;
import dom.plato.Plato;
import dom.platoPrincipal.PlatoPrincipal;

@Named("Plato Principal")
public class PlatoPrincipalServicio extends AbstractFactoryAndRepository {

	@Named("Crear Plato Principal")
	@MemberOrder(sequence = "1")
	public Plato crearPlatoPrincipal(
			/* Parametros de Entrada */
			@RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 25) @Named("Nombre") final String nombre,
			@Named("Condición") final CondicionDePlatoEnum unaCondicion,
			@Optional @MultiLine(numberOfLines = 3) @Named("Descripción") final String unaDescripcion,
			@Named("Precio") @MaxLength(value = 6) @Digits(integer = 3, fraction = 2) final BigDecimal unPrecio) {
		/* Empieza el metodo */
		return crearUnPlatoPrincipal(nombre, unaCondicion, unaDescripcion,
				unPrecio);
	}

	@Hidden
	public PlatoPrincipal crearUnPlatoPrincipal(final String nombre,
			final CondicionDePlatoEnum unaCondicion,
			final String unaDescripcion, final BigDecimal unPrecio) {
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
		persist(unPlato);
		return unPlato;
	}

	@Named("Listar Platos Principales")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "3")
	public List<PlatoPrincipal> listarPLatosPrincipales() {
		final List<PlatoPrincipal> listaPlatos = allInstances(PlatoPrincipal.class);
		return listaPlatos;
	}

	// Se verifica que el elemento por borrar no este relacionado con ninguna
	// comanda o menu
	@Hidden
	public boolean validaBorrado(final PlatoPrincipal _plato) {
		return firstMatch(Menu.class, new Predicate<Menu>() {
			@Override
			public boolean apply(Menu _menu) {
				// TODO Auto-generated method stub
				return _menu.getPlatoPrincipal().equals(_plato);
			}
		}) != null ? false : firstMatch(Comanda.class,
				new Predicate<Comanda>() {
					@Override
					public boolean apply(Comanda _comanda) {
						// TODO Auto-generated method stub
						return _comanda.getPlatoPrincipal().equals(_plato);
					}
				}) != null ? false : true;
	}
}
