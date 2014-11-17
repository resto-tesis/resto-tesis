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

package dom.producto.plato;
/**
 * Enumerado que le dara al posibilidad de implementar la condicion de frio o caliente
 * a los platos elaborados
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
public enum CondicionDePlatoEnum {

	Caliente("Caliente"), Frio("Frio");

	private final String nombre;

	/**
	 * Permite obtener el nombre de la condicion
	 * @return nombre String
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setea el nombre de la condicion
	 * @param _nombre String
	 */
	private CondicionDePlatoEnum(String _nombre) {
		nombre = _nombre;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

}
