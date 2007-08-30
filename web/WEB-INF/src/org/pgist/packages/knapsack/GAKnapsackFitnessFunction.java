package org.pgist.packages.knapsack;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;


/**
 * 
 * @author kenny
 *
 */
class GAKnapsackFitnessFunction extends FitnessFunction {
    
    
    public static final double MAX_BOUND = 1E9;

    
    private KSChoices[] choices;
    
    private double limit;
    
    
    public GAKnapsackFitnessFunction(KSChoices[] choices, double limit) {
        if (limit<1 || limit>=MAX_BOUND) {
            throw new IllegalArgumentException("Knapsack volumen must be between 1 and " + MAX_BOUND + ".");
        }
        
        this.choices = choices;
        this.limit = limit;
    }
    
    
    public KSChoices[] getChoices() {
        return choices;
    }


    public double getLimit() {
        return limit;
    }


    /**
     * Calculates the total amount of change represented by the given potential solution
     * and returns that amount.
     * 
     * @param chromosome the potential solution to evaluate
     * 
     * @return the total amount of change represented by the given solution
     */
    private double getTotalCost(IChromosome chromosome) {
        double totalCost = 0.0d;
        
        IntegerGene gene = null;
        KSItem item = null;
        
        /*
        for (int i=0; i<chromosome.size(); i++) {
            gene = (IntegerGene) chromosome.getGene(i);
            item = (KSItem) gene.getApplicationData();
            totalCost += gene.intValue() * item.getCost();
        }//for i
        */
        
        int k = 0;
        for (int i=0; i<choices.length; i++) {
            for (int j=0; j<choices[i].getChoices().size(); j++) {
                gene = (IntegerGene) chromosome.getGene(k);
                item = choices[i].getChoices().get(j);
                totalCost += gene.intValue() * item.getCost();
                k++;
            }
        }//for i
        
        return totalCost;
    }//getTotalCost()
    
    
    private double getTotalBenefit(IChromosome chromosome) {
        double totalBenefit = 0.0d;
        
        IntegerGene gene = null;
        KSItem item = null;
        
        /*
        for (int i = 0; i < chromosome.size(); i++) {
            gene = (IntegerGene) chromosome.getGene(i);
            item = (KSItem) gene.getApplicationData();
            totalBenefit += gene.intValue() * item.getProfit();
        }//for i
        */
        
        int k = 0;
        for (int i=0; i<choices.length; i++) {
            for (int j=0; j<choices[i].getChoices().size(); j++) {
                gene = (IntegerGene) chromosome.getGene(k);
                item = choices[i].getChoices().get(j);
                totalBenefit += gene.intValue() * item.getProfit();
                k++;
            }
        }//for i
        
        return totalBenefit;
    }//getTotalBenefit()
    
    
    /**
     * Bonus calculation of fitness value.
     * 
     * @param a_maxFitness maximum fitness value appliable
     * @param a_volumeDifference volume difference in ccm for the items problem
     * 
     * @return bonus for given volume difference
     */
    private double costDifferenceBonus(double costDifference) {
        if (costDifference == 0) {
            return MAX_BOUND;
        } else if (costDifference < 0) {
            return -MAX_BOUND;
        } else {
            /*
             * we arbitrarily work with half of the maximum fitness as basis for non-
             * optimal solutions (concerning costDifference)
             */
            return MAX_BOUND/2 - (costDifference * costDifference);
        }
    }//costDifferenceBonus()
    
    
    /**
     * Bonus calculation of fitness value.
     * 
     * @param totalBenefit
     * 
     * @return bonus for given volume difference
     */
    protected double benefitBonus(double totalBenefit) {
        return (Math.min(MAX_BOUND, totalBenefit));
    }//benefitBonus()
    
    
    /**
     * Calculates the penalty to apply to the fitness value based on the amount of items in the solution.
     *
     * @param chromosome the potential solution to evaluate
     * 
     * @return a penalty for the fitness value based on the single penalty
     */
    private double computeSinglePenalty(IChromosome chromosome) {
        //Map<KSChoices, Integer> counts = new HashMap<KSChoices, Integer>();
        
        IntegerGene gene = null;
        KSItem item = null;
        KSChoices choice = null;
        
        /*
        for (int i=0; i<chromosome.getGenes().length; i++) {
            gene = (IntegerGene) chromosome.getGene(i);
            item = (KSItem) gene.getApplicationData();
            choice = item.getChoices();
            
            if (choice.isSingle()) {
                if (counts.containsKey(choice)) {
                    counts.put(choice, counts.get(choices)+1);
                } else {
                    counts.put(choice, 1);
                }
            }
        }//for i
        
        for (Map.Entry<KSChoices, Integer> entry : counts.entrySet()) {
            if (entry.getValue()>1) return -MAX_BOUND;
        }//for entry
        */
        
        int k = 0;
        for (int i=0; i<choices.length; i++) {
            if (choices[i].isSingle()) {
                int selection = 0;
                for (int j=0; j<choices[i].getChoices().size(); j++) {
                    gene = (IntegerGene) chromosome.getGene(k);
                    item = choices[i].getChoices().get(j);
                    
                    selection += gene.intValue();
                    
                    k++;
                }
                if (selection>1) return -MAX_BOUND;
            }
        }//for i
        
        return 0;
    }//computeSinglePenalty()
    
    
    /**
     * Determine the fitness of the given Chromosome instance. The higher the
     * return value, the more fit the instance. This method should always
     * return the same fitness value for two equivalent Chromosome instances.
     *
     * @param chromosome the Chromosome instance to evaluate
     * 
     * @return a positive double reflecting the fitness rating of the given Chromosome
     */
    public double evaluate(IChromosome chromosome) {
        double totalCost = getTotalCost(chromosome);
        double costDifference = limit - totalCost;
        double totalBenefit = getTotalBenefit(chromosome);
        double fitness = 0.0d;
        
        // Good things
        fitness += costDifferenceBonus(costDifference);
        fitness += benefitBonus(totalBenefit);
        
        // bad things
        fitness += computeSinglePenalty(chromosome);
        
        // Make sure fitness value is always positive.
        return Math.max(1.0d, fitness);
    }//evaluate()
    
    
}//class KnapsackFitnessFunction
