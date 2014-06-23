package dom.mesa;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Mesa extends AbstractDomainObject {

	// {{ numeroMesa (property)
	private int numeroMesa;

	@MemberOrder(sequence = "1")
	public int getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(final int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	// {{ CapacidadMesa (property)
	private int capacidadMesa;

	@MemberOrder(sequence = "")
	public int getCapacidadMesa() {
		return capacidadMesa;
	}

	public void setCapacidadMesa(final int capacidadMesa) {
		this.capacidadMesa = capacidadMesa;
	}

	// {{ CondicionMesa (property)
	private Estado condicionMesa;

	@MemberOrder(sequence = "")
	public Estado getCondicionMesa() {
		return condicionMesa;
	}

	public void setCondicionMesa(final Estado condicionMesa) {
		this.condicionMesa = condicionMesa;
	}
	// }}



	
	// }}

	// // {{ listaComandas (Collection)
	// private List<Comanda> listaComandas = new ArrayList<Comanda>();
	//
	// @MemberOrder(sequence = "1")
	// public List<Comanda> getlistaComandas() {
	// return listaComandas;
	// }
	//
	// public void setlistaComandas(final List<Comanda> listaComandas) {
	// this.listaComandas = listaComandas;
	// }
	//
	// // }}
	//
	// // {{ cantidadDeComensales (property)
	// private int cantidadDeComensales;
	//
	// @MemberOrder(sequence = "1")
	// public int getcantidadDeComensales() {
	// return cantidadDeComensales;
	// }
	//
	// public void setcantidadDeComensales(final int cantidadDeComensales) {
	// this.cantidadDeComensales = cantidadDeComensales;
	// }
	//

	// private DomainObjectContainer contenedor;
	//
	// public void injectDomainObjectContainer(
	// final DomainObjectContainer container) {
	// this.setContainer(container);
	// }
	//
	// public DomainObjectContainer getContainer() {
	// return contenedor;
	// }
	//
	// public void setContainer(final DomainObjectContainer container) {
	// this.contenedor = container;
	// }
	//
	// private MesaServicio mesaServicio;
	//
	// public void injectarMesaServicio(MesaServicio mesaServicio) {
	// this.mesaServicio = mesaServicio;
	// }
}
