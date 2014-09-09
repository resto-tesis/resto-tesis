
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

package dom.absComanda;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Columns;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.mesa.Mesa;
import dom.mozo.Mozo;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Sequence(name = "secuenciaNumeroComanda", strategy = SequenceStrategy.CONTIGUOUS)
public abstract class AbsComanda {
	
	// {{ Numero (property)
		private int numero;

		@Named("Número")
		@TypicalLength(3)
		@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroComanda")
		@Disabled
		@Column(allowsNull = "false")
		@MemberOrder(sequence = "1")
		public int getNumero() {
			return numero;
		}

		public void setNumero(final int numeroComanda) {
			this.numero = numeroComanda;
		}

		// }}


		// {{ Mesa (property)
		private Mesa mesa;

		@Disabled
		@Title(prepend = "Comanda ")
		@MemberOrder(sequence = "3")
		@Column(allowsNull = "false")
		public Mesa getMesa() {
	        return mesa;
		}

		public void setMesa(final Mesa mesa) {
			this.mesa = mesa;
		}
		// }}
		
		// {{ Mozo (property)
		/*
				private Mozo mozo;

				@Disabled
				@Title(prepend = "Comanda ")
				@MemberOrder(sequence = "2")
				@Column(allowsNull = "false")
				public Mozo getMozo() {
			        return mozo;
				}

				public void setMozo(final Mozo mozo) {
					this.mozo = mozo;
				}*/
				// }}
				
		// {{ FechaDePedido (property)
		private Date fechaDePedido;

		@MemberOrder(sequence = "1")
		@Column(allowsNull="false")
		public Date getFechaDePedido() {
			return fechaDePedido;
		}

		public void setFechaDePedido(final Date fechaDePedido) {
			this.fechaDePedido = fechaDePedido;
		}
		// }}
		
		


}