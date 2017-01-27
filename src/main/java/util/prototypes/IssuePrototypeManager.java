package util.prototypes;

import util.Issue;

import java.util.HashMap;
import java.util.Map;


public class IssuePrototypeManager {

	private Map<String,Issue> issuePrototype;

	public IssuePrototypeManager(){
		Map<String,Issue> map=new HashMap<String,Issue>();
		this.issuePrototype=map;
	}
	
	public Issue getPrototype(String key){
		return this.issuePrototype.get(key);	
	}
	
	public void putPrototype(String key, Issue issue){
		this.issuePrototype.put(key,issue);
	}
	
	
}
