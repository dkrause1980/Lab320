/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JCheckBox;

/**
 *
 * @author diego
 */



public class Datos_Informe {
    private String solicitante, num_informe, num_remito,  temperatura, 
            tipo_muestra, cant_muestras, f_emision, establecimiento, direccion;
    private String f_analisis, f_recepcion, f_muestreo;
    private int lugar;
    SimpleDateFormat formateador = new SimpleDateFormat("dd/mm/yyyy");
    boolean grasa, proteina, lactosa, sng, st, urea, crio, rcs, rmv, inh, obs;
    
    ArrayList <String[]> datos = Otras.leer_lista_empresas();
    
    public String getEstablecimiento (int i){
        
        String [] dato = datos.get(i);
        establecimiento = dato[1];
        
        return establecimiento;
        
    }
    
    public String getDireccion (int i){
        
        String [] dato = datos.get(i);
        direccion = dato[2];
        
        return direccion;
        
    }
    
    public int getLugar(){
        return lugar;
    }
    
    public void setLugar(int lugar){
        this.lugar = lugar;
    }

    public boolean isGrasa() {
        return grasa;
    }

    public void setGrasa(boolean grasa) {
        this.grasa = grasa;
    }

    public boolean isProteina() {
        return proteina;
    }

    public void setProteina(boolean proteina) {
        this.proteina = proteina;
    }

    public boolean isLactosa() {
        return lactosa;
    }

    public void setLactosa(boolean lactosa) {
        this.lactosa = lactosa;
    }

    public boolean isSng() {
        return sng;
    }

    public void setSng(boolean sng) {
        this.sng = sng;
    }

    public boolean isSt() {
        return st;
    }

    public void setSt(boolean st) {
        this.st = st;
    }

    public boolean isUrea() {
        return urea;
    }

    public void setUrea(boolean urea) {
        this.urea = urea;
    }

    public boolean isCrio() {
        return crio;
    }

    public void setCrio(boolean crio) {
        this.crio = crio;
    }

    public boolean isRcs() {
        return rcs;
    }

    public void setRcs(boolean rcs) {
        this.rcs = rcs;
    }

    public boolean isRmv() {
        return rmv;
    }

    public void setRmv(boolean rmv) {
        this.rmv = rmv;
    }

    public boolean isInh() {
        return inh;
    }

    public void setInh(boolean inh) {
        this.inh = inh;
    }

    public boolean isObs() {
        return obs;
    }

    public void setObs(boolean obs) {
        
        this.obs = obs;
    }
    
    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getNum_informe() {
        return num_informe;
    }

    public void setNum_informe(String num_informe) {
        this.num_informe = num_informe;
    }

    public String getNum_remito() {
        return num_remito;
    }

    public void setNum_remito(String num_remito) {
        this.num_remito = num_remito;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getTipo_muestra() {
        return tipo_muestra;
    }

    public void setTipo_muestra(String tipo_muestra) {
        this.tipo_muestra = tipo_muestra;
    }

    public String getCant_muestras() {
        return cant_muestras;
    }

    public void setCant_muestras(String cant_muestras) {
        this.cant_muestras = cant_muestras;
    }

    public String getF_analisis() {
        return f_analisis;
    }

    public void setF_analisis(String f_analisis) {
        this.f_analisis = f_analisis;
    }
    
    

    public String getF_recepcion() {
        return f_recepcion;
    }

    public void setF_recepcion(String f_recepcion) {
        this.f_recepcion = f_recepcion;
    }

    public String getF_emision() {
        return f_emision;
    }

    public void setF_emision(String f_emision) {
        this.f_emision = f_emision;
    }

    public String getF_muestreo() {
        return f_muestreo;
    }

    public void setF_muestreo(String f_muestreo) {
        this.f_muestreo = f_muestreo;
    }

    

    
    
}
