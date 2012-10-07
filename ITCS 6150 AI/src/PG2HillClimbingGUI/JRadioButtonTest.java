package PG2HillClimbingGUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class JRadioButtonTest {

    // ��ͼ�����ʼ��
    private static JFrame f = new JFrame();
    private static ButtonGroup bg = new ButtonGroup();
    private static JRadioButton yes = new JRadioButton("YES");
    private static JRadioButton no = new JRadioButton("NO");

    public static void main(String[] args) {

        // ͬһ��ť��ĸ���JRadioButton�Ǿ��л����Ե�
        bg.add(yes);
        bg.add(no);

        class Listener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String selected = null;
                if (yes.isSelected()) {
                    selected = "yes";
                } else if (no.isSelected()) {
                    selected = "no";
                }

                System.out.println(selected);
            }
        }

        yes.addActionListener(new Listener());
        no.addActionListener(new Listener());

        // �����Ͽ�����ButtonGroup���ټ�
        f.setLayout(new FlowLayout());
        f.getContentPane().add(yes);
        f.getContentPane().add(no);

        // ��ʾ����
        f.setVisible(true);
    }
}