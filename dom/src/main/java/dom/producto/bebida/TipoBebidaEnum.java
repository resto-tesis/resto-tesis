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

import java.util.Arrays;
import java.util.List;
/**
 * Clase enumerada, de la cual agrega el tipo de Bebida
 * @author RestoTesis
 * @since 10/06/2014
 * @version 1.0.0
 */
public enum TipoBebidaEnum {
	Gaseosa {
		/**
		 * Permite seleccionar el volumen a la Gaseosa
		 * @return List<VolumenBebidaEmun>
		 */
		@Override
		public List<VolumenBebidaEnum> volumen() {
			return Arrays.asList(VolumenBebidaEnum.Lata,
					VolumenBebidaEnum.Lata_Grande,
					VolumenBebidaEnum.Medio_Litro,
					VolumenBebidaEnum.Siete_Cincuenta_Mililitros,
					VolumenBebidaEnum.Un_Litro,
					VolumenBebidaEnum.Un_Litro_Y_Un_Cuarto,
					VolumenBebidaEnum.Un_Litro_Y_Medio,
					VolumenBebidaEnum.Dos_Litros,
					VolumenBebidaEnum.Dos_Litros_Y_Un_Cuarto,
					VolumenBebidaEnum.Tres_Litros);
		}
	},
	Vino {
		/**
		 * Permite seleccionar un volumen al Vino
		 * @return List<VolumenBebidaEmun>
		 */
		@Override
		public List<VolumenBebidaEnum> volumen() {
			return Arrays.asList(VolumenBebidaEnum.Un_Litro);
		}
	},
	Cerveza {
		/**
		 * Permite seleccionar el volumen a la Cerveza
		 * @return List<VolumenBebidaEmun>
		 */
		@Override
		public List<VolumenBebidaEnum> volumen() {
			return Arrays.asList(VolumenBebidaEnum.Lata,
					VolumenBebidaEnum.Lata_Grande, VolumenBebidaEnum.Un_Litro);
		}
	},
	Jugo {
		/**
		 * Permite seleccionar un volumen al Jugo
		 * @return List<VolumenBebidaEmun>
		 */
		@Override
		public List<VolumenBebidaEnum> volumen() {
			return Arrays.asList(VolumenBebidaEnum.Un_Litro);
		}
	},
	Agua {
		/**
		 * Permite seleccionar el volumen al Agua mineral
		 * @return List<VolumenBebidaEmun>
		 */
		@Override
		public List<VolumenBebidaEnum> volumen() {
			return Arrays.asList(VolumenBebidaEnum.Medio_Litro,
					VolumenBebidaEnum.Un_Litro);
		}
	},
	Cafe {
		/**
		 * Permite seleccionar un volumen al Cafe
		 * @return List<VolumenBebidaEmun>
		 */
		@Override
		public List<VolumenBebidaEnum> volumen() {
			return Arrays.asList(VolumenBebidaEnum.Chico,
					VolumenBebidaEnum.Mediano, VolumenBebidaEnum.Grande);
		}
	},
	Otro {
		/**
		 * Permite seleccionar a otro tipo de bebida, otro volumen
		 * @return List<VolumenBebidaEmun>
		 */
		@Override
		public List<VolumenBebidaEnum> volumen() {
			return Arrays.asList(VolumenBebidaEnum.Otro_Volumen);
		}
	};

	public abstract List<VolumenBebidaEnum> volumen();
}
