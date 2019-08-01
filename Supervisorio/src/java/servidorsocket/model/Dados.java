/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.model;

import java.util.Calendar;
import java.util.Objects;
import servidorsocket.factory.FactoryCalendario;

/**
 *
 * @author fmaia
 */

public class Dados {
    private final Calendar dataLeitura;
    private final Double temperatura;
    private final Double lumix;
    private final Double fumaca;

    public Dados(Double temperatura, Double lumix, Double fumaca) {
        this.dataLeitura = Calendar.getInstance();
        this.temperatura = (temperatura==Double.NaN?0:temperatura);
        this.lumix = lumix;
        this.fumaca = fumaca;
    }
    
    public Dados(Long data,Double temperatura, Double lumix, Double fumaca) {
        this.dataLeitura = Calendar.getInstance();
        this.dataLeitura.setTimeInMillis(data);
        this.temperatura = (temperatura==Double.NaN?0:temperatura);
        this.lumix = lumix;
        this.fumaca = fumaca;
    }
    
    public Dados(String data,Double temperatura, Double lumix, Double fumaca) {
        this.dataLeitura = (FactoryCalendario.toDBCalendar(data));
        this.temperatura = (temperatura==Double.NaN?0:temperatura);
        this.lumix = lumix;
        this.fumaca = fumaca;
    }

    public Calendar getDataLeitura() {
        return (Calendar) dataLeitura.clone();
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
        return "Dados{ temperatura=" + temperatura + ", lumix=" + lumix + ", fuma\u00e7a=" + fumaca + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dados other = (Dados) obj;
        if (!Objects.equals(this.dataLeitura, other.dataLeitura)) {
            return false;
        }
        if (!Objects.equals(this.temperatura, other.temperatura)) {
            return false;
        }
        if (!Objects.equals(this.lumix, other.lumix)) {
            return false;
        }
        if (!Objects.equals(this.fumaca, other.fumaca)) {
            return false;
        }
        return true;
    }
 
}
