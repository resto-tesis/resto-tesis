package dom.mozo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.empleado.Empleado;
import dom.mesa.Mesa;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Mozo extends Empleado {

	// {{ listamesas (Collection)
	private List<Mesa> listaMesas = new ArrayList<Mesa>();

	@MemberOrder(sequence = "1")
	public List<Mesa> getListamesas() {
		return listaMesas;
	}

	public void setListaMesas(final List<Mesa> listaMesas) {
		this.listaMesas = listaMesas;
	}

	// }}
	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public void injectDomainObjectContainer(
			final DomainObjectContainer container) {
		this.setContainer(container);
	}

	public DomainObjectContainer getContainer() {
		return contenedor;
	}

	public void setContainer(DomainObjectContainer container) {
		this.contenedor = container;
	}

	/*
	 * Inyección del servicio
	 */

	private MozoServicio mozoServicio;

	public void injectarMozoServicio(final MozoServicio serviciomozo) {
		this.mozoServicio = serviciomozo;
	}

	@Named("Borrar")
	@Bulk
	@MemberOrder(name = "accionMozo", sequence = "1")
	public List<Mozo> borrar() {

		contenedor.removeIfNotAlready(this);

		return mozoServicio.listarMozos();
	}

}
