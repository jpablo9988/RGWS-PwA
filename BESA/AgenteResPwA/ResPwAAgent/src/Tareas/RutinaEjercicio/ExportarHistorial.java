/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tareas.RutinaEjercicio;

import ResPwAEntities.Ejercicio;
import ResPwAEntities.Historial;
import ResPwAEntities.PerfilEjercicio;
import ResPwAEntities.PerfilMedico;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import rational.mapping.Believes;
import rational.mapping.Task;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author tesispepper
 */
public class ExportarHistorial extends Task {

    private final static String FILENAME = "historialMedicoOutput.txt";
    private boolean puedoProseguir = false;

    public ExportarHistorial() {
        puedoProseguir = false;

        // System.out.println(" (ExportarHistorial) - Task construida.");
    }

    @Override
    public void executeTask(Believes parameters) {
         System.out.println("-------- GUARDANDO HISTORIAL ............");

        boolean newFileFlag = false;
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        String oldFile = "";
        try {File textFile = new File(FILENAME);
            if (!textFile.exists()) {
                textFile.createNewFile();
                newFileFlag = true;
            }
            else
            {
                newFileFlag = false;
                System.out.println("-------- LEYENDO DOCUEMNTO PRIMO ............");
                Scanner reader = new Scanner(textFile);
                while(reader.hasNextLine())
                {
                   oldFile += reader.nextLine() +"\n";
                }
                reader.close();
            }
            try (FileWriter writer = new FileWriter(textFile)) {
                
                PerfilMedico currPerfilMedico = blvs.getbPerfilPwA().getPerfil().getPerfilMedico();
                PerfilEjercicio currPerfilEjercicio = blvs.getbPerfilPwA().getPerfil().getPerfilEjercicio();
                Date currDate = new Date();
                if (newFileFlag) {
                     System.out.println("-------- CREANDO DOCUMENTO ............");
                    // -- encontrar edad de PwA. -- //

                    Calendar currDiaCalendar = GregorianCalendar.getInstance();
                    currDiaCalendar.setTime(currDate);
                    int edad = currDiaCalendar.get(Calendar.YEAR);
                    currDiaCalendar.setTime(blvs.getbPerfilPwA().getPerfil().getFechaNacimiento());
                    edad -= currDiaCalendar.get(Calendar.YEAR);
                    // ----------------------------- //
                    writer.write("Backlog - Historial de Ejercicios\n"
                            + "----------------INFORMACIóN DEL PACIENTE---------------\n"
                            + "Nombre: " + blvs.getbPerfilPwA().getPerfil().getNombre() + ".\n"
                            + "Apellido: " + blvs.getbPerfilPwA().getPerfil().getApellido() + ".\n"
                            + "Cedula: " + blvs.getbPerfilPwA().getPerfil().getCedula() + ".\n"
                            + "Edad: " + edad + ".\n"
                            + "Causa de demencia: " + currPerfilMedico.getCausaDemenciaCondicion().getCondicion() + ".\n"
                            + "Puntaje SPPB: " + currPerfilMedico.getSppb() + ".\n"
                            + "Puntaje Riesgo de Caida: " + currPerfilMedico.getRiesgoCaida() + ".\n"
                            + "----------------HISTORIAL DE EJERCICIOS ---------------\n"
                    );

                }
                if (!newFileFlag)
                {
                    System.out.println("-------- LEYENDO DOCUEMNTO ............");
                    writer.write(oldFile);
                }
                writer.write("----------Sesion del dia " + currDate.toString() + "---------\n");
                String pesoUsado = "N/A";
                String retroGusto = "N/A";
                String retroCans = "N/A";
                System.out.println("-------- LEYENDO  HISTORIAL!!!!!!!!!............");
                for (int i = 0; i < blvs.getbEstadoInteraccion().getCurrHistorialList().size(); i++) {
                    Historial currHistorial = blvs.getbEstadoInteraccion().getCurrHistorialList().get(i);
                    System.out.println("-------- ENTRE AL FOR LOOP CON ESTE HISTORIAL: " + currHistorial.getEjercicio() + " !!!!!!!!!............");
                    
                    for (int j = 0; j < currPerfilEjercicio.getEjercicioList().size(); j++) {
                        System.out.println("-------- PESO GUARDADO " + pesoUsado + " !!!!!!!!!............");
                        if (currPerfilEjercicio.getEjercicioList().get(j).getNombre().equals(currHistorial.getEjercicio())) {
                            Ejercicio currEjercicio = currPerfilEjercicio.getEjercicioList().get(j);
                            for (int k = 0; k < currEjercicio.getCategoriaEntrenamientoList().size(); k++) {
                                if (currEjercicio.getCategoriaEntrenamientoList().get(k).getIntensidadList().get(currPerfilEjercicio.getIndexIntensidadActual()).getPeso() != null && currEjercicio.getNecesitaPeso()) {
                                    pesoUsado = String.valueOf(currEjercicio.getCategoriaEntrenamientoList().get(k).getIntensidadList().get(currPerfilEjercicio.getIndexIntensidadActual()).getPeso());
                                }
                            }
                        }
                    }
                    System.out.println("-------- PESO GUARDADO " + pesoUsado + " !!!!!!!!!............");
                    if (currHistorial.getRetroalimentacionGusto() != null)
                    {
                        retroGusto = String.valueOf(currHistorial.getRetroalimentacionGusto());
                    }
                    if (currHistorial.getRetroalimentacionCansancio()!= null)
                    {
                        retroCans = String.valueOf(currHistorial.getRetroalimentacionCansancio());
                    }
                    System.out.println("-------- PESO GUARDADO " + pesoUsado + " !!!!!!!!!............");
                    System.out.println("-------- RETROGUSTO GUARDADO " + retroGusto + " !!!!!!!!!............");
                    System.out.println("-------- RETROCANS GUARDADO " + retroCans + " !!!!!!!!!............");
                    System.out.println("-------- EESTOY ANTES DEL LOOP ............");
                    writer.write(" -----------------------\n"
                            + "Nombre del Ejercicio: " + currHistorial.getEjercicio() + ".\n"
                            + "Repeticiones realizadas : " + currHistorial.getRepeticionesHechas() + ".\n"
                            + "Retroalimentación de Gusto(1 - 3): " + retroGusto + ".\n"
                            + "Retroalimentación de Cansancio (1 - 3): " + retroCans + ".\n"
                            + "Peso usado: " + pesoUsado + ".\n"
                            + "Notas: " + currHistorial.getNotas() + ".\n"
                    );
                     System.out.println("-------- ESCRIBIO LO NECESARIO EN EL HISTORIAL EN EL LOOP ............");
                }
                writer.write("-------------------------------------------\n");
                System.out.println("--------QUEDO GUARDADO EL HISTORIAL ............");
            }
            
        } catch (IOException e) {
            System.out.println("An error has ocurred in creating a historial File following this: " + e.getMessage());
        }
        puedoProseguir = true;
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println("ExportarHistorial - Interrumpido. ");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println("ExportarHistorial - Cancelado. ");
    }

    @Override
    public boolean checkFinish(Believes believes) {
        return puedoProseguir;
    }

}
