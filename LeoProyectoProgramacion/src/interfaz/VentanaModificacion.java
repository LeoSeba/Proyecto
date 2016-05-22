/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import excepciones.MyException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import leoproyectoprogramacion.DataBase;
import leoproyectoprogramacion.Voluntario;

/**
 * Clase VentanaModificacion
 * @author Leo
 * @version 22/05/2016
 */
public class VentanaModificacion extends JFrame implements ActionListener, WindowListener {

    JPanel contenedor;
    JButton botonAlta, botonFin;
    JTextField[] atributos;
    JLabel[] etiquetas;
    String textoEtiquetas[] = {"DNI: ", "Nombre: ", "Apellido: ", "Email: ", "Telefono: ",
        "  Horas Dedicadas: "};
    DataBase db;
    ArrayList<Voluntario> voluntarios;
    String dni;
    /**
     * Constructor de la clase VentanaModificacion
     * @param db El parametro db define la base de datos con la que vamos a trabajar
     * @param voluntarios El parametro voluntarios define un ArrayList de voluntarios
     * @param dni El parametro dni define el dni del voluntario al que se le va a modificar 
     *      algun dato
     */
    public VentanaModificacion(DataBase db, ArrayList voluntarios, String dni) {
        this.db = db;
        this.voluntarios = voluntarios;
        this.dni = dni;
        this.setTitle("Modificacion Voluntario");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(300, 300);
    }
    /**
     * metodo de la clase VentanaModificacion que inicializa los componentes de la ventana
     */
    private void initComponents() {

        atributos = new JTextField[textoEtiquetas.length];
        etiquetas = new JLabel[textoEtiquetas.length];
        //Utilizo todo el fondo del JFrame
        contenedor = (JPanel) this.getContentPane();
        //Inicializo un layout
        contenedor.setLayout(new GridLayout(textoEtiquetas.length + 1, 2, 5, 5));
        //Inicializo los objetos
        for (int x = 0; x < etiquetas.length; x++) {
            etiquetas[x] = new JLabel(textoEtiquetas[x]);
            atributos[x] = new JTextField();
            contenedor.add(etiquetas[x]);
            contenedor.add(atributos[x]);
        }
        botonAlta = new JButton("Aceptar");
        botonAlta.addActionListener(this);
        botonAlta.setActionCommand("aceptar");
        botonFin = new JButton("Cancelar");
        botonFin.addActionListener(this);
        botonFin.setActionCommand("cancelar");
        //los pongo en el contendor
        contenedor.add(botonAlta);
        contenedor.add(botonFin);
    }
    /**
     * metodo de la clase VentanaModificaion que borra los que se haya escrito en cada uno de los campos de texto
     */
    private void limpiaPantalla() {
        for (int x = 0; x < textoEtiquetas.length; x++) {
            atributos[x].setText(null);
        }
    }
    /**
     * metodo de la clase VentanaModificaion que sirve para crear mensajes de error
     * @param cadena El parametro cadena define el mensaje de error que se mostrara
     */
    private void ventanaError(String cadena) {

        JOptionPane.showMessageDialog(
                this, cadena,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * metodo de la clase VentanaModificaion modifica los datos al voluntario que tiene el mismo dni que 
     * el seleccionado
     */
    private void modificar() {

        Iterator<Voluntario> v1 = voluntarios.iterator();
        while (v1.hasNext()) {
            Voluntario n1 = v1.next();
            if (dni.equals(n1.getDni())) {
                campos(n1);
                //lo guardo en la base de datos
                String cadena = "UPDATE VOLUNTARIO SET DNI = '" + n1.getDni() + "', NOMBRE = '" + n1.getNombre()
                        + "',  APELLIDO = '" + n1.getApellido() + "',  EMAIL = '" + n1.getEmail() +
                        "',  TELEFONO = '" + n1.getTelefono() 
                        + "',  HORAS_DEDICADAS = '" + n1.getHorasDedicadas() +"' where DNI = '" +
                        dni + "'";
                db.ejecutaUpdate(cadena);
                JOptionPane.showMessageDialog(null, "Modificacion correcta");
            }

        }

        limpiaPantalla();
    }
    /**
     * metodo de la clase VentanaModificacion que comprueba lo que se ha escrito en el campo de texto de DNI
     * @return devuelve un String
     * @throws MyException 
     */
    private String dni() throws MyException {
        if (atributos[0].getText().length() > 0 && atributos[0].getText().length() < 10) {
//            if (buscaDNI(atributos[0].getText(), voluntarios)) {
            return atributos[0].getText();
//            } else {
//                ventanaError("DNI no válido, ya existe un voluntario con ese dni");
//                throw new Errorcito("Nombre no válido");
//            }

        } else {
            ventanaError("DNI no válido, tiene que tener entre 1 y 9 caracteres");
            throw new MyException("Nombre no válido");
        }
    }

  /*
     este metodo lo dejo comentado por que no se por que pero no estra al "if(dni.equals(n1.getDni())"
     cuando inserto un dni repetido, de todas formas el programa funciona por que salta el error de la
        base de datos
    */
//    /**
//     * metodo de la clase VentanaModificacion que omprueba que el dni que se escrito no este repetido
//     * @param dni El parametro dni define el dni del voluntario que se va ha dar de alta
//     * @param voluntarios El parametro volunarios define un ArrayList de voluntarios
//     * @return 
//     */
//    private static boolean buscaDNI(String dni, ArrayList voluntarios) {
//        boolean buscaDNI = true;
//        Iterator<Voluntario> v1 = voluntarios.iterator();
//        while (v1.hasNext()) {
//            Voluntario n1 = v1.next();
////            System.out.print(n1.getDni() + " / ");
//            if (dni.equals(n1.getDni())) {
//                System.out.print(n1.getDni() + " / ");
//                buscaDNI = false;
//            }
//
//        }
//        return buscaDNI;
//    }
    /**
      * metodo de la clase VentanaModificacion que en caso de que se haya escrito algo en los campos opcionales
      * actuliza los atributos correspondientes del voluntario
      * @param v1 El parametro v1 define el  Voluntario que se va a dar de alta
      */
    private void campos(Voluntario v1) {
        if (atributos[0].getText().length() > 0) {
            try {
                v1.setDni(dni());
            } catch (MyException ex) {
                System.out.println("dni no valido");
            }
        }

        if (atributos[1].getText().length() > 0) {
            try {
                v1.setNombre(nombre());
            } catch (MyException ex) {
                System.out.println("nombre no valido");
            }
        }

        if (atributos[2].getText().length() > 0) {
            try {
                v1.setApellido(apellido());
            } catch (MyException ex) {
                System.out.println("apellido no valido");
            }
        }

        if (atributos[3].getText().length() > 0) {
            try {
                v1.setEmail(email());
            } catch (MyException ex) {
                System.out.println("email no valido");
            }
        }

        if (atributos[4].getText().length() > 0) {
            try {
                v1.setTelefono(telefono());
            } catch (MyException ex) {
                System.out.println("telefono no valido");
            }
        }

        if (atributos[5].getText().length() > 0) {
            v1.setHorasDedicadas(Integer.parseInt(atributos[5].getText()));
        }
    }
    /**
      * metodo de la clase VentanaModificacion que comprueba lo que se ha escrito en el campo de texto de nombre
      * @return devuelve un String
      * @throws MyException 
      */
    private String nombre() throws MyException {
        if (atributos[1].getText().length() > 0 && atributos[1].getText().length() < 26) {
            return atributos[1].getText();
        } else {
            ventanaError("Nombre no válido, tiene que tener entre 1 y 25 caracteres");
            throw new MyException("Nombre no válido");
        }
    }
    /**
     * metodo de la clase VentanaModificacion que comprueba lo que se ha escrito en el campo de texto de apellido
     * @return devuelve un String
     * @throws MyException 
     */
    private String apellido() throws MyException {
        if (atributos[2].getText().length() > 0 && atributos[2].getText().length() < 51) {
            return atributos[2].getText();
        } else {
            ventanaError("Apellido no válido, tiene que tener entre 1 y 50 caracteres");
            throw new MyException("Apellido no válido");
        }
    }
    /**
     * metodo de la clase VentanaModificacion que comprueba lo que se ha escrito en el campo de texto de email
     * @return devuelve un String
     * @throws MyException 
     */
    private String email() throws MyException {
        if (atributos[3].getText().length() > 0 && atributos[3].getText().length() < 26) {
            return atributos[3].getText();
        } else {
            ventanaError("Email no válido, tiene que tener entre 1 y 25 caracteres");
            throw new MyException("Email no válido");
        }
    }
    /**
     * metodo de la clase VentanaModificacion que comprueba lo que se ha escrito en el campo de texto de telefono
     * @return devuelve un String
     * @throws MyException 
     */
    private String telefono() throws MyException {
        if (atributos[4].getText().length() > 0 && atributos[4].getText().length() < 16) {
            return atributos[4].getText();
        } else {
            ventanaError("Telefono no válido, tiene que tener entre 1 y 25 caracteres");
            throw new MyException("Telefono no válido");
        }
    }
    /**
     * metodo de la clase VentanaModificacion que comprueba lo que se ha escrito en el campo de texto de horas
     * @return devuelve un Int
     * @throws MyException 
     */
    private int horas() throws MyException {
        try {
            if (Integer.parseInt(atributos[5].getText()) > 0
                    && Integer.parseInt(atributos[5].getText()) < 100) {
                return Integer.parseInt(atributos[5].getText());
            } else {
                ventanaError("Horas dedicadas al mes no válidas (Maximo 100 horas, Minimo 1 hora");
                throw new MyException("Horas no válida");
            }
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "aceptar":
                modificar();
                break;
            default:
                this.dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
