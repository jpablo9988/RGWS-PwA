package RobotAgentBDI.Believes.ModeloEmocional;

public class SemanticValue {
    private final String name;
    private final double value;
    
    public SemanticValue(String name, double value) {
        this.name = name;
        this.value = Utils.checkNegativeOneToOneLimits(value);
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return name + ": " + value;
    }
}
