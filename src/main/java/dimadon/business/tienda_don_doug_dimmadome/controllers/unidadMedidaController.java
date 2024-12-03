package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dimadon.business.tienda_don_doug_dimmadome.entities.TipoUsuario;
import dimadon.business.tienda_don_doug_dimmadome.entities.UnidadMedida;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceUnidadMedida;

@RestController
@RequestMapping("/unidadMedida")
public class unidadMedidaController {

    @Autowired
    private ServiceUnidadMedida serviceUnidadMedida;

    @GetMapping("/obtener")
    public ArrayList<UnidadMedida> obtenerUnidadMedida() {
        return serviceUnidadMedida.obtnerUnidadMedida();
    }

    @PostMapping("/insertar")
    public UnidadMedida guardarUnidadMedida(@RequestBody UnidadMedida unidadMedida) {
        return this.serviceUnidadMedida.insertarUnidadMedida(unidadMedida);
    }
}
