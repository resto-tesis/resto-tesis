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

package dom.comanda;

import java.util.Date;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService
public class ComandaServicio extends AbstractFactoryAndRepository {

	public ComandaServicio() {
		// TODO Auto-generated constructor stub
	}

	@Programmatic
	public Comanda crearComanda() {
		final Comanda comanda = newTransientInstance(Comanda.class);
		comanda.setFechaDePedido(new Date());
		comanda.setMozo(getUser().getName());
		persist(comanda);
		return comanda;
	}
}
