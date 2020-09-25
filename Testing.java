import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class Testing extends JFrame {

  public Testing() {
    setTitle("Scheduling Algorithms");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setSize(700, 500);
    String m = JOptionPane.showInputDialog(this, "Enter number of process(es): ");
    int numP = Integer.parseInt(m);
    while(numP < 3 || numP > 10){
      JOptionPane.showMessageDialog(this,"Sorry, the process number should be in between 3 and 10","Warning", JOptionPane.WARNING_MESSAGE);
      m = JOptionPane.showInputDialog(this, "Enter number of process(es): ");
      numP = Integer.parseInt(m);
    }

    Process[] process = new Process[numP];
    for(int i = 0; i < numP; i++){ 
      JTextField aT = new JTextField(2);
      JTextField bT = new JTextField(2);
      JTextField p = new JTextField(2);

      Object[] message = {
        "Arrival Time: ", aT,
        "Burst Time: ", bT,
        "Priority: ", p
    };

      int result = JOptionPane.showConfirmDialog(this, message, "Process " + i, JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
        while(aT.getText().equals("") || bT.getText().equals("") || p.getText().equals("")){
          if(result == JOptionPane.CANCEL_OPTION)
            return;
            JOptionPane.showMessageDialog(this,"Sorry, We have not received your input(s)","Warning",JOptionPane.WARNING_MESSAGE);     
          result = JOptionPane.showConfirmDialog(this, message, "Process " + i, JOptionPane.OK_CANCEL_OPTION);
        }
        process[i] = new Process(Integer.parseInt(aT.getText()), Integer.parseInt(bT.getText()), Integer.parseInt(p.getText()));
      } else{
        return;
      }
    }

    String msg = JOptionPane.showInputDialog(this, "Time Quantum for Round Robin: ");
    while(msg.equals("")){
      JOptionPane.showMessageDialog(this,"Sorry, We have not received your input(s)","Warning", JOptionPane.WARNING_MESSAGE);   
      msg = JOptionPane.showInputDialog(this, "Time Quantum for Round Robin: ");
    }
    int timeQuantum = Integer.parseInt(msg);

    Calculation cal = new Calculation();

    String[][] data = new String[numP][4];
    for(int i = 0; i < numP; i++){
      data[i][0] = process[i].getPName();
      data[i][1] = Integer.toString(process[i].getAT());
      data[i][2] = Integer.toString(process[i].getBT());
      data[i][3] = Integer.toString(process[i].getPriority());
    }
    String[] columnNames = {"Process", "Arrival time", "Burst Time", "Priority"};
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    JTable processTable = new JTable(data, columnNames);
    JLabel l1 = new JLabel("Process Table");
    panel.add(l1);
    panel.add(processTable.getTableHeader());
    panel.add(processTable);
    
    String[][] nPData = new String[numP][6];
    String[][] getData = cal.nPreemptive(process, "Priority");
    for(int i = 0; i < numP; i++){
      nPData[i][0] = process[i].getPName();
      nPData[i][1] = Integer.toString(process[i].getAT());
      nPData[i][2] = Integer.toString(process[i].getBT());
      nPData[i][3] = getData[i][0];
      nPData[i][4] = getData[i][1];
      nPData[i][5] = getData[i][2];
    }
    String[] calColumnNames = {"Process", "Arrival time", "Burst Time", "Finish Time", "Turnaround Time", "Waiting Time"};

    JTable nPTable = new JTable(nPData, calColumnNames);

    String[] nPName = new String[numP+1];
    for(int i = 0; i < numP; i++){
      nPName[i] = getData[i][5];
    }
    nPName[numP] = "";

    DefaultTableModel model = new DefaultTableModel(nPName,  0);

    Object[] row = new Object[numP+1];
    for(int i = 0; i < numP; i++){
      row[i] = getData[i][3];
    }
    row[numP] = getData[0][4];

    model.addRow(row);

    JTable nPGantt = new JTable(model);

    panel.add(Box.createVerticalStrut(10));
    panel.add(new JLabel("\nNon-preemptive Priority schedule: "));
    panel.add(new JLabel("Gantt Chart"));
    panel.add(nPGantt.getTableHeader());
    panel.add(nPGantt);
    panel.add(new JLabel("Result Table"));
    panel.add(nPTable.getTableHeader());
    panel.add(nPTable);

    String[][] nPData2 = new String[numP][6];
    String[][] getData2 = cal.nPreemptive(process, "SJF");
    for(int i = 0; i < numP; i++){
      nPData2[i][0] = process[i].getPName();
      nPData2[i][1] = Integer.toString(process[i].getAT());
      nPData2[i][2] = Integer.toString(process[i].getBT());
      nPData2[i][3] = getData2[i][0];
      nPData2[i][4] = getData2[i][1];
      nPData2[i][5] = getData2[i][2];
    }

    JTable nPTable2 = new JTable(nPData2, calColumnNames);

    String[] nPName2 = new String[numP+1];
    for(int i = 0; i < numP; i++){
      nPName2[i] = getData2[i][5];
    }
    nPName2[numP] = "";

    DefaultTableModel model2 = new DefaultTableModel(nPName2,  0);

    Object[] row2 = new Object[numP+1];
    for(int i = 0; i < numP; i++){
      row2[i] = getData2[i][3];
    }
    row2[numP] = getData2[0][4];

    model2.addRow(row2);

    JTable nPGantt2 = new JTable(model2);

    panel.add(Box.createVerticalStrut(10));
    panel.add(new JLabel("\nNon-preemptive SJF: "));
    panel.add(new JLabel("Gantt Chart"));
    panel.add(nPGantt2.getTableHeader());
    panel.add(nPGantt2);
    panel.add(new JLabel("Result Table"));
    panel.add(nPTable2.getTableHeader());
    panel.add(nPTable2);

    String[][] getRRData = cal.rRobin(process, timeQuantum);
    String[][] rRData = new String[numP][6];
    for(int i = 0; i < numP; i++){
      rRData[i][0] = process[i].getPName();
      rRData[i][1] = Integer.toString(process[i].getAT());
      rRData[i][2] = Integer.toString(process[i].getBT());
      rRData[i][3] = getRRData[i][0];
      rRData[i][4] = getRRData[i][1];
      rRData[i][5] = getRRData[i][2];
    }

    JTable rRTable = new JTable(rRData, calColumnNames);

    String[] rRName = new String[getRRData.length+1];
    for(int i = 0; i < rRData.length; i++){
      rRName[i] = getRRData[i][5];
    }
    rRName[getRRData.length] = "";

    DefaultTableModel rRModel = new DefaultTableModel(rRName,  0);

    Object[] rRRow = new Object[getRRData.length+1];
    for(int i = 0; i < rRData.length; i++){
      rRRow[i] = getRRData[i][3];
    }
    rRRow[getRRData.length] = getRRData[0][4];

    rRModel.addRow(rRRow);

    JTable rRGantt = new JTable(rRModel);

    panel.add(Box.createVerticalStrut(10));
    panel.add(new JLabel("Round Robin with Quantum-" + timeQuantum +" : "));
    panel.add(new JLabel("Gantt Chart"));
    panel.add(rRGantt.getTableHeader());
    panel.add(rRGantt);
    panel.add(new JLabel("Result Table"));
    panel.add(rRTable.getTableHeader());
    panel.add(rRTable);
    
    String[][] getPSJFData = cal.preemptive(process, "SJF");
    String[][] pSJFData = new String[numP][6];
    for(int i = 0; i < numP; i++){
      pSJFData[i][0] = process[i].getPName();
      pSJFData[i][1] = Integer.toString(process[i].getAT());
      pSJFData[i][2] = Integer.toString(process[i].getBT());
      pSJFData[i][3] = getPSJFData[i][0];
      pSJFData[i][4] = getPSJFData[i][1];
      pSJFData[i][5] = getPSJFData[i][2];
    }

    JTable pSJFTable = new JTable(pSJFData, calColumnNames);

    String[] pSJFName = new String[getPSJFData.length+1];
    for(int i = 0; i < pSJFData.length; i++){
      pSJFName[i] = getPSJFData[i][5];
    }
    pSJFName[getPSJFData.length] = "";

    DefaultTableModel pSJFModel = new DefaultTableModel(pSJFName,  0);

    Object[] pSJFRow = new Object[getPSJFData.length+1];
    for(int i = 0; i < pSJFData.length; i++){
      pSJFRow[i] = getPSJFData[i][3];
    }
    pSJFRow[getPSJFData.length] = getPSJFData[0][4];

    pSJFModel.addRow(pSJFRow);

    JTable pSJFGantt = new JTable(pSJFModel);

    panel.add(Box.createVerticalStrut(10));
    panel.add(new JLabel("Preemptive SJF: "));
    panel.add(new JLabel("Gantt Chart"));
    panel.add(pSJFGantt.getTableHeader());
    panel.add(pSJFGantt);
    panel.add(new JLabel("Result Table"));
    panel.add(pSJFTable.getTableHeader());
    panel.add(pSJFTable);

    
    String[][] getPPData = cal.preemptive(process, "Priority");
    String[][] pPData = new String[numP][6];
    for(int i = 0; i < numP; i++){
      pPData[i][0] = process[i].getPName();
      pPData[i][1] = Integer.toString(process[i].getAT());
      pPData[i][2] = Integer.toString(process[i].getBT());
      pPData[i][3] = getPPData[i][0];
      pPData[i][4] = getPPData[i][1];
      pPData[i][5] = getPPData[i][2];
    }

    JTable pPTable = new JTable(pPData, calColumnNames);

    String[] pPName = new String[getPPData.length+1];
    for(int i = 0; i < pPData.length; i++){
      pPName[i] = getPPData[i][5];
    }
    pPName[getPPData.length] = "";

    DefaultTableModel pPModel = new DefaultTableModel(pPName,  0);

    Object[] pPRow = new Object[getPPData.length+1];
    for(int i = 0; i < pPData.length; i++){
      pPRow[i] = getPPData[i][3];
    }
    pPRow[getPPData.length] = getPPData[0][4];

    pPModel.addRow(pPRow);

    JTable pPGantt = new JTable(pPModel);

    panel.add(Box.createVerticalStrut(10));
    panel.add(new JLabel("Preemptive Priority: "));
    panel.add(new JLabel("Gantt Chart"));
    panel.add(pPGantt.getTableHeader());
    panel.add(pPGantt);
    panel.add(new JLabel("Result Table"));
    panel.add(pPTable.getTableHeader());
    panel.add(pPTable);

    JScrollPane sp = new JScrollPane(panel);

    add(sp);
    
    setVisible(true);
  }
  public static void main(String[] args){
    new Testing();
  }
}
