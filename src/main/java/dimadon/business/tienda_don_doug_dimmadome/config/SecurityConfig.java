package dimadon.business.tienda_don_doug_dimmadome.config;

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

import dimadon.business.tienda_don_doug_dimmadome.security.JwtAuthenticationFilter;
import dimadon.business.tienda_don_doug_dimmadome.services.ServiceUsuario;

@Configuration
public class SecurityConfig {

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private ServiceUsuario userDetailsService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.and())
                                .authorizeHttpRequests(authz -> authz
                                                // Public endpoints
                                                .requestMatchers("/auth/**").permitAll()

                                                // VENDEDOR and ADMIN endpoints
                                                .requestMatchers(
                                                                "/categoria/obtener/**",
                                                                "/producto/obtener/**", // Changed from
                                                                                        // /producto/obtener to include
                                                                                        // sub-paths
                                                                "/tipoDevolucion/obtener/**",
                                                                "/cliente/obtener/**",
                                                                "/cliente/insertar/**",
                                                                "/devolucion/insertar/**",
                                                                "/salida/insertar/**",
                                                                "/detalleSalida/insertar/**",
                                                                "/unidadMedida/obtener/**",
                                                                "/tipoPago/obtener/**",
                                                                "/api/reniec/dni/**")
                                                .hasAnyRole("ADMIN", "VENDEDOR")

                                                // ADMIN only endpoints
                                                .requestMatchers(
                                                                "/categoria/insertar/**",
                                                                "/cliente/{id}/estado/**",
                                                                "/detalleEntrada/**",
                                                                "/detalleSalida/obtener/**",
                                                                "/devolucion/obtener/**",
                                                                "/entrada/**",
                                                                "/kardex/**",
                                                                "/producto/insertar/**",
                                                                "/producto/{id}/**",
                                                                "/producto/{id}/estado/**",
                                                                "/producto/estado/**",
                                                                "/proveedor/**",
                                                                "/salida/obtener/**",
                                                                "/tipoDevolucion/insertar/**",
                                                                "/tipoPago/insertar/**",
                                                                "/tipoUsuario/**",
                                                                "/unidadMedida/insertar/**",
                                                                "/usuario/**")
                                                .hasRole("ADMIN")

                                                // Default policy
                                                .anyRequest().authenticated())
                                .sessionManagement(sess -> sess
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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