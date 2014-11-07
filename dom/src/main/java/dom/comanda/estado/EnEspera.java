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

package dom.comanda.estado;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;

import dom.comanda.Comanda;

/**
 * Estado Particular de la Comanda que implementa la Interface IEstadoComanda
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class EnEspera implements IEstadoComanda {

	private String mensajeEstadoActual = "Comanda en Espera";

	public EnEspera(Comanda _comanda) {
		// TODO Auto-generated constructor stub
		comanda = _comanda;
	}

	public String iconName(){
		return "PedidoEnEspera2";
	}
	
	// {{ Comanda (property)
	private Comanda comanda;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "true")
	public Comanda getComanda() {
		return comanda;
	}

	public void setComanda(final Comanda comanda) {
		this.comanda = comanda;
	}

	// }}

	@Override
	public String title() {
		return "En Espera";
	}

	/**
	 * Metodo para cambiar el estado de la Comanda
	 * @author RestoTesis
	 * @since 10/05/2014
	 * @version 1.0.0
	 */
	@Override
	@Programmatic
	public void cambiarEstado() {
		// TODO Auto-generated method stub
		getComanda().setEstado(getComanda().getEnPreparacion());
	}

	@Override
	@Programmatic
	public String Enviar() {
		// TODO Auto-generated method stub
		return mensajeEstadoActual;
	}

	@Override
	@Programmatic
	public String Preparar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Programmatic
	public String ComandaLista() {
		// TODO Auto-generated method stub
		return mensajeEstadoActual;
	}

	@Override
	@Programmatic
	public String validarModificacion() {
		// TODO Auto-generated method stub
		return null;
	}
}