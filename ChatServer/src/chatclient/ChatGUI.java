/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author ingim
 */
public class ChatGUI extends javax.swing.JFrame implements ChatObserver {

    /**
     * Creates new form ChatGUI
     */
    ChatClient client = new ChatClient();
    JFrame frame = new JFrame("InputDialog Example #1");

    public final void starter() {
        client.addChatObserver(this);
        client.start();
        String name = JOptionPane.showInputDialog(frame, "Enter your username");
        client.send("USER#" + name);
        this.setTitle("Logged on as: " + name);
    }

    public ChatGUI() {
        initComponents();
        jListUsers.setModel(usersModel);
        jListMessage.setModel(messageModel);
        starter();
    }

    public static DefaultListModel messageModel = new DefaultListModel();
    public static DefaultListModel usersModel = new DefaultListModel();

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListUsers = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListMessage = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jListUsers.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jListUsers);

        jListMessage.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jListMessage);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

//        try {
//            client.connect("localhost", 9999);
//        } catch (IOException ex) {
//            Logger.getLogger(ChatGUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
        String text = jTextField1.getText();
        List<String> selectedValuesList = jListUsers.getSelectedValuesList();
        if(!(selectedValuesList.isEmpty())){
            client.send("SEND#" + selectedValuesList.toString().replaceAll("[\\s\\[\\]]","") + "#" + text);
        }else{
            client.send("SEND#*#"+ text);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatGUI().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JList jListMessage;
    private javax.swing.JList jListUsers;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void RecieveMessageReady(String recieveMsg) {

        messageModel.addElement(recieveMsg);

    }

    @Override
    public void RecieveUsers(String[] recieveUsers) {
        
        usersModel.removeAllElements();
        
        for (String s : recieveUsers) {
            usersModel.addElement(s);          
        }

//        for (int i = 0; i < recieveUsers.length; i++) {
//            usersModel.addElement(recieveUsers[i]);
//        }
    }
}
