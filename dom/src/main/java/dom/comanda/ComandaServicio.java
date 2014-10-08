package dom.comanda;

import java.util.Date;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService
public class ComandaServicio extends AbstractFactoryAndRepository {

	public ComandaServicio() {
		// TODO Auto-generated constructor stub
	}

	@Programmatic
	public Comanda crearComanda() {
		final Comanda comanda = newTransientInstance(Comanda.class);
		comanda.setFechaDePedido(new Date());
		comanda.setMozo(getUser().getName());
		persist(comanda);
		return comanda;
	}
}
