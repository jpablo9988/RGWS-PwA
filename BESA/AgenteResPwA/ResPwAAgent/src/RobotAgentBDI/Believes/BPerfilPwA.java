package RobotAgentBDI.Believes;

import BDInterface.RESPwABDInterface;
import Personalizacion.Modelo.CromosomaCancion;
import Personalizacion.Modelo.ModeloSeleccion;
import ResPwAEntities.ActividadPwa;
import ResPwAEntities.ActXPreferencia;
import ResPwAEntities.Cancion;
import ResPwAEntities.Cuento;
import ResPwAEntities.PerfilPwa;
import ResPwAEntities.PreferenciaXCancion;
import RobotAgentBDI.Utils.ResPwAActivity;
import SensorHandlerAgent.Guards.SensorData;
import Tareas.AnimarElogiarPwA.OpcionesAnimar;
import Tareas.MantenerAtencionPwA.OpcionesAtencion;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import rational.data.InfoData;
import rational.mapping.Believes;

public class BPerfilPwA implements Believes {

    private PerfilPwa perfil;
    private RobotAgentBelieves blvs;

    public BPerfilPwA(RobotAgentBelieves blvs) {
        this.blvs = blvs;
    }

    public PerfilPwa getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilPwa perfil) {
        this.perfil = perfil;
    }

    @Override
    public boolean update(InfoData si) {
//        SensorData infoRecibida = (SensorData) si;
//        if (infoRecibida.getDataP().containsKey("retroalimCancion")) {
//            double porc = ReSPwARACategories.valueOf((String) infoRecibida.getDataP().get("retroalimCancion")).getCambio();
//            double gustoC = blvs.getbEstadoActividad().getCancionActual().getGusto();
//            gustoC += (gustoC * porc);
//            blvs.getbEstadoActividad().getCancionActual().setGusto(gustoC);
//            Cancion c= blvs.getbEstadoActividad().getCancionActual();
//            RESPwABDInterface.updateCancion(c);
//        }
//        if (infoRecibida.getDataP().containsKey("retroalimCuento")) {
//            double gustoC = blvs.getbEstadoActividad().getCuentoActual().getGusto();
//            double porc = ReSPwARACategories.valueOf((String) infoRecibida.getDataP().get("retroalimCuento")).getCambio();
//            gustoC += (gustoC * porc);
//            blvs.getbEstadoActividad().getCuentoActual().setGusto(gustoC);
//            Cuento c= blvs.getbEstadoActividad().getCuentoActual();
//            RESPwABDInterface.updateCuento(c);
//        }
//        if (infoRecibida.getDataP().containsKey("retroalimActividad")) {
//            ResPwAActivity act = blvs.getbEstadoActividad().getActividadActual();
//            List<ActividadPwa> acts = RESPwABDInterface.getActivities();
//            BigInteger bi = null;
//            for (ActividadPwa a : acts) {
//                if (a.getNombre().equalsIgnoreCase(act.toString())) {
//                    bi = a.getId();
//                    break;
//                }
//
//            }
//            List<ActXPreferencia> listap = perfil.getPerfilPreferencia().getActXPreferenciaList();
//            ActXPreferencia pref = null;
//            for (ActXPreferencia axp : listap) {
//                if (axp.getActXPreferenciaPK().getActividadPwaId().equals(bi)) {
//                    pref = axp;
//                    break;
//                }
//            }
//
//            double gustoC = pref.getGusto();
//            double porc = ReSPwARACategories.valueOf((String) infoRecibida.getDataP().get("retroalimActividad")).getCambio();
//            gustoC += (gustoC * porc);
//            pref.setGusto(gustoC);
//            RESPwABDInterface.updateActXPref(pref);
//        }
        
        return true;
    }

  public  Cancion selectSong( ) { // BEstadoEmocionalPwa estadoEmocional,
//        si la emocion es displacentera se traen las canciones aptas para dar el soporte y se toma aleatoriamente
        
//        si la emocion es placentera, la cancion se escoge por un factor
        
        List<PreferenciaXCancion> canciones = blvs.getbPerfilPwA().getPerfil().getPerfilPreferencia().getPreferenciaXCancionList();
        ModeloSeleccion modeloSeleccionCancion = new ModeloSeleccion(canciones);
        
        CromosomaCancion cromCancion = (CromosomaCancion) modeloSeleccionCancion.selectCromosoma();
        Cancion songSelected = cromCancion.getCancion().getCancion();
          
        return songSelected;
    }
    
//    double getFactor( List<Cancion> canciones ){
//        double factor = Math.abs(Math.random() - 0.5);
//        double prom = 0.0;
//        int contadorCancionesMayoresFactor = 0;
//        for (Cancion cancion : canciones) {
//            if( cancion.getGusto() > factor ){
//                contadorCancionesMayoresFactor ++;
//            }
//            prom += cancion.getGusto();
//        }
//        if (contadorCancionesMayoresFactor <= 1 ){
//            prom = prom / canciones.size();
//            factor = prom;
//        }
//        
//        return factor;
//    }
    
 
        @Override
    public Believes clone() throws CloneNotSupportedException {
        super.clone();
        return this;
    }
    
    public OpcionesAtencion getAtencionStrategy()
    {
        Random rand = new Random();
        OpcionesAtencion[]opcs = OpcionesAtencion.values();
        return opcs[rand.nextInt(opcs.length)];
    }

    @Override
    public String toString() {
        return "BPerfilPwA{" + "perfil=" + perfil + ", blvs=" + blvs + '}';
    }
    
    
}
