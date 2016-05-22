/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leoproyectoprogramacion;

import interfaz.Ventana;

/**
 *
 * @author Leo
 */
public class LeoProyectoProgramacion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String servidor = "jdbc:oracle:thin:@localhost:";
        String bd = "1521:xe";
        String user = "damlocal";
        String password = "case";
        DataBase db = new DataBase(bd, user, password, servidor);
        if (db.abrirConexion()) {
            Ventana v=new Ventana(db);
        }
    }
    
}
