package CNPAlgorithms.PSO;

import java.util.Random;
import java.lang.Math;

public class Particle {


    private double[] currentPosition;
    public double[] bestPosition;
    public double bestPositionCost;
    private double[] velocity;
    private Random rand = new Random();
    private int size;

    private static final double inertialCoefficient = (1/(2 * Math.log(2.0)));
    private static final double cognitiveCoefficient = ((Math.log(2)) + 0.5);

    public static void main(String[] args) {
        AntennaArray antArr = new AntennaArray(3, 90.0);
        double[] st = antArr.generateDesign();
        double[] rn = antArr.generateDesign();
        Particle particle = new Particle(st, antArr.evaluate(st), rn);
    }

    public Particle(double[] startingPos, double startingCost, double[] randomPosition) {
        currentPosition = startingPos;
        bestPosition = startingPos;
        size = startingPos.length;
        velocity = new double[size];

        for (int i = 0; i < size; i++){
            velocity[i] = (randomPosition[i] + currentPosition[i])/2;
        }
    }

    public void updatePersonalBest(double[] newPosition, double newCost) {
        bestPosition = newPosition;
        bestPositionCost = newCost;
        //System.out.println(this + ": New personal best:" + Arrays.toString(bestPosition) + " - " + bestPositionCost);
    }

    public void updatePosition(double[] newPosition) {
        currentPosition = newPosition;
    }

    public void updateVelocity(double[] newPosition) {
        currentPosition = newPosition;
    }

    private double[] calculateNewVelocity(double[] globalBestPosition) {
        double[] newVelocity = new double[size];
        for (int i = 0; i < size; i++){
            newVelocity[i] = velocityOnAxis(globalBestPosition, i);
        }
        return newVelocity;
    }

    private double velocityOnAxis(double[] globalBestPosition, int axis) {
        // 0 = X axis,  1 = Y axis
        return inertialCoefficient * velocity[axis] + cognitiveCoefficient * rand.nextDouble()/5 * (bestPosition[axis] - currentPosition[axis]) + cognitiveCoefficient * rand.nextDouble()/5 * (globalBestPosition[axis] - currentPosition[axis]);
    }

    public double[] move(double[] globalBestPosition) {
        velocity = calculateNewVelocity(globalBestPosition);

        double[] newPosition = new double[size];
        for (int i = 0; i < size-1; i++){
            newPosition[i] = currentPosition[i] + velocity[i];
        }
        newPosition[size-1] = size/2.0;

        currentPosition = newPosition;

        return newPosition;
    }
}