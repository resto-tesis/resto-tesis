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

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.producto.Producto;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Query(name = "bebidasQueEmpiezan", language = "JDOQL", value = "SELECT FROM dom.bebida.Bebida WHERE nombre.matches(:nombre)")
@AutoComplete(repository = BebidaServicio.class, action = "completarBebidas")
public class Bebida extends Producto {

	// {{ Tipo (property)
	private TipoBebidaEnum tipoBebida;

	@TypicalLength(10)
	@Optional
	@MemberOrder(sequence = "2")
	public TipoBebidaEnum getTipoBebida() {
		return tipoBebida;
	}

	public void setTipoBebida(final TipoBebidaEnum tipoBebida) {
		this.tipoBebida = tipoBebida;
	}

	// }}

	// {{ Volumen (property)
	private VolumenBebidaEnum volumen;

	@TypicalLength(15)
	@Optional
	@MemberOrder(sequence = "4")
	public VolumenBebidaEnum getVolumen() {
		return volumen;
	}

	public void setVolumen(final VolumenBebidaEnum volumen) {
		this.volumen = volumen;
	}

	// }}

	@Named("Borrar")
	@Bulk
	@MemberOrder(sequence = "1")
	public List<Bebida> borrarBebida() {
//		if (bebidaServicio.validaBorrado(this))
			contenedor.removeIfNotAlready(this);
//		else
			contenedor.informUser("Existe un Menu o Comanda dependiente!!");
		return bebidaServicio.listarBebidas();
	}

	// {{ injected: DomainObjectContainer
	@Inject
	private DomainObjectContainer contenedor;

	/*
	 * Inyección del servicio
	 */
	@Inject
	private BebidaServicio bebidaServicio;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tipoBebida == null) ? 0 : tipoBebida.hashCode());
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
