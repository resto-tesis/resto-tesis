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

import org.joda.time.LocalDate;

public interface ICocineroServicio {
	public String validateCrearCocinero(final String _nombre,
			final String _apellido, final long _dni,
			final LocalDate fechadeNacimiento, final LocalDate fechadeIngreso,
			String _nombreUsuario, String _password);

	public boolean validaMayorEdad(final LocalDate fechadeNacimiento);

	public int getDiasNacimiento_Hoy(final LocalDate fechadeNacimiento);
}
