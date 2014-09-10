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

package dom.usuario;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Permiso {

	public Permiso() {
		// TODO Auto-generated constructor stub
	}

	// {{ Permiso (property)
	private String permiso;

	@Title
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(final String permiso) {
		this.permiso = permiso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permiso == null) ? 0 : permiso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permiso other = (Permiso) obj;
		if (permiso == null) {
			if (other.permiso != null)
				return false;
		} else if (!permiso.equals(other.permiso))
			return false;
		return true;
	}

	// }}
	
	

}
