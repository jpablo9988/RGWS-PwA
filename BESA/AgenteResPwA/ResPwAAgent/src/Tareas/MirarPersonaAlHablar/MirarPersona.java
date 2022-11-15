/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tareas.MirarPersonaAlHablar;

import RobotAgentBDI.Believes.RobotAgentBelieves;
import Utils.ResPwaUtils;

import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.AutonomyServices.AutonomyServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import java.util.HashMap;
import rational.mapping.Believes;
import rational.mapping.Task;
/**
 *
 * @author juan.amorocho
 */
public class MirarPersona extends Task {
    private HashMap<String, Object> infoServicio = new HashMap<>();
    private static final String ACTIVATE_MOVEMENT_WHILE_TALKIING = "ACTIVATESPEAKMOVEMENTS";
    @Override
    public boolean checkFinish(Believes believes) {
        System.out.println("--- DEBUG - FINISH TASK: MirarPersona ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if(!blvs.getbEstadoRobot().isActivadoMovHabla())
        {
            return true;
        }
        return false;
    }
    @Override
    public void executeTask(Believes parameters) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        //TODO: ¿Qué tipo de servicio es aquel que mira a la persona mientras habla? 
        infoServicio.put(ACTIVATE_MOVEMENT_WHILE_TALKIING, true); 
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(AutonomyServiceRequestType.ACTIVATESPEAKMOVEMENTS , infoServicio);
        ResPwaUtils.requestService(srb, blvs);
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task ActivarSenialesVida ---");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task ActivarSenialesVida ---");
    }
}

