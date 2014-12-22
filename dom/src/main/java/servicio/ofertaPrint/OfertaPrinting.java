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

package servicio.ofertaPrint;

import java.text.DecimalFormat;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NotInServiceMenu;

import dom.oferta.Oferta;

/**
 * Clase para crear el encabezado, cuerpo y datos del los productos que integran
 * la oferta a enviar por mail.
 * 
 * @author RestoTesis
 * @since 10/11/2014
 * @version 1.0.0
 */
@DomainService
public class OfertaPrinting extends AbstractFactoryAndRepository {

	@NotInServiceMenu
	public String ofertaToText(Oferta unaOferta) {
		String encabezado = "##############Ofertas##############\n\nNombre: ";
		encabezado += unaOferta.getNombre() + "\n";
		encabezado += "Oferta valida desde: "
				+ unaOferta.getFechaInicio().toString() + " hasta: "
				+ unaOferta.getCaducidad().toString() + "\n"
				+ "Cantidad de Comensales: "
				+ String.valueOf(unaOferta.getCantidadPersonas()) + "\n"
				+ "Descuento: " + String.valueOf(unaOferta.getDescuento())
				+ "%\n" + "Precio Final: $"
				+ new DecimalFormat("#.00").format(unaOferta.getPrecioFinal())
				+ "\n" + "Menu: " + unaOferta.getMenu().getNombre() + "\n";
		String cuerpoMenu = "";
		if (unaOferta.getMenu().getPlatoEntrada() != null) {
			cuerpoMenu = "Plato de Entrada: "
					+ unaOferta.getMenu().getPlatoEntrada().getNombre() + "\n";
		}
		cuerpoMenu += "Plato Principal: "
				+ unaOferta.getMenu().getPlatoPrincipal().getNombre() + "\n";
		if (unaOferta.getMenu().getGuarnicion() != null) {
			cuerpoMenu += "Guarnicion: "
					+ unaOferta.getMenu().getGuarnicion().getNombre() + "\n";
		}

		if (unaOferta.getMenu().getPostre() != null) {
			cuerpoMenu += "Postre: "
					+ unaOferta.getMenu().getPostre().getNombre() + "\n";
		}
		return encabezado + cuerpoMenu;
	}

}
