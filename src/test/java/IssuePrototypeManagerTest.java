import org.junit.Test;
import util.Issue;
import util.prototypes.IssuePrototypeManager;

import static org.assertj.core.api.Assertions.assertThat;


public class IssuePrototypeManagerTest {
    @Test
    public void shouldBuildProperObject() {
       
		Issue issue1=new Issue(1,1,"wzor","przykladowy opis","zwykly");
		IssuePrototypeManager issuePrototypeManager=new IssuePrototypeManager();
		issuePrototypeManager.putPrototype("key",issue1);

		assertThat((issue1)).isEqualTo(issuePrototypeManager.getPrototype("key"));
    }
}
