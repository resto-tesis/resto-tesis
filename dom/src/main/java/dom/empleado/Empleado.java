package dom.empleado;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.COMPLETE_TABLE)
public abstract class Empleado {

	// {{ legajo (property)
	private int lejago;
	@javax.jdo.annotations.Column(allowsNull = "false")
	@MemberOrder(sequence = "1")
	public int getLegajo() {
		return lejago;
	}

	public void setLegajo(final int lejago) {
		this.lejago = lejago;
	}
	// }}

// {{ apellido (property)
private String apellido;

@javax.jdo.annotations.Column(allowsNull = "false")
@MemberOrder(sequence = "2")
public String getApellido() {
	return apellido;
}

public void setApellido(final String apellido) {
	this.apellido = apellido;
}
// }}

// {{ nombre (property)
private String nombre;

@javax.jdo.annotations.Column(allowsNull = "false")
@MemberOrder(sequence = "3")
public String getNombre() {
	return nombre;
}

public void setNombre(final String nombre) {
	this.nombre = nombre;
}
// }}

// {{ documento (property)
private long documento;

@javax.jdo.annotations.Column(allowsNull = "false")
@MemberOrder(sequence = "4")
public long getDocumento() {
	return documento;
}

public void setDocumento(final long documento) {
	this.documento = documento;
}
// }}

// {{ fechadeNacimiento (property)
private Date fechadeNacimiento;

@javax.jdo.annotations.Column(allowsNull = "false")
@MemberOrder(sequence = "5")
public Date getFechadeNacimiento() {
	return fechadeNacimiento;
}

public void setFechadeNacimiento(final Date fechadeNacimiento) {
	this.fechadeNacimiento = fechadeNacimiento;
}
// }}

// {{ fechadeIngreso (property)
private Date fechadeIngreso;

@javax.jdo.annotations.Column(allowsNull = "false")
@MemberOrder(sequence = "6")
public Date getFechadeIngreso() {
	return fechadeIngreso;
}

public void setFechadeIngreso(final Date fechadeIngreso) {
	this.fechadeIngreso = fechadeIngreso;
}
// }}

}
