/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tareas.DetectarPersonaCercana;

import RobotAgentBDI.Believes.RobotAgentBelieves;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.ActivityServices.ActivityServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import Utils.ResPwaUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rational.mapping.Believes;
import rational.mapping.Task;

/**
 *
 * @author tesispepper
 */
public class InterrumpirActividades extends Task {

    private HashMap<String, Object> infoServicio = new HashMap<>();
    private long start = -1;
    private long secondTimerDebug = -1;
    private int repeticiones = 0;
    private boolean logreTerminar = false;
    List<String> frases;

    public InterrumpirActividades() {
        System.out.println(" (RealizarEjercicio) - Meta construida.");
        frases = new ArrayList<>();
        frases.add("Ah, veo que estas muy cerca a mi! Alejate un poco para que podamos seguir!");
        frases.add("Guau, aprecio tu cercania, pero necesito espacio para poder moverme!");
        frases.add("Yo te quiero tambien, créeme, pero me gusta mi espacio personal. Te puedes alejar un poco, porfavor?");
        logreTerminar = false;
    }

    @Override
    public void executeTask(Believes parameters) {
        // Esta meta se repetirá hasta que se logré ciertas repeticiones del ejercicio.;
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        long now = System.currentTimeMillis();
        
        if ((blvs.getbEstadoInteraccion().getDistanciaPwA() < 0.8f) && (blvs.getbEstadoInteraccion().getDistanciaPwA() >= 0)) {
            logreTerminar = false;
            if ((now - start > 6000 || start == -1) && !blvs.getbEstadoActividad().isEstaMoviendo()) {
                if (repeticiones >= frases.size()) {
                    repeticiones = 0;
                }
                start = now;
                infoServicio = new HashMap<>();
                
                infoServicio.put("SAY", frases.get(repeticiones));
                ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
                infoServicio = new HashMap<>();
                repeticiones++;
                
            }
            setTaskWaitingForExecution();
        }
        else
        {
            System.out.println("Entré a salirme...");
            infoServicio = new HashMap<>();
            infoServicio.put("SAY", "Muchas gracias! Sigamos con nuestra rutina!");
            ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
            ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
            logreTerminar = true;
            
        }
        /*
        // -- DEBUG -- //
        if ((now - start > 6000 || start == -1) && !blvs.getbEstadoActividad().isEstaMoviendo()) {
                if (repeticiones >= frases.size()) {
                    repeticiones = 0;
                }
                start = now;
                infoServicio = new HashMap<>();
                
                infoServicio.put("SAY", frases.get(repeticiones));
                ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
                infoServicio = new HashMap<>();
                repeticiones++;
                
        }
        if (secondTimerDebug == -1)
        {
            secondTimerDebug = now;
        }
        if ((now - secondTimerDebug > 15000 ))
        {
            infoServicio = new HashMap<>();
            infoServicio.put("SAY", "Gracias!");
            ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
            ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
            logreTerminar = true;
        }
        setTaskWaitingForExecution();*/
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println(" (RealizarEjercicio) - Interrupción de Meta.");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println(" (RealizarEjercicio) - Cancelación de Meta.");
    }

    @Override
    public boolean checkFinish(Believes believes) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        
        if (!blvs.getbEstadoInteraccion().getEstoyDetectandoPersonaCerca())
        {
            return true;
        }
        if (logreTerminar) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA - No detecto persona cerca");
            return false;
        } else {
            //System.out.println("detecto persona cerca");
            return false;
        }
        // DEBUG
        //return logreTerminar;
    }

}
