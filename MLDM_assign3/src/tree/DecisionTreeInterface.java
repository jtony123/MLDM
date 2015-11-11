/**
 * 
 */
package tree;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public interface DecisionTreeInterface<T> {

//}
//
//public interface TreeInterface<T>
//{
  public T getRootData();
  public int getHeight();
  //public int getNumberOfNodes();
  public boolean isEmpty();
  public void clear();
  /** Task: Sets an existing binary tree to a new one-node binary tree.
   *  @param rootData   an object that is the data in the new tree’s root
   */
  public void setTree(T rootData);
  
  /** Task: Sets an existing binary tree to a new binary tree.
   *  @param rootData   an object that is the data in the new tree’s root
   *  @param leftTree   the left subtree of the new tree
   *  @param rightTree  the right subtree of the new tree */
  public void setTree(T rootData, DecisionTreeInterface<T> leftTree,
		  DecisionTreeInterface<T> rightTree);

}