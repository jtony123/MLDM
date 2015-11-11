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

//}
//
//
//class BinaryNode<T> implements BinaryNodeInterface<T>, java.io.Serializable
//{
  private static final long serialVersionUID = 1L; // needed for serializable objects
  
  private T data;
  
  private int attributeIndex;
  private double threshold;
  private String classification;
  
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
  } // end constructor
  
  
  
  /**
 * @return the attributeIndex
 */
public int getAttributeIndex() {
	return attributeIndex;
}

/**
 * @param attributeIndex the attributeIndex to set
 */
public void setAttributeIndex(int attributeIndex) {
	this.attributeIndex = attributeIndex;
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

public T getData()
  {
    return data;
  } // end getData
  
  public void setData(T newData)
  {
    data = newData;
  } // end setData
  
  public DecisionNodeInterface<T> getLeftChild()
  {
    return left;
  } // end getLeftChild
  
	public DecisionNodeInterface<T> getRightChild()
	{
		return right;
	} // end getRightChild

    public void setLeftChild(DecisionNodeInterface<T> leftChild)
    {
      left = (DecisionNode<T>)leftChild;
    } // end setLeftChild
  
	public void setRightChild(DecisionNodeInterface<T> rightChild)
	{
		right = (DecisionNode<T>)rightChild;
	} // end setRightChild	
	
    public boolean hasLeftChild()
    {
      return left != null;
    } // end hasLeftChild
  
	public boolean hasRightChild()
	{
		return right != null;
	} // end hasRightChild
	
    public boolean isLeaf()
    {
      return (left == null) && (right == null);
    } // end isLeaf
  
    
    
    
  	// 26.06
	public DecisionNodeInterface<T> copy()
	{
		DecisionNode<T> newRoot = new DecisionNode<T>(data);
	  
	  if (left != null)
	    newRoot.left = (DecisionNode<T>)left.copy();
	    
	  if (right != null)
	    newRoot.right = (DecisionNode<T>)right.copy();
	    
	  return newRoot;	
	} // end copy

	// 26.11
	public int getHeight()
	{
	  return getHeight(this); // call private getHeight
	} // end getHeight

	// 26.11
	private int getHeight(DecisionNode<T> node)
	{
	  int height = 0;
	  
	  if (node != null)
	    height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
	                          
	  return height;
	} // end getHeight

//	// 26.11
//	public int getNumberOfNodes()
//	{
//	  int leftNumber = 0;
//	  int rightNumber = 0;
//	  
//	  if (left != null)
//	    leftNumber = left.getNumberOfNodes();
//	    
//	  if (right != null)
//	    rightNumber = right.getNumberOfNodes();
//	    
//	  return 1 + leftNumber + rightNumber;
//	} // end getNumberOfNodes
}
