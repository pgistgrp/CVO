package org.pgist.tests;

import java.util.Scanner;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Transaction;
import org.pgist.search.SearchHelper;

import bsh.Interpreter;


/**
 * 
 * @author kenny
 *
 */
public class TestLucene extends MatchingTask {
    
    
    private String indexPath;
    
    private String script;
    
    
    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }


    public void setScript(String script) {
        this.script = script;
    }


    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
    }//execute()
    
    
    private void test() throws Exception {
        Transaction tx = null;
        
        Scanner scanner = new Scanner(System.in);
        
        SearchHelper searchHelper = new SearchHelper();
        searchHelper.setContextPath("");
        searchHelper.setIndexPath(indexPath);
        
        Interpreter interpreter = new Interpreter();
        interpreter.set("searchHelper", searchHelper);
        
        while (true) {
            System.out.println();
            System.out.println();
            System.out.print("Press Ctrl+C to exit; or press ENTER to execute script \""+script+"\"");
            
            scanner.nextLine();
            
            try {
                interpreter.source(script);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            System.out.print("------------------------------------------------------------------------");
        }//while
    }//test()
    
    
}//class TestLucene
