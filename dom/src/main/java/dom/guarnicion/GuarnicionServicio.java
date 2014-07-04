package dom.guarnicion;

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
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Optional;

@Named("Guarnicion")
public class GuarnicionServicio extends AbstractFactoryAndRepository{

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Guarnicion crearGuarnicion(
			@Named("Nombre") final String nombreGuarnicion,
			@Optional
			@MultiLine(numberOfLines = 3)
			@Named("Descripcion") final String descripcionGuarnicion, 
			@Named("Precio") final double precioGuarnicion){
		return crearGuarnicionNueva(nombreGuarnicion, descripcionGuarnicion, precioGuarnicion);
	}

	// }}
	@Hidden
	public Guarnicion crearGuarnicionNueva(final String nombreGuarnicion, final String descripcionGuarnicion, final double precioGuarnicion){
		final Guarnicion guarnicion = newTransientInstance(Guarnicion.class);
		guarnicion.setNombre(nombreGuarnicion);
		guarnicion.setDescripcion(descripcionGuarnicion);
		guarnicion.setPrecio(precioGuarnicion);
		persist(guarnicion);
		return guarnicion;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Guarnicion> listarGuarniciones() {
		final List<Guarnicion> listaguarniciones = allInstances(Guarnicion.class);
		return listaguarniciones;
	}
}