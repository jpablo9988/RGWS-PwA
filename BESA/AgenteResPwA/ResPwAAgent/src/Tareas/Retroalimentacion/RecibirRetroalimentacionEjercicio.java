package Tareas.Retroalimentacion;

import BDInterface.RESPwABDInterface;
import ResPwAEntities.Historial;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import Utils.ResPwaUtils;
import rational.mapping.Believes;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import RobotAgentBDI.Utils.ResPwAActivity;
import ServiceAgentResPwA.HumanServices.HumanServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.TabletServices.TabletServiceRequestType;
import ServiceAgentResPwA.VoiceServices.PepperTopicsNames;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import rational.mapping.Task;

/**
 *
 * @author mafegarces
 */
public class RecibirRetroalimentacionEjercicio extends Task {

    private HashMap<String, Object> infoServicio = new HashMap<>();
    private int num;

    public RecibirRetroalimentacionEjercicio() {

        num = 0;
//        System.out.println("--- Task Recibir Retroalimentacion Iniciada ---");
    }

    @Override
    public void executeTask(Believes parameters) {
        System.out.println("--- Execute Task Recibir Retroalimentacion ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        ServiceDataRequest srb;
        if (blvs.getbEstadoInteraccion().getRetroalimentacionValue() == null) {
            System.out.println("No tengo mis respuestas!...");
            if (blvs.getbEstadoInteraccion().isTopicoActivo(PepperTopicsNames.RETROEJERTOPIC)) {
                if (num == 1) {
                    System.out.println("HOLA 3 " + num + "  " + blvs.getbEstadoInteraccion().getRetroalimentacionValue());
                    infoServicio = new HashMap<>();
                    infoServicio.put("SAY", "Podria hacerte una pregunta?");
                    srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                    ResPwaUtils.requestService(srb, blvs);
                    num++;
                }
                num++;
            }
            setTaskWaitingForExecution();

        } else {
            System.out.println("Tengo mis respuestas!...");
            String retroalimentacion = blvs.getbEstadoInteraccion().getRetroalimentacionValue(); //TODO - Cambiar Python
            List<String> resulset = Arrays.asList(retroalimentacion.split(" "));
            if (resulset != null) {

                for (int i = 0; i < blvs.getbEstadoInteraccion().getCurrHistorialList().size(); i++) {
                    Historial auxHist = blvs.getbEstadoInteraccion().getCurrHistorialList().get(i);

                    auxHist.setRetroalimentacionGusto(CheckRetroValue(resulset.get(0)));
                    if (resulset.size() > 1) {
                        auxHist.setRetroalimentacionCansancio(CheckRetroValue(resulset.get(1)));
                    }
                    blvs.getbEstadoInteraccion().updateHistorialList(auxHist, i);
                    RESPwABDInterface.updateHistorial(auxHist);
                }

            } else {
                setTaskWaitingForExecution();
            }
        }

    }

    private int CheckRetroValue(String frase) {
        switch (frase.toLowerCase()) {
            case "uno":
                return 1;
            case "dos":
                return 2;
            case "tres":
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println("--- Interrupt Task Recibir Retroalimentacion ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("--- Cancel Task Recibir Retroalimentacion ---");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
    }

    @Override
    public boolean checkFinish(Believes believes) {
        System.out.println("Chequee finish - RecibirRetroEjercicio");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        System.out.println("¿Está mi tópico de retro activo? " + blvs.getbEstadoInteraccion().isTopicoActivo(PepperTopicsNames.RETROEJERTOPIC));
        if (!blvs.getbEstadoInteraccion().isTopicoActivo(PepperTopicsNames.RETROEJERTOPIC) && blvs.getbEstadoInteraccion().getRetroalimentacionValue() != null) {
//            ResPwaUtils.activateTopic(PepperTopicsNames.BLANKTOPIC, believes);
            blvs.getbEstadoInteraccion().setRetroalimentacionValue(null);
            System.out.println("Terminé mi retroalimentación...");
            num = 0;
            return true;
        }
        return false;
    }

}
