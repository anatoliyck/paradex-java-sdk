package trade.paradex.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import trade.paradex.model.ParadexAccount;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParadexUtilsTest {

    @ParameterizedTest
    @DisplayName("Get signature for message")
    @MethodSource("provideInputParameters")
    void testGetSignature(ParadexAccount account, String message, String expectedSignature, String expectedMessageHash) {
        Pair<String, String> data = ParadexUtils.getSignature(account, message);

        assertEquals(expectedSignature, data.getLeft());
        assertEquals(expectedMessageHash, data.getRight());
    }

    private static Stream<Arguments> provideInputParameters() {
        return Stream.of(
                Arguments.of(
                        new ParadexAccount("0x42325deda84d51a8c3709d10c4fd662e90f8da000e04341a48e439b593817ff", "0x4be6ef84a396f1c800de6c6e2bbab29a2ff729cd4a14dde6418ae2eaf809ae5"),
                        oneFieldTypeData("SUP3R_TEST"),
                        "[\"2991378666635163354791370535914842462089765885717925697092196263694608500543\",\"2774712669628078981985255070975880118080407065594429627460694212085508101064\"]",
                        "0x5bf2ce0b662b06a25fd07d2ef99551055bfd91e421a57b7f57f4ebf76f26859"
                ),
                Arguments.of(
                        new ParadexAccount("0x42325deda84d51a8c3709d10c4fd662e90f8da000e04341a48e439b593817ff", "0x4be6ef84a396f1c800de6c6e2bbab29a2ff729cd4a14dde6418ae2eaf809ae5"),
                        twoFieldTypeData("QYQ&!&&!&!&@@(!)Z_AQ+W)", Integer.MAX_VALUE),
                        "[\"3547419103756076520686614713748894238355651147168803394394991631931711709608\",\"909460591595498906016163900945018611903528527287170562144423746082570990334\"]",
                        "0x5977818978453656e3ada1172e1d6f4368dd1036c62e81a2c5931ededb32e78"
                ),
                Arguments.of(
                        new ParadexAccount("0x74427b8f874daf324ffe2c815dd7af4188d24e2186557659db4d4f3e535e2ba", "0x21ed7a56f50d6519a190f27bcaac49c21058f4bcee002ce2efa9a1cef748efc"),
                        oneFieldTypeData("#(@!#*UAJWNQEZJIZSE)!_AKQ>?<"),
                        "[\"2244029992152742049488732969098838561854847752729588390950662262421608250693\",\"1556229189191796971654739504736342771633848423843075071054923326815041650424\"]",
                        "0x5c8793886e294d82a8b16078e7e4afa1fd5164d38e73596268816f5727820a1"
                ),
                Arguments.of(
                        new ParadexAccount("0x74427b8f874daf324ffe2c815dd7af4188d24e2186557659db4d4f3e535e2ba", "0x21ed7a56f50d6519a190f27bcaac49c21058f4bcee002ce2efa9a1cef748efc"),
                        twoFieldTypeData("#############################", 333333337),
                        "[\"1944492017967360067788696211527373437905476605617428987638657449140946436929\",\"427957835923188944553480312832956580728964706777031302149455361696129492644\"]",
                        "0x267d665ff8c9463f6df2f0861abc4368ef71d981d01edc536331c82b0ccf885"
                )
        );
    }

    private static String oneFieldTypeData(String value) {
        return String.format("{\n" +
                "  \"message\": {\n" +
                "    \"someField\": \"%s\"\n" +
                "  },\n" +
                "  \"domain\": {\n" +
                "    \"name\": \"TEST_NAME\",\n" +
                "    \"chainId\": \"TEST_CHAIN\",\n" +
                "    \"version\": \"1\"\n" +
                "  },\n" +
                "  \"primaryType\": \"Request\",\n" +
                "  \"types\": {\n" +
                "    \"StarkNetDomain\": [\n" +
                "      {\n" +
                "        \"name\": \"name\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"chainId\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"version\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"Request\": [\n" +
                "      {\n" +
                "        \"name\": \"someField\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}", value);
    }

    private static String twoFieldTypeData(String value, Integer intValue) {
        return String.format("{\n" +
                "  \"message\": {\n" +
                "    \"someField\": \"%s\",\n" +
                "    \"someIntField\": %d\n" +
                "  },\n" +
                "  \"domain\": {\n" +
                "    \"name\": \"TEST_NAME\",\n" +
                "    \"chainId\": \"TEST_CHAIN\",\n" +
                "    \"version\": \"1\"\n" +
                "  },\n" +
                "  \"primaryType\": \"Request\",\n" +
                "  \"types\": {\n" +
                "    \"StarkNetDomain\": [\n" +
                "      {\n" +
                "        \"name\": \"name\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"chainId\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"version\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"Request\": [\n" +
                "      {\n" +
                "        \"name\": \"someField\",\n" +
                "        \"type\": \"felt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"someIntField\",\n" +
                "        \"type\": \"felt\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}", value, intValue);
    }
}