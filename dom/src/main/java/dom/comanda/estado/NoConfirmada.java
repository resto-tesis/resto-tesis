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

import dom.comanda.Comanda;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class NoConfirmada implements IEstadoComanda {

	private String mensajeEstadoActual = "Comanda no Confirmada";

	public NoConfirmada(Comanda _comanda) {
		// TODO Auto-generated constructor stub
		comanda = _comanda;
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
		// TODO Auto-generated method stub
		return "No Confirmada";
	}

	@Override
	public void cambiarEstado() {
		// TODO Auto-generated method stub
		getComanda().setEstado(getComanda().getEnEspera());
	}

	@Override
	public String Enviar() {
		// TODO Auto-generated method stub
		if (!getComanda().getBebidas().isEmpty())
			return null;
		if (!getComanda().getPostres().isEmpty())
			return null;
		if (!getComanda().getGuarniciones().isEmpty())
			return null;
		if (!getComanda().getPlatosEntrada().isEmpty())
			return null;
		if (!getComanda().getPlatosPrincipales().isEmpty())
			return null;
		return "Comanda vacia";
	}

	@Override
	public String Preparar() {
		// TODO Auto-generated method stub
		return mensajeEstadoActual;
	}

	@Override
	public String ComandaFinalizada() {
		// TODO Auto-generated method stub
		return mensajeEstadoActual;
	}

}