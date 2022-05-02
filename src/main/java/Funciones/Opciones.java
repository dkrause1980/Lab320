/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import com.mycompany.laboratorio_3.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author diego
 */
public class Opciones extends javax.swing.JDialog {

    /**
     * Creates new form Opciones
     */
    Datos_Informe dt = new Datos_Informe();
    JTextField campo;
    static String obser,remito, temperatura,tipo_muestra;
    JTable tabla;
    boolean yaSeleccionado;
    static int seleccion;
    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
    static Date fecha_recepcion, fecha_analisis, fecha_muestreo;

    
    
    public String getRemito() {
        return remito;
    }

    public void setRemito() {
        this.remito = txt_num_rem.getText();
    }

    public Date getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion() {
        this.fecha_recepcion = date_recep.getDate();
    }

    public Date getFecha_analisis() {
        return fecha_analisis;
    }

    public void setFecha_analisis() {
        this.fecha_analisis = date_anal.getDate();
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura() {
        this.temperatura = txt_temp.getText();
    }

    public Date getFecha_muestreo() {
        return fecha_muestreo;
    }

    public void setFecha_muestreo() {
        this.fecha_muestreo = date_muest.getDate();
    }

    public String getTipo_muestra() {
        return tipo_muestra;
    }

    public void setTipo_muestra() {
        this.tipo_muestra = txt_tipo_muest.getText();
    }
    
    
    

    public Opciones(Principal parent, boolean modal) {
        super(parent, modal);
        principal = parent;
        initComponents();
       //System.out.println(seleccion);
        this.rellenar();
        java.util.Date hoy = new Date();
        
        txt_emision.setText(formateador.format(hoy));
        tabla = principal.getTabla();
        txt_cant_muest.setText(this.cantMuestras());

    }
    
    public String cantMuestras (){
        ArrayList<Muestra> muestras;
        Exportar exp = new Exportar();
        muestras = exp.obtenerModelo(tabla);
        return String.valueOf(muestras.size());
    }
    
    
    
    

    public void rellenar() {

        ArrayList<String[]> empresas = Otras.leer_lista_empresas();

        for (String[] datos : empresas) {

            jComboBox1.addItem(datos[0]);

        }
        
        jComboBox1.setSelectedIndex(this.getSeleccion());
        txt_num_rem.setText(this.getRemito());
        date_recep.setDate(this.getFecha_recepcion());
        date_anal.setDate(this.getFecha_analisis());
        date_muest.setDate(this.getFecha_muestreo());
        txt_temp.setText(this.getTemperatura());
        txt_tipo_muest.setText(this.getTipo_muestra());
        

    }

    public String recuperafecha(JDateChooser jdt) {

        String dia = Integer.toString(jdt.getCalendar().get(Calendar.DAY_OF_MONTH));
        if (Integer.valueOf(dia) < 10) {
            dia = "0" + dia;
        }
        String mes = Integer.toString(jdt.getCalendar().get(Calendar.MONTH) + 1);
        if (Integer.valueOf(mes) < 10) {
            mes = "0" + mes;
        }
        String year = Integer.toString(jdt.getCalendar().get(Calendar.YEAR));
        String fecha = (dia + "/" + mes + "/" + year);
        return fecha;

    }

    public String convertirFecha(Date date) {
        String fecha;
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        return df.format(date);

    }

    public void validarAceptar() {
        if (null != date_anal.getDate() || null != date_muest.getDate()
                || null != date_recep.getDate()) {

            btnAceptar.setEnabled(true);
        }
    }

    public String campoNulo(JTextField campo) {
        this.campo = campo;
        if (null == this.campo) {
            return "";
        } else {
            return this.campo.getText();
        }
    }

    public boolean validarJDate() {
        boolean bandera = false;
        for (int i = 0; i < this.getComponentCount(); i++) {
            if (this.getComponent(i) instanceof JDateChooser) {

                JDateChooser camp = (JDateChooser) this.getComponent(i);
                if (null == camp.getDate()) {

                    bandera = true;
                }

            }
        }
        return bandera;
    }
    
    public void setSeleccion (){
        this.seleccion = jComboBox1.getSelectedIndex();
    }
    
    public int getSeleccion(){
        return seleccion;
    }
    
    

    public void guardarDatosInforme(Datos_Informe dt) {

        dt.setNum_informe(campoNulo(txt_num_informe));
        dt.setNum_remito(campoNulo(txt_num_rem));
        dt.setCant_muestras(txt_cant_muest.getText());
        dt.setTemperatura(txt_temp.getText());
        dt.setTipo_muestra(txt_tipo_muest.getText());
        dt.setSolicitante(jComboBox1.getSelectedItem().toString());
        dt.setLugar(jComboBox1.getSelectedIndex());
        dt.setF_emision(txt_emision.getText());

        dt.setF_analisis(this.recuperafecha(date_anal));
        dt.setF_muestreo(this.recuperafecha(date_muest));
        dt.setF_recepcion(this.recuperafecha(date_recep));
        dt.setGrasa(ChkGrasa.isSelected());
        dt.setProteina(chkProteina.isSelected());
        dt.setCrio(chkCrioscopia.isSelected());
        dt.setRcs(chkRcs.isSelected());
        dt.setRmv(chkRmv.isSelected());
        dt.setSng(chkSng.isSelected());
        dt.setSt(chkSt.isSelected());
        dt.setLactosa(chkLactosa.isSelected());
        dt.setUrea(chkUrea.isSelected());
        dt.setObs(chkObs.isSelected());
        dt.setInh(chkInh.isSelected());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ChkGrasa = new javax.swing.JCheckBox();
        chkProteina = new javax.swing.JCheckBox();
        chkLactosa = new javax.swing.JCheckBox();
        chkSng = new javax.swing.JCheckBox();
        chkSt = new javax.swing.JCheckBox();
        chkUrea = new javax.swing.JCheckBox();
        chkCrioscopia = new javax.swing.JCheckBox();
        chkRcs = new javax.swing.JCheckBox();
        chkRmv = new javax.swing.JCheckBox();
        chkInh = new javax.swing.JCheckBox();
        chkObs = new javax.swing.JCheckBox();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        txt_num_informe = new javax.swing.JTextField();
        txt_num_rem = new javax.swing.JTextField();
        txt_emision = new javax.swing.JTextField();
        txt_temp = new javax.swing.JTextField();
        txt_tipo_muest = new javax.swing.JTextField();
        txt_cant_muest = new javax.swing.JTextField();
        date_recep = new com.toedter.calendar.JDateChooser();
        date_anal = new com.toedter.calendar.JDateChooser();
        date_muest = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Solicitante");

        jLabel2.setText("N° de informe");

        jLabel3.setText("N° de remito");

        jLabel4.setText("Fecha de recepción");

        jLabel5.setText("Fecha de análisis");

        jLabel6.setText("Fecha de emisión");

        jLabel7.setText("Temperatura de recepción");

        jLabel8.setText("Fecha de muestreo");

        jLabel9.setText("Cant de muestras");

        jLabel10.setText("Tipo de muestras");

        ChkGrasa.setText("Grasa");

        chkProteina.setText("Proteína");

        chkLactosa.setText("Lactosa");

        chkSng.setText("SNG");

        chkSt.setText("ST");

        chkUrea.setText("Urea");
        chkUrea.setToolTipText("");

        chkCrioscopia.setText("Crioscopía");

        chkRcs.setText("RCS");

        chkRmv.setText("RMV");

        chkInh.setText("Inhibidores");

        chkObs.setText("Observaciones");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txt_num_informe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_num_informeActionPerformed(evt);
            }
        });

        txt_emision.setEditable(false);
        txt_emision.setText("jTextField5");

        txt_cant_muest.setEditable(false);

        date_recep.setMaxSelectableDate(new Date());

        date_anal.setMaxSelectableDate(new Date());

        date_muest.setMaxSelectableDate(new Date());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ChkGrasa)
                            .addComponent(chkSt)
                            .addComponent(chkRmv))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkProteina)
                            .addComponent(chkUrea)
                            .addComponent(chkInh)))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkLactosa)
                            .addComponent(chkCrioscopia))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkRcs)
                            .addComponent(chkSng)))
                    .addComponent(chkObs)
                    .addComponent(txt_cant_muest)
                    .addComponent(txt_tipo_muest)
                    .addComponent(txt_temp)
                    .addComponent(txt_num_rem)
                    .addComponent(txt_num_informe)
                    .addComponent(txt_emision)
                    .addComponent(date_muest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_recep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_anal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_num_informe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_num_rem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(date_recep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(date_anal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_emision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_temp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(date_muest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_tipo_muest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_cant_muest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChkGrasa)
                    .addComponent(chkProteina)
                    .addComponent(chkLactosa)
                    .addComponent(chkSng)
                    .addComponent(btnAceptar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkSt)
                    .addComponent(chkUrea)
                    .addComponent(chkCrioscopia)
                    .addComponent(chkRcs)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkRmv)
                    .addComponent(chkInh)
                    .addComponent(chkObs))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        
        this.setSeleccion();
        this.setRemito();
        this.setFecha_recepcion();
        this.setFecha_analisis();
        this.setFecha_muestreo();
        this.setTemperatura();
        this.setTipo_muestra();
        //dt = new Datos_Informe();


        if (null != date_anal.getDate() && null != date_muest.getDate()
                && null != date_recep.getDate()) {

            try {
                Informe informe = new Informe();
                
                this.guardarDatosInforme(dt);
                informe.guardarInforme();
                tabla = principal.getTabla();
                informe.cargarDatosPdf(dt,tabla);
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
                //JOptionPane.showMessageDialog(null, ex.getMessage());
            } //catch (Exception en){
                //JOptionPane.showMessageDialog(null, en.getMessage());

            //}
        } else {
            JOptionPane.showMessageDialog(null, "Completar todos los campos fecha",
                    "Atención", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txt_num_informeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_num_informeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_num_informeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Opciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Opciones dialog = new Opciones((Principal) new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    Principal principal;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ChkGrasa;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox chkCrioscopia;
    private javax.swing.JCheckBox chkInh;
    private javax.swing.JCheckBox chkLactosa;
    private javax.swing.JCheckBox chkObs;
    private javax.swing.JCheckBox chkProteina;
    private javax.swing.JCheckBox chkRcs;
    private javax.swing.JCheckBox chkRmv;
    private javax.swing.JCheckBox chkSng;
    private javax.swing.JCheckBox chkSt;
    private javax.swing.JCheckBox chkUrea;
    private com.toedter.calendar.JDateChooser date_anal;
    private com.toedter.calendar.JDateChooser date_muest;
    private com.toedter.calendar.JDateChooser date_recep;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txt_cant_muest;
    private javax.swing.JTextField txt_emision;
    private javax.swing.JTextField txt_num_informe;
    private javax.swing.JTextField txt_num_rem;
    private javax.swing.JTextField txt_temp;
    private javax.swing.JTextField txt_tipo_muest;
    // End of variables declaration//GEN-END:variables
}
