package dom.empleado;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
public abstract class Empleado {

	// {{ legajo (property)
	private int lejago;

	@MemberOrder(sequence = "1")
	public int getlegajo() {
		return lejago;
	}

	public void setlegajo(final int lejago) {
		this.lejago = lejago;
	}
	// }}

// {{ apellido (property)
private String apellido;

@MemberOrder(sequence = "2")
public String getapellido() {
	return apellido;
}

public void setapellido(final String apellido) {
	this.apellido = apellido;
}
// }}

// {{ nombre (property)
private String nombre;

@MemberOrder(sequence = "3")
public String getnombre() {
	return nombre;
}

public void setnombre(final String nombre) {
	this.nombre = nombre;
}
// }}

// {{ documento (property)
private long documento;

@MemberOrder(sequence = "4")
public long getdocumento() {
	return documento;
}

public void setdocumento(final long documento) {
	this.documento = documento;
}
// }}

// {{ fechadeNacimiento (property)
private Date fechadeNacimiento;

@MemberOrder(sequence = "5")
public Date getfechadeNacimiento() {
	return fechadeNacimiento;
}

public void setfechadeNacimiento(final Date fechadeNacimiento) {
	this.fechadeNacimiento = fechadeNacimiento;
}
// }}

// {{ fechadeIngreso (property)
private Date fechadeIngreso;

@MemberOrder(sequence = "6")
public Date getfechadeIngreso() {
	return fechadeIngreso;
}

public void setfechadeIngreso(final Date fechadeIngreso) {
	this.fechadeIngreso = fechadeIngreso;
}
// }}


}
