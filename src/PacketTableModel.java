/*
 *	==========================================================================================
 *	PacketTableModel.java : This class extends AbstractTableModel
 *  and design my own custom table model
 *  upi: ydia530
 *  Name: Diao Yuan
 *	==========================================================================================
 */

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class PacketTableModel extends AbstractTableModel {
    private Packet[] packets; // a array of packet objects
    private boolean aBoolean; // a boolean check is destination or source
    private int ipPacketSize; // total packet size
    private double averageBytes; // the average packet size
    private String[] columnNames1 = {"Time Stamp", "Destination IP Address", "Packet size"}; // table columnNames if aBoolean
    private String[] columnNames2 ={"Time Stamp", "Host IP Address", "Packet size"}; // table columnNames if not aBoolean


    /** Constructor of the PacketTableModel
     */
    public PacketTableModel(Packet[] packets, boolean aBoolean){
        this.packets = packets;
        this.aBoolean = aBoolean;
        int total=0;  // count total size
        for(Packet packet:packets){
            total += packet.getIpPacketSize(); // calculate the total size
        }
        try{
            ipPacketSize = total;
            averageBytes = total / packets.length; // get average
        }
        catch (Exception e){
        }
    }

    /**
     *  override getRowCount
     *  get the row of table
     * @return size
     */
    @Override
    public int getRowCount() {
        int size;
        if(packets==null) size = 0;
        else{
            size = packets.length+2;
        }
        return size;
    }

    /**
     * override getColumnCount
     * get number of column
     * @return column
     */
    @Override
    public int getColumnCount() {
        return columnNames1.length;
    }

    /**
     * override getColumnName
     * get column name depend on aBoolean and col
     * @param col
     * @return string
     */
    @Override
    public String getColumnName(int col){
        if(aBoolean) return columnNames1[col];
        else return columnNames2[col];
    }

    /**
     *  get average of ip packets size
     * @return averageBytes
     */
    public double getAverageBytes() {
        return averageBytes;
    }

    /**
     * get the total of Ip packet size
     * @return ipPacketSize
     */
    public int getIpPacketSize() {
        return ipPacketSize;
    }

    /**
     * override getValueAt
     * return the value at row and col
     * @param row     // table row
     * @param col     // table column
     * @return object
     */
    @Override
    public Object getValueAt(int row, int col){
        ArrayList<Packet> packet = new ArrayList<Packet>(Arrays.asList(packets));
        if(row<packet.size()){
            Packet packet1 = packet.get(row);
            switch (col){
                case 0:
                    return packet1.getTimeStamp();
                case 1:
                    if(aBoolean)return packet1.getDestinationHost(); // if source button is selected
                    else return packet1.getSourceHost();   // if destination button is selected
                case 2:
                    return packet1.getIpPacketSize();
            }
        }
        else {
            if (row == packet.size() &&col==2){   // return total size
                return getIpPacketSize();

            }
            if (row == packet.size()+1&&col==2){ // return average size
                return getAverageBytes();
            }
        }
        return null;
    }

    /**
     * override setValueAt
     * update table cell value
     * @param aValue   new value
     * @param rowIndex   table row
     * @param columnIndex  table column
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ArrayList<Packet> packet = new ArrayList<Packet>(Arrays.asList(packets));
        Packet packet1 = packet.get(rowIndex);
        try{
        packet1.setSize(Integer.valueOf((String) aValue));}// set the new packet size
        catch (Exception e){System.out.println("Please input a valued integer!");}
        int total=0; // recount the total
        for(Packet packet2:packets){
            total += packet2.getIpPacketSize();
        }
        ipPacketSize = total; // set the new total size
        averageBytes = ipPacketSize / packets.length; // set the new average size
        fireTableCellUpdated(rowIndex, columnIndex);  // update change
        fireTableCellUpdated(getRowCount()-2,2); // update ip total size
        fireTableCellUpdated(getRowCount()-1,2); // update ip average size
        writeToFile(); // call the function to write the change to a new file
    }

    /**
     * the function write data to a new file when the table have change
     */
    public void writeToFile() {
        try {
            FileWriter fstream = new FileWriter("out.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            for (int i = 0; i < packets.length; i++) {
                out.write(i +"\t");
                out.write((packets[i].getTimeStamp())+"\t");
                out.write(String.valueOf(packets[i].getSourceHost())+"\t");
                out.write("\t");
                out.write(String.valueOf(packets[i].getDestinationHost())+"\t");
                out.write("\t");
                out.write("\t");
                out.write(String.valueOf(packets[i].getIpPacketSize())+"\t");
                out.write("\t");
                out.newLine();
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());

        }
    }

    /**
     * override isCellEditable
     * make the cell is editable
     * @param row    table row
     * @param col    table column
     * @return boolean
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        if (col ==2 && row<getRowCount()-2) return true;  // make the last column can editable
        return false;
    }

}
