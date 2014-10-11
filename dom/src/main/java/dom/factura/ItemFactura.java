package dom.factura;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ItemFactura {

	public ItemFactura() {
		// TODO Auto-generated constructor stub
	}

	// {{ Nombre (property)
	private String nombre;

	@Title
	@Hidden(where = Where.ALL_TABLES)
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ Precio (property)
	private double precio;

	@Named("Precio ($)")
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(final double precio) {
		this.precio = precio;
	}

	// }}

	// {{ Descuento (property)
	private int descuento;

	@Named("Descuento (%)")
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "false")
	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(final int descuento) {
		this.descuento = descuento;
	}

	// }}

	@Named("Precio Final ($)")
	@MemberOrder(sequence = "5")
	public double getPrecioFinal() {
		return precio - (precio / 100 * descuento);
	}

}
