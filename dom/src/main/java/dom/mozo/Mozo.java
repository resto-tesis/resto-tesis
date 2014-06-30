package dom.mozo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import dom.empleado.Empleado;
import dom.mesa.Mesa;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)


public class Mozo extends Empleado {

 // {{ Lista De Mesas (Collection)
	
private List<Mesa> listamesas = new ArrayList<Mesa>();

@MemberOrder(sequence = "1")
public List<Mesa> getListamesas() {
	return listamesas;
}

public void setListamesas(final List<Mesa> listamesas) {
	this.listamesas = listamesas;
}
// }}
//{{ injected: DomainObjectContainer
private DomainObjectContainer contenedor;

public void injectDomainObjectContainer(final DomainObjectContainer container) {
    this.setContainer(container);
}

public DomainObjectContainer getContainer() {
	return contenedor;
}

public void setContainer(DomainObjectContainer container) {
	this.contenedor = container;
}



/*
 * Inyección del servicio
 */

private MozoServicio mozoServicio;

public void injectarMozoServicio(final MozoServicio serviciomozo) {
    this.mozoServicio = serviciomozo;
}

@Named("Borrar")
@Bulk
@MemberOrder(name="accionMozo", sequence = "1")
public List<Mozo> borrar() {
	
	contenedor.removeIfNotAlready(this);
	
	return mozoServicio.listarMozos();
}

@Named("Asignar Mesas a Mozo")
@Bulk
@MemberOrder(name="accionMesaMozo", sequence = "2")
public  Mozo asignarMesasMozo(@Named("Numero de Mesa")final int numeroDeMesa){
	
	this.listamesas.add(mozoServicio.devolverMesa(numeroDeMesa));
	return  this;
	
}

}
