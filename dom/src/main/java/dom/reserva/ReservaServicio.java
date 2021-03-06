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

package dom.reserva;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Where;
import com.google.common.base.Predicate;
import dom.cliente.Cliente;
import dom.mesa.Mesa;

/**
 * Da la funcionalidad de cear, persistir y listar a la Clase Reserva 
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@DomainService
@Named("Reserva")
public class ReservaServicio extends AbstractFactoryAndRepository {

	/**
	 * Asigna el nombre al icono de la Reserva
	 * @return String 
	 */
	public String iconName() {
		return "Reserva";
	}

	/**
	 * Constructor de la clase
	 */
	public ReservaServicio() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Crea uan nueva Reserva
	 * @param _comensales int
	 * @param _mesa Mesa
	 * @param _fecha Date
	 * @param _hora String
	 * @return nuevaReserva() Reserva
	 */
	@Named("Crear")
	@Hidden(where = Where.OBJECT_FORMS)
	public Reserva crearReserva(@Named("Comensales") final int _comensales,
			@Named("Mesa") final Mesa _mesa, @Named("Fecha") final Date _fecha,
			@Named("Hora") final String _hora) {
		return nuevaReserva(_comensales, _mesa, _fecha, _hora,
				uniqueMatch(Cliente.class, new Predicate<Cliente>() {

					@Override
					public boolean apply(Cliente input) {
						// TODO Auto-generated method stub
						return input.getUsuario().getNombre()
								.equals(getUser().getName()) ? true : false;
					}
				}));
	}

	/**
	 * Asigan distintos Horarios al combo, para su leccion
	 * @return String
	 */
	public String[] choices3CrearReserva() {
		return new String[] { "11:30", "12:00", "12:30", "13:00", "13:30",
				"14:00", "14:30", "20:00", "20:30", "21:00", "21:30", "22:00",
				"22:30", "23:00", "23:30" };
	}

	/**
	 * Devuelve la lista de mesas para los combos
	 * @param _comensales int
	 * @return List<Mesa>
	 */
	public List<Mesa> choices1CrearReserva(final int _comensales) {
		return allMatches(Mesa.class, new Predicate<Mesa>() {
			@Override
			public boolean apply(Mesa input) {
				// TODO Auto-generated method stub
				return input.getCapacidad() >= _comensales
						&& input.getCapacidad() <= (_comensales + 2);
			}
		});
	}
	
	/**
	 * Crea una reserva desde el usuario Encargado
	 * @param _comensales int
	 * @param _mesa Mesa
	 * @param _fecha Date
	 * @param _hora String 
	 * @param unCliente Cliente
	 * @return nuevaReserva()
	 */
	@Named("Crear")
	@Hidden(where = Where.OBJECT_FORMS)
	public Reserva crearReservaEncargado(@Named("Comensales") final int _comensales,
			@Named("Mesa") final Mesa _mesa, @Named("Fecha") final Date _fecha,
			@Named("Hora") final String _hora,
			@Named("Cliente") final Cliente unCliente) {
		return nuevaReserva(_comensales, _mesa, _fecha, _hora,unCliente);
	}

	/**
	 * Asigan distintos Horarios al combo, para su leccion
	 * @return String
	 */
	public String[] choices3CrearReservaEncargado() {
		return new String[] { "11:30", "12:00", "12:30", "13:00", "13:30",
				"14:00", "14:30", "20:00", "20:30", "21:00", "21:30", "22:00",
				"22:30", "23:00", "23:30" };
	}

	/**
	 *  Devuelve la lista de mesas para los combos
	 * @param _comensales int
	 * @return List<Mesa>
	 */
	public List<Mesa> choices1CrearReservaEncargado(final int _comensales) {
		return allMatches(Mesa.class, new Predicate<Mesa>() {
			@Override
			public boolean apply(Mesa input) {
				// TODO Auto-generated method stub
				return input.getCapacidad() >= _comensales
						&& input.getCapacidad() <= (_comensales + 2);
			}
		});
	}

	/**
	 * Toam los datos obtenidos de la reserva y los persiste
	 * @param _comensales int
	 * @param _mesa Mesa
	 * @param _fecha Date
	 * @param _hora String 
	 * @param unCliente Cliente
	 * @return reserva Reserva
	 */
   @Programmatic
	public Reserva nuevaReserva(final int _comensales, final Mesa _mesa,
			final Date _fecha, final String _hora,
			final Cliente unCliente) {
		final Reserva reserva = newTransientInstance(Reserva.class);
		reserva.setMesa(_mesa);
		reserva.setComensales(_comensales);
		reserva.setFechaHora(sumaFechaHora(_fecha, _hora).getTime());
		reserva.setCliente(unCliente);
		persist(reserva);
		return reserva;
	}

   /**
    * Toma los valores ingresados de la fecha y hora, los suma y convierte en un sola variable
    * @param _fecha Date
    * @param _hora String
    * @return fechaHora Calendar
    */
	@Programmatic
	public Calendar sumaFechaHora(Date _fecha, String _hora) {
		Calendar fechaHora = Calendar.getInstance();
		fechaHora.setTime(_fecha);
		fechaHora.set(Calendar.HOUR_OF_DAY,
				Integer.parseInt(_hora.substring(0, 2)));
		fechaHora.set(Calendar.MINUTE, Integer.parseInt(_hora.substring(3, 5)));
		return fechaHora;
	}

	/**
	 * Valida la fecha ingresa, obtiene las reservas de la fecha de la mesa seleccionada,
	 * valida que el horario de reserva no interfiera con uno existente
	 * @param _comensales int
	 * @param _mesa Mesa
	 * @param _fecha Date
	 * @param _hora String 
	 * @return String
	 */
	public String validateCrearReserva(final int _comensales, final Mesa _mesa,
			final Date _fecha, final String _hora) {
		// se valida que la fecha ingresada sea posterior a la fecha actual.
		if (sumaFechaHora(_fecha, _hora).before(Calendar.getInstance()))
			return "La fecha y hora debe ser posterior a hoy";
		for (Reserva _reserva : allMatches(Reserva.class,
		// Se obtienen todas las reservas de la fecha
		// seleccionada correspondientes a la mesa
		// seleccionada.
				new Predicate<Reserva>() {
					@Override
					public boolean apply(Reserva input) {
						// TODO Auto-generated method stub
						Calendar calendarioInput = Calendar.getInstance();
						calendarioInput.setTime(input.getFechaHora());
						calendarioInput.set(Calendar.HOUR_OF_DAY, 0);
						calendarioInput.set(Calendar.MINUTE, 0);
						Calendar calendarioSeteado = sumaFechaHora(_fecha,
								_hora);
						calendarioSeteado.set(Calendar.HOUR_OF_DAY, 0);
						calendarioSeteado.set(Calendar.MINUTE, 0);
						if (input.getMesa().getNumero() == _mesa.getNumero())
							if (calendarioInput.compareTo(calendarioSeteado) == 0)
								return true;
						return false;
					}
				})) {
			Calendar calendarioReservado = Calendar.getInstance();
			calendarioReservado.setTime(_reserva.getFechaHora());
			Calendar calendarioSeteado = sumaFechaHora(_fecha, _hora);
			if (calendarioSeteado.get(Calendar.HOUR_OF_DAY) == calendarioReservado
					.get(Calendar.HOUR_OF_DAY))
				return "Mesa Reservada";
			calendarioReservado.add(Calendar.MINUTE, 30);
			if (calendarioReservado.compareTo(calendarioSeteado) == 0)
				return "Mesa Reservada";
			calendarioReservado.add(Calendar.MINUTE, -60);
			if (calendarioReservado.compareTo(calendarioSeteado) == 0)
				return "Mesa Reservada";
			calendarioReservado.add(Calendar.MINUTE, 90);
			if (calendarioReservado.compareTo(calendarioSeteado) == 0)
				return "Mesa Reservada";
			calendarioReservado.add(Calendar.MINUTE, -120);
			if (calendarioReservado.compareTo(calendarioSeteado) == 0)
				return "Mesa Reservada";
		}
		return null;
	}

	/**
	 * Permite listar todas las Resevas
	 * @return List<Reserva>
	 */
	@Named("Listar")
	public List<Reserva> listarReservasTodas() {
		return allInstances(Reserva.class);
	}

	/**
	 * Permite listar todas las reservas de un cliente
	 * @returnList<Reserva>
	 */
	@Named("Listar")
	public List<Reserva> listarReservas() {
		return allMatches(Reserva.class, new Predicate<Reserva>() {

			@Override
			public boolean apply(Reserva input) {
				// TODO Auto-generated method stub
				return input.getCliente().getUsuario().getNombre()
						.equals(getUser().getName()) ? true : false;
			}
		});
	}
}
