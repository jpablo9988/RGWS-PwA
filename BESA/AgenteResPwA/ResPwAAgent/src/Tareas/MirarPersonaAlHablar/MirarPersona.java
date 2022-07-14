/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tareas.MirarPersonaAlHablar;

import RobotAgentBDI.Believes.RobotAgentBelieves;
import Utils.ResPwaUtils;

import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.ActivityServices.ActivityService;
import ServiceAgentResPwA.ActivityServices.ActivityServiceRequestType;
import ServiceAgentResPwA.AutonomyServices.AutonomyServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.TabletServices.TabletServiceRequestType;
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
                

        System.out.println("--- DEBUG - FINISH TASK?: MirarPersona ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        //TODO: Toca mirar bien cual son los parámetros para definir si está terminado.
            // Por ahora, si no esta moviendose al hablar, estaría terminado, lo cual haria sentido.
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
        //Se activará ACTIVATESPEAKMOVEMENTS. Toca probar con Robot por si es la respuesta incorrecta al TODO.
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
