package trade.paradex;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import trade.paradex.model.ParadexAccount;

@Testcontainers
public class BaseIntegrationTest {

    public static final String TEST_CHAIN_ID = "test_chain";

    public static final ParadexAccount PARADEX_TEST_ACCOUNT = new ParadexAccount(
            "0x4fdb88e125e9705b7c54d08a36d534b2544ae01ec980f2e3de44dfbc91ddbe5",
            "0x6b7dc8cdb6925c36b2fd52bd7b21efbbd0b0b0210431137ba15f7eaaf361cf8"
    );

    @Container
    public static MockServerContainer MOCK_SERVER_CONTAINER = new MockServerContainer(DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-5.15.0")
    );
    public static MockServerClient MOCK_SERVER_CLIENT;

    public static ParadexClient PARADEX_CLIENT;


    @BeforeAll
    public static void beforeAll() {
        String host = MOCK_SERVER_CONTAINER.getHost();
        int port = MOCK_SERVER_CONTAINER.getServerPort();

        MOCK_SERVER_CLIENT = new MockServerClient(host, port);

        String serverURL = "http://" + host + ":" + port;
        PARADEX_CLIENT = ParadexClient.builder(serverURL, TEST_CHAIN_ID).build();
    }

    @AfterAll
    public static void afterAll() {
        MOCK_SERVER_CLIENT.close();
    }
}
