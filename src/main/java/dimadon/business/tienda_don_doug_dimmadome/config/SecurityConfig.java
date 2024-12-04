package dimadon.business.tienda_don_doug_dimmadome.config;

import dimadon.business.tienda_don_doug_dimmadome.services.ServiceUsuario;
import dimadon.business.tienda_don_doug_dimmadome.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private ServiceUsuario userDetailsService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable()) // Nueva forma de deshabilitar CSRF
                                .cors(cors -> cors.and()) // Configurar CORS
                                .authorizeHttpRequests(authz -> authz // Nueva forma de autorizar peticiones
                                                .requestMatchers("/auth/**").permitAll() // Público
                                                .requestMatchers("/categoria/obtener**", "/categoria/insertar**",
                                                                "/cliente//{id}/estado**",
                                                                "/detalleEntrada/obtener**",
                                                                "/detalleEntrada/insertar**",
                                                                "/detalleSalida/obtener**",
                                                                "/devolucion/obtener**",
                                                                "/entrada/obtener**", "/entrada/insertar**",
                                                                "/kardex/obtener**", "/kardex/insertar**",
                                                                "/producto/insertar**", "/producto/{id}**",
                                                                "/producto/{id}/estado**", "/producto/estado**",
                                                                "/proveedor/obtener**", "/proveedor/insertar**",
                                                                "/salida/obtener**",
                                                                "tipoDevolucion/obtener**",
                                                                "/tipoDevolucion/insertar**",
                                                                "/tipoPago/insertar**",
                                                                "/tipoUsuario/obtener**", "/tipoUsuario/insertar**",
                                                                "/unidadMedida/insertar**",
                                                                "/usuario/obtener**", "/usuario/insertar**",
                                                                "/usuario/{id}**", "/usuario/{id}/actualizar**",
                                                                "/usuario/estado/{estado}**")
                                                .hasAnyRole("ADMIN")
                                                .requestMatchers("/producto/obtener/**",
                                                                "/cliente/obtener/**",
                                                                "/cliente/insertar**",
                                                                "/devolucion/insertar/**",
                                                                "/detalleSalida/insertar/**",
                                                                "/unidadMedida/obtener**",
                                                                "tipoPago/obtener**",
                                                                "/salida/insertar**",
                                                                "/api/reniec/dni**")
                                                .hasAnyRole("ADMIN", "VENDEDOR")
                                                .anyRequest().hasRole("ADMIN") // Por defecto, solo ADMIN
                                )
                                .sessionManagement(sess -> sess
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Política de
                                                                                                        // creación de
                                                                                                        // sesiones
                                )
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder())
                                .and()
                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
