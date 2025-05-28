package com.guilhermeguida.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
    public static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localhost:3306/sistemacarros";
    private static final String USUARIO = "root";
    private static final String SENHA = "1234";

    public static Connection getConexao() throws ErroSistema{
        if (conexao == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            } catch (ClassNotFoundException e) {
                throw new ErroSistema("Não foi possivel conectar ao banco de dados!", e);
            } catch (SQLException e) {
                throw new ErroSistema("Driver do banco de dados não foi encontrado!",e);
            }
        }
        return conexao;
    }

    public static void fecharConexao() throws ErroSistema{
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException e) {
                throw new ErroSistema("Erro ao fechar conexão com o banco de dados!",e);
            }
        }
    }
}
