import com.model.RoleId;
import com.model.User;

public class TestBase {

    protected User constructUser(int id, RoleId roleId) {
        User user = new User();
        user.setId(id);
        user.setRoleId(roleId);

        return user;
    }

}
