package filehandling;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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


	List<Instance> instances = new ArrayList<Instance>();
	List<String> classes = new ArrayList<String>();
	List<String> attributeLabels = new ArrayList<String>();
	int numAttributes = 0;

	public CSVLoader(){		
	}
	
	public void loadFile(String fileName){

		BufferedReader fileReader = null;
		String line ="";
		String[] tokens;
		int lineCounter = 0;
		
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
			
			// this first line should contain the attribute labels
			line = fileReader.readLine();
			tokens = line.split(",");
			for ( int i=0; i<tokens.length-1; ++i){
				attributeLabels.add(tokens[i]);
			}
		
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
	 * @return the attributeLabels
	 */
	public List<String> getAttributeLabels() {
		return attributeLabels;
	}

	/**
	 * @param attributeLabels the attributeLabels to set
	 */
	public void setAttributeLabels(List<String> attributeLabels) {
		this.attributeLabels = attributeLabels;
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

