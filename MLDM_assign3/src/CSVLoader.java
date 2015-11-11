import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

	public CSVLoader(String fileName){

		//C:\Users\jtony_000\Google Drive\NUIG 2015\CT413 FYP\simulator_stats
		//File file = new File( "C:\\Users\\jtony_000\\Google Drive\\NUIG 2015\\CT413 FYP\\simulator_stats\\file1.csv"); 
		//File file = new File(filepath+filename+fileExtension);
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

				//Get all tokens available in line

				tokens = line.split(",");

				if (tokens.length > 0) {

					Instance instance = new Instance(lineCounter);
					
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


	
	
	

}

