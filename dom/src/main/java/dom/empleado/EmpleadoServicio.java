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
import org.apache.isis.applib.annotation.Hidden;
import org.joda.time.LocalDate;

public class EmpleadoServicio extends AbstractFactoryAndRepository {

	public EmpleadoServicio() {
		// TODO Auto-generated constructor stub
	}

	final LocalDate fecha_actual = LocalDate.now();

	public String validarDocumento(final long _dni) {
		for (Empleado _empleado : listarEmpleados())
			return _dni == _empleado.getDocumento() ? "Ya existe el número de documento ingresado."
					: null;
		return null;
	}

	@Hidden
	public List<Empleado> listarEmpleados() {
		return allInstances(Empleado.class);
	}
}