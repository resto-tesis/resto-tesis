package dom.bebida;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
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

	public BebidaServicio() {
		// TODO Auto-generated constructor stub
	}

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Bebida crearBebida(
			@Named("Nombre") final String _nombre,
			@Named("Descripcion") @Optional @MultiLine(numberOfLines = 3) final String _descripcion,
			@Named("Precio") final double _precio) {
		return nuevaInstanciaBebida(_nombre, _descripcion, _precio);
	}

	@Hidden
	public Bebida nuevaInstanciaBebida(final String _nombre,
			final String _descripcion, final double _precio) {
		final Bebida nuevaBebida = new Bebida();
		nuevaBebida.setNombre(_nombre);
		nuevaBebida.setDescripcion(_descripcion);
		nuevaBebida.setPrecio(_precio);
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
