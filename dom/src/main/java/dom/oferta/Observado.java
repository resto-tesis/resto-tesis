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

package dom.oferta;

/**
 * Clase abstracta que se utiliza para que una vez modificada la Oferta,
 * se notifique a los clientes de los cambios, a implemenatar en Oferta.
 * @author RestoTesis
 * @since 15/10/2014
 * @version 1.0.0
 */
public abstract class Observado {

	/**
	 * Contructor de la clase
	 */
	public Observado() {
	}

	/**
	 * Metodo a implementar, para cada observador se invoca el método actualizar()
	 * a los clientes registrados, implementa Observado
	 * @author RestoTesis
	 * @since 15/10/2014
	 * @version 1.0.0
	 */
	public abstract Observado notificarClientes();

}


