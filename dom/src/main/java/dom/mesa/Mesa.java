package mesa;

import java.util.ArrayList;
import java.util.List;

import org.apache.isis.applib.annotation.MemberOrder;

import dom.Comanda;

public class Mesa {

	public Mesa() {
		// TODO Auto-generated constructor stub

	}

	// {{ numeroMesa (property)
	private int numeroMesa;

	@MemberOrder(sequence = "1")
	public int getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(final int numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	// }}
	// {{ capacidadMesa (property)
	private int capacidadMesa;

	@MemberOrder(sequence = "1")
	public int getcapacidadMesa() {
		return capacidadMesa;
	}

	public void setcapacidadMesa(final int capacidadMesa) {
		this.capacidadMesa = capacidadMesa;
		// }}
	}

	// {{ listaComandas (Collection)
	private List<Comanda> listaComandas = new ArrayList<Comanda>();

	@MemberOrder(sequence = "1")
	public List<Comanda> getlistaComandas() {
		return listaComandas;
	}

	public void setlistaComandas(final List<Comanda> listaComandas) {
		this.listaComandas = listaComandas;
	}

	// }}

	// {{ cantidadDeComensales (property)
	private int cantidadDeComensales;

	@MemberOrder(sequence = "1")
	public int getcantidadDeComensales() {
		return cantidadDeComensales;
	}

	public void setcantidadDeComensales(final int cantidadDeComensales) {
		this.cantidadDeComensales = cantidadDeComensales;
	}

	// }}

	// {{ estadoMesa (property)
	private Estado estadoMesa;

	@MemberOrder(sequence = "1")
	public Estado getestadoMesa() {
		return estadoMesa;
	}

	public void setestadoMesa(final Estado estadoMesa) {
		this.estadoMesa = estadoMesa;
	}
	// }}







}
