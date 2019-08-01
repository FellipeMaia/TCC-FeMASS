/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fmaia
 */
class Conexao {
    private static Connection conn = null;
    
    private Conexao() throws SQLException {
        Conexao.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/monitor","FMaia","killer1507");
    }
    
    public static Connection getConexao() throws SQLException{
        if(conn == null){
            new Conexao();
        }
        return conn;
    }
}
