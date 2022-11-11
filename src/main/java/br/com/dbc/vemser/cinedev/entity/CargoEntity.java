package br.com.dbc.vemser.cinedev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Cargo")
public class CargoEntity implements GrantedAuthority {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQ")
    @SequenceGenerator(name = "CARGO_SEQ", sequenceName = "seq_cargo", allocationSize = 1)
    @Id
    @Column(name = "id_cargo")
    private int idCargo;

    @Column(name = "nome")
    private String nome ;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cargo")
    private Set<UsuarioEntity> usuario;

    @Override
    public String getAuthority() {
        return nome;
    }
}
