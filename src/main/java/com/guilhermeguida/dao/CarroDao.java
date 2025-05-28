package com.guilhermeguida.dao;

import com.guilhermeguida.model.Carro;
import com.guilhermeguida.util.ErroSistema;
import com.guilhermeguida.util.FabricaConexao;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDao {

    public void salvar(Carro carro) throws ErroSistema{
        try {
            Connection con = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(carro.getId() == null) {
                ps = con.prepareCall("INSERT INTO carro(modelo, fabricante, ano, cor) " +
                        "VALUES(?,?,?,?);");
            } else {
                ps = con.prepareStatement("UPDATE carro SET modelo=?, fabricante=?, ano=?, cor=? WHERE id=?");
                ps.setInt(5, carro.getId());
            }
            ps.setString(1, carro.getModelo());
            ps.setString(2, carro.getFabricante());
            ps.setInt(3, carro.getAno());
            ps.setString(4, carro.getCor());
            ps.executeUpdate();
            FabricaConexao.fecharConexao();
        } catch (SQLException e) {
            throw new ErroSistema("Erro ao salvar!", e);
        }
    }

    public List<Carro> buscar() throws ErroSistema{
        try {
        Connection con = FabricaConexao.getConexao();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM carro;");
            ResultSet rs = ps.executeQuery();
            List<Carro> carros = new ArrayList<Carro>();
            while (rs.next()) {
                Carro carro = new Carro();
                carro.setId(rs.getInt("id"));
                carro.setModelo(rs.getString("modelo"));
                carro.setFabricante(rs.getString("fabricante"));
                carro.setAno(rs.getInt("ano"));
                carro.setCor(rs.getString("cor"));
                carros.add(carro);
            }
            FabricaConexao.fecharConexao();
            return carros;
        } catch (SQLException e) {
            throw new ErroSistema("Erro ao buscar!", e);
        }
    }

    public void deletar(Integer idCarro) throws ErroSistema{
        try {
            Connection con = FabricaConexao.getConexao();
            PreparedStatement ps = con.prepareStatement("DELETE FROM carro WHERE id = ?;");
            ps.setInt(1, idCarro);
            ps.executeUpdate();
            FabricaConexao.fecharConexao();
        } catch (SQLException e) {
            throw new ErroSistema("Erro ao deletar!", e);
        }
    }
}