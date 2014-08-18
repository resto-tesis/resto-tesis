package dom.bebida;

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

import java.util.Arrays;
import java.util.List;

public enum TipoBebidaEnum {
	Gaseosa{
		@Override
        public List<VolumenBebidaEnum> volumen() {
            return Arrays.asList(VolumenBebidaEnum.Lata, VolumenBebidaEnum.Lata_Grande, 
            		VolumenBebidaEnum.Medio_Litro, VolumenBebidaEnum.Siete_Cincuenta_Mililitros, 
            		VolumenBebidaEnum.Un_Litro, VolumenBebidaEnum.Un_Litro_Y_Un_Cuarto, 
            		VolumenBebidaEnum.Un_Litro_Y_Medio, VolumenBebidaEnum.Dos_Litros, 
            		VolumenBebidaEnum.Dos_Litros_Y_Un_Cuarto, VolumenBebidaEnum.Tres_Litros);
        }
	}, Vino{
		@Override
        public List<VolumenBebidaEnum> volumen() {
            return Arrays.asList(VolumenBebidaEnum.Un_Litro);
        }
	}, Cerveza{
		@Override
        public List<VolumenBebidaEnum> volumen() {
            return Arrays.asList(VolumenBebidaEnum.Lata, VolumenBebidaEnum.Lata_Grande, 
            		VolumenBebidaEnum.Un_Litro);
        }
	}, Jugo{
		@Override
        public List<VolumenBebidaEnum> volumen() {
            return Arrays.asList(VolumenBebidaEnum.Un_Litro);
        }
	}, Agua{
		@Override
        public List<VolumenBebidaEnum> volumen() {
            return Arrays.asList(VolumenBebidaEnum.Medio_Litro, VolumenBebidaEnum.Un_Litro);
        }
	}, Cafe{
		@Override
        public List<VolumenBebidaEnum> volumen() {
            return Arrays.asList(VolumenBebidaEnum.Chico, VolumenBebidaEnum.Mediano, 
            		VolumenBebidaEnum.Grande);
        }
	}, Otro{
		@Override
        public List<VolumenBebidaEnum> volumen() {
            return Arrays.asList(VolumenBebidaEnum.Otro_Volumen);
        }
	};
	
	public abstract  List<VolumenBebidaEnum> volumen();
}
