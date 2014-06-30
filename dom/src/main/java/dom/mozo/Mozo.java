package dom.mozo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
<<<<<<< HEAD
=======
import javax.jdo.annotations.PersistenceCapable;
>>>>>>> 6c7b933bcbe42381e02ebfae761b3d85681de17b

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
<<<<<<< HEAD
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
=======
>>>>>>> 6c7b933bcbe42381e02ebfae761b3d85681de17b

import dom.empleado.Empleado;
import dom.mesa.Mesa;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Mozo extends Empleado {

<<<<<<< HEAD
 // {{ Lista De Mesas (Collection)
	
private List<Mesa> listamesas = new ArrayList<Mesa>();
=======
	// {{ listamesas (Collection)
	private List<Mesa> listaMesas = new ArrayList<Mesa>();
>>>>>>> 6c7b933bcbe42381e02ebfae761b3d85681de17b

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

@Named("Asignar Mesas a Mozo")
@Bulk
@MemberOrder(name="accionMesaMozo", sequence = "2")
public  Mozo asignarMesasMozo(@Named("Numero de Mesa")final int numeroDeMesa){
	
	this.listamesas.add(mozoServicio.devolverMesa(numeroDeMesa));
	return  this;
	
}

}
