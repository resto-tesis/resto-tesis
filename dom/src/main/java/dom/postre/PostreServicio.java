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

package dom.postre;

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

import com.google.common.base.Predicate;

import dom.comanda.Comanda;
import dom.menu.Menu;

@DomainService
public class PostreServicio extends AbstractFactoryAndRepository {

	@Named("Postre")
	@MemberOrder(name = "Crear",sequence = "1")
	public Postre crearPostre(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 30) final String nombrePostre,
			@Optional @MultiLine(numberOfLines = 3) @Named("Descripción") final String descripcionPostre,
			@Named("Precio") @MaxLength(value = 5) @Digits(integer = 2, fraction = 2) final BigDecimal precioPostre) {
		return crearPostreNuevo(nombrePostre, descripcionPostre, precioPostre);
	}

	@Hidden
	public Postre crearPostreNuevo(final String nombrePostre,
			final String descripcionPostre, final BigDecimal precioPostre) {
		final Postre postre = newTransientInstance(Postre.class);
		postre.setNombre(nombrePostre.substring(0, 1).toUpperCase()
				+ nombrePostre.substring(1));
		if (descripcionPostre != null) {
			postre.setDescripcion(descripcionPostre.substring(0, 1)
					.toUpperCase() + descripcionPostre.substring(1));
		}
		postre.setPrecio(precioPostre.doubleValue());
		persist(postre);
		return postre;
	}

	@Hidden
	public List<Postre> completarPostres(final String nombre) {
		return allMatches(new QueryDefault<Postre>(Postre.class,
				"postresQueEmpiezan", "nombre", "(?i).*" + nombre + ".*"));
	}

	@Named("Postres")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name="Listar",sequence = "2")
	public List<Postre> listarPostres() {
		final List<Postre> listapostres = allInstances(Postre.class);
		return listapostres;
	}

	// Se verifica que el elemento por borrar no este relacionado con ninguna
	// comanda o menu
	@Hidden
	public boolean validaBorrado(final Postre _postre) {
		return (firstMatch(Menu.class, new Predicate<Menu>() {
			@Override
			public boolean apply(Menu _menu) {
				// TODO Auto-generated method stub
				return _menu.getPostre().equals(_postre);
			}
		}) != null) ? false : (firstMatch(Comanda.class,
				new Predicate<Comanda>() {
					@Override
					public boolean apply(Comanda _comanda) {
						// TODO Auto-generated method stub
						for (Postre postre : _comanda.getPostres())
							return postre.equals(_postre);
						return false;
					}
				}) != null) ? false : true;
	}
}
