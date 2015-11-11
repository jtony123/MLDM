import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.Instance;
import tree.BinaryTree;

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

	public ID3(){
		
	}
	
	public BinaryTree<Object> ID3(List<Instance> instances, List<Integer> attributesToTest){
		
		BinaryTree<Object> tree;
//		Input:	Examples:set of classified examples
//				Attributes:set of attributes in the examples
//				Target:classification to be predicted
		
//		if Examples is empty then return a Default class
		if(instances.isEmpty()){
			//System.out.println("empty list");
			tree = new BinaryTree<>(defaultClass);			
			return tree;
		}
//		else if all Examples have same class then return this class
		
		else if(allSameClass(instances)){
			//System.out.println("all same class");
			String allThisClass = (String) instances.get(0).getAttributes().get(4).getValue();
			tree = new BinaryTree<>(allThisClass);			
			return tree;
		}
		
//		else if all Attributes are tested then return majority class
		else if (attributesToTest.isEmpty()) {
			String majorityClass = getMajorityClass(instances);
			tree = new BinaryTree<>(majorityClass);			
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
					public int compare(Instance o1, Instance o2) {
						// TODO Auto-generated method stub
						return (int)((Double) o1.getAttributes().get(n).getValue()).compareTo((Double) o2.getAttributes().get(n).getValue());
					}
				});

				double totbarncount = 0.0;
				double totsnowycount = 0.0;
				double totlongearcount = 0.0;
				double maxValue = 0.0;
				double minValue = 10.0;


				
				for(Instance ins : instances){
					// get the range of the values of this attribute
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
				double totalSize = (double)instances.size();
				//System.out.println("max = " + maxValue);
				//System.out.println("min = "+ minValue);
		
				
				int num = (int)Math.ceil((maxValue - minValue)/0.1);
				
				//System.out.println("num sections = "+num);
				double bestGainThisAttribute = 10.0;
				double thresholdThisAttribute = 0.0;
				

				// loop over this attribute of the instances
				int counter = 0;
				double thisValue = (double)instances.get(counter).getAttributes().get(n).getValue();
				double nextValue = (double)instances.get(counter).getAttributes().get(n).getValue();
				
				do{
					// skip past equal attribute values
					while(counter < (instances.size()-1) && nextValue == (double)instances.get(counter).getAttributes().get(n).getValue()){
						++counter;
					}
					
					nextValue = (double)instances.get(counter).getAttributes().get(n).getValue();
					//System.out.println("this value is "+thisValue);
					//System.out.println("next value is "+nextValue);
					
					double threshold = (thisValue+nextValue)/2;
					//System.out.println("threshold = "+threshold);
					double barncount = 0.0;
					double snowycount = 0.0;
					double longearcount = 0.0;
					double insCount = 0.0;

					for(Instance ins : instances){

						if((double)ins.getAttributes().get(n).getValue() <= threshold){
							++insCount;
							if(ins.getAttributes().get(4).getValue().equals("BarnOwl")){
								barncount++;
							} else if (ins.getAttributes().get(4).getValue().equals("SnowyOwl")){
								snowycount++;
							} else if(ins.getAttributes().get(4).getValue().equals("LongEaredOwl")){
								longearcount++;
							}
						}			
					}
					//System.out.println("Instance count below = "+ insCount);

					double entropy1 = -1*(longearcount/insCount)*log(longearcount/insCount);
					entropy1 += -1*(snowycount/insCount)*log(snowycount/insCount);
					entropy1 += -1*(barncount/insCount)*log(barncount/insCount);
					
					double entropy2 = -1*((totlongearcount-longearcount)/(totalSize-insCount))*log((totlongearcount-longearcount)/(totalSize-insCount));//(Math.log((totlongearcount-longearcount)/(totalSize-insCount))/Math.log(2));
					entropy2 += -1*((totsnowycount-snowycount)/(totalSize-insCount))*log((totsnowycount-snowycount)/(totalSize-insCount));
					entropy2 += -1*((totbarncount-barncount)/(totalSize-insCount))*log((totbarncount-barncount)/(totalSize-insCount));
					
					double result = entropy1 + entropy2;
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
				
				//System.out.println("atIndex = "+atIndex);
				//System.out.println("bestAttribute = "+bestOverallAttribute);
				//System.out.println("maxOverallAt = "+bestAttributeThreshold);
				
			} // end for
			
			//System.out.println();
			Collections.sort(instances, new Comparator<Instance>() {			
				@Override
				public int compare(Instance o1, Instance o2) {
					// TODO Auto-generated method stub
					return (int)((Double) o1.getAttributes().get(atIndex).getValue()).compareTo((Double) o2.getAttributes().get(atIndex).getValue());
				}
			});
			

			List<Instance> leftList = new ArrayList<Instance>();
			List<Instance> rightList = new ArrayList<Instance>();
			for(Instance instance : instances){
				if((double)instance.getAttributes().get(atIndex).getValue() <= bestAttributeThreshold){
					leftList.add(instance);
				} else {
					rightList.add(instance);
				}
			}		
			attributesToTest.remove(atIndex);
			tree = new BinaryTree<>(atIndex, bestAttributeThreshold, ID3(leftList, attributesToTest),ID3(rightList, attributesToTest));			
		}
		return tree;		
	}
	
	
	/**
	 * @param instances
	 * @return
	 */
	private String getMajorityClass(List<Instance> instances) {
		
		int barncount = 0;
		int snowycount = 0;
		int longearcount = 0;
		String[] majorityClass = {"BarnOwl", "SnowyOwl", "LongEaredOwl"};

		for(Instance ins : instances){
	
			if(ins.getAttributes().get(4).getValue().equals(majorityClass[0])){
				barncount++;
			} else if (ins.getAttributes().get(4).getValue().equals(majorityClass[1])){
				snowycount++;
			} else if(ins.getAttributes().get(4).getValue().equals(majorityClass[2])){
				longearcount++;
			}						
		}				
		int x = Math.max(Math.max(snowycount, longearcount), barncount);
		if(barncount==x){
			return majorityClass[0];
		}
		if(snowycount == x){
			return majorityClass[1];
		}		
		return majorityClass[2];
	}



	/**
	 * @param instances
	 * @return
	 */
	private boolean allSameClass(List<Instance> instances) {

		String firstClassFound = (String) instances.get(0).getAttributes().get(4).getValue();
		// search thru' instances until it finds a different classification to the first one
		for(Instance ins : instances){			
			if(!ins.getAttributes().get(4).getValue().equals(firstClassFound)){
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
