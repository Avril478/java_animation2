/*
 *	==========================================================================================
 *	Packet.java ：The class store information regarding to the source host,
 *  destination host, time stamp and ip packet size.
 *  upi: ydia530
 *  Name: Diao Yuan
 *	==========================================================================================
 */

public class Packet {
    private String source=""; // a string for packet source host.
    private String dest=""; // a string for destination host.
    private double timestamp=0; // a double for packet timestamp
    private int size=0; // a int for packet size

    /** Constructor of the AnimationPanel
     *  takes a string object as a parameter and set up instance variables defined above.
     */
    public Packet(String text) {

        String fields[] = text.split("\t", -1);
        if (fields.length >= 8) {
            source = fields[2].trim();
            dest = fields[4].trim();
            timestamp = parseAsNumber(fields[1]);
            size = (int) parseAsNumber(fields[7]);
        }
    }

    /**
     * take a string parse to double.
     * @param string
     * @return number
     */
    public double parseAsNumber(String string) {
        try {
            double number = Double.parseDouble(string);
            return number;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * get source host
     * @return source
     */
    public String getSourceHost() {
        return source;
    }

    /**
     * get destination host
     * @return dest
     */
    public String getDestinationHost() {
        return dest;
    }

    /**
     * get timestamp
     * @return timestamp
     */
    public double getTimeStamp() {
        return timestamp;
    }

    /**
     * get ip packet size
     * @return size
     */
    public int getIpPacketSize() {
        return size;
    }

    /**
     * set the packet size
     * @param size  the new size of packet
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * set source host of packet
     * @param source the new source of the packet
     */
    public void setSourceHost(String source) {
        this.source = source;
    }

    /**
     * set destination host of packet
     * @param dest  the new destination of packet
     */
    public void setDestinationHost(String dest) {
        this.dest = dest;
    }

    /**
     * set timestamp of packet
     * @param timestamp   the new timestamp of packet
     */
    public void setTimeStamp(double timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * set ip packet size
     * @param size   the new size of ip packet
     */
    public void setIpPacketSize(int size) {
        this.size = size;
    }

    /**
     * return a string represent a packet.
     */
    public String toString() {
        return String.format("src=%s, dest=%s, time=%.2f, size=%d", source,
                dest, timestamp, size);
    }

}