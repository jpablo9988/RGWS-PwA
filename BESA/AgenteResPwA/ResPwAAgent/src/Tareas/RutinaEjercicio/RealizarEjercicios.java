package Tareas.RutinaEjercicio;

import BDInterface.RESPwABDInterface;
import ResPwAEntities.Ejercicio;
import ResPwAEntities.FraseInspiracional;
import ResPwAEntities.Historial;
import ResPwAEntities.PerfilEjercicio;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.ActivityServices.ActivityServiceRequestType;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.TabletServices.TabletServiceRequestType;
import ServiceAgentResPwA.VoiceServices.PepperTopicsNames;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import Utils.ResPwaUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import rational.mapping.Believes;
import rational.mapping.Task;

/**
 *
 * @author juan.amorocho
 */
public class RealizarEjercicios extends Task {

    private HashMap<String, Object> infoServicio = new HashMap<>();
    private long start = -1, startCardio = -1;
    private int currRepeticiones = 0;
    private int series = -1;
    private int currSeries = -1;
    List<Ejercicio> misEjercicios = new ArrayList<>();
    List<FraseInspiracional> currFrases = new ArrayList<>();
    Ejercicio currEjercicio = null;
    boolean firstTime = true;
    boolean dijoFrase = false;
    boolean isCardio = false;
    int noRepeticiones = 0;
    int randomIndex = 0;
    int duracionCardio = 100;
    boolean puedoProseguir = false;

    // --- variables locales para espera -- //
    private boolean estoyEsperando = false;
    private int millisecsParaEspera = 0;

    public RealizarEjercicios() {
        // System.out.println(" (RealizarEjercicio) - Task construida.");
    }

    @Override
    public void executeTask(Believes parameters) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        long now = System.currentTimeMillis();

        if (currSeries == -1) {
            System.out.println("EXERCISE - DEBUG: ...........................Entre a setup......................");
            PerfilEjercicio miPerfil = RESPwABDInterface.getExcerciseProfile(blvs.getbPerfilPwA().getPerfil().getCedula());
            // Revisar bien esta variable... (se reempleza?)
            if (firstTime) {
                misEjercicios = miPerfil.getEjercicioList();
                firstTime = false;
            }
            blvs.getbEstadoInteraccion().setCurrEjercicios(misEjercicios);
            currRepeticiones = 0;
            isCardio = false;
            currEjercicio = misEjercicios.get(0);

            for (int i = 0; i < currEjercicio.getCategoriaEntrenamientoList().size(); i++) {
                if (currEjercicio.getCategoriaEntrenamientoList().get(i).getTipo().contains(miPerfil.getNombrePrograma().getNombre())) {
                    if (!currEjercicio.getCategoriaEntrenamientoList().get(i).getIntensidadList().isEmpty()) {
                        noRepeticiones = currEjercicio.getCategoriaEntrenamientoList().get(i).getIntensidadList().get(miPerfil.getIndexIntensidadActual()).getRepeticiones();
                        series = currEjercicio.getCategoriaEntrenamientoList().get(i).getIntensidadList().get(miPerfil.getIndexIntensidadActual()).getSeries();
                        currSeries = 0;
                        if (currEjercicio.getCategoriaEntrenamientoList().get(i).getIntensidadList().get(miPerfil.getIndexIntensidadActual()).getDuracionEjercicio() != null) {
                            duracionCardio = currEjercicio.getCategoriaEntrenamientoList().get(i).getIntensidadList().get(miPerfil.getIndexIntensidadActual()).getDuracionEjercicio() * 1000;
                        }
                        if (currEjercicio.getCategoriaEntrenamientoList().get(i).getIntensidadList().get(miPerfil.getIndexIntensidadActual()).getPeso() != null
                                && currEjercicio.getNecesitaPeso()) {
                            infoServicio = new HashMap<>();
                            infoServicio.put("SAY", "Para este ejercicio, trabajaremos con un peso de "
                                    + currEjercicio.getCategoriaEntrenamientoList().get(i).getIntensidadList().get(miPerfil.getIndexIntensidadActual()).getPeso()
                                    + "libras. Si no las tienes contigo, tu cuidador te las acercará!");
                            ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                            ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
                            // Wait of 10_ seconds
                            estoyEsperando = true;
                            millisecsParaEspera += 10000;

                        }
                        if (currEjercicio.getCategoriaEntrenamientoList().get(i).getTipo().contains("Cardiovascular")) {
                            isCardio = true;
                        }
                    }
                }
            }
            AddEmotionalFrases(blvs);
            randomIndex = ThreadLocalRandom.current().nextInt(0, currFrases.size());
            puedoProseguir = true;

        }
        if (estoyEsperando) {
            System.out.println("EXERCISE - DEBUG: ...........................Estoy esperando.....................");
            if (start == -1) {
                start = now;
            }
            if ((now - start > millisecsParaEspera) && !blvs.getbEstadoActividad().isEstaMoviendo()) {
                estoyEsperando = false;
                millisecsParaEspera = 0;
                infoServicio = new HashMap<>();
                infoServicio.put("SAY", "Listo, continuemos!");
                ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
                start = -1;
            }

        }
        if (!estoyEsperando) {
            double duracion = currEjercicio.getDuracion() * 1000;
            double roundedTime = (now - start) / 1000;
            if (((Math.round(roundedTime) == currFrases.get(randomIndex).getTiempoEjecucion())
                    || (currFrases.get(randomIndex).getTiempoEjecucion() == 0))
                    && !dijoFrase) {
                System.out.println("EXERCISE - DEBUG: ...........................Entre a decir mi frase......................");
                infoServicio = new HashMap<>();
                infoServicio.put("SAY", currFrases.get(randomIndex).getContenidos());
                ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                ResPwaUtils.requestService(srb, blvs);
                //AddEmotionalFrases(blvs);
                randomIndex = ThreadLocalRandom.current().nextInt(0, currFrases.size());
                dijoFrase = true;

            }
            if ((now - start > duracion || start == -1) && !blvs.getbEstadoActividad().isEstaMoviendo()) {
                ServiceDataRequest srb;
                if (currRepeticiones == noRepeticiones) {
                    currRepeticiones = 0;
                    currSeries++;
                    estoyEsperando = true;
                    infoServicio = new HashMap<>();
                    if (currSeries == series) {

                        misEjercicios.remove(0);
                        System.out.println("Saqué ejercicio");
                        millisecsParaEspera += 60000;
                        if (misEjercicios.size() == 1) {
                            infoServicio.put("SAY", "Ya casí! Nos falta un ejercicio. Descansemos un minuto y le damos con toda!");
                        } else if (misEjercicios.isEmpty()) {
                            infoServicio.put("SAY", "Hemos terminado nuestra rutina para hoy! Que buen trabajo!");
                        } else {
                            infoServicio.put("SAY", "Estoy orgulloso de ti! Descansemos un minuto, y seguimos al siguiente ejercicio.");
                        }
                        GuardarHistorial(noRepeticiones, series, currEjercicio, blvs.getbPerfilPwA().getPerfil().getPerfilEjercicio(), blvs);
                        currSeries = -1;

                    } else {
                        infoServicio.put("SAY", "Guau! Descansemos 30 segundos y seguimos a la siguiente serie. Lo estas haciendo super!");
                        millisecsParaEspera += 30000;
                    }
                    srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                    ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
                    dijoFrase = false;

                } else {

                    start = now;
                    // -- Run Animation -- //
                    infoServicio = new HashMap<>();
                    infoServicio.put("EXERCISE", currEjercicio.getNombre());
                    srb = ServiceRequestBuilder.buildRequest(ActivityServiceRequestType.RUNANIMATION, infoServicio);
                    ResPwaUtils.requestService(srb, blvs);
                    if (isCardio) {
                        if (startCardio == -1) {
                            startCardio = now;
                        }
                        if ((now - startCardio > duracionCardio)) {
                            startCardio = now;
                            currRepeticiones++;
                        }

                    } else {
                        currRepeticiones++;
                    }
                    if (currEjercicio.getUrlVideo() != null) {
                        infoServicio = new HashMap<>();
                        System.out.println(currEjercicio.getUrlVideo());
                        infoServicio.put("SHOWIMG", currEjercicio.getUrlVideo());
                        srb = ServiceRequestBuilder.buildRequest(TabletServiceRequestType.SHOWIMG, infoServicio);
                        ResPwaUtils.requestService(srb, blvs);
                    }
                    if (!(currRepeticiones == noRepeticiones)) {
                        dijoFrase = false;
                    }

                }
            }
        }

        // -- DEBUG -- Ejecuta movimientos sin necesidad de la BD. //
        //System.out.println("Tiempo actual - start: " + (now - start));
        /*
        List<String> frases = new ArrayList<>();
        frases.add("Hoo, tu no puedes!");
        frases.add("Mueve esas mancuernas!");
        frases.add("Yahoo Wahoo!");
        frases.add("Me estoy cansando!!");
        if ((now - start > 10000 || start == -1) && !blvs.getbEstadoActividad().isEstaMoviendo()) 
        {
            start = now;
            infoServicio = new HashMap<>();
            infoServicio.put("TAGSDANCE", "MANCUERNA");
            ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(ActivityServiceRequestType.RUNANIMATION, infoServicio);
            ResPwaUtils.requestService(srb, blvs);
            infoServicio = new HashMap<>();
            infoServicio.put("SAY", frases.get(currRepeticiones % 4));
            currRepeticiones++;
            srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
            ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
            infoServicio = new HashMap<>();
            
            infoServicio = new HashMap<>();
            infoServicio.put("SHOWIMG", "http://10.195.40.154:49152/content/media/object_id/8/res_id/0");
            srb = ServiceRequestBuilder.buildRequest(TabletServiceRequestType.SHOWIMG, infoServicio);
            ResPwaUtils.requestService(srb, blvs);

        }
         */
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println(" (RealizarEjercicio) - Interrupción de Meta.");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        infoServicio = new HashMap<>();
        infoServicio.put("EXERCISE", currEjercicio.getNombre());
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(ActivityServiceRequestType.STOPANIMATION, infoServicio);
        ResPwaUtils.requestService(srb, blvs);
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println(" (RealizarEjercicio) - Cancelación de Meta.");
    }

    @Override
    public boolean checkFinish(Believes believes) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;

        if (misEjercicios.isEmpty() && (!blvs.getbEstadoInteraccion().isEstaMoviendo()) && puedoProseguir) {
            ResPwaUtils.activateTopic(PepperTopicsNames.RETROEJERTOPIC, believes);
            return true;
        } else {
            setTaskWaitingForExecution();
            return false;
        }

        //--EBUG --Termina hasta un valor de repeticiones determinadas.
        /*
        if ((currRepeticiones >= 3) && (!blvs.getbEstadoInteraccion().isEstaMoviendo())) {
            currRepeticiones = 0;
            //ResPwaUtils.activateTopic(PepperTopicsNames.RETROEJERTOPIC, believes);
            System.out.println("Acabe mi rutina. Phew!");
            return true;

        } else {
            setTaskWaitingForExecution();
            return false;
        }*/
    }

    private void GuardarHistorial(int repeticiones, int series, Ejercicio currEjercicio, PerfilEjercicio miPerfil, RobotAgentBelieves blvs) {
        Historial nHistorial = new Historial(currEjercicio.getNombre(), repeticiones * series);
        nHistorial.setPwaCedula(miPerfil);
        nHistorial.setNotas("-- Cambiar este campo para agregar notas personalizadas. --");
        blvs.getbEstadoInteraccion().addToHistorialList(nHistorial);
        RESPwABDInterface.createHistory(nHistorial);
    }

    private void AddEmotionalFrases(RobotAgentBelieves blvs) {
        String emotionalTalk = blvs.getbEstadoInteraccion().getEstadoEmocional();
        String resulset[] = emotionalTalk.split(" ");
        currFrases.clear();
        if (resulset != null) {
            for (int i = 0; i < currEjercicio.getFraseInspiracionalList().size(); i++) {
                if (currEjercicio.getFraseInspiracionalList().get(i).getPwaEmocion().equals(resulset[0])) {
                    currFrases.add(currEjercicio.getFraseInspiracionalList().get(i));
                }
            }
        } else {
            currFrases = currEjercicio.getFraseInspiracionalList();
        }
        if (currFrases.isEmpty()) {
            currFrases = currEjercicio.getFraseInspiracionalList();
        }
    }
}
