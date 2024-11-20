package dimadon.business.tienda_don_doug_dimmadome.services;

import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.Repository.RepositoryUsuario;
import dimadon.business.tienda_don_doug_dimmadome.config.CustomUser;
import dimadon.business.tienda_don_doug_dimmadome.entities.Usuario;

@Service
public class ServiceUsuario implements UserDetailsService {

    @Autowired
    RepositoryUsuario repositoryUsuario;

    public ArrayList<Usuario> obtenerUsuarios() {
        return (ArrayList<Usuario>) repositoryUsuario.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        // System.out.println("Clave");
        // System.out.println(new
        // BCryptPasswordEncoder().encode(usuario.getContrasena()));
        usuario.setContrasena(new BCryptPasswordEncoder().encode(usuario.getContrasena()));
        return repositoryUsuario.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repositoryUsuario.findByEmail(username);
        if (usuario != null) {
            return new CustomUser(usuario);
        }
        System.out.println("No coincide");
        throw new UsernameNotFoundException("Usuario no encontrado");

    }

    // // login
    // public Usuario verificarCredenciales(String email, String contrasena) {
    // Usuario usuario = repositoryUsuario.findByEmail(email);
    // if (usuario != null && passwordEncoder.matches(contrasena,
    // usuario.getContrasena())) {
    // return usuario;
    // }
    // return null;
    // }

    // // cambiar estado del usuario
    // public Usuario cambiarEstado(int id, String nuevoEstado) {
    // Optional<Usuario> usuarioOpt = repositoryUsuario.findById(id);
    // if (usuarioOpt.isPresent()) {
    // Usuario usuario = usuarioOpt.get();
    // usuario.setEstado(nuevoEstado);
    // return repositoryUsuario.save(usuario);
    // } else {
    // throw new RuntimeException("Usuario no encontrado");
    // }
    // }

    // // actualizar usuario por id
    // public Usuario actualizarUsuario(int id, Usuario usuarioActualizado) {
    // Optional<Usuario> usuarioExistenteOpt = repositoryUsuario.findById(id);
    // if (usuarioExistenteOpt.isPresent()) {
    // Usuario usuarioExistente = usuarioExistenteOpt.get();

    // usuarioExistente.setNombre(usuarioActualizado.getNombre());
    // usuarioExistente.setEmail(usuarioActualizado.getEmail());
    // usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
    // usuarioExistente.setTipoUsuario(usuarioActualizado.getTipoUsuario());
    // usuarioExistente.setEstado(usuarioActualizado.getEstado());

    // return repositoryUsuario.save(usuarioExistente);
    // } else {
    // return null;
    // }
    // }

    // // obtener usuarios activos e inactivos
    // public List<Usuario> obtenerUsuariosPorEstado(String estado) {
    // return repositoryUsuario.findByEstado(estado);
    // }
}
