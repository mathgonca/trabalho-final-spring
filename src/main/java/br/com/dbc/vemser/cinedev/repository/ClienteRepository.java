package br.com.dbc.vemser.cinedev.repository;

import br.com.dbc.vemser.cinedev.entity.Cliente;
import br.com.dbc.vemser.cinedev.exception.BancoDeDadosException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ClienteRepository implements Repositorio<Integer, Cliente> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ID_CLIENTE.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Cliente adicionar(Cliente cliente) throws BancoDeDadosException {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cliente.setIdCliente(proximoId);

            String sql = "INSERT INTO CLIENTE\n" +
                    "(ID_CLIENTE, PRIMEIRO_NOME, ULTIMO_NOME, CPF, DATA_NASCIMENTO, EMAIL)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getPrimeiroNome());
            stmt.setString(3, cliente.getUltimoNome());
            stmt.setString(4, cliente.getCpf());
            stmt.setDate(5, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(6, cliente.getEmail());

            int res = stmt.executeUpdate();
            System.out.println("adcionarCliente.res=" + res);
            return cliente;
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

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM CLIENTE WHERE id_cliente = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            System.out.println("removerClientePorId.res=" + res);

            return res > 0;
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

    @Override
    public Cliente editar(Integer id, Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CLIENTE SET ");
            sql.append(" PRIMEIRO_NOME = ?, ");
            sql.append(" ULTIMO_NOME = ?, ");
            sql.append(" CPF = ?, ");
            sql.append(" DATA_NASCIMENTO = ?, ");
            sql.append(" EMAIL = ? ");
            sql.append(" WHERE ID_CLIENTE = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, cliente.getPrimeiroNome());
            stmt.setString(2, cliente.getUltimoNome());
            stmt.setString(3, cliente.getCpf());
            stmt.setDate(4, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(5, cliente.getEmail());
            stmt.setInt(6, id);

            int res = stmt.executeUpdate();
            System.out.println("editarCliente.res=" + res);

            return listarClientePorId(id).get();
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

    @Override
    public List<Cliente> listar() throws BancoDeDadosException {
        List<Cliente> clientes = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CLIENTE ORDER BY ID_CLIENTE";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(res.getInt("ID_CLIENTE"));
                cliente.setPrimeiroNome(res.getString("PRIMEIRO_NOME"));
                cliente.setUltimoNome(res.getString("ULTIMO_NOME"));
                cliente.setCpf(res.getString("CPF"));
                cliente.setDataNascimento(res.getDate("DATA_NASCIMENTO").toLocalDate());
                cliente.setEmail(res.getString("EMAIL"));
                clientes.add(cliente);
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
        return clientes;
    }

    public Optional<Cliente> listarClientePorCPF(String cpf) throws BancoDeDadosException {
        Optional<Cliente> clienteOptional = Optional.empty();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CLIENTE c");
            sql.append(" WHERE c.CPF = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, cpf);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(res.getInt("ID_CLIENTE"));
                cliente.setPrimeiroNome(res.getString("PRIMEIRO_NOME"));
                cliente.setUltimoNome(res.getString("ULTIMO_NOME"));
                cliente.setCpf(res.getString("CPF"));
                cliente.setDataNascimento(res.getDate("DATA_NASCIMENTO").toLocalDate());
                cliente.setEmail(res.getString("EMAIL"));

                clienteOptional = Optional.of(cliente);
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
        return clienteOptional;
    }

    public Optional<Cliente> listarClientePorEmail(String cpf) throws BancoDeDadosException {
        Optional<Cliente> clienteOptional = Optional.empty();
        Cliente cliente = new Cliente();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CLIENTE c");
            sql.append(" WHERE c.EMAIL = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, cpf);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                cliente.setIdCliente(res.getInt("ID_CLIENTE"));
                cliente.setPrimeiroNome(res.getString("PRIMEIRO_NOME"));
                cliente.setUltimoNome(res.getString("ULTIMO_NOME"));
                cliente.setCpf(res.getString("CPF"));
                cliente.setDataNascimento(res.getDate("DATA_NASCIMENTO").toLocalDate());
                cliente.setEmail(res.getString("EMAIL"));

                clienteOptional = Optional.of(cliente);
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
        return clienteOptional;
    }

    public Optional<Cliente> listarClientePorId(int id) throws BancoDeDadosException {
        Optional<Cliente> cliente = Optional.empty();
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CLIENTE c");
            sql.append(" WHERE c.ID_CLIENTE = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Cliente clienteCache = new Cliente();

                clienteCache.setIdCliente(res.getInt("ID_CLIENTE"));
                clienteCache.setPrimeiroNome(res.getString("PRIMEIRO_NOME"));
                clienteCache.setUltimoNome(res.getString("ULTIMO_NOME"));
                clienteCache.setCpf(res.getString("CPF"));
                clienteCache.setDataNascimento(res.getDate("DATA_NASCIMENTO").toLocalDate());
                clienteCache.setEmail(res.getString("EMAIL"));

                cliente = Optional.of(clienteCache);
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

        return cliente;
    }
}
