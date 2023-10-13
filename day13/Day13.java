import java.util.*;

public class Day13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> packets = new ArrayList<>();

        // Read input packets until there is no more input
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                packets.add(line);
            }
        }

        // Sort the packets based on the custom comparison logic
        packets.sort((a, b) -> comparePackets(a, b));

        // Find the indices of divider packets
        int dividerIndex1 = packets.indexOf("[[2]]");
        int dividerIndex2 = packets.indexOf("[[6]]");

        // Calculate the decoder key
        long decoderKey = (dividerIndex1 + 1L) * (dividerIndex2 + 1L);

        System.out.println("Decoder Key: " + decoderKey);
    }

    // Custom comparison function for sorting packets
    private static int comparePackets(String packet1, String packet2) {
        String[] parts1 = packet1.split(",");
        String[] parts2 = packet2.split(",");
        int i = 0;
        int j = 0;

        while (i < parts1.length && j < parts2.length) {
            String token1 = parts1[i];
            String token2 = parts2[j];
            int compareResult = compareTokens(token1, token2);

            if (compareResult != 0) {
                return compareResult;
            }

            i++;
            j++;
        }

        return Integer.compare(parts1.length, parts2.length);
    }

    // Compare two tokens within a packet
    private static int compareTokens(String token1, String token2) {
        if (token1.equals(token2)) {
            return 0;
        }

        if (token1.startsWith("[") && token2.startsWith("[")) {
            return comparePackets(token1, token2);
        } else if (token1.startsWith("[") || token2.startsWith("[")) {
            return compareTokens(token1.replace("[", "").replace("]", ""), token2.replace("[", "").replace("]", ""));
        } else {
            return Integer.compare(Integer.parseInt(token1), Integer.parseInt(token2));
        }
    }
}