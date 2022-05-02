/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;

/**
 *
 * @author diego
 */
public class Otras {

    public static JTable nuevo(JTable tabla) {

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        //tabla.setModel(modelo);
        return tabla;

    }

    public static Class<?> getColumnClasss(int column) {

        return Boolean.class;

    }
    
    public static void buscarReemplazar(JTable tabla){
        
        JDialog buscarR = new JDialog();
        buscarR.setModal(true);
        JLabel buscar = new JLabel("Valor a buscar: ");
        JLabel reemplazar = new JLabel("Reemplazar con: ");
        JTextField valorBuscar = new JTextField();
        
        
        
        
        
    }
    
    

    public static void acomodarColumnas(JTable tabla) {

        TableColumnModel columnas = tabla.getColumnModel();
        columnas.getColumn(0).setPreferredWidth(50);
        columnas.getColumn(1).setPreferredWidth(50);
        columnas.getColumn(2).setPreferredWidth(50);
        columnas.getColumn(3).setPreferredWidth(140);
        columnas.getColumn(4).setPreferredWidth(100);
        columnas.getColumn(5).setPreferredWidth(130);
        columnas.getColumn(6).setPreferredWidth(130);
        columnas.getColumn(7).setPreferredWidth(130);
        columnas.getColumn(18).setPreferredWidth(10);
        columnas.getColumn(19).setPreferredWidth(10);
        columnas.getColumn(20).setPreferredWidth(10);
        columnas.getColumn(21).setPreferredWidth(10);
        //tabla.setDefaultRenderer(Object.class, resaltado);

    }

//    public static String cuitEmpresas(Object empresa) {
//
//        String cuit = null;
//
//        switch (empresa.toString()) {
//
//            case "ABASTECIMIENTOS LACTEOS S.A.":
//                cuit = "33711861039";
//                break;
//            case "ADECOAGRO":
//                cuit = "30716198983";
//                break;
//            case "ASOCIACION COOP. DE LA ESTACION EXPERIMENTAL REGIONAL AGROPECUARIA RAFAELA":
//                cuit = "30638072552";
//                break;
//            case "BRESCIALAT S.A.":
//                cuit = "30708213876";
//                break;
//            case "BUTTO OSCAR ALBERTO":
//                cuit = "20203750103";
//                break;
//            case "CAFFALAC S.R.L.":
//                cuit = "30708615206";
//                break;
//            case "CASSINI Y CESARATTO S.A.":
//                cuit = "30646618521";
//                break;
//            case "CORLASA":
//                cuit = "30709044946";
//                break;
//            case "COTAR":
//                cuit = "30525556405";
//                break;
//            case "DANIEL BENVENUTTI  S.R.L.":
//                cuit = "30715640232";
//                break;
//            case "DIAZLAC SRL":
//                cuit = "33711217849";
//                break;
//            case "DIEGO LEONARDO MONTARAZZINO":
//                cuit = "20347204693";
//                break;
//            case "DON FRANCISCO AGROPECUARIA S R L":
//                cuit = "30515373558";
//                break;
//            case "FABRICA DE ALIMENTOS SANTA CLARA S.A.":
//                cuit = "30710825196";
//                break;
//            case "FREDDO S.A.":
//                cuit = "30701965554";
//                break;
//            case "FUNDACION ARGENINTA":
//                cuit = "30676303657";
//                break;
//            case "GARCIA HERMANOS AGROINDUSTRIAL SRL":
//                cuit = "30566108352";
//                break;
//            case "LA ESTANCIA DE ORO":
//                cuit = "30708369876";
//                break;
//            case "LACTEAR S.A.":
//                cuit = "30708296410";
//                break;
//            case "LACTEOS 3L S.A.":
//                cuit = "30710795130";
//                break;
//            case "LACTEOS LA RAMADA S.A.":
//                cuit = "30711970777";
//                break;
//            case "LACTEOS LAS TRES S R L":
//                cuit = "30669389724";
//                break;
//            case "LACTEOS SANTA MARIA":
//                cuit = "30574118642";
//                break;
//            case "LAS TAPERITAS S.A.":
//                cuit = "30615374640";
//                break;
//            case "LEIG-LAC S.R.L.":
//                cuit = "30709232548";
//                break;
//            case "MANFREY COOP. DE TAMBEROS DE COMERC.E INDUSTRIALIZACION LTDA":
//                cuit = "30501773383";
//                break;
//            case "MILKAUT S.A.":
//                cuit = "30682032630";
//                break;
//            case "MOLFINO HNOS. S.A.":
//                cuit = "30500768262";
//                break;
//            case "MONTHELADO S.A.":
//                cuit = "30709588652";
//                break;
//            case "NESTLE ARGENTINA S A":
//                cuit = "30546764040";
//                break;
//            case "NOAL S.A.":
//                cuit = "30648060846";
//                break;
//            case "PAMPA CHEESE S.A.":
//                cuit = "30709971359";
//                break;
//            case "PUNTA DEL AGUA S.A.":
//                cuit = "30657468440";
//                break;
//            case "RAFELAB SRL":
//                cuit = "30711645973";
//                break;
//            case "RAMOLAC":
//                cuit = "30501632364";
//                break;
//            case "RICOLACT S.R.L.":
//                cuit = "30708450304";
//                break;
//            case "SAN LUCIO S.A.":
//                cuit = "30710581297";
//                break;
//            case "SANCOR COOPERATIVAS UNIDAS LIMITADA":
//                cuit = "30501677643";
//                break;
//            case "SUCESORES DE ALFREDO WILLINER S.A.":
//                cuit = "30501601213";
//                break;
//            case "TREMBLAY S.R.L.":
//                cuit = "30709279765";
//                break;
//            case "VERONICA S.A.C.I.A.F.E.I.":
//                cuit = "30500979824";
//                break;
//            case "VILA S.A.C.I.":
//                cuit = "30658544264";
//                break;
//            case "OTRA EMPRESA":
//                cuit = JOptionPane.showInputDialog("Ingresa cuit de empresa");
//                break;
//        }
//
//        return cuit;
//    }
    
    public static ArrayList<String[]> leer_lista_empresas(){
        String ruta = "empresas.csv";
        File file = new File(ruta);
        ArrayList<String[]> empresas = new ArrayList<>();
        if(file.exists()){
            Scanner scanner;
            try {
                scanner = new Scanner(file);
                while(scanner.hasNext()){
                    String linea = scanner.nextLine();
                    String cadena[] = linea.split(";");
                    empresas.add(cadena);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Otras.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        return empresas;
    }
    
    public static ArrayList<String[]> leer_empresas_clientes(){
        String ruta = "empresas_origen.csv";
        File file = new File(ruta);
        ArrayList<String[]> empresas = new ArrayList<>();
        if(file.exists()){
            Scanner scanner;
            try {
                scanner = new Scanner(file);
                while(scanner.hasNext()){
                    String linea = scanner.nextLine();
                    String cadena[] = linea.split(";");
                    empresas.add(cadena);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Otras.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        return empresas;
    }

//    public static ArrayList<String[]> empresasOpciones() {
//
//        ArrayList<String[]> datosEmp = new ArrayList();
//
//        String[] butto = {"BUTTO OSCAR", "Presidente Roca - Santa Fe", "Ruta 70 - Km 90"};
//        String[] williner = {"SUC. DE A. WILLINER S.A.", "Bella Italia - Santa Fe", "Ruta 70 - Km 74"};
//        String[] veronica1 = {"VERONICA S.A.C.I.A.F.E.I (1)", "Lehmann - Santa Fe", "San Martin 401"};
//        String[] veronica2 = {"VERONICA S.A.C.I.A.F.E.I (2)", "Suardi - Santa Fe", "Ruta 23 - Km. 9"};
//        String[] veronica3 = {"VERONICA S.A.C.I.A.F.E.I (3)", "Clason - Santa Fe", "Ruta Nac.34 Km. 67"};
//        String[] diazlac = {"DIAZ LAC S.R.L.", "Diaz - Córdoba", "Viamonte 161"};
//        String[] lalacteo = {"LA LACTEO S.A.", "Ferreira - Córdoba", "Cno. Capilla de los Remedios"};
//        String[] garcia = {"GARCIA HNOS AGROINDUSTRIAL SRL", "GOBERNADOR CRESPO - SANTA FE", "RUTA 1 KM 622"};
//        String[] milkaut1 = {"MILKAUT S.A. (1)", "San Jerónimo - Santa Fe", "Sargento Cabral 486"};
//        String[] milkaut2 = {"MILKAUT S.A. (2)", "Colonia Nueva - Santa Fe", "Ruta Provincial Nro 70"};
//        String[] milkaut3 = {"MILKAUT S.A. (3)", "Franck - Santa Fe", "Ruta 178 - Km 175"};
//        String[] lacteosvalle = {"LACTEOS VALLE DEL CARMEN", "Avellaneda - Santa Fe", "Calle 3 - Lote 9 y 10"};
//        String[] lactear = {"LACTEAR S.A.", "Morteros - Córdoba", "Italia 894"};
//        String[] tremblay = {"TREMBLAY", "PILAR - SANTA FE", "Ruta Provincial 67 F (Zona Rural)"};
//        String[] laramada = {"LACTEOS LA RAMADA S.A.", "FRANCK - SANTA FE", "RUTA PROVINCIAL NRO 6"};
//        String[] ramolac = {"RAMOLAC", "RAMONA", "ZONA RURAL S/N"};
//        String[] inti = {"INTI","Rafaela - Santa Fe","Ruta 34 km 227,6"};
//
//        datosEmp.add(butto);
//        datosEmp.add(williner);
//        datosEmp.add(veronica1);
//        datosEmp.add(veronica2);
//        datosEmp.add(veronica3);
//        datosEmp.add(diazlac);
//        datosEmp.add(lalacteo);
//        datosEmp.add(garcia);
//        datosEmp.add(milkaut1);
//        datosEmp.add(milkaut2);
//        datosEmp.add(milkaut3);
//        datosEmp.add(lacteosvalle);
//        datosEmp.add(lactear);
//        datosEmp.add(tremblay);
//        datosEmp.add(laramada);
//        datosEmp.add(ramolac);
//        datosEmp.add(inti);
//        return datosEmp;
//
//    }

    public static void eliminarFila(JTable tabla) {
        // TODO add your handling code here:
        DefaultTableModel datosTabla;
        if (tabla.getSelectedRow() > -1) {
            int opcion = JOptionPane.showConfirmDialog(null, "Desea eliminar esta fila?",
                    "Atención", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                datosTabla = (DefaultTableModel) tabla.getModel();
                datosTabla.removeRow(tabla.getSelectedRow());
                tabla.setModel(datosTabla);
            }
        }
    }

    public static void reemplazarFecha(JTable tabla) {
        String fecha;
        fecha = JOptionPane.showInputDialog(null, "Introduce nueva fecha");
        if (!(fecha.isEmpty())) {
            for (int i = 0; i < tabla.getRowCount(); i++) {
                tabla.setValueAt(fecha, i, 7);
            }
        }
    }

    public static void cargarInh(JTable tabla) {

        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.setValueAt("0", i, 22);
        }
    }

    public static void refrescar(JTable tabla) {

        DefaultTableModel modelo_viejo = (DefaultTableModel) tabla.getModel();

        int filas = modelo_viejo.getRowCount();
        int cant_columnas = modelo_viejo.getColumnCount();

        List<List<String>> vector_muestra = new ArrayList<>();
        ArrayList<Integer> repetidos = new ArrayList<>();

        for (int i = 0; i < filas; i++) {

            ArrayList<String> datos = new ArrayList<>();

            for (int k = 0; k < modelo_viejo.getColumnCount(); k++) {
                datos.add(k, (String) modelo_viejo.getValueAt(i, k));
            }

            for (int j = i + 1; j < filas; j++) {

                if (modelo_viejo.getValueAt(j, 6).equals(modelo_viejo.getValueAt(i, 6))
                        && modelo_viejo.getValueAt(j, 5).equals(modelo_viejo.getValueAt(i, 5))) {

                    repetidos.add(j);

                    for (int h = 0; h < cant_columnas; h++) {

                        if (modelo_viejo.getValueAt(j, h) == null || modelo_viejo.getValueAt(j, h).equals("")) {

                            datos.set(h, (String) modelo_viejo.getValueAt(i, h));
                            //modelo_nuevo.setValueAt(vector_muestra.get(h),i,h);
                        } else {
                            datos.set(h, (String) modelo_viejo.getValueAt(j, h));

                        }

                    }

                    //datos.remove(j);
                }
            }

            vector_muestra.add(datos);
        }

        Iterator it = repetidos.iterator();
        int p = 0;
        while (it.hasNext()) {

            int pos = (int) it.next();
            vector_muestra.remove(pos);
            p++;

        }

        DefaultTableModel modelo_nuevo = new DefaultTableModel(Otras.colocarColumnas(tabla), vector_muestra.size());
        for (int i = 0; i < vector_muestra.size(); i++) {
            for (int j = 0; j < cant_columnas; j++) {
                modelo_nuevo.setValueAt(vector_muestra.get(i).get(j), i, j);
            }
        }
        tabla.setModel(modelo_nuevo);

        Otras.acomodarColumnas(tabla);

    }

    public static String formatearFecha(String fechaCsv) {

        String[] parts = fechaCsv.split("/");
        if (parts[0].length() == 1) {
            parts[0] = "0" + parts[0];
        }
        if (parts[1].length() == 1) {
            parts[1] = "0" + parts[1];
        }
        if (parts[2].length() == 2) {
            parts[2] = "20" + parts[2];
        }
        String fechaFinal = parts[1] + "/" + parts[0] + "/" + parts[2];
        return fechaFinal;

    }

    public static void cargarInhFilasSeleccionadas(JTable tabla) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        //ArrayList <Integer> filasSeleccionadas = tabla.getSelectedRows();
        int[] rows = tabla.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            tabla.setValueAt("0", rows[i], 22);
        }
    }

    public static void cargarSigleaFilasSeleccionadas(JTable tabla) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        //ArrayList <Integer> filasSeleccionadas = tabla.getSelectedRows();
        int[] rows = tabla.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            tabla.setValueAt("s", rows[i], 24);
        }
    }

    public static Object[] colocarColumnas(JTable tabla) {

        Object[] columnas = new Object[25];
        //modelo = new DefaultTableModel();
        //Boolean column = new Boolean(false);

        columnas[0] = "F";
        columnas[1] = "I";
        columnas[2] = "C";
        columnas[3] = "CUIT";
        columnas[4] = "REMITO";
        columnas[5] = "BATCH";
        columnas[6] = "MUESTRA";
        columnas[7] = "FECHA";
        columnas[8] = "FAT";
        columnas[9] = "PROTEINAS";
        columnas[10] = "LACTOSA";
        columnas[11] = "SNF";
        columnas[12] = "TOTAL SOLIDS";
        columnas[13] = "MUN";
        columnas[14] = "FPD";
        columnas[15] = "SCC";
        columnas[16] = "BACTERIA";
        columnas[17] = "CFU";
        columnas[18] = "SIGN_MEAN";
        columnas[19] = "REMARK";
        columnas[20] = "RESULT_TYPE";
        columnas[21] = "BOTTLE_TYPE";
        columnas[22] = "INH";
        columnas[23] = "CASEINA";
        columnas[24] = "SIGLEA";

        /*for (Object col : columnas) {
            modelo.addColumn(col);
        }
        
        tabla.setModel(modelo);
        TableColumnModel colModel = tabla.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(30);
        colModel.getColumn(1).setPreferredWidth(30);
        colModel.getColumn(2).setPreferredWidth(30);*/
        return columnas;

    }

    static DefaultTableModel modelo;
    //static Resaltador resaltado;

}
