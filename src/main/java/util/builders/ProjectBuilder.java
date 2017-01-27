package util.builders;

import util.Issue;
import util.Project;
import util.User;

public class ProjectBuilder {

    private Project project = new Project();

    public ProjectBuilder() {
    }

    public Project build() {
        return this.project;
    }

    public ProjectBuilder setTitle(String title) {
        this.project.setTitle(title);
        return this;
    }

    public ProjectBuilder setDescription(String description) {
        this.project.setDescription(description);
        return this;
    }

    public ProjectBuilder addUser(User user) {
        this.project.addUser(user);
        return this;
    }

    public ProjectBuilder addIssue(Issue issue) {
        this.project.addIssue(issue);
        return this;
    }

}

