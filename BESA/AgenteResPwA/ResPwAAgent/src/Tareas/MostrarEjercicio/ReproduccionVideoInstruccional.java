/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tareas.MostrarEjercicio;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import rational.mapping.Believes;
import Utils.ResPwaUtils;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.ActivityServices.ActivityServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.TabletServices.TabletServiceRequestType;
import ServiceAgentResPwA.VoiceServices.PepperTopicsNames;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import java.util.HashMap;
import java.util.List;
import rational.mapping.Task;
/**
 *
 * @author juan.amorocho
 */
public class ReproduccionVideoInstruccional extends Task {
    private HashMap<String, Object> infoServicio = new HashMap<>();
    private boolean b_Reproduccion; //Determina si el video se está reproduciendo. 
    public ReproduccionVideoInstruccional() {
        System.out.println("--- DEBUG: Iniciado - Reproducción video instruccional. ---");
        b_Reproduccion = false;
    }
    @Override
    public void executeTask(Believes parameters) 
    {
       System.out.println("--- DEBUG: Ejecutando - Reproducción video instruccional. ---");
       RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
       if (!b_Reproduccion)
        {
            //String urlVideo = blvs.getbEstadoActividad().getCancionActual().getCancion().getUrl();
            //TODO: Get URL from Ejercicio.
            String urlVideo = "https://www.youtube.com/watch?v=tl8Esq9Oxpg&list=RDBYVOnEwbTMk&index=2";
            //Link anterior = Placeholder.
            infoServicio.put("SHOWVIDEO",urlVideo);
            ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(TabletServiceRequestType.SHOWVIDEO, infoServicio);
            ResPwaUtils.requestService(srb, blvs);
            infoServicio.clear();
            b_Reproduccion = true;
        }
    }
    @Override
    public void interruptTask(Believes believes) 
    {
        //infoServicio.put("PAUSEVIDEO","BLANK");
        //TODO: Para pausar video, ¿tocará enviar el URL?
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(TabletServiceRequestType.PAUSEVIDEO, null);
        b_Reproduccion = false;
    }
    @Override
    public void cancelTask(Believes believes) {
        //TODO: Para salir del video, ¿tocará enviar el URL?
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(TabletServiceRequestType.QUITVIDEO, null);
        b_Reproduccion = false;
    }
    @Override
    public boolean checkFinish(Believes believes) {
        //TODO: Revisar si las repeticiones de ejercicios se ha terminado.
        return true;
   }
}
    