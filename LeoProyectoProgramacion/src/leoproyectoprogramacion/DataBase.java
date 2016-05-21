/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leoproyectoprogramacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import oracle.jdbc.driver.OracleDriver;

/**
 * Clase para trabajar con una base de datos de mysql o oracle
 * @author Leo
 * @version 21/05/2016
 */
public class DataBase {

    String bd;
    String login;
    String password;
    String servidor;
    Connection conexion;

    /**
     * Metodo constructor con parametros.
     *
     * @param bd El parametro bd define el nombre de la base de datos
     * @param login El parametro login define el nombre  del usuario de la base de datos
     * @param password El parametro password define la contraseña de la base de datos
     * @param servidor El parametro servidor define el servidor de la base de datos
     */
    public DataBase(String bd, String login, String password, String servidor) {

        this.bd = bd;
        this.login = login;
        this.password = password;
        this.servidor = servidor;
    }

    /**
     * Metodo que abre la conexión con la base de datos.
     * @return trueo o false dependiendo de si se ha podido conectar o no a la base de datos
     */
    public boolean abrirConexion() {

        boolean estado = false;

        try {
            // Cargar el driver.
            Class.forName("com.mysql.jdbc.Driver");//mysql
            DriverManager.registerDriver( new OracleDriver());//oracle

            // Crear conenection a la base de datos.
            conexion = DriverManager.getConnection(servidor + bd, login, password);
            estado = true;
            System.out.println("Conectado");
        } catch (SQLException e) {
            System.out.println("SQL Exception:\n" + e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception:\n" + e.toString());
        } catch (Exception e) {
            System.out.println("Exception:\n" + e.toString());
        }

        return estado;
    }

    /**
     * Metodo que cierra la conexion con la base de datos.
     */
    public void cerrarConexion() {

        try {
            conexion.close();
            System.out.println("Desconectado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metodo de la clase DataBase
     * @param statement El parametro statement define una sentencia
     * @return 
     */
    public int ejecutaUpdate(String statement) {
        int n = 0;
        try {
            Statement st = conexion.createStatement();
            System.out.println("La sentencia es: " + statement);
            n = st.executeUpdate(statement);
            System.out.println("Se ha ejecutado correctamente");
        } catch (SQLException ex) {
            System.out.println("SQL Exception:\n" + ex.getMessage());
        }
        return n;
    }
    /**
     * Metodo de la clase DataBase
     * @param consulta El parametro consulta define una sentenia
     * @return 
     */
    public ResultSet ejecutaConsulta(String consulta) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conexion.createStatement();
            rs = st.executeQuery(consulta);
        } catch (SQLException ex) {
            System.out.println("Error sql: " + ex.getMessage());
        }
//        try {
//            st.close();
//        } catch (SQLException ex) {
//            System.out.println("Error sql: " + ex.getMessage());
//        }
        return rs;
    }

    /**
     * Metodo de la clase DataBase
     * @param dniBuscar El parametro dniBuscar define un dni de uno de los voluntarios 
     * @return true o false dependiendo del resultado de la consulta
     */
    public boolean buscaRegistro(String dniBuscar) {
        ResultSet rs;
        PreparedStatement st;
        //sustituimos la variable por un ?
        String sentencia = "SELECT * from asociacion where dni= ?";// and edad= ?
        System.out.println(sentencia);
        try {
            st = conexion.prepareCall(sentencia);
            //pasamos los valores a cada uno de los interrogantes
            // comenzamos numerando por el 1
            st.setString(1, dniBuscar);
//            st.setInt(2, 20);
            rs = st.executeQuery();
            if(rs.isBeforeFirst()){
                return false;
//                VentanaListado vl = new VentanaListado(rs);
            }else{
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error con la base de datos: " + ex.getMessage());
        }
        return true;
    }
    
    /**
     * Metodo de la clase DataBase
     * @param rs El parametro rs define una sentenia
     */
    public void recorreResultado(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3)
                        + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6)
                 + "\t" + rs.getString(7) + "\t" + rs.getString(8) + "\t" + rs.getString(9)
                 + "\t" + rs.getString(10));
            }
        } catch (SQLException ex) {
            System.out.println("Error sql: " + ex.getMessage());
        }
    }
    /**
     * Metodo de la clase DataBase
     * @param rs El parametro consulta define una sentenia
     * @param voluntarios El parametro voluntarios define un ArrayList de voluntario generado con los datos 
     *      de la base de datos
     */
    public void pasarDatos(ResultSet rs, ArrayList voluntarios){
        try {
            while (rs.next()) {
                Voluntario v1= new Voluntario(rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5));
                v1.setTelefono(rs.getString(7));
                v1.setHorasDedicadas(Integer.parseInt(rs.getString(10)));
                voluntarios.add(v1);
            }
        } catch (SQLException ex) {
            System.out.println("Error sql: " + ex.getMessage());
        }
    }

}
