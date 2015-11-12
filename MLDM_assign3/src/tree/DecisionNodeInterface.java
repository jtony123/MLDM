/**
 * 
 */
package tree;

/**
 * @author Anthony Jackson
 * @id 11170365
 * 
 *	Adapted from Frank M. Carrano version of BinaryNodeInterface.
 */
public interface DecisionNodeInterface<T> {

	public double getThreshold();
	public void setThreshold(double threshold);
	public String getClassification();
	public void setClassification(String classification);
	public String getAttributeLabel();
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
