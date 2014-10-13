package dom.producto;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ProductoCantidad {

	public ProductoCantidad() {
		// TODO Auto-generated constructor stub
	}

	// {{ Producto (property)
	private Producto producto;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(final Producto producto) {
		this.producto = producto;
	}

	// }}

	// {{ Cantidad (property)
	private int cantidad;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad;
	}
	// }}

}
