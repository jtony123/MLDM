package classifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.Instance;
import tree.DecisionTree;

/**
/**
 * @author Anthony Jackson
 * @id 11170365
 *
 *	A modified version of Quinlans ID3 algorithm such 
 *	that it handles numerical(not nominal) data.
 *	Each step of Quinlan's algorithm is included in the
 *	code below.  
 *	
 *	In each iteration of the outer loop, the algorithm seeks the 
 *	attribute that contributes the best information gain. A split
 *	point(threshold) is moved along the sorted attribute values
 *	and that point that contributes the best separation of the attribute
 *	is recorded.
 *	The information gain is calculated as the entropy(uncertainty) on the
 *	lower side of the threshold plus the entropy above the threshold point.
 *	That sum of both that gives the lowest overall uncertainty is chosen as
 *	the best split point.
 *	The buildTree is called recursively until all attributes have been used,
 *	resulting in leaf nodes with a classification for any given instance
 *	passed to it. 
 */

public class C45 {

	int atIndex = -1;
	public String defaultClass = "BarnOwl";
	List<String> classes;
	List<String> attributeLabels;

	public C45(){

	}

	public void setClasses(List<String> allClasses){
		classes = allClasses;
	}

	public void setAttributeLabels(List<String> attLabels) {		
		attributeLabels = attLabels;
	}

	public DecisionTree<Object> buildTree(List<Instance> instances, List<Integer> attributesToTest){

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
			String allThisClass = instances.get(0).getClassAttribute();;
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

			atIndex = 0;
			double bestOverallAttribute = 100.0;
			double bestAttributeThreshold = 0;


			// outer loop iterates across each row of remaining attribute values
			// to ascertain which attribute best separtes the data

			for(Integer n : attributesToTest){	


				instances = sortInstancesByAttribute(instances, n);

				double maxValue = 0.0;
				double minValue = 10.0;

				// each index position in the array corresponds to the same location in the
				// arraylist of classes
				int[] totalCounts = countClasses(instances);			


				for(Instance ins : instances){
					// get the range of the values of attribute n
					minValue = (double)ins.getAttributes().get(n).getValue() < minValue?(double)ins.getAttributes().get(n).getValue():minValue;
					maxValue = (double)ins.getAttributes().get(n).getValue() > maxValue?(double)ins.getAttributes().get(n).getValue():maxValue;

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


					// calculate the entropy on both sides of the split
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

					thisValue = nextValue;
					++counter;
				} while(counter < instances.size());			

				if(bestGainThisAttribute <= bestOverallAttribute){
					bestAttributeThreshold = thresholdThisAttribute;
					bestOverallAttribute = bestGainThisAttribute;
					atIndex = n;
				}

			}

			// we have found the attribute that best separates the data, now sort the instances by this attribute
			// so we can split it at the threshold
			instances = sortInstancesByAttribute(instances, atIndex);

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
			tree = new DecisionTree<>(atIndex, bestAttributeThreshold, attributeLabels.get(atIndex), buildTree(leftList, attributesToTest),buildTree(rightList, attributesToTest));			
		}
		return tree;		
	}


	/**
	 * Helper method for buildTree, sorts the list of instances
	 * according to the index of an attribute.
	 * 
	 * @param instances
	 * @param n - the index to be sorted on
	 */
	private List<Instance> sortInstancesByAttribute(List<Instance> instances, int n) {

		Collections.sort(instances, new Comparator<Instance>() {			
			@Override
			public int compare(Instance instance1, Instance instance2) {
				return (int)((Double) instance1.getAttributes().get(n).getValue()).compareTo((Double) instance2.getAttributes().get(n).getValue());
			}
		});
		return instances;
	}


	/**
	 * counts each of the classes in the collection
	 * 
	 * @param instances
	 * @return counts of each of the classes
	 */
	private int[] countClasses(List<Instance> instances){
		int[] counters = new int[classes.size()];
		for(Instance ins : instances){

			for(String str : classes){
				if(ins.getClassAttribute().equals(str)){
					counters[classes.indexOf(str)]++;
				}
			}
		}	
		return counters;
	}

	/**
	 * gets the class which has the majority of 
	 * instances in the collection
	 * 
	 * @param instances
	 * @return the majority class 
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
	 * checks if all the instances in the collection has the same class
	 * 
	 * @param instances
	 * @return true/false
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



	/**
	 * helper function for entropy calculation
	 * 
	 * @param double 
	 * @return the log of the double to base 2
	 */
	public double log(double d){

		if(d == 0){
			return 0.0;
		} else {
			return Math.log(d)/Math.log(2);
		}

	}

}
