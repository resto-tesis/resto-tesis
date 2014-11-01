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

package dom.vista.comanda;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

import com.google.common.base.Predicate;

import dom.comanda.Comanda;

@Named("Comandas")
@DomainService
public class ServicioComandasView extends AbstractFactoryAndRepository {

	public ComandasView mostrarComandas() {
		return newViewModelInstance(ComandasView.class, "comandas");
	}

	@Programmatic
	public List<Comanda> listarComandasEnEsp_EnPrep() {
		return allMatches(Comanda.class, new Predicate<Comanda>() {

			@Override
			public boolean apply(Comanda input) {
				// TODO Auto-generated method stub
				return input.getEstado() == input.getEnEspera()
						|| input.getEstado() == input.getEnPreparacion() ? true
						: false;
			}
		});
	}

	@Programmatic
	public List<Comanda> listarComandasTodas() {
		return allInstances(Comanda.class);
	}
}
