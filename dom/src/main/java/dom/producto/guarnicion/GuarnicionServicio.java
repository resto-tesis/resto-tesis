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

package dom.producto.guarnicion;

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
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.query.QueryDefault;

import com.google.common.base.Predicate;
/**
 * Clase que da la funcionalidad de crear, persistir y listar a la clase guarnicion
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@DomainService
@Named("Guarnición")
public class GuarnicionServicio extends AbstractFactoryAndRepository {

	/**
	 * Obtiene el nombre del icono de una guarnicion
	 * @return String
	 */
	public String iconName(){
		return "Guarnicion";
	}
	
	/**
	 * Obtiene de la UI los datos validados de la Guarnicion a crear
	 * @param nombreGuarnicion String
	 * @param descripcionGuarnicion String
	 * @param precioGuarnicion BigDecimal
	 * @return crearGuarnicionNueva() Guarnicion
	 */
	@Named("Guarnición")
	@MemberOrder(name = "Crear", sequence = "1")
	public Guarnicion crearGuarnicion(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*") @MaxLength(value = 30) final String nombreGuarnicion,
			@Named("Descripción") @Optional @MultiLine(numberOfLines = 3) final String descripcionGuarnicion,
			@Named("Precio") @MaxLength(value = 5) @Digits(integer = 2, fraction = 2) final BigDecimal precioGuarnicion) {
		return crearGuarnicionNueva(nombreGuarnicion, descripcionGuarnicion,
				precioGuarnicion);
	}

	/**
	 * Toma los datos obtenidos en crearGuarnicion() y los persiste
	 * @param nombreGuarnicion String
	 * @param descripcionGuarnicion String
	 * @param precioGuarnicion BigDecimal
	 * @return guarnicion Guarnicion
	 */
	@Programmatic
	public Guarnicion crearGuarnicionNueva(final String nombreGuarnicion,
			final String descripcionGuarnicion,
			final BigDecimal precioGuarnicion) {
		final Guarnicion guarnicion = newTransientInstance(Guarnicion.class);
		guarnicion.setNombre(nombreGuarnicion.substring(0, 1).toUpperCase()
				+ nombreGuarnicion.substring(1));
		guarnicion.setDescripcion(descripcionGuarnicion);
		guarnicion.setPrecio(precioGuarnicion.doubleValue());
		guarnicion.setBaja(false);
		persist(guarnicion);
		return guarnicion;
	}

	/**
	 * Obtiene una lista de Guarniciones, que empiecen con un determinado parametro
	 * @param nombre String
	 * @return list<Guarnicion>
	 */
	@Programmatic
	public List<Guarnicion> completarGuarniciones(final String nombre) {
		return allMatches(new QueryDefault<Guarnicion>(Guarnicion.class,
				"guarnicionesQueEmpiezan", "nombre", "(?i).*" + nombre + ".*"));
	}

	/**
	 * Lista todas las Guarniciones de alta
	 * @return List<Guarnicion>
	 */
	@Named("Guarniciones")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Listar", sequence = "2")
	public List<Guarnicion> listarGuarnicionesAlta() {
		return allMatches(Guarnicion.class, new Predicate<Guarnicion>() {

			@Override
			public boolean apply(Guarnicion input) {
				// TODO Auto-generated method stub
				return input.getBaja() ? false : true;
			}
		});
	}

	/**
	 * Lista todas las Guarniciones
	 * @return listaguarniciones List<Guarnicion>
	 */
	@Named("Guarniciones")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Listar", sequence = "2")
	public List<Guarnicion> listarGuarnicionesTodas() {
		final List<Guarnicion> listaguarniciones = allInstances(Guarnicion.class);
		return listaguarniciones;
	}

}
