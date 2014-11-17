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

import org.apache.isis.applib.value.Password;
import org.joda.time.LocalDate;
/**
 * Interface para realizar validaciones  
 * @author RestoTesis
 * @since 10/06/2014
 * @version 1.0.0
 */
public interface IValidacionEmpleado {
	/**
	 * Metodo a implementar para realizar la Validacion de existencia por dni, fecha, edad, telefono del ingreso por UI
	 * @param _nombre String
	 * @param _apellido String
	 * @param _dni long
	 * @param _direccion String
	 * @param _telefono String
	 * @param _celular String
	 * @param _correo String
	 * @param fechadeNacimiento LocalDate
	 * @param fechadeIngreso LocalDate
	 * @param _nombreUsuario String
	 * @param _password Password
	 */
	public String validateCrear(final String _nombre, final String _apellido,
			final long _dni, final String _direccion, final String _telefono,
			final String _celular, final String _correo,
			final LocalDate fechadeNacimiento, final LocalDate fechadeIngreso,
			String _nombreUsuario, Password _password);

	/**
	 * Metodo a implementar para validar la mayoria de edad
	 * @param fechadeNacimiento LocalDate
	 */
	public boolean validaMayorEdad(final LocalDate fechadeNacimiento);

	/**
	 * Metodo a implementar para obtener la cantidad de dias entre la fecha de nacimiento y la fecha actual
	 * @param fechadeNacimiento LocalDate
	 */
	public int getDiasNacimiento_Hoy(final LocalDate fechadeNacimiento);

}
