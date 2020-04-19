package tiger.com.tiger.netty.ui;

import com.sun.javafx.css.CalculatedValue;
import lombok.extern.slf4j.Slf4j;
import tiger.com.tiger.netty.Client;
import tiger.com.tiger.netty.common.MessageType;
import tiger.com.tiger.netty.entity.ChatRecord;
import tiger.com.tiger.netty.entity.MessageFrame;
import tiger.com.tiger.netty.util.TimeUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

@Slf4j
public class DialogFrame extends JFrame {

    private JPanel contentPane;
    private JButton sendButton;
    private JTextArea receivedMsgTextArea;
    private JEditorPane sendEditorPane;
    private String from;
    private String to;
    private Client client;

    public DialogFrame(String from, String to, Client client) {
        this();
        this.from = from;
        this.to = to;
        this.client = client;
        setTitle(to);
    }

    private DialogFrame() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        receivedMsgTextArea = new JTextArea();
        receivedMsgTextArea.setEditable(false);
        receivedMsgTextArea.setBounds(5, 5, 485, 230);
        contentPane.add(receivedMsgTextArea);
        JScrollPane scrollPane = new JScrollPane(receivedMsgTextArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(5, 5, 480, 210);
        contentPane.add(scrollPane);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 220, 500, 2);
        contentPane.add(separator);

        sendEditorPane = new JEditorPane();
        sendEditorPane.setBounds(5, 235, 485, 120);
        contentPane.add(sendEditorPane);
        JScrollPane scrollPane_1 = new JScrollPane(sendEditorPane);
        scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_1.setBounds(5, 230, 480, 100);
        contentPane.add(scrollPane_1);

        sendButton = new JButton("发送");
        sendButton.setBounds(395, 335, 90, 25);
        contentPane.add(sendButton);
        sendButton.addActionListener(new SendMessageActionListener());
    }

    public void notify(ChatRecord chatRecord) {
        receivedMsgTextArea.append(chatRecord.toString());
        receivedMsgTextArea.setCaretPosition(receivedMsgTextArea.getText().length());
    }

    class SendMessageActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = sendEditorPane.getText();
            sendEditorPane.setText("");
            log.info("send:" + message);
            MessageFrame frame = new MessageFrame();
            frame.setType((byte) MessageType.SESSION.flag);
            frame.setFrom(from.getBytes());
            frame.setTo(to.getBytes());
            frame.setBody(message.getBytes());
            client.send(frame.toBytes());
            ChatRecord chatRecord = new ChatRecord(from, TimeUtil.dateTimeToString(Calendar.getInstance().getTime()), message);
            DialogFrame.this.notify(chatRecord);
        }
    }
}
