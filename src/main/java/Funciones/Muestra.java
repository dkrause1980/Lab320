/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author diego
 */
public class Muestra {

    String cuit, remito, batch, muestra;
    String fecha,w;
    double fat, proteinas, lactosa, snf, total_solids, mun,ureaTotal, caseina, fpd, scc;
    int bacteria, cfu, inh;
    DecimalFormatSymbols simboloPunto = new DecimalFormatSymbols(Locale.UK);
    DecimalFormatSymbols simboloComa = new DecimalFormatSymbols();
    DecimalFormat formateaPunto = new DecimalFormat("0.00",simboloPunto);
    DecimalFormat formateaComa = new DecimalFormat("0.00",simboloComa);
    

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getMuestra() {
        return muestra;
    }

    public void setMuestra(String muestra) {
        this.muestra = muestra;
    }
    
    public String getFpd(String formato) {
        
        if(formato.equals("siglea")){
            return String.format(Locale.UK, "%.3f", (fpd/-1000d));
        }
        else {
            return String.valueOf(fpd);
        }
        
    }
    
    /*public double getFpd(String form){
        
        return (double)Math.round(fpd/-1000d);
        
    }*/
    public int getFpd() {
        
        return (int)fpd;
    }

    public void setFpd(Double fpd) {
        this.fpd = fpd;
    }

    
    /*public String getScc(){
        if (scc==0){
            return "";
        }else{
            return String.valueOf(scc);
        }
    }*/
    
    public int getScc(){
        return (int)scc;
    }

    public void setScc(Double scc) {
        this.scc = scc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getFat(String simbolo){
        if(simbolo.equals("punto")){
            return formateaPunto.format(fat);
        }else {
            return formateaComa.format(fat);
        }
        
    }
    public double getFat(){
        return ((double) Math.round(fat * 100d) / 100d);
        
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

   
    public String getProteinas(String simbolo){
        if(simbolo.equals("punto")){
            return formateaPunto.format(proteinas);
        }else {
            return formateaComa.format(proteinas);
        }
    }
    public double getProteinas(){
        return ((double) Math.round(proteinas * 100d) / 100d);
        
    }
    

    public void setProteinas(Double proteinas) {
        this.proteinas = proteinas;
    }

    public String getLactosa(String simbolo){
        if(simbolo.equals("punto")){
            return formateaPunto.format(lactosa);
        }else {
            return formateaComa.format(lactosa);
        }      

    }
    public double getLactosa(){
        return ((double) Math.round(lactosa * 100d) / 100d);
        
    }

    public void setLactosa(Double lactosa) {
        this.lactosa = lactosa;
    }

    
    
    public String getSnf(String simbolo){
        if(simbolo.equals("punto")){
            return formateaPunto.format(snf);
        }else {
            return formateaComa.format(snf);
        }
    }
    public double getSnf(){
        return ((double) Math.round(snf * 100d) / 100d);
        
    }

    public void setSnf(Double snf) {
        this.snf = snf;
    }

    public String getTotal_solids(String simbolo){
        if(simbolo.equals("punto")){
            return formateaPunto.format(total_solids);
        }else {
            return formateaComa.format(total_solids);
        }   

    }

    public void setTotal_solids(Double total_solids) {
        this.total_solids = total_solids;
    }
    
    public double getTotal_solids(){
        return ((double) Math.round(total_solids * 100d) / 100d);
        
    }

    
    public String getMun(String simbolo){
        if(simbolo.equals("punto")){
            return formateaPunto.format(mun);
        }else {
            return formateaComa.format(mun);
        }
    }
    
    public double getMun(){
        return ((double) Math.round(mun * 100d) / 100d);
        
    }
    

    public void setMun(Double mun) {
        this.mun = mun;
    }
    
    public double getCaseina(){
        return ((double) Math.round(caseina * 100d) / 100d);
        
    }
    

    public void setCaseina(Double caseina) {
        this.caseina = caseina;
    }

    
    
    /*public String getBacteria(){
        if (bacteria==0){
            return "";
        }else{
            return String.valueOf(bacteria);
        }
    }*/
    
    public int getBacteria(){
        return bacteria;
    }

    public void setBacteria(int bacteria) {

        this.bacteria = bacteria;
    }

        
    /*public String getCfu(){
        if (cfu==0){
            return "";
        }else{
            return String.valueOf(cfu);
        }
    }*/
    
    public int getCfu(){
        return cfu;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    public int getInh() {
        return inh;
    }
    
    public String getInh(String w){
        this.w=w;
        String inhibidor="";
        if(this.w.equals("williner")){
            switch(inh){
                case 0:
                    inhibidor = "NO";
                    break;
                    
                case 1:
                    inhibidor = "SI";
                    break;
                default:
                    inhibidor = "";
                    break;
            }
        }
        if(this.w.equals("completo")){
            switch(inh){
                case 0:
                    inhibidor = "Negativo";
                    break;
                    
                case 1:
                    inhibidor = "Positivo";
                    break;
                default:
                    inhibidor = "";
                    break;
            }
        }
        
        return inhibidor;
        
    }

    public void setInh(int inh) {
        this.inh = inh;
    }
    
    public String getUreaTotal(String simbolo){
        ureaTotal = mun * 2.144;
        if(simbolo.equals("punto")){
            
            return formateaPunto.format(ureaTotal);
        }else {
            return formateaComa.format(ureaTotal);
        }
    }
    public double getUreaTotal(){
        ureaTotal = mun * 2.144;
        return ((double) Math.round(ureaTotal * 100d) / 100d);
        
    }

}
