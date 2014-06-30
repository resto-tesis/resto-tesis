package dom.mesa;

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Mesa {

	// {{ numeroMesa (property)
	private int numeroMesa;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(final int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	// {{ CapacidadMesa (property)
	private int capacidadMesa;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "")
	public int getCapacidadMesa() {
		return capacidadMesa;
	}

	public void setCapacidadMesa(final int capacidadMesa) {
		this.capacidadMesa = capacidadMesa;
	}

	// {{ EstadoMesa (property)
	private Estado estadoMesa;

	@Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public Estado getEstadoMesa() {
		return estadoMesa;
	}

	public void setEstadoMesa(final Estado estadoMesa) {
		this.estadoMesa = estadoMesa;
	}

	// }}

	@Named("Borrar")
	@Bulk
	@MemberOrder(name = "accionMesa", sequence = "1")
	public List<Mesa> borrar() {

		contenedor.removeIfNotAlready(this);

		return mesaServicio.listarMesas();
	}

	// {{ injected: DomainObjectContainer
	private DomainObjectContainer contenedor;

	public void injectDomainObjectContainer(
			final DomainObjectContainer container) {
		this.setContenedor(container);
	}

	public DomainObjectContainer getContenedor() {
		return contenedor;
	}

	public void setContenedor(DomainObjectContainer contenedor) {
		this.contenedor = contenedor;
	}

	/*
	 * Inyección del servicio
	 */
	private MesaServicio mesaServicio;

	public void injectarMesaServicio(final MesaServicio serviciomesa) {
		this.mesaServicio = serviciomesa;
	}

}
