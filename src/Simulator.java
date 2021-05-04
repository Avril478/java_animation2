/*
 *	==========================================================================================
 *	Simulator.java : The class takes a File object as a parameter and reads the content
 *  in the text file to create an ArrayList of valid Packet objects
 *  upi: ydia530
 *  Name: Diao Yuan
 *	==========================================================================================
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Simulator {
    private ArrayList<Packet> resultList = new ArrayList<Packet>(); // a array list of packet.

    /** Constructor of the Simulator
     */
    public Simulator(File file){

        try{
            Scanner sc = new Scanner(file); // get file input
            while (sc.hasNext()){
                String string = sc.nextLine();
                Packet p1 = new Packet(string);
                String regExpStr = "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d?)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d?)";
                Pattern pattern = Pattern.compile(regExpStr);
                Matcher matcher = pattern.matcher(p1.getSourceHost());
                if(matcher.matches()) resultList.add(p1);} //use pattern and matcher get valid packet object
            }
        catch (IOException e){
            System.out.println(String.format("java.io.FileNotFoundException: %s (No such file or directory)",file));
        }
    }

    /**
     * return a list of validPackets
     * @return resultList
     */
    public ArrayList<Packet> getValidPackets(){
        return resultList;
    }

    /**
     *  get a unique sorted source hosts list
     * @return list1
     */
    public Object[] getUniqueSortedSourceHosts(){
        ArrayList<Packet> list= getValidPackets();
        ArrayList<String> strings = new ArrayList<String>();
        for(Packet p :list){
            String src = p.getSourceHost();
            strings.add(src);
        }
        HashSet<String> unique = new HashSet<>(strings);
        Object[] list1= new Object[unique.size()];
        Object[] strings1= unique.toArray();
        for(int i = 0;i<strings1.length;i++){
            Host h = new Host((String) strings1[i]);
            list1[i]=h;
        }
        Arrays.sort(list1);
        return list1;
    }

    /**
     * get a unique sorted destination hosts list
     * @return list1
     */
    public Object[] getUniqueSortedDestHosts(){
        ArrayList<Packet> list= getValidPackets();
        ArrayList<String> strings = new ArrayList<String>();
        for(Packet p :list){
            String src = p.getDestinationHost();
            strings.add(src);
        }
        HashSet<String> unique = new HashSet<>(strings);
        Object[] list1= new Object[unique.size()];
        Object[] strings1= unique.toArray();
        for(int i = 0;i<strings1.length;i++){
            Host h = new Host((String) strings1[i]);
            list1[i]=h;
        }
        Arrays.sort(list1);
        return list1;
    }


    /**
     * get a list of packet depend on specific ip address and a boolean
     * if boolean return a source host list else return a destination list
     * @param ipAddress
     * @param isSrcHost
     * @return list
     */
    public Packet[] getTableData(String ipAddress ,boolean isSrcHost){
        ArrayList<Packet> packets= getValidPackets(); // get valid packets
        if(isSrcHost){
            ArrayList<Packet> isSrcHostList = new ArrayList<Packet>();
            for(Packet p:packets){

                if(p.getSourceHost().equals(ipAddress)){
                    isSrcHostList.add(p);
                }
            }
            Packet[] list = new Packet[isSrcHostList.size()];
            for(int i=0;i<isSrcHostList.size();i++) list[i] = isSrcHostList.get(i);
            return list;
        }
        else{
            ArrayList<Packet> isDesList = new ArrayList<Packet>();
            for(Packet p:packets){
                if(p.getDestinationHost().equals(ipAddress)){
                    isDesList.add(p);
                }
            }
            Packet[] list = new Packet[isDesList.size()];
            for(int i=0;i<isDesList.size();i++) list[i] = isDesList.get(i);
            return list;
        }

    }


}
