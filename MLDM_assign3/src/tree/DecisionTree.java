/**
 * 
 */
package tree;

import java.util.LinkedList;
import java.util.Queue;

import data.Instance;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 *	Adapted from Frank M. Carrano version of BinaryTree.
 *
 *	
 */
public class DecisionTree<T> implements DecisionTreeInterface<T> {

	private DecisionNodeInterface<T> root;

	public DecisionTree()
	{
		root = null;
	}

	public DecisionTree(T rootData)
	{
		root = new DecisionNode<T>(rootData);
	}

	public DecisionTree(T rootData, T threshold, String attLabel, DecisionTree<T> leftTree, DecisionTree<T> rightTree)
	{
		
		privateSetTree(rootData, threshold, attLabel, leftTree, rightTree);
	} 
	
	public void setTree(T rootData)
	{
		root = new DecisionNode<T>(rootData);
	}

	public void setTree(T rootData, DecisionTreeInterface<T> leftTree,
			DecisionTreeInterface<T> rightTree)
	{
		privateSetTree(rootData, null, null, (DecisionTree<T>)leftTree, 
				(DecisionTree<T>)rightTree);
	}

	private void privateSetTree(T rootData, T threshold, String attLabel, DecisionTree<T> leftTree, 
			DecisionTree<T> rightTree)
	{

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
		} 

		if ((leftTree != null) && (leftTree != this))
			leftTree.clear(); 

		if ((rightTree != null) && (rightTree != this))
			rightTree.clear();
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
	
	public String displayTree() 
	{
		return displayTree(root);
	} 

	private String displayTree(DecisionNodeInterface<T> node) 
	{
		StringBuilder sb = new StringBuilder();
		Queue<DecisionNodeInterface> queue = new LinkedList<DecisionNodeInterface>();
		
		if (node != null)		
		{		
			sb.append("Overall height of tree = "+node.getHeight()+"\n\n");
			sb.append("Legend: (*) denotes a leaf node containing the classification;\n");
			sb.append("       <<*>> denotes a binary decision node with the threshold value;\n");
			sb.append("       --O-- denotes each child of the binary decision node above\n\n");
			queue.add(node);
			String line="";			
			
			while(!queue.isEmpty()){
				
				int queueSize = queue.size();
				DecisionNodeInterface<DecisionNode<T>> n = queue.remove();

				if(queueSize%2==0){ // going left

					if(n.getThreshold()>0){
						line = "<<" + n.getAttributeLabel()+" <> "+n.getThreshold()+">>";					
					} else {
						line = "("+n.getData()+")";
					}
					line += "   <--O-->   ";
					sb.append(line);
					
				} else { // going right
					
					if(n.getThreshold()>0){
						line = "<<" + n.getAttributeLabel()+" <> "+n.getThreshold()+">>";
												
					} else {
						line = "("+n.getData()+")";
					}
					sb.append(line+"\n\n");
				}
				
				if(n.hasLeftChild()){
					queue.add(n.getLeftChild());
				} 
				if(n.hasRightChild()){
					queue.add(n.getRightChild());
				}
			}					
		}
		return sb.toString();
	}
	
	public T classify(Instance instance)
	{
	  return findEntry(getRootNode(), instance);
	}

	private T findEntry(DecisionNodeInterface<T> decisionNode, Instance instance)
	{	

	  T result = null;
	  
	  if (decisionNode != null)
	  {
		  // the node data will contain which attribute to test, if not a leaf node
	    T data = decisionNode.getData();
	    
	    if (data instanceof Integer){
	    	
	    	if((double)instance.getAttributes().get((int)data).getValue() <= decisionNode.getThreshold()){
	    		result = findEntry(decisionNode.getLeftChild(), instance);
	    	} else {
	    		result = findEntry(decisionNode.getRightChild(), instance);
	    	}
	    	
	    } else {
	    	result = data;
	    }
	  }
	  
	  return result;
	}

}