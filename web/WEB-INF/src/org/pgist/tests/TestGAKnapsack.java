package org.pgist.tests;

import java.util.Scanner;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;

import bsh.Interpreter;


/**
 * Init the PGIST system
 * @author kenny
 *
 */
public class TestGAKnapsack extends MatchingTask {
    
    
    private String script;
    

    public void setScript(String script) {
        this.script = script;
    }


    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        Scanner scanner = new Scanner(System.in);
        
        Interpreter interpreter = new Interpreter();
        
        while (true) {
            System.out.println();
            System.out.println();
            System.out.print("Press Ctrl+C to exit; or press ENTER to execute script \""+script+"\"");
            
            String line = scanner.nextLine();
            
            try {
                interpreter.source(script);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            System.out.print("------------------------------------------------------------------------");
        }//while
    }//execute()
    
    
}//class WorkflowImporter
