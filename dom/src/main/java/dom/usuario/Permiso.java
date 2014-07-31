package dom.usuario;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Title;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Permiso {

	public Permiso() {
		// TODO Auto-generated constructor stub
	}

	// {{ Permiso (property)
	private String permiso;

	@Title
	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(final String permiso) {
		this.permiso = permiso;
	}

	// }}

}
