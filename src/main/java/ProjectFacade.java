
public class ProjectFacade {
	
	private ProjectBuilder projectBuilder= new ProjectBuilder();
	private IssuePrototypeManager issuePrototypeManager= new IssuePrototypeManager();
	
	public Project createProject(){		
		//tworzenie pustego projektu builderem
		return projectBuilder.build();  
	}
	
	public void addIssue(String key){
		//dodawanie issue z prototypu przez buildera, (przy uzyciu klucza ma byc?)
		projectBuilder.addIssue(issuePrototypeManager.getPrototype(key));
	}
	
	public void addUser(User user){
		//dodawanie usera do projektu przez buildera, 
		//(ma byc tak? czy moze ma tu dodawac pustego usera?)
		projectBuilder.addUser(user);
	}
}




