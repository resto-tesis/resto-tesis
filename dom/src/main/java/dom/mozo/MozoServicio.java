package dom.mozo;

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

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.filter.Filter;

import dom.mesa.Mesa;
import dom.mesa.MesaServicio;

@Named("Mozo")
public class MozoServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mozo crearMozo(@Named("Apellido") final String apellido,
			@Named("Nombre") final String nombre,
			@Named("Documento") final long documento,
			@Named("Fecha de Nacimiento") final Date fechaDeNacimiento,
			@Named("Fecha de Ingreso") final Date fechaDeIngreso) {
		return crearNuevoMozo(apellido, nombre, documento, fechaDeIngreso,
				fechaDeNacimiento);
	}

	@Hidden
	public Mozo crearNuevoMozo(final String apellido, final String nombre,
			final long documento, final Date fechaDeIngreso,
			final Date fechaDeNacimiento) {
		final Mozo mozo = newTransientInstance(Mozo.class);
		mozo.setApellido(apellido);
		mozo.setNombre(nombre);
		mozo.setDocumento(documento);
		mozo.setFechadeIngreso(fechaDeIngreso);
		mozo.setFechadeNacimiento(fechaDeNacimiento);
		persist(mozo);
		return mozo;
	}




	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Mozo> listarMozos() {
		final List<Mozo> listamozos = allInstances(Mozo.class);
		return listamozos;
	}

	@Hidden
	public List<Mesa> listaDeMesas(){
		return allInstances(Mesa.class);
	}
	@Hidden 
	public Mesa devolverMesa(final int numeroDeMesa){
		return firstMatch(Mesa.class, new Filter<Mesa>() {
        	@Override
            public boolean accept(final Mesa m) {
                return m.getNumeroMesa() == numeroDeMesa;
            }
        });
	}
	

}
