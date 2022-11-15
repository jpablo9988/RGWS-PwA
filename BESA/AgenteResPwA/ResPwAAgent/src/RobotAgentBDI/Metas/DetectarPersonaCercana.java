/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RobotAgentBDI.Metas;

import BESA.BDI.AgentStructuralModel.GoalBDI;
import BESA.BDI.AgentStructuralModel.GoalBDITypes;
import BESA.BDI.AgentStructuralModel.StateBDI;
import BESA.Kernel.Agent.Event.KernellAgentEventExceptionBESA;
import Init.InitRESPwA;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import Tareas.DetectarPersonaCercana.InterrumpirActividades;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;

/**
 *
 * @author tesispepper
 */
public class DetectarPersonaCercana extends GoalBDI{
     private static String descrip = "DetectarPersonaCercana";
    public static DetectarPersonaCercana buildGoal() {
        InterrumpirActividades ia = new InterrumpirActividades();
        Plan rolePlan= new Plan();
        rolePlan.addTask(ia);
        
        RationalRole detectPersona = new RationalRole(descrip, rolePlan);
        DetectarPersonaCercana b = new DetectarPersonaCercana(InitRESPwA.getPlanID(), detectPersona, descrip, GoalBDITypes.DUTY);
        return b;
    }
    public DetectarPersonaCercana(long id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
        //System.out.println("Meta DemostrarSenialesVida created
    }

    @Override
    public double evaluateViability(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta DemostrarSenialesVida evaluateViability");
        return 1;
    }

    @Override
    public double detectGoal(Believes believes) throws KernellAgentEventExceptionBESA {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if ((blvs.getbEstadoInteraccion().getDistanciaPwA() < 0.8f) && (blvs.getbEstadoInteraccion().getDistanciaPwA() >= 0)
                && !blvs.getbEstadoRobot().isEstaSuspendido())
        {
            blvs.getbEstadoInteraccion().setEstoyDetectandoPersonaCerca(true);
            return 1;
        }
        if (blvs.getbEstadoInteraccion().getEstoyDetectandoPersonaCerca())
        {
            return 1;
        }
        return 0;
    }

    @Override
    public double evaluatePlausibility(Believes believes) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta DemostrarSenialesVida evaluatePlausibility");
        return 1;
    }

    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta DemostrarSenialesVida evaluateContribution");
        return 1;
    }

    @Override
    public boolean predictResultUnlegality(StateBDI agentStatus) throws KernellAgentEventExceptionBESA {
        //System.out.println("Meta DemostrarSenialesVida predictResultUnlegality");
        return true;
    }

    @Override
    public boolean goalSucceeded(Believes believes) throws KernellAgentEventExceptionBESA {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        System.out.println("Mi condicional está true??" + blvs.getbEstadoInteraccion().getEstoyDetectandoPersonaCerca());
        System.out.println("y mi otra conficional?" + (blvs.getbEstadoInteraccion().getDistanciaPwA() >= 0.8f || blvs.getbEstadoInteraccion().getDistanciaPwA() < 0));
        if (blvs.getbEstadoInteraccion().getDistanciaPwA() >= 0.8f || blvs.getbEstadoInteraccion().getDistanciaPwA() < 0)
        //if (!blvs.getbEstadoInteraccion().getEstoyDetectandoPersonaCerca())
        {
            System.out.println("Logré salir");
            blvs.getbEstadoInteraccion().setEstoyDetectandoPersonaCerca(false);
            return true;
        }
        return false;
    }
}
