
package CNPAlgorithms.PSO;

import java.util.Arrays;
import java.lang.Math;

public class Swarm {

    private Particle[] particles;
    private AntennaArray antArr;
    private double[] globalBestPosition;
    private double globalBestCost;

    public static void main(String[] args) {
        Swarm swarm = new Swarm();

        int size = 3;
        swarm.antArr = new AntennaArray(size, 90.0);

        int swarmSize = (int) Math.ceil(20.0 + Math.sqrt((double) size));

        swarm.createSwarm(swarmSize);

        swarm.globalBestPosition = swarm.antArr.generateDesign();
        swarm.globalBestCost = swarm.antArr.evaluate(swarm.globalBestPosition);

        System.out.println("OLD");
        System.out.println(swarm.globalBestCost);
        System.out.println(Arrays.toString(swarm.globalBestPosition) + "\n");

        swarm.swarmSearch(400);

        System.out.println("\nNEW:");
        System.out.println(swarm.globalBestCost);
        System.out.println(Arrays.toString(swarm.globalBestPosition));

    }

    private void swarmSearch(int steps) {
        for (int i = 1; i <= steps; i++) {
            swarmSearchStep(i);
        }
    }

    private void swarmSearchStep(int step) {
        double[] newGlobalBestPosition = globalBestPosition;
        double newGlobalBestCost = globalBestCost;

        for (Particle particle : particles) {
            double[] newPosition = particle.move(globalBestPosition);

            if (antArr.is_valid(newPosition)) {
                double newCost = antArr.evaluate(newPosition);

                if (newCost < particle.bestPositionCost) {
                    particle.updatePersonalBest(newPosition, newCost);
                }

                if (newCost < newGlobalBestCost) {
                    newGlobalBestCost = newCost;
                    newGlobalBestPosition = newPosition;
                    System.out.println(step + ") " + Arrays.toString(newGlobalBestPosition) + " - (" + newGlobalBestCost + ")");
                }
            }
            
        }

        globalBestCost = newGlobalBestCost;
        globalBestPosition = newGlobalBestPosition;

    }

    private void createSwarm(int swarmSize) {
        int i = 0;
        particles = new Particle[swarmSize];

        while (i < swarmSize) {
            double[] startingPos = antArr.generateDesign();
            Particle particle = new Particle(startingPos, antArr.evaluate(startingPos), antArr.generateDesign());
            particles[i] = particle;
            i++;
        }

    }


}