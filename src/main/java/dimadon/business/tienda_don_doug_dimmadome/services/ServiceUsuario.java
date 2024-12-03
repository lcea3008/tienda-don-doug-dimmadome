package dimadon.business.tienda_don_doug_dimmadome.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dimadon.business.tienda_don_doug_dimmadome.entities.Usuario;
import dimadon.business.tienda_don_doug_dimmadome.repositories.RepositoryUsuario;

@Service
public class ServiceUsuario implements UserDetailsService {

    @Autowired
    RepositoryUsuario repositoryUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ArrayList<Usuario> obtenerUsuarios() {
        return (ArrayList<Usuario>) repositoryUsuario.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        // Usar el PasswordEncoder proporcionado por el Bean
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return repositoryUsuario.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Cargar el usuario por email
        Usuario usuario = repositoryUsuario.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Determinar el rol según el ID del tipo de usuario
        String roleName = usuario.getTipoUsuario().getIdTipoUsuario() == 1 ? "ROLE_ADMIN" : "ROLE_VENDEDOR";
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);

        // Devolver el usuario con las credenciales y el rol
        return new User(usuario.getEmail(), usuario.getContrasena(), Collections.singletonList(authority));
    }

    // obtener usuario por email
    public Usuario obtenerUsuarioPorEmail(String email) {
        return repositoryUsuario.findByEmail(email).orElse(null);
    }

    // cambiar estado del usuario
    public Usuario cambiarEstadoUsuario(int idUsuario, String nuevoEstado) {
        Usuario usuario = repositoryUsuario.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + idUsuario));

        usuario.setEstado(nuevoEstado);
        return repositoryUsuario.save(usuario);
    }

    // modificar usuario segun el id
    public Usuario actualizarUsuario(int idUsuario, Usuario usuarioActualizado) {
        // Buscar el usuario existente
        Usuario usuarioExistente = repositoryUsuario.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + idUsuario));

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        if (usuarioActualizado.getContrasena() != null && !usuarioActualizado.getContrasena().isEmpty()) {
            usuarioExistente.setContrasena(passwordEncoder.encode(usuarioActualizado.getContrasena()));
        }
        usuarioExistente.setEstado(usuarioActualizado.getEstado());
        usuarioExistente.setTipoUsuario(usuarioActualizado.getTipoUsuario());

        return repositoryUsuario.save(usuarioExistente);
    }

    // obtener usuarios por estado
    public List<Usuario> obtenerUsuariosPorEstado(String estado) {
        if (!estado.equalsIgnoreCase("activo") && !estado.equalsIgnoreCase("inactivo")) {
            throw new IllegalArgumentException("Estado no válido. Debe ser 'activo' o 'inactivo'.");
        }
        return repositoryUsuario.findByEstado(estado);
    }

}
