package trade.paradex.utils;

import com.swmansion.starknet.data.TypedData;
import com.swmansion.starknet.data.types.Felt;
import lombok.experimental.UtilityClass;
import trade.paradex.model.ParadexAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class ParadexUtils {

    public static Map<String, String> prepareHeaders(String jwt) {
        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer " + jwt);

        return headers;
    }

    public static Pair<String, String> getSignature(ParadexAccount account, String message) {
        // Convert the account address and private key to Felt types
        Felt accountAddress = Felt.fromHex(account.getAddress());
        Felt privateKey = Felt.fromHex(account.getPrivateKey());

        // Convert the message to a typed data object
        TypedData typedData = TypedData.fromJsonString(message);
        Felt messageHash = typedData.getMessageHash(accountAddress);
        String messageHashStr = messageHash.hexString();

        // Sign message hash using paradex.StarknetCurve
        String signatureValues = StarknetCurve.sign(privateKey, messageHash).toList()
                .stream()
                .map(Felt::getValue)
                .map(v -> "\"" + v + "\"")
                .collect(Collectors.joining(","));

        var signatureStr = "[" + signatureValues + "]";
        return Pair.of(signatureStr, messageHashStr);
    }
}
