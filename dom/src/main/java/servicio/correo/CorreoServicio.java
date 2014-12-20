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
package servicio.correo;

import javax.inject.Inject;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;

import servicio.ofertaPrint.OfertaPrinting;
import dom.cliente.Cliente;
import dom.oferta.Oferta;

/**
 * Clase que permite enviar a los clientes las oferts mail
 * @author RestoTesis
 * @since 10/09/2014
 * @version 1.0.0
 */
@DomainService
public class CorreoServicio extends AbstractFactoryAndRepository {
	
	@Inject
	private OfertaPrinting printing;
	
	
	@NotInServiceMenu
	@Named("Enviar Correo")
	public String send(final Cliente unCliente, final Oferta unaOferta) {

		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthentication("resto.tesis@gmail.com", "modica1234");
			email.setSSLOnConnect(true);
			email.setFrom("resto.tesis@gmail.com", "Resto Tesis");
			email.setSubject("Ofertas para esta Semana!");
			email.setMsg(printing.ofertaToText(unaOferta));			
			email.addTo(unCliente.getCorreo());
			return email.send();
		} catch (EmailException e) {
			throw new servicio.correo.CorreoException(e.getMessage(), e);
		}
	}
}
