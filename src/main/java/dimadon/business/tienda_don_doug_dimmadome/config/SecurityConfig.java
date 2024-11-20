package dimadon.business.tienda_don_doug_dimmadome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dimadon.business.tienda_don_doug_dimmadome.services.ServiceUsuario;

@Configuration
public class SecurityConfig {

    @Autowired
    ServiceUsuario service;

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetailsService getDetailsService() {
        return (UserDetailsService) service;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(getDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
