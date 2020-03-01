package tiger.com.tiger.netty.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DialogFrame extends JFrame {

    private JPanel contentPane;
    private JButton sendButton;
    private JTextArea receivedMsgTextArea;
    private JEditorPane sendEditorPane;

    public DialogFrame(String title) {
        this();
        setTitle(title);
    }

    private DialogFrame() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    }
}
