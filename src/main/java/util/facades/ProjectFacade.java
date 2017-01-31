package util.facades;

import util.Project;
import util.User;
import util.builders.ProjectBuilder;
import util.prototypes.IssuePrototypeManager;
import java.util.ArrayList;


public class ProjectFacade {
	
	private ProjectBuilder projectBuilder= new ProjectBuilder();
	private IssuePrototypeManager issuePrototypeManager= new IssuePrototypeManager();

	private static ProjectFacade projectFacade = null;

	private ProjectFacade() {
		super();
	}

	public static ProjectFacade getInstance() {
		if (projectFacade == null) {
			projectFacade = new ProjectFacade();
		}
		return projectFacade;
	}



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




