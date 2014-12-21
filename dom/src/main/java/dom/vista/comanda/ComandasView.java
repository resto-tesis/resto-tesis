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

import javax.inject.Inject;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

import dom.comanda.Comanda;

/**
 * Crea una vista Cocina, donde se pueden listar las Comandas
 * en estado En_espera y En_Preparacion
 * @author RestoTesis
 * @since 10/11/2014
 * @version 1.0.0
 */
@MemberGroupLayout(columnSpans = { 6, 0, 6 }, left = { "lista" })
public class ComandasView extends AbstractViewModel {

	/**
	 * Asigna el nombre al icono
	 * @return String
	 */
	public String iconName()
	{
		return "Cocina";
	}
	/**
	 * Asigan el nombre al titulo
	 * @return String
	 */
	public String title() {
		return "Cocina";
	}

	private String memento;

	@Override
	public String viewModelMemento() {
		// TODO Auto-generated method stub
		return memento;
	}

	@Override
	public void viewModelInit(String memento) {
		// TODO Auto-generated method stub
		this.memento = memento;
	}

	/**
	 * Obtiene una Lista de Comandas en estado En_Espera y En_Preparacion
	 * @return List<Comanda>
	 */
	@Named("Comandas")
	@Render(Type.EAGERLY)
	@MemberOrder(name = "lista", sequence = "1")
	public List<Comanda> getListaComandas() {
		return servicioComandasView.listarComandasEnEsp_EnPrep();

	}

	/**
	 * Inyeccion del servicio de ComandasView
	 */
	@Inject
	private ServicioComandasView servicioComandasView;

}
