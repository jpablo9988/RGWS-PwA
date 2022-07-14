/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tareas.PrepararEjercicio;

import rational.mapping.Believes;
import Utils.ResPwaUtils;
import java.util.HashMap;
import rational.mapping.Task;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;

/**
 *
 * @author juan.amorocho
 */
public class PreguntarPreparacion extends Task 
{
    private HashMap<String, Object> infoServicio = new HashMap<>();
    private String pregunta;
    //Constructor: Recibe pregunta que irá a responder. 
    public PreguntarPreparacion(String pregunta) {
         System.out.println("--- DEBUG: Task Preguntar Preparacion Iniciada ---");
         this.pregunta = pregunta;
    }
    //TODO: Template. Implementar clase.
     @Override
    public void executeTask(Believes parameters) {
        infoServicio.put("SAY", pregunta);
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
        ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task Enviar Mensaje ---");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task Enviar Mensaje ---");
    }

    @Override
    public boolean checkFinish(Believes believes) {
       RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
       String respuestaEncontrada[];
       ServiceDataRequest srb = null;
       /*
            PuedoContinuar tiene tres estados:
                0 - No escuchó un si o un no. No termina la meta.
                1 - Escuchó un no. No termina la meta.
                2 - Escuchó un si. Termina la meta.
        */
       int puedoContinuar = 0;
       if(blvs.getbEstadoInteraccion().isRecibirRespuestaPwA())
       {
            blvs.getbEstadoInteraccion().setRecibirRespuestaPwA(false);
            respuestaEncontrada = blvs.getbEstadoInteraccion().getRespuestaResultSet();
            for (int i = 0; i < respuestaEncontrada.length; i++)
            {
                System.out.println(respuestaEncontrada[i]);
                if (respuestaEncontrada[i] == "si") puedoContinuar = 2;
                if (respuestaEncontrada[i] == "no") puedoContinuar = 1;
            }

            switch(puedoContinuar)
            {
                case 1:
                    infoServicio.put("SAY", "OK, no te preocupes! Yo esperaré.");
                    srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                    ResPwaUtils.requestService(srb, (RobotAgentBelieves) believes);
                    return false;
                case 2:
                    infoServicio.put("SAY", "Super!");
                    srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                    ResPwaUtils.requestService(srb, (RobotAgentBelieves) believes);
                    return true;
                default:
                    infoServicio.put("SAY", "Ash, no te oí bien! Me podrias responder con un si o no?");
                    srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                    ResPwaUtils.requestService(srb, (RobotAgentBelieves) believes);
                    return false;                    
            }


       }
       return false;
    }
}
