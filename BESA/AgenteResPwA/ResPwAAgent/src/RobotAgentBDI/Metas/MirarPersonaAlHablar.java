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
import Tareas.MirarPersonaAlHablar.*;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
/**
 *
 * @author juan.amorocho
 */
public class MirarPersonaAlHablar extends GoalBDI 
{
    private static final String DESCRIPTION = "MirarPersonaAlHablar";

    public static MirarPersonaAlHablar buildGoal() 
    {
        System.out.println("DEBUG - CONSTRUCCION META: Mirar Persona la Hablar.");
        MirarPersona mp = new MirarPersona();
        Plan rolePlan= new Plan();
        rolePlan.addTask(mp);
        RationalRole mirarPersonaRol = new RationalRole(DESCRIPTION, rolePlan);
        MirarPersonaAlHablar b = new MirarPersonaAlHablar(InitRESPwA.getPlanID(), mirarPersonaRol, DESCRIPTION, GoalBDITypes.NEED);
        return b;
    }
    public MirarPersonaAlHablar(long id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
        System.out.println("DEBUG - CREACION META: Mirar Persona al Hablar");
    }
    @Override
    public double detectGoal(Believes believes) throws KernellAgentEventExceptionBESA {
        System.out.println("DEBUG - DETECTAR META: Mirar Persona al Hablar");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if(!blvs.getbEstadoInteraccion().isSistemaSuspendidoInt())
        {
            if(!blvs.getbEstadoRobot().isActivadoMovHabla() && blvs.getbEstadoInteraccion().isEstaHablando())
            {
                System.out.println("DEBUG - CONFIRMAR META (Mirar persona la hablar): "
                        + "Las condiciones se han cumplido!");
                return 1.0;
            }
        }
        return 0;
    }

    @Override
    public double evaluateViability(Believes believes) throws KernellAgentEventExceptionBESA {
        return 1;
    }
    @Override
    public double evaluatePlausibility(Believes believes) throws KernellAgentEventExceptionBESA {
        return 1;
    }

    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        return 1;
    }

    @Override
    public boolean predictResultUnlegality(StateBDI agentStatus) throws KernellAgentEventExceptionBESA {
        return true;
    }
    @Override
    public boolean goalSucceeded(Believes believes) throws KernellAgentEventExceptionBESA 
    {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        return !blvs.getbEstadoInteraccion().isEstaHablando();
    }


}
