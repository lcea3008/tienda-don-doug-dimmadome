package dimadon.business.tienda_don_doug_dimmadome.controllers;

import dimadon.business.tienda_don_doug_dimmadome.security.JwtTokenProvider;
import dimadon.business.tienda_don_doug_dimmadome.entities.Usuario;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceUsuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
        String email = loginRequest.get("email");
        String contrasena = loginRequest.get("contrasena");

        System.out.println("Intento de inicio de sesión con email: " + email + " y contraseña: " + contrasena);

        try {
            // Autenticación del usuario con las credenciales proporcionadas
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, contrasena));

            // Obtener el usuario desde la base de datos
            Usuario usuario = serviceUsuario.obtenerUsuarioPorEmail(email);

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
            }

            if (!"activo".equals(usuario.getEstado())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario está inactivo");
            }

            String token = jwtTokenProvider.generateToken(email, usuario.getTipoUsuario().getIdTipoUsuario());

            // Configurar la cookie para el access token
            Cookie accessTokenCookie = new Cookie("token", token);
            accessTokenCookie.setHttpOnly(false);
            accessTokenCookie.setSecure(false); // Establecer en true si usas HTTPS
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(60 * 10); // 5 minutos
            response.addCookie(accessTokenCookie);

            // Responder con un mensaje de éxito, sin mostrar los tokens en el cuerpo
            return ResponseEntity.ok("Inicio de sesión exitoso");

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrecta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PostMapping("/registro")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return serviceUsuario.guardarUsuario(usuario);
    }
}