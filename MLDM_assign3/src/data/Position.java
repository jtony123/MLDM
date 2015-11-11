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
	int depth;
	int latitude;
	
	public Position(String dat, int d, int l){
		data = dat;
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
