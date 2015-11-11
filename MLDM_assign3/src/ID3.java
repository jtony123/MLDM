import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.Instance;
import tree.DecisionTree;

/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class ID3 {
	
	public static int i = 0;
	int atIndex = -1;
	public static String defaultClass ="Owl";
	List<String> classes;

	public ID3(){
		
	}
	
	public void setClasses(List<String> allClasses){
		classes = allClasses;
	}
	
	public DecisionTree<Object> ID3(List<Instance> instances, List<Integer> attributesToTest){
		
		DecisionTree<Object> tree;
//		Input:	Examples:set of classified examples
//				Attributes:set of attributes in the examples
//				Target:classification to be predicted
		
//		if Examples is empty then return a Default class
		if(instances.isEmpty()){
			//System.out.println("empty list");
			tree = new DecisionTree<>(defaultClass);			
			return tree;
		}
//		else if all Examples have same class then return this class
		
		else if(allSameClass(instances)){
			//System.out.println("all same class");
			String allThisClass = instances.get(0).getClassAttribute();//.getAttributes().get(4).getValue();
			tree = new DecisionTree<>(allThisClass);			
			return tree;
		}
		
//		else if all Attributes are tested then return majority class
		else if (attributesToTest.isEmpty()) {
			String majorityClass = getMajorityClass(instances);
			tree = new DecisionTree<>(majorityClass);			
			return tree;
		}
		
//		else:
//			let Best= attribute that best separatesExamples relative toTarget
//			let Tree= new decision tree with Best as root node
//			foreach value vi of Best
//				let Examplesi = subset of Examples that have Best = vi
//				let Subtree= ID3(Examplesi, Attributes-Best, Target)
//				add branch from Tree to Subtree with label vi
		
		else {
			
			i = 0;
			atIndex = 0;
			double bestOverallAttribute = 100.0;
			double bestAttributeThreshold = 0;
			
			
			// outer loop across all attributes
			//attributesToTest
			//for(;i<attributesToTest.size();++i){
			for(Integer n : attributesToTest){	
			//for(;i<4;++i){

				Collections.sort(instances, new Comparator<Instance>() {			
					@Override
					public int compare(Instance instance1, Instance instance2) {
						return (int)((Double) instance1.getAttributes().get(n).getValue()).compareTo((Double) instance2.getAttributes().get(n).getValue());
					}
				});

				double totbarncount = 0.0;
				double totsnowycount = 0.0;
				double totlongearcount = 0.0;
				double maxValue = 0.0;
				double minValue = 10.0;
				
				// each index position in the array corresponds to the same location in the
				// arraylist of classes
				int[] totalCounts = countClasses(instances);			
				
				
				for(Instance ins : instances){
					// get the range of the values of attribute n
					minValue = (double)ins.getAttributes().get(n).getValue() < minValue?(double)ins.getAttributes().get(n).getValue():minValue;
					maxValue = (double)ins.getAttributes().get(n).getValue() > maxValue?(double)ins.getAttributes().get(n).getValue():maxValue;

					// get the count of each classification in this subset of the data 
					if(ins.getAttributes().get(4).getValue().equals("BarnOwl")){
						totbarncount++;
					} else if (ins.getAttributes().get(4).getValue().equals("SnowyOwl")){
						totsnowycount++;
					} else if(ins.getAttributes().get(4).getValue().equals("LongEaredOwl")){
						totlongearcount++;
					}
				}
				double totalNumInstances = (double)instances.size();								
				double bestGainThisAttribute = 10.0;
				double thresholdThisAttribute = 0.0;
				
				int counter = 0;
				double thisValue = (double)instances.get(counter).getAttributes().get(n).getValue();
				double nextValue = (double)instances.get(counter).getAttributes().get(n).getValue();
				
				do{
					// skip past equal attribute values
					while(counter < (instances.size()-1) && nextValue == (double)instances.get(counter).getAttributes().get(n).getValue()){
						++counter;
					}					
					nextValue = (double)instances.get(counter).getAttributes().get(n).getValue();
					
					// take the midway point between the values
					double threshold = (thisValue+nextValue)/2;
					// keep count of the instances found below the threshold
					double insCount = 0.0;

					double[] belowThresholdCounts = new double[classes.size()];
					
					for(Instance ins : instances){

						if((double)ins.getAttributes().get(n).getValue() <= threshold){
							++insCount;
							
							// keep count of each class below the threshold
							for(String str : classes){
								if(ins.getClassAttribute().equals(str)){
									belowThresholdCounts[classes.indexOf(str)]++;
								}
							}							
						}			
					}
					
					double entropyBelowThreshold = 0;
					double entropyAboveThreshold = 0;
					for(int i = 0; i<classes.size();++i){
						entropyBelowThreshold += -1*(belowThresholdCounts[i]/(insCount))*log(belowThresholdCounts[i]/(insCount));
						entropyAboveThreshold += -1*(((double)totalCounts[i]-belowThresholdCounts[i])/(totalNumInstances-insCount))*log(((double)totalCounts[i]-belowThresholdCounts[i])/(totalNumInstances-insCount));
					}				
				
					double result = (entropyBelowThreshold + entropyAboveThreshold);
					if(result < bestGainThisAttribute){
						bestGainThisAttribute = result;
						thresholdThisAttribute = threshold;						
					}
					//System.out.println();
					thisValue = nextValue;
					++counter;
				} while(counter < instances.size());			
				
				if(bestGainThisAttribute <= bestOverallAttribute){
					bestAttributeThreshold = thresholdThisAttribute;
					bestOverallAttribute = bestGainThisAttribute;
					atIndex = n;
				}
				
//				System.out.println("atIndex = "+atIndex);
//				System.out.println("bestAttribute = "+bestOverallAttribute);
//				System.out.println("maxOverallAt = "+bestAttributeThreshold);
				
			} // end for
			
			//System.out.println();
			
			// we have found the attribute that best separates the data, now sort the instances by this attribute
			// so we can split it at the threshold
			Collections.sort(instances, new Comparator<Instance>() {			
				@Override
				public int compare(Instance o1, Instance o2) {
					// TODO Auto-generated method stub
					return (int)((Double) o1.getAttributes().get(atIndex).getValue()).compareTo((Double) o2.getAttributes().get(atIndex).getValue());
				}
			});
			
			// put all the instances in two separate lists, one for each side of the threshold
			List<Instance> leftList = new ArrayList<Instance>();
			List<Instance> rightList = new ArrayList<Instance>();
			for(Instance instance : instances){
				if((double)instance.getAttributes().get(atIndex).getValue() <= bestAttributeThreshold){
					leftList.add(instance);
				} else {
					rightList.add(instance);
				}
			}		
			// remove this attribute from the list before recurring the method
			attributesToTest.remove(attributesToTest.indexOf(atIndex));
			// induce the tree with this attribute as the decisionnode
			tree = new DecisionTree<>(atIndex, bestAttributeThreshold, ID3(leftList, attributesToTest),ID3(rightList, attributesToTest));			
		}
		return tree;		
	}
	
	
	private int[] countClasses(List<Instance> instances){
		int[] counters = new int[classes.size()];
		for(Instance ins : instances){
			
			for(String str : classes){
				if(ins.getClassAttribute().equals(str)){
					counters[classes.indexOf(str)]++;
				}
			}
		}	
		
//		for(int i:counters){
//			System.out.println(i);
//		}
//		System.out.println();
		return counters;
	}
	
	/**
	 * @param instances
	 * @return
	 */
	private String getMajorityClass(List<Instance> instances) {
		
		int[] counters = countClasses(instances);

		int max = 0;
		int index = 0;
		for(int i = 0;i<counters.length;++i){
			if(max<counters[i]){
				max=counters[i];
				index = i;
			}
		}
		return classes.get(index);
	}



	/**
	 * @param instances
	 * @return
	 */
	private boolean allSameClass(List<Instance> instances) {
		
		String firstClassFound = (String) instances.get(0).getClassAttribute();//.getAttributes().get(4).getValue();
		// search thru' instances until it finds a different classification to the first one
		for(Instance ins : instances){			
			//if(!ins.getAttributes().get(4).getValue().equals(firstClassFound)){
			if(!ins.getClassAttribute().equals(firstClassFound)){
				return false;				
			}
		}		
		return true;
	}



	public static double log(double d){

		if(d == 0){
			return 0.0;
		} else {
			return Math.log(d)/Math.log(2);
		}

	}
	
	
	
}// end ID3 class
