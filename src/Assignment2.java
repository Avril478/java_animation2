/*
 *	==========================================================================================
 *	Assignment2.java : This class is main GUI application with one JFrame, a JPanel and a
 *  JTable. User can open file and show a table of data depend on the combobox and two
 *  ration button.
 *  upi: ydia530
 *  Name: Diao Yuan
 *	==========================================================================================
 */


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

class Assignment2 extends JFrame implements ActionListener {
    private JPanel jPanelButton; // panel for button and combobox
    private ButtonGroup group;  // a group of two ration button
    private JComboBox comboBox = new JComboBox(); // a combobox of ip address
    private JRadioButton jRadioButton1 = new JRadioButton("Source hosts", true); // source host button
    private JRadioButton jRadioButton2 = new JRadioButton("Destination hosts"); // destination host button
    private Simulator s; // a simulator object
    private Object[] srcHosts = null; // a list of source host
    private Object[] destHosts = null; // a list of destHosts
    private boolean aBoolean = true; // a boolean value when source hosts button is selected
    private JTable table; // a table for data
    private JScrollPane scrollPane; // the scrollPane contain a table
    private PacketTableModel packetTableModel = null; // an abstract table model
    private DefaultComboBoxModel model; // default combobox model


    /** constructor to initialise components
     */
    public Assignment2(String title) {
        super(title);
        setLayout(new FlowLayout());
        setSize(500, 550);
        setUpJMenu();
        setUpJRadioButton();
        setVisible(true);
    }

    /**
     * set up two radio button
     */
    private void setUpJRadioButton() {
        jPanelButton = new JPanel();
        jPanelButton.setLayout(new FlowLayout(FlowLayout.LEFT));
        jRadioButton1.setSelected(true); // button1 is selected by default
        group = new ButtonGroup();
        group.add(jRadioButton1);
        group.add(jRadioButton2);
        jPanelButton.add(jRadioButton1);
        jPanelButton.add(jRadioButton2);
        add(jPanelButton);
        jRadioButton1.addActionListener(this);
        jRadioButton2.addActionListener(this);
        setVisible(true);
        jPanelButton.add(comboBox);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = comboBox.getSelectedItem().toString();
                Packet[] data = s.getTableData(string, aBoolean);
                if (packetTableModel == null) {
                    packetTableModel = new PacketTableModel(data, aBoolean);
                    table = new JTable(packetTableModel);
                    scrollPane = new JScrollPane(table);
                    scrollPane.setVisible(true);
                    add(scrollPane);
                    setVisible(true);
                } else {
                    packetTableModel = new PacketTableModel(data, aBoolean);
                    table.setModel(packetTableModel);
                }
                TableCellRenderer colorRenderer = new ColorRenderer();
                table.setDefaultRenderer(Object.class, colorRenderer);
                repaint();

            }
        });
        comboBox.setVisible(false);
    }

    /**
     * make combobox visible and set up value
     * @param ipAddress
     */
    public void addComboBox(Object[] ipAddress) {
        model = new DefaultComboBoxModel(ipAddress);
        comboBox.setModel(model);
        comboBox.setSelectedIndex(0);
        comboBox.setVisible(true);
    }

    /**
     * set up the JMenu
     */
    private void setUpJMenu() {
        JMenu file = new JMenu("File");
        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem open = new JMenuItem("Open");
        quit.addActionListener(this);
        open.addActionListener(this);
        file.add(open);
        file.add(quit);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(file);
    }

    /**
     * listen action event and performed
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();
        if (choice.equals("Quit")) {
            System.exit(0);
        } else if (choice.equals("Open")) {
            JFileChooser fileChooser = new JFileChooser(".");
            int userChoice = fileChooser.showOpenDialog(this);
            if (userChoice == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                s = new Simulator(file);
                srcHosts = s.getUniqueSortedSourceHosts();
                destHosts = s.getUniqueSortedDestHosts();
                if (jRadioButton1.isSelected()) addComboBox(srcHosts);
                else addComboBox(destHosts);
            }
        }
        if (jRadioButton1.isSelected()) {
            if (srcHosts != null) {
                aBoolean = true;
                addComboBox(srcHosts);
            } else aBoolean = true;
        }

        if (jRadioButton2.isSelected())
            if (destHosts != null) {
                aBoolean = false;
                addComboBox(destHosts);
            } else {
                aBoolean = false;
            }
    }


    /**
     * the color renderer make the last two row have different color
     */
    private class ColorRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row == packetTableModel.getRowCount() - 2 || row == packetTableModel.getRowCount() - 1) {
                setBackground(Color.lightGray);
            } else {
                setBackground(Color.white);
            }
            return this;
        }
    }

}



