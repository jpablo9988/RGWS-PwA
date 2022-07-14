/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RobotAgentBDI.Metas;

/**
 *
 * @author juan.amorocho
 */
import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import Init.InitRESPwA;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import Tareas.MostrarEjercicio.*;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
public class MostrarInstruccionesEjercicios extends GoalBDI 
{
private static String descrip = "MostrarEjercicio";
    public static MostrarInstruccionesEjercicios buildGoal() {

        ReproduccionVideoInstruccional rvi = new ReproduccionVideoInstruccional();
        Plan rolePlan= new Plan();
        rolePlan.addTask(rvi);
        RationalRole mostEjerRole = new RationalRole(descrip, rolePlan);
        MostrarInstruccionesEjercicios b= new MostrarInstruccionesEjercicios(InitRESPwA.getPlanID(), mostEjerRole, descrip, GoalBDITypes.REQUIREMENT);
        return b;
    }
    public MostrarInstruccionesEjercicios(long id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
        System.out.println("DEBUG - CREACION META: Mostrar Ejercicios en Pantalla");
    }
 @Override
    public double evaluateViability(Believes believes) throws KernellAgentEventExceptionBESA {
        System.out.println("DEBUG - VIABILIDAD EVAL. META: Mostrar Ejercicios en Pantalla");
        return 1;
    }

    @Override
    public double detectGoal(Believes believes) throws KernellAgentEventExceptionBESA {
        System.out.println("DEBUG - META DETECTADA: Mostrar Ejercicios en Pantalla");

        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        //TODO: tiene el detect goal de salir de suspensión. Cambiar a algo mas apropiado.
        if (blvs.getbEstadoRobot().getBatteryPerc()>30.0 && !blvs.getbEstadoRobot().getBateria() && blvs.getbEstadoInteraccion().isSistemaSuspendido()) {
                return 1.0;
        }
        return 0;
    }

    @Override
    public double evaluatePlausibility(Believes believes) throws KernellAgentEventExceptionBESA {
        System.out.println("DEBUG - PLAUSABILIDAD EVAL.: Mostrar Ejercicios en Pantalla");
        return 1;
    }

    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        System.out.println("DEBUG - CONTRIBUCION EVAL.: Mostrar Ejercicios en Pantalla");
        //TODO: Cambiar a algo mas apropiado para mostrar instrucciones.
        RobotAgentBelieves blvs = (RobotAgentBelieves) stateBDI.getBelieves();
        return blvs.getbEstadoRobot().getBateria() ? 1 : 0;
    }

    @Override
    public boolean predictResultUnlegality(StateBDI agentStatus) throws KernellAgentEventExceptionBESA {
        System.out.println("DEBUG - PREDICT UNLEGALITY: Mostrar Ejercicios en Pantalla");
        return true;
    }

    @Override
    public boolean goalSucceeded(Believes believes) throws KernellAgentEventExceptionBESA {
        System.out.println("DEBUG - GOAL SUCCEDED: Mostrar Ejercicios en Pantalla");
        //TODO: Cambiar a algo mas apropiado para mostrar instrucciones.

        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        return !blvs.getbEstadoRobot().getBateria();
    }

}
