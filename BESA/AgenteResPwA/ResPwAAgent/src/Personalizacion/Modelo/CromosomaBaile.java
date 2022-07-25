/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personalizacion.Modelo;

import ResPwAEntities.PreferenciaXBaile;

/**
 *
 * @author juan.amorocho
 */
public class CromosomaBaile extends Cromosoma{
    private PreferenciaXBaile baile;

    public CromosomaBaile(PreferenciaXBaile baile) {
        this.baile = baile;
    }

    public PreferenciaXBaile getBaile() {
        return baile;
    }
    
    @Override
    protected void calculateObjectiveValue() {
        objectiveValue = this.baile.getGusto();
    }
    
}
