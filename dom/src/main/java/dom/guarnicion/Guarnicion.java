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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaNumeroGuarnicion", strategy = SequenceStrategy.CONTIGUOUS)
public class Guarnicion {

	// {{ Numero (property)
	private int  numeroGuarnicion;

	@TypicalLength(3)
	@Disabled
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroGuarnicion")
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int  getNumero() {
		return numeroGuarnicion;
	}

	public void setNumero(final int  numeroGuarnicion) {
		this.numeroGuarnicion = numeroGuarnicion;
	}
	// }}
	// {{ Nombre (property)
	private String nombreGuarnicion;

	@Title
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getNombre() {
		return nombreGuarnicion;
	}

	public void setNombre(final String nombreGuarnicion) {
		this.nombreGuarnicion = nombreGuarnicion;
	}
	// }}
	// {{ Descripcion (property)
	private String descripcionGuarnicion;

	@MultiLine(numberOfLines = 3)
	@Optional
	@MemberOrder(sequence = "3")
	public String getDescripcion() {
		return descripcionGuarnicion;
	}

	public void setDescripcion(final String descripcionGuarnicion) {
		this.descripcionGuarnicion = descripcionGuarnicion;
	}
	// }}
	// {{ Precio (property)
	private double precioGuarnicion;

	@TypicalLength(5)
	@Column(allowsNull = "false")
	@MemberOrder(sequence = "4")
	public double getPrecio() {
		return precioGuarnicion;
	}

	public void setPrecio(final double precioGuarnicion) {
		this.precioGuarnicion = precioGuarnicion;
	}
	// }}

	@Named("Borrar")
	@Bulk
	@MemberOrder(name = "accionGuarnicion", sequence = "1")
	public List<Guarnicion> borrar() {

		contenedor.removeIfNotAlready(this);

		return guarnicionServicio.listarGuarniciones();
	}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public void injectDomainObjectContainer(final DomainObjectContainer container) {
		this.setContenedor(container);
	}

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	/*
	 * Inyección del servicio
	 */
	private GuarnicionServicio guarnicionServicio;

	public void injectarGuarnicionServicio(final GuarnicionServicio servicioguarnicion) {
		this.guarnicionServicio = servicioguarnicion;
	}
}
