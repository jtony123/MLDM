import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classifier.C45;
import data.Instance;
import filehandling.CSVLoader;
import filehandling.CSVOutput;
import tree.DecisionTree;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 *	C45Test prompts the user for a test file and a path to a folder to store
 *	the results generated.  Following user input, it retrieves the file, shuffles 
 *	the instances of the file contents, splits it 2/3 training, 1/3 testing.  It 
 *	repeats this 10 times, building a decision tree and testing each time.  It records 
 *	the correctly classified accuracy for each run, then averages them.  It outputs the 
 *	accuracies and the best performing decision tree to a csv file at the location specified
 *	by the user.  It also outputs the testing instances with the actual and predicted 
 *	classifications.
 *	
 */
public class C45Test {

	
	public static String filePath;// = "C://Users//jtony_000//Google Drive//NUIG 2015//CT475 Machine Learning Data Mining//assignments//assign_3_files//owls15.csv";
	public static String destinationFile;// = "C://Users//jtony_000//Google Drive//NUIG 2015//CT475 Machine Learning Data Mining//assignments//assign_3_files//testResults";
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		// Initialising 2 new JTextFields for the form.
		JTextField sourceFile = new JTextField(25);
		JTextField destinationFolder = new JTextField(25);
		
		// Initialising 2 new JPanels in a grid format to match each of the field names.
		JPanel input = new JPanel(new GridLayout(3, 1));
		input.add(new JLabel());
		input.add(new JLabel());
		input.add(new JLabel("Source file path Eg.  C:/myFolder/owls.csv  "));
		input.add(sourceFile);
		input.add(new JLabel("Results folder Eg.  C:/Users/results  "));
		input.add(destinationFolder);

		// Handle to the form with the title message
		int inputReceived = JOptionPane.showOptionDialog(null, 
												        input, 
												        "MLDM assign_3 :: A version of C4.5",
												        JOptionPane.OK_CANCEL_OPTION, 
												        JOptionPane.INFORMATION_MESSAGE, 
												        null, 
												        new String[]{"Run Test", "Finish"}, 
												        "default");
		
		
		// Keep accepting record details until the cancel button is clicked.
		while (inputReceived == JOptionPane.OK_OPTION){		
			
			String src = sourceFile.getText();
			String dest = destinationFolder.getText();
			if(sourceFile.getText().equals("")){
				filePath = "C://Users//jtony_000//Google Drive//NUIG 2015//CT475 Machine Learning Data Mining//assignments//assign_3_files//owls15.csv";
			} else {
				filePath = src.replace("/", "//");
			}
			
			if(destinationFolder.getText().equals("")){
				destinationFile = "C://Users//jtony_000//Google Drive//NUIG 2015//CT475 Machine Learning Data Mining//assignments//assign_3_files//testResults";
			} else { 
				destinationFile = dest.replace("/", "//");
			}
					
			// **********   main business logic   *****
			
			CSVLoader csv = new CSVLoader();
			csv.loadFile(filePath);
			double percentageTraining = 0.66;
			
			// keep a list of the accuracy percentage recorded
			List<Double> accuracies = new ArrayList<Double>();
			// record which of the decision trees performed the best
			DecisionTree<Object> bestDecisionTree = null;
			double bestAccuracy = 0;
			List<Instance> bestTestingInstances = null;
			
			for(int numTests = 0; numTests < 10; ++ numTests){
				
				// get 2 lists of instances, one for training, one for testing
				// the entire list is shuffled before splitting
				List<ArrayList<Instance>> splitInstances = csv.getSplit(percentageTraining);		
				List<Instance> trainingInstances = splitInstances.get(0);
				List<Instance> testingInstances = splitInstances.get(1);
				
				C45 c45 = new C45();		
				c45.setClasses(csv.getClasses());
				c45.setAttributeLabels(csv.getAttributeLabels());
				
				// set the number of times an attribute can be reused
				// at each level of the tree, a primitive way of 'pruning'
				int numTimesReuseEachAttribute = 1;
				
				List<Integer> attributesToTest = new ArrayList<Integer>();
				int i = csv.getNumAttributes()-1;		
				for(int j = i*numTimesReuseEachAttribute; j>=0 ;--j){
					attributesToTest.add(j%i);
				}
				
				// build the tree on the training instances
				DecisionTree<Object> dt = c45.buildTree(trainingInstances, attributesToTest);
				
				// get results for the testing set using the tree just built
				int correctCount = 0;	
				// create a list of the instances tested and the result for output
				// to a file
				List<Instance> testresult = new ArrayList<Instance>();
				for(Instance instance : testingInstances){
					
					String retrieved = (String) dt.classify(instance);
					instance.setClassifiedAs(retrieved);
					instance.setTestResult(instance.getClassAttribute().equals(retrieved)?"true":"false");
					testresult.add(instance);
					if(retrieved.equals(instance.getAttributes().get(4).getStringValue())){
						++correctCount;
					}
				}	
				double correctPercentage = (double)correctCount/testingInstances.size();
				accuracies.add(correctPercentage);
				
				if(correctPercentage > bestAccuracy){
					bestAccuracy = correctPercentage;
					bestDecisionTree = dt;
					bestTestingInstances = testingInstances;
				}
				
			}
			
			double average = 0;
			for(double d : accuracies){
				average += d;
			}
			int averageInt = (int)(average/accuracies.size()*100);
			System.out.println("Average accuracy = " +average);		
			
			// write out the results to a file
			CSVOutput csvOutput = new CSVOutput(destinationFile);
			csvOutput.writeOutResults(averageInt, accuracies, bestDecisionTree.displayTree(), bestTestingInstances);			
						
			// show a crude version of the tree in the console
			System.out.println(bestDecisionTree.displayTree());
			
			// Call the dialog box again
			inputReceived = JOptionPane.showOptionDialog(null, 
			        input, 
			        "MLDM assign_3 :: A version of C4.5",
			        JOptionPane.OK_CANCEL_OPTION, 
			        JOptionPane.INFORMATION_MESSAGE, 
			        null, 
			        new String[]{"Run Another Test", "Exit"}, 
			        "default");

		}
	}
}
