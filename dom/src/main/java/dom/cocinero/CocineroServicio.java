package dom.cocinero;

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

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@Named("Cocinero")
public class CocineroServicio extends AbstractFactoryAndRepository {

	public CocineroServicio() {
		// TODO Auto-generated constructor stub
	}

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Cocinero crearCocinero(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ]*") @MaxLength(value = 20) final String _apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ]*") @MaxLength(value = 20) final String _nombre,
			@Named("Documento") final long _dni,
			@Named("Fecha de Nacimiento") final Date _fechaNacimiento,
			@Named("Fecha de Ingreso") final Date _fechaIngreso) {
		return crearNuevoCocinero(_nombre, _apellido, _dni, _fechaNacimiento,
				_fechaIngreso);
	}

	@Hidden
	public Cocinero crearNuevoCocinero(final String _nombre,
			final String _apellido, final long _dni,
			final Date _fechaNacimiento, final Date _fechaIngreso) {
		final Cocinero cocineroNuevo = newTransientInstance(Cocinero.class);
		cocineroNuevo.setApellido(_apellido.substring(0, 1).toUpperCase()
				+ _apellido.substring(1));
		cocineroNuevo.setDocumento(_dni);
		cocineroNuevo.setFechadeIngreso(_fechaIngreso);
		cocineroNuevo.setFechadeNacimiento(_fechaNacimiento);
		cocineroNuevo.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		persist(cocineroNuevo);
		return cocineroNuevo;
	}

	@Named("Listar")
	@MemberOrder(sequence = "1")
	@ActionSemantics(Of.SAFE)
	public List<Cocinero> listarCocineros() {
		final List<Cocinero> listaCocinero = allInstances(Cocinero.class);
		return listaCocinero;
	}
}
