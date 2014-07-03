package dom;

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

import org.apache.isis.applib.annotation.MemberOrder;

public class Comanda {

	public Comanda() {
		// TODO Auto-generated constructor stub
	}
	
	// {{ numeroComanda (property)
	private int numeroComanda;

	@MemberOrder(sequence = "1")
	public int getnumeroComanda() {
		return numeroComanda;
	}

	public void setnumeroComanda(final int numeroComanda) {
		this.numeroComanda = numeroComanda;
	}
	// }}





}
