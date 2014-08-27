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

package dom.guarnicion;

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
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.query.QueryDefault;

import com.google.common.base.Predicate;

import dom.comanda.Comanda;
import dom.menu.Menu;

@DomainService
public class GuarnicionServicio extends AbstractFactoryAndRepository {

	@Named("Guarnición")
	@MemberOrder(name = "Crear", sequence = "1")
	public Guarnicion crearGuarnicion(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 30) final String nombreGuarnicion,
			@Named("Descripción") @Optional @MultiLine(numberOfLines = 3) final String descripcionGuarnicion,
			@Named("Precio") @MaxLength(value = 5) @Digits(integer = 2, fraction = 2) final BigDecimal precioGuarnicion) {
		return crearGuarnicionNueva(nombreGuarnicion, descripcionGuarnicion,
				precioGuarnicion);
	}

	@Hidden
	public Guarnicion crearGuarnicionNueva(final String nombreGuarnicion,
			final String descripcionGuarnicion,
			final BigDecimal precioGuarnicion) {
		final Guarnicion guarnicion = newTransientInstance(Guarnicion.class);
		guarnicion.setNombre(nombreGuarnicion.substring(0, 1).toUpperCase()
				+ nombreGuarnicion.substring(1));
		guarnicion.setDescripcion(descripcionGuarnicion);
		guarnicion.setPrecio(precioGuarnicion.doubleValue());
		persist(guarnicion);
		return guarnicion;
	}

	@Hidden
	public List<Guarnicion> completarGuarniciones(final String nombre) {
		return allMatches(new QueryDefault<Guarnicion>(Guarnicion.class,
				"guarnicionesQueEmpiezan", "nombre", "(?i).*" + nombre + ".*"));
	}

	@Named("Guarniciones")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name="Listar",sequence = "2")
	public List<Guarnicion> listarGuarniciones() {
		final List<Guarnicion> listaguarniciones = allInstances(Guarnicion.class);
		return listaguarniciones;
	}

	// Se verifica que el elemento por borrar no este relacionado con ninguna
	// comanda o menu
	@Hidden
	public boolean validaBorrado(final Guarnicion _guarnicion) {
		return (firstMatch(Menu.class, new Predicate<Menu>() {
			@Override
			public boolean apply(Menu _menu) {
				// TODO Auto-generated method stub
				return _menu.getGuarnicion().equals(_guarnicion);
			}
		}) != null) ? false : (firstMatch(Comanda.class,
				new Predicate<Comanda>() {
					@Override
					public boolean apply(Comanda _comanda) {
						// TODO Auto-generated method stub
						for (Guarnicion guarnicion : _comanda.getGuarniciones())
							return guarnicion.equals(_guarnicion);
						return false;
					}
				}) != null) ? false : true;
	}
}
