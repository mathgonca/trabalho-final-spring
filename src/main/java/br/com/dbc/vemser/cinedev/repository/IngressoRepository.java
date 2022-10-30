package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.dto.IngressoCompradoDTO;
import br.com.dbc.vemser.cinedev.entity.Ingresso;
import br.com.dbc.vemser.cinedev.entity.enums.Disponibilidade;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IngressoRepository {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public Integer getProximoId(Connection connection) throws SQLException, SQLException {
        String sql = "SELECT SEQ_ID_INGRESSO.nextval seq FROM DUAL";
        Statement stat = connection.createStatement();
        ResultSet rest = stat.executeQuery(sql);
        if(rest.next()){
            rest.getInt("seq");
        }
        return null;
    }
    public IngressoCompradoDTO getIngressoResultSet(ResultSet res) throws SQLException {
        IngressoCompradoDTO ingresso = new IngressoCompradoDTO();
        ingresso.setIdIngressoComprado(res.getInt("ID_INGRESSO"));
        ingresso.setNomeCliente(res.getString("CLIENTE"));
        ingresso.setNomeFilme(res.getString("FILME"));
        ingresso.setDataHora(res.getTimestamp("DATA_HORA").toLocalDateTime());
        ingresso.setNomeCinema(res.getString("CINEMA"));
        return ingresso;
    }

    public Ingresso adicionar(Ingresso ingresso) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = conexaoBancoDeDados.getConnection();
            Integer chaveId = this.getProximoId(conexao);
            ingresso.setIdIngresso(chaveId);
            String sql = "INSERT INTO INGRESSO (ID_INGRESSO,ID_CINEMA, ID_FILME, ID_CLIENTE, VALOR, DATA_HORA, DISPONIBLIDADE)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, ingresso.getIdIngresso());
            pst.setInt(2,ingresso.getIdCinema());
            pst.setInt(3,  ingresso.getIdFilme());
            pst.setInt(4, ingresso.getIdCliente());
            pst.setDouble(5, ingresso.getPreco());
            pst.setTimestamp(7, Timestamp.valueOf(ingresso.getDataHora()));
            pst.setString(8, ingresso.getDisponibilidade().isDisponibilidade());
            int ret = pst.executeUpdate();
            if(ret==0){
                System.out.println("Não foi possivel realizar a compra!");
            }
            System.out.println("Parabéns! Você realizou sua compra!");
            return ingresso;
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM INGRESSO WHERE ID_INGRESSO = ?";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, id);
            int ret = pst.executeUpdate();
            if(ret==0){
                System.out.println("Não foi possível realizar o cancelamento do Ingresso!");
            }
            System.out.println("O Ingresso foi cancelado com sucesso!");
            return ret>0;
        }catch (SQLException e) {
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
    public IngressoCompradoDTO editar(Integer idCliente, Integer idIngresso) throws BancoDeDadosException {
        Connection conexao = null;
        try{
            conexao = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE INGRESSO SET ID_CLIENTE = ?, VALOR = ?, DISPONIBLIDADE = ? WHERE ID_INGRESSO = ?";

            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, idCliente);
            pst.setDouble(2, 30);
            pst.setString(3, "N");
            pst.setInt(4, idIngresso);

            int ret = pst.executeUpdate();
            if (ret == 0) {
                System.out.println("Não foi possível realizar a alteração do seu Ingresso!");
            }
            System.out.println("O Ingresso foi alterado com sucesso!");
            return listarIngressoCompradoPorId(idIngresso);
        }catch (SQLException e) {
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

    public List<Ingresso> listarIngressos() throws BancoDeDadosException {
        List<Ingresso> listarIngresso = new ArrayList<>();
        Connection conexao = null;
        try{
            conexao = conexaoBancoDeDados.getConnection();
            Statement stmt = conexao.createStatement();
            String sql = "SELECT * FROM INGRESSO WHERE DISPONIBLIDADE = 'S' ORDER BY ID_INGRESSO";
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                Ingresso ingresso = new Ingresso();
                ingresso.setIdIngresso(res.getInt("ID_INGRESSO"));
                ingresso.setIdFilme(res.getInt("ID_FILME"));
                ingresso.setIdCliente(res.getInt("ID_CLIENTE"));
                ingresso.setIdCinema(res.getInt("ID_CINEMA"));
                ingresso.setPreco(res.getDouble("VALOR"));
                ingresso.setDataHora(res.getTimestamp("DATA_HORA").toLocalDateTime());
                ingresso.setDisponibilidade(Disponibilidade.valueOf(res.getString("DISPONIBLIDADE")));
                listarIngresso.add(ingresso);
            }
        }catch (SQLException e) {
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
        return listarIngresso;
    }
    public List<Ingresso> listarIngressosComprados() throws BancoDeDadosException {
        List<Ingresso> listarIngresso = new ArrayList<>();
        Connection conexao = null;
        try{
            conexao = conexaoBancoDeDados.getConnection();
            Statement stmt = conexao.createStatement();
            String sql = "SELECT * FROM INGRESSO WHERE DISPONIBLIDADE = 'N' ORDER BY ID_INGRESSO";
            ResultSet res = stmt.executeQuery(sql);
            while(res.next()){
                Ingresso ingresso = new Ingresso();
                ingresso.setIdIngresso(res.getInt("ID_INGRESSO"));
                ingresso.setIdFilme(res.getInt("ID_FILME"));
                ingresso.setIdCliente(res.getInt("ID_CLIENTE"));
                ingresso.setIdCinema(res.getInt("ID_CINEMA"));
                ingresso.setPreco(res.getDouble("VALOR"));
                ingresso.setDataHora(res.getTimestamp("DATA_HORA").toLocalDateTime());
                ingresso.setDisponibilidade(Disponibilidade.valueOf(res.getString("DISPONIBLIDADE")));
                listarIngresso.add(ingresso);
            }
        }catch (SQLException e) {
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
        return listarIngresso;
    }

    public List<IngressoCompradoDTO> listarIngressoCompradoPorCliente(Integer id) throws  BancoDeDadosException {
        List<IngressoCompradoDTO> ingressosComprados = new ArrayList<>();
        Connection conexao = null;

        try{
            conexao = conexaoBancoDeDados.getConnection();

            String sql =
                    "SELECT  CT.PRIMEIRO_NOME AS CLIENTE, F.NOME AS FILME, C.NOME AS CINEMA,ID_INGRESSO,I.DATA_HORA FROM INGRESSO I\n" +
                            "INNER JOIN CLIENTE CT ON I.ID_CLIENTE = I.ID_CLIENTE \n" +
                            "INNER JOIN FILME F ON F.ID_FILME = I.ID_FILME  \n" +
                            "INNER JOIN CINEMA C ON C.ID_CINEMA = I.ID_CINEMA WHERE CT.ID_CLIENTE = I.ID_CLIENTE AND CT.ID_CLIENTE = ? " +
                            "ORDER BY I.DATA_HORA";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();
            while(res.next()){
                IngressoCompradoDTO ingresso = getIngressoResultSet(res);
                ingressosComprados.add(ingresso);
            }
            return ingressosComprados;

        }catch (SQLException e) {
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
    public IngressoCompradoDTO listarIngressoCompradoPorId(Integer id) throws  BancoDeDadosException {
        IngressoCompradoDTO ingressosComprado = new IngressoCompradoDTO();
        Connection conexao = null;

        try{
            conexao = conexaoBancoDeDados.getConnection();

            String sql =
                    "SELECT  CT.PRIMEIRO_NOME AS CLIENTE, F.NOME AS FILME, C.NOME AS CINEMA,ID_INGRESSO,I.DATA_HORA FROM INGRESSO I\n" +
                            "INNER JOIN CLIENTE CT ON I.ID_CLIENTE = I.ID_CLIENTE \n" +
                            "INNER JOIN FILME F ON F.ID_FILME = I.ID_FILME  \n" +
                            "INNER JOIN CINEMA C ON C.ID_CINEMA = I.ID_CINEMA WHERE CT.ID_CLIENTE = I.ID_CLIENTE AND ID_INGRESSO = ? " +
                            "ORDER BY I.DATA_HORA";

            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();
            if (res.next()) {
            ingressosComprado = getIngressoResultSet(res);
            }
            return ingressosComprado;

        }catch (SQLException e) {
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

    public Ingresso listarIngressoPeloId(int idIngresso) throws BancoDeDadosException {
        Optional<Ingresso> ingressoOptional = Optional.empty();

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM INGRESSO i");
            sql.append(" WHERE i.ID_INGRESSO = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, idIngresso);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Ingresso ingresso = new Ingresso();
                ingresso.setIdIngresso(res.getInt("ID_INGRESSO"));
                ingresso.setIdFilme(res.getInt("ID_FILME"));
                ingresso.setIdCliente(res.getInt("ID_CLIENTE"));
                ingresso.setPreco(res.getDouble("VALOR"));
                ingresso.setDataHora(res.getTimestamp("DATA_HORA").toLocalDateTime());
                ingresso.setDisponibilidade(Disponibilidade.valueOf(res.getString("DISPONIBLIDADE")));

                ingressoOptional = Optional.of(ingresso);
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
        return ingressoOptional.get();
    }

}
