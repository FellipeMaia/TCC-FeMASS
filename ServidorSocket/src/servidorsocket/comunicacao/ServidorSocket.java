/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket.comunicacao;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidorsocket.dao.DaoDados;
import servidorsocket.model.Dados;

/**
 *
 * @author fmaia
 */
public class ServidorSocket implements Runnable {
    
    private Boolean TRUE = true;
    
    private final Integer port;
    private DatagramSocket serve;
    private DatagramPacket reader;
    private byte[] bufReader = new byte[256];
    private DatagramPacket write;
    private byte [] bufWriter = new byte[256];
    
    public ServidorSocket(Integer port) throws IOException {
        this.port = port;
    }
     
    public void close() throws Throwable{
        this.TRUE = false;
        this.serve.close();
    }
    
    @Override
    public void run() {       
        System.out.println("------ Run do ServidorSocket iniciado!!!\n");
        
        try {
            InetSocketAddress intSocketAddress = new InetSocketAddress("10.0.0.100", this.port);
            serve = new DatagramSocket(intSocketAddress);
            
            while (TRUE) {
                System.out.println("------ Aguardando mensagem!!!!\n");
                this.reader = new DatagramPacket(this.bufReader,this.bufReader.length);
                serve.receive(reader);
                System.out.println("----- Mensagem recebida!!!\n");
                String mensage = new String(this.reader.getData());
                if(mensage.length() > 0){
                    
                    if(mensage.startsWith("CONECT::")){
                        System.out.println(" ---- Conectado a "+this.reader.getSocketAddress().toString()+"\n");
                        this.write = new DatagramPacket(bufWriter, bufWriter.length,this.reader.getSocketAddress());
                        String t = "CONECTED::"+'\0';
                        write.setData(t.getBytes());
                        serve.send(write);
                    }else 
                        if(mensage.startsWith("MSG::")){
                            Double temp = Double.NaN;
                            String t = mensage.substring(mensage.lastIndexOf("TEMP::")+6,mensage.indexOf("LUZ::"));
                            if(!"NAN".equals(t.replace(" ",""))){
                                temp = Double.valueOf(mensage.substring(mensage.lastIndexOf("TEMP::")+6,mensage.indexOf("LUZ::")));
                            }
                            Dados dados = new Dados(
                                    mensage.substring(mensage.lastIndexOf("NAME::")+6,mensage.indexOf("TEMP::")),
                                    temp,
                                    Double.valueOf(mensage.substring(mensage.lastIndexOf("LUZ::")+5,mensage.indexOf("FUM::"))),
                                    Double.valueOf(mensage.substring(mensage.lastIndexOf("FUM::")+5,mensage.lastIndexOf("END")))
                            );
                            DaoDados.persistir(dados);
                        }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
                /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServidorSocket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServidorSocket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServidorSocket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServidorSocket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                ServidorSocket serve1 = new ServidorSocket(2000);
                new Thread(serve1).start();
            }catch (IOException ex) {
                Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}

