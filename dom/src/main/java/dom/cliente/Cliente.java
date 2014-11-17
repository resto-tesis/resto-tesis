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

import java.util.List;

import javax.inject.Inject;
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
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.TypicalLength;

import servicio.correo.CorreoServicio;
import dom.oferta.Oferta;
import dom.persona.Persona;

/**
 * Entidad Cliente la cual representa a cualquier persona que consuma 
 * productos dentro del local, extiende de la clase Persona, implementa IObservador
 * Entidad Cliente la cual representa a cualquier persona que consuma productos
 * dentro del local, extiende de la clase Persona
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroCliente", strategy = SequenceStrategy.CONTIGUOUS)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Cliente extends Persona implements IObservador {

	/**
	 * Obtiene el nombre del icono segun el cliente este dado de baja/alta
	 * 
	 * @see dom.persona.Persona.getBaja();
	 * @return String
	 */
	public String iconName() {
		return getBaja() ? "ClienteDes" : "Cliente";
	}

	// {{ NumeroCliente (property)
	private long numeroCliente;

	/**
	 * Retorna el numero de Cliente que se va a crear
	 * @return numeroCliente long
	 * @return long numeroCliente
	 */
	@TypicalLength(5)
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroCliente")
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public long getNumeroCliente() {
		return numeroCliente;
	}

	/**
	 * Setea el numero de Cliente que se va a crear.
	 * @param numeroCliente long
	 * @param long numeroCliente
	 */
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

	/**
	 * Retorna la Oferta que se va a crear.
	 * @return oferta Oferta
	 */
	@Hidden
	@Optional
	@MemberOrder(sequence = "1")
	public Oferta getOferta() {
		return oferta;
	}

	/**
	 * Setea la Oferta que se va a crear.
	 * @param oferta Oferta
	 */
	public void setOferta(final Oferta oferta) {
		this.oferta = oferta;
	}
	//
	// // {{ Oferta (property)
	// private Oferta oferta;
	//
	// /**
	// * Retorna la Oferta que se va a crear.
	// *
	// * @return Oferta oferta
	// */
	// @Hidden
	// @Optional
	// @MemberOrder(sequence = "1")
	// public Oferta getOferta() {
	// return oferta;
	// }
	//
	// /**
	// * Setea la Oferta que se va a crear.
	// *
	// * @param Oferta
	// * oferta
	// */
	// public void setOferta(final Oferta oferta) {
	// this.oferta = oferta;
	// }

	@Inject
	private CorreoServicio correoServicio;

	@Inject
	private ClienteServicio clienteServicio;

	/**
	 * Metodo para actualizar la oferta
	 * @param _oferta Oferta
	 */
	@Named("Enviar Oferta")
	@Override
	public Cliente actualizar(final Oferta _oferta) {
		// TODO Auto-generated method stub
		// this.setOferta(_oferta);
		correoServicio.send(this, _oferta);
		return this;
	}

	public List<Oferta> choices0Actualizar() {
		return clienteServicio.listarOfertas();
	}
}
