package dom.encargado;

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
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@Named("Encargado")
public class EncargadoServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Encargado crearEncargado(
			@Named("Apellido:") final String apellidoEncargado,
			@Named("Nombre:") final String nombreEncargado,
			@Named("Documento:") final long documentoEncargado,
			@Named("Fecha de Nacimiento:") final Date fechadeNacimientoEncargado,
			@Named("Fecha de Encargado:") final Date fechadeIngresoEncargado) {
		return crearEncargadoNuevo(apellidoEncargado, nombreEncargado,
				documentoEncargado, fechadeNacimientoEncargado,
				fechadeIngresoEncargado);
	}

	// }}
	@Hidden
	public Encargado crearEncargadoNuevo(final String apellidoEncargado,
			final String nombreEncargado, final long documentoEncargado,
			final Date fechadeNacimientoEncargado,
			final Date fechadeIngresoEncargado) {
		final Encargado encargado = newTransientInstance(Encargado.class);
		encargado.setApellido(apellidoEncargado);
		encargado.setNombre(nombreEncargado);
		encargado.setDocumento(documentoEncargado);
		encargado.setFechadeNacimiento(fechadeNacimientoEncargado);
		encargado.setFechadeIngreso(fechadeIngresoEncargado);
		persist(encargado);
		return encargado;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Encargado> listarEncargados() {
		final List<Encargado> listaencargados = allInstances(Encargado.class);
		return listaencargados;
	}
}
