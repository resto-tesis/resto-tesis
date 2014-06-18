package dom.mesa;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

@Named("Mesa")
public class MesaServicio extends AbstractFactoryAndRepository {

	public MesaServicio() {
		// TODO Auto-generated constructor stub

	}

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mesa crearMesa(@Named("Numero") final int numero,
			@Named("Capacidad de la Mesa") final int capacidadMesa) {
		return crearMesaNueva(numero, capacidadMesa); // TODO: business logic here
	}
	// }}
	@Hidden
	public Mesa crearMesaNueva(final int numero,final int capacidadMesa){
		final Mesa mesa=newTransientInstance(Mesa.class);
		mesa.setcapacidadMesa(capacidadMesa);
		mesa.setNumeroMesa(numero);
		return mesa;
	}
}
