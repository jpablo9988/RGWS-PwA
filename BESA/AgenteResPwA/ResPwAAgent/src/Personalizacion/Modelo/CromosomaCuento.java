/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personalizacion.Modelo;

import ResPwAEntities.PreferenciaXCuento;

/**
 *
 * @author ASUS
 */
public class CromosomaCuento extends Cromosoma{

    private PreferenciaXCuento cuento;

    public CromosomaCuento(PreferenciaXCuento cuento) {
        this.cuento = cuento;
    }
    
    @Override
    protected void calculateObjectiveValue() {
        objectiveValue =  this.cuento.getGusto();
    }

    public PreferenciaXCuento getCuento() {
        return cuento;
    }
    
}
