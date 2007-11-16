package org.pgist.tests;

import java.util.Scanner;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.hibernate.Transaction;
import org.htmlparser.Parser;
import org.pgist.search.SearchHelper;
import org.pgist.web.HtmlTitleFilter;

import bsh.Interpreter;


/**
 * 
 * @author kenny
 *
 */
public class TestHtmlParser extends MatchingTask {
    
    
    private String script;
    
    
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
        
        Parser parser = new Parser();
        HtmlTitleFilter filter = new HtmlTitleFilter(true);
        
        Interpreter interpreter = new Interpreter();
        interpreter.set("parser", parser);
        interpreter.set("filter", filter);
        
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
    
    
}//class TestHtmlParser
