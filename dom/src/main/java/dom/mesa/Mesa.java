package dom.mesa;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Mesa extends AbstractDomainObject {

	// {{ numeroMesa (property)
	private int numeroMesa;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(final int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	// {{ CapacidadMesa (property)
	private int capacidadMesa;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@MemberOrder(sequence = "")
	public int getCapacidadMesa() {
		return capacidadMesa;
	}

	public void setCapacidadMesa(final int capacidadMesa) {
		this.capacidadMesa = capacidadMesa;
	}

	// {{ EstadoMesa (property)
	private Estado estadoMesa;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public Estado getEstadoMesa() {
		return estadoMesa;
	}

	public void setEstadoMesa(final Estado estadoMesa) {
		this.estadoMesa = estadoMesa;
	}
	// }}

}
