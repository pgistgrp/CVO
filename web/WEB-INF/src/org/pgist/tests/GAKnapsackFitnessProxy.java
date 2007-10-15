package org.pgist.tests;

import java.util.List;
import org.jgap.IChromosome;


/**
 * 
 * @author kenny
 *
 */
interface GAKnapsackFitnessProxy {
    
    
    void setChoices(Object[] choices);
    
    void setLimit(double limit);
    
    double evaluate(IChromosome chromosome);
    
    
}//class GAKnapsackFitnessProxy

