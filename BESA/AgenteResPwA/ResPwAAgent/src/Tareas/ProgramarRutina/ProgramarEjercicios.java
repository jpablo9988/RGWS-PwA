/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tareas.ProgramarRutina;

import BDInterface.RESPwABDInterface;
import ResPwAEntities.CategoriaEntrenamiento;
import ResPwAEntities.Dia;
import ResPwAEntities.Ejercicio;
import ResPwAEntities.PerfilEjercicio;
import ResPwAEntities.ProgramaEjercicio;
import RobotAgentBDI.Believes.RobotAgentBelieves;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import rational.mapping.Believes;
import rational.mapping.Task;

/**
 *
 * @author juan.amorocho
 */
public class ProgramarEjercicios extends Task {

    //private HashMap<String, Object> infoServicio = new HashMap<>();
    boolean puedoProseguir;

    public ProgramarEjercicios() {
        System.out.println(" (ProgramarEjercicios) - Meta construida.");
        puedoProseguir = false;

    }

    // ¿Qué se hace en esta meta?
    //  Confirma -> ¿Ha pasado la rutina de hoy? y ¿Tiene ejercicios asignados?
    //  Si ha pasado la rutina de hoy; Mira los ejercicios asignados, los quita del 'pool' aleatorio.
    //  Si no existen ejercicios dentro de una categoría, no los asigna.
    //  Ordena los ejercicios dependiendo del orden dictado por 'dia'.
    //  Finalmente, cambia la 'proxFecha' al dia siguiente...!
    @Override
    public void executeTask(Believes parameters) {
        RobotAgentBelieves blvs = (RobotAgentBelieves) parameters;
        //PerfilEjercicio miPerfil = blvs.getbPerfilPwA().getPerfil().getPerfilEjercicio();
        PerfilEjercicio miPerfil = RESPwABDInterface.getExcerciseProfile(blvs.getbPerfilPwA().getPerfil().getCedula());
        System.out.println("Tiene programa de ejercicio (BD)" +  RESPwABDInterface.getExcerciseProfile(blvs.getbPerfilPwA().getPerfil().getCedula()).getPerfilPwaCedula());
        //System.out.println("Tiene programa de ejercicio (Local)" + miPerfil.getPerfilPwaCedula());
        Date currDate = new Date();
        
        List<Ejercicio> listaEjercicio = miPerfil.getEjercicioList();
        Calendar currDiaCalendar = GregorianCalendar.getInstance();
        currDiaCalendar.setTime(currDate);
        int currHour = currDiaCalendar.get(Calendar.HOUR_OF_DAY);
        /*if (((currDate.after(miPerfil.getFechaProx())) && currHour > miPerfil.getHoraProx())
                || listaEjercicio.isEmpty()) {*/
        ProgramaEjercicio miPrograma = miPerfil.getNombrePrograma();
        List<EjercicioMap> ejerciciosValidos = new ArrayList<>();
        //HashMap<Ejercicio, String> ejerciciosValidos = new HashMap<>();

        for (int i = 0; i < miPrograma.getCategoriaEntrenamientoList().size(); i++) {
            for (int j = 0; j < miPrograma.getCategoriaEntrenamientoList().get(i).getEjercicioList().size(); j++) {
                EjercicioMap auxMap = new EjercicioMap(miPrograma.getCategoriaEntrenamientoList().get(i).getEjercicioList().get(j), miPrograma.getCategoriaEntrenamientoList().get(i).getTipo());
                ejerciciosValidos.add(auxMap);
                //ejerciciosValidos.put(miPrograma.getCategoriaEntrenamientoList().get(i).getEjercicioList().get(j), miPrograma.getCategoriaEntrenamientoList().get(i).getTipo());
            }

        }
        /*
        if (!listaEjercicio.isEmpty()) {
            for (int i = 0; i < listaEjercicio.size(); i++) {

                for (int j = 0; j < ejerciciosValidos.size(); j++) {

                    if (ejerciciosValidos.get(j).getMiEjercicio().getNombre().equals(listaEjercicio.get(i).getNombre())) {
                        for (int z = 0; z < listaEjercicio.get(i).getCategoriaEntrenamientoList().size(); z++)
                        {
                        if (listaEjercicio.get(i).getCategoriaEntrenamientoList().get(z).getTipo().contains(miPrograma.getNombre())) {
                            int noEjerciciosInTag = 0;
                            for (int k = 0; k < ejerciciosValidos.size(); k++) {
                                if (ejerciciosValidos.get(j).getTipo().equals(listaEjercicio.get(i).getCategoriaEntrenamientoList().get(z).getTipo())) {
                                    noEjerciciosInTag++;
                                }
                                if (noEjerciciosInTag > 1) {
                                    ejerciciosValidos.remove(j);
                                    break;
                                }
                            }

                        }
                        }
                    }
                }
            }
        }*/
        listaEjercicio.removeAll(listaEjercicio); //removes all items.
        int currDia = currDiaCalendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("Mi fecha proxima quedo como: "+ miPerfil.getFechaProx());
        System.out.println("Mi fecha actual es: " + currDate);
        System.out.println("Mi hora próxima es" + miPerfil.getHoraProx());
        System.out.println("Mi hora actual es: " + currHour);
        if (currDate.after(miPerfil.getFechaProx()) && miPerfil.getHoraProx() < currHour) {
            currDiaCalendar.add(Calendar.DAY_OF_MONTH, 1);
            currDia = currDiaCalendar.get(Calendar.DAY_OF_WEEK);
            currDiaCalendar.set(Calendar.HOUR_OF_DAY, miPerfil.getHorarioList().get(currDia).getHora()); //Cambia en nuevo!
            miPerfil.setFechaProx(currDiaCalendar.getTime());
            miPerfil.setHoraProx( miPerfil.getHorarioList().get(currDia).getHora());
        }
        Dia miDia = new Dia();
        for (int i = 0; i < miPrograma.getDiaXProgramaEjercicioList().size(); i++) {
            if (miPrograma.getDiaXProgramaEjercicioList().get(i + 1).getIndiceOrden() == currDia) {

                miDia = miPrograma.getDiaXProgramaEjercicioList().get(i).getDia();
                break;
            }
        }
        for (int i = 0; i < miDia.getDiaXCategoriaEntrenamientoList().size(); i++) {
            List<Ejercicio> ejerciciosXCategoria = new ArrayList<>();
            for (int j = 0; j < ejerciciosValidos.size(); j++) {

                CategoriaEntrenamiento catAux = miDia.getDiaXCategoriaEntrenamientoList().get(i).getCategoriaEntrenamiento();
                if (ejerciciosValidos.get(j).getTipo().equals(catAux.getTipo())) {
                    ejerciciosXCategoria.add(ejerciciosValidos.get(j).getMiEjercicio());
                    System.out.println("Añadi ejercicio a pool de posibles:" + ejerciciosValidos.get(j).getMiEjercicio().getNombre());
                }

            }
            if (!ejerciciosXCategoria.isEmpty()) {
                int randomIndex = ThreadLocalRandom.current().nextInt(0, ejerciciosXCategoria.size());

                listaEjercicio.add(ejerciciosXCategoria.get(randomIndex));
                System.out.println("MI EJERCICIO GUARDADO :!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!." + listaEjercicio.get(listaEjercicio.size() - 1).getNombre());

            }
        }
        miPerfil.setEjercicioList(listaEjercicio);
        miPerfil.setDiasHechos(miPerfil.getDiasHechos() + 1);
        if (miPerfil.getDiasHechos() % 14 == 14) {
            miPerfil.setIndexIntensidadActual(miPerfil.getIndexIntensidadActual() + 1);
        }
        RESPwABDInterface.updateExcerciseProfile(miPerfil);

        puedoProseguir = true;
    }

    @Override
    public void interruptTask(Believes believes) {
        System.out.println(" (ProgramarEjercicios) - Interrupción de Meta.");
    }

    @Override
    public void cancelTask(Believes believes) {
        System.out.println(" (ProgramarEjercicios) - Cancelación de Meta.");
    }

    @Override
    public boolean checkFinish(Believes believes) {
        return puedoProseguir == true;
    }

    class EjercicioMap {

        private Ejercicio miEjercicio;
        private String tipo;

        public EjercicioMap(Ejercicio miEjercicio, String tipo) {
            this.miEjercicio = miEjercicio;
            this.tipo = tipo;
        }

        public Ejercicio getMiEjercicio() {
            return miEjercicio;
        }

        public String getTipo() {
            return tipo;
        }

        public void SetMiEjercicio(Ejercicio miEjercicio) {
            this.miEjercicio = miEjercicio;
        }

        public void SetTipo(String tipo) {
            this.tipo = tipo;
        }

    }
}
