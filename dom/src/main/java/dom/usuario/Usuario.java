package dom.usuario;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Usuario {

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	// {{ Nombre (property)
	private String nombre;

	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ Password (property)
	private String password;

	@Column(allowsNull = "false")
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	// }}

	// {{ Rol (property)
	private Rol rol;

	@Column(allowsNull = "false")
	public Rol getRol() {
		return rol;
	}

	public void setRol(final Rol rol) {
		this.rol = rol;
	}
	// }}

}
