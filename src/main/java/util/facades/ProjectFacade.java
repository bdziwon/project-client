package util.facades;

import util.Project;
import util.User;
import util.builders.ProjectBuilder;
import util.prototypes.IssuePrototypeManager;
import java.util.ArrayList;


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
	
	public void addUser(ArrayList<User> list){
	}
}




