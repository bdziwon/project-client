
public class ProjectBuilder {

	private Project project;
	
<<<<<<< HEAD
	public Project build(){
=======
	public Project Build(){
>>>>>>> 1be31d98e8c135d647d63dadaae99e6ccb2646cd
	Project project= new Project();
	return this.project;
	}
	
	public ProjectBuilder(){
<<<<<<< HEAD
	this.project=build();	
=======
	this.project=Build();	
>>>>>>> 1be31d98e8c135d647d63dadaae99e6ccb2646cd
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

