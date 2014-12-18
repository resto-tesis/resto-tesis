/*
 * Copyright 2014 resto-tesis
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package dom.factura;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.value.Blob;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import servicio.estadistica.Log;

import com.google.common.io.Resources;

import dom.objetosValor.ValueMenu;
import dom.objetosValor.ValueOferta;
import dom.objetosValor.ValueProductoElaborado;
import dom.objetosValor.ValueProductoNoElaborado;
import dom.pedido.Pedido;

/**
 * Clase que da funcionalidad de crear y persistir la Factura
 * 
 * @author RestoTesis
 * @since 15/10/2014
 * @version 1.0.0
 */
@DomainService(menuOrder = "90")
@Named("Facturas")
public class FacturaServicio extends AbstractFactoryAndRepository {

	/**
	 * Constructor de la clase FacturaServicio
	 */
	public FacturaServicio() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo que crea y persiste una nueva Factura, cargando todos sus items
	 * con precio
	 * 
	 * @param List
	 *            <Pedido> _pedidos
	 * @see dom.objetosValor.ValueProductoNoElaborado.getProducto()
	 * @see dom.objetosValor.ValueProductoNoElaborado.getCantidad()
	 * @see dom.objetosValor.ValueMenu.getMenu()
	 * @see dom.producto.Producto.getNombre()
	 * @see dom.producto.Producto.getPrecio()
	 * @see dom.factura.ItemFactura.getPrecioFinal()
	 * @see dom.pedido.Pedido.getComanda()
	 * @see dom.pedido.Pedido.getBebidas()
	 * @see dom.comanda.Comanda.getMenues()
	 * @see dom.menu.Menu.getNombre()
	 * @see dom.menu.Menu.getDescuento()
	 * @see dom.menu.Menu.getPrecioSinDescuento()
	 * @see dom.menu.Menu.getPlatoPrincipal()
	 * @see dom.menu.Menu.getPlatoEntrada()
	 * @see dom.menu.Menu.getPostre()
	 * @see dom.menu.Menu.getGuarnicion()
	 * @return factura Factura
	 */
	@Programmatic
	public Factura crearFactura(final List<Pedido> _pedidos) {
		double precioTotal = 0;
		final Factura factura = newTransientInstance(Factura.class);
		for (Pedido pedido : _pedidos) {
			for (ValueProductoNoElaborado bebida : pedido.getBebidas()) {
				final ItemFactura item = newTransientInstance(ItemFactura.class);
				item.setNombre(bebida.getProducto().getNombre());
				item.setCantidad(bebida.getCantidad());
				item.setDescuento(0);
				item.setPrecio(bebida.getProducto().getPrecio());
				persist(item);
				precioTotal += item.getPrecioFinal();
				factura.addToItems(item);
			}
			if (!pedido.getComanda().getProductos().isEmpty()) {
				for (ValueProductoElaborado producto : pedido.getComanda()
						.getProductos()) {
					final ItemFactura itemProducto = newTransientInstance(ItemFactura.class);
					itemProducto.setNombre(producto.getProducto().getNombre());
					itemProducto.setCantidad(producto.getCantidad());
					itemProducto.setDescuento(0);
					itemProducto.setPrecio(producto.getProducto().getPrecio());
					persist(itemProducto);
					newPersistentInstance(Log.class).nuevoRegistro(
							producto.getProducto(), producto.getCantidad());
					precioTotal += itemProducto.getPrecioFinal();
					factura.addToItems(itemProducto);
				}
			}
			if (!pedido.getComanda().getMenues().isEmpty()) {
				for (ValueMenu menu : pedido.getComanda().getMenues()) {
					ItemFactura itemMenu = newTransientInstance(ItemFactura.class);
					itemMenu.setNombre("Menú - " + menu.getMenu().getNombre());
					itemMenu.setCantidad(menu.getCantidad());
					itemMenu.setDescuento(menu.getMenu().getDescuento());
					itemMenu.setPrecio(menu.getMenu().getPrecioSinDescuento());
					persist(itemMenu);
					precioTotal += itemMenu.getPrecioFinal();
					factura.addToItems(itemMenu);
				}
			}
			if (!pedido.getComanda().getOfertas().isEmpty()) {
				for (ValueOferta oferta : pedido.getComanda().getOfertas()) {
					ItemFactura itemOferta = newTransientInstance(ItemFactura.class);
					itemOferta.setNombre("Oferta - "
							+ oferta.getOferta().getNombre());
					itemOferta.setCantidad(oferta.getCantidad());
					itemOferta.setDescuento(oferta.getOferta().getDescuento());
					itemOferta.setPrecio(oferta.getOferta()
							.getPrecioSinDescuento());
					persist(itemOferta);
					precioTotal += itemOferta.getPrecioFinal();
					factura.addToItems(itemOferta);
				}
			}
		}
		factura.setTotal(precioTotal);
		factura.setFechaHora(new Date());
		persist(factura);
		return factura;
	}

	/**
	 * Obtiene una lista de todas las facturas
	 * 
	 * @return List<Factura> lista
	 */
	@Named("Listar")
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "60")
	public List<Factura> listar() {
		final List<Factura> lista = allInstances(Factura.class);
		return lista;
	}

	private byte[] pdfAsBytes;

	@PostConstruct
	public void init() throws IOException {
		pdfAsBytes = Resources.toByteArray(Resources.getResource(
				this.getClass(), "plantilla.pdf"));
	}

	@NotContributed(NotContributed.As.ASSOCIATION)
	@NotInServiceMenu
	@ActionSemantics(Of.SAFE)
	@MemberOrder(sequence = "10")
	public Blob imprimirFactura(final Factura _factura) throws Exception {

		try (PDDocument pdfDocument = cargarPlantilla(_factura)) {

			final ByteArrayOutputStream target = new ByteArrayOutputStream();
			pdfDocument.save(target);

			final String name = "Factura-" + _factura.getNumero() + ".pdf";
			final String mimeType = "application/pdf";
			final byte[] bytes = target.toByteArray();

			return new Blob(name, mimeType, bytes);
		}
	}

	private PDDocument cargarPlantilla(Factura _factura) throws Exception {
		PDDocument pdfDocument = PDDocument.load(new ByteArrayInputStream(
				pdfAsBytes));

		PDAcroForm pdfForm = pdfDocument.getDocumentCatalog().getAcroForm();

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm",
				new Locale("es_AR"));
		pdfForm.getField("fecha").setValue(
				formato.format(_factura.getFechaHora()));
		pdfForm.getField("numero").setValue(
				String.valueOf(_factura.getNumero()));
		pdfForm.getField("total").setValue(
				new DecimalFormat("#.00").format(_factura.getTotal()));

		int i = 1;
		Iterator<ItemFactura> iterador = _factura.getItems().iterator();
		while (i < 20 && iterador.hasNext()) {
			ItemFactura item = iterador.next();

			String txtDescripcion = "desc" + i;
			String txtPrecio = "precio" + i;

			pdfForm.getField(txtDescripcion).setValue(
					item.getNombre() + " (" + item.getCantidad() + ")");
			pdfForm.getField(txtPrecio).setValue(
					new DecimalFormat("#.00").format(item.getPrecioFinal()));

			i++;
		}
		return pdfDocument;
	}
}
