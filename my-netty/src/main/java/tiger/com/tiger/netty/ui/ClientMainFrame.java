package tiger.com.tiger.netty.ui;

import tiger.com.tiger.netty.Client;
import tiger.com.tiger.netty.common.MessageType;
import tiger.com.tiger.netty.entity.CustomMessage;
import tiger.com.tiger.netty.entity.MessageCallback;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClientMainFrame extends JFrame {

    private JPanel contentPane;

    private JLabel accountLabel;
    private JButton exitButton;
    private JList userList;
    private UserListModel userListModel = new UserListModel();
    private Client client;
    private String account;
    private MessageCallback callback;

    public ClientMainFrame(String account, Client client) {
        this();
        this.client = client;
        this.account = account;
        this.accountLabel.setText(account);
        this.callback = new MessageDisplayCallbak();
        client.setDisplayMessageCallback(this.callback);
    }


    public ClientMainFrame() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 300, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("用户:");
        lblNewLabel_1.setBounds(10, 10, 50, 25);
        contentPane.add(lblNewLabel_1);

        accountLabel = new JLabel("  ");
        accountLabel.setBounds(70, 10, 100, 25);
        accountLabel.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(accountLabel);

        exitButton = new JButton("退出");
        exitButton.setBounds(200, 10, 60, 25);
        contentPane.add(exitButton);


        JLabel lblNewLabel = new JLabel("在线用户");
        lblNewLabel.setBounds(10, 60, 60, 25);
        contentPane.add(lblNewLabel);

        userList = new JList();
        userList.setModel(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setBounds(10, 90, 280, 460);
        contentPane.add(userList);


        JSeparator separator = new JSeparator();
        separator.setBounds(0, 50, 300, 2);
        contentPane.add(separator);

        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 87, 280, 455);
        contentPane.add(scrollPane);


        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                client.close();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }




    void updateUserList(List<String> userList){
        userListModel.updateAll(userList);
    }



    class MessageDisplayCallbak implements MessageCallback {
        @Override
        public void handle(CustomMessage customMessage) {
            switch (customMessage.getType()) {
                case USER_LIST:
                    String[] split = customMessage.getBody().split(",");
                    List<String> list = new ArrayList<>();
                    for(String user:split){
                        list.add(user);
                    }
                    updateUserList(list);
                    break;
                case SESSION:

                    break;
            }
        }
    }


    private class UserListModel extends AbstractListModel<String> {
        private List<String> list = new ArrayList<>();

        @Override
        public int getSize() {

            return list.size();
        }

        @Override
        public String getElementAt(int index) {
            // TODO Auto-generated method stub
            return list.get(index);
        }

        public void add(String user) {
            if (!list.contains(user)) {
                list.add(user);
                fireIntervalAdded(user, list.size(), list.size());
            }
        }

        public void remove(String user) {
            if (list.contains(user)) {
                list.remove(user);
                fireIntervalRemoved(user, list.size(), list.size());
            }
        }

        public void updateAll(List<String> list) {
            for (String user : this.list) {
                    remove(user);
            }
            for(String user: list){
                add(user);
            }
        }
    }
}
