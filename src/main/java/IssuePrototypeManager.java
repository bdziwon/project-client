import java.util.HashMap;
import java.util.Map;


public class IssuePrototypeManager {

	private Map<String,Issue> issuePrototype;

	IssuePrototypeManager(Map<String,Issue> issuePrototype){
		this.issuePrototype=issuePrototype;
	}
	
	Issue getPrototype(String key){
		return this.issuePrototype.get(key);	
	}
	
	public static void main(String[] args){
		
		Map<String,Issue> map=new HashMap<String,Issue>();
		Issue issue1=new Issue(1,1,"wzor","przykladowy opis","zwykly");
		System.out.println(issue1);
		map.put("wzor1", issue1);
		IssuePrototypeManager issuePrototypeManager=new IssuePrototypeManager(map);
		System.out.println(issuePrototypeManager.getPrototype("wzor1"));
	}
	
}
