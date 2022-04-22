/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tareas.Test2;
import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import java.util.HashMap;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.ActivityServices.ActivityServiceRequestType;
import ServiceAgentResPwA.MovementServices.MovementServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.TabletServices.TabletServiceRequestType;
import ServiceAgentResPwA.VoiceServices.PepperTopicsNames;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import Utils.ResPwaUtils;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
import rational.mapping.Task;
/**
 *
 * @author juanamorocho
 */
public class TestTask extends Task {
    /**
    * TestTask - Ejecuta un Tópico de prueba.
    */
    private HashMap<String, Object> infoServicio = new HashMap<>();
    public TestTask() {}
    @Override
    public void executeTask(Believes parameters)
    {
        System.out.println("--- Execute Task - Test Task ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        ResPwaUtils.activateTopic( PepperTopicsNames.ALEGRETOPIC, blvs); //Cambiar a Topico de Prueba
        ServiceDataRequest srb = null;
        // Decir oración terminada!
        infoServicio.put("SAY", "Ahem. He terminado mi primera tarea! Guau!");
        srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
        ResPwaUtils.requestService(srb, blvs);
    }
    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task Test Task ---");
        ResPwaUtils.deactivateTopic(PepperTopicsNames.ALEGRETOPIC, believes);
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task Test Task ---");
        ResPwaUtils.deactivateTopic(PepperTopicsNames.ALEGRETOPIC, believes);
    }

    @Override
    public boolean checkFinish(Believes believes) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if (!blvs.getbEstadoInteraccion().isEstaHablando())
        {
            ResPwaUtils.deactivateTopic(PepperTopicsNames.ALEGRETOPIC, believes);
            return true;
        }
        return false;
    }

}
