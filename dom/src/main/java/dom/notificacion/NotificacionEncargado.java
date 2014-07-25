package dom.notificacion;

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

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.annotation.MemberOrder;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Queries({
	@Query(name = "notificacionesEncargado", language = "JDOQL", value = "SELECT FROM dom.notificacion.NotificacionEncargado")
	})
public class NotificacionEncargado {

	// {{ NotificacionesMozos (Collection)
	private List<NotificacionMozo> listaNotificacionesMozos = new ArrayList<NotificacionMozo>();

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public List<NotificacionMozo> getNotificacionesMozos() {
		return listaNotificacionesMozos;
	}

	public void setNotificacionesMozos(final List<NotificacionMozo> listaNotificacionesMozos) {
		this.listaNotificacionesMozos = listaNotificacionesMozos;
	}
	// }}

	// {{ NotificacionesCocineros (Collection)
	private List<NotificacionCocinero> listaNotificacionesCocineros = new ArrayList<NotificacionCocinero>();

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "2")
	public List<NotificacionCocinero> getNotificacionesCocineros() {
		return listaNotificacionesCocineros;
	}

	public void setNotificacionesCocineros(final List<NotificacionCocinero> listaNotificacionesCocineros) {
		this.listaNotificacionesCocineros = listaNotificacionesCocineros;
	}
	// }}


}
