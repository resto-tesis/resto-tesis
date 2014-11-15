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

import javax.inject.Inject;

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
import dom.oferta.OfertaServicio;
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

	/**
	 * Retorna el nombre del icono para el cliente
	 * 
	 * @return String
	 */
	public String iconName() {
		return "Cliente";
	}

	/**
	 * Obtiene los datos validados del Cliente
	 * 
	 * @param String
	 *            _apellido
	 * @param String
	 *            _nombre
	 * @param long _dni
	 * @param String
	 *            _direccion
	 * @param String
	 *            _telefono
	 * @param String
	 *            _celular
	 * @param String
	 *            _correo
	 * @param String
	 *            _nombreUsusario
	 * @param Password
	 *            _password
	 * @param Oferta
	 *            _oferta
	 * @return Cliente nuevoCliente
	 */
	@Named("Registrar")
	@MemberOrder(sequence = "1")
	public Cliente cargarCliente(
			@Named("Apellido") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _apellido,
			@Named("Nombre") @RegEx(validation = "[a-zA-ZáéíóúÁÉÍÓÚ\\s]*") @MaxLength(value = 20) final String _nombre,
			@Named("Documento") @RegEx(validation = "[0-9*") @MaxLength(value = 8) @MinLength(value = 7) final long _dni,
			@Named("Direccion") @MultiLine(numberOfLines = 2) @Optional final String _direccion,
			@Named("Telefono Fijo") @RegEx(validation = "\\d{7,11}") @Optional @MaxLength(value = 15) final String _telefono,
			@Named("Celular") @RegEx(validation = "\\d{3,7}(-)?\\d{6}") @Optional @MaxLength(value = 15) final String _celular,
			@Named("Correo Electronico") @RegEx(validation = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+") @Optional final String _correo,
			@Named("Usuario") final String _nombreUsuario,
			@Named("Contraseña") final Password _password) {
		return nuevoCliente(_apellido, _nombre, _dni, _direccion, _telefono,
				_celular, _correo, crearUsuario(_nombreUsuario, _password));
	}

	/**
	 * Realiza la validacion del ingreso del cliente por Dni y valida al menos
	 * un numero de Telefono
	 * 
	 * @param String
	 *            _apellido
	 * @param String
	 *            _nombre
	 * @param long _dni
	 * @param String
	 *            _direccion
	 * @param String
	 *            _telefono
	 * @param String
	 *            _celular
	 * @param String
	 *            _correo
	 * @param String
	 *            _nombreUsusario
	 * @param Password
	 *            _password
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
		return _telefono == null && _celular == null ? "Debe ingresar al menos un teléfono"
				: null;
	}

	/**
	 * Crea Usuario y Password para el nuevo cliente
	 * 
	 * @param String
	 *            _nombreUsuario
	 * @param Password
	 *            _password
	 * @return Usuario usuario
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
	 * Toma el cliente ingresado y lo persiste
	 * 
	 * @param String
	 *            _apellido
	 * @param String
	 *            _nombre
	 * @param long _dni
	 * @param String
	 *            _direccion
	 * @param String
	 *            _telefono
	 * @param String
	 *            _celular
	 * @param String
	 *            _correo
	 * @param Usuario
	 *            _usuario
	 * @param Password
	 *            _password
	 * @param Cliente
	 *            clienteNuevo
	 * @return Cliente clienteNuevo
	 */
	@Programmatic
	public Cliente nuevoCliente(final String _apellido, final String _nombre,
			final long _dni, final String _direccion, final String _telefono,
			final String _celular, final String _correo, final Usuario _usuario) {
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
		clienteNuevo.setBaja(false);
		persist(clienteNuevo);
		return clienteNuevo;
	}

	/**
	 * Obtiene una lista de clientes Activos
	 * 
	 * @return List<Cliente>
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

	@Programmatic
	public List<Oferta> listarOfertas() {
		return ofertaServicio.listarOfertasAlta();
	}

	@Inject
	private OfertaServicio ofertaServicio;

	/**
	 * Obtiene una lista de todos los clientes
	 * 
	 * @return List<Cliente> listaDeClientes
	 */
	@Named("Listar Todos")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Cliente> listarClientesTodos() {
		final List<Cliente> listaDeClientes = allInstances(Cliente.class);
		return listaDeClientes;
	}
}
