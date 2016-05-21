/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leoproyectoprogramacion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Clase DocumentoXML para crear documentos xml
 *
 * @author Leo
 * @version 21/05/2016
 */
public class DocumentoXML {

    /**
     * Metodo de la clase DocumentoXML
     *
     * @param nombreDocumento El parametro nombreDocumento define el nombre que
     * tendra el documento xml
     * @param voluntarios El parametro voluntarios define un ArrayList de
     * voluntario generado con los datos de la base de datos
     */
    public void escribo(String nombreDocumento, ArrayList voluntarios) {
        if (voluntarios.isEmpty()) {
            System.out.println("ERROR datos de entrada no validos");
        } else {
            //sin datos, sólo el elemento raiz
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;

            try {
                db = dbf.newDocumentBuilder();
                //Creamos el documento XML y le pasamos la etiqueta raiz
                DOMImplementation implementation = db.getDOMImplementation();
                Document document = implementation.createDocument(null, nombreDocumento, null);
                document.setXmlVersion("1.0");
                //Main Node: Primer ejemplos, sólo con el elemento raíz
                Element raiz = document.getDocumentElement();
                System.out.println("Raiz: " + raiz.getNodeName());
                //Ahora creamos un elemento con los datos del array
                Iterator<Voluntario> v1 = voluntarios.iterator();
                while (v1.hasNext()) {
                    Voluntario n1 = v1.next();
                    //Creamos la Etiqueta voluntario
                    Element etiquetaVoluntario = document.createElement("Voluntario");
                    //Creamos la Etiqueta dni
                    Element etiquetaDNI = document.createElement("DNI");
                    Text valordni = document.createTextNode(n1.getDni());
                    etiquetaDNI.appendChild(valordni);
                    //Creamos la Etiqueta nombre
                    Element etiquetaNombre = document.createElement("Nombre");
                    Text valorNombre = document.createTextNode(n1.getNombre());
                    etiquetaNombre.appendChild(valorNombre);
                    //Creamos la Etiqueta Apellido
                    Element etiquetaApellido = document.createElement("Apellido");
                    Text valorApellido = document.createTextNode(n1.getApellido());
                    etiquetaApellido.appendChild(valorApellido);
                    //Creamos la Etiqueta Email
                    Element etiquetaEmail = document.createElement("Email");
                    Text valorEmail = document.createTextNode(n1.getEmail());
                    etiquetaEmail.appendChild(valorEmail);
                    //Creamos la Etiqueta Telefono
                    Element etiquetaTelefono = document.createElement("Telefono");
                    Text valorTelefono = document.createTextNode(n1.getTelefono());
                    etiquetaTelefono.appendChild(valorTelefono);
                    //Creamos la Etiqueta Horas
                    Element etiquetaHoras_Dedicadas = document.createElement("Horas_Dedicadas");
                    Text valorHoras_Dedicadas = document.createTextNode(String.valueOf(n1.getHorasDedicadas()));
                    etiquetaNombre.appendChild(valorHoras_Dedicadas);

                    //Añadimos la etiqueta dni, nombre, apellidos, email, telefono, horas_dedicadas
                    //a la etiqueta voluntarios
                    etiquetaVoluntario.appendChild(etiquetaDNI);
                    etiquetaVoluntario.appendChild(etiquetaNombre);
                    etiquetaVoluntario.appendChild(etiquetaApellido);
                    etiquetaVoluntario.appendChild(etiquetaEmail);
                    etiquetaVoluntario.appendChild(etiquetaTelefono);
                    etiquetaVoluntario.appendChild(etiquetaHoras_Dedicadas);
                    //Añadimos la etiqueta persona a la raiz 
                    raiz.appendChild(etiquetaVoluntario);

                }

                //Generate XML
                Source source = new DOMSource(document);
                //Indicamos donde lo queremos almacenar
                //No tiene porque coincidir el Nombre de la etiqueta Raiz con la Etiqueta Raiz
                Result result = new StreamResult(new java.io.File("fichero/" + nombreDocumento + ".xml"));
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.transform(source, result);
                JOptionPane.showMessageDialog(null, "Documento creado correctamente");
            } catch (ParserConfigurationException ex) {
                System.out.println("Error escribiendo Fichero");
            } catch (TransformerConfigurationException ex) {
                System.out.println("Error escribiendo Fichero");
            } catch (TransformerException ex) {
                System.out.println("Error escribiendo Fichero");
            }

        }

    }
}
