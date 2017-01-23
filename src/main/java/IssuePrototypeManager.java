import java.util.HashMap;
import java.util.Map;


public class IssuePrototypeManager {

	private Map<String,Issue> issuePrototype;

	IssuePrototypeManager(){
		Map<String,Issue> map=new HashMap<String,Issue>();
		this.issuePrototype=map;
	}
	
	Issue getPrototype(String key){
		return this.issuePrototype.get(key);	
	}
	
	void putPrototype(String key, Issue issue){
		this.issuePrototype.put(key,issue);
	}
	
	
}
