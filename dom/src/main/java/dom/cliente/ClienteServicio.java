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

package dom.cliente;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MaxLength;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Password;

import dom.oferta.Oferta;
import dom.usuario.Rol;
import dom.usuario.Usuario;

@DomainService(menuOrder = "11")
@Named("Cliente")
public class ClienteServicio extends AbstractFactoryAndRepository {

	@Named("Registrar")
	@MemberOrder(sequence = "1")
	public Cliente cargarCliente(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _nombre,
			@Named("Documento") @RegEx(validation = "[0-9*") @MaxLength(value = 8) @MinLength(value = 7) final long _dni,
			@Named("Direccion") @MultiLine(numberOfLines = 2) final String _direccion,
			@Named("Telefono") @RegEx(validation = "\\d{7,10}") @Optional @MaxLength(value = 15) final String _telefono,
			@Named("Celular") @RegEx(validation = "\\d{3,7}(-)?\\d{6}") @Optional @MaxLength(value = 15) final String _celular,
			@Named("Correo Electronico") @RegEx(validation = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+") @Optional final String _correo,
			@Named("Usuario") final String _nombreUsuario,
			@Named("Contraseña") final Password _password) {
		Oferta _oferta = null;
		return nuevoCliente(_oferta, _apellido, _nombre, _dni, _direccion,
				_telefono, _celular, _correo,
				crearUsuario(_nombreUsuario, _password));
	}

	@Programmatic
	public Usuario crearUsuario(final String _nombreUsuario,
			final Password _password) {
		final Usuario usuario = newTransientInstance(Usuario.class);
		usuario.setNombre(_nombreUsuario);
		usuario.setPassword(_password.getPassword());
		usuario.setRol(uniqueMatch(new QueryDefault<Rol>(Rol.class,
				"cliente-role")));
		persistIfNotAlready(usuario);
		return usuario;
	}

	@Programmatic
	public Cliente nuevoCliente(Oferta _oferta, final String _apellido,
			final String _nombre, final long _dni, final String _direccion,
			final String _telefono, final String _celular,
			final String _correo, final Usuario _usuario) {
		final Cliente clienteNuevo = newTransientInstance(Cliente.class);
		clienteNuevo.setApellido(_apellido.substring(0, 1).toUpperCase()
				+ _apellido.substring(1));
		clienteNuevo.setNombre(_nombre.substring(0, 1).toUpperCase()
				+ _nombre.substring(1));
		clienteNuevo.setDocumento(_dni);
		clienteNuevo.setDireccion(_direccion);
		clienteNuevo.setTelefono(_telefono);
		clienteNuevo.setCelular(_celular);
		clienteNuevo.setCorreo(_correo);
		clienteNuevo.setUsuario(_usuario);
		clienteNuevo.setOferta(_oferta);
		persist(clienteNuevo);
		return clienteNuevo;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Cliente> listarClientes() {
		final List<Cliente> listaDeClientes = allInstances(Cliente.class);
		return listaDeClientes;
	}
}
