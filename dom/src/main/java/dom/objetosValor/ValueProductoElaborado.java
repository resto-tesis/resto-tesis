package dom.objetosValor;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.producto.ProductoElaborado;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ValueProductoElaborado {

	public String iconName() {
		return getProducto().iconName();
	}

	public ValueProductoElaborado() {
		// TODO Auto-generated constructor stub
	}

	public String title() {
		return getProducto().getNombre() + " (x " + getCantidad() + ")";
	}

	// {{ Producto (property)
	private ProductoElaborado productoElaborado;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public ProductoElaborado getProducto() {
		return productoElaborado;
	}

	public void setProducto(final ProductoElaborado productoElaborado) {
		this.productoElaborado = productoElaborado;
	}

	// }}

	// {{ Nota (property)
	private String nota;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "true")
	public String getNota() {
		return nota;
	}

	public void setNota(final String nota) {
		this.nota = nota;
	}

	// }}

	// {{ Cantidad (property)
	private int cantidad;

	@TypicalLength(2)
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad;
	}

	// }}

	@Inject
	private DomainObjectContainer contenedor;
}
