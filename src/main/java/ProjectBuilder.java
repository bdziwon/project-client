
public class ProjectBuilder {

	private Project project;
	
	Project Build(){
	Project project= new Project();
	return project;
	}
	
	ProjectBuilder(){
	this.project=Build();	
	}
	
	void SetTitle(String title){
	this.project.setTitle(title);	
	}
	
	void SetDescription(String description){
	this.project.setDescription(description);	
	}
	
	void AddUser(User user){
	this.project.addUser(user);	
	}
	
	void AddIssue(Issue issue){
	this.project.addIssue(issue);
	}
	
}
