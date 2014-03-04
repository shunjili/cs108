package questions;

import java.util.*;

public class category {
	private ArrayList<String> category;
	public category(){
		category = new ArrayList<String>();	
		category.add("sport");
		category.add("Human");
		category.add("life");
	}
	
	public ArrayList<String> getAllCategory(){	//put in html combo box
		return category;
	}
	
	public void addCategory(String newItem){
		category.add(newItem);
	}
}
