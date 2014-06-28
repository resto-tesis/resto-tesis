package dom.cocinero;

import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.empleado.Empleado;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Cocinero extends Empleado {

	public Cocinero() {
		// TODO Auto-generated constructor stub
	}

	// {{ Contenedor (property)
	private DomainObjectContainer contenedor;

	public void injectDomainObjectContainer(
			final DomainObjectContainer _contenedor) {
		contenedor = _contenedor;
	}

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContainer(DomainObjectContainer container) {
		contenedor = container;
	}

	private CocineroServicio servicioCocinero;

	public void injectarCocineroServicio(CocineroServicio _servicioCocinero) {
		servicioCocinero = _servicioCocinero;
	}

	@Named("Borrar")
	@Bulk
	@MemberOrder(name = "accionCocinero", sequence = "1")
	public List<Cocinero> borrarCocinero() {
		contenedor.removeIfNotAlready(this);
		return servicioCocinero.listarCocineros();
	}
}
