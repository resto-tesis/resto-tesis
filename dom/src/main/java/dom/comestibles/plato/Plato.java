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

package dom.comestibles.plato;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.comestibles.Comestible;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public abstract class Plato extends Comestible {

	// {{ CondicionDePlato (property)
	private CondicionDePlatoEnum condicionDePlato;

	@Named("Condición")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "5")
	public CondicionDePlatoEnum getCondicionDePlato() {
		return condicionDePlato;
	}

	public void setCondicionDePlato(final CondicionDePlatoEnum condicionDePLato) {
		this.condicionDePlato = condicionDePLato;
	}

	// }}

}
