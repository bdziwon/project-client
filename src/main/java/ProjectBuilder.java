
public class ProjectBuilder {

	private Project project;
	
	public Project Build(){
	Project project= new Project();
	return project;
	}
	
	public ProjectBuilder(){
	this.project=Build();	
	}
	
	public ProjectBuilder setTitle(String title){
	this.project.setTitle(title);
	return this;
	}
	
	public ProjectBuilder setDescription(String description){
	this.project.setDescription(description);
	return this;
	}
	
	public ProjectBuilder addUser(User user){
	this.project.addUser(user);
	return this;
	}
	
	public ProjectBuilder addIssue(Issue issue){
	this.project.addIssue(issue);
	return this;
	}
	
}
