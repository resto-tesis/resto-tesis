package dom.plato;

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

@Named("Plato")
public class PlatoServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Plato crearPlato(
			/* Parametros de Entrada */
			@RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ]*") @MaxLength(value = 25) @Named("Nombre") final String nombre,
			@Named("Tipo") final TipoDePlatoEnum unTipoDePlato,
			@Named("Condicion") final CondicionDePlatoEnum unaCondicion,
			@Optional @MultiLine(numberOfLines = 3) @Named("Descripcion") final String unaDescripcion,
			@Named("Precio") @MaxLength(value = 6) @Digits(integer=3, fraction=2) final BigDecimal unPrecio) {
		/* Empieza el metodo */
		return crearUnPlato(nombre, unTipoDePlato, unaCondicion,unaDescripcion, unPrecio);
	}

	@Hidden
	public Plato crearUnPlato(final String nombre, final TipoDePlatoEnum unTipoDePlato, final CondicionDePlatoEnum unaCondicion, final String unaDescripcion, final BigDecimal unPrecio) { 
		/* Empieza el Metodo*/
		final Plato unPlato = newTransientInstance(Plato.class);
		unPlato.setNombre(nombre.substring(0, 1).toUpperCase()+ nombre.substring(1));
		unPlato.setTipoDePlato(unTipoDePlato);
		unPlato.setCondicionDePlato(unaCondicion);
		unPlato.setDescripcion(unaDescripcion);
		unPlato.setPrecio(unPrecio.doubleValue());
		persist(unPlato);
		return unPlato;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Plato> listarPLatos() {
		final List<Plato> listaPlatos = allInstances(Plato.class);
		return listaPlatos;
	}

}
