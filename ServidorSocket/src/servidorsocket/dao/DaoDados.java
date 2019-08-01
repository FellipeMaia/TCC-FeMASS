/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import servidorsocket.factory.FactoryCalendario;
import servidorsocket.model.Dados;

/**
 *
 * @author fmaia
 */
public class DaoDados {
    
    Connection conn;

    public DaoDados() throws SQLException {
        this.conn = Conexao.getConexao();
    }
    
    public static void persistir(Dados dados) throws SQLException{
        String sql = 
            "INSERT INTO DADO (data_leitura,NOME,TEMPERATURA,LUMINOSO,FUMACA) "
           + "values ('"+
                FactoryCalendario.toDBFormat(
                    dados.getDataLeitura(),FactoryCalendario.DATA_TIME_MILLISECOND
                )
            +"',?,?,?,?)";
        PreparedStatement stm = Conexao.getConexao().prepareStatement(sql);
        stm.setString(1, dados.getNome());
        stm.setDouble(2, dados.getTemperatura());
        stm.setDouble(3, dados.getLumix());
        stm.setDouble(4, dados.getFumaca());
        System.out.println(stm.toString());
        stm.execute();
    }
    
}
