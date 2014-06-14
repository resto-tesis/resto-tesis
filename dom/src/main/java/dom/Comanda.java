package dom;

import org.apache.isis.applib.annotation.MemberOrder;

public class Comanda {

	public Comanda() {
		// TODO Auto-generated constructor stub
	}
	
	// {{ numeroComanda (property)
	private int numeroComanda;

	@MemberOrder(sequence = "1")
	public int getnumeroComanda() {
		return numeroComanda;
	}

	public void setnumeroComanda(final int numeroComanda) {
		this.numeroComanda = numeroComanda;
	}
	// }}





}
