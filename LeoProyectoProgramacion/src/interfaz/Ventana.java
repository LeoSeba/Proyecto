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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import leoproyectoprogramacion.DataBase;
import leoproyectoprogramacion.DocumentoXML;
import leoproyectoprogramacion.Voluntario;

/**
 * Clase Ventana
 * @author Leo
 * @version 22/05/2016
 */
public class Ventana extends JFrame implements ActionListener, WindowListener {

    JPanel contenedor;
    JButton botones[];
    DataBase db;
    ArrayList<Voluntario> voluntarios = new ArrayList<Voluntario>();
    /**
     * Constructor de la clase Ventana
     * @param db El parametro db define la base de datos con la que vamos a trabajar
     */
    public Ventana(DataBase db) {
        this.db = db;
        this.setTitle("Ventana principal");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(300, 300);
    }
    /**
     * Metodo de la clase Ventana que inicializa los componentes de la ventana
     */
    private void initComponents() {
        String textoBotones[] = {"Alta", "Baja", "Modificacion", "Crear documento XML", "Listado1", "Listado2", "Listado3", "Fin"};
        botones = new JButton[textoBotones.length];
        //Utilizo todo el fondo del JFrame
        contenedor = (JPanel) this.getContentPane();
        //Inicializo un layout
        contenedor.setLayout(new GridLayout(textoBotones.length, 1, 5, 5));
        //Inicializo los objetos
        actualizarArray();
        for (int x = 0; x < textoBotones.length; x++) {
            botones[x] = new JButton();
            botones[x].setText(textoBotones[x]);
            botones[x].setActionCommand(Integer.toString(x));
            botones[x].addActionListener(this);
            contenedor.add(botones[x]);
        }
        //Atiendo a la ventana
        this.addWindowListener(this);
    }
    /**
     * metodo de la clase Ventana que pide el numero de horas
     * @param mensaje El parametro mensaje define el mensaje que se mostrara por pantalla
     * @return devuelve el numero de horas dedicadas de un voluntario
     */
    public int pideHoras(String mensaje) {
        int horas;
        while (true) {
            try {
                horas = compruebaNumero(mensaje);
                return horas;
            } catch (MyException e) {
                ventanaError("El numero  de horas debe estar entre 1 y 100");
                System.out.println("El numero  de horas debe estar entre 1 y 100");
            }
        }
    }
    /**
     * metodo de la clase Ventana que comprueba que el numero este entre 1 y 100
     * @param mensaje El parametro mensaje define el mensaje que se mostrara por pantalla
     * @return un numero
     * @throws MyException 
     */
    public int compruebaNumero(String mensaje) throws MyException {
        int n;

        n = Integer.parseInt(JOptionPane.showInputDialog("Introduce el numero de horas dedicadas: "));

        if (n < 1 || n > 100) {
            MyException error = new MyException("Error. el numero de horas dedicadas al mes "
                    + "debe estar entre 1 y 100");
            throw error;
        }
        return n;
    }
    /**
     * metodo de la clase Ventana que pide el nombre de una asociacion
     * @param cadena El parametro cadena define el mensaje que se mostrara por pantalla
     * @return devuelve el nombre de una asociacion
     */
    public String pideCadena(String cadena) {
        String nombre = null;
        while (true) {
            try {
                nombre = compruebaNombre(cadena);
                return nombre;
            } catch (MyException e) {
                ventanaError("Nombre no valido; Introduce el nombre de una asociacion sin espacios");
                System.out.println("ERROR. Nombre no valido; Introduce el nombre de una asociacion sin espacios");
            } catch (StringIndexOutOfBoundsException e) {
                ventanaError("Nombre no valido; Introduce el nombre de una asociacion sin espacios");
                System.out.println("ERROR. Nombre no valido; Introduce el nombre de una asociacion sin espacios");
            }
        }
    }
    /**
     * metodo de la clase Ventana que comprueba que el nombre de la asociacion sea valido
     * @param cadena El parametro cadena define el mensaje que se mostrara por pantalla
     * @return devuelve el nombre de una asociacion
     * @throws MyException 
     */
    static String compruebaNombre(String cadena) throws MyException {
        String[] asociaciones = {"cruzroja", "greenpeace", "medicossinfronteras", "brac", "unicef"};
        String nombre = null;
        nombre = JOptionPane.showInputDialog(cadena);
        boolean compruebaNombre = false;
        for (int x = 0; x < asociaciones.length; x++) {
            if (asociaciones[x].equals(nombre.toLowerCase())) {
                compruebaNombre = true;
            }
        }
        if (!compruebaNombre) {
            MyException error = new MyException("Nombre de asociacion no valido");
            throw error;
        }
        return nombre;
    }
     /**
     * metodo de la clase Ventana que sirve para crear mensajes de error
     * @param cadena El parametro cadena define el mensaje de error que se mostrara
     */
    private void ventanaError(String cadena) {
        JOptionPane.showMessageDialog(
                this, cadena,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * metodo de la clase Ventana que crea un Array de String con los DNI de los voluntarios
     * @return devuelve un Array de string
     */
    private String[] crearArrayDNI() {
        String[] dni = new String[voluntarios.size()];
        int num = 0;
        Iterator<Voluntario> v1 = voluntarios.iterator();
        while (v1.hasNext()) {
            Voluntario n1 = v1.next();
            dni[num] = n1.getDni();
            num++;
        }
        return dni;
    }
    /**
     * metodo de la clase Ventana que actualiza los datos del ArrayList por si se han dado de alta, de baja 
     * o modificado a algun voluntario antes
     * @return devuelve un ArrayList de voluntario 
     */
    private ArrayList actualizarArray(){
        ArrayList<Voluntario> voluntariosNuevo = new ArrayList();
        db.pasarDatos(db.ejecutaConsulta("Select * from voluntario order by ID_VOLUNTARIO"), voluntariosNuevo);
        return voluntariosNuevo;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "0"://alta
                voluntarios=actualizarArray();
                VentanaAlta vA = new VentanaAlta(db, voluntarios);
                break;
            case "1"://baja
                voluntarios=actualizarArray();
                String[] dniBaja = crearArrayDNI();
                String opcionBaja = (String) JOptionPane.showInputDialog(null,
                        "Selecciona un dni", "Elegir", JOptionPane.QUESTION_MESSAGE,
                        null, dniBaja, dniBaja[0]);
                if (opcionBaja != null) {
                    db.ejecutaUpdate("delete from contiene\n"
                            + "where VOLUNTARIO_ID_VOLUNTARIO =(select ID_VOLUNTARIO "
                            + "from voluntario "
                            + "where dni = '" + opcionBaja + "')");
                    db.ejecutaUpdate("delete from voluntario "
                            + " where ID_VOLUNTARIO = (select ID_VOLUNTARIO "
                            + "from voluntario where dni = '" + opcionBaja + "')");
                    JOptionPane.showMessageDialog(null, "Baja realizada correctamente");
                }
                break;
            case "2"://modificaciones
                voluntarios=actualizarArray();
                String[] dni = crearArrayDNI();
                try{
                String opcion = (String) JOptionPane.showInputDialog(null, "Selecciona un dni", "Elegir",
                        JOptionPane.QUESTION_MESSAGE, null, dni, dni[0]);
                
                if (opcion != null) {
                    VentanaModificacion m1 = new VentanaModificacion(db,voluntarios,opcion);
                }
                System.out.println(opcion);
                }catch (NullPointerException ex) {

                }
                break;
            case "3"://crear documento xml
                voluntarios=actualizarArray();
                DocumentoXML doc = new DocumentoXML();
                doc.escribo("Voluntarios", voluntarios);
                

                break;
            case "4"://listado1 Todos los voluntarios ordenados alfabéticamente
                ArrayList<Voluntario> voluntarios1 = new ArrayList();
                db.pasarDatos(db.ejecutaConsulta("Select * from voluntario order by NOMBRE"), voluntarios1);
                VentanaListado v1 = new VentanaListado(voluntarios1);
                break;
            case "5"://listado2 Pedir por teclado el nº de horas y mostrar todos los voluntarios que tienen
                //una dedicación de más de esas horas.
                ArrayList<Voluntario> voluntarios2 = new ArrayList();
                try {
                    db.pasarDatos(db.ejecutaConsulta("Select * from voluntario where HORAS_DEDICADAS > "
                            + pideHoras("Introduce el numero de horas dedicadas: ")), voluntarios2);
                    VentanaListado v2 = new VentanaListado(voluntarios2);
                } catch (NumberFormatException ex) {
//                ventanaError("Has Insertado un cararter no valido");
//                System.out.println("Has Insertado un cararter no valido");
                }

//                VentanaListado vL = new VentanaListado(db.ejecutaConsulta("SELECT * from voluntario"));
                break;
            case "6"://listado3 Pedir el nombre de una asociación y que muestre todos los voluntarios
                //de dicha asociación.
                String[] asociaciones = {"cruzroja", "greenpeace", "medicossinfronteras", "brac", "unicef"};
                ArrayList<Voluntario> voluntarios3 = new ArrayList();

                try {
                    String opcion = (String) JOptionPane.showInputDialog(null, "Selecciona una asociacion", "Elegir",
                        JOptionPane.QUESTION_MESSAGE, null, asociaciones, asociaciones[0]);
                    // le paso el nombre de una de las vistas que he creado en la base de datos
                    if(opcion!=null){
                    db.pasarDatos(db.ejecutaConsulta("select * from  " + opcion), voluntarios3);
                    VentanaListado v3 = new VentanaListado(voluntarios3);
                    }
                } catch (NullPointerException ex) {

                }
                break;
            default:
                fin();
        }
    }
    /**
     * metodo de la clase Ventana que se desconecta de la base de datos y cierra la centana
     */
    private void fin() {
        db.cerrarConexion();
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("cerrando");
        fin();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("Cerrado");
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
