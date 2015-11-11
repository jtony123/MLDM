/**
 * 
 */
package tree;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public interface DecisionNodeInterface<T> {

//	public int getAttributeIndex();
//	public void setAttributeIndex(int attributeIndex);
	public double getThreshold();
	public void setThreshold(double threshold);
	public String getClassification();
	public void setClassification(String classification);
	/**
	 * @return the attributeLabel
	 */
	public String getAttributeLabel();

	/**
	 * @param attributeLabel the attributeLabel to set
	 */
	public void setAttributeLabel(String attributeLabel);
	public T getData();
	public void setData(T newData);
	public DecisionNodeInterface<T> getLeftChild();
	public DecisionNodeInterface<T> getRightChild();
	public void setLeftChild(DecisionNodeInterface<T> leftChild);
	public void setRightChild(DecisionNodeInterface<T> rightChild);
	public boolean hasLeftChild();
	public boolean hasRightChild();
	public boolean isLeaf();
	public DecisionNodeInterface<T> copy();
	public int getHeight();
}
