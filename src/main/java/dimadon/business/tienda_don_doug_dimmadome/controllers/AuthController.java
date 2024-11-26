package dimadon.business.tienda_don_doug_dimmadome.controllers;

import dimadon.business.tienda_don_doug_dimmadome.security.JwtTokenProvider;
import dimadon.business.tienda_don_doug_dimmadome.entities.Usuario;
import dimadon.business.tienda_don_doug_dimmadome.exceptions.CredencialesIncorrectasException;
import dimadon.business.tienda_don_doug_dimmadome.exceptions.UsuarioInactivoException;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String contrasena = loginRequest.get("contrasena");

        Authentication authentication;
        try {
            // Intentar autenticar al usuario con las credenciales proporcionadas
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, contrasena));
        } catch (BadCredentialsException e) {
            // Si las credenciales son incorrectas, lanzar CredencialesIncorrectasException
            throw new CredencialesIncorrectasException("Usuario o contraseña incorrecta");
        }

        // Intentar obtener el usuario desde la base de datos por su email
        Usuario usuario = serviceUsuario.obtenerUsuarioPorEmail(email);
        if (usuario == null) {
            // Si el usuario no existe, lanzar una excepción
            throw new CredencialesIncorrectasException("Usuario no encontrado");
        }

        // Verificar si el usuario está activo
        if (!"activo".equals(usuario.getEstado())) {
            // Si el usuario no está activo, lanzar UsuarioInactivoException
            throw new UsuarioInactivoException("El usuario está inactivo");
        }

        // Si el usuario está activo, generar el token
        String token = jwtTokenProvider.generateToken(email);

        // Devolver el token en la respuesta
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    @PostMapping("/registro")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return serviceUsuario.guardarUsuario(usuario);
    }
}