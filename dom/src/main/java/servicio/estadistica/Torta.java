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

package servicio.estadistica;

import java.awt.Color;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.value.DateTime;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;

import com.google.common.base.Predicate;
import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.CssStyle;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.Function;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Labels;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;

@DomainService
@Named("Estadistica")
public class Torta extends AbstractFactoryAndRepository {

	@ActionSemantics(Of.SAFE)
	@Named("Platos Vendidos...")
	public WickedChart pieChart(@Named("Producto") final TipoPlato _tipoPlato,
			@Optional final Date _desde, @Optional final Date _hasta) {
		final List<Log> listaLog = new ArrayList<Log>();
		allMatches(Log.class, new Predicate<Log>() {
			@Override
			public boolean apply(Log input) {
				if (!input.getProducto().getClass().getSimpleName()
						.equals(_tipoPlato.name()))
					return false;
				int condicion = 0;
				if (_desde == null & _hasta != null)
					condicion = 1;
				if (_desde != null & _hasta == null)
					condicion = 2;
				if (_desde != null & _hasta != null)
					condicion = 3;
				switch (condicion) {
				case 0:
					cargarLista(listaLog, input);
					break;
				case 1:
					if (input.getFecha().compareTo(_hasta) <= 0)
						cargarLista(listaLog, input);
					break;
				case 2:
					if (input.getFecha().compareTo(_desde) >= 0)
						cargarLista(listaLog, input);
					break;
				case 3:
					if (input.getFecha().compareTo(_desde) >= 0
							& input.getFecha().compareTo(_hasta) <= 0)
						cargarLista(listaLog, input);
					break;
				}
				return false;
			}
		});
		return new WickedChart(new PieWithGradientOptions(listaLog, _desde,
				_hasta, _tipoPlato));
	}

	@Programmatic
	private void cargarLista(List<Log> listaLog, Log input) {
		boolean existe = false;
		for (Log log : listaLog)
			if (log.getProducto().getNumero() == input.getProducto()
					.getNumero()) {
				log.addToCantidad(input.getCantidad());
				existe = true;
				break;
			}
		if (!existe)
			listaLog.add(input);
	}

	public String validatePieChart(final TipoPlato _tipoPlato,
			final Date _desde, final Date _hasta) {
		Date ahora = new Date(new DateTime().getMillisSinceEpoch());
		if (_hasta != null) {
			if (_hasta.compareTo(ahora) > 0)
				return "Fecha Hasta sobrepasa el dia de Hoy";
			if (_desde != null && _desde.compareTo(_hasta) > 0)
				return "Rango inconsistente de Fechas";
		}
		if (_desde != null && _desde.compareTo(ahora) > 0)
			return "Fecha Desde sobrepasa el dia de hoy";
		return null;
	}

	public enum TipoPlato {
		PlatoPrincipal("Platos Principales"), PlatoEntrada("Platos Entrada"), Postre(
				"Postres"), Guarnicion("Guarniciones");

		private TipoPlato(String _nombre) {
			nombre = _nombre;
		}

		private String nombre;

		public String getPropertyName() {
			return nombre;
		}

		@Override
		public String toString() {
			return nombre;
		}
	}

	public static class PieWithGradientOptions extends Options {
		private static final long serialVersionUID = 1L;

		public PieWithGradientOptions(List<Log> _lista, Date _fechaDesde,
				Date _fechaHasta, TipoPlato _tipoPlato) {

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
			setTitle(new Title("Productos - "
					+ (_fechaDesde != null ? formato.format(new java.util.Date(
							_fechaDesde.getTime())) : "1º Registro")
					+ " - "
					+ (_fechaHasta != null ? formato.format(new java.util.Date(
							_fechaHasta.getTime())) : "Último Registro")));

			List<Number> valores = new ArrayList<Number>();
			List<String> labels = new ArrayList<String>();
			for (Log entry : _lista) {
				valores.add(entry.getCantidad());
				labels.add(entry.getProducto().getNombre());
			}

			addSeries(new SimpleSeries()
					.setColor(Color.DARK_GRAY.darker())
					.setName(_tipoPlato.toString())
					.setData(valores)
					.setDataLabels(
							new DataLabels()
									.setEnabled(Boolean.TRUE)
									.setColor(new HexColor("#ffffff"))
									.setAlign(HorizontalAlignment.CENTER)
									.setX(0)
									.setY(20)
									.setFormatter(
											new Function()
													.setFunction(" return this.y;"))
									.setStyle(
											new CssStyle()
													.setProperty("font-size",
															"13px")
													.setProperty("font-family",
															"Verdana, sans-serif"))));

			setyAxis(new Axis().setMin(0).setTitle(new Title("Cantidad")));

			setLegend(new Legend(Boolean.TRUE));

			setxAxis(new Axis().setCategories(labels).setLabels(
					new Labels()
							.setRotation(-45)
							.setAlign(HorizontalAlignment.RIGHT)
							.setStyle(
									new CssStyle().setProperty("font-size",
											"13px").setProperty("font-family",
											"Verdana, sans-serif"))));

			setChartOptions(new ChartOptions().setType(SeriesType.COLUMN)
					.setMarginTop(50).setMarginRight(50).setMarginBottom(100)
					.setMarginLeft(80));
		}
	}
}
