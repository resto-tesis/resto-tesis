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

package dom.producto.postre;

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

/**
 * Clase que da la funcionalidad de crear, persistir y listar a la clase Postre
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@DomainService
@Named("Postre")
public class PostreServicio extends AbstractFactoryAndRepository {

	/**
	 * Obtiene el nombre del icono de un Postre
	 * @return String
	 */
	public String iconName(){
		return "Postre";
	}
	
	/**
	 * Obtiene de la UI los datos validados de un postre a crear
	 * @param nombrePostre String
	 * @param descripcionPostre String
	 * @param preciopostre BigDecimal
	 * @return crearGPostreNuevo() Postre
	 */
	@Named("Postre")
	@MemberOrder(name = "Crear", sequence = "1")
	public Postre crearPostre(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*") @MaxLength(value = 30) final String nombrePostre,
			@Optional @MultiLine(numberOfLines = 3) @Named("Descripción") final String descripcionPostre,
			@Named("Precio") @MaxLength(value = 5) @Digits(integer = 2, fraction = 2) final BigDecimal precioPostre) {
		return crearPostreNuevo(nombrePostre, descripcionPostre, precioPostre);
	}

	/**
	 * Toma los datos obtenidos en crearGPostreNuevo() y los persiste
	 * @param nombrePostre String
	 * @param descripcionPostre String
	 * @param preciopostre BigDecimal
	 * @return postre Postre
	 */
	@Programmatic
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
		postre.setBaja(false);
		persist(postre);
		return postre;
	}

	/**
	 * Obtiene una lista de Postres, que empiecen con un determinado parametro
	 * @param nombre String
	 * @return list<Postre>
	 */
	@Programmatic
	public List<Postre> completarPostres(final String nombre) {
		return allMatches(new QueryDefault<Postre>(Postre.class,
				"postresQueEmpiezan", "nombre", "(?i).*" + nombre + ".*"));
	}

	/**
	 * Lista todos los Postres de alta
	 * @return List<Guarnicion>
	 */
	@Named("Postres")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Listar", sequence = "2")
	public List<Postre> listarPostresAlta() {
		return allMatches(Postre.class, new Predicate<Postre>() {

			@Override
			public boolean apply(Postre input) {
				// TODO Auto-generated method stub
				return input.getBaja() ? false : true;
			}
		});
	}

	/**
	 * Lista todos los Postres
	 * @return listapostres List<Postres>
	 */
	@Named("Postres")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(name = "Listar", sequence = "2")
	public List<Postre> listarPostresTodos() {
		final List<Postre> listapostres = allInstances(Postre.class);
		return listapostres;
	}
}
