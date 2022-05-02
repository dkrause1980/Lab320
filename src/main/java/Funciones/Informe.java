/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.mycompany.laboratorio_3.Principal;
import java.awt.Font;
import static java.awt.Font.BOLD;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class Informe {

    /*public static final String LABORATORIO = "D:\\Documentos\\NetBeansProjects\\Laboratorio_3\\src\\main\\java\\Imagenes\\laboratorio.jpg";
    public static final String OAA = "D:\\Documentos\\NetBeansProjects\\Laboratorio_3\\src\\main\\java\\Imagenes\\oaa.jpg";
    public static final String FIRMA = "D:\\Documentos\\NetBeansProjects\\Laboratorio_3\\src\\main\\java\\Imagenes\\firma.jpg";
    public static final String MUSSO = "D:\\Documentos\\NetBeansProjects\\Laboratorio_3\\src\\main\\java\\Imagenes\\musso.jpg";
    */
    public static final String LABORATORIO = "Imagenes\\laboratorio.png";
    public static final String OAA = "Imagenes\\oaa2.jpg";
    public static final String FIRMA = "Imagenes\\firma.png";
    public static final String MUSSO = "Imagenes\\musso.png";
    
    public FileOutputStream guardarInforme() {

        javax.swing.filechooser.FileNameExtensionFilter filtropdf
                = new javax.swing.filechooser.FileNameExtensionFilter("Documentos pdf", "pdf");

        final String LAST_USED_FOLDER = "";
        Preferences prefs = Preferences.userRoot().node(getClass().getName());

        final JFileChooser fc = new JFileChooser(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath()));
        //String userprofile = System.getenv("USERPROFILE");
        //fc.setCurrentDirectory(new File(userprofile + "\\Desktop"));
        Action details = fc.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        fc.setFileFilter(filtropdf);
        int returnVal = fc.showSaveDialog(null);

        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File filePDF = null;
            prefs.put(LAST_USED_FOLDER, fc.getSelectedFile().getParent());
            try {
                //Creamos un objeto archivo con la ruta seleccionada
                filePDF = fc.getSelectedFile();

                //Validamos si en la ruta el archivo se ha especificado la extensión
                String name = filePDF.getName();
                if (name.indexOf('.') == -1) {

                    //De no ser asi le agregamos
                    name += ".pdf";
                    filePDF = new File(filePDF.getParentFile(), name);
                }
                fo = new FileOutputStream(filePDF);
                
                // sacamos los datos de la tabla

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Exportar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fo;
    }

    public void cargarDatosPdf(Datos_Informe dt, JTable tabla) throws IOException {

        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        PdfFont normal = PdfFontFactory.createFont(FontConstants.HELVETICA);
        URL urlLabo = this.getClass().getResource("/Imagenes/laboratorio2.png");
        URL urlOaa = this.getClass().getResource("/Imagenes/oaa2.jpg");
        URL urlFirma = this.getClass().getResource("/Imagenes/firma.png");
        URL urlMusso = this.getClass().getResource("/Imagenes/musso.png");
        

        try {
            //dt = new Datos_Informe();
            //dt = new Datos_Informe();
            PdfWriter writer = new PdfWriter(fo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(10, 10, 10, 20);
            document.setFontSize(8f);
            ArrayList<Muestra> muestras;
            Exportar exp = new Exportar();
            muestras = exp.obtenerModelo(tabla);
            int cantHojas = (int) Math.ceil((double) muestras.size() / 40);

            int muestrasUltHoja = muestras.size() % 40;

            int hojaNro = 0;
            int cantMuestras = muestras.size();
            int muestraInicio;
            int muestrasPorHoja;
            boolean fin = false;

            // empieza construccion pdf
            //linea
            while (cantHojas > 0) {
                muestraInicio = 40 * hojaNro;

                hojaNro++;

                if (cantMuestras >= 40) {
                    muestrasPorHoja = muestraInicio + 40;

                } else {
                    muestrasPorHoja = muestras.size();
                    fin = true;
                }
                //System.out.println(muestraInicio + "-" + cantMuestras + "-" + muestrasPorHoja + "-" + muestrasUltHoja);

                String guion = "_";
                for (int i = 0; i < 126; i++) {
                    guion = guion + "_";
                }
                // imagenes
                Image laboratorio = new Image(ImageDataFactory.create(urlLabo));
                Image oaa = new Image(ImageDataFactory.create(urlOaa));
                Image firma = new Image(ImageDataFactory.create(urlFirma));
                Image musso = new Image(ImageDataFactory.create(urlMusso));

                laboratorio.setWidthPercent(30);
                laboratorio.setHeight(50);
                oaa.setWidthPercent(25);
                oaa.setHeight(60);
                firma.setWidthPercent(12);
                firma.setHeight(25);
                musso.setWidthPercent(12);
                musso.setHeight(25);

                // Estilos
                String par1 = "Sistema de Gestión de la Calidad ISO 17025";
                String par2 = "Comisario V. Kaiser 129 / 2300 Rafaela - Santa Fe";
                String par3 = "Tel-Fax 03492-570889 / rafelab@wilnet.com.ar";
                String par4 = "INFORME DE RESULTADOS Nº: " + dt.getNum_informe();

                //contruccion primer parte
                Table table = new Table(2);
                table.addCell(getCell(laboratorio, HorizontalAlignment.LEFT)
                        //.add(par1 + "\n")
                        .add(par2 + "\n")
                        .add(par3)
                );
                table.addCell(getCell(oaa, HorizontalAlignment.RIGHT));
                table.setFontSize(5f);

                //contruccion 2da parte
                Paragraph espaciado = new Paragraph().add("\n");

                Paragraph inf = new Paragraph()
                        .add(par4).setTextAlignment(TextAlignment.CENTER).setFont(bold)
                        .add("\n")
                        .add(guion);
                //.add("\n");

                Table tabla2 = new Table(UnitValue.createPercentArray(new float[]{15,50,25,10}));
                tabla2.addCell(getCell("Solicitante:", TextAlignment.LEFT));
                tabla2.addCell(getCell(dt.getSolicitante(), TextAlignment.LEFT).setFont(bold));
                tabla2.addCell(getCell("N° de remito:", TextAlignment.LEFT));
                tabla2.addCell(getCell(dt.getNum_remito(), TextAlignment.LEFT).setFont(bold));

                Table tabla3 = new Table(UnitValue.createPercentArray(new float[]{15,50,25,10}));
                tabla3.addCell(getCell("Establecimiento:", TextAlignment.LEFT));
                tabla3.addCell(getCell(dt.getEstablecimiento(dt.getLugar()), TextAlignment.LEFT).setFont(bold));
                tabla3.addCell(getCell("Fecha de recepcion:", TextAlignment.LEFT));
                tabla3.addCell(getCell(dt.getF_recepcion(), TextAlignment.LEFT).setFont(bold));

                Table tabla4 = new Table(UnitValue.createPercentArray(new float[]{15,50,25,10}));
                tabla4.addCell(getCell("Dirección:", TextAlignment.LEFT));
                tabla4.addCell(getCell(dt.getDireccion(dt.getLugar()), TextAlignment.LEFT).setFont(bold));
                tabla4.addCell(getCell("Fecha de análisis:", TextAlignment.LEFT));
                tabla4.addCell(getCell(dt.getF_analisis(), TextAlignment.LEFT).setFont(bold));

                Table tabla5 = new Table(UnitValue.createPercentArray(new float[]{15,50,25,10}));
                tabla5.addCell(getCell("               ", TextAlignment.LEFT));
                tabla5.addCell(getCell("               ", TextAlignment.LEFT));
                tabla5.addCell(getCell("Fecha de emisión:", TextAlignment.LEFT));
                tabla5.addCell(getCell(dt.getF_emision(), TextAlignment.LEFT).setFont(bold));

                Paragraph inf2 = new Paragraph().add(guion);
                //.add("\n");

                Table tabla6 = new Table(UnitValue.createPercentArray(new float[]{30,35,25,10}));
                tabla6.addCell(getCell("Tipo de muestra:", TextAlignment.LEFT));
                tabla6.addCell(getCell(dt.getTipo_muestra(), TextAlignment.LEFT).setFont(bold));
                tabla6.addCell(getCell("Fecha de muestreo:", TextAlignment.LEFT));
                tabla6.addCell(getCell(dt.getF_muestreo(), TextAlignment.LEFT).setFont(bold));

                Table tabla7 = new Table(UnitValue.createPercentArray(new float[]{30,35,25,10}));
                tabla7.addCell(getCell("Total de muestras de este informe:", TextAlignment.LEFT));
                tabla7.addCell(getCell(dt.getCant_muestras(), TextAlignment.LEFT).setFont(bold));
                tabla7.addCell(getCell("Temp de recepción en °C:", TextAlignment.LEFT));
                tabla7.addCell(getCell(dt.getTemperatura(), TextAlignment.LEFT).setFont(bold));

                Table tabla8 = new Table(UnitValue.createPercentArray(new float[]{30,35,25,10}));
                tabla8.addCell(getCell("Observaciones referidas a la recepción:", TextAlignment.LEFT));
                String obser;
                if (dt.isObs()) {
                    obser = JOptionPane.showInputDialog(null, "Agregar observación",
                            "Observación", JOptionPane.QUESTION_MESSAGE);
                } else {
                    obser = " Sin Observaciones";
                }
                tabla8.addCell(getCell(obser, TextAlignment.LEFT).setFont(bold));
                tabla8.addCell(getCell("                 ", TextAlignment.LEFT));
                tabla8.addCell(getCell("                 ", TextAlignment.LEFT));

                Paragraph refe = new Paragraph("Referencias y Métodos:");
                refe.setFont(bold);
                refe.setTextAlignment(TextAlignment.LEFT);
                Paragraph gr2 = new Paragraph("Grasa: Determinación del "
                        + "contenido de grasa (ISO 9622/ IDF 141); Proteínas: "
                        + "Determinación del contenido de proteínas (ISO 9622/ "
                        + "IDF 141); Lactosa: Determinación del contenido de "
                        + "lactosa (ISO 9622/ IDF 141); SNG: Determinación del "
                        + "contenido de sólidos no grasos (ISO 9622/IDF141); ST: "
                        + "Determinación del contenido de sólidos totales "
                        + "(ISO 9622/ IDF 141); P.crioscópico: Estimación del "
                        + "punto de congelación (ISO 9622/IDF 141); RCS: "
                        + "Recuento de células somáticas (ISO 13366-2 IDF 148-2)"
                        + "; RMV: Estimación del recuento de microorganismos "
                        + "aerobios mesófilos viables (TA 004 LC); Inhibidores: "
                        + "Determinación de residuos de inhibidores (TA 005 LC)."
                        + " MUN: Determinación del contenido de Nitrógeno Ureico"
                        + " (ISO 9622/ IDF 141)"+"\n" +"C: cortada – V: vacía - "
                        + "Otros: detallar");
                /*Paragraph gr = new Paragraph("Grasa: Determinación del contenido de grasa "
                        + "(ISO 9622/ IDF 141); Proteínas: Determinación del contenido "
                        + "de proteínas (ISO 9622/ IDF 141); ST: Determinación del "
                        + "contenido de sólidos totales (ISO 9622/ IDF 141);  Lact: "
                        + "Determinación del contenido de lactosa (ISO 9622/ IDF 141); "
                        + "SNG: Determinación del contenido de sólidos no grasos "
                        + "(ISO 9622/IDF141); RCS: Recuento de células somáticas "
                        + "(ISO 13366-2 IDF 148-2); P. Crio: Estimación del punto "
                        + "de congelamiento (ISO 9622/IDF 141); RMV: Estimación del "
                        + "recuento de microorganismos aerobios mesófilos viables ");*/
                gr2.setTextAlignment(TextAlignment.JUSTIFIED);
                gr2.setFontSize(7f);
                Paragraph res = new Paragraph("Los resultados del presente informe "
                        + "corresponden a muestras procesadas en laboratorio Rafelab "
                        + "SRL, las cuales fueron obtenidas, identificadas y "
                        + "enviadas por el solicitante. Este informe se encuentra "
                        + "disponible en soporte electrónico y en papel en caso de  "
                        + "requerimiento del solicitante. Este informe no podrá ser "
                        + "reproducido parcialmente sin la autorización escrita del "
                        + "laboratorio Rafelab SRL, que deslinda toda "
                        + "responsabilidad respecto a la utilización que se de a "
                        + "los resultados.");
                res.setFontSize(6f);
                res.setTextAlignment(TextAlignment.JUSTIFIED);

                Table tabla10 = new Table(new float[]{450, 100});

                tabla10.addCell(getCell("Los parámetros marcados con (*) no "
                        + "estan incluídos en el alcance de la acreditación del OAA", TextAlignment.LEFT));
                tabla10.addCell(getCell("Página: " + hojaNro + " de " + cantHojas, TextAlignment.RIGHT));
                tabla10.setFont(bold);
                tabla10.setFontSize(6f);

                Table tabla9 = new Table(1);
                tabla9.addCell(getCell(firma, HorizontalAlignment.RIGHT));
                tabla9.addCell(getCell(musso, HorizontalAlignment.RIGHT));

                //armo tabla
                Table tablaPrincipal = new Table(new float[]{15, 7, 7, 7, 7, 7, 7, 8, 6, 6, 12, 15});
                tablaPrincipal.setWidthPercent(100);
                tablaPrincipal.addCell(agregarCabecera("Identificación Muestra"));
                tablaPrincipal.addCell(agregarCabecera("Grasa % g/100ml"));
                tablaPrincipal.addCell(agregarCabecera("Proteína % g/100ml"));
                tablaPrincipal.addCell(agregarCabecera("Lactosa % (*) g/100ml"));
                tablaPrincipal.addCell(agregarCabecera("SNG % (*) g/100ml"));
                tablaPrincipal.addCell(agregarCabecera("ST % (*) g/100ml"));
                tablaPrincipal.addCell(agregarCabecera("MUN (*) mg/dl"));
                tablaPrincipal.addCell(agregarCabecera("P.Crioscópico m°C"));
                tablaPrincipal.addCell(agregarCabecera("RCS x 1000 cél/ml"));
                tablaPrincipal.addCell(agregarCabecera("RMV x 1000 ufc/ml"));
                tablaPrincipal.addCell(agregarCabecera("Inhibidores"));
                tablaPrincipal.addCell(agregarCabecera("Observaciones"));

                tablaPrincipal.setFontSize(6f);

                // and data
                DecimalFormatSymbols simboloPunto = new DecimalFormatSymbols(Locale.UK);
                DecimalFormatSymbols simboloComa = new DecimalFormatSymbols();
                DecimalFormat formateaPunto = new DecimalFormat("0.00", simboloPunto);
                DecimalFormat formateaComa = new DecimalFormat("0.00", simboloComa);
                for (int i = muestraInicio; i < muestrasPorHoja; i++) {
                    String muestra = muestras.get(i).getMuestra() + "";
                    String grasa = "-.-";
                    String proteina = "-.-";
                    String lactosa = "-.-";
                    String snf = "-.-";
                    String ts = "-.-";
                    String mun = "-.-";
                    String fpd = "-.-";
                    String scc = "-.-";
                    String cfu = "-.-";
                    String inh = "-.-";
                    String obs = "-.-";

                    if (muestra.length() > 16) {
                        muestra = muestra.substring(0, 16);

                    }

                    if (dt.isGrasa()) {
                        grasa = muestras.get(i).getFat("coma");
                        if(grasa.equals("0,00")){
                            grasa = "-.-";
                        }
                    }

                    if (dt.isProteina()) {
                        proteina = muestras.get(i).getProteinas("coma");
                        if(proteina.equals("0,00")){
                            proteina = "-.-";
                        }
                    }
                    if (dt.isLactosa()) {
                        lactosa = muestras.get(i).getLactosa("coma");
                        if(lactosa.equals("0,00")){
                            lactosa = "-.-";
                        }
                    }
                    if (dt.isSng()) {
                        snf = muestras.get(i).getSnf("coma");
                        if(snf.equals("0,00")){
                            snf = "-.-";
                        }
                    }
                    if (dt.isSt()) {
                        ts = muestras.get(i).getTotal_solids("coma");
                        if(ts.equals("0,00")){
                            ts = "-.-";
                        }
                    }
                    if (dt.isUrea()) {
                        mun = muestras.get(i).getMun("coma");
                        if(mun.equals("0,00")){
                            mun = "-.-";
                        }
                    }
                    if (dt.isCrio()) {
                        fpd = muestras.get(i).getFpd() * -1 + "";
                        if(fpd.equals("0")){
                            fpd = "-.-";
                        }
                    }
                    if (dt.isRcs()) {
                        scc = muestras.get(i).getScc() + "";
                        if(scc.equals("0")){
                            scc = "-.-";
                        }
                    }
                    if (dt.isRmv()) {
                        cfu = muestras.get(i).getCfu() + "";
                        if(cfu.equals("0")){
                            cfu = "-.-";
                        }
                    }
                    if (dt.isInh()) {
                        inh = muestras.get(i).getInh("completo") + "";
                        if(inh.equals("")){
                            inh = "-.-";
                        }
                    }
                    tablaPrincipal.addCell(addCommonCell(muestra));
                    tablaPrincipal.addCell(addCommonCell(grasa));
                    tablaPrincipal.addCell(addCommonCell(proteina));
                    tablaPrincipal.addCell(addCommonCell(lactosa));
                    tablaPrincipal.addCell(addCommonCell(snf));
                    tablaPrincipal.addCell(addCommonCell(ts));
                    tablaPrincipal.addCell(addCommonCell(mun));
                    tablaPrincipal.addCell(addCommonCell(fpd));
                    tablaPrincipal.addCell(addCommonCell(scc));
                    tablaPrincipal.addCell(addCommonCell(cfu));
                    tablaPrincipal.addCell(addCommonCell(inh));
                    tablaPrincipal.addCell(addCommonCell(obs));
                }

                if (cantMuestras < 40) {

                    for (int i = 0; i < 40 - muestrasUltHoja; i++) {
                        for (int j = 0; j < 12; j++) {
                            tablaPrincipal.addCell(addCommonCell("Nada").setFontColor(Color.WHITE));
                        }
                    }

                }
                //agrego al doc
                document.add(table)
                        .add(inf)
                        .add(tabla2)
                        .add(tabla3)
                        .add(tabla4)
                        .add(tabla5)
                        .add(inf2)
                        .add(tabla6)
                        .add(tabla7)
                        .add(tabla8)
                        .add(espaciado)
                        .add(tablaPrincipal)
                        //.add(espaciado)
                        .add(tabla9)
                        .add(refe)
                        .add(gr2)
                        .add(res)
                        .add(tabla10);

                //cantMuestras = cantMuestras + 40;
                cantMuestras = cantMuestras - 40;
                if (fin) {
                    cantHojas = 0;
                }

            }
            document.close();
            JOptionPane.showMessageDialog(null, "Exportacion exitosa");

        } catch (MalformedURLException ex) {
            Logger.getLogger(Informe.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
        }

    }

    public Cell getCell(String text, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public Cell getCell(Image imagen, HorizontalAlignment alignment) {
        Cell cell = new Cell().add(imagen);
        cell.setPadding(0);
        imagen.setHorizontalAlignment(alignment);
        //imagen.setWidthPercent(20);
        //imagen.setHeight(40);
        //cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public Cell agregarCabecera(String text) throws IOException {
        Paragraph p = new Paragraph(text);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Cell cell = new Cell().add(p);
        cell.setPadding(0);
        cell.setTextAlignment(TextAlignment.CENTER);
        //cell.setFontSize(6);
        cell.setFont(bold);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        return cell;

    }

    public Cell addCommonCell(String value) {
        Paragraph p = new Paragraph(value);
        Cell cell = new Cell().add(p);
        cell.setPadding(0);
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }

    FileOutputStream fo;
    ArrayList datos_solicitante;
    Datos_Informe dt;
    //JTable tabla, tabla1;

    Principal principal;

}
