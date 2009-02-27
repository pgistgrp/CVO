package org.pgist.packages.knapsack;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.pgist.util.WebUtils;

import bsh.Interpreter;


/**
 * 
 * @author kenny
 *
 */
class GAKnapsackFundingFitnessFunction extends FitnessFunction {
    
    
    private GAKnapsackFitnessProxy proxy;
    
    private GAKnapsackCalculator calculator;
    
    private int evolutionTimes;
    
    private int populationSize;
    
    
    public int getEvolutionTimes() {
        return evolutionTimes;
    }


    public int getPopulationSize() {
        return populationSize;
    }


    public GAKnapsackFundingFitnessFunction(GAKnapsackCalculator calculator, String scriptPath) {
        try {
            this.calculator = calculator;
            
            Interpreter interpreter = new Interpreter();
            
            interpreter.source(WebUtils.getContextPath()+scriptPath);
            
            proxy = (GAKnapsackFitnessProxy) interpreter.get("fitnessFunction");
            proxy.setCalculator(calculator);
            
            evolutionTimes = ((Number) interpreter.get("evolutionTimes")).intValue();
            populationSize = ((Number) interpreter.get("populationSize")).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    
    public GAKnapsackCalculator getCalculator() {
        return calculator;
    }
    
    
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
        return proxy.evaluate(chromosome);
    }//evaluate()
    
    
}//class KnapsackFitnessFunction
