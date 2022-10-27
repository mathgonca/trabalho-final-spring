package br.com.dbc.vemser.cinedev.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConexaoBancoDeDados {


    @Value("${jdbc-string}")
    private String jdbcString;

    @Value("${jdbc-user}")
    private String user;

    @Value("${jdbc-pass}")
    private String pass;

    @Value("${jdbc-schema}")
    private String schema;

    public Connection getConnection() throws SQLException {
        //  String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;
        // jdbc:oracle:thin:@localhost:1521:xe
        Connection con = DriverManager.getConnection(jdbcString, user, pass);

        con.createStatement().execute("alter session set current_schema=" + schema);

        return con;
    }
}
