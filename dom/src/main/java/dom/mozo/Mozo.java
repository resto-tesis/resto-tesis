package dom.mozo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;

import dom.empleado.Empleado;
import dom.mesa.Mesa;

@PersistenceCapable
public class Mozo extends Empleado {

 // {{ listamesas (Collection)
private List<Mesa> listamesas = new ArrayList<Mesa>();

@MemberOrder(sequence = "1")
public List<Mesa> getlistamesas() {
	return listamesas;
}

public void setlistamesas(final List<Mesa> listamesas) {
	this.listamesas = listamesas;
}
// }}


}
