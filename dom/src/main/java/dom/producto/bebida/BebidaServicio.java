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

package dom.producto.bebida;

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
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;

import com.google.common.base.Predicate;
/**
 * Clase que da la funcionalidad de crear, persistir, listar, obtener tipos y volumenes
 * a la clase bebida
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@DomainService
@Named("Bebida")
public class BebidaServicio extends AbstractFactoryAndRepository {

	/**
	 * Obtiene el nombre del icono de la bebida.
	 * @return String
	 */
	public String iconName(){
		return "BebidaFria";
	}
	
	/**
	 * Obtiene de la UI los datos validados de la bebida a crear
	 * @param _nombre String
	 * @param _tipo TipoBebidaEnum
	 * @param _volumen VolumenBebidaEnum
	 * @param _descripcion String
	 * @param _precio Bigdecimal
	 * @return nuevaInstanciaBebida() Bebida
	 */
	@Named("Bebida")
	@MemberOrder(name = "Crear", sequence = "1")
	public Bebida crearBebida(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*") @MaxLength(value = 30) final String _nombre,
			@Named("Tipo de Bebida") @TypicalLength(10) final TipoBebidaEnum _tipo,
			@Named("Volumen") @Optional @TypicalLength(15) final VolumenBebidaEnum _volumen,
			@Named("Descripción") @Optional @MultiLine(numberOfLines = 3) final String _descripcion,
			@Named("Precio") @MaxLength(value = 5) @Digits(integer = 2, fraction = 2) final BigDecimal _precio) {
		return nuevaInstanciaBebida(_nombre, _tipo, _volumen, _descripcion,
				_precio);
	}

	/**
	 * Toma los valores de crearBebida() y los persiste
	 * @param _nombre String
	 * @param _tipo TipoBebidaEnum
	 * @param _volumen VolumenBebidaEnum
	 * @param _descripcion String
	 * @param _precio Bigdecimal
	 * @return
	 */
	@Programmatic
	public Bebida nuevaInstanciaBebida(final String _nombre,
			final TipoBebidaEnum _tipo, final VolumenBebidaEnum _volumen,
			final String _descripcion, final BigDecimal _precio) {
		final Bebida nuevaBebida = new Bebida();
		nuevaBebida.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		nuevaBebida.setTipoBebida(_tipo);
		nuevaBebida.setVolumen(_volumen);
		nuevaBebida.setDescripcion(_descripcion);
		nuevaBebida.setPrecio(_precio.doubleValue());
		nuevaBebida.setBaja(false);
		persist(nuevaBebida);
		return nuevaBebida;
	}

	/**
	 * Obtiene una lista de bebidas, que empiecen con un determinado parametro
	 * @param nombre String
	 * @return list<Bebidas>
	 */
	@Programmatic
	public List<Bebida> completarBebidas(final String nombre) {
		return allMatches(new QueryDefault<Bebida>(Bebida.class,
				"bebidasQueEmpiezan", "nombre", "(?i).*" + nombre + ".*"));
	}

	/**
	 * Obtiene por defecto un bebida del tipo gaseosa
	 */
	public TipoBebidaEnum default1CrearBebida() {
		return TipoBebidaEnum.Gaseosa;
	}

	/**
	 * Crea por defecto un bebida del tipo gaseosa con su volumen
	 */
	public VolumenBebidaEnum default2CrearBebida() {
		return default1CrearBebida().volumen().get(0);
	}

	/**
	 * Permite seleccionar las Bibidas de un mismo nombre y tipo, sin importar el volumen
	 * @param nombre String 
	 * @param tipoBebida TipoBebidaEnum
	 * @return List<VolumenBebidaEnum>
	 */
	public List<VolumenBebidaEnum> choices2CrearBebida(final String nombre,
			final TipoBebidaEnum tipoBebida) {
		return VolumenBebidaEnum.listar(tipoBebida);
	}

	/**
	 * Valida la creacion de una nueva Bebida
	 * @param _nombre String
	 * @param _tipoBebida TipoBebidaEnum
	 * @param _volumenBebida VolumenBebidaEnum
	 * @param _descripcion String
	 * @param _precio BigDecimal
	 * @return String
	 */
	public String validateCrearBebida(final String _nombre,
			final TipoBebidaEnum _tipoBebida,
			final VolumenBebidaEnum _volumenBebida, final String _descripcion,
			final BigDecimal _precio) {
		return VolumenBebidaEnum.validate(_tipoBebida, _volumenBebida);
	}

	/**
	 * Lista todas las Bebidas
	 * @return lista_bebidas List<Bebidas>
	 */
	@MemberOrder(name = "Listar", sequence = "2")
	@ActionSemantics(Of.SAFE)
	@Named("Bebidas")
	public List<Bebida> listarBebidasTodas() {
		final List<Bebida> lista_bebidas = allInstances(Bebida.class);
		return lista_bebidas;
	}

	/**
	 * Lista todas las Bebidas de alta
	 * @return List<Bebidas>
	 */
	@MemberOrder(name = "Listar", sequence = "2")
	@ActionSemantics(Of.SAFE)
	@Named("Bebidas")
	public List<Bebida> listarBebidasAlta() {
		return allMatches(Bebida.class, new Predicate<Bebida>() {

			@Override
			public boolean apply(Bebida input) {
				// TODO Auto-generated method stub
				return input.getBaja() ? false : true;
			}
		});
	}
}
