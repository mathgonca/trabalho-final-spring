package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.Filme;
import br.com.dbc.vemser.cinedev.entity.enums.Idioma;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilmeRepository implements Repositorio<Integer, Filme> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ID_FILME.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Filme adicionar(Filme filme) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            Integer chaveId = this.getProximoId(conexao);
            filme.setIdFilme(chaveId);
            String sql = "INSERT INTO FILME (ID_FILME, NOME, IDIOMA, CLASSIFICACAO, DURACAO)\n" +
                    "VALUES (?, ?, ?, ?, ?)\n";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, filme.getIdFilme());
            pst.setString(2, filme.getNome());
            pst.setString(3, filme.getIdioma().getIdioma());
            pst.setInt(4, filme.getClassificacaoEtaria());
            pst.setInt(5, filme.getDuracao());
            int ret = pst.executeUpdate();
            if (ret == 0) {
                System.out.println("Não foi possivel realizar o cadastramento!");
            }
            System.out.println("O Filme foi cadastrado com sucesso!");
            return filme;
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
            String sql = "DELETE FROM FILME WHERE ID_FILME = ?";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, id);
            int ret = pst.executeUpdate();
            if (ret == 0) {
                System.out.println("Não foi possível realizar a remoção do Filme!");
            }
            System.out.println("O filme foi removido com sucesso!");
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
    @Override
    public Filme editar(Integer id, Filme filme) throws BancoDeDadosException {
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "UPDATE FILME SET NOME = ?, IDIOMA = ?," +
                    "CLASSIFICACAO = ?, DURACAO = ? WHERE ID_FILME = ?";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setString(1, filme.getNome());
            pst.setString(2, filme.getIdioma().getIdioma());
            pst.setInt(3, filme.getClassificacaoEtaria());
            pst.setInt(4, filme.getDuracao());
            pst.setInt(5, id);
            filme.setIdFilme(id);
            int ret = pst.executeUpdate();
            if (ret == 0) {
                System.out.println("Não foi possível realizar a alteração do Filme!");
            }
            return filme;
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
    public List<Filme> listar() throws BancoDeDadosException {
        List<Filme> listaFilmes = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM FILME ORDER BY ID_FILME";
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                Filme filme = new Filme();
                filme.setIdFilme(res.getInt("ID_FILME"));
                filme.setNome(res.getString("NOME"));
                filme.setIdioma(Idioma.valueOf(res.getString("IDIOMA")));
                filme.setClassificacaoEtaria(res.getInt("CLASSIFICACAO"));
                filme.setDuracao(res.getInt("DURACAO"));
                listaFilmes.add(filme);
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
        return listaFilmes;
    }
    public List<String> listaFilmesPorCinema(int idCinema) throws BancoDeDadosException {
        List<String> listaNomeFilme = new ArrayList<>();
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "SELECT f.NOME FROM INGRESSO i" +
                    " INNER JOIN FILME f ON f.ID_FILME = i.ID_FILME" +
                    " WHERE i.ID_CINEMA = ?" +
                    " GROUP BY f.NOME";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idCinema);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                listaNomeFilme.add(res.getString("NOME"));
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
        return listaNomeFilme;
    }
    public List<LocalDateTime> listaHorariosDoFilme(int idFilme, int idCinema) throws BancoDeDadosException {
        List<LocalDateTime> listaHorario = new ArrayList<>();
        Connection conexao = null;
        try {
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "SELECT i.DATA_HORA FROM INGRESSO i" +
                    " WHERE i.ID_FILME = ? AND i.ID_CINEMA = ?" +
                    " GROUP BY i.DATA_HORA";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idFilme);
            stmt.setInt(2, idCinema);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                listaHorario.add(res.getTimestamp("DATA_HORA").toLocalDateTime());
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
        return listaHorario;
    }
}
