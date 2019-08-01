/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import servidorsocket.dao.DaoDados;
import servidorsocket.factory.FactoryCharjsData;
import servidorsocket.model.Local;


/**
 *
 * @author fmaia
 */

@ManagedBean
public class ChartBean implements Serializable{
    
    private List<Local> listLocal;
    
    public void buscarDados() {
        try {
            this.listLocal = DaoDados.pesquisar();
        } catch (SQLException ex) {
            Logger.getLogger(ChartBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChartBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getFactoryCharjsDataTemp(){
        FactoryCharjsData charjsData;
        charjsData = new FactoryCharjsData(DaoDados.getLestRead());
        for(Local l : this.listLocal){
            charjsData.addFactoryDatasets(l.getNome(), l.getListTemp());
        }
        return charjsData.getCharjsData();
    }
    
    public String getFactoryCharjsDataLum(){
        FactoryCharjsData charjsData;
        charjsData = new FactoryCharjsData(DaoDados.getLestRead());
        for(Local l : this.listLocal){
            charjsData.addFactoryDatasets(l.getNome(), l.getListLum());
        }
        return charjsData.getCharjsData();
    }
    
    public String getFactoryCharjsDataFumaca(){
        FactoryCharjsData charjsData;
        charjsData = new FactoryCharjsData(DaoDados.getLestRead());
        for(Local l : this.listLocal){
            charjsData.addFactoryDatasets(l.getNome(), l.getListFumaca());
        }
        return charjsData.getCharjsData();
    }
    
}




