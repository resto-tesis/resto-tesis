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

package dom.producto.bebida;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.producto.ProductoNoElaborado;
/**
 * Entidad que representa las bebidas que se podran consumir en el local, 
 * extiende de ProductoNoElaborado
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Query(name = "bebidasQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.bebida.Bebida WHERE nombre.matches(:nombre)")
@AutoComplete(repository = BebidaServicio.class, action = "completarBebidas")
public class Bebida extends ProductoNoElaborado {

	/**
	 * Obtiene el nombre del icono de la bebida.
	 * @return String
	 */
	public String iconName() {
		return getBaja() ? "BebidaFriaDes" : "BebidaFria";
	}

	// {{ Tipo (property)
	private TipoBebidaEnum tipoBebida;

	/**
	 * Obtiene el tipo de bebida del enumerado
	 * @return tipoBebida TipoBebidaEnum
	 */
	@TypicalLength(10)
	@Optional
	@MemberOrder(sequence = "2")
	public TipoBebidaEnum getTipoBebida() {
		return tipoBebida;
	}

	/**
	 * Setea el tipo de bebida 
	 * @param tipoBebida TipoBebidaEnum
	 */
	public void setTipoBebida(final TipoBebidaEnum tipoBebida) {
		this.tipoBebida = tipoBebida;
	}

	// {{ Volumen (property)
	private VolumenBebidaEnum volumen;

	/**
	 * Obtiene el volumen de la bebida del enumerado
	 * @return tipoBebida VolumenBebidaEnum
	 */
	@TypicalLength(15)
	@Optional
	@MemberOrder(sequence = "4")
	public VolumenBebidaEnum getVolumen() {
		return volumen;
	}

	/**
	 * Setea el volumen de la bebida 
	 * @param tipoBebida VolumenBebidaEnum
	 */
	public void setVolumen(final VolumenBebidaEnum volumen) {
		this.volumen = volumen;
	}

	/**
	 * Inyeccion del contenedor
	 */
	@Inject
	private DomainObjectContainer contenedor;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((tipoBebida == null) ? 0 : tipoBebida.hashCode());
		result = prime * result + ((volumen == null) ? 0 : volumen.hashCode());
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
		Bebida other = (Bebida) obj;
		if (tipoBebida != other.tipoBebida)
			return false;
		if (volumen != other.volumen)
			return false;
		return true;
	}

}
