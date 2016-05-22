/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import excepciones.MyException;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import leoproyectoprogramacion.Voluntario;

/**
 *Clase VentanaListado
 * @author Leo
 * @version 22/05/2016
 */
public class VentanaListado extends JFrame {

    JPanel contenedor;
    JTable table;
    DefaultTableModel modelo;
    ArrayList<Voluntario> voluntarios;
    /**
     * Constructor de la clase VentanaListado
     * @param voluntarios El parametro voluntarios define un ArrayList de voluntarios generado con los
     *      datos del a base de datos
     */
    public VentanaListado(ArrayList voluntarios) {
        this.voluntarios = voluntarios;
        this.setTitle("Listado");
        this.setVisible(true);
        initComponents();
        this.pack();
        this.setSize(900, 300);
    }
    /**
     * metodo de la clase VentanaListado que inicializa los componentes de la ventana
     */
    public void initComponents() {
        try {
            contenedor = (JPanel) this.getContentPane();
            modelo = new DefaultTableModel();
            // se crea la Tabla con el modelo DefaultTableModel
            table = new JTable(modelo);
            //Creamos un JscrollPane y le agregamos la JTable
            JScrollPane scrollPane = new JScrollPane(table);
            //Agregamos el JScrollPane al contenedor
            contenedor.add(scrollPane, BorderLayout.CENTER);
            // insertamos los nombres de las celdas cabecera.
            modelo.addColumn("DNI");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Email");
            modelo.addColumn("Teléfono");
            modelo.addColumn("Horas_Dedicadas");
            muestraFilas();
        } catch (MyException ex) {
            ventanaError("No se ha encontrdo ningun resultado");
        }
    }
    /**
     * metodo de la clase VentanaListado que sirve para crear mensajes de error
     * @param cadena El parametro cadena define el mensaje de error que se mostrara
     */
    private void ventanaError(String cadena) {
        JOptionPane.showMessageDialog(
                this, cadena,
                "Error", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * metodo de la clase VentanaListado que sirve para añadir los datos de los voluntarios a la tabla
     * @throws MyException 
     */
    public void muestraFilas() throws MyException {
        String fila[] = new String[6];

        Iterator<Voluntario> v1 = voluntarios.iterator();
        while (v1.hasNext()) {
            Voluntario n1 = v1.next();
            fila[0] = n1.getDni();
            fila[1] = n1.getNombre();
            fila[2] = n1.getApellido();
            fila[3] = n1.getEmail();
            fila[4] = n1.getTelefono();
            fila[5] = String.valueOf(n1.getHorasDedicadas());
            modelo.addRow(fila);
        }
        if(voluntarios.size()<1){
            MyException error = new MyException("No se ha encontrdo ningun resultado");
            throw error;
        }
    }
}
