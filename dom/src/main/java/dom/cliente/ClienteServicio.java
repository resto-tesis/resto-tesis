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

import com.google.common.base.Predicate;

import dom.oferta.Oferta;
import dom.persona.Persona;
import dom.usuario.Rol;
import dom.usuario.Usuario;

/**
 * Contiene la funcionalidad para Cargar/Listar un nuevo Cliente
 * 
 * @author RestoTesis
 * @since 10/06/2014
 * @version 1.0.0
 */
@DomainService(menuOrder = "11")
@Named("Cliente")
public class ClienteServicio extends AbstractFactoryAndRepository {

	public String iconName() {
		return "Cliente";
	}

	/**
	 * Obtiene los datos validados del Cliente
	 * 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	 * @return nuevoCliente
	 */
	@Named("Registrar")
	@MemberOrder(sequence = "1")
	public Cliente cargarCliente(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _nombre,
			@Named("Documento") @RegEx(validation = "[0-9*") @MaxLength(value = 8) @MinLength(value = 7) final long _dni,
			@Named("Direccion") @MultiLine(numberOfLines = 2) final String _direccion,
			@Named("Telefono Fijo") @RegEx(validation = "\\d{7,11}") @Optional @MaxLength(value = 15) final String _telefono,
			@Named("Celular") @RegEx(validation = "\\d{3,7}(-)?\\d{6}") @Optional @MaxLength(value = 15) final String _celular,
			@Named("Correo Electronico") @RegEx(validation = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+") @Optional final String _correo,
			@Named("Usuario") final String _nombreUsuario,
			@Named("Contraseña") final Password _password) {
		Oferta _oferta = null;
		return nuevoCliente(_oferta, _apellido, _nombre, _dni, _direccion,
				_telefono, _celular, _correo,
				crearUsuario(_nombreUsuario, _password));
	}

	/**
	 * Realiza la validacion del ingreso del cliente
	 * 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	 */

	public String validateCargarCliente(final String _apellido,
			final String _nombre, final long _dni, final String _direccion,
			final String _telefono, final String _celular,
			final String _correo, final String _nombreUsuario,
			final Password _password) {
		if (firstMatch(Cliente.class, new Predicate<Cliente>() {

			@Override
			public boolean apply(Cliente _cliente) {
				// TODO Auto-generated method stub
				return (_cliente.getDocumento() == _dni) ? true : false;
			}
		}) != null) {
			return "Ya existe un cliente con el dni ingresado!";
		}
		if (firstMatch(Persona.class, new Predicate<Persona>() {

			@Override
			public boolean apply(Persona _persona) {
				// TODO Auto-generated method stub
				return _persona.getUsuario().getNombre().equals(_nombreUsuario);
			}
		}) != null)
			return "Ya existe el nombre de usuario!";
		return _telefono.length() == 0 && _celular.length() == 0 ? "Debe ingresar al menos un teléfono"
				: null;
	}

	/**
	 * Crea Usuario y Password para el nuevo cliente
	 * 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	 * @return usuario
	 */
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

	/**
	 * Persiste un nuevo Cliente
	 * 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	 * @return clienteNuevo
	 */
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
		clienteNuevo.setBaja(false);
		persist(clienteNuevo);
		return clienteNuevo;
	}

	/**
	 * Obtiene una lista de clientes Activos
	 * 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Cliente> listarClientesAlta() {
		return allMatches(Cliente.class, new Predicate<Cliente>() {

			@Override
			public boolean apply(Cliente input) {
				// TODO Auto-generated method stub
				return input.getBaja() ? false : true;
			}
		});
	}

	/**
	 * Obtiene una lista de todos los clientes
	 * 
	 * @author RestoTesis
	 * @since 10/06/2014
	 * @version 1.0.0
	 */
	@Named("Listar Todos")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Cliente> listarClientesTodos() {
		final List<Cliente> listaDeClientes = allInstances(Cliente.class);
		return listaDeClientes;
	}
}
