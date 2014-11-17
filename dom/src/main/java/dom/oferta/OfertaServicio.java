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

package dom.oferta;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;
import org.joda.time.LocalDate;

import com.google.common.base.Predicate;

import dom.menu.Menu;
import dom.menu.MenuServicio;
/**
 * Otorga la funcionalidad a la clase Oferta: permitinedo crearla,persistirla, listarlas,
 * otorgar y calcular descuento aplicado; listar los menues a incorporar
 * @author RestoTesis
 * @since 15/10/2014
 * @version 1.0.0
 */
@DomainService
@Named("Oferta")
public class OfertaServicio extends AbstractFactoryAndRepository {

	/**
	 * Retorna el nombre del icono de la Oferta
	 * @return String
	 */
	public String iconName() {
		return "Oferta";
	}

	/**
	 * Obtiene de la interfaz de usuario, una nueva Oferta
	 * @param _nombre String
	 * @param _cantidad_personas int
	 * @param _descripcion String
	 * @param _menu Menu
	 * @param _fecha_inicio Date
	 * @param _caducidad Date
	 * @param _descuento int
	 * @return nuevaOferta() Oferta
	 */
	@Hidden(where = Where.OBJECT_FORMS)
	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Oferta crearOferta(
			@Named("Nombre") @TypicalLength(30) @RegEx(validation = "[0-9a-zA-ZñÑáéíóúÁÉÍÓÚñÑ\\s]*") final String _nombre,
			@Named("Cantidad de Personas") @Optional final int _cantidad_personas,
			@Named("Descripción") @MultiLine(numberOfLines = 3) final String _descripcion,
			@Named("Menu") final Menu _menu,
			@Named("Fecha de Inicio") final Date _fecha_inicio,
			@Named("Caducidad") final Date _caducidad,
			@Named("Descuento (%)") final int _descuento) {
		return nuevaOferta(_nombre, _cantidad_personas, _descripcion, _menu,
				_fecha_inicio, _caducidad, _descuento);
	}

	/**
	 * Persiste una nueva Oferta
	 * @param _nombre String
	 * @param _cantidad_personas int
	 * @param _descripcion String
	 * @param _menu Menu
	 * @param _fecha_inicio Date
	 * @param _caducidad Date
	 * @param _descuento int
	 * @return oferta Oferta
	 */
	@Programmatic
	public Oferta nuevaOferta(final String _nombre,
			final int _cantidad_personas, final String _descripcion,
			final Menu _menu, final Date _fecha_inicio, final Date _caducidad,
			final int _descuento) {
		final Oferta oferta = newTransientInstance(Oferta.class);
		oferta.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		oferta.setCantidadPersonas(_cantidad_personas);
		oferta.setDescripcion(_descripcion);
		oferta.setMenu(_menu);
		oferta.setFechaInicio(_fecha_inicio);
		oferta.setCaducidad(_caducidad);
		oferta.setDescuento(_descuento);
		oferta.setBaja(false);
		persist(oferta);
		return oferta;
	}

	/**
	 * Carga en la UI una lista de nemues
	 * @return ListarMenues() List<Menu>
	 */
	@Programmatic
	public List<Menu> choices3CrearOferta() {
		return listarMenues();
	}

	/**
	 * Obtiene una lista de menues de alta
	 * @see dom.menu.MenuServicio.listarMenuesAlta()
	 * @return List<Menu>
	 */
	@Programmatic
	public List<Menu> listarMenues() {
		return menuServicio.listarMenuesAlta();
	}

	/**
	 * Inyeccion del servicio del Menu
	 */
	@Inject
	private MenuServicio menuServicio;

	final LocalDate fecha_actual = LocalDate.now();

	/**
	 * Valida la fecha de inicio y finalizacion de la Oferta
	 * @param _nombre String 
	 * @param _cantidad_personas int
	 * @param _descripcion String 
	 * @param _menu Menu
	 * @param _fecha_inicio Date
	 * @param _caducidad Date
	 * @param _descuento int
	 * @return String
	 */
	@Programmatic
	public String validateCrearOferta(String _nombre, int _cantidad_personas,
			String _descripcion, Menu _menu, Date _fecha_inicio,
			Date _caducidad, int _descuento) {
		if (_fecha_inicio.compareTo(fecha_actual.toDate()) < 0)
			return "La fecha de inicio debe ser mayor o igual a la fecha actual";
		if (_caducidad.compareTo(_fecha_inicio) < 0)
			return "La fecha de caducidad debe ser mayor o igual a la fecha de inicio de la oferta";
		return null;
	}

	/**
	 * Calcula el precio con un descuento determinado
	 * @param _oferta Oferta
	 * @see dom.oferta.Oferta.getDescuento()
	 * @return total double
	 */
	@Programmatic
	public double calcularDescuento(Oferta _oferta) {
		final double total = calcularTotal(_oferta);
		return (total - ((total / 100) * _oferta.getDescuento()));
	}

	/**
	 * Calcula el precio segun la cantidad de personas
	 * @param _oferta Oferta
	 * @see dom.menu.Menu.getPrecioFinal()
	 * @return double
	 */
	@Programmatic
	public double calcularTotal(Oferta _oferta) {
		return (_oferta.getMenu().getPrecioFinal())
				* _oferta.getCantidadPersonas();
	}

	/**
	 * Obtiene una lista de Ofertas que comiencen con determinada especificacion
	 * @param nombre String
	 * @return list<Oferta>
	 */
	@Programmatic
	public List<Oferta> completarOferta(final String nombre) {
		return allMatches(new QueryDefault<Oferta>(Oferta.class,
				"ofertasQueEmpiezan", "nombre", "(?i).*" + nombre + ".*"));
	}

	/**
	 * Obtiene una lista de Ofertas que se encuentren de alta
	 * @return List<Oferta>
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Oferta> listarOfertasAlta() {
		return allMatches(Oferta.class, new Predicate<Oferta>() {

			@Override
			public boolean apply(Oferta input) {
				// TODO Auto-generated method stub
				return input.getBaja() ? false : true;
			}
		});
	}

	/**
	 * Obtiene una lista de la totalidad de las Ofertas
	 * @return listaOfertas List<Oferta>
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Oferta> listarOfertasTodas() {
		final List<Oferta> listaOfertas = allInstances(Oferta.class);
		return listaOfertas;
	}
}
