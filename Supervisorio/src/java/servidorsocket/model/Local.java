/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author fmaia
 */
public class Local {
    private final String nome;
    private final List<Dados> listDados = new ArrayList<>();

    public Local(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public List<Dados> getListDados() {
        return listDados;
    }
    
    public Dados remove(int posicao) {
        return this.listDados.remove(posicao);
    }
    
    public void remove(Dados posicao) {
        this.listDados.remove(posicao);
    }
    
    public void add(Dados dados) {
        this.listDados.add(dados);
    }
    
    public List getListTemp(){
        List<Double> listTemp = new ArrayList<>();
        for(Dados d:this.listDados){
            listTemp.add(d.getTemperatura());
        }
        return listTemp;
    }
    
    public List getListLum(){
        List<Double> listTemp = new ArrayList<>();
        for(Dados d:this.listDados){
            listTemp.add(d.getLumix());
        }
        return listTemp;
    }
    
    public List getListFumaca(){
        List<Double> listTemp = new ArrayList<>();
        for(Dados d:this.listDados){
            listTemp.add(d.getFumaca());
        }
        return listTemp;
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
        final Local other = (Local) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
       
}
