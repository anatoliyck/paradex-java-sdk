package trade.paradex.utils;

import com.swmansion.starknet.crypto.StarknetCurveSignature;
import com.swmansion.starknet.data.types.Felt;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Arrays;

public class StarknetCurve {

    // Curve parameters
    // https://docs.starknet.io/documentation/architecture_and_concepts/Cryptography/stark-curve/
    private static final BigInteger ALPHA = BigInteger.ONE; // A
    private static final BigInteger BETA = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592307816406665"); // B
    private static final BigInteger PRIME = new BigInteger("3618502788666131213697322783095070105623107215331596699973092056135872020481"); // P
    private static final BigInteger EC_ORDER = new BigInteger("3618502788666131213697322783095070105526743751716087489154079457884512865583"); // N
    private static final BigInteger EC_GENERATOR_POINT_X = new BigInteger("874739451078007766457464989774322083649278607533249481151382481072868806602"); // Gx
    private static final BigInteger EC_GENERATOR_POINT_Y = new BigInteger("152666792071518830868575557812948353041420400780739481342941381225525861407"); // Gy

    private static final ECCurve curve;
    private static final ECPoint pointG;

    static {
        // Setup curve and generator point
        ECCurve dummy = new ECCurve.Fp(PRIME, ALPHA, BETA, EC_ORDER, BigInteger.ONE);
        ECCurve.Config configure = dummy.configure();
        configure.setCoordinateSystem(ECCurve.COORD_AFFINE);
        curve = configure.create();
        pointG = curve.createPoint(EC_GENERATOR_POINT_X, EC_GENERATOR_POINT_Y);
    }

    // Sign message hash with a private key
    public static StarknetCurveSignature sign(Felt privateKeyFelt, Felt messageHashFelt) {
        BigInteger privateKey = privateKeyFelt.getValue();
        BigInteger messageHash = messageHashFelt.getValue();
        byte[] messageHashBytes = toByteArray(fixMessageLength(messageHash));

        // Note: k generated here is different from `crypto-cpp`
        // k should be a strong cryptographical random, and not repeat
        // See: https://tools.ietf.org/html/rfc6979
        HMacDSAKCalculator hMacDSAKCalculator = new HMacDSAKCalculator(new SHA256Digest());
        ECDSASigner signer = new ECDSASigner(hMacDSAKCalculator);

        signer.init(true, createPrivateKeyParams(privateKey));
        BigInteger[] signature = signer.generateSignature(messageHashBytes);

        // Can be simplified to directly return List<BigInteger> as needed
        return new StarknetCurveSignature(new Felt(signature[0]), new Felt(signature[1]));
    }

    // Create params for private key
    public static ECPrivateKeyParameters createPrivateKeyParams(BigInteger privateKey) {
        return new ECPrivateKeyParameters(
                privateKey,
                new ECDomainParameters(curve, pointG, EC_ORDER)
        );
    }

    // Fix message hash length
    public static BigInteger fixMessageLength(BigInteger message) {
        String hashHex = message.toString(16);
        if (hashHex.length() <= 62) {
            return message;
        }
        if (hashHex.length() != 63) {
            throw new RuntimeException("InvalidHashLength with length" + hashHex.length());
        }
        return message.shiftLeft(4);
    }

    // Convert BigInteger to byte array
    public static byte[] toByteArray(BigInteger value) {
        byte[] signedValue = value.toByteArray();
        if (signedValue[0] != 0x00) {
            return signedValue;
        }
        return Arrays.copyOfRange(signedValue, 1, signedValue.length);
    }
}
