import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.Instance;
import tree.BinarySearchTreeR;
import tree.BinaryTree;


/**
 * 
 */

/**
 * @author Anthony Jackson
 * @id 11170365
 *
 */
public class C45Test {

	public static String filePath = "C://Users//jtony_000//Google Drive//NUIG 2015//CT475 Machine Learning Data Mining//assignments//assign_3_files//owls15.csv";
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {

		
//		
//		BinaryTree<String> lt = new BinaryTree<>("right");
//		BinaryTree<String> rt = new BinaryTree<>("fred");
		
		
		//BinaryNodeInterface<> bn = new BinaryNode<T>(rootData);
		//dt.add("fred");
//		dt.inorderTraverse();
//		System.out.println();
//		dt.preorderTraverse();
		

		CSVLoader csv = new CSVLoader(filePath);
		List<Instance> instances = csv.getInstances();
		
		ID3 id3 = new ID3();
		List<Integer> attributesToTest = new ArrayList<Integer>();
		attributesToTest.add(0);
		attributesToTest.add(1);
		attributesToTest.add(2);
		attributesToTest.add(3);
		
		BinaryTree<Object> dt = id3.ID3(instances, attributesToTest);
		//dt.preorderTraverse();
		//System.out.println(dt.getHeight());
		
		// LongEarOwl
		Instance ins = new Instance(1);
		ins.addAttributeValue(3.5, null);
		ins.addAttributeValue(5.0, null);
		ins.addAttributeValue(1.3, null);
		ins.addAttributeValue(0.3, null);
		String x = (String) dt.classify(ins);
		System.out.println(x);
		
		// SnowyOwl
		Instance ins1 = new Instance(2);
		ins1.addAttributeValue(2.9, null);
		ins1.addAttributeValue(6.9, null);
		ins1.addAttributeValue(3.6, null);
		ins1.addAttributeValue(1.3, null);
		String x1 = (String) dt.classify(ins1);
		System.out.println(x1);
		
		
		// BarnOwl
		Instance ins2 = new Instance(3);
		ins2.addAttributeValue(2.0, null);
		ins2.addAttributeValue(5.0, null);
		ins2.addAttributeValue(3.5, null);
		ins2.addAttributeValue(1.0, null);
		String x2 = (String) dt.classify(ins2);
		System.out.println(x2);
	} // end main

	
	

	
	


}
