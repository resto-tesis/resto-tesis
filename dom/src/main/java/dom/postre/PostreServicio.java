package dom.postre;

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

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@Named("Postre")
public class PostreServicio extends AbstractFactoryAndRepository{

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Postre crearPostre(@Named("Numero") final int numeroPostre,
			@Named("Descripcion") final String descripcionPostre, 
			@Named("Precio") final double precioPostre){
		return crearPostreNuevo(numeroPostre, descripcionPostre, precioPostre);
	}

	// }}
	@Hidden
	public Postre crearPostreNuevo(final int numeroPostre, final String descripcionPostre, final double precioPostre) {
		final Postre postre = newTransientInstance(Postre.class);
		postre.setNumeroPostre(numeroPostre);
		postre.setDescripcionPostre(descripcionPostre);
		postre.setPrecioPostre(precioPostre);
		persist(postre);
		return postre;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Postre> listarPostres() {
		final List<Postre> listapostres = allInstances(Postre.class);
		return listapostres;
	}
}
