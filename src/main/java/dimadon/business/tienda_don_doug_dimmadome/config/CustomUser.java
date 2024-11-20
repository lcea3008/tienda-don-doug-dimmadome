package dimadon.business.tienda_don_doug_dimmadome.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dimadon.business.tienda_don_doug_dimmadome.entities.Usuario;

public class CustomUser implements UserDetails {

    @Autowired
    private final Usuario usuario;

    public CustomUser(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getTipoUsuario().toString());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getContrasena();
    }

}