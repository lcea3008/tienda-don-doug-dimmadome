package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dimadon.business.tienda_don_doug_dimmadome.entities.TipoDevolucion;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceTipoDevolucion;

@RestController
@RequestMapping("/tipoDevolucion")
public class TipoDevolucionController {

    @Autowired
    ServiceTipoDevolucion serviceTipoDevolucion;

    @GetMapping("/obtener")
    public ArrayList<TipoDevolucion> obtenerTipoDevolucion() {
        return serviceTipoDevolucion.obtenerTipoDevolucion();
    }

    @PostMapping("/insertar")
    public TipoDevolucion insertarTipoDevolucion(@RequestBody TipoDevolucion tipoDevolucion) {
        return serviceTipoDevolucion.insertarTipoDevolucion(tipoDevolucion);
    }

}
