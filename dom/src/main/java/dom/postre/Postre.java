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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.postre.Postre;
import dom.postre.PostreServicio;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Postre {

	// {{ NumeroPostre (property)
	private int numeroPostre;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumeroPostre() {
		return numeroPostre;
	}

	public void setNumeroPostre(final int numeroPostre) {
		this.numeroPostre = numeroPostre;
	}
	// }}

    // {{ DescripcionPostre (property)
	private String descripcionPostre;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public String getDescripcionPostre() {
		return descripcionPostre;
	}

	public void setDescripcionPostre(final String descripcionPostre) {
		this.descripcionPostre = descripcionPostre;
	}
	// }}

	// {{ PrecioPostre (property)
	private double precioPostre;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "3")
	public double getPrecioPostre() {
		return precioPostre;
	}

	public void setPrecioPostre(final double precioPostre) {
		this.precioPostre = precioPostre;
	}
	// }}
	
	@Named("Borrar")
	@Bulk
	@MemberOrder(name = "accionPostre", sequence = "1")
	public List<Postre> borrar() {

		contenedor.removeIfNotAlready(this);

		return postreServicio.listarPostres();
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
	private PostreServicio postreServicio;

	public void injectarPostreServicio(final PostreServicio serviciopostre) {
		this.postreServicio = serviciopostre;
	}

}
