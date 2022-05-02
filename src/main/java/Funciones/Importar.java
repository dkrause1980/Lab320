/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import com.opencsv.CSVReader;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author diego
 */
public class Importar {

    public String abrirArchivo() {

        final String LAST_USED_FOLDER = "";
        String textArchivo = "";
        Preferences prefs = Preferences.userRoot().node(getClass().getName());
        JFileChooser fc = new JFileChooser(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath()));
        fc.setPreferredSize(new Dimension(600, 400));
        Action details = fc.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.csv", "csv");
        fc.setFileFilter(filtro);
        int seleccion = fc.showOpenDialog(fc.getParent());
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fc.getSelectedFile();
            prefs.put(LAST_USED_FOLDER, fc.getSelectedFile().getParent());
            textArchivo = fichero.getAbsolutePath();

            ArrayList<String[]> empresas = Otras.leer_empresas_clientes();
            int cant_emp = empresas.size();

            String[] rsocial = new String[cant_emp];

            int i = 0;
            for (String[] e : empresas) {

                rsocial[i] = e[0];
                i++;

            }

            opcion = JOptionPane.showInputDialog(null, "Selecciona empresa origen",
                    "Elegir", JOptionPane.QUESTION_MESSAGE, null, rsocial, rsocial[0]);
            opcion_remito = JOptionPane.showInputDialog("Ingrese remito: ");

            for (String[] e : empresas) {

                if (e[0].equals(opcion)) {
                    cuit = e[1];
                }

            }

        }
        return textArchivo;
    }

    public void cargarArchivoFoss(JTextField jtext, JTable tabla) {

        try {
            String archivo = jtext.getText();
            try ( CSVReader csvReader = new CSVReader(new FileReader(archivo))) {
                List<String[]> datos = csvReader.readAll();
                modelo_viejo = (DefaultTableModel) tabla.getModel();
                DefaultTableModel modelo_nuevo = new DefaultTableModel(
                        Otras.colocarColumnas(tabla), datos.size() - 14 + modelo_viejo.getRowCount()
                );

                int pos = 0;
                if (modelo_viejo.getRowCount() != 0) {
                    for (int i = 0; i < modelo_viejo.getRowCount(); i++) {
                        for (int j = 0; j < modelo_viejo.getColumnCount(); j++) {
                            modelo_nuevo.setValueAt(modelo_viejo.getValueAt(i, j), i, j);
                        }
                    }
                    pos = modelo_viejo.getRowCount();
                }
                String[] fechaLinea = datos.get(1);
                String[] partes = fechaLinea[1].split(Pattern.quote("."));
                partes[2] = "20" + partes[2];
                String fecha = partes[0] + "/" + partes[1] + "/" + partes[2];
                String[] batchLinea = datos.get(0);
                String batch = batchLinea[1];
                //String cuit = Otras.cuitEmpresas(opcion);

                ArrayList<String> id_muestras = new ArrayList();

                for (int i = 14; i < datos.size(); i++) {
                    int aux = -1;
                    String[] dato = datos.get(i);

                    String id_muestra = dato[6];

                    if (id_muestras.contains(id_muestra)) {

                        id_muestra = id_muestra + "(" + (i - 13) + ")";

                    }
                    id_muestras.add(id_muestra);

                    for (int h = 0; h < tabla.getRowCount(); h++) {
                        if (id_muestra.equals(tabla.getValueAt(h, 6)) && batch.equals(tabla.getValueAt(h, 5))) {
                            aux = pos;
                            pos = h;
                        }
                    }

                    modelo_nuevo.setValueAt(dato[1], pos, 0);
                    modelo_nuevo.setValueAt(cuit, pos, 3);
                    modelo_nuevo.setValueAt(opcion_remito, pos, 4);
                    modelo_nuevo.setValueAt(fecha, pos, 7);
                    modelo_nuevo.setValueAt(batch, pos, 5);
                    modelo_nuevo.setValueAt(id_muestra, pos, 6);
                    modelo_nuevo.setValueAt(dato[2], pos, 16);
                    modelo_nuevo.setValueAt(dato[3], pos, 17);
                    modelo_nuevo.setValueAt("9", pos, 22);

                    if (aux != -1) {
                        pos = aux;
                    }
                    pos++;

                }

                //this.removerFilas(modelo_nuevo);
                tabla.setAutoCreateRowSorter(true);
                tabla.setModel(modelo_nuevo);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarArchivoCombi(JTextField jtext, JTable tabla) {

        try {
            String archivo = jtext.getText();
            try ( CSVReader csvReader = new CSVReader(new FileReader(archivo))) {
                List<String[]> datos = csvReader.readAll();

                for (int i = 0; i < datos.size(); i++) {
                    String[] fila = datos.get(i);
                    for (int h = 0; h < fila.length; h++) {
                        fila[h] = fila[h].replace("\"", "");
                    }
                }
                modelo_viejo = (DefaultTableModel) tabla.getModel();
                DefaultTableModel modelo_nuevo = new DefaultTableModel(
                        Otras.colocarColumnas(tabla), datos.size() - 1 + modelo_viejo.getRowCount()
                );
                int pos = 0;
                if (modelo_viejo.getRowCount() != 0) {
                    for (int i = 0; i < modelo_viejo.getRowCount(); i++) {
                        for (int j = 0; j < modelo_viejo.getColumnCount(); j++) {
                            modelo_nuevo.setValueAt(modelo_viejo.getValueAt(i, j), i, j);
                        }
                    }
                    pos = modelo_viejo.getRowCount();
                }

                //String cuit = Otras.cuitEmpresas(opcion);
                ArrayList<String> id_muestras = new ArrayList();

                for (int i = 1; i < datos.size(); i++) {
                    int aux = -1;
                    String[] dato = datos.get(i);
                    String id_muestra = dato[14];
                    if (id_muestra.equals("")) {
                        id_muestra = dato[1];
                    }

                    if (id_muestras.contains(id_muestra)) {

                        id_muestra = id_muestra + "(" + i + ")";
                    }
                    id_muestras.add(id_muestra);

                    for (int h = 0; h < tabla.getRowCount(); h++) {

                        if (id_muestra.equals(tabla.getValueAt(h, 6))
                                && dato[0].equals(tabla.getValueAt(h, 5)) //tabla.getValueAt(h, 2)==null
                                ) {
                            aux = pos;
                            pos = h;
                        }

                    }

                    modelo_nuevo.setValueAt(dato[2], pos, 2);
                    modelo_nuevo.setValueAt(cuit, pos, 3);
                    //String barcode = dato[1];
                    //if (barcode.equals("") || barcode.equalsIgnoreCase("noread")){
                    modelo_nuevo.setValueAt(id_muestra, pos, 6);
                    //}else{
                    //    modelo_nuevo.setValueAt(barcode,pos,6);
                    //}
                    modelo_nuevo.setValueAt(dato[13], pos, 15);
                    modelo_nuevo.setValueAt(opcion_remito, pos, 4);
                    String fpd = "";
                    if (dato[12].length() >= 3) {
                        fpd = dato[12].substring(0, 3);
                    }
                    modelo_nuevo.setValueAt(fpd, pos, 14);
                    modelo_nuevo.setValueAt(dato[11], pos, 13);
                    modelo_nuevo.setValueAt(dato[10], pos, 11);
                    modelo_nuevo.setValueAt(dato[9], pos, 12);
                    modelo_nuevo.setValueAt(dato[8], pos, 10);
                    modelo_nuevo.setValueAt(dato[7], pos, 9);
                    modelo_nuevo.setValueAt(dato[6], pos, 8);
                    modelo_nuevo.setValueAt(Otras.formatearFecha(dato[3]), pos, 7);
                    modelo_nuevo.setValueAt(dato[0], pos, 5);
                    modelo_nuevo.setValueAt("9", pos, 22);

                    if (aux != -1) {
                        pos = aux;
                    }
                    pos++;

                }

                //this.removerFilas(modelo_nuevo);
                tabla.setAutoCreateRowSorter(true);
                tabla.setModel(modelo_nuevo);
                //Otras.resaltar(tabla);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarArchivoCombiConCaseina(JTextField jtext, JTable tabla) {

        try {
            String archivo = jtext.getText();
            try ( CSVReader csvReader = new CSVReader(new FileReader(archivo))) {
                List<String[]> datos = csvReader.readAll();

                for (int i = 0; i < datos.size(); i++) {
                    String[] fila = datos.get(i);
                    for (int h = 0; h < fila.length; h++) {
                        fila[h] = fila[h].replace("\"", "");
                    }
                }
                modelo_viejo = (DefaultTableModel) tabla.getModel();
                DefaultTableModel modelo_nuevo = new DefaultTableModel(
                        Otras.colocarColumnas(tabla), datos.size() - 1 + modelo_viejo.getRowCount()
                );

                int pos = 0;
                if (modelo_viejo.getRowCount() != 0) {
                    for (int i = 0; i < modelo_viejo.getRowCount(); i++) {
                        for (int j = 0; j < modelo_viejo.getColumnCount(); j++) {
                            modelo_nuevo.setValueAt(modelo_viejo.getValueAt(i, j), i, j);
                        }
                    }
                    pos = modelo_viejo.getRowCount();
                }

                //String cuit = Otras.cuitEmpresas(opcion);
                ArrayList<String> id_muestras = new ArrayList();

                for (int i = 1; i < datos.size(); i++) {
                    int aux = -1;
                    String[] dato = datos.get(i);
                    String id_muestra = dato[15];
                    if (id_muestra.equals("")) {
                        id_muestra = dato[1];
                    }

                    if (id_muestras.contains(id_muestra)) {

                        id_muestra = id_muestra + "(" + i + ")";

                    }
                    id_muestras.add(id_muestra);

                    for (int h = 0; h < tabla.getRowCount(); h++) {

                        if (id_muestra.equals(tabla.getValueAt(h, 6)) && dato[0].equals(tabla.getValueAt(h, 5))) {
                            aux = pos;
                            pos = h;
                        }
                    }

                    modelo_nuevo.setValueAt(dato[2], pos, 2);
                    modelo_nuevo.setValueAt(cuit, pos, 3);
                    //String barcode = dato[1];
                    //if (barcode.equals("") || barcode.equalsIgnoreCase("noread")){
                    modelo_nuevo.setValueAt(id_muestra, pos, 6);
                    //}else{
                    //    modelo_nuevo.setValueAt(barcode,pos,6);
                    //}

                    modelo_nuevo.setValueAt(dato[13], pos, 15);
                    modelo_nuevo.setValueAt(opcion_remito, pos, 4);
                    String fpd = "";
                    if (dato[12].length() >= 3) {
                        fpd = dato[12].substring(0, 3);
                    }
                    modelo_nuevo.setValueAt(fpd, pos, 14);
                    modelo_nuevo.setValueAt(dato[11], pos, 13);
                    modelo_nuevo.setValueAt(dato[10], pos, 11);
                    modelo_nuevo.setValueAt(dato[9], pos, 12);
                    modelo_nuevo.setValueAt(dato[8], pos, 10);
                    modelo_nuevo.setValueAt(dato[7], pos, 9);
                    modelo_nuevo.setValueAt(dato[6], pos, 8);
                    modelo_nuevo.setValueAt(Otras.formatearFecha(dato[3]), pos, 7);
                    modelo_nuevo.setValueAt(dato[0], pos, 5);
                    modelo_nuevo.setValueAt("9", pos, 22);
                    modelo_nuevo.setValueAt(dato[14], pos, 23);

                    if (aux != -1) {
                        pos = aux;
                    }
                    pos++;

                }
                tabla.setAutoCreateRowSorter(true);
                //this.removerFilas(modelo_nuevo);
                tabla.setModel(modelo_nuevo);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarArchivoIbc(JTextField jtext, JTable tabla) {

        try {
            String archivo = jtext.getText();
            try ( CSVReader csvReader = new CSVReader(new FileReader(archivo))) {
                List<String[]> datos = csvReader.readAll();

                for (int i = 0; i < datos.size(); i++) {
                    String[] fila = datos.get(i);
                    for (int h = 0; h < fila.length; h++) {
                        fila[h] = fila[h].replace("\"", "");
                    }
                }
                modelo_viejo = (DefaultTableModel) tabla.getModel();
                DefaultTableModel modelo_nuevo = new DefaultTableModel(
                        Otras.colocarColumnas(tabla), datos.size() - 1 + modelo_viejo.getRowCount()
                );

                int pos = 0;
                if (modelo_viejo.getRowCount() != 0) {
                    for (int i = 0; i < modelo_viejo.getRowCount(); i++) {
                        for (int j = 0; j < modelo_viejo.getColumnCount(); j++) {
                            modelo_nuevo.setValueAt(modelo_viejo.getValueAt(i, j), i, j);
                        }
                    }
                    pos = modelo_viejo.getRowCount();
                }

                //String cuit = Otras.cuitEmpresas(opcion);
                ArrayList<String> id_muestras = new ArrayList();

                for (int i = 1; i < datos.size(); i++) {
                    int aux = -1;
                    String[] dato = datos.get(i);
                    String id_muestra = dato[8];
                    if (id_muestra.equals("")) {
                        id_muestra = dato[1];
                    }

                    if (id_muestras.contains(id_muestra)) {

                        id_muestra = id_muestra + "(" + i + ")";

                    }
                    id_muestras.add(id_muestra);

                    for (int h = 0; h < tabla.getRowCount(); h++) {
                        if (id_muestra.equals(tabla.getValueAt(h, 6)) && dato[0].equals(tabla.getValueAt(h, 5))) {
                            aux = pos;
                            pos = h;
                        }
                    }

                    modelo_nuevo.setValueAt(dato[2], pos, 1);
                    modelo_nuevo.setValueAt(cuit, pos, 3);
                    modelo_nuevo.setValueAt(opcion_remito, pos, 4);
                    modelo_nuevo.setValueAt(dato[0], pos, 5);
                    modelo_nuevo.setValueAt(Otras.formatearFecha(dato[3]), pos, 7);
                    modelo_nuevo.setValueAt(dato[6], pos, 16);
                    modelo_nuevo.setValueAt(dato[7], pos, 17);
                    String barcode = dato[1];
                    //if (barcode.equals("") || barcode.equalsIgnoreCase("noread")){
                    modelo_nuevo.setValueAt(id_muestra, pos, 6);
                    //}else{
                    //    modelo_nuevo.setValueAt(barcode,pos,6);
                    //}

                    modelo_nuevo.setValueAt("9", pos, 22);

                    if (aux != -1) {
                        pos = aux;
                    }
                    pos++;

                }
                tabla.setAutoCreateRowSorter(true);
                //this.removerFilas(modelo_nuevo);
                tabla.setModel(modelo_nuevo);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int detectarTipoCsv(String archivo) {
        CSVReader csvReader;
        int tipo = -1;
        try {
            csvReader = new CSVReader(new FileReader(archivo));
            List<String[]> datos = csvReader.readAll();
            String[] dato = datos.get(0);

            if (dato[0].equals("Batch")) {
                tipo = 0;
            } else if (dato[6].equals("IBC")) {
                tipo = 1;
            } else if (dato[14].equals("CAS")) {
                tipo = 3;
            } else if (dato[6].equals("Fat")) {
                tipo = 2;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Importar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tipo;
    }

    public void removerFilas(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        //System.out.println(modelo.getRowCount());
        if (modelo.getRowCount() != 0) {
            for (int h = 0; h < 10; h++) {
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    if (null == modelo.getValueAt(i, 6)) {
                        modelo.removeRow(i);

                    }
                }
            }

        }
        Otras.acomodarColumnas(tabla);

        tabla.setModel(modelo);

    }

    DefaultTableModel modelo_viejo;
    Object opcion, opcion_remito;
    String cuit = "";

}
