package org.pgist.packages.knapsack;

import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;
import org.pgist.packages.FundingSourceKSItem;


/**
 * 
 * @author kenny
 *
 */
public class GAKnapsackCalculator {
    
    
    public static final double MAX_BOUND = 1E20;
    
    
    private KSChoices[] choices;
    
    private double limit;
    
    
    public GAKnapsackCalculator(KSChoices[] choices, double limit) {
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
    public double getTotalCost(IChromosome chromosome) {
        double totalCost = 0.0d;
        
        IntegerGene gene = null;
        KSItem item = null;
        
        /*
        for (int i=0; i<chromosome.size(); i++) {
            gene = (IntegerGene) chromosome.getGene(i);
            //item = (KSItem) gene.getApplicationData();
            System.out.println("---> "+gene.getApplicationData());
            //totalCost += gene.intValue() * item.getCost();
        }//for i
        */
        
        int k = 0;
        for (int i=0; i<choices.length; i++) {
            if (choices[i].isSingle()) {
                gene = (IntegerGene) chromosome.getGene(k);
                if (gene.intValue()>0) {
                    item = choices[i].getChoices().get(gene.intValue()-1);
                    totalCost += gene.intValue() * item.getCost();
                }
                k++;
            } else {
                for (int j=0; j<choices[i].getChoices().size(); j++) {
                    gene = (IntegerGene) chromosome.getGene(k);
                    item = choices[i].getChoices().get(j);
                    totalCost += gene.intValue() * item.getCost();
                    k++;
                }
            }
        }//for i
        
        return totalCost;
    }//getTotalCost()
    
    
    public double getTotalBenefit(IChromosome chromosome) {
        double totalBenefit = 0.0d;
        
        IntegerGene gene = null;
        KSItem item = null;
        
        int k = 0;
        for (int i=0; i<choices.length; i++) {
            if (choices[i].isSingle()) {
                gene = (IntegerGene) chromosome.getGene(k);
                if (gene.intValue()>0) {
                    item = choices[i].getChoices().get(gene.intValue()-1);
                    totalBenefit += gene.intValue() * item.getProfit();
                }
                k++;
            } else {
                for (int j=0; j<choices[i].getChoices().size(); j++) {
                    gene = (IntegerGene) chromosome.getGene(k);
                    item = choices[i].getChoices().get(j);
                    totalBenefit += gene.intValue() * item.getProfit();
                    k++;
                }
            }
        }//for i
        
        return totalBenefit;
    }//getTotalBenefit()
    
    
    public double getCostToAvgRes(IChromosome chromosome){
        double totalCostToAvgRes = 0.0;
        IntegerGene gene = null;
        FundingSourceKSItem item = null;
        
        int k = 0;
        for (int i=0; i<choices.length; i++) {
            if (choices[i].isSingle()) {
                gene = (IntegerGene) chromosome.getGene(k);
                if (gene.intValue()>0) {
                    item = (FundingSourceKSItem) choices[i].getChoices().get(gene.intValue()-1);
                    totalCostToAvgRes += gene.intValue() * item.getAvgCost();
                }
                k++;
            } else {
                for (int j=0; j<choices[i].getChoices().size(); j++) {
                    gene = (IntegerGene) chromosome.getGene(k);
                    item = (FundingSourceKSItem) choices[i].getChoices().get(j);
                    totalCostToAvgRes += gene.intValue() * item.getAvgCost();
                    k++;
                }
            }
        }//for i
        
        return totalCostToAvgRes;
    }//getCostToAvgRes()
    
    
}//class GAKnapsackCalculator
