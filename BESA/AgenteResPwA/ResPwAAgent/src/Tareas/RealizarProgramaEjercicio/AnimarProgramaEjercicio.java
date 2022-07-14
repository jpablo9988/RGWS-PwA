/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tareas.RealizarProgramaEjercicio;

import EmotionalAnalyzerAgent.Utils.EmotionPwA;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import Utils.ResPwaUtils;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.ActivityServices.ActivityServiceRequestType;
import ServiceAgentResPwA.HumanServices.HumanServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import rational.mapping.Believes;
import rational.mapping.Task;
/**
 *
 * @author juan.amorocho
 */
public class AnimarProgramaEjercicio extends Task{

private HashMap<String,Object> infoServicio = new HashMap<>();
    

    public AnimarProgramaEjercicio() {
        System.out.println("--- DEBUG: Animar Programa Ejercicio INICIADA.---");
    }
    
    @Override
    public void executeTask(Believes parameters) {
        System.out.println("--- DEBUG: Ejecuta TASK ProgramaEjercicio. ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        for (int i = 0; i < 4; i++)
        {
             infoServicio = new HashMap<>();
             infoServicio.put("TAGSDANCE", "RUTINA");
             ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(ActivityServiceRequestType.RUNANIMATION, infoServicio);
             ResPwaUtils.requestService(srb, blvs);
        }
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task Seleccionar Estrategia Animar PwA ---");

    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task Seleccionar Estrategia Animar PwA ---");

    }

    @Override
    public boolean checkFinish(Believes believes) {
        
        return true;
    }
}
