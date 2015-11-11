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
	private List<AttributeValue> attributes;
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
