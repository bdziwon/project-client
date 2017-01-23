import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Bartłomiej Dziwoń on 23.01.2017.
 */
public class ProjectBuilderTest {
    @Test
    public void shouldBuildProperObject() {
        ProjectBuilder projectBuilder = new ProjectBuilder();
        Project p = projectBuilder.setTitle("title").setDescription("description").Build();
        assertThat(p.getTitle()).isEqualTo("title");
        assertThat(p.getDescription()).isEqualTo("description");

    }
}
