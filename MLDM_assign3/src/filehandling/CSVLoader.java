package filehandling;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.Instance;

//import com.csvreader.CsvReader;


/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class CSVLoader {

	//CsvReader csInput = null;

	List<Instance> instances = new ArrayList<Instance>();
	List<String> classes = new ArrayList<String>();
	int numAttributes = 0;

	public CSVLoader(String fileName){


		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line ="";
		String[] tokens = line.split(",");
		int lineCounter = 0;
		
		try {
			// skip the header
			line = fileReader.readLine();	
//			String[] tokens = line.split(",");
//			for(String tok : tokens){
//				headers.add(tok);
//			}

			
			//Read the file line by line

			while ((line = fileReader.readLine()) != null) {

				tokens = line.split(",");

				if (tokens.length > 0) {

					numAttributes = tokens.length-1;
					Instance instance = new Instance(lineCounter);
					instance.setClassAttribute(tokens[tokens.length-1]);
					if(!classes.contains(instance.getClassAttribute())){
						classes.add(instance.getClassAttribute());
					}
					for ( int i=0; i<tokens.length-1; ++i){
						instance.addAttributeValue(Double.parseDouble(tokens[i]),null);
					}
					instance.addAttributeValue(null, tokens[tokens.length-1]);
					instances.add(instance);

				}
				++lineCounter;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//	for(String s:classes){
//		System.out.println("classfound "+s);
//	}
		
	}

	/**
	 * @return the instances
	 */
	public List<Instance> getInstances() {
		return instances;
	}

	/**
	 * @param instances the instances to set
	 */
	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	/**
	 * @return the classes
	 */
	public List<String> getClasses() {
		return classes;
	}

	/**
	 * @param classes the classes to set
	 */
	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	/**
	 * @return the numAttributes
	 */
	public int getNumAttributes() {
		return numAttributes;
	}

	/**
	 * @param numAttributes the numAttributes to set
	 */
	public void setNumAttributes(int numAttributes) {
		this.numAttributes = numAttributes;
	}

	public List<ArrayList<Instance>> getSplit(double trainingPercentage){
		
		ArrayList<Instance> training = new ArrayList<Instance>();
		ArrayList<Instance> testing = new ArrayList<Instance>();
		
		int numTrainingNeeded = (int)((double)instances.size()*trainingPercentage);
		
		Collections.shuffle(instances);
		
		int i = 0;
		for(; i<numTrainingNeeded; ++i){			
			training.add(instances.get(i));
		}
		for(;i<instances.size();++i){
			testing.add(instances.get(i));
		}
		List<ArrayList<Instance>> bothLists = new ArrayList<>();
		bothLists.add(training);
		bothLists.add(testing);
		
		
		return bothLists;
	}
	
	
	
	
	

}

