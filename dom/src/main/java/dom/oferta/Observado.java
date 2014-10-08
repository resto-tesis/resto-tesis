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

package dom.oferta;

import java.util.ArrayList;
import java.util.List;

import dom.cliente.Cliente;

public abstract class Observado {

	//El constructor crea el vector con la asociacion Observable-Observador
	public Observado() {
        listaClientes = new ArrayList<Cliente>();
    }
 
    //Agregar y eliminar sencillamente operan sobre vector _observadores...
    public void registrarCliente(Cliente _cliente) {
        listaClientes.add(_cliente);
    }
 
    public void removerCliente(Cliente _cliente) {
        listaClientes.remove(_cliente);
    }
 
    //Notificacion: Para cada observador se invoca el método actualizar().
    public void notificarClientes() {
        for (Cliente _cliente : listaClientes) {
            _cliente.actualizar(oferta);
        }
    }

	//Este atributo privado mantiene el vector con los observadores
    private List<Cliente> listaClientes;
    private Oferta oferta;
    
}