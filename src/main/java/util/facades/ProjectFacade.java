package util.facades;

import net.ServerRequest;
import org.apache.maven.settings.Server;
import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.Zips;
import org.zeroturnaround.zip.commons.FileUtils;
import util.DataPackage;
import util.Project;
import util.RuntimeDataHolder;
import zipper.ZipSender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public void sendFiles(Project selectedProject, byte[] bytes, int count) {
		ArrayList<Object> objects = new ArrayList<Object>();

		objects.add(selectedProject);
		objects.add(bytes);
		objects.add(count);

		DataPackage dataPackage = new DataPackage("save files",objects);
		ServerRequest request = new ServerRequest(dataPackage);

		RuntimeDataHolder.getInstance().getCommunication().addRequest(request);

		try {
			request.getSemaphore().acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void getFiles(Project selectedProject) {
		DataPackage 		dataPackage 	= new DataPackage("get files",selectedProject);
		ServerRequest 		request 		= new ServerRequest(dataPackage);
		ArrayList<Object> 	objects			= null;

		RuntimeDataHolder.getInstance().getCommunication().addRequest(request);

		try {
			request.getSemaphore().acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		objects = (ArrayList<Object>) request.getDataPackage().getObject();
		Project project 	= (Project) objects.get(0);
		String 	folderName 	= Integer.toString(project.getId());
		byte[] 	bytes 		= (byte[]) objects.get(1);
		int 	count 		= (Integer) objects.get(2);

		File file = new File(folderName+".zip");
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(folderName+".zip");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ZipSender.WriteBuff(fileOutputStream,bytes,count);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File folder = new File(folderName);
		try {
			FileUtils.deleteDirectory(folder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		folder.mkdir();
		ZipUtil.unpack(file, folder);

	}
}




