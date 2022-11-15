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
import ServiceAgentResPwA.VoiceServices.PepperTopicsNames;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author juan.amorocho
 */
public class PreguntarPreparacion extends Task {

    private HashMap<String, Object> infoServicio = new HashMap<>();
    private final String PREGUNTA;
    private final int NOPREGUNTAS = 3;
    boolean estoyEsperandoNegacion = false;
    double waitingTimer = 15000;
    //double waitingTimer = 3000;
    private long start = -1;

    //Constructor: Recibe pregunta que irá a responder. 
    public PreguntarPreparacion(String pregunta) {
        System.out.println(" (PreguntarPreparacion) - Meta construida.");
        this.PREGUNTA = pregunta;
    }

    @Override
    public void executeTask(Believes parameters) {
        //System.out.println(" (PreguntarPreparacion) - Meta ejecutada.");
        ServiceDataRequest srb;
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        // -- Llamar Tópico de preparación. --

        // -- Terminar la ejecución de la meta -- //
        if (blvs.getbEstadoInteraccion().getPreparacionNegada()) {
            long now = System.currentTimeMillis();
            System.out.println("Estoy esperando");
            if (!estoyEsperandoNegacion) {
                ResPwaUtils.deactivateTopic(PepperTopicsNames.PREPARACION, parameters);
                start = now;
                estoyEsperandoNegacion = true;
            }
            if ((now - start > waitingTimer) || blvs.getbEstadoInteraccion().getEstoyListoEmpezarPreparacion()) {
                estoyEsperandoNegacion = false;
                blvs.getbEstadoInteraccion().setEstoyListoEmpezarPreparacion(false);
                blvs.getbEstadoInteraccion().resetPreparacionNegada();
                System.out.println("Salí!");
                blvs.getbEstadoInteraccion().setRespuestasPorContexto(0);
                setTaskWaitingForExecution();
            }
        } else {
            ResPwaUtils.activateTopic(PepperTopicsNames.PREPARACION, parameters);
            // -- Realiza pregunta inicial para lograr una respuesta del PwA en el tópico. --
            infoServicio = new HashMap<>();
            infoServicio.put("SAY", PREGUNTA);
            srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
            ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
        }
    }

    @Override
    public void interruptTask(Believes believes) {
        ResPwaUtils.deactivateTopic(PepperTopicsNames.PREPARACION, believes);
        System.out.println(" (PreguntarPreparacion) - Interrupción de Meta.");
        ResPwaUtils.deactivateTopic(PepperTopicsNames.PREPARACION, believes);
    }

    @Override
    public void cancelTask(Believes believes) {
        ResPwaUtils.deactivateTopic(PepperTopicsNames.PREPARACION, believes);
        System.out.println(" (PreguntarPreparacion) - Cancelación de Meta.");
        ResPwaUtils.deactivateTopic(PepperTopicsNames.PREPARACION, believes);
    }

    @Override
    public boolean checkFinish(Believes believes) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if (blvs.getbEstadoInteraccion().getPreparacionNegada()) {
            setTaskWaitingForExecution();
            return false;
        }
        if (blvs.getbEstadoInteraccion().isTopicoActivo(PepperTopicsNames.PREPARACION)) {
            /*System.out.println("¿Se ha negado la preparación?");
            System.out.println(blvs.getbEstadoInteraccion().getPreparacionNegada());
            System.out.println("Mis respuestas por contexto son:");
            System.out.println(blvs.getbEstadoInteraccion().getRespuestasPorContexto());*/
            if ((blvs.getbEstadoInteraccion().getRespuestasPorContexto() >= NOPREGUNTAS)
                    && !blvs.getbEstadoInteraccion().getPreparacionNegada()) {
                ResPwaUtils.deactivateTopic(PepperTopicsNames.PREPARACION, believes);
                blvs.getbEstadoInteraccion().setRespuestasPorContexto(0);
                //blvs.getbEstadoInteraccion().setPreparadoEjercicio(true);
                
                return true;
            }
        }
        return false;
    }
}
