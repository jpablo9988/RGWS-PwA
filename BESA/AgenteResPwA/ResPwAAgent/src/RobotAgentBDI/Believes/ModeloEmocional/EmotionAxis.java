package RobotAgentBDI.Believes.ModeloEmocional;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EmotionAxis {

    private final String positiveName;
    private final String negativeName;
    private double currentValue;
    private double baseValue;
    private final Map<String, Double> eventInfluence;
    private double forgetFactor = 0.0f;
    private Date lastForgetUpdateTime;

    public EmotionAxis(String positiveName, String negativeName, double currentValue, double baseValue, double forgetFactor) {
        this.positiveName = positiveName;
        this.negativeName = negativeName;
        this.forgetFactor = forgetFactor;
        this.setCurrentValue(currentValue);
        this.setBaseValue(baseValue);
        eventInfluence = new HashMap<>();
    }

    public String getPositiveName() {
        return positiveName;
    }

    public String getNegativeName() {
        return negativeName;
    }

    public double getCurrentValue() {
        double newValue = applyForgetFactor(getBaseValue(), currentValue, lastForgetUpdateTime, forgetFactor);
        this.setCurrentValue(newValue);
        return currentValue;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public double getActivationValue() {
        return Math.abs(baseValue - getCurrentValue());
    }

    public double getForgetFactor() {
        return forgetFactor;
    }

    public void setForgetFactor(double forgetFactor) {
        this.forgetFactor = forgetFactor;
    }

    public final void setCurrentValue(double value) {
        this.currentValue = Utils.checkNegativeOneToOneLimits(value);
        this.lastForgetUpdateTime = new Date();
    }

    public final void setBaseValue(double value) {
        this.baseValue = Utils.checkNegativeOneToOneLimits(value);
    }

    public void setEventInfluence(String eventName, double influence) {
        eventInfluence.put(eventName, Utils.checkZeroToOneLimits(influence));
    }

    public void setEventInfluences(Map<String, Double> evInfluences) {
        if (evInfluences != null) {
            Iterator itr = evInfluences.keySet().iterator();
            if (itr != null) {
                while (itr.hasNext()) {
                    String key = (String) itr.next();
                    double value = evInfluences.get(key);
                    setEventInfluence(key, value);
                }
            }
        }
    }

    public Double getEventInfluence(String eventName) {
        return eventInfluence.get(eventName);
    }

    public Map<String, Double> getEventInfluences() {
        return eventInfluence;
    }
    public void printEventInfluences(){
        eventInfluence.keySet().forEach(object -> {
            System.out.println("Event: "+object+" Object: "+eventInfluence.get(object));
        });
    }

    public void updateIntensity(String event, double intensity) {
        Double influence = getEventInfluence(event);
        if (influence != null) {
            intensity = influence * intensity;
            setCurrentValue(getCurrentValue() + intensity);
        }
    }

    @Override
    public String toString() {
        String str = this.positiveName + "/" + this.negativeName
                + " {Value:" + this.getCurrentValue() + " Base:" + this.getBaseValue() + "}";
        str += " EvInf: " + eventInfluence.toString();
        return str;
    }

    @Override
    public EmotionAxis clone() throws CloneNotSupportedException {
        EmotionAxis e = new EmotionAxis(this.positiveName, this.negativeName, this.getCurrentValue(), this.baseValue, this.forgetFactor);
        Iterator itr = eventInfluence.keySet().iterator();
        if (itr != null) {
            while (itr.hasNext()) {
                String key = (String) itr.next();
                double value = eventInfluence.get(key);
                e.setEventInfluence(key, value);
            }
        }
        return e;
    }

    private static double applyForgetFactor(double _baseValue, double _currentValue, Date lastUpdateTime, Double forgetFactor) {
        if (forgetFactor != null) {
            Date now = new Date();
            long tDif = now.getTime() - lastUpdateTime.getTime();
            double slope = ((_baseValue - _currentValue > 0) ? 1 : -1) * forgetFactor / 1000;
            double value = (slope * tDif) + _currentValue;

            if (slope > 0) {
                if (value > _baseValue) {
                    value = _baseValue;
                }
            } else if (slope < 0) {
                if (value <= _baseValue) {
                    value = _baseValue;
                }
            }
            return value;
        }
        return _currentValue;
    }

}
