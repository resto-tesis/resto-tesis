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

package dom.cliente;

import dom.oferta.Oferta;
/**
 * Interface para actualizar las Ofertas 
 * @author RestoTesis
 * @since 10/07/2014
 * @version 1.0.0
 */
public interface IObservador {
	/**
	 * Metodo a implementar para actualizar la oferta 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	 * @param _oferta
	 */
	public void actualizar(Oferta _oferta);
}
