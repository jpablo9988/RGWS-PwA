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
import java.util.ArrayList;
import java.util.List;
import rational.RationalRole;
import rational.mapping.Believes;
import rational.mapping.Plan;
import rational.mapping.Task;
import Tareas.Test2.*;
/**
 *
 * @author juan.amorocho
 */
public class TestPlan2 extends GoalBDI
{
    private static String description = "Meta de Prueba";

    /* buildGoal
        Retorna instancia de Gol, crea tareas y plan.
    */
    public static TestPlan2 buildGoal()
    {
        //Instanciación de Tareas
        TestTask task1 = new TestTask();
        TestTask2 task2 = new TestTask2();
        // Agregar Tareas a PLAN. No olvidar agregar una lista con solo la tarea anterior.
        List<Task> taskList = new ArrayList<>();
        Plan rolePlan = new Plan();
        rolePlan.addTask(task1);
        taskList.add(task1);
        rolePlan.addTask(task2,taskList);
        // Creación del Rol
        RationalRole testTask2Role = new RationalRole(description, rolePlan);
        //Instanciacion de Meta y Retorno
        TestPlan2 goal = new TestPlan2 (InitRESPwA.getPlanID(), testTask2Role, description, GoalBDITypes.OPORTUNITY);
        System.out.println("DEBUG: TestPlan2, gol construido");
        return goal;
    }
    public TestPlan2(long id, RationalRole role, String description, GoalBDITypes type) {
        super(id, role, description, type);
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
    public boolean predictResultUnlegality(StateBDI agentStatus) throws KernellAgentEventExceptionBESA {
        return true;
    }
    @Override
    public double detectGoal(Believes believes) throws KernellAgentEventExceptionBESA 
    {        
        //System.out.println("DEBUG: TestPlan2 Detectado");
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        if (blvs.getbEstadoInteraccion().getTestPlanDone()) 
        {
            return 0;
        }
        else
        {
            return 100;
        }
        
    }
    @Override
    public double evaluateContribution(StateBDI stateBDI) throws KernellAgentEventExceptionBESA {
        //System.out.println("DEBUG: TestPlan2, evaluación contribuida");
        return 100;
    }
    @Override
    public boolean goalSucceeded(Believes believes) throws KernellAgentEventExceptionBESA {
        RobotAgentBelieves blvs = (RobotAgentBelieves) believes;
        System.out.println("DEBUG: TestPlan2, gol checkeado: " +  blvs.getbEstadoInteraccion().getTestPlanDone());
        return blvs.getbEstadoInteraccion().getTestPlanDone();
    }
}