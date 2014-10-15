package dom.factura;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Sequence;
import javax.jdo.annotations.SequenceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.TypicalLength;

@Sequence(name = "secuenciaNumeroFactura", strategy = SequenceStrategy.CONTIGUOUS)
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Factura {

	public Factura() {
		// TODO Auto-generated constructor stub
	}

	// {{ Numero (property)
	private long numero;

	@Title(prepend = "Factura Nº ")
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "secuenciaNumeroFactura")
	@Named("Número")
	@TypicalLength(3)
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public long getNumero() {
		return numero;
	}

	public void setNumero(final long numero) {
		this.numero = numero;
	}

	// }}

	// {{ Total (property)
	private double total;

	@Named("Total ($)")
	@Disabled
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public double getTotal() {
		return total;
	}

	public void setTotal(final double total) {
		this.total = total;
	}

	// }}

	// {{ Items (Collection)
	private List<ItemFactura> items = new ArrayList<ItemFactura>();

	@Join
	@Named("Detalle")
	@Render(Type.EAGERLY)
	@MemberOrder(sequence = "1")
	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(final List<ItemFactura> items) {
		this.items = items;
	}

	// }}

	public void addToItems(final ItemFactura _item) {
		items.add(_item);
	}

	public void removeFromItems(final ItemFactura _item) {
		items.remove(_item);
	}

	@Inject
	private DomainObjectContainer contenedor;
}
