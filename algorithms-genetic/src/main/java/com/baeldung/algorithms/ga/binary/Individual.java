package com.baeldung.algorithms.ga.binary;

import java.util.Arrays;
import lombok.Data;

@Data
public class Individual {

    protected int defaultGeneLength = 16;
    private byte[] genes = new byte[defaultGeneLength];
    private int fitness = 0;
    private String check = "0001111100001111";
    private String check2 ="0101111100001111";
    private String[] couple = {check,check2};
    

    public Individual(int z) {
        
        if (z ==0){
            check = couple[0];
        } else {
            check = couple[1];
        }
        int [] arr = new int [16];
        for (int i = 0; i < genes.length; i++) {
//            byte gene2 = (byte) check[i];
//            System.out.println("The gene2 is :" + gene2);
            
            
              arr[i] = (int) check.charAt(i);
              System.out.println( Character.getNumericValue(arr[i]));
              byte gene = (byte) Character.getNumericValue(arr[i]);
              genes[i] = gene;
             System.out.println("Single gene value is:" +  genes[i]);
                 
            
//            byte gene = (byte) Math.round(Math.random());
            
//            genes[i] = gene;
//             System.out.println("Single gene value is:" +  genes[i]);
        }
        System.out.print("[ ");
        for (int i = 0; i < genes.length; i++) {
            System.out.print(genes[i]);
        }
        System.out.println("] ");
    }

    protected byte getSingleGene(int index) {
        return genes[index];
    }

    protected void setSingleGene(int index, byte value) {
        genes[index] = value;
//        System.out.println("Single gene value is:" +  genes[index]);
        fitness = 0;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = SimpleGeneticAlgorithm.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < genes.length; i++) {
            geneString += getSingleGene(i);
        }
        return geneString;
    }

}
