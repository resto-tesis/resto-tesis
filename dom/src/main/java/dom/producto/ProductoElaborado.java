package dom.producto;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public abstract class ProductoElaborado extends Producto {

	public ProductoElaborado() {
		// TODO Auto-generated constructor stub
	}

}
