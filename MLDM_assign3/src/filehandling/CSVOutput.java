/**
 * 
 */
package filehandling;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import data.AttributeValue;
import data.Instance;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class CSVOutput {
	
		public static String destinationFile;
	
	public CSVOutput(String destination){
		destinationFile = destination;
	}
	
	
	/**
	 * @param testresult 
	 * @param args
	 * @throws Exception 
	 */
	public void writeOutResults(List<Instance> testResult) {
		
		
		
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(destinationFile, "UTF-8");
			for(Instance ins : testResult){
				out.print(ins.getInstanceId()+", ");
				for(AttributeValue att : ins.getAttributes()){
					out.print(att.getValue()+", ");
				}
//							ins.getAttributes().get(0).getDoubleValue()+", "+
//							ins.getAttributes().get(1).getDoubleValue()+", "+
//							ins.getAttributes().get(2).getDoubleValue()+", "+
//							ins.getAttributes().get(3).getDoubleValue()+", "+
//							ins.getAttributes().get(4).getStringValue()+", "+
			out.println(ins.getClassifiedAs()+", "+ins.getTestResult());
			}
		} catch (Exception e) {
			// TODO return message to the user
			e.printStackTrace();
		}	finally {
			out.flush();
			out.close();
		}	
		
	}
	

}
