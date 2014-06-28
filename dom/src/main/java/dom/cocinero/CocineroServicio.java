package dom.cocinero;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@Named("Cocinero")
public class CocineroServicio extends AbstractFactoryAndRepository {

	public CocineroServicio() {
		// TODO Auto-generated constructor stub
	}

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Cocinero crearCocinero(@Named("Legajo") final int _legajo,
			@Named("Apellido") final String _apellido,
			@Named("Nombre") final String _nombre,
			@Named("DNI") final long _dni,
			@Named("Fecha de Nacimiento") final Date _fechaNacimiento,
			@Named("Fecha de Ingreso") final Date _fechaIngreso) {
		return crearNuevoCocinero(_legajo, _nombre, _apellido, _dni,
				_fechaNacimiento, _fechaIngreso);
	}

	@Hidden
	public Cocinero crearNuevoCocinero(final int _legajo, final String _nombre,
			final String _apellido, final long _dni,
			final Date _fechaNacimiento, final Date _fechaIngreso) {
		final Cocinero cocineroNuevo = newTransientInstance(Cocinero.class);
		cocineroNuevo.setapellido(_apellido);
		cocineroNuevo.setdocumento(_dni);
		cocineroNuevo.setfechadeIngreso(_fechaIngreso);
		cocineroNuevo.setfechadeNacimiento(_fechaNacimiento);
		cocineroNuevo.setlegajo(_legajo);
		cocineroNuevo.setnombre(_nombre);
		persist(cocineroNuevo);
		return cocineroNuevo;
	}

	@Named("Listar")
	@MemberOrder(sequence = "1")
	@ActionSemantics(Of.SAFE)
	public List<Cocinero> listarCocineros() {
		final List<Cocinero> listaCocinero = allInstances(Cocinero.class);
		return listaCocinero;
	}
}
