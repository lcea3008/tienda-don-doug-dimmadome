package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dimadon.business.tienda_don_doug_dimmadome.dto.DetallesSalidaRequest;
import dimadon.business.tienda_don_doug_dimmadome.entities.DetalleSalida;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceDetalleSalida;

@RestController
@RequestMapping("/detalleSalida")
public class DetalleSalidaController {

    @Autowired
    ServiceDetalleSalida serviceDetalleSalida;

    @GetMapping("/obtener")
    public ArrayList<DetalleSalida> obtenerDetalleSalidas() {
        return serviceDetalleSalida.obtenerDetalleSalidas();
    }

    @PostMapping("/insertar")
    public List<DetalleSalida> insertarDetallesSalida(@RequestBody DetallesSalidaRequest detallesSalidaRequest) {
        return serviceDetalleSalida.registrarDetallesSalidaConKardex(
                detallesSalidaRequest.getDetallesSalida(),
                detallesSalidaRequest.getDescripcion(),
                detallesSalidaRequest.getSalida());
    }

    // obtener ultimo is de salida
    @GetMapping("/ultimoIdSalida")
    public int obtenerUltimoId() {
        return serviceDetalleSalida.obtenerUltimoIdSalida();
    }
}
