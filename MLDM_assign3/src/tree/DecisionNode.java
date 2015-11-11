/**
 * 
 */
package tree;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class DecisionNode<T> implements DecisionNodeInterface<T> {

  //private static final long serialVersionUID = 1L; // needed for serializable objects
  
  private T data;
  
  //private int attributeIndex;
  private double threshold;
  private String classification;
  private String attributeLabel;
  
  private DecisionNode<T> left;
  private DecisionNode<T> right;
  
  public DecisionNode()
  {
    this(null); // call next constructor
  } // end default constructor
  
  public DecisionNode(T dataPortion)
  {
    this(dataPortion, null, null); // call next constructor
  } // end constructor

  public DecisionNode(T dataPortion, DecisionNode<T> leftChild,
		  DecisionNode<T> rightChild)
  {
    data = dataPortion;
    
    
    left = leftChild;
    right = rightChild;
  } 


/**
 * @return the threshold
 */
public double getThreshold() {
	return threshold;
}

/**
 * @param threshold the threshold to set
 */
public void setThreshold(double threshold) {
	this.threshold = threshold;
}

/**
 * @return the classification
 */
public String getClassification() {
	return classification;
}

/**
 * @param classification the classification to set
 */
public void setClassification(String classification) {
	this.classification = classification;
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

public T getData()
  {
    return data;
  } 

  public void setData(T newData)
  {
    data = newData;
  } 

  public DecisionNodeInterface<T> getLeftChild()
  {
    return left;
  } 

	public DecisionNodeInterface<T> getRightChild()
	{
		return right;
	} 

    public void setLeftChild(DecisionNodeInterface<T> leftChild)
    {
      left = (DecisionNode<T>)leftChild;
    }

	public void setRightChild(DecisionNodeInterface<T> rightChild)
	{
		right = (DecisionNode<T>)rightChild;
	} 

    public boolean hasLeftChild()
    {
      return left != null;
    } 
    
	public boolean hasRightChild()
	{
		return right != null;
	} 
	
    public boolean isLeaf()
    {
      return (left == null) && (right == null);
    } 

	public DecisionNodeInterface<T> copy()
	{
		DecisionNode<T> newRoot = new DecisionNode<T>(data);
	  
	  if (left != null)
	    newRoot.left = (DecisionNode<T>)left.copy();
	    
	  if (right != null)
	    newRoot.right = (DecisionNode<T>)right.copy();
	    
	  return newRoot;	
	}

	public int getHeight()
	{
	  return getHeight(this); // call private getHeight
	}

	private int getHeight(DecisionNode<T> node)
	{
	  int height = 0;
	  
	  if (node != null)
	    height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
	                          
	  return height;
	}
}
