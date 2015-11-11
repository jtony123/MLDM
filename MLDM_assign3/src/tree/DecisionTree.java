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

	private static final long serialVersionUID = 1L;
	private DecisionNodeInterface<T> root;
	private double thresholdMarker;

	public DecisionTree()
	{
		root = null;
	} // end default constructor

	public DecisionTree(T rootData)
	{
		root = new DecisionNode<T>(rootData);
	} // end constructor

	public DecisionTree(T rootData, T threshold, DecisionTree<T> leftTree, 
			DecisionTree<T> rightTree)
	{
		
		privateSetTree(rootData, threshold, leftTree, rightTree);
	} 
	
	public DecisionTree(T rootData, DecisionTree<T> leftTree, 
			DecisionTree<T> rightTree)
	{
		privateSetTree(rootData, null, leftTree, rightTree);
	} 

	public void setTree(T rootData)
	{
		root = new DecisionNode<T>(rootData);
	}

	public void setTree(T rootData, DecisionTreeInterface<T> leftTree,
			DecisionTreeInterface<T> rightTree)
	{
		privateSetTree(rootData, null, (DecisionTree<T>)leftTree, 
				(DecisionTree<T>)rightTree);
	} // end setTree

	// 26.08
	private void privateSetTree(T rootData, T threshold, DecisionTree<T> leftTree, 
			DecisionTree<T> rightTree)
	{
		thresholdMarker = (double) threshold;
		root = new DecisionNode<T>(rootData);

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
	
	
	
	public ArrayList<ArrayList<String>> displayTree(ArrayList<ArrayList<Position>> allrows) 
	{
		int depth = 0;
		int latitude = 0;
		displayTree(root, allrows, depth, latitude);
		return null;
	} 

	private ArrayList<ArrayList<Position>> displayTree(DecisionNodeInterface<T> node, ArrayList<ArrayList<Position>> allrows, int depth, int latitude) 
	{
		if (node != null)			
		{
			Position p = new Position(node.getData().toString(), depth, latitude);
			allrows.get(depth).add(p);
			++depth;
			displayTree(node.getLeftChild(), allrows, depth, latitude-1);
			
			displayTree(node.getRightChild(), allrows, depth, latitude+1);
		}
		return allrows;
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
	    	
	    	if((double)instance.getAttributes().get((int) rootEntry).getValue() <= thresholdMarker){
	    		result = findEntry(rootNode.getLeftChild(), instance);
	    	} else {
	    		result = findEntry(rootNode.getRightChild(), instance);
	    	}
	    	
	    } else {
	    	result = rootEntry;
	    }
	  } // end if
	  
	  return result;
	} // end findEntry

	/* (non-Javadoc)
	 * @see tree.DecisionTreeInterface#setTree(java.lang.Object, tree.DecisionTreeInterface, tree.DecisionTreeInterface)
	 */
	//@Override
	//public void setTree(T rootData, DecisionTreeInterface<T> leftTree, DecisionTreeInterface<T> rightTree) {
		// TODO Auto-generated method stub
		
	//}

} // end BinaryTree