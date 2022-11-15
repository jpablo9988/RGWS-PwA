package RobotAgentBDI.Metas;

import BDInterface.RESPwABDInterface;
import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import Init.InitRESPwA;
import ResPwAEntities.PerfilEjercicio;
import ResPwAEntities.PerfilPwa;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import ServiceAgentResPwA.VoiceServices.PepperTopicsNames;
import Tareas.DetectarPersonaCercana.InterrumpirActividades;
import Tareas.LogIn.ConversacionInicial;
import Tareas.LogIn.IniciarServicios;
import Tareas.PrepararEjercicio.PreguntarPreparacion;
import Tareas.ProgramarRutina.ProgramarEjercicios;
import Tareas.Retroalimentacion.RecibirRetroalimentacionEjercicio;
import Tareas.RutinaEjercicio.ExportarHistorial;
import Tareas.RutinaEjercicio.RealizarEjercicios;
import Utils.ResPwaUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
import rational.mapping.Task;

/**
 *
 * @author juan.amorocho
 */
public class RutinaEjercicio extends GoalBDI {

    private final static String DESCRIPTION = "RutinaEjercicio";

    public RutinaEjercicio(int id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
    }

    public static RutinaEjercicio buildGoal() {

        //DEBUG - Activando Servicios... si no está activada la meta de LogIn
        
        IniciarServicios iniciarServicios = new IniciarServicios();
        RealizarEjercicios tareaEjercicios = new RealizarEjercicios();
        ProgramarEjercicios progEjercicio = new ProgramarEjercicios();
        PreguntarPreparacion preparacionEjercicio = new PreguntarPreparacion("Según mi reloj, nos cae la hora de empezar nuestra rutina de ejercicio! "
                + ". Ahora, espera un momento, que necesito asegurarme que estemos listo para empezar nuestra jornada.");
        RecibirRetroalimentacionEjercicio retroEjercicio = new RecibirRetroalimentacionEjercicio();
        ExportarHistorial expHistorial = new ExportarHistorial();
        
        List<Task> taskList;

        Plan rolePlan = new Plan();
        rolePlan.addTask(iniciarServicios);

        /*taskList = new ArrayList<>();
        taskList.add(iniciarServicios);
        rolePlan.addTask(preparacionEjercicio, taskList);*/

        taskList = new ArrayList<>();
        taskList.add(iniciarServicios);
        rolePlan.addTask(tareaEjercicios, taskList);
        
        taskList = new ArrayList<>();
        taskList.add(tareaEjercicios);
        rolePlan.addTask(retroEjercicio, taskList);
        
        taskList = new ArrayList<>();
        taskList.add(retroEjercicio);
        rolePlan.addTask(expHistorial, taskList);
        

        RationalRole reiActRole = new RationalRole(DESCRIPTION, rolePlan);
        RutinaEjercicio b = new RutinaEjercicio(InitRESPwA.getPlanID(), reiActRole, DESCRIPTION, GoalBDITypes.OPORTUNITY);
        return b;
        
        
        //DEBUG: Comentar código anterior y usar este en caso de querer probar solo la rútina de ejercicios.
        /*
        RealizarEjercicios tareaEjercicios = new RealizarEjercicios();
        List<String> resources = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        Plan rolePlan= new Plan();
        
        rolePlan.addTask(tareaEjercicios);
        
        RationalRole rutEjercicioRole = new RationalRole(DESCRIPTION, rolePlan);
        RutinaEjercicio b = new RutinaEjercicio(InitRESPwA.getPlanID(), rutEjercicioRole, 
                DESCRIPTION, GoalBDITypes.OPORTUNITY);
        return b;
        
        // --- Debug no. 2. Revise si está funcionando meta interrumpir actividades sin necesitad del sensor.
        /*
        RealizarEjercicios tareaEjercicios = new RealizarEjercicios();
        RealizarEjercicios tareaEjercicios2 = new RealizarEjercicios();
        InterrumpirActividades interruptAct = new InterrumpirActividades();
        List<Task> taskList;

        Plan rolePlan = new Plan();
        rolePlan.addTask(tareaEjercicios);

        taskList = new ArrayList<>();
        taskList.add(tareaEjercicios);
        rolePlan.addTask(interruptAct, taskList);
        
        taskList = new ArrayList<>();
        taskList.add(interruptAct);
        rolePlan.addTask(tareaEjercicios2, taskList);
        
        RationalRole reiActRole = new RationalRole(DESCRIPTION, rolePlan);
        RutinaEjercicio b = new RutinaEjercicio(InitRESPwA.getPlanID(), reiActRole, DESCRIPTION, GoalBDITypes.OPORTUNITY);
        return b;
        */

    }

    @Override
    public double evaluateViability(Believes believes) throws KernellAgentEventExceptionBESA {
        return 1;
    }

    @Override
    public double evaluatePlausibility(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta MantenerAtencionPwA evaluatePlausibility");
        return 1;
    }

    @Override
    public double detectGoal(Believes believes) throws KernellAgentEventExceptionBESA {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        PerfilPwa miPerfil = blvs.getbPerfilPwA().getPerfil();
        PerfilEjercicio miPerfilEjercicio = RESPwABDInterface.getExcerciseProfile(miPerfil.getCedula());
        Date currDate = new Date();
        Calendar currDiaCalendar = GregorianCalendar.getInstance();
        currDiaCalendar.setTime(currDate);
        int currHour = currDiaCalendar.get(Calendar.HOUR_OF_DAY);
        if (miPerfilEjercicio != null && !blvs.getbEstadoInteraccion().isSistemaSuspendido()) {
            if ((blvs.getbEstadoInteraccion().getDistanciaPwA() <= 0.8f) && (blvs.getbEstadoInteraccion().getDistanciaPwA() >= 0)) {
                return 0;
            } else if (!miPerfilEjercicio.getEjercicioList().isEmpty()) {
                if ((miPerfilEjercicio.getFechaProx().equals(currDate) || miPerfilEjercicio.getFechaProx().before(currDate))
                        && miPerfilEjercicio.getHoraProx() <= currHour) {
                    return 1;
                }
                return 1; //Debug, para no probar con fechas.
            }
        }
        return 0;
    }

    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        return 1;
    }

    @Override
    public boolean predictResultUnlegality(StateBDI agentStatus) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta MantenerAtencionPwA predictResultUnlegality");
        return true;
    }

    @Override
    public boolean goalSucceeded(Believes believes) throws KernellAgentEventExceptionBESA {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        return false;
    }

}
