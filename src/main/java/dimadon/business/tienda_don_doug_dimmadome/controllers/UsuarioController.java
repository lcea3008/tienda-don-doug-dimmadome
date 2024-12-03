package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dimadon.business.tienda_don_doug_dimmadome.entities.Usuario;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceUsuario;

@RestController
@RequestMapping("/admin")
public class UsuarioController {

    @Autowired
    ServiceUsuario serviceUsuario;

    @GetMapping("/obtener")
    public ArrayList<Usuario> obtenerUsuarios() {
        return serviceUsuario.obtenerUsuarios();
    }

    @PostMapping("/insertar")
    public Usuario guardarUsuarios(@RequestBody Usuario usuario) {
        return this.serviceUsuario.guardarUsuario(usuario);
    }

    @PatchMapping("/{id}")
    public Usuario cambiarEstadoUsuario(@PathVariable("id") int idUsuario, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        return serviceUsuario.cambiarEstadoUsuario(idUsuario, nuevoEstado);
    }

    @PutMapping("/{id}/actualizar")
    public Usuario actualizarUsuario(@PathVariable("id") int idUsuario, @RequestBody Usuario usuario) {
        return serviceUsuario.actualizarUsuario(idUsuario, usuario);
    }

    @GetMapping("/estado/{estado}")
    public List<Usuario> obtenerUsuariosPorEstado(@PathVariable("estado") String estado) {
        return serviceUsuario.obtenerUsuariosPorEstado(estado);
    }

}