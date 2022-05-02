/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFCell;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 *
 * @author diego
 */
public class Exportar {

    public ArrayList obtenerModelo(JTable tabla) {

        modelo = (DefaultTableModel) tabla.getModel();
        //System.out.print(modelo.getRowCount() + "->" + modelo.getColumnCount());

        muestras = new ArrayList<>();
        //Muestra muestra = new Muestra();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            Muestra muestra = new Muestra();
            //System.out.println();

            for (int j = 0; j < modelo.getColumnCount(); j++) {

                String dato = (String) modelo.getValueAt(i, j);
                //System.out.print(dato);
                if (null == dato || dato.isEmpty()) {
                    continue;
                }

                switch (j) {

                    case 3:
                        muestra.setCuit(dato);
                        break;
                    case 4:
                        muestra.setRemito(dato);
                        break;
                    case 5:
                        muestra.setBatch(dato);
                        break;
                    case 6:
                        muestra.setMuestra(dato);
                        break;
                    case 7:
                        muestra.setFecha(dato);
                        break;
                    case 8:
                        muestra.setFat((double) Double.parseDouble(dato));
                        break;
                    case 9:
                        muestra.setProteinas((double) Double.parseDouble(dato));
                        break;
                    case 10:
                        muestra.setLactosa((double) Double.parseDouble(dato));
                        break;
                    case 11:
                        muestra.setSnf((double) Double.parseDouble(dato));
                        break;
                    case 12:
                        muestra.setTotal_solids((double) Double.parseDouble(dato));
                        break;
                    case 13:
                        muestra.setMun((double) Double.parseDouble(dato));
                        break;
                    case 14:
                        muestra.setFpd((double) Double.parseDouble(dato));
                        break;
                    case 15:
                        muestra.setScc((double) Double.parseDouble(dato));
                        break;
                    case 16:
                        muestra.setBacteria((int) Integer.parseInt(dato));
                        break;
                    case 17:
                        muestra.setCfu((int) Integer.parseInt(dato));
                        break;
                    case 22:
                        muestra.setInh((int) Integer.parseInt(dato));
                        break;
                    case 23:
                        muestra.setCaseina((double) Double.parseDouble(dato));
                        break;
                }
            }
            muestras.add(muestra);
        }
        return muestras;
    }

    public int getMuestrasBatch(JTable tabla) {
        int cant_batch = 1;
        cambio_batch = new ArrayList<>();
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String m = (String) tabla.getValueAt(i, 5);
            if (i > 0) {
                if (!m.equals(tabla.getValueAt(i - 1, 5))) {

                    cambio_batch.add(i);
                    cant_batch++;

                }

            }
        }
        cambio_batch.add(tabla.getRowCount());

        return cant_batch;
    }

    public void encriptarExcel() {
        try (POIFSFileSystem fs = new POIFSFileSystem()) {
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            // EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile, CipherAlgorithm.aes192, HashAlgorithm.sha384, -1, -1, null);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword("foobaa");
            // Read in an existing OOXML file and write to encrypted output stream
            // don't forget to close the output stream otherwise the padding bytes aren't added
            try (OPCPackage opc = OPCPackage.open(new File("..."), PackageAccess.READ_WRITE);
                OutputStream os = enc.getDataStream(fs)) {
                opc.save(os);
            } catch (InvalidFormatException ex) {
                Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Write out the encrypted version
            try (FileOutputStream fos = new FileOutputStream("...")) {
                fs.writeFilesystem(fos);
            }
        } catch (IOException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FileOutputStream exportarTablaExcel() {

        javax.swing.filechooser.FileNameExtensionFilter filtroxls
                = new javax.swing.filechooser.FileNameExtensionFilter("Documentos excel", "xls");

        final String LAST_USED_FOLDER = "";
        Preferences prefs = Preferences.userRoot().node(getClass().getName());

        final JFileChooser fc = new JFileChooser(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath()));
        //String userprofile = System.getenv("USERPROFILE");
        //fc.setCurrentDirectory(new File(userprofile + "\\Desktop"));
        Action details = fc.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        fc.setFileFilter(filtroxls);
        int returnVal = fc.showSaveDialog(null);

        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File fileXLS = null;
            prefs.put(LAST_USED_FOLDER, fc.getSelectedFile().getParent());
            try {
                //Creamos un objeto archivo con la ruta seleccionada
                fileXLS = fc.getSelectedFile();

                //Validamos si en la ruta el archivo se ha especificado la extensión
                String name = fileXLS.getName();
                if (name.indexOf('.') == -1) {

                    //De no ser asi le agregamos
                    name += ".xls";
                    fileXLS = new File(fileXLS.getParentFile(), name);
                }
                fo = new FileOutputStream(fileXLS);
                // sacamos los datos de la tabla

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fo;
    }

    public void formatoSiglea(FileOutputStream fo, JTable tabla) {
        final String headers[] = {"fechaAnalisis", "CuitIndustria", "nroMuestra", "gr %",
            "pr %", "ufc/ml", "crio", "rcs/ml", "inh", "grIrrealizable", "grIdMotivo",
            "grOtroMotivo", "prIrrealizable", "prIdMotivo", "prOtroMotivo", "ufcIrrealizable",
            "ufcIdMotivo", "ufcOtroMotivo", "crioIrrealizable", "crioIdMotivo", "crioOtroMotivo",
            "rcsIrrealizable", "rcsIdMotivo", "rcsotromotivo", "inhIrrealizable", "inhIdMotivo",
            "inhOtroMotivo", "TodoIrrealizable", "TodoidMotivo", "TodoOtroMotivo"};

        //Crear libro excel
        Workbook wb = new HSSFWorkbook();
        //crear hoja
        Sheet sheet = (Sheet) wb.createSheet("SIGLEA");

        //FILA HEADERS
        Row row = sheet.createRow((short) 0);
        row.setHeightInPoints(15);

        //fuentes
        HSSFFont fuente = (HSSFFont) wb.createFont();
        fuente.setFontHeightInPoints((short) 11);
        fuente.setFontName("Calibri");

        CellStyle estilo3 = wb.createCellStyle();
        estilo3.setFont(fuente);
        estilo3.setAlignment(HorizontalAlignment.CENTER);

        //estilo celdas 2 decimales
        CellStyle estilo = wb.createCellStyle();
        estilo.setFont(fuente);
        estilo.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        estilo.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estilo2 = wb.createCellStyle();
        estilo2.setFont(fuente);
        estilo2.setDataFormat(wb.createDataFormat().getFormat("0.000"));
        estilo2.setAlignment(HorizontalAlignment.CENTER);

        int cantFila = tabla.getRowCount();
        int cantColumna = tabla.getColumnCount();

        for (int i = 0; i < headers.length; i++) {

            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(estilo);

        }
        try {
            for (int i = 0; i < cantFila; i++) {

                Row fila = sheet.createRow(i + 1);
                fila.setHeightInPoints(15);

                for (int j = 0; j < cantColumna; j++) {

                    Cell celda = fila.createCell(j);
                    celda.setCellStyle(estilo3);

                    if (i == -1) {

                        celda.setCellValue(String.valueOf(tabla.getColumnName(j)));

                    } else {
                        switch (j) {
                            case 0:
                                celda.setCellValue(muestras.get(i).getFecha());

                                break;
                            case 1:
                                celda.setCellValue(muestras.get(i).getCuit());
                                break;
                            case 2:
                                celda.setCellValue(muestras.get(i).getMuestra());
                                break;
                            case 3:
                                if (muestras.get(i).getFat() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getFat());
                                }
                                celda.setCellStyle(estilo);
                                break;
                            case 4:
                                if (muestras.get(i).getProteinas() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getProteinas());
                                    celda.setCellStyle(estilo);
                                }

                                break;
                            case 5:
                                if (muestras.get(i).getCfu() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getCfu());

                                }
                                break;
                            case 6:
                                if (muestras.get(i).getFpd() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue((double) Math.round(muestras.get(i).getFpd()) / -1000d);
                                    celda.setCellStyle(estilo2);
                                }
                                break;
                            case 7:
                                if (muestras.get(i).getScc() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getScc());

                                }

                                break;

                            case 8:

                                switch (muestras.get(i).getInh()) {
                                    case 0:
                                        celda.setCellValue("No");
                                        break;
                                    case 1:
                                        celda.setCellValue("Si");
                                        break;
                                    default:
                                        celda.setCellValue("");
                                        break;
                                }

                                break;

                        }

                    }

                }

            }
            for (int i = 0; i < cantColumna; i++) {
                sheet.autoSizeColumn(i);

            }

            wb.write(fo);
            wb.close();
            fo.close();

            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }
    }

    public void formatoSigleaTildados(FileOutputStream fo, JTable tabla) {
        final String headers[] = {"fechaAnalisis", "CuitIndustria", "nroMuestra", "gr %",
            "pr %", "ufc/ml", "crio", "rcs/ml", "inh", "grIrrealizable", "grIdMotivo",
            "grOtroMotivo", "prIrrealizable", "prIdMotivo", "prOtroMotivo", "ufcIrrealizable",
            "ufcIdMotivo", "ufcOtroMotivo", "crioIrrealizable", "crioIdMotivo", "crioOtroMotivo",
            "rcsIrrealizable", "rcsIdMotivo", "rcsotromotivo", "inhIrrealizable", "inhIdMotivo",
            "inhOtroMotivo", "TodoIrrealizable", "TodoidMotivo", "TodoOtroMotivo"};

        //Crear libro excel
        Workbook wb = new HSSFWorkbook();
        //crear hoja
        Sheet sheet = (Sheet) wb.createSheet("SIGLEA");

        //FILA HEADERS
        Row row = sheet.createRow((short) 0);
        row.setHeightInPoints(15);

        //fuentes
        HSSFFont fuente = (HSSFFont) wb.createFont();
        fuente.setFontHeightInPoints((short) 11);
        fuente.setFontName("Calibri");

        CellStyle estilo3 = wb.createCellStyle();
        estilo3.setFont(fuente);
        estilo3.setAlignment(HorizontalAlignment.CENTER);

        //estilo celdas 2 decimales
        CellStyle estilo = wb.createCellStyle();
        estilo.setFont(fuente);
        estilo.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        estilo.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estilo2 = wb.createCellStyle();
        estilo2.setFont(fuente);
        estilo2.setDataFormat(wb.createDataFormat().getFormat("0.000"));
        estilo2.setAlignment(HorizontalAlignment.CENTER);

        int cantFila = tabla.getRowCount();
        int cantColumna = tabla.getColumnCount();

        for (int i = 0; i < headers.length; i++) {

            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(estilo);

        }
        try {
            int k = 0;
            for (int i = 0; i < cantFila; i++) {

                if (tabla.getValueAt(i, 24) != null && tabla.getValueAt(i, 24).toString().equalsIgnoreCase("s")) {
                    //System.out.println(tabla.getValueAt(i, 6));
                    Row fila = sheet.createRow(k + 1);
                    fila.setHeightInPoints(15);

                    for (int j = 0; j < cantColumna; j++) {

                        Cell celda = fila.createCell(j);
                        celda.setCellStyle(estilo3);

                        if (i == -1) {

                            celda.setCellValue(String.valueOf(tabla.getColumnName(j)));

                        } else {
                            switch (j) {
                                case 0:
                                    celda.setCellValue(muestras.get(i).getFecha());

                                    break;
                                case 1:
                                    celda.setCellValue(muestras.get(i).getCuit());
                                    break;
                                case 2:
                                    celda.setCellValue(muestras.get(i).getMuestra());
                                    break;
                                case 3:
                                    if (muestras.get(i).getFat() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getFat());
                                    }
                                    celda.setCellStyle(estilo);
                                    break;
                                case 4:
                                    if (muestras.get(i).getProteinas() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getProteinas());
                                        celda.setCellStyle(estilo);
                                    }

                                    break;
                                case 5:
                                    if (muestras.get(i).getCfu() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getCfu());

                                    }
                                    break;
                                case 6:
                                    if (muestras.get(i).getFpd() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue((double) Math.round(muestras.get(i).getFpd()) / -1000d);
                                        celda.setCellStyle(estilo2);
                                    }
                                    break;
                                case 7:
                                    if (muestras.get(i).getScc() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getScc());

                                    }

                                    break;

                                case 8:

                                    switch (muestras.get(i).getInh()) {
                                        case 0:
                                            celda.setCellValue("No");
                                            break;
                                        case 1:
                                            celda.setCellValue("Si");
                                            break;
                                        default:
                                            celda.setCellValue("");
                                            break;
                                    }

                                    break;

                            }

                        }

                    }
                    k++;

                }

            }
            for (int i = 0; i < cantColumna; i++) {
                sheet.autoSizeColumn(i);

            }

            wb.write(fo);
            wb.close();
            fo.close();

            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }
    }

    public void formatoMilkautCisternas(FileOutputStream fo, JTable tabla) {

        final String headers[] = {"MUESTRA", "BARRA", "GRASA", "PROTEINA", "LACTOSA",
            "ST", "CELULAS", "RBT", "PAL", "INHIBI", "CRIOSCOP", "IA", "SOLNOGRA", "UREA TOT",
            "RANGO"};

        //Crear libro excel
        Workbook wb = new HSSFWorkbook();
        //crear hoja
        Sheet sheet = wb.createSheet("Milkaut");
        sheet.setDefaultColumnWidth(10);
        //FILA HEADERS
        Row row = sheet.createRow((short) 0);
        row.setHeightInPoints(15);
        //fuentes
        HSSFFont fuente = (HSSFFont) wb.createFont();
        fuente.setFontHeightInPoints((short) 11);
        fuente.setFontName("Calibri");
        //estilo headers
        CellStyle estilo = wb.createCellStyle();

        estilo.setFont(fuente);

        estilo.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estilo2 = wb.createCellStyle();

        estilo2.setFont(fuente);
        estilo2.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        estilo2.setAlignment(HorizontalAlignment.CENTER);

        int cantFila = tabla.getRowCount();
        int cantColumna = tabla.getColumnCount();

        for (int i = 0; i < headers.length; i++) {

            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(estilo);

        }
        try {
            for (int i = 0; i < cantFila; i++) {

                Row fila = sheet.createRow(i + 1);
                fila.setHeightInPoints(15);

                for (int j = 0; j < cantColumna; j++) {

                    Cell celda = fila.createCell(j);
                    celda.setCellStyle(estilo);

                    if (i == -1) {

                        celda.setCellValue(String.valueOf(tabla.getColumnName(j)));

                    } else {
                        switch (j) {
                            case 0:

                                celda.setCellValue(i + 1);
                                break;
                            case 1:
                                celda.setCellValue(muestras.get(i).getMuestra());
                                break;
                            case 2:
                                celda.setCellStyle(estilo2);
                                celda.setCellValue("-");
                                break;
                            case 3:
                                celda.setCellStyle(estilo2);
                                celda.setCellValue("-");

                                break;
                            case 4:
                                celda.setCellStyle(estilo2);
                                celda.setCellValue("-");

                                break;
                            case 5:
                                celda.setCellStyle(estilo2);
                                celda.setCellValue("");

                                break;
                            case 6:

                                if (muestras.get(i).getScc() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getScc());
                                }
                                break;
                            case 7:

                                if (muestras.get(i).getCfu() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getCfu());
                                }
                                break;

                            case 8:
                                celda.setCellValue("");
                                break;
                            case 9:

                                switch (muestras.get(i).getInh()) {
                                    case 0:
                                        celda.setCellValue("Negativo");
                                        break;
                                    case 1:
                                        celda.setCellValue("Positivo");
                                        break;
                                    default:
                                        celda.setCellValue("");
                                        break;
                                }

                                break;
                            case 10:

                                if (muestras.get(i).getFpd() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getFpd());
                                }
                                break;
                            case 11:
                                celda.setCellValue("");
                                break;
                            case 12:
                                celda.setCellStyle(estilo2);
                                celda.setCellValue("-");
                                break;
                            case 13:
                                celda.setCellStyle(estilo2);
                                celda.setCellValue("-");
                                break;
                            case 14:
                                celda.setCellValue("");
                                break;

                        }
                    }
                }
            }

            wb.write(fo);
            wb.close();

            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
            JOptionPane.showMessageDialog(null, "Recuerde exportar a SIGLEA",
                    "Recordatorio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }

    }

    public void formatoMilkautTambos(FileOutputStream fo, JTable tabla) {

        final String headers[] = {"MUESTRA", "BARRA", "GRASA", "PROTEINA", "LACTOSA",
            "ST", "CELULAS", "RBT", "PAL", "INHIBI", "CRIOSCOP", "IA", "SOLNOGRA", "UREA TOT",
            "RANGO"};

        //Crear libro excel
        Workbook wb = new HSSFWorkbook();
        //crear hoja
        Sheet sheet = wb.createSheet("Milkaut");
        sheet.setDefaultColumnWidth(10);
        //FILA HEADERS
        Row row = sheet.createRow((short) 0);
        row.setHeightInPoints(15);
        //fuentes
        HSSFFont fuente = (HSSFFont) wb.createFont();
        fuente.setFontHeightInPoints((short) 11);
        fuente.setFontName("Calibri");
        //estilo headers
        CellStyle estilo = wb.createCellStyle();
        estilo.setFont(fuente);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        CellStyle estilo2 = wb.createCellStyle();
        estilo2.setFont(fuente);
        estilo2.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        estilo2.setAlignment(HorizontalAlignment.CENTER);

        int cantFila = tabla.getRowCount();
        int cantColumna = tabla.getColumnCount();

        for (int i = 0; i < headers.length; i++) {

            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(estilo);

        }
        try {
            for (int i = 0; i < cantFila; i++) {

                Row fila = sheet.createRow(i + 1);
                fila.setHeightInPoints(15);

                for (int j = 0; j < cantColumna; j++) {

                    Cell celda = fila.createCell(j);
                    celda.setCellStyle(estilo);

                    if (i == -1) {

                        celda.setCellValue(String.valueOf(tabla.getColumnName(j)));

                    } else {
                        switch (j) {
                            case 0:

                                celda.setCellValue(i + 1);
                                break;
                            case 1:
                                celda.setCellValue(muestras.get(i).getMuestra());
                                break;
                            case 2:
                                celda.setCellStyle(estilo2);
                                if (muestras.get(i).getFat() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getFat());
                                }

                                break;
                            case 3:
                                celda.setCellStyle(estilo2);
                                if (muestras.get(i).getProteinas() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getProteinas());
                                }

                                break;
                            case 4:
                                celda.setCellStyle(estilo2);
                                if (muestras.get(i).getLactosa() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getLactosa());
                                }

                                break;
                            case 5:
                                celda.setCellStyle(estilo2);
                                if (muestras.get(i).getTotal_solids() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getTotal_solids());
                                }

                                break;
                            case 6:

                                if (muestras.get(i).getScc() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getScc());
                                }
                                break;
                            case 7:

                                if (muestras.get(i).getCfu() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getCfu());
                                }
                                break;

                            case 8:
                                celda.setCellValue("");
                                break;
                            case 9:

                                switch (muestras.get(i).getInh()) {
                                    case 0:
                                        celda.setCellValue("-");
                                        break;
                                    case 1:
                                        celda.setCellValue("+");
                                        break;
                                    default:
                                        celda.setCellValue("");
                                        break;
                                }

                                break;
                            case 10:

                                if (muestras.get(i).getFpd() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getFpd());
                                }
                                break;
                            case 11:
                                celda.setCellValue("");
                                break;
                            case 12:
                                celda.setCellStyle(estilo2);
                                if (muestras.get(i).getSnf() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getSnf());
                                }
                                break;
                            case 13:
                                celda.setCellStyle(estilo2);
                                if (muestras.get(i).getUreaTotal() == 0) {
                                    celda.setCellValue("");
                                } else {
                                    celda.setCellValue(muestras.get(i).getUreaTotal());
                                }
                                break;
                            case 14:
                                celda.setCellValue("");
                                break;

                        }
                    }
                }
            }

            wb.write(fo);
            wb.close();

            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
            JOptionPane.showMessageDialog(null, "Recuerde exportar a SIGLEA",
                    "Recordatorio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }

    }

    public void formatoVeronica(FileOutputStream fo, JTable tabla) {
        // TODO add your handling code here:

        final String headers[] = {"CUIT", "REMITO", "BATCH", "MUESTRA", "FECHA",
            "FAT", "PROTEINAS", "LACTOSA", "SNF", "TOTAL SOLIDS", "MUN", "FPD",
            "SCC", "BACTERIA", "CFU", "SIGN MEAN", "REMARK", "RESULT TYPE",
            "BOTLE TYPE", "INHIBIDORES"};

        //Crear libro excel
        Workbook wb = new HSSFWorkbook();
        //crear hoja
        Sheet sheet = wb.createSheet("Sheet1");
        //FILA HEADERS
        //Row rows[] = null;
        CellStyle estiloTit = wb.createCellStyle();
        CellStyle estiloHead = wb.createCellStyle();
        CellStyle estilo = wb.createCellStyle();
        estilo.setAlignment(HorizontalAlignment.CENTER);
        CellStyle estilo2 = wb.createCellStyle();
        estilo.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        estilo.setAlignment(HorizontalAlignment.CENTER);
        CellStyle estilo3 = wb.createCellStyle();
        estilo3.setAlignment(HorizontalAlignment.CENTER);

        //fuentes
        HSSFFont fuente = (HSSFFont) wb.createFont();
        fuente.setFontHeightInPoints((short) 15);
        fuente.setBold(true);
        estiloTit.setFont(fuente);

        HSSFFont fuente2 = (HSSFFont) wb.createFont();
        fuente2.setBold(true);
        estiloHead.setFont(fuente2);
        estiloHead.setAlignment(HorizontalAlignment.CENTER);

        //fecha en la cabecera
        String fechaInicio = tabla.getValueAt(0, 7).toString();
        String[] partes = fechaInicio.split("/");
        int dia = Integer.parseInt(partes[0]);
        String fecha = String.valueOf(dia + 1);
        if (fecha.length() == 1) {
            fecha = "0" + fecha;
        }
        String fechaFin = fecha + "/" + partes[1] + "/" + partes[2];

        for (int i = 0; i <= 9; i++) {
            Row rows = sheet.createRow((short) i);
            //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            switch (i) {
                case 0:
                    Cell celdaTitulo = rows.createCell(0);
                    celdaTitulo.setCellValue("Lectura de equipos agrupados");
                    celdaTitulo.setCellStyle(estiloTit);
                    rows.setHeightInPoints(20);
                    break;
                case 4:
                    Cell inicioFecha = rows.createCell(0);
                    inicioFecha.setCellValue("FECHA DESDE");
                    inicioFecha.setCellStyle(estiloHead);
                    Cell inicioFecha2 = rows.createCell(1);
                    inicioFecha2.setCellValue(fechaInicio);
                    //System.out.println(fechaText);
                    break;
                case 5:
                    Cell finalFecha = rows.createCell(0);
                    finalFecha.setCellValue("FECHA HASTA");
                    finalFecha.setCellStyle(estiloHead);
                    Cell finFecha2 = rows.createCell(1);
                    finFecha2.setCellValue(fechaFin);
                    //System.out.println(fechaText2);
                    break;
            }
        }

        Row row = sheet.createRow(10);
        int cantFila = tabla.getRowCount();
        int cantColumna = tabla.getColumnCount();

        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(estiloHead);
        }
        for (int i = 0; i < cantFila; i++) {

            Row fila = sheet.createRow(i + 11);

            for (int j = 0; j < cantColumna; j++) {

                Cell celda = fila.createCell(j);
                celda.setCellStyle(estilo3);

                if (i == -1) {

                    celda.setCellValue(String.valueOf(tabla.getColumnName(j)));

                } else {
                    switch (j) {
                        case 0:
                            celda.setCellValue(muestras.get(i).getCuit());
                            break;
                        case 1:
                            celda.setCellValue(muestras.get(i).getRemito());
                            break;
                        case 2:
                            celda.setCellValue(muestras.get(i).getBatch());
                            break;
                        case 3:
                            celda.setCellValue(muestras.get(i).getMuestra());
                            break;
                        case 4:
                            celda.setCellValue(muestras.get(i).getFecha());
                            break;
                        case 5:
                            celda.setCellStyle(estilo);
                            celda.setCellValue(muestras.get(i).getFat());
                            break;
                        case 6:
                            celda.setCellStyle(estilo);
                            celda.setCellValue(muestras.get(i).getProteinas());
                            break;
                        case 7:
                            celda.setCellStyle(estilo);
                            celda.setCellValue(muestras.get(i).getLactosa());
                            break;
                        case 8:
                            celda.setCellStyle(estilo);
                            celda.setCellValue(muestras.get(i).getSnf());
                            break;
                        case 9:
                            celda.setCellStyle(estilo);
                            celda.setCellValue(muestras.get(i).getTotal_solids());
                            break;
                        case 10:
                            celda.setCellStyle(estilo);
                            celda.setCellValue(muestras.get(i).getMun());
                            break;
                        case 11:
                            celda.setCellValue(muestras.get(i).getFpd());
                            break;
                        case 12:
                            celda.setCellValue(muestras.get(i).getScc());
                            break;
                        case 13:
                            celda.setCellValue(muestras.get(i).getBacteria());
                            break;
                        case 14:
                            celda.setCellValue(muestras.get(i).getCfu());
                            break;
                        case 19:
                            celda.setCellValue(muestras.get(i).getInh());
                            break;

                    }
                }
            }
        }
        CellRangeAddress datos1 = new CellRangeAddress(11, cantFila + 10, 5, 14);
        CellRangeAddress datos2 = new CellRangeAddress(11, cantFila + 10, 19, 19);

        // Sets the borders to the merged cell-
        RegionUtil.setBorderTop(BorderStyle.DOUBLE, datos1, sheet);
        RegionUtil.setBorderBottom(BorderStyle.DOUBLE, datos1, sheet);
        RegionUtil.setBorderRight(BorderStyle.DOUBLE, datos1, sheet);
        RegionUtil.setBorderLeft(BorderStyle.DOUBLE, datos1, sheet);

        RegionUtil.setBorderTop(BorderStyle.DOUBLE, datos2, sheet);
        RegionUtil.setBorderBottom(BorderStyle.DOUBLE, datos2, sheet);
        RegionUtil.setBorderRight(BorderStyle.DOUBLE, datos2, sheet);
        RegionUtil.setBorderLeft(BorderStyle.DOUBLE, datos2, sheet);

        try {
            wb.write(fo);
            fo.close();
            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
            JOptionPane.showMessageDialog(null, "Recuerde exportar a SIGLEA",
                    "Recordatorio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }

    }

    public void formatoGral(FileOutputStream fo, JTable tabla) {

        //Crear libro excel
        Workbook wb = new HSSFWorkbook();

        //exportarTablaExcel();
        int cant_batchs = this.getMuestrasBatch(tabla);

        //this.rowsNoNulas(tabla);
        try {
            for (int p = 0; p < cant_batchs; p++) {

                final String headers[] = {"MUESTRA", "BARRA", "GRASA", "PROTEINA", "LACTOSA",
                    "ST", "CELULAS", "RBT", "PAL", "INHIBI", "CRIOSCOP", "%AGUA", "SOLNOGRA",
                    "MUN", "RANGO", "UREA TOTAL", "REMITO"};
                //crear hoja

                Sheet sheet = wb.createSheet("Hoja " + p);
                //FILA HEADERS
                Row row = sheet.createRow((short) 0);
                row.setHeightInPoints(15);
                //fuentes
                HSSFFont fuente = (HSSFFont) wb.createFont();
                fuente.setFontHeightInPoints((short) 11);
                fuente.setFontName("Calibri");
                //estilo headers
                CellStyle estilo = wb.createCellStyle();
                estilo.setFont(fuente);
                estilo.setAlignment(HorizontalAlignment.CENTER);
                CellStyle estilo2 = wb.createCellStyle();
                estilo2.setFont(fuente);
                estilo2.setDataFormat(wb.createDataFormat().getFormat("0.00"));
                estilo2.setAlignment(HorizontalAlignment.CENTER);
                CellStyle estilo3 = wb.createCellStyle();
                estilo3.setFont(fuente);
                estilo3.setDataFormat(wb.createDataFormat().getFormat("0.0"));
                estilo3.setAlignment(HorizontalAlignment.CENTER);

                sheet.setDefaultColumnWidth(10);
                //int h = 0;

                int cantColumna = tabla.getColumnCount();
                int k = 0;
                for (int i = 0; i < headers.length; i++) {

                    Cell cell = row.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(estilo);

                }
                int cb;
                if (p == 0) {
                    cb = 0;
                } else {
                    cb = this.cambio_batch.get(p - 1);
                }

                for (int i = cb; i < this.cambio_batch.get(p); i++) {

                    Row fila = sheet.createRow(k + 1);
                    fila.setHeightInPoints(15);

                    for (int j = 0; j < cantColumna; j++) {

                        Cell celda = fila.createCell(j);
                        celda.setCellStyle(estilo);

                        if (i == -1) {

                            celda.setCellValue(String.valueOf(tabla.getColumnName(j)));

                        } else {
                            Crioscopia crio = new Crioscopia();
                            switch (j) {
                                case 0:
                                    celda.setCellValue(k + 1);
                                    break;
                                case 1:
                                    celda.setCellValue(muestras.get(i).getMuestra());
                                    break;
                                case 2:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getFat() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getFat());
                                    }

                                    break;
                                case 3:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getProteinas() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getProteinas());
                                    }

                                    break;
                                case 4:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getLactosa() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getLactosa());
                                    }
                                    break;
                                case 5:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getTotal_solids() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getTotal_solids());
                                    }
                                    break;
                                case 6:
                                    if (muestras.get(i).getScc() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getScc());
                                    }
                                    break;
                                case 7:
                                    if (muestras.get(i).getCfu() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getCfu());
                                    }
                                    break;

                                case 8:
                                    celda.setCellValue("");
                                    break;
                                case 9:

                                    switch (muestras.get(i).getInh()) {
                                        case 0:
                                            celda.setCellValue("Negativo");
                                            break;
                                        case 1:
                                            celda.setCellValue("Positivo");
                                            break;
                                        default:
                                            celda.setCellValue("");
                                            break;
                                    }

                                    break;
                                case 10:
                                    if (muestras.get(i).getFpd() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getFpd() * -1);
                                    }

                                    break;
                                case 11:
                                    celda.setCellStyle(estilo3);
                                    if (crio.obtenerAgua(muestras.get(i).getFpd()) == 0
                                            || muestras.get(i).getFpd() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(crio.obtenerAgua(muestras.get(i).getFpd()));
                                    }
                                    break;
                                case 12:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getSnf() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getSnf());
                                    }
                                    break;
                                case 13:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getMun() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getMun());
                                    }
                                    break;
                                case 14:
                                    celda.setCellValue("");
                                    break;
                                case 15:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getUreaTotal() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getUreaTotal());
                                    }
                                    break;
                                case 16:
                                    celda.setCellValue(muestras.get(i).getRemito());
                                    break;
                            }
                        }
                    }
                    k++;
                }
            }

            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
            JOptionPane.showMessageDialog(null, "Recuerde exportar a SIGLEA",
                    "Recordatorio", JOptionPane.WARNING_MESSAGE);
            /*String clave = JOptionPane.showInputDialog(null,"Si lo desea puede agregar contraseña","CONTRASEÑA",
                    JOptionPane.INFORMATION_MESSAGE);
            if(!clave.equals("")){
                
            }*/

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }

        try {
            wb.write(fo);
            fo.close();
        } catch (IOException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void formatoGralCaseina(FileOutputStream fo, JTable tabla) {

        //Crear libro excel
        Workbook wb = new HSSFWorkbook();
        //exportarTablaExcel();
        int cant_batchs = this.getMuestrasBatch(tabla);

        //this.rowsNoNulas(tabla);
        try {
            for (int p = 0; p < cant_batchs; p++) {

                final String headers[] = {"MUESTRA", "BARRA", "GRASA", "PROTEINA", "LACTOSA",
                    "ST", "CELULAS", "RBT", "PAL", "INHIBI", "CRIOSCOP", "%AGUA", "SOLNOGRA",
                    "MUN", "RANGO", "UREA TOTAL", "CASEINA"};
                //crear hoja

                Sheet sheet = wb.createSheet("Hoja " + p);
                //FILA HEADERS
                Row row = sheet.createRow((short) 0);
                row.setHeightInPoints(15);
                //fuentes
                HSSFFont fuente = (HSSFFont) wb.createFont();
                fuente.setFontHeightInPoints((short) 11);
                fuente.setFontName("Calibri");
                //estilo headers
                CellStyle estilo = wb.createCellStyle();
                estilo.setFont(fuente);
                estilo.setAlignment(HorizontalAlignment.CENTER);
                CellStyle estilo2 = wb.createCellStyle();
                estilo2.setFont(fuente);
                estilo2.setDataFormat(wb.createDataFormat().getFormat("0.00"));
                estilo2.setAlignment(HorizontalAlignment.CENTER);
                CellStyle estilo3 = wb.createCellStyle();
                estilo3.setFont(fuente);
                estilo3.setDataFormat(wb.createDataFormat().getFormat("0.0"));
                estilo3.setAlignment(HorizontalAlignment.CENTER);

                sheet.setDefaultColumnWidth(10);
                //int h = 0;

                int cantColumna = tabla.getColumnCount();
                int k = 0;
                for (int i = 0; i < headers.length; i++) {

                    Cell cell = row.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(estilo);

                }
                int cb;
                if (p == 0) {
                    cb = 0;
                } else {
                    cb = this.cambio_batch.get(p - 1);
                }

                for (int i = cb; i < this.cambio_batch.get(p); i++) {

                    Row fila = sheet.createRow(k + 1);
                    fila.setHeightInPoints(15);

                    for (int j = 0; j < cantColumna; j++) {

                        Cell celda = fila.createCell(j);
                        celda.setCellStyle(estilo);

                        if (i == -1) {

                            celda.setCellValue(String.valueOf(tabla.getColumnName(j)));

                        } else {
                            Crioscopia crio = new Crioscopia();
                            switch (j) {
                                case 0:
                                    celda.setCellValue(k + 1);
                                    break;
                                case 1:
                                    celda.setCellValue(muestras.get(i).getMuestra());
                                    break;
                                case 2:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getFat() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getFat());
                                    }

                                    break;
                                case 3:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getProteinas() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getProteinas());
                                    }

                                    break;
                                case 4:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getLactosa() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getLactosa());
                                    }
                                    break;
                                case 5:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getTotal_solids() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getTotal_solids());
                                    }
                                    break;
                                case 6:
                                    if (muestras.get(i).getScc() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getScc());
                                    }
                                    break;
                                case 7:
                                    if (muestras.get(i).getCfu() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getCfu());
                                    }
                                    break;

                                case 8:
                                    celda.setCellValue("");
                                    break;
                                case 9:

                                    switch (muestras.get(i).getInh()) {
                                        case 0:
                                            celda.setCellValue("Negativo");
                                            break;
                                        case 1:
                                            celda.setCellValue("Positivo");
                                            break;
                                        default:
                                            celda.setCellValue("");
                                            break;
                                    }

                                    break;
                                case 10:
                                    if (muestras.get(i).getFpd() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getFpd() * -1);
                                    }

                                    break;
                                case 11:
                                    celda.setCellStyle(estilo3);
                                    if (crio.obtenerAgua(muestras.get(i).getFpd()) == 0
                                            || muestras.get(i).getFpd() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(crio.obtenerAgua(muestras.get(i).getFpd()));
                                    }
                                    break;
                                case 12:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getSnf() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getSnf());
                                    }
                                    break;
                                case 13:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getMun() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getMun());
                                    }
                                    break;
                                case 14:
                                    celda.setCellValue("");
                                    break;
                                case 15:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getUreaTotal() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getUreaTotal());
                                    }
                                    break;
                                case 16:
                                    celda.setCellStyle(estilo2);
                                    if (muestras.get(i).getCaseina() == 0) {
                                        celda.setCellValue("");
                                    } else {
                                        celda.setCellValue(muestras.get(i).getCaseina());
                                    }
                                    break;
                            }
                        }
                    }
                    k++;
                }
            }

            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
            JOptionPane.showMessageDialog(null, "Recuerde exportar a SIGLEA",
                    "Recordatorio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }

        try {
            wb.write(fo);
            fo.close();
        } catch (IOException ex) {
            Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FileWriter exportarTxt() {

        javax.swing.filechooser.FileNameExtensionFilter filtrotxt
                = new javax.swing.filechooser.FileNameExtensionFilter("Documentos de texto", "txt");
        final String LAST_USED_FOLDER = "";
        Preferences prefs = Preferences.userRoot().node(getClass().getName());

        final JFileChooser fc = new JFileChooser(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath()));
        //String userprofile = System.getenv("USERPROFILE");
        //fc.setCurrentDirectory(new File(userprofile + "\\Desktop"));
        Action details = fc.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        fc.setFileFilter(filtrotxt);
        int returnVal = fc.showSaveDialog(null);

        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //fileOut = null;
            File fileTXT = null;
            prefs.put(LAST_USED_FOLDER, fc.getSelectedFile().getParent());

            try {
                //Creamos un objeto archivo con la ruta seleccionada
                fileTXT = fc.getSelectedFile();

                //Validamos si en la ruta el archivo se ha especificado la extensión
                String name = fileTXT.getName();
                if (name.indexOf('.') == -1) {

                    //De no ser asi le agregamos
                    name += ".txt";
                    fileTXT = new File(fileTXT.getParentFile(), name);
                }
                //fileOut = new FileOutputStream(fileTXT);
                fw = new FileWriter(fileTXT);
                //this.obtenerModelo();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fw;
    }

    public void formatoGarcia(FileWriter fw) {

        try {
            // TODO add your handling code here:

            String coma = "";

            double porc_agua = 1;

            for (int i = 0; i < muestras.size(); i++) {

                fw.write("I01000000" + ";");
                fw.write(muestras.get(i).getFecha() + ";");
                fw.write(muestras.get(i).getMuestra() + ";");
                fw.write(muestras.get(i).getFat() + ";");
                fw.write(muestras.get(i).getProteinas() + ";");
                fw.write(muestras.get(i).getLactosa() + ";");
                fw.write(muestras.get(i).getSnf() + ";");
                fw.write(muestras.get(i).getTotal_solids() + ";");
                fw.write(muestras.get(i).getMun() + ";");
                fw.write(muestras.get(i).getCfu() + ";");
                fw.write(muestras.get(i).getScc() + ";");
                String inhibidor = "";
                switch (muestras.get(i).getInh()) {
                    case 0:
                        inhibidor = "Neg";
                        break;
                    case 1:
                        inhibidor = "Pos";
                        break;
                    default:
                        inhibidor = "";
                        break;
                }

                fw.write(inhibidor + ";0;0;");
                fw.write((muestras.get(i).getFpd() * -1) + ";");
                if (muestras.get(i).getFpd() == 0) {
                    fw.write("0;0;");
                } else {
                    porc_agua = Crioscopia.obtenerAgua(muestras.get(i).getFpd());
                    fw.write(porc_agua + ";0;");
                }

                //System.out.println(porc_agua);
                fw.write("\r\n");
            }
            fw.close();
            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
            JOptionPane.showMessageDialog(null, "Recuerde exportar a SIGLEA",
                    "Recordatorio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }

    }

    public void formatoWilliner(FileWriter fw) {
        try {
            // TODO add your handling code here:

            String tipos[] = {"rcs", "crioscopia", "grasa", "proteina", "SNG", "ST",
                "lactosa", "ufc", "urea", "inhibidores"};

            for (int i = 0; i < muestras.size(); i++) {

                for (int j = 0; j < 23; j++) {
                    if (muestras.get(i).getCuit().equals("")) {
                        i++;
                    }
                }
                String fecha = muestras.get(i).getFecha() + "\r\n";
                String muest = muestras.get(i).getMuestra() + "\t";

                if (muestras.get(i).getScc() != 0) {
                    fw.write(muest + tipos[0] + "\t" + muestras.get(i).getScc() + "\t" + fecha);
                }
                if (muestras.get(i).getFpd() != 0) {
                    fw.write(muest + tipos[1] + "\t" + muestras.get(i).getFpd("siglea") + "\t" + fecha);
                }
                if (muestras.get(i).getFat() != 0) {
                    fw.write(muest + tipos[2] + "\t" + muestras.get(i).getFat("punto") + "\t" + fecha);
                }
                if (muestras.get(i).getProteinas() != 0) {
                    fw.write(muest + tipos[3] + "\t" + muestras.get(i).getProteinas("punto") + "\t" + fecha);
                }
                if (muestras.get(i).getSnf() != 0) {
                    fw.write(muest + tipos[4] + "\t" + muestras.get(i).getSnf("punto") + "\t" + fecha);
                }
                if (muestras.get(i).getTotal_solids() != 0) {
                    fw.write(muest + tipos[5] + "\t" + muestras.get(i).getTotal_solids("punto") + "\t" + fecha);
                }
                if (muestras.get(i).getLactosa() != 0) {
                    fw.write(muest + tipos[6] + "\t" + muestras.get(i).getLactosa("punto") + "\t" + fecha);
                }
                if (muestras.get(i).getCfu() != 0) {
                    fw.write(muest + tipos[7] + "\t" + muestras.get(i).getCfu() + "\t" + fecha);
                }
                if (muestras.get(i).getMun() != 0) {
                    fw.write(muest + tipos[8] + "\t" + muestras.get(i).getMun() + "\t" + fecha);
                }
                if (muestras.get(i).getInh() != 9) {
                    fw.write(muest + tipos[9] + "\t" + muestras.get(i).getInh("williner") + "\t" + fecha);
                }

            }
            fw.close();
            JOptionPane.showMessageDialog(null, "Exportacion exitosa");
            JOptionPane.showMessageDialog(null, "Recuerde exportar a SIGLEA",
                    "Recordatorio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }

    }

    public DefaultTableModel rowsNoNulas(JTable tabla) {

        DefaultTableModel datosTabla = new DefaultTableModel();

        filasNulas = new ArrayList<>();

        int j = 0;
        for (int i = 0; i < tabla.getRowCount() - 1; i++) {
            if (datosTabla.getValueAt(i, 0) == null) {
                filasNulas.add(i);
                datosTabla.removeRow(i);

            }
            j++;
        }
        filasNulas.add(tabla.getRowCount() - 1);

        tabla.setModel(datosTabla);
        return datosTabla;

    }

    private DefaultTableModel modelo;
    private ArrayList<Muestra> muestras;
    private FileWriter fw;
    private FileOutputStream fo;
    ArrayList<Integer> filasNulas;
    ArrayList<Integer> cambio_batch;

}
