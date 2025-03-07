package trade.paradex.utils;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

@UtilityClass
public class GZIPUtils {

    public static String decode(byte[] data) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        GZIPInputStream gzipStream = new GZIPInputStream(byteStream);
        InputStreamReader reader = new InputStreamReader(gzipStream);
        BufferedReader in = new BufferedReader(reader);

        StringBuilder decompressed = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            decompressed.append(line);
        }

        return decompressed.toString();
    }
}
