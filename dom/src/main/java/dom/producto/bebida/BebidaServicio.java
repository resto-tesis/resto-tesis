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
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;

import dom.producto.EstadoLogico;

@DomainService
public class BebidaServicio extends AbstractFactoryAndRepository {

	@Named("Bebida")
	@MemberOrder(name = "Crear", sequence = "1")
	public Bebida crearBebida(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 30) final String _nombre,
			@Named("Tipo de Bebida") @TypicalLength(10) final TipoBebidaEnum _tipo,
			@Named("Volumen") @Optional @TypicalLength(15) final VolumenBebidaEnum _volumen,
			@Named("Descripción") @Optional @MultiLine(numberOfLines = 3) final String _descripcion,
			@Named("Precio") @MaxLength(value = 5) @Digits(integer = 2, fraction = 2) final BigDecimal _precio,
			@Named("Habilitado") final EstadoLogico _estadoLogico) {
		return nuevaInstanciaBebida(_nombre, _tipo, _volumen, _descripcion,
				_precio, _estadoLogico);
	}

	@Hidden
	public Bebida nuevaInstanciaBebida(final String _nombre,
			final TipoBebidaEnum _tipo, final VolumenBebidaEnum _volumen,
			final String _descripcion, final BigDecimal _precio,
			final EstadoLogico _estadoLogico) {
		final Bebida nuevaBebida = new Bebida();
		nuevaBebida.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		nuevaBebida.setTipoBebida(_tipo);
		nuevaBebida.setVolumen(_volumen);
		nuevaBebida.setDescripcion(_descripcion);
		nuevaBebida.setPrecio(_precio.doubleValue());
		nuevaBebida.setEstadoLogico(_estadoLogico);
		persist(nuevaBebida);
		return nuevaBebida;
	}

	public EstadoLogico default5CrearBebida() {
		// TODO Auto-generated method stub
		return EstadoLogico.Habilitado;
	}

	@Hidden
	public List<Bebida> completarBebidas(final String nombre) {
		return allMatches(new QueryDefault<Bebida>(Bebida.class,
				"bebidasQueEmpiezan", "nombre", "(?i).*" + nombre + ".*"));
	}

	public TipoBebidaEnum default1CrearBebida() {
		return TipoBebidaEnum.Gaseosa;
	}

	public VolumenBebidaEnum default2CrearBebida() {
		return default1CrearBebida().volumen().get(0);
	}

	public List<VolumenBebidaEnum> choices2CrearBebida(final String nombre,
			final TipoBebidaEnum tipoBebida) {
		return VolumenBebidaEnum.listar(tipoBebida);
	}

	public String validateCrearBebida(final String _nombre,
			final TipoBebidaEnum _tipoBebida,
			final VolumenBebidaEnum _volumenBebida, final String _descripcion,
			final BigDecimal _precio, final EstadoLogico _estadoLogico) {
		return VolumenBebidaEnum.validate(_tipoBebida, _volumenBebida);
	}

	@MemberOrder(name = "Listar", sequence = "2")
	@ActionSemantics(Of.SAFE)
	@Named("Bebidas")
	public List<Bebida> listarBebidas() {
		final List<Bebida> lista_bebidas = allInstances(Bebida.class);
		return lista_bebidas;
	}

	// // Se verifica que el elemento por borrar no este relacionado con ninguna
	// // comanda o menu
	// @Hidden
	// public boolean validaBorrado(final Bebida _bebida) {
	// return (firstMatch(Menu.class, new Predicate<Menu>() {
	// @Override
	// public boolean apply(Menu _menu) {
	// // TODO Auto-generated method stub
	// return _menu.getBebida().equals(_bebida);
	// }
	// }) != null) ? false : (firstMatch(ComandaBebida.class,
	// new Predicate<ComandaBebida>() {
	// @Override
	// public boolean apply(ComandaBebida _comanda) {
	// // TODO Auto-generated method stub
	// for (Bebida bebida : _comanda.getBebidas())
	// return bebida.equals(_bebida);
	// return false;
	// }
	// }) != null) ? false : true;
	// }
}