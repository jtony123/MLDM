/**
 * 
 */
package data;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class Position {

	String data;
	String attributeLabel;
	int depth;
	int latitude;
	
	public Position(String dat, String aL, int d, int l){
		data = dat;
		attributeLabel = aL;
		depth = d;
		latitude = l;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the attributeLabel
	 */
	public String getAttributeLabel() {
		return attributeLabel;
	}

	/**
	 * @param attributeLabel the attributeLabel to set
	 */
	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return the latitude
	 */
	public int getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	
}
