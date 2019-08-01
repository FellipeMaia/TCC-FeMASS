/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.factory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author fmaia
 */
public class FactoryCharjsData {
    
    
    private List<FactoryDatasets> datasets = new ArrayList<>();
    private String label;

    public FactoryCharjsData(List<Calendar> label) {
        this.setLabelCalendar(label);
    }

    public void setLabelCalendar(List<Calendar> label){
        String Label = "";
        for(Calendar c: label){
            Label = Label +"\""+ 
                    FactoryCalendario.toFormatString(c, "dd-MM-yyyy hh:mm")+
                    "\", ";
        }
        this.label = Label.substring(0, Label.length()-2);
    }

    public void addFactoryDatasets(String nameLine,List<Double> listDados){
        this.datasets.add(new FactoryDatasets(nameLine, listDados));
    }
    
    public String getCharjsData(){
        String data = 
                "{\n" +
"                    labels: ["+this.label+"],\n" +
"                    datasets: [";
        for(FactoryDatasets d: this.datasets){
            data += d.getDatasets();
            data += ",";
        } 
        data = data.substring(0, data.length()-1);
        data += "]\n}";
        return data;
    }
    
}
