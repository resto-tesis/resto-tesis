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

package dom.comandaBebida;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

import dom.comanda.Comanda;
import dom.comestible.bebida.Bebida;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@ObjectType("ComandaBebida")
public class ComandaBebida extends Comanda {

	public ComandaBebida() {

	}
	
	public String title(){
		return "Comanda Bebida";
	}

	// {{ Bebidas (Collection)
	private List<Bebida> bebidas = new ArrayList<Bebida>();

	@Render(Type.EAGERLY)
	public List<Bebida> getBebidas() {
		return bebidas;
	}

	public void setBebidas(final List<Bebida> bebidas) {
		this.bebidas = bebidas;
	}

	// }}

	@MemberOrder(name = "bebidas", sequence = "1")
	public ComandaBebida agregarBebida(final Bebida bebida) {
		getBebidas().add(bebida);
		return this;
	}

	@MemberOrder(name = "bebidas", sequence = "1")
	public ComandaBebida quitarBebida(final Bebida bebida) {
		getBebidas().remove(bebida);
		return this;
	}

	public List<Bebida> choices0QuitarBebida() {
		return getBebidas();
	}

}
