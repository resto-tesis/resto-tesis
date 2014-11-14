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

package dom.encargado;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import dom.empleado.Empleado;

/**
 * Entidad Encargado la cual representa un empleado del comercio, que desarrollara
 * funciones especificas de encargado de los demas empleados, extiende de la clase Empleado
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Encargado extends Empleado {
	/**
	 * Retorna el nombre del icono segun el Encargado esta dado de baja/alta
	 * @see dom.persona.Persona.getBaja()
	 * @return Strnig
	 */
	public String iconName() {
		return getBaja() ? "EncargadoBaja" : "Encargado";
	}
}
