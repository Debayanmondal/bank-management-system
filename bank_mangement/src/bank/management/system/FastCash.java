package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6, b7;
    
    String pin;

    FastCash(String pin) {
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 960, 1080);
        add(l3);

        l1 = new JLabel("SELECT WITHDRAWL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("Rs 100");
        b2 = new JButton("Rs 500");
        b3 = new JButton("Rs 1000");
        b4 = new JButton("Rs 2000");
        b5 = new JButton("Rs 5000");
        b6 = new JButton("Rs 10000");
        b7 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(275, 400, 700, 35);
        l3.add(l1);

        b1.setBounds(212, 480, 150, 35);
        l3.add(b1);

        b2.setBounds(442, 478, 150, 35);
        l3.add(b2);

        b3.setBounds(212, 520, 150, 35);
        l3.add(b3);

        b4.setBounds(442, 515, 150, 35);
        l3.add(b4);

        b5.setBounds(212, 558, 150, 35);
        l3.add(b5);

        b6.setBounds(442, 552, 150, 35);
        l3.add(b6);

        b7.setBounds(442, 589, 150, 35);
        l3.add(b7);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==b7){
            setVisible(false);
            new Transactions(pin).setVisible(true);
        }
        else{
            String amount = ((JButton)ae.getSource()).getText().substring(3);
            Conn c1= new Conn();
            try{
                ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");
                int balance=0;
                while (rs.next()){
                    if (rs.getString("type").equals("Deposit")){
                       balance += Integer.parseInt(rs.getString("amount"));
                    }
                    else{
                        balance-=Integer.parseInt(rs.getString("amount"));
                    }
                }
                if (ae.getSource()!= b7 && balance<Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null,"Insufficient Balance");
                    return;
                }
                else{
                    Date date = new Date();
                    String query = "insert into bank values('"+pin+"','"+date+"','Withdrawl','"+amount+"')";
                    c1.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null,"Rs"+amount+"Debited succesfully");

                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                }
                

            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        // try {
        //     String amount = ((JButton)ae.getSource()).getText().substring(3); //k
        //     Conn c1 = new Conn();            

        //     if (ae.getSource() == b7) {
        //         this.setVisible(false);
        //         new Transactions(pin).setVisible(true);
        //     }else{
        //         Date date = new Date();
        //         c1.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', '"+amount+"')");
        //         JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");
                    
        //         setVisible(false);
        //         new Transactions(pin).setVisible(true);
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

    }

    public static void main(String[] args) {
        new FastCash("").setVisible(true);
    }
}
