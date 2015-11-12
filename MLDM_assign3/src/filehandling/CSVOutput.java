
package filehandling;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import data.Instance;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 *	CSVOutput writes out the results of a series of tests to a csv
 *	file called result_1.csv.  Each time the test is run, the file name
 *	is incremented.
 *
 */
public class CSVOutput {
	
		public static String destinationFile;
		public static int fileVersion = 1;
		public static String fileExtension = ".csv";
	
	public CSVOutput(String destination){		
		destination = destination.trim();
		destinationFile = destination + "//result_";
	}
	
	
	/**
	 * @param averageInt 
	 * @param accuracies 
	 * @param tree 
	 * @param testresult 
	 * @param args
	 * @throws Exception 
	 */
	public void writeOutResults(int averageInt, List<Double> accuracies, String tree, List<Instance> testResult) {
			
		File file = new File(destinationFile+fileVersion+fileExtension);
		
		// find the next available filename
		while(file.exists()){			
			++fileVersion;;			
			file = new File(destinationFile+fileVersion+fileExtension);
		}
		// create the new file
		if ( !file.exists() ){
			try {
				
				file.createNewFile();
			} catch (IOException e1) {
				// TODO return an error message to the user
				e1.printStackTrace();
			}
	      }		
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(file, "UTF-8");
			out.println("Average accuracy = " + averageInt +"%");
			out.println("Accuracy values;");
			for(Double d : accuracies){				
				out.print((int)(d*100)+"%, ");
			}
			out.println();
			out.print(tree);
			out.println();
			for(Instance ins : testResult){
				out.println(ins.toString());
			}
		} catch (Exception e) {
			// TODO return an error message to the user
			e.printStackTrace();
		}	finally {
			out.flush();
			out.close();
		}	
	}
}
