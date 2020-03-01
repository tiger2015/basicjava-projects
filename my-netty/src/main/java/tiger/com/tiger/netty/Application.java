package tiger.com.tiger.netty;

import tiger.com.tiger.netty.ui.LoginFrame;

import java.awt.*;

public class Application {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginFrame frame = new LoginFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
