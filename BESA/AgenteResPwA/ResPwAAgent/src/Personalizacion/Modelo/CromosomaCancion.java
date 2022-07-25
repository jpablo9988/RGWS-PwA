/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personalizacion.Modelo;

import ResPwAEntities.PreferenciaXCancion;

/**
 *
 * @author ASUS
 */
public class CromosomaCancion extends Cromosoma{
    
    private PreferenciaXCancion cancion;

    public CromosomaCancion( PreferenciaXCancion cancion ) {
      this.cancion = cancion;
    }
    
    @Override
    protected void calculateObjectiveValue() {
        objectiveValue = (float) this.cancion.getGusto();
        
        if ( cancion.getReminiscencia().floatValue()==1f){
            objectiveValue += 0.4;
        }
    }

    public PreferenciaXCancion getCancion() {
        return cancion;
    }
    
     
    
}
