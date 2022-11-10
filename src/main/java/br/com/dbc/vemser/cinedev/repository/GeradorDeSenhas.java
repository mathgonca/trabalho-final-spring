package br.com.dbc.vemser.cinedev.repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class GeradorDeSenhas {

    public static void main(String[] args) {

        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
//        String senha = sCryptPasswordEncoder.encode("123");
//        System.out.println(senha);

        String senha = "123";

        String senha2 = "$e0801$drP1lh89Cky2j7lwePorT4GSyXU7gtiPXB2SpSvSnif51/qnoIjPP5rNsdrv9isqqQPzdXu/s9DTmUBWfICExw==$EqABe9u5Vq/SWKF/gHxU6JinBioFAD8zEYLzpqSTe1M=";
        System.out.println(sCryptPasswordEncoder.matches(senha, senha2));

    }
}
