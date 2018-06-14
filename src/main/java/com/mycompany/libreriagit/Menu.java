
package com.mycompany.libreriagit;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.errors.GitAPIException;
/**
 * Esta clase contiene el metodo que nos permite visualizar un menu de opciones
 * para el proyecto
 * @author Damian
 */

public class Menu {
    /**
     * Este metodo nos muestra un menu donde aparecen todas las funciones del proyecto,
     * a escoger la que deseas, hasta que desees cerrar el programa
     */
     public void escogerMenu(){
        int op;
        
        do{
        
        op = Integer.parseInt(JOptionPane.showInputDialog("1)Crear repositorio en Github\n"
                                                             + "2)Clonar el proyecto desde Github\n"
                                                             + "3)Inicializar el repositorio\n"
                                                             + "4)Crear un commit\n"
                                                             + "5)Realizar un push\n"
                                                             + "6)Salir"));
        
        switch(op){
            case 1: 
            try {
                Metodos.crearRepositorio();
            } catch (IOException ex) {
                System.out.println("ERRO!"+ex.getMessage());
            }
                break;
                
            case 2: try {
                Metodos.clonar();
            } catch (GitAPIException ex) {
                System.out.println("ERRO!"+ex.getMessage());
            }    
            
                break;
                
            case 3: 
                try {
                Metodos.inicializarRepositorio();
            } catch (GitAPIException ex) {
                System.out.println("ERRO!"+ex.getMessage());
            }
            
                break;
                
            case 4: 
                 try {
                Metodos.crearCommit();
            } catch (IOException ex) {
                System.out.println("ERRO!"+ex.getMessage());
            } catch (GitAPIException ex) {
             System.out.println("ERRO"+ex.getMessage());
            }
           
                break;
                
            case 5:
            try {
                Metodos.hacerPush();
            } catch (IOException ex) {
               System.out.println("ERRO!"+ex.getMessage());
            } catch (GitAPIException ex) {
                 System.out.println("ERRO!"+ex.getMessage());
            } catch (URISyntaxException ex) {
                System.out.println("ERRO!"+ex.getMessage());
            }
                
                break;
                
            case 6: System.exit(0);
                break;
                
            default: JOptionPane.showMessageDialog(null, "La opcion introducida no es válida");
        }
        } while(op!=6);
                                                          
    }
    
}
