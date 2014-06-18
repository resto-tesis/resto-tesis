package dom.mesa;

import org.apache.isis.applib.annotation.MemberOrder;

public enum Estado {
	OCUPADA,
	DESOCUPADA;
	
	// {{ Nombre (property)
	private String nombre;

	@MemberOrder(sequence = "1")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	// }}


}
