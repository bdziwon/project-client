import java.util.ArrayList;
import java.util.Scanner;


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
		//odczyt indeksu bezposrednio z konsoli, jako ¿e fasada bêdzie chyba najwy¿ej w hierarchii
		list.toString();
		Scanner sc = new Scanner(System.in);
	    int index = sc.nextInt();
	    sc.close();
		projectBuilder.addUser(list.get(index));
	}
}




