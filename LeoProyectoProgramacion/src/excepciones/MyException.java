/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Clase para lanzar mis propias excepciones
 * @author Leo
 * @version 21/05/2016
 */
public class MyException extends Exception {
    public MyException(String mensaje) {
        super(mensaje);
    }
    
}
