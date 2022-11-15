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
import ResPwAEntities.Ejercicio;
import ResPwAEntities.PerfilEjercicio;
import ResPwAEntities.PerfilPwa;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import Tareas.ProgramarRutina.ProgramarEjercicios;
import java.util.ArrayList;
import java.util.List;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
import rational.mapping.Task;

/**
 *
 * @author tesispepper
 */
public class ProgramarRutina extends GoalBDI  {
    private final static String DESCRIPTION = "ProgramarRutina";
    public ProgramarRutina(int id, RationalRole role, String description, GoalBDITypes type) 
    {
        super(id, role, description, type);
    }
    public static ProgramarRutina buildGoal() 
    {

        ProgramarEjercicios progEjercicios = new ProgramarEjercicios();
        List<String> resources = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        Plan rolePlan= new Plan();
        
        rolePlan.addTask(progEjercicios);
        
        RationalRole rutEjercicioRole = new RationalRole(DESCRIPTION, rolePlan);
        ProgramarRutina b = new ProgramarRutina(InitRESPwA.getPlanID(), rutEjercicioRole, 
                DESCRIPTION, GoalBDITypes.OPORTUNITY);
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
        
        if (miPerfil.getPerfilEjercicio() == null)
        {
            System.out.println("No tengo perfil.");
            return 0;
        }
        else
        {
            PerfilEjercicio miPerfilEjercicio = miPerfil.getPerfilEjercicio();
            List<Ejercicio> listaEjercicio = miPerfilEjercicio.getEjercicioList();
            if (!listaEjercicio.isEmpty())
            {
                System.out.println("La lista no esta vacia");
                return 0;
            }
            return 1;
        }
        
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
        return false;
    }
    
}
