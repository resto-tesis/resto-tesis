package dom.encargado;

import java.util.List;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

import dom.empleado.Empleado;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)

public class Encargado extends Empleado{
	@Named("Borrar")
    @Bulk
    @MemberOrder(name="accionEncargado", sequence = "1")
    public List<Encargado> borrar() {
    	
    	contenedor.removeIfNotAlready(this);
    	
    	return encargadoServicio.listarEncargados();
    }

    //{{ injected: DomainObjectContainer
    private DomainObjectContainer contenedor;

    public void injectDomainObjectContainer(final DomainObjectContainer container) {
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

	private EncargadoServicio encargadoServicio;

	public void injectarEncargadoServicio(final EncargadoServicio servicioencargado) {
        this.encargadoServicio = servicioencargado;
	}
}
