package dom.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.annotation.MemberOrder;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Queries({
		@Query(name = "mozo-role", language = "JDOQL", value = "SELECT FROM dom.usuario.Rol where nombre=='mozo-role'"),
		@Query(name = "cocina-role", language = "JDOQL", value = "SELECT FROM dom.usuario.Rol where nombre=='cocina-role'"),
		@Query(name = "encargado-role", language = "JDOQL", value = "SELECT FROM dom.usuario.Rol where nombre=='encargado-role'") })
public class Rol {

	public Rol() {
		// TODO Auto-generated constructor stub
	}

	// {{ Nombre (property)
	private String nombre;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	// }}

	// {{ ListaPermiso (Collection)
	private List<Permiso> listaPermiso = new ArrayList<Permiso>();

	public List<Permiso> getListaPermiso() {
		return listaPermiso;
	}

	public void setListaPermiso(final List<Permiso> listaPermiso) {
		this.listaPermiso = listaPermiso;
	}

	// }}

}
