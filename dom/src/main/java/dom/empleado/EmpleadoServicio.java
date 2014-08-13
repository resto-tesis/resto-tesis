package dom.empleado;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Hidden;
import org.joda.time.LocalDate;

public class EmpleadoServicio extends AbstractFactoryAndRepository {

	public EmpleadoServicio() {
		// TODO Auto-generated constructor stub
	}

	final LocalDate fecha_actual = LocalDate.now();

	public String validarDocumento(final long _dni) {
		for (Empleado _empleado : listarEmpleados())
			return _dni == _empleado.getDocumento() ? "Ya existe el número de documento ingresado."
					: null;
		return null;
	}

	@Hidden
	public List<Empleado> listarEmpleados() {
		return allInstances(Empleado.class);
	}
}
