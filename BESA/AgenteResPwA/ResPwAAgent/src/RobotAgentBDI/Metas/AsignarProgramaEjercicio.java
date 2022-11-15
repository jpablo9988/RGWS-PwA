package RobotAgentBDI.Metas;

import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import Init.InitRESPwA;
import ResPwAEntities.PerfilPwa;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import Tareas.AsignarProgramaEjercicio.CrearProgramaEjercicio;
import Tareas.ProgramarRutina.ProgramarEjercicios;
import java.util.ArrayList;
import java.util.List;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
import rational.mapping.Task;

/**
 *
 * @author juan.amorocho
 */
public class AsignarProgramaEjercicio extends GoalBDI
{
    private final static String DESCRIPTION = "RutinaEjercicio";
    public AsignarProgramaEjercicio(int id, RationalRole role, String description, GoalBDITypes type) 
    {
        super(id, role, description, type);
    }
    public static AsignarProgramaEjercicio buildGoal() 
    {

        CrearProgramaEjercicio progRutina = new CrearProgramaEjercicio();
        ProgramarEjercicios progEjercicio = new ProgramarEjercicios();
        Plan rolePlan= new Plan();
        List<Task> taskList;
        
        rolePlan.addTask(progRutina);
        
        taskList = new ArrayList<>();
        taskList.add(progRutina);
        rolePlan.addTask(progEjercicio, taskList);
        
        RationalRole progRutinaRole = new RationalRole(DESCRIPTION, rolePlan);
        AsignarProgramaEjercicio b = new AsignarProgramaEjercicio(InitRESPwA.getPlanID(), progRutinaRole, 
                DESCRIPTION, GoalBDITypes.DUTY);
        return b;
    }
    @Override
    public double evaluateViability(Believes believes) throws KernellAgentEventExceptionBESA 
    {
        return 1;
    }
    @Override
    public double evaluatePlausibility(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta MantenerAtencionPwA evaluatePlausibility");
        return 1;
    }
    @Override
    public double detectGoal(Believes believes) throws KernellAgentEventExceptionBESA 
    {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        PerfilPwa miPerfil = blvs.getbPerfilPwA().getPerfil();
        if (miPerfil.getPerfilEjercicio() != null) return 0;
        return 1;
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
    public boolean goalSucceeded(Believes believes) throws KernellAgentEventExceptionBESA 
    {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        PerfilPwa miPerfil = blvs.getbPerfilPwA().getPerfil();
        return miPerfil.getPerfilEjercicio() != null || blvs.getbEstadoInteraccion().getCancelarProgramacionEjercicio();
    } 
}
