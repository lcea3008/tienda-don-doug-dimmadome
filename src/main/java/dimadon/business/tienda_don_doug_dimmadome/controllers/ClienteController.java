package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dimadon.business.tienda_don_doug_dimmadome.entities.Cliente;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceCliente;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ServiceCliente serviceCliente;

    @GetMapping("/obtener")
    public ResponseEntity<?> obtenerClientes() {
        try {
            ArrayList<Cliente> clientes = serviceCliente.obtenerClientes();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los clientes: " + e.getMessage());
        }
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> guardarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = serviceCliente.guardarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al insertar el cliente: " + e.getMessage());
        }
    }

    // Cambiar estado de un cliente
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable("id") int id,
            @RequestBody Map<String, String> estadoData) {
        try {
            String nuevoEstado = estadoData.get("estado");
            if (nuevoEstado == null || nuevoEstado.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El estado proporcionado no es válido.");
            }
            Cliente clienteActualizado = serviceCliente.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cambiar el estado del cliente: " + e.getMessage());
        }
    }

}
