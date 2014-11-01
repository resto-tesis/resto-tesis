package dom.objetosValor;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.TypicalLength;

import dom.menu.Menu;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ValueMenu {

	public String iconName(){
		return getMenu().iconName();
	}
	
	public ValueMenu() {
		// TODO Auto-generated constructor stub
	}

	public String title() {
		return getMenu().getNombre() + " (x " + getCantidad() + ")";
	}

	// {{ Menu (property)
	private Menu menu;

	@MemberOrder(sequence = "5")
	@Column(allowsNull = "false")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(final Menu menu) {
		this.menu = menu;
	}

	// }}

	// {{ Nota (property)
	private String nota;

	@MemberOrder(sequence = "4")
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
	@MemberOrder(sequence = "3")
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
