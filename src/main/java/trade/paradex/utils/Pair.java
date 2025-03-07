package trade.paradex.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Pair<T1, T2> {

    private final T1 left;
    private final T2 right;

}
