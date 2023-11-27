package com.tiger.chatroom.ui;

import com.tiger.chatroom.Client;
import com.tiger.chatroom.entity.ChatRecord;
import com.tiger.chatroom.entity.CustomMessage;
import com.tiger.chatroom.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;

@Slf4j
public class ClientMainFrame extends JFrame {

    private JPanel contentPane;
    private JLabel accountLabel;
    private JButton exitButton;
    private JList userList;
    private UserListModel userListModel = new UserListModel();
    private Client client;
    private String account;
    private Map<String, DialogFrame> dialogFrameMap;


    public ClientMainFrame(String account, Client client) {
        this();
        this.client = client;
        this.account = account;
        this.accountLabel.setText(account);
        dialogFrameMap = new HashMap<>();
    }


    public ClientMainFrame() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                log.info("closing window");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                log.info("window closed");
                client.close();
                System.exit(0);
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

        userList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = userList.getSelectedIndex();
                    String user = userListModel.getElementAt(index);
                    DialogFrame dialogFrame = new DialogFrame(account, user, client);
                    dialogFrame.setVisible(true);
                    dialogFrameMap.put(user, dialogFrame);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        exitButton.addActionListener(e -> {
            client.close();
            client.start();
            ClientMainFrame.this.setVisible(false);
        });
    }


    public void updateUserList(List<String> userList) {
        userListModel.removeAll();
        userListModel.addAll(userList);
    }


    public void handleMessage(CustomMessage customMessage) {
        switch (customMessage.getType()) {
            case USER_LIST:
                String[] split = customMessage.getBody().split(",");
                List<String> list = new ArrayList<>();
                for (String user : split) {
                    list.add(user);
                }
                updateUserList(list);
                break;
            case SESSION:
                String from = customMessage.getFrom();
                if (dialogFrameMap.containsKey(from)) {
                    ChatRecord chatRecord = new ChatRecord(customMessage.getFrom(), TimeUtil.dateTimeToString(Calendar.getInstance().getTime()), customMessage.getBody());
                    dialogFrameMap.get(from).notify(chatRecord);
                }
                break;
        }
    }

    private class UserListModel extends AbstractListModel<String> {
        private List<String> list = new ArrayList<>();

        @Override
        public synchronized int getSize() {
            return list.size();
        }

        @Override
        public synchronized String getElementAt(int index) {
            // TODO Auto-generated method stub
            return list.get(index);
        }

        public synchronized void add(String user) {
            if (!list.contains(user)) {
                list.add(user);
                fireIntervalAdded(this, list.size() - 1, list.size() - 1);
            }
        }

        public synchronized void remove(String user) {
            if (list.contains(user)) {
                list.remove(user);
                fireIntervalRemoved(this, list.size() - 1, list.size() - 1);
            }
        }

        public synchronized void removeAll() {
            if(list.size() == 0){
                return;
            }
            int index1 = list.size() - 1;
            this.list.clear();
            fireIntervalRemoved(this, 0, index1);
        }

        public synchronized void addAll(List<String> list) {
            this.list.clear();
            this.list.addAll(list);
            fireContentsChanged(this, 0, list.size() - 1);
        }
    }
}



