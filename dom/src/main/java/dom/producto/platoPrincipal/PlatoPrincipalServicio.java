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
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;

import com.google.common.base.Predicate;

import dom.producto.plato.CondicionDePlatoEnum;
import dom.producto.plato.Plato;
import dom.producto.platoPrincipal.PlatoPrincipal;
/**
 * Clase que da la funcionalidad de crear, persistir y listar a la clase PlatoPrincipal
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@DomainService
@Named("Plato Principal")
public class PlatoPrincipalServicio extends AbstractFactoryAndRepository {

	/**
	 * Obtiene el nombre del icono de un Plato Principal
	 * @return String
	 */
	public String iconName(){
		return "PlatoPrincipal";
	}
	/**
	 * Obtiene de la UI los datos validados del Plato Principal
	 * @param nombre String 
	 * @param unaCondicion CondicionDePlatoEnum
	 * @param unaDescripcion String
	 * @param unPrecio BigDecimal
	 * @return crearUnPlatoPrincipal() PlatoPrincipal
	 */
	@Named("Plato Principal")
	@MemberOrder(name = "Crear", sequence = "1")
	public Plato crearPlatoPrincipal(
			/* Parametros de Entrada */
			@RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*") @MaxLength(value = 30) @Named("Nombre") final String nombre,
			@Named("Condición") final CondicionDePlatoEnum unaCondicion,
			@Optional @MultiLine(numberOfLines = 3) @Named("Descripción") final String unaDescripcion,
			@Named("Precio") @MaxLength(value = 6) @Digits(integer = 3, fraction = 2) final BigDecimal unPrecio) {
		/* Empieza el metodo */
		return crearUnPlatoPrincipal(nombre, unaCondicion, unaDescripcion,
				unPrecio);
	}

	/**
	 * Toma los datos obtenidos en crearPlatoPrincipal() y los persiste
	 * @param nombre String 
	 * @param unaCondicion CondicionDePlatoEnum
	 * @param unaDescripcion String
	 * @param unPrecio BigDecimal
	 * @return unPlato PlatoPrincipal
	 */
	@Programmatic
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
		unPlato.setBaja(false);
		persist(unPlato);
		return unPlato;
	}

	/**
	 * Obtiene una lista de Platos de Principales, que empiecen con un determinado parametro
	 * @param nombre String
	 * @return list<PlatoPrincipal>
	 */
	@Programmatic
	public List<PlatoPrincipal> completarPlatoPrincipal(final String nombre) {
		return allMatches(new QueryDefault<PlatoPrincipal>(
				PlatoPrincipal.class, "platoPrincipalQueEmpiezan", "nombre",
				"(?i).*" + nombre + ".*"));
	}

	/**
	 * Obtiene una lista de Platos de Principales de alta
	 * @return list<PlatoPrincipal>
	 */
	@Named("Platos Principales")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Listar", sequence = "2")
	public List<PlatoPrincipal> listarPLatosPrincipalesAlta() {
		return allMatches(PlatoPrincipal.class,
				new Predicate<PlatoPrincipal>() {

					@Override
					public boolean apply(PlatoPrincipal input) {
						// TODO Auto-generated method stub
						return input.getBaja() ? false : true;
					}
				});
	}

	/**
	 * Obtiene una lista de todos los Platos de Principales 
	 * @return listaPlatos list<PlatoPrincipal>
	 */
	@Named("Platos Principales")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Listar", sequence = "2")
	public List<PlatoPrincipal> listarPLatosPrincipalesTodos() {
		final List<PlatoPrincipal> listaPlatos = allInstances(PlatoPrincipal.class);
		return listaPlatos;
	}
}
