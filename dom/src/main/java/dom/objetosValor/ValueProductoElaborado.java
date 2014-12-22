package dom.objetosValor;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.producto.ProductoElaborado;
/**
 * Objeto de Valor que se creará cuando se desee agregar una cantidad o alguna nota
 * al momento de tomar un pedido que tenga mas de un Producto Elaborado.
 * @author RestoTesis
 * @since 15/10/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ValueProductoElaborado {

	/**
	 * Obtiene el nombre del icono para un Producto Elaborado
	 * @return String
	 */
	public String iconName() {
		return getProducto().iconName();
	}

	/**
	 * Constructor de la clase
	 */
	public ValueProductoElaborado() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtiene el nombre y cantidad de Producto Elaborado
	 * @see dom.menu.Menu.getNombre()
	 * @return String
	 */
	public String title() {
		return getProducto().getNombre() + " (x " + getCantidad() + ")";
	}

	// {{ Producto (property)
	private ProductoElaborado productoElaborado;

	/**
	 * Obtiene un Producto Elaborado
	 * @return productoElaborado ProductoElaborado
	 */
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public ProductoElaborado getProducto() {
		return productoElaborado;
	}

	/**
	 * Setea un Producto Elaborado
	 * @param productoElaborado ProductoElaborado
	 */
	public void setProducto(final ProductoElaborado productoElaborado) {
		this.productoElaborado = productoElaborado;
	}

	// {{ Nota (property)
	private String nota;

	/**
	 * Obtiene una nota o comentario del Producto Elaborado
	 * @return nota String
	 */
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "true")
	public String getNota() {
		return nota;
	}

	/**
	 * Setea una nota o comentario del Producto Elaborado
	 * @param nota String
	 */
	public void setNota(final String nota) {
		this.nota = nota;
	}

	// }}

	// {{ Cantidad (property)
	private int cantidad;

	/**
	 * Obtiene la cantidad del Producto Elaborado
	 * @return cantidad int
	 */
	@TypicalLength(2)
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Setea la cantidad del Producto Elaborado
	 * @param cantidad int
	 */
	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Inyeccion del contenedor
	 */
	@SuppressWarnings("unused")
	@Inject
	private DomainObjectContainer contenedor;
}
