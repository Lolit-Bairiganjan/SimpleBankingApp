import com.brainware.simplebankingapp.service.AuthService;
import com.brainware.simplebankingapp.service.TransactionService;

public class BackendTest {

    public static void main(String[] args) {

        AuthService auth = new AuthService();
        TransactionService ts = new TransactionService();

        System.out.println(auth.login("admin", "1234"));

        System.out.println(ts.transfer(1, 2, 100));
    }
}