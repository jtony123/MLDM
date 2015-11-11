package data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class Instance {

	private int instanceId;
	private String classAttribute;
	private List<AttributeValue> attributes;
	private String classifiedAs;
	private String testResult;
	/**
	 * @param id
	 */
	public Instance(int id) {
		this.instanceId = id;
		attributes = new ArrayList<AttributeValue>();
	}
	
	
	
	
		/**
	 * @return the instanceId
	 */
	public int getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return the classAttribute
	 */
	public String getClassAttribute() {
		return classAttribute;
	}




	/**
	 * @param classAttribute the classAttribute to set
	 */
	public void setClassAttribute(String classAttribute) {
		this.classAttribute = classAttribute;
	}




	/**
	 * @return the attributes
	 */
	public List<AttributeValue> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<AttributeValue> attributes) {
		this.attributes = attributes;
	}

	
		/**
	 * @return the classifiedAs
	 */
	public String getClassifiedAs() {
		return classifiedAs;
	}




	/**
	 * @param classifiedAs the classifiedAs to set
	 */
	public void setClassifiedAs(String classifiedAs) {
		this.classifiedAs = classifiedAs;
	}




	/**
	 * @return the testResult
	 */
	public String getTestResult() {
		return testResult;
	}




	/**
	 * @param testResult the testResult to set
	 */
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}




		/**
	 * @param string
	 */
	public void addAttributeValue(Double d, String string) {
		
		
		AttributeValue av = new AttributeValue();
		if(d!=null){
			av.setDoubleValue(d);
		} else {
			av.setStringValue(string);
		}				
		attributes.add(av);		
	}
	
}
