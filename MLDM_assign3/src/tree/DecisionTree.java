/**
 * 
 */
package tree;

import java.util.ArrayList;

import data.Instance;
import data.Position;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class DecisionTree<T> implements DecisionTreeInterface<T> {

	//private static final long serialVersionUID = 1L;
	private DecisionNodeInterface<T> root;
	//private double thresholdMarker;

	public DecisionTree()
	{
		root = null;
	} // end default constructor

	public DecisionTree(T rootData)
	{
		root = new DecisionNode<T>(rootData);
	}

	public DecisionTree(T rootData, T threshold, String attLabel, DecisionTree<T> leftTree, DecisionTree<T> rightTree)
	{
		
		privateSetTree(rootData, threshold, attLabel, leftTree, rightTree);
	} 
	
//	public DecisionTree(T rootData, DecisionTree<T> leftTree, 
//			DecisionTree<T> rightTree)
//	{
//		privateSetTree(rootData, null, null, leftTree, rightTree);
//	} 

	public void setTree(T rootData)
	{
		root = new DecisionNode<T>(rootData);
	}

	public void setTree(T rootData, DecisionTreeInterface<T> leftTree,
			DecisionTreeInterface<T> rightTree)
	{
		privateSetTree(rootData, null, null, (DecisionTree<T>)leftTree, 
				(DecisionTree<T>)rightTree);
	} // end setTree

	// 26.08
	private void privateSetTree(T rootData, T threshold, String attLabel, DecisionTree<T> leftTree, 
			DecisionTree<T> rightTree)
	{
		//thresholdMarker = (double) threshold;
		root = new DecisionNode<T>(rootData);
		root.setThreshold((double) threshold);
		root.setAttributeLabel(attLabel);

		if ((leftTree != null) && !leftTree.isEmpty())
			root.setLeftChild(leftTree.root);

		if ((rightTree != null) && !rightTree.isEmpty())
		{
			if (rightTree != leftTree)
				root.setRightChild(rightTree.root);
			else
				root.setRightChild(rightTree.root.copy());
		} // end if

		if ((leftTree != null) && (leftTree != this))
			leftTree.clear(); 

		if ((rightTree != null) && (rightTree != this))
			rightTree.clear();
	}


	private DecisionNode<T> copyNodes() // not essential
	{
		return (DecisionNode<T>)root.copy();
	} 

	public T getRootData()
	{
		T rootData = null;

		if (root != null)
			rootData = root.getData();

		return rootData;
	} 
	
	public boolean isEmpty()
	{
		return root == null;
	} 
	
	public void clear()
	{
		root = null;
	} 
	
	protected void setRootData(T rootData)
	{
		root.setData(rootData);
	} 
	
	protected void setRootNode(DecisionNodeInterface<T> rootNode)
	{
		root = rootNode;
	}
	protected DecisionNodeInterface<T> getRootNode()
	{
		return root;
	} 
	public int getHeight()
	{
		return root.getHeight();
	}


	public void inorderTraverse() 
	{
		inorderTraverse(root);
	} // end inorderTraverse

	private void inorderTraverse(DecisionNodeInterface<T> node) 
	{
		if (node != null)
		{			
			inorderTraverse(node.getLeftChild());
			System.out.println(node.getData());
			inorderTraverse(node.getRightChild());
		}
	}
	
	public void preorderTraverse() 
	{
		preorderTraverse(root);
	}

	private void preorderTraverse(DecisionNodeInterface<T> node) 
	{
		if (node != null)
		{
			System.out.println(node.getData());
			System.out.println("going left");
			preorderTraverse(node.getLeftChild());
			System.out.println("goingright");
			preorderTraverse(node.getRightChild());
			System.out.println("up a level");
		}
	} 
	
	
	
	public void displayTree() 
	{
		int depth = 0;
		displayTree(root, depth);
	} 

	private void displayTree(DecisionNodeInterface<T> node, int depth) 
	{
		if (node != null)			
		{
			++depth;
			
			displayTree(node.getLeftChild(), depth);
			
			
			String attributeLabel = null;
			if(node.getAttributeLabel()!=null){
				attributeLabel = node.getAttributeLabel();
				for(int spaces = depth*2;spaces>=0;--spaces){
					System.out.print(" ");
				}
				System.out.print(attributeLabel + " : < " + node.getThreshold());
			} else {
				for(int spaces = depth;spaces>=0;--spaces){
					System.out.print(" ");
				}
				System.out.println(node.getData());
			}
			
			
			displayTree(node.getRightChild(), depth);
		}
	}
	
	
	public T classify(Instance instance)
	{
	  return findEntry(getRootNode(), instance);
	}

	private T findEntry(DecisionNodeInterface<T> rootNode, Instance instance)
	{	// header uses BinaryNodeInterface instead of BinaryNode
		// to avoid cast of values returned from getLeftChild and
		// getRightChild
	  T result = null;
	  
	  if (rootNode != null)
	  {
		  // the root node data will contain which attribute to test
		  // the data will also contain the threshold to test at
	    T rootEntry = rootNode.getData();
	    
	    // entry will be the instance being passed in, with the data telling it which attribute
	    // to test for equality on
	    if (rootEntry instanceof Integer){
	    	
	    	//if((double)instance.getAttributes().get((int)rootEntry).getValue() <= thresholdMarker){
	    	if((double)instance.getAttributes().get((int)rootEntry).getValue() <= rootNode.getThreshold()){
	    		result = findEntry(rootNode.getLeftChild(), instance);
	    	} else {
	    		result = findEntry(rootNode.getRightChild(), instance);
	    	}
	    	
	    } else {
	    	result = rootEntry;
	    }
	  }
	  
	  return result;
	}



}