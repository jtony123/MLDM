import java.util.ArrayList;
import java.util.List;

import data.Instance;
import data.Position;
import filehandling.CSVLoader;
import filehandling.CSVOutput;
import tree.DecisionTree;


/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class C45Test {

	public static String filePath = "C://Users//jtony_000//Google Drive//NUIG 2015//CT475 Machine Learning Data Mining//assignments//assign_3_files//owls15.csv";
	public static String destinationFile = "C://Users//jtony_000//Google Drive//NUIG 2015//CT475 Machine Learning Data Mining//assignments//assign_3_files//testResults//owls1.csv";
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {

		CSVLoader csv = new CSVLoader(filePath);
		double percentageTraining = 0.66;
		List<ArrayList<Instance>> splitInstances = csv.getSplit(percentageTraining);		
		
		List<Instance> trainingInstances = splitInstances.get(0);
		List<Instance> testingInstances = splitInstances.get(1);
		
		ID3 id3 = new ID3();
		
		id3.setClasses(csv.getClasses());
		
		List<Integer> attributesToTest = new ArrayList<Integer>();
		int i = csv.getNumAttributes()-1;
		for(;i>=0;--i){
			attributesToTest.add(i);
		}
		
	
		System.out.println();
		//dt.preorderTraverse();
		// partial training set
		
		DecisionTree<Object> dt = id3.ID3(trainingInstances, attributesToTest);
		//dt.preorderTraverse();
		System.out.println(dt.getHeight());
		int correctCount = 0;
		
		List<Instance> testresult = new ArrayList<Instance>();
		for(Instance instance : testingInstances){
			String retrieved = (String) dt.classify(instance);
			instance.setClassifiedAs(retrieved);
			instance.setTestResult(instance.getAttributes().get(4).getStringValue().equals(retrieved)?"true":"false");
			testresult.add(instance);
			if(retrieved.equals(instance.getAttributes().get(4).getStringValue())){
				++correctCount;
				//System.out.println("correctly classified " + retrieved);
			}
		}
		
		CSVOutput csvOutput = new CSVOutput(destinationFile);
		csvOutput.writeOutResults(testresult);
		
		
		double correctPercentage = (double)correctCount/testingInstances.size();
		System.out.println("percentage correct = " +correctPercentage);
		
		ArrayList<ArrayList<Position>> allrows = new ArrayList<ArrayList<Position>>();
		for(int x = 0; x < dt.getHeight();++x){
			allrows.add(new ArrayList<Position>());
		}
		dt.displayTree(allrows);
		for(ArrayList<Position> l : allrows){
			for(Position p : l){
				System.out.print("data= "+p.getData()+", depth="+p.getDepth()+", latitude="+p.getLatitude()+": ");
			}
			System.out.println();
		}

	}

}
