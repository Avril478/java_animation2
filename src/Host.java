/*
 *	==========================================================================================
 *	Host.java : This class implements Comparable interface and get a host with the
 *  given ip address (of type String)
 *  upi: ydia530
 *  Name: Diao Yuan
 *	==========================================================================================
 */


import java.util.Scanner;

class Host implements Comparable<Host>{
    private String ip; // a string variable of ip Address

    /** Constructor of the Host
     */
    public Host(String ip){
        super();
        this.ip = ip;
    }

    /**
     *
     * @return a ip address
     */
    public String toString(){
        return ip;
    }

    /**
     *
     * @param other
     * @return an integer value.
     */
    public int compareTo(Host other)
    {
        return toNumeric(ip).compareTo(toNumeric(other.ip));
    }

    /**
     *
     * @param ip
     * @return a lone value
     * */
    static Long toNumeric(String ip) {
        Scanner sc = new Scanner(ip).useDelimiter("\\.");
        Long l = (sc.nextLong() << 24) + (sc.nextLong() << 16) + (sc.nextLong() << 8)
                + (sc.nextLong());

        sc.close();
        return l;
    }

}
