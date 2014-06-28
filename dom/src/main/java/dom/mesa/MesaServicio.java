package dom.mesa;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@Named("Mesa")
public class MesaServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mesa crearMesa(@Named("Numero") final int numero,
			@Named("Capacidad") final int capacidadMesa,
			@Named("Estado") final Estado estadoMesa) {
		return crearMesaNueva(numero, capacidadMesa, estadoMesa); // TODO: business logic
														// here
	}

	// }}
	@Hidden
	public Mesa crearMesaNueva(final int numero, final int capacidadMesa, final Estado estadoMesa) {
		final Mesa mesa = newTransientInstance(Mesa.class);
		mesa.setCapacidadMesa(capacidadMesa);
		mesa.setNumeroMesa(numero);
		mesa.setEstadoMesa(estadoMesa);
		persist(mesa);
		return mesa;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Mesa> listarMesas() {
		final List<Mesa> listamesas = allInstances(Mesa.class);
		return listamesas;
	}

}
