import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class IssuePrototypeManagerTest {
    @Test
    public void shouldBuildProperObject() {

       
		Map<String,Issue> map=new HashMap<String,Issue>();
		Issue issue1=new Issue(1,1,"title","description","priority");
		map.put("key", issue1);
		IssuePrototypeManager issuePrototypeManager=new IssuePrototypeManager(map);
		
		assertThat(issue1.equals(issuePrototypeManager.getPrototype("key")));
        
    }
}
