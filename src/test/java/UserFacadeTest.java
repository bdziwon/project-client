import net.Communication;
import org.junit.Test;
import util.Credentials;
import util.User;
import util.facades.UserFacade;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lorkano on 30.01.17.
 */
public class UserFacadeTest {

    @Test
    public void registerShouldNotCreateTwoSameUsers () {
        String      result;
        Credentials credentials = new Credentials("adam","1234");
        User user = new User();
        user.setName("adam");
        user.setSurname("adam");

        UserFacade userFacade = new UserFacade();

        userFacade.connectToServer();
        Communication.getInstance().startThread();

        result = userFacade.register(credentials, user);
        assertThat(result).isEqualTo("registered");

        result = userFacade.register(credentials, user);
        assertThat(result).isNotEqualTo("registered");


    }

}
