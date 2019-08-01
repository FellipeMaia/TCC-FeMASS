/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import servidorsocket.factory.FactoryCalendario;
import servidorsocket.model.Dados;
import servidorsocket.model.Local;

/**
 *
 * @author fmaia
 */
public class DaoDados {
    
    private static List<Calendar> listTempo = null;

    public static List<Calendar> getLestRead(){
        return DaoDados.listTempo;
    }
    
    
    public static List pesquisar() throws SQLException, ClassNotFoundException{
        String sql = 
            "select  TO_CHAR(d.data_leitura, 'YYYY-MM-DD HH24:MI:00') as tempo, \n" +
            "	d.nome, CAST(AVG(d.temperatura) as decimal(7,2)) as temperatura, \n" +
            "	 CAST(AVG(d.luminoso) as decimal(7,2)) as luminoso, CAST(AVG(d.fumaca) as decimal(9,4)) as fumaca \n" +
            "    from dado d\n" +
            "    GROUP BY TO_CHAR(DATA_LEITURA, 'YYYY-MM-DD HH24:MI:00'), d.nome\n" +
            "    ORDER BY tempo, nome";
        
        PreparedStatement stm = Conexao.getConexao().prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        List<String> stringTime = new ArrayList<>();
        List<Local> lista = new ArrayList<>();
        while(rs.next()){
            Local l = new Local(rs.getString("nome"));
            if(lista.contains(l)){
                l = lista.get(lista.indexOf(l));
            }else{
                lista.add(l);
            }
            l.add(
                new Dados(
                        rs.getString("tempo") , rs.getDouble("temperatura"), 
                        rs.getDouble("luminoso"), rs.getDouble("fumaca")
                )     
            );
            if(!stringTime.contains(rs.getString("tempo"))){
                stringTime.add(rs.getString("tempo"));
            }
        }
        DaoDados.listTempo = new ArrayList<>();
        for(String s: stringTime){
            DaoDados.listTempo.add(FactoryCalendario.toDBCalendar(s));
        }
        
        return lista;
    }
    
}
