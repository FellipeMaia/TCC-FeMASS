/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author fmaia
 */

public class Dados implements Serializable {
    private final Calendar dataLeitura;
    private final String nome;
    private final Double temperatura;
    private final Double lumix;
    private final Double fumaca;

    public Dados(String nome,Double temperatura, Double lumix, Double fumaca) {
        this.dataLeitura = Calendar.getInstance();
        this.nome = nome;
        this.temperatura = temperatura;
        this.lumix = lumix;
        this.fumaca = fumaca;
    }
    
    public Dados(Long data,String nome,Double temperatura, Double lumix, Double fumaca) {
        this.dataLeitura = Calendar.getInstance();
        this.dataLeitura.setTimeInMillis(data);
        this.nome = nome;
        this.temperatura = temperatura;
        this.lumix = lumix;
        this.fumaca = fumaca;
    }

    public Calendar getDataLeitura() {
        return (Calendar) dataLeitura.clone();
    }

    public String getNome() {
        return nome;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getLumix() {
        return lumix;
    }

    public double getFumaca() {
        return fumaca;
    }
    

    @Override
    public String toString() {
        return "Dados{" + "nome=" + nome + ", temperatura=" + temperatura + ", lumix=" + lumix + ", fuma\u00e7a=" + fumaca + '}';
    }
   
    
}
