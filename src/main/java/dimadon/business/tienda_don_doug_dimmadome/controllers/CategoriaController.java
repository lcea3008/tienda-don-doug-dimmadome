package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dimadon.business.tienda_don_doug_dimmadome.entities.Categoria;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceCategoria;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    ServiceCategoria serviceCategoria;

    @GetMapping("/obtener")
    public ResponseEntity<?> obtenerCategoria() {
        try {
            ArrayList<Categoria> categorias = serviceCategoria.obtnerCategoria();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            // Manejo del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las categorías: " + e.getMessage());
        }
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> guardarCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria nuevaCategoria = this.serviceCategoria.insertarCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
        } catch (Exception e) {
            // Manejo del error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al insertar la categoría: " + e.getMessage());
        }
    }
}
