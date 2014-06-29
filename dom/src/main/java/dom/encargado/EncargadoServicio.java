package dom.encargado;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
@Named("Encargado")
public class EncargadoServicio extends AbstractFactoryAndRepository {

	@Named("Crear")
	@MemberOrder(sequence = "1")
	public Encargado crearEncargado(
			@Named("Legajo:") final int legajoEncargado,
			@Named("Apellido:") final String apellidoEncargado,
			@Named("Nombre:") final String nombreEncargado,
			@Named("Documento:") final long documentoEncargado,
			@Named("Fecha de Nacimiento:") final Date fechadeNacimientoEncargado,
			@Named("Fecha de Encargado:") final Date fechadeIngresoEncargado) {
		return crearEncargadoNuevo(legajoEncargado, apellidoEncargado, nombreEncargado, documentoEncargado, fechadeNacimientoEncargado, fechadeIngresoEncargado); 
	}

	// }}
	@Hidden
	public Encargado crearEncargadoNuevo(final int legajoEncargado, final String apellidoEncargado, final String nombreEncargado, final long documentoEncargado, final Date fechadeNacimientoEncargado, final Date fechadeIngresoEncargado) {
		final Encargado encargado = newTransientInstance(Encargado.class);
		encargado.setLegajo(legajoEncargado);
		encargado.setApellido(apellidoEncargado);
		encargado.setNombre(nombreEncargado);
		encargado.setDocumento(documentoEncargado);
		encargado.setFechadeNacimiento(fechadeNacimientoEncargado);
		encargado.setFechadeIngreso(fechadeIngresoEncargado);
		persist(encargado);
		return encargado;
	}

	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "2")
	public List<Encargado> listarEncargados() {
		final List<Encargado> listaencargados = allInstances(Encargado.class);
		return listaencargados;
	}
}
