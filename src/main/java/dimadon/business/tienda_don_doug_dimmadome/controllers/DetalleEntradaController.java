package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dimadon.business.tienda_don_doug_dimmadome.entities.DetalleEntrada;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceDetalleEntrada;

@RestController
@RequestMapping("/detalleEntrada")
public class DetalleEntradaController {

    @Autowired
    ServiceDetalleEntrada serviceDetalleEntrada;

    @GetMapping("/obtener")
    public ArrayList<DetalleEntrada> obtenerDetalleEntrada() {
        return this.serviceDetalleEntrada.obtenerDetalleEntradas();
    }

    @PostMapping("/insertar")
    public List<DetalleEntrada> insertarDetallesEntrada(@RequestBody List<DetalleEntrada> detallesEntrada) {
        return serviceDetalleEntrada.registrarDetallesEntradaConKardex(detallesEntrada);
    }

    // obtener ultimo is de entrada
    @GetMapping("/ultimoIdEntrada")
    public int obtenerUltimoId() {
        return serviceDetalleEntrada.obtenerUltimoIdEntrada();
    }
}
