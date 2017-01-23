import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;


public class IssuePrototypeManagerTest {
    @Test
    public void shouldBuildProperObject() {
       
		Issue issue1=new Issue(1,1,"wzor","przykladowy opis","zwykly");
		IssuePrototypeManager issuePrototypeManager=new IssuePrototypeManager();
		issuePrototypeManager.putPrototype("key",issue1);

		assertThat((issue1)).isEqualTo(issuePrototypeManager.getPrototype("key"));
    }
}
