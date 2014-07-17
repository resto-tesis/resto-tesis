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

public enum VolumenBebidaEnum {
	Lata("Lata"),
	TresLitros("3 Litros"),
	DosLitrosYUnCuarto("2,25 Litros"),
	DosLitros("2 Litros"),
	UnLitroYMedio("1,5 Litros"),
	UnLitroYUnCuarto("1,25 Litros"),
	UnLitro("1 Litro"), 
	MedioLitro("500 ml"),
	SieteCincuentaCentimetrosCubicos("750 cc"),
	TresCincuentaCentimetrosCubicos("350 cc");
	
	private final String nombre;

	public String getNombre() {
		return nombre;
	}

	private VolumenBebidaEnum(String _nombre) {
		nombre = _nombre;
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
