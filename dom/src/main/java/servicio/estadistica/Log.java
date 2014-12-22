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

package servicio.estadistica;


import java.util.Date;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.value.DateTime;

import dom.producto.Producto;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Sequence(name = "secuenciaRegistro", strategy = SequenceStrategy.CONTIGUOUS)
public class Log {

	// {{ NumeroRegistro (property)
	private int numeroRegistro;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaRegistro")
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(final int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	// }}

	// {{ Producto (property)
	private Producto producto;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(final Producto producto) {
		this.producto = producto;
	}

	// }}

	// {{ Cantidad (property)
	private int cantidad;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad;
	}

	public void addToCantidad(final int cantidad) {
		this.cantidad += cantidad;
	}

	// }}

	// {{ Fecha (property)
	private Date fecha;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(final Date fecha) {
		this.fecha = fecha;
	}

	// }}

	public void nuevoRegistro(final Producto _producto, final int _cantidad) {
		setProducto(_producto);
		setCantidad(_cantidad);
		setFecha(new Date(new DateTime().getMillisSinceEpoch()));
	}

	@SuppressWarnings("unused")
	@Inject
	private static DomainObjectContainer contenedor;

}
