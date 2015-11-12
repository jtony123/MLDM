
package tree;

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 *	Adapted from Frank M. Carrano version of BinaryTreeInterface.
 */
public interface DecisionTreeInterface<T> {

  public T getRootData();
  public int getHeight();
  public boolean isEmpty();
  public void clear();
  public void setTree(T rootData);
  public void setTree(T rootData, 
					  DecisionTreeInterface<T> leftTree,
					  DecisionTreeInterface<T> rightTree);

}