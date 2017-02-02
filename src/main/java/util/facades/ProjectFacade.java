package util.facades;

import net.ServerRequest;
import org.apache.maven.settings.Server;
import util.DataPackage;
import util.Project;
import util.RuntimeDataHolder;
import java.util.ArrayList;


public class ProjectFacade {

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



	public Project createProject(Project project){
		DataPackage dataPackage     = new DataPackage("insert",project);
		ServerRequest request         = new ServerRequest(dataPackage);

		RuntimeDataHolder.getInstance().getCommunication().addRequest(request);

		try {
			request.getSemaphore().acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		dataPackage = request.getDataPackage();
		project     = (Project) dataPackage.getObject();
		return project;
	}

	public Project updateProject(Project project) {
		DataPackage dataPackage = new DataPackage("update",project);
		ServerRequest request = new ServerRequest(dataPackage);

		RuntimeDataHolder.getInstance().getCommunication().addRequest(request);

		try {
			request.getSemaphore().acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		project = (Project) request.getDataPackage().getObject();
		return project;
	}

	public ArrayList<Project> getProjectList() {
	    DataPackage     dataPackage     = new DataPackage("list projects","");
        ServerRequest   request         = new ServerRequest(dataPackage);

        RuntimeDataHolder.getInstance().getCommunication().addRequest(request);

        try {
            request.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Project> projects = (ArrayList<Project>) request.getDataPackage().getObject();
        return projects;
    }

	public void removeProject(Project selectedProject) {
		DataPackage dataPackage = new DataPackage("delete",selectedProject);
		ServerRequest request = new ServerRequest(dataPackage);

		RuntimeDataHolder.getInstance().getCommunication().addRequest(request);

		try {
			request.getSemaphore().acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Project getProject(Project selectedProject) {
		DataPackage dataPackage = new DataPackage("select",selectedProject);
		ServerRequest request = new ServerRequest(dataPackage);

		RuntimeDataHolder.getInstance().getCommunication().addRequest(request);

		try {
			request.getSemaphore().acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return (Project) request.getDataPackage().getObject();
	}
}




