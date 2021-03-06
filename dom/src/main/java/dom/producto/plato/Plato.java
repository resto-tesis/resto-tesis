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
 * 
 */

package dom.producto.plato;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.producto.ProductoElaborado;
/**
 * Entidad que le dara al posibilidad de implementar la condicion de frio o caliente
 * a los platos que se sirvan a los clientes, extiende de ProductoElaborado
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public abstract class Plato extends ProductoElaborado {

	// {{ CondicionDePlato (property)
	private CondicionDePlatoEnum condicionDePlato;

	/**
	 * Permite obtener la condicion del un plato
	 * @return condicionDePlato String
	 */
	@Named("Condición")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	public CondicionDePlatoEnum getCondicionDePlato() {
		return condicionDePlato;
	}

	/**
	 * Setea la condicion del un plato
	 * @param condicionDePlato CondicionDePlatoEnum
	 */
	public void setCondicionDePlato(final CondicionDePlatoEnum condicionDePLato) {
		this.condicionDePlato = condicionDePLato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((condicionDePlato == null) ? 0 : condicionDePlato.hashCode());
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
		Plato other = (Plato) obj;
		if (condicionDePlato != other.condicionDePlato)
			return false;
		return true;
	}

}
