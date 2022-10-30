package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.Cinema;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CinemaRepository implements Repositorio<Integer, Cinema> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;


    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ID_CINEMA.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Cinema adicionar(Cinema cinema) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();

            Integer chaveID = this.getProximoId(conexao);
            cinema.setIdCinema(chaveID);

            String sql = "INSERT INTO CINEMA (ID_CINEMA, NOME, ESTADO, CIDADE)\n" +
                    "values (?, ?, ?, ?)";
            PreparedStatement pst = conexao.prepareStatement(sql);

            pst.setInt(1, cinema.getIdCinema());
            pst.setString(2, cinema.getNome());
            pst.setString(3, cinema.getEstado());
            pst.setString(4, cinema.getCidade());

            int ret = pst.executeUpdate();
            if (ret == 0) {
                System.out.println("Não foi possivel realizar o cadastro");
            }

            System.out.println("o cinema foi cadastrado com sucesso");
            return cinema;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    @Override

    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM CINEMA WHERE ID_CINEMA = ?";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, id);
            int ret = pst.executeUpdate();
            if (ret == 0) {
                System.out.println("Não foi possivel realizar a remoção do cinema");
            }
            System.out.println("O cinema foi removido com sucesso");
            return ret > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cinema editar(Integer id, Cinema cinema) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();


            String sql = "UPDATE CINEMA SET NOME = ?, ESTADO = ?," +
                    "CIDADE = ? WHERE ID_CINEMA = ?";

            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setString(1, cinema.getNome());
            pst.setString(2, cinema.getEstado());
            pst.setString(3, cinema.getCidade());
            pst.setInt(4, id);

            int ret = pst.executeUpdate();
            if (ret == 0) {
                System.out.println("Não foi possível realizar a alteração do Cinema!");
            }
            System.out.println("O Cinema foi alterado com sucesso!");
            return cinema;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Cinema> listar() throws BancoDeDadosException {
        List<Cinema> listaCinema = new ArrayList<>();
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Statement stat = conexao.createStatement();

            String sql = "SELECT * FROM CINEMA";

            ResultSet ret = stat.executeQuery(sql);
            while (ret.next()) {
                Cinema cinema = new Cinema();
                cinema.setIdCinema(ret.getInt("ID_CINEMA"));
                cinema.setNome(ret.getString("NOME"));
                cinema.setEstado(ret.getString("ESTADO"));
                cinema.setCidade(ret.getString("CIDADE"));
                listaCinema.add(cinema);
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaCinema;
    }

    public Optional<Cinema> listarCinemaId(int ID_CINEMA) throws BancoDeDadosException {
        Optional<Cinema> cinemaOptional = Optional.empty();
        Cinema cinema = new Cinema();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CINEMA");
            sql.append(" WHERE ID_CINEMA = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, ID_CINEMA);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                cinema.setIdCinema(res.getInt("ID_CINEMA"));
                cinema.setNome(res.getString("NOME"));
                cinema.setEstado(res.getString("ESTADO"));
                cinema.setCidade(res.getString("CIDADE"));
                listar().add(cinema);

                cinemaOptional = Optional.of(cinema);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cinemaOptional;
    }

    public Optional<Cinema> listarCinemaPeloNome(String nome) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CINEMA");
            sql.append(" WHERE NOME = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());
            stmt.setString(1, nome);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Cinema cinema = new Cinema();
                cinema.setIdCinema(res.getInt("ID_CINEMA"));
                cinema.setNome(res.getString("NOME"));
                cinema.setEstado(res.getString("ESTADO"));
                cinema.setCidade(res.getString("CIDADE"));
                listar().add(cinema);

                return Optional.of(cinema);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}