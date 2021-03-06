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

import org.apache.isis.applib.ApplicationException;

/**
 * Clase que interpreta los errores del Correo
 * @author RestoTesis
 * @since 10/09/2014
 * @version 1.0.0
 */
public class CorreoException extends ApplicationException {

	public CorreoException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CorreoException(String msg) {
		super(msg);
	}

	public CorreoException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1L;

}
