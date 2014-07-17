package dom.bebida;

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
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

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

@Named("Bebida")
public class BebidaServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Bebida crearBebida(
			@Named("Nombre") @RegEx(validation = "[0-9a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _nombre,
			@Named("Volumen") @TypicalLength(15) final VolumenBebidaEnum _volumen,
			@Named("Descripción") @Optional @MultiLine(numberOfLines = 3) final String _descripcion,
			@Named("Precio") @MaxLength(value = 5) @Digits(integer = 2, fraction = 2) final BigDecimal _precio) {
		return nuevaInstanciaBebida(_nombre, _volumen, _descripcion, _precio);
	}

	@Hidden
	public Bebida nuevaInstanciaBebida(final String _nombre, final VolumenBebidaEnum _volumen,
			final String _descripcion, final BigDecimal _precio) {
		final Bebida nuevaBebida = new Bebida();
		nuevaBebida.setNombre(_nombre.substring(0, 1).toUpperCase() + _nombre.substring(1));
		nuevaBebida.setVolumen(_volumen);
		nuevaBebida.setDescripcion(_descripcion);
		nuevaBebida.setPrecio(_precio.doubleValue());
		persist(nuevaBebida);
		return nuevaBebida;
	}

	@MemberOrder(sequence = "2")
	@ActionSemantics(Of.SAFE)
	@Named("Listar")
	public List<Bebida> listarBebidas() {
		return allInstances(Bebida.class);
	}
}
