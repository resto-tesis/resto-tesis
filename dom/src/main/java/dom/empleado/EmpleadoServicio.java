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

package dom.empleado;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDate;

import com.google.common.base.Predicate;

/**
 * Clase que implenta la funcionalidad al Empleado
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@DomainService(menuOrder = "10")
public class EmpleadoServicio extends AbstractFactoryAndRepository {

	public EmpleadoServicio() {
		// TODO Auto-generated constructor stub
	}

	final LocalDate fecha_actual = LocalDate.now();

	/**
	 * Metodo para validar la existencia de un empleado por su dni 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	 * @param long _dni
	 */
	@Programmatic
	public String validarDocumento(final long _dni) {
		for (Empleado _empleado : listarEmpleadosTodos())
			return _dni == _empleado.getDocumento() ? "Ya existe el número de documento ingresado."
					: null;
		return null;
	}

	/**
	 * Metodo para obtener la lista de Empleados 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	*/
	@Programmatic
	public List<Empleado> listarEmpleadosTodos() {
		return allInstances(Empleado.class);
	}

	@Programmatic
	public List<Empleado> listarEmpleadosAlta() {
		return allMatches(Empleado.class, new Predicate<Empleado>() {

			@Override
			public boolean apply(Empleado input) {
				// TODO Auto-generated method stub
				return input.getBaja() ? false : true;
			}
		});
	}
}
