package br.com.dbc.vemser.cinedev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQUENCIA")
    @SequenceGenerator(name = "USUARIO_SEQUENCIA", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String senha;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    private CargoEntity cargo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ToString.Exclude
    private CinemaEntity cinema;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ToString.Exclude
    private ClienteEntity cliente;

    // FIXME implementar métodos de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<CargoEntity> cargo = new ArrayList<>();
        return cargo; // por enquanto
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
