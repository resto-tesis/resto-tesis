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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optional;

import dom.oferta.Oferta;
import dom.persona.Persona;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroCliente", strategy = SequenceStrategy.CONTIGUOUS)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Cliente extends Persona implements IObservador{

	// {{ NumeroCliente (property)
	private long numeroCliente;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroCliente")
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public long getNumeroCliente() {
		return numeroCliente;
	}

	public void setNumeroCliente(final long numeroCliente) {
		this.numeroCliente = numeroCliente;
	}

	// }}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ (int) (numeroCliente ^ (numeroCliente >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (numeroCliente != other.numeroCliente)
			return false;
		return true;
	}

	// {{ Oferta (property)
	private Oferta oferta;

	@Optional
	@MemberOrder(sequence = "1")
	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(final Oferta oferta) {
		this.oferta = oferta;
	}
	// }}
	
	@Hidden
	@Override
	public void actualizar(Oferta _oferta) {
		// TODO Auto-generated method stub
		this.setOferta(_oferta);
	}
	
}
