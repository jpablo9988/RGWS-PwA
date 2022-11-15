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
        System.out.println("--- DEBUG: Execute Task - Test Task ---");
        System.out.println("--- DEBUG: -- Se repitio??? ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        infoServicio = new HashMap<>();
        infoServicio.put("TAGSDANCE", "MANCUERNA");
        ServiceDataRequest data = ServiceRequestBuilder.buildRequest(ActivityServiceRequestType.RUNANIMATION, infoServicio);
        ResPwaUtils.requestService(data, blvs);
        // Decir oración terminada!
        infoServicio.put("SAY", "Oh dios mio santisimo y virgen, estoy haciendo un ejercicio. Un. dos. tres. c u a t r o.");
        ServiceDataRequest srb = null;
        srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
        ResPwaUtils.activateTopic( PepperTopicsNames.AYUDATOPIC, blvs); //Cambiar a Topico de Prueba
        infoServicio = new HashMap<>(); //Reset infoServicio...
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
        //System.out.println("--- DEBUG: TestTask Revisado. ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        
        System.out.println("Sigue el Topico Activado:" + (blvs.getbEstadoInteraccion().isTopicoActivo(PepperTopicsNames.AYUDATOPIC)));
        System.out.println("---------------------------------");
        /*
        System.out.println("He recibido respuestadePWA?" + blvs.getbEstadoInteraccion().isRecibirRespuestaPwA());
        System.out.println("---------------------------------");
        System.out.println("Estas son mis respuestas por contexto" +  blvs.getbEstadoInteraccion().getRespuestasPorContexto());
        System.out.println("---------------------------------");
        */
        if (!blvs.getbEstadoInteraccion().isEstaHablando() && ((blvs.getbEstadoInteraccion().isTopicoActivo(PepperTopicsNames.AYUDATOPIC)))
                && blvs.getbEstadoInteraccion().isRecibirRespuestaPwA() && blvs.getbEstadoInteraccion().getRespuestasPorContexto() >= 1)
        {
            ResPwaUtils.deactivateTopic(PepperTopicsNames.AYUDATOPIC, believes);
            blvs.getbEstadoInteraccion().setRespuestasPorContexto(0);
            blvs.getbEstadoInteraccion().setRecibirRespuestaPwA(false);
            System.out.println("--- TESTASK_1_Terminado ---");
            return true;
        }
        return false;
    }

}
