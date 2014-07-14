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
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;

import dom.mesa.EstadoAsignacionMesaEnum;
import dom.mesa.Mesa;

@Named("Mozo")
public class MozoServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mozo crearMozo(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ]*") @MaxLength(value = 20) final String apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ]*") @MaxLength(value = 20) final String nombre,
			@Named("Documento") @RegEx(validation = "[0-9*") @MaxLength(value = 8)final long _dni,
			@Named("Fecha de Nacimiento") final Date fechaDeNacimiento,
			@Named("Fecha de Ingreso") final Date fechaDeIngreso) {
		return crearNuevoMozo(apellido, nombre, _dni, fechaDeIngreso,
				fechaDeNacimiento);
	}

	@Hidden
	public Mozo crearNuevoMozo(final String apellido, final String nombre,
			final long documento, final Date fechaDeIngreso,
			final Date fechaDeNacimiento) {
		final Mozo mozo = newTransientInstance(Mozo.class);
		mozo.setApellido(apellido.substring(0, 1).toUpperCase()
				+ apellido.substring(1));
		mozo.setNombre(nombre.substring(0, 1).toUpperCase()
				+ nombre.substring(1));
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
	public List<Mesa> listarMesaSinAsignar() {
		return allMatches(
                new QueryDefault<Mesa>(Mesa.class, 
                        "mesasSinAsignar"                        
                        ));
	}
	
	@Hidden
	public List<Mesa> listaMesasSeleccionadas() {
		return allMatches(
                new QueryDefault<Mesa>(Mesa.class, 
                        "mesasSeleccionadas" 
                        ));			
	}

	@Hidden
	public Mozo asignarMesas(Mozo _mozo) {
		List<Mesa> listaMesas = listaMesasSeleccionadas();
		if (!listaMesas.isEmpty()) {
			for (Mesa _mesa : listaMesas) {
				_mesa.setEstadoAsignacion(EstadoAsignacionMesaEnum.Asignada);
				_mesa.setEstadoSeleccion(false);
				_mozo.addMesa(_mesa);
			}
			getContainer().informUser("Mesas asignadas.");
		} else {
			getContainer().informUser(
					"No hay Mesas seleccionadas para su asignación");
		}
		return _mozo;
	}

}
