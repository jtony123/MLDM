package data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 *	Instance hold all the information pertaining to each instance(line)
 *	of the dataset being analysed.
 */
public class Instance {

	private int instanceId;
	private String classAttribute;			// this instance's class as per the data set
	private List<AttributeValue> attributes;// the other attributes
	private String classifiedAs;			// output from the decision tree
	private String testResult;				// whether classAttribute matches classifiedAs
	
	/**
	 * @param id
	 */
	public Instance(int id) {
		this.instanceId = id;
		attributes = new ArrayList<AttributeValue>();
	}
	
	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getClassAttribute() {
		return classAttribute;
	}

	public void setClassAttribute(String classAttribute) {
		this.classAttribute = classAttribute;
	}

	public List<AttributeValue> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeValue> attributes) {
		this.attributes = attributes;
	}

	public String getClassifiedAs() {
		return classifiedAs;
	}

	public void setClassifiedAs(String classifiedAs) {
		this.classifiedAs = classifiedAs;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}


	public void addAttributeValue(Double d, String string) {		
		
		AttributeValue av = new AttributeValue();
		if(d!=null){
			av.setDoubleValue(d);
		} else {
			av.setStringValue(string);
		}				
		attributes.add(av);		
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.getInstanceId()+", ");
		for(AttributeValue att : this.getAttributes()){
			sb.append(att.getValue()+", ");
		}		
		sb.append(this.getClassifiedAs()+", "+this.getTestResult());
	
		return sb.toString();		
	}	
}
