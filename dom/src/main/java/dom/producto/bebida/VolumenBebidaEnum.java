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

package dom.producto.bebida;

import java.util.Collections;
import java.util.List;
/**
 * Clase enumerada, por la cual agrega el volumen de a la Bebida
 * @author RestoTesis
 * @since 10/06/2014
 * @version 1.0.0
 */
public enum VolumenBebidaEnum {
	Lata("Lata"), Lata_Grande("Lata Grande"), Tres_Litros("3 Litros"), Dos_Litros_Y_Un_Cuarto(
			"2,25 Litros"), Dos_Litros("2 Litros"), Un_Litro_Y_Medio(
			"1,5 Litros"), Un_Litro_Y_Un_Cuarto("1,25 Litros"), Un_Litro(
			"1 Litro"), Siete_Cincuenta_Mililitros("750 ml"), Medio_Litro(
			"500 ml"), Grande("Grande"), Mediano("Mediano"), Chico("Chico"), Otro_Volumen(
			"Otro");

	/**
	 * Permite agregar un volumen al tipo de bebida
	 * @param _tipoBebida TipoBebidaEnum
	 * @return List<>
	 */
	public static List<VolumenBebidaEnum> listar(TipoBebidaEnum _tipoBebida) {
		return _tipoBebida != null ? _tipoBebida.volumen() : Collections
				.<VolumenBebidaEnum> emptyList();
	}

	/**
	 * Valida la seleccion de la bebida primero y su volumen
	 * @param _tipoBebida TipoBebidaEnum
	 * @param _volumenBebida VolumeBebidaEnum
	 * @return String
	 */
	public static String validate(final TipoBebidaEnum _tipoBebida,
			final VolumenBebidaEnum _volumenBebida) {
		if (_tipoBebida == null)
			return "Seleccionar un tipo de bebida primero";
		return !_tipoBebida.volumen().contains(_volumenBebida) ? "Volumen inválido"
				: null;
	}

	private final String nombre;

	/**
	 * Obtiene el nombre del volumen de la bebida
	 * @return nombre String
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Asigna el nombre del volumen de la bebida
	 * @param _nombre String
	 */
	private VolumenBebidaEnum(String _nombre) {
		nombre = _nombre;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
