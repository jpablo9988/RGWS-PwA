/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tareas.Test2;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import rational.mapping.Believes;
import rational.RationalRole;
import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import ServiceAgentResPwA.ActivityServices.ActivityServiceRequestType;
import ServiceAgentResPwA.MovementServices.MovementServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.TabletServices.TabletServiceRequestType;
import ServiceAgentResPwA.VoiceServices.PepperTopicsNames;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import rational.mapping.Task;
import java.util.HashMap;
import Utils.ResPwaUtils;

/**
 *
 * @author juan.amorocho
 */
public class TestTask2 extends Task 
{
    private static String description = "testTask2";
    private HashMap<String,Object> infoServicio = new HashMap<>();
    public TestTask2()
    {
    }
    @Override
    public void executeTask(Believes parameters)
    {
        System.out.println("--- Prueba Animacion ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        infoServicio.put("TAGSDANCE", "SAD");
        ServiceDataRequest data = ServiceRequestBuilder.buildRequest(ActivityServiceRequestType.RUNANIMATION, infoServicio);
        ResPwaUtils.requestService(data, blvs);
    }
    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task Revisar Perfil ---");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task Revisar Perfil ---");
    }

    @Override
    public boolean checkFinish(Believes believes) {

        return false;
    }

}
