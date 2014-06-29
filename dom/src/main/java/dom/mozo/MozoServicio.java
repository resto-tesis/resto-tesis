package dom.mozo;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@Named("Mozo")
public class MozoServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Mozo crearMozo(@Named("Apellido") final String apellido,
			@Named("Nombre") final String nombre,
			@Named("Documento") final long documento,
			@Named("Fecha de Nacimiento") final Date fechaDeNacimiento,
			@Named("Fecha de Ingreso") final Date fechaDeIngreso) {
		return crearNuevoMozo(apellido, nombre, documento, fechaDeIngreso,
				fechaDeNacimiento);
	}

	@Hidden
	public Mozo crearNuevoMozo(final String apellido, final String nombre,
			final long documento, final Date fechaDeIngreso,
			final Date fechaDeNacimiento) {
		final Mozo mozo = newTransientInstance(Mozo.class);
		mozo.setApellido(apellido);
		mozo.setNombre(nombre);
		mozo.setDocumento(documento);
		mozo.setFechadeIngreso(fechaDeIngreso);
		mozo.setFechadeNacimiento(fechaDeNacimiento);
		persist(mozo);
		return mozo;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Mozo> listarMozos() {
		final List<Mozo> listamozos = allInstances(Mozo.class);
		return listamozos;
	}

}
