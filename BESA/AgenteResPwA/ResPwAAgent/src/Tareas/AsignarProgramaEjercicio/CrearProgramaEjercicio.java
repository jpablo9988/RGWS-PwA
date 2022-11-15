package Tareas.AsignarProgramaEjercicio;

import BDInterface.RESPwABDInterface;
import ResPwAEntities.Horario;
import ResPwAEntities.PerfilEjercicio;
import ResPwAEntities.PerfilMedico;
import ResPwAEntities.PerfilPwa;
import ResPwAEntities.ProgramaEjercicio;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import RobotAgentBDI.ServiceRequestDataBuilder.ServiceRequestBuilder;
import ServiceAgentResPwA.Guard.ServiceDataRequest;
import ServiceAgentResPwA.VoiceServices.VoiceServiceRequestType;
import Utils.ResPwaUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import rational.mapping.Believes;
import rational.mapping.Task;

/**
 *
 * @author juan.amorocho
 */
public class CrearProgramaEjercicio extends Task {

    private HashMap<String, Object> infoServicio = new HashMap<>();
    Scanner scan;
    boolean puedoProseguir;

    public CrearProgramaEjercicio() {
        System.out.println(" (ProgramarRutina) - Meta construida.");
        scan = new Scanner(System.in);
        puedoProseguir = false;
    }

    @Override
    public void executeTask(Believes parameters) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        PerfilPwa miPerfil = blvs.getbPerfilPwA().getPerfil();
        ServiceDataRequest srb;
        int respuesta;
        if (miPerfil.getPerfilEjercicio() == null) {
            try {
                //Say - Solo entra acá cuando vé que no tiene un perfil de ejercicio.
                infoServicio.put("SAY", "Bienvenidos a mi servicio de programa de ejercicios basado en Vivifrail! "
                        + " Al final de este programa, garantizo que quedaremos fuertes, flexibles y saludables.");
                srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
                infoServicio = new HashMap<>();

                // -- NEW --
                if (miPerfil.getPerfilMedico().getSppb() == null || miPerfil.getPerfilMedico().getRiesgoCaida() == null) {
                    PerfilMedico updatedPM = miPerfil.getPerfilMedico();
                    infoServicio.put("SAY", "Veo que no tengo los datos apropiados para asignar un programa de ejercicio."
                            + "Necesito el resultado de las pruebas SPPB y Caida de Riesgo para asignar mi"
                            + "programa de ejercicio! Porfavor, refiere a la consola de B.D.I para ingresar tus resultados.");
                    srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                    ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
                    infoServicio = new HashMap<>();
                    blvs.getbEstadoInteraccion().setExistenPruebasEjercicio(false);
                    // -- Consola Pruebas -- //
                    respuesta = preguntarXConsola(0, 13, "Ahora, ingresa los resultados de la "
                            + "prueba SPPB a en la consola de B.D.I (slash) BESA de cero a doce. Si todavia no tienes las pruebas, cancela ingresando"
                            + " (13).", blvs);
                    if (respuesta == 13) {
                        System.out.println(" Se ha cancelado la creación del perfil de ejercicio. ");
                        blvs.getbEstadoInteraccion().setCancelarProgramacionEjercicio(true);
                        return;
                    } else {

                        updatedPM.setSppb(respuesta);
                        respuesta = preguntarXConsola(1, 5, "Genial! Ahora ingresa el puntaje, de uno a cuatro, de riesgo de Caida del PwA"
                                + ", Ingresa cinco para cancelar", blvs);
                        if (respuesta == 5) {
                            System.out.println(" Se ha cancelado la creación del perfil de ejercicio. ");
                            blvs.getbEstadoInteraccion().setCancelarProgramacionEjercicio(true);
                            return;
                        } else {
                            updatedPM.setRiesgoCaida(respuesta);
                            System.out.println(" Se ha guardado los valores! ");
                        }
                        RESPwABDInterface.updateMedicalProfile(updatedPM);
                        // -- persistir en base de datos.
                    }

                }

                
                respuesta = preguntarXConsola(1, 3, "Listo, Voy a ajustar el nuevo programa para ser realizado todos los dias a las 3 p.m, EST."
                        + "En la consola BESA, ingresa 1 para confirmar, 2 para cambiar e ingresar tu propio horario, y 3 para cancelar.", blvs);

                switch (respuesta) {

                    case 2:
                        respuesta = asignarHorario(blvs);
                        if (respuesta == 3) {
                            System.out.println(" Se ha cancelado la creación del perfil de ejercicio. ");
                            blvs.getbEstadoInteraccion().setCancelarProgramacionEjercicio(true);
                            return;
                        }

                        break;

                    case 1:

                        Date date = new Date();
                        Calendar calendar = GregorianCalendar.getInstance();
                        calendar.setTime(date);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        if (hour > 15) {
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.HOUR_OF_DAY, 15);
                        date = calendar.getTime();
                        PerfilEjercicio nuevoPerfilEjercicio = new PerfilEjercicio(miPerfil.getCedula(), 0,
                                date, 15, 0);
                        
                        List<ProgramaEjercicio> misProgramas = RESPwABDInterface.getExercisePrograms();
                        for (int i = 0; i < misProgramas.size(); i++) {
                            if (misProgramas.get(i).getNombre().equals("C1")) {
                                nuevoPerfilEjercicio.setNombrePrograma(misProgramas.get(i));
                            }
                        }
                        
                        RESPwABDInterface.createExcerciseProfile(nuevoPerfilEjercicio);
                        miPerfil.setPerfilEjercicio(nuevoPerfilEjercicio);
                        miPerfil.setTieneProgramaEjercicio(true);
                        RESPwABDInterface.updateProfile(miPerfil);
                        blvs.updateProfileInBelieves(miPerfil.getCedula());
                        // --- CREAR HORARIO --- //
                        List<Horario> horario = new ArrayList<>();
                        for (int i = 0; i < 7; i++) {
                            Horario aux = new Horario(i + 1, 15);
                            aux.setCedula(nuevoPerfilEjercicio);
                            RESPwABDInterface.createSchedule(aux);
                            horario.add(aux);
                        }
                        nuevoPerfilEjercicio.setHorarioList(horario);
                        RESPwABDInterface.updateExcerciseProfile(nuevoPerfilEjercicio);
                        break;

                    default:
                        System.out.println(" Se ha cancelado la creación del perfil de ejercicio. ");
                        blvs.getbEstadoInteraccion().setCancelarProgramacionEjercicio(true);
                        return;

                }
                infoServicio = new HashMap<>();
                infoServicio.put("SAY", "Muy bien; He cuadrado tu creación de perfil de ejercicios! Yupi!");
                srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
                ResPwaUtils.requestService(srb, (RobotAgentBelieves) parameters);
                infoServicio = new HashMap<>();

            } catch (Exception e) {
                System.out.println(e + "...ERROR al Crear perfil!");
            }
        }
        blvs.updateProfileInBelieves(miPerfil.getCedula());
        puedoProseguir = true;
        System.out.println(" (ProgramarRutina) - Meta Ejecutada.");
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println(" (ProgramarRutina) - Interrupción de Meta.");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println(" (ProgramarRutina) - Cancelación de Meta.");
    }

    @Override
    public boolean checkFinish(Believes believes) {
        return puedoProseguir == true;
    }

    private int asignarHorario(RobotAgentBelieves blvs) {
        List<Integer> horaList = new ArrayList<>();
        int respuesta = 2;
        while (respuesta == 2) {
            horaList.add(preguntarXConsola(0, 24, "Selecciona una hora desde 0 a 24 para el dia Lunes:", blvs));
            horaList.add(preguntarXConsola(0, 24, "¿para el dia Martes?:", blvs));
            horaList.add(preguntarXConsola(0, 24, "¿para el dia Miercoles?:", blvs));
            horaList.add(preguntarXConsola(0, 24, "¿el Jueves?", blvs));
            horaList.add(preguntarXConsola(0, 24, "Selecciona una hora para el dia Viernes:", blvs));
            horaList.add(preguntarXConsola(0, 24, "Selecciona una hora para el dia Sabado:", blvs));
            horaList.add(preguntarXConsola(0, 24, "Selecciona una hora para el dia Domingo:", blvs));
            respuesta = preguntarXConsola(1, 3, "¿Esta bien el horario que escogiste?"
                    + ". Ingresa en la consola 1 para confirmar, dos para intentar de nuevo, y tres para cancelar.", blvs);

        }
        if (respuesta == 3) {
            System.out.println(" Se ha cancelado la creación del perfil de ejercicio. ");
            blvs.getbEstadoInteraccion().setCancelarProgramacionEjercicio(true);
            return respuesta;

        } else {

            Date date = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            System.out.println("dia " + dayOfWeek);
            if (hour > horaList.get(dayOfWeek -1)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);

            }
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.HOUR_OF_DAY, horaList.get(dayOfWeek -1));
            date = calendar.getTime();
            PerfilEjercicio nuevoPerfilEjercicio = new PerfilEjercicio(blvs.getbPerfilPwA().getPerfil().getCedula(),
                     0, date, horaList.get(dayOfWeek -1), 0);
            List<ProgramaEjercicio> misProgramas = RESPwABDInterface.getExercisePrograms();
                        for (int i = 0; i < misProgramas.size(); i++) {
                            if (misProgramas.get(i).getNombre().equals("C1")) {
                                nuevoPerfilEjercicio.setNombrePrograma(misProgramas.get(i));
                            }
                        }
            System.out.println("LLega aca?");
            RESPwABDInterface.createExcerciseProfile(nuevoPerfilEjercicio);
            blvs.getbPerfilPwA().getPerfil().setPerfilEjercicio(nuevoPerfilEjercicio);
            RESPwABDInterface.updateProfile(blvs.getbPerfilPwA().getPerfil());
            blvs.updateProfileInBelieves(blvs.getbPerfilPwA().getPerfil().getCedula());
            // -- CREAR HORARIO -- //
            
            System.out.println("y aca?");
            List<Horario> horario = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                Horario aux = new Horario(i + 1, horaList.get(i));
                aux.setCedula(nuevoPerfilEjercicio);
                RESPwABDInterface.createSchedule(aux);
                horario.add(aux);
            }
            nuevoPerfilEjercicio.setHorarioList(horario);
            return 1;
        }

    }

    private int preguntarXConsola(int minLimite, int maxLimite, String pregunta, RobotAgentBelieves blvs) {
        int respuesta = minLimite;
        System.out.println(pregunta);
        infoServicio = new HashMap<>();
        infoServicio.put("SAY", pregunta);
        ServiceDataRequest srb = ServiceRequestBuilder.buildRequest(VoiceServiceRequestType.SAY, infoServicio);
        ResPwaUtils.requestService(srb, (RobotAgentBelieves) blvs);
        while (respuesta >= minLimite && respuesta <= maxLimite) {
            while (!scan.hasNextInt()) {
                scan.next();
            }
            respuesta = scan.nextInt();
            if ((respuesta < minLimite) && (respuesta > maxLimite)) {
                System.out.println(" Porfavor ingresa un valor válido. A continuación, vuelve a intentar:");
            } else {
                break;
            }
        }
        return respuesta;
    }
}
