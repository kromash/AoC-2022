package org.kromash.day13;

import org.kromash.common.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Main extends Solution {

    public Main() {
        super(13);
    }

    public static void main(String[] args) {
        new Main().solve();
    }

    public String partOne() {

        int result = 0;
        int idx = 1;
        Iterator<String> iterator = readInputLines().iterator();

        while (iterator.hasNext()) {
            Packet packet_1 = new Packet(iterator.next());
            Packet packet_2 = new Packet(iterator.next());

            if (packet_1.compareTo(packet_2) <= 0) {
                result += idx;
            }

            ++idx;
            if (iterator.hasNext())
                iterator.next();
        }


        return String.valueOf(result);
    }

    public String partTwo() {

        Iterator<String> iterator = readInputLines().iterator();


        ArrayList<Packet> packets = new ArrayList<>();
        while (iterator.hasNext()) {
            packets.add(new Packet(iterator.next()));
            packets.add(new Packet(iterator.next()));

            if (iterator.hasNext())
                iterator.next();
        }
        Packet additionalPacket_1 = new Packet("[[2]]");
        Packet additionalPacket_2 = new Packet("[[6]]");
        packets.add(additionalPacket_1);
        packets.add(additionalPacket_2);

        Collections.sort(packets);
        int result = (packets.indexOf(additionalPacket_1) + 1) * (packets.indexOf(additionalPacket_2) + 1);

        return String.valueOf(result);
    }

    record Packet(String packet) implements Comparable<Packet> {

        static Packet getPacket(String string) {
            if (isNumeric(string)) {
                string = "[" + string + "]";
            }

            assert string.startsWith("[") && string.endsWith("]");

            return new Packet(string);
        }

        static boolean isNumeric(String string) {
            if (string == null) {
                return false;
            }
            try {
                Integer.parseInt(string);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }

        @Override
        public int compareTo(Packet o) {
            if (this.packet.equals(o.packet)) {
                return 0;
            }

            if (this.packet.equals("")) {
                return 1;
            }
            if (o.packet.equals("")) {
                return -1;
            }


            Iterator<String> iteratorThis = splitIntoSubPackets(this);
            Iterator<String> iteratorO = splitIntoSubPackets(o);
            int compare_result;

            while (iteratorThis.hasNext() && iteratorO.hasNext()) {
                String nextThis = iteratorThis.next();
                String nextO = iteratorO.next();


                if (isNumeric(nextThis) && isNumeric(nextO)) {
                    compare_result = Integer.compare(Integer.parseInt(nextThis), Integer.parseInt(nextO));
                    if (compare_result != 0)
                        return compare_result;
                }


                compare_result = getPacket(nextThis).compareTo(getPacket(nextO));
                if (compare_result != 0)
                    return compare_result;

            }

            if (!iteratorThis.hasNext() && !iteratorO.hasNext()) {
                return 0;
            }

            if (iteratorThis.hasNext()) {
                return 1;
            } else {
                return -1;
            }


        }

        Iterator<String> splitIntoSubPackets(Packet packet) {

            String stripped = packet.packet.substring(1, packet.packet.length() - 1);

            List<String> subPackets = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            int opened = 0;
            for (char c : stripped.toCharArray()) {


                if (c == '[') {
                    opened += 1;
                } else if (c == ']') {
                    opened -= 1;
                }

                if (c == ',' && opened == 0) {
                    subPackets.add(sb.toString());
                    sb = new StringBuilder();
                } else {
                    sb.append(c);
                }


            }
            if (!sb.isEmpty()) {
                subPackets.add(sb.toString());
            }
            return subPackets.stream().iterator();
        }
    }


}
