package org.pgist.packages.knapsack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;


/**
 * 
 * @author kenny
 *
 */
public class GAKnapsackEngine {
    
    
    /**
     * The total number of times we'll let the population evolve.
     */
    private static final int MAX_ALLOWED_EVOLUTIONS = 100;
    
    private static final int MAX_POPULATION_SIZE = 100;
    
    
    /**
     * Executes the genetic algorithm to determine the minimum number of
     * items necessary to make up the given target volume. The solution will then
     * be written to the console.
     *
     * @param a_knapsackVolume the target volume for which this method is
     * attempting to produce the optimal list of items
     *
     * @throws Exception
     */
    private static IChromosome findBestSolution(GAKnapsackFitnessFunction fitnessFunction) throws Exception {
        // Start with a DefaultConfiguration, which comes setup with the most common settings.
        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
        
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
        List<Gene> sampleGenes = new ArrayList<Gene>();
        
        Gene gene = null;
        for (KSChoices choices : fitnessFunction.getChoices()) {
            for (KSItem item : choices.getChoices()) {
                gene = new IntegerGene(conf, 0, 1);
                gene.setApplicationData(item);
                sampleGenes.add(gene);
            }
        }
        
        Gene[] temp = new Gene[sampleGenes.size()];
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
        conf.setPopulationSize(MAX_POPULATION_SIZE);
        
        /*
         * Create random initial population of Chromosomes.
         */
        Genotype population = Genotype.randomInitialGenotype(conf);
        
        /*
         * Evolve the population. Since we don't know what the best answer
         * is going to be, we just evolve the max number of times.
         */
        for (int i=0; i<MAX_ALLOWED_EVOLUTIONS; i++) {
            population.evolve();
        }
        
        return population.getFittestChromosome();
    }//findBestSolution()
    
    
    /**
     * Test.
     */
    public static void main(String[] args) {
        try {
            Random random = new Random();
            
            KSChoices[] choices = new KSChoices[10];
            for (int i=0; i<choices.length; i++) {
                choices[i] = new KSChoices(i%3==0);
                for (int j=0; j<3; j++) {
                    new TempItem(choices[i], random.nextInt(100), random.nextInt(100));
                }
            }
            
            Collection<KSItem> items = mcknap(choices, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main()
    
    
    public static Collection<KSItem> mcknap(KSChoices[] choices, double limit) throws Exception {       
        ArrayList<KSItem> result = new ArrayList<KSItem>();
        
        GAKnapsackFitnessFunction fitnessFunction = new GAKnapsackFitnessFunction(choices, limit);
        
        IChromosome chromosome = findBestSolution(fitnessFunction);
        
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
        float cost0 = 0;
        float cost = 0;
        float profit0 = 0;
        float profit = 0;
        for (int i=0; i<choices.length; i++) {
            for (int j=0; j<choices[i].getChoices().size(); j++) {
                gene = (IntegerGene) chromosome.getGene(i);
                item = choices[i].getChoices().get(j);
                
                cost0 += item.getCost();
                profit0 += item.getProfit();
                
                if (gene.intValue()==1) {
                    result.add(item);
                    cost += item.getCost();
                    profit += item.getProfit();
                    System.out.printf(" + %d(%c) %f %f\n", i, choices[i].isSingle()?'s':'m', item.getCost(), item.getProfit());
                } else {
                    System.out.printf(" - %d(%c) %f %f\n", i, choices[i].isSingle()?'s':'m', item.getCost(), item.getProfit());
                }
                
                k++;
            }
        }//for i
        
        System.out.println("total cost0 ---> "+cost0);
        System.out.println("total cost ---> "+cost);
        System.out.println("total profit0 ---> "+profit0);
        System.out.println("total profit ---> "+profit);
        
        return result;
    }//mcknap()
    
    
}//class GAKnapsackEngine
