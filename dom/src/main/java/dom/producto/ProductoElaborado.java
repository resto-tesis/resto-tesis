package dom.producto;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Clase asbtarcta que permitira diferenciar al momento de crear cada producto,
 * los que requieran ser elaborados en el local de los que no lo sean.- 
 * @author RestoTesis
 * @since 10/05/2014
 * @version 1.0.0
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public abstract class ProductoElaborado extends Producto {

	/**
	 * Contructor de la clase
	 */
	public ProductoElaborado() {
		// TODO Auto-generated constructor stub
	}

}
