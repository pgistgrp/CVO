package org.pgist.tests;

import java.util.Scanner;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import bsh.Interpreter;


/**
 * Init the PGIST system
 * @author kenny
 *
 */
public class TestGAKnapsack extends MatchingTask {
    
    
    private String engine;
    
    private String fitness;
    

    public void setEngine(String engine) {
        this.engine = engine;
    }


    public void setFitness(String fitness) {
        this.fitness = fitness;
    }


    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println();
            System.out.println();
            System.out.print("Press Ctrl+C to exit; or press ENTER to execute script \""+engine+"\" and script \""+fitness+"\"");
            
            String line = scanner.nextLine();
            
            try {
                Configuration.reset();
                
                Interpreter interpreter1 = new Interpreter();
                interpreter1.source(fitness);
                
                final GAKnapsackFitnessProxy fitnessFunction = (GAKnapsackFitnessProxy) interpreter1.get("fitnessFunction");
                
                Interpreter interpreter2 = new Interpreter();
                interpreter2.set("proxy", fitnessFunction);
                interpreter2.set(
                    "fitnessFunction",
                    new FitnessFunction() {
                        public double evaluate(IChromosome chromosome) {
                            return fitnessFunction.evaluate(chromosome);
                        }//evaluate()
                    }
                );
                interpreter2.source(engine);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            System.out.print("------------------------------------------------------------------------");
        }//while
    }//execute()
    
    
}//class WorkflowImporter
