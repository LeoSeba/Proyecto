/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leoproyectoprogramacion;

/**
 * Clase voluntario
 * @author Leo
 * @version 21/05/2016
 */
public class Voluntario {
    String dni, nombre, apellido, email, telefono;
    
    int horasDedicadas;
    
    /**
     * Constructor de la clase voluntario, que pide solo los atributos que son obligatorios
     * en la base de datos los opcionales los inicializa a null o a 0.
     * @param dni El parametro dni define el dni del voluntario
     * @param nombre El parametro nombre define el nombre del voluntario
     * @param apellido El parametro apellido  define el apellido del voluntario
     * @param email El parametro email define el email del voluntario
     */
    public Voluntario(String dni, String nombre, String apellido, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = null;
        this.horasDedicadas = 0;
    }
    /**
     * Metodo de la clase voluntario 
     * @return el dni del voluntario
     */
    public String getDni() {
        return dni;
    }
    /**
     * Metodo de la clase voluntario que permite cambiar el dni del voluntario
     * @param dni El parametro dni define el dni del voluntario
     */
    public void setDni(String dni) {
        this.dni = dni;
    }
    /**
     * Metodo de la clase voluntario
     * @return el nommbre del voluntario
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Metodo de la clase voluntario que permite cambiar el nombre del voluntario
     * @param nombre El parametro nombre define el nombre del voluntario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Metodo de la clase voluntario
     * @return el apellido del voluntario
     */
    public String getApellido() {
        return apellido;
    }
    /**
     * Metodo de la clase voluntario que permite cambiar el apellido del voluntario
     * @param apellido El parametro apellido define el apellido del voluntario
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    /**
     * Metodo de la clase voluntario
     * @return el email del voluntario
     */
    public String getEmail() {
        return email;
    }
    /**
     * Metodo de la clase voluntario que permite cambiar el email del voluntario
     * @param email El parametro email define el email del voluntario
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Metodo de la clase voluntario
     * @return el telefono del voluntario
     */
    public String getTelefono() {
        return telefono;
    }
    /**
     * Metodo de la clase voluntario que permite cambiar el telefono del voluntario
     * @param telefono El parametro telefono define el telefono del voluntario
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    /**
     * Metodo de la clase voluntario
     * @return las horasDedicadas del voluntario
     */
    public int getHorasDedicadas() {
        return horasDedicadas;
    }
    /**
     * Metodo de la clase voluntario que permite cambiar las horasDedicadas del voluntario
     * @param horasDedicadas El parametro horasDedicadas define el numero de
     *      horas que dedica al mes el voluntario
     */
    public void setHorasDedicadas(int horasDedicadas) {
        this.horasDedicadas = horasDedicadas;
    }
          
}
