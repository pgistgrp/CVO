package org.pgist.packages.knapsack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;


/**
 * 
 * @author kenny
 *
 */
public class GAKnapsackProjectEngine {
    
    
    private static void printPopulation(Population pop) {
        System.out.println(" ------------------->");
        for (IChromosome ch : (List<IChromosome>) pop.getChromosomes()) {
            for (Gene one : (Gene[]) ch.getGenes()) {
                System.out.print(one.getAllele());
            }
            System.out.println();
        }
        System.out.println(" <-------------------");
    }//printPopulation()
    
    
    /**
     * Executes the genetic algorithm to determine the best solution.
     *
     * @param fitnessFunction
     * @param evolutionTimes
     * @param populationSize
     * 
     * @return
     *
     * @throws Exception
     */
    private static IChromosome findBestSolution(
        GAKnapsackProjectFitnessFunction fitnessFunction,
        final int evolutionTimes,
        int populationSize
    ) throws Exception {
        Configuration.reset();
        
        // Start with a DefaultConfiguration, which comes setup with the most common settings.
        DefaultConfiguration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
        
        // Mutation Rate
        //MutationOperator mutationOperator = new MutationOperator(conf, 2);
        //conf.addGeneticOperator(mutationOperator);
        
        // Set the fitness function we want to use.
        conf.setFitnessFunction(fitnessFunction);
        
        /*
         * Now we need to tell the Configuration object how we want our
         * Chromosomes to be setup. We do that by actually creating a
         * sample Chromosome and then setting it on the Configuration
         * object. As mentioned earlier, we want our Chromosomes to each
         * have as many genes as there are different items available. We want the
         * values (alleles) of those genes to be integers, which represent
         * how many items of that type we have. We therefore use the
         * IntegerGene class to represent each of the genes. That class
         * also lets us specify a lower and upper bound, which we set
         * to senseful values (i.e. maximum possible) for each item type.
         */
        List<IntegerGene> sampleGenes = new ArrayList<IntegerGene>();
        List<IntegerGene> initGenes = new ArrayList<IntegerGene>();
        
        IntegerGene gene = null;
        int choiceCount = 0;
        for (KSChoices choices : fitnessFunction.getCalculator().getChoices()) {
            if (choices.isSingle()) {
                sampleGenes.add(new IntegerGene(conf, 0, choices.getChoices().size()));
                initGenes.add(new IntegerGene(conf, 0, choices.getChoices().size()));
                choiceCount++;
            } else {
                for (KSItem item : choices.getChoices()) {
                    gene = new IntegerGene(conf, 0, 1);
                    //gene.setApplicationData(item);
                    sampleGenes.add(gene);
                    initGenes.add(new IntegerGene(conf, 0, 1));
                    choiceCount++;
                }
            }
        }
        
        IntegerGene[] temp = new IntegerGene[sampleGenes.size()];
        for (int i=0; i<sampleGenes.size(); i++) {
            temp[i] = sampleGenes.get(i);
        }
        
        IChromosome sampleChromosome = new Chromosome(conf, temp);
        conf.setSampleChromosome(sampleChromosome);
        
        /*
         * Finally, we need to tell the Configuration object how many
         * Chromosomes we want in our population. The more Chromosomes,
         * the larger number of potential solutions (which is good for
         * finding the answer), but the longer it will take to evolve
         * the population (which could be seen as bad).
         */
        if (populationSize<sampleGenes.size()+1) populationSize = sampleGenes.size()+1;
        conf.setPopulationSize(populationSize);
        
        Population pop = new Population(conf, populationSize);
        
        IntegerGene[] sGenes = (IntegerGene[]) sampleChromosome.getGenes();
        IntegerGene[] newGenes = new IntegerGene[sGenes.length];
        
        //create an all-0 chromosome
        for (int i=0; i<sGenes.length; i++) {
            newGenes[i] = (IntegerGene) sGenes[i].newGene();
            newGenes[i].setAllele(0);
        }
        IChromosome possibility = Chromosome.randomInitialChromosome(conf);
        possibility.setGenes(newGenes);
        pop.addChromosome(possibility);
        
        //create equal possibility chromosome
        
        for (int i=0; i<initGenes.size(); i++) {
            for (int j=sGenes[i].getLowerBounds(); j<sGenes[i].getUpperBounds(); j++) {
                newGenes = new IntegerGene[sGenes.length];
                
                for (int k=0; k<sGenes.length; k++) {
                    newGenes[k] = (IntegerGene) sGenes[k].newGene();
                    newGenes[k].setAllele(0);
                }
                
                newGenes[i].setAllele(j+1);
                possibility = Chromosome.randomInitialChromosome(conf);
                possibility.setGenes(newGenes);
                pop.addChromosome(possibility);
            }
        }
        
        //fill in the other chromosomes to random ones
        for (int i=0; i<populationSize-initGenes.size()-1; i++) {
            possibility = Chromosome.randomInitialChromosome(conf);
            pop.addChromosome(possibility);
        }
        
        //printPopulation(pop);
        
        Genotype population = new Genotype(conf, pop);
        
        //printPopulation(population.getPopulation());
        
        /*
         * Evolve the population. Since we don't know what the best answer
         * is going to be, we just evolve the max number of times.
         */
        for (int i=0; i<evolutionTimes; i++) {
            population.evolve();
        }
        
        return population.getFittestChromosome();
    }//findBestSolution()
    
    
    public static Collection<KSItem> mcknap(Collection<KSChoices> choices, double limit) throws Exception {
        return mcknap(choices.toArray(new KSChoices[0]), limit, 100, 100);
    }//mcknap()
    
    
    public static Collection<KSItem> mcknap(
        Collection<KSChoices> choices,
        double limit,
        final int evolutionTimes,
        final int populationSize
    ) throws Exception {
        return mcknap(choices.toArray(new KSChoices[0]), limit, evolutionTimes, populationSize);
    }
    
    
    public static Collection<KSItem> mcknap(KSChoices[] choices, double limit) throws Exception {
        return mcknap(choices, limit, 100, 100);
    }//mcknap()
    
    
    public static Collection<KSItem> mcknap(
        KSChoices[] choices,
        double limit,
        int evolutionTimes,
        int populationSize
    ) throws Exception {
        ArrayList<KSItem> result = new ArrayList<KSItem>();
        
        GAKnapsackCalculator calculator = new GAKnapsackCalculator(choices, limit);
        GAKnapsackProjectFitnessFunction fitnessFunction = new GAKnapsackProjectFitnessFunction(calculator, "/WEB-INF/config/knapsack-project.bsh");
        
        evolutionTimes = fitnessFunction.getEvolutionTimes();
        populationSize = fitnessFunction.getPopulationSize();
        
        IChromosome chromosome = findBestSolution(fitnessFunction, evolutionTimes, populationSize);
        
        IntegerGene gene = null;
        KSItem item = null;
        
        /*
        for (int i=0; i<chromosome.size(); i++) {
            gene = (IntegerGene) chromosome.getGene(i);
            item = (KSItem) gene.getApplicationData();
            result.add(item);
        }//for i
        */
        
        int k = 0;
        float projTotalCost = 0;
        float selectedTotalCost = 0;
        float projTotalProfit = 0;
        float selectedTotalProfit = 0;
        System.out.printf(" group (s/m)     cost        profit\n");
        for (int i=0; i<choices.length; i++) {
            if (choices[i].isSingle()) {
                gene = (IntegerGene) chromosome.getGene(k);
                for (int j=0; j<choices[i].getChoices().size(); j++) {
                    item = choices[i].getChoices().get(j);
                    projTotalCost += item.getCost();
                    projTotalProfit += item.getProfit();
                    if (gene.intValue()-1==j) {
                        result.add(item);
                        selectedTotalCost += item.getCost();
                        selectedTotalProfit += item.getProfit();
                        System.out.printf(" + %d   (%c)    %8.2f    %8.2f\n", i, choices[i].isSingle()?'s':'m', item.getCost(), item.getProfit());
                    } else {
                        System.out.printf(" - %d   (%c)    %8.2f    %8.2f\n", i, choices[i].isSingle()?'s':'m', item.getCost(), item.getProfit());
                    }
                }
                k++;
            } else {
                for (int j=0; j<choices[i].getChoices().size(); j++) {
                    gene = (IntegerGene) chromosome.getGene(k);
                    item = choices[i].getChoices().get(j);
                    
                    projTotalCost += item.getCost();
                    projTotalProfit += item.getProfit();
                    
                    if (gene.intValue()==1) {
                        result.add(item);
                        selectedTotalCost += item.getCost();
                        selectedTotalProfit += item.getProfit();
                        System.out.printf(" + %d   (%c)    %8.2f    %8.2f\n", i, choices[i].isSingle()?'s':'m', item.getCost(), item.getProfit());
                    } else {
                        System.out.printf(" - %d   (%c)    %8.2f    %8.2f\n", i, choices[i].isSingle()?'s':'m', item.getCost(), item.getProfit());
                    }
                    
                    k++;
                }
            }
        }//for i
        
        System.out.println("fitness : "+chromosome.getFitnessValue());
        System.out.println("limit : "+limit);
        System.out.println("total cost of all projects : "+projTotalCost);
        System.out.println("total cost of selected projects : "+selectedTotalCost);
        System.out.println("total profit of all projects : "+projTotalProfit);
        System.out.println("total profit of selected projects : "+selectedTotalProfit);
        
        return result;
    }//mcknap()
    
    
}//class GAKnapsackEngine
