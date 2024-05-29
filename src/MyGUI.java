import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MyGUI extends JFrame implements ActionListener {
    private JPanel myWindow, firstClassPanel, secondClassPanel, thirdClassPanel, controlPanel, leftPanel, rightPanel, screenPanel, seatsPanel, infoPanel;
    private JButton seat;
    private JButton reserveButton, cancelButton, exitButton, resetButton, invoiceButton;
    private JLabel label1, infoLabel1, infoLabel2, infoLabel3, totalCostLabel;
    ArrayList<JButton> seats = new ArrayList<JButton>();
    ArrayList<JButton> reservedSeats = new ArrayList<JButton>();
    ArrayList<JButton> selectedSeats = new ArrayList<JButton>();
    Color seatColor = new Color(164, 181, 196, 255);
    Color controlButtonsColor = new Color(227, 195, 157, 255);

    public MyGUI() {
        setTitle("My Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        controlPanel = new JPanel();
        myWindow = new JPanel();
        firstClassPanel = new JPanel();
        secondClassPanel = new JPanel();
        thirdClassPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        screenPanel = new JPanel();
        seatsPanel = new JPanel();
        infoPanel = new JPanel();
        reserveButton = new JButton("Reserve");
        cancelButton = new JButton("Cancel");
        exitButton = new JButton("Exit");
        resetButton = new JButton("Reset");
        invoiceButton = new JButton("Invoice");
        reserveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        exitButton.addActionListener(this);
        resetButton.addActionListener(this);
        invoiceButton.addActionListener(this);
        label1 = new JLabel("Screen");
        infoLabel1 = new JLabel("Available");
        infoLabel2 = new JLabel("Selected");
        infoLabel3 = new JLabel("Not Available");
        totalCostLabel = new JLabel("Total Cost: 0$");

        infoLabel1.setForeground(seatColor);
        infoLabel1.setIcon(new ColorIcon(seatColor, 10, 10));
        infoLabel2.setForeground(Color.GREEN);
        infoLabel2.setIcon(new ColorIcon(Color.GREEN, 10, 10));
        infoLabel3.setForeground(Color.RED);
        infoLabel3.setIcon(new ColorIcon(Color.RED, 10, 10));
        totalCostLabel.setForeground(Color.WHITE);

        firstClassPanel.setBackground(new Color(166, 136, 104, 255));
        secondClassPanel.setBackground(new Color(134, 113, 90, 255));
        thirdClassPanel.setBackground(new Color(96, 80, 64, 255));

        rightPanel.setBackground(new Color(7, 23, 57, 255));
        controlPanel.setBackground(new Color(7, 23, 57, 255));
        infoPanel.setBackground(new Color(7, 23, 57, 255));
        myWindow.setBackground(new Color(48, 25, 52, 255));
        reserveButton.setBackground(controlButtonsColor);
        cancelButton.setBackground(controlButtonsColor);
        exitButton.setBackground(controlButtonsColor);
        resetButton.setBackground(controlButtonsColor);
        invoiceButton.setBackground(controlButtonsColor);

        firstClassPanel.setLayout(new GridLayout(4, 5));

        for (int i = 0; i < 20; i++) {
            seat = new Seat(i+1, 50);
            seat.addActionListener(this);
            seats.add(seat);
            firstClassPanel.add(seat);
        }
        secondClassPanel.setLayout(new GridLayout(8, 5));
        for(int i = 0; i < 40; i++){
            seat = new Seat(i+21, 15);
            seat.addActionListener(this);
            seats.add(seat);
            secondClassPanel.add(seat);
        }
        thirdClassPanel.setLayout(new GridLayout(10, 5));
        for(int i = 0; i < 50; i++){
            seat = new Seat(i+61, 10);
            seat.addActionListener(this);
            seats.add(seat);
            thirdClassPanel.add(seat);
        }
        myWindow.setLayout(new GridLayout(1, 2,1,1));

        seatsPanel.setLayout(new GridLayout(3, 1));
        leftPanel.setLayout(new BorderLayout());
        controlPanel.setLayout(new GridLayout(5,1,10,30));

        controlPanel.add(reserveButton, BorderLayout.CENTER);
        controlPanel.add(cancelButton, BorderLayout.CENTER);
        controlPanel.add(invoiceButton, BorderLayout.CENTER);
        controlPanel.add(resetButton, BorderLayout.CENTER);
        controlPanel.add(exitButton, BorderLayout.CENTER);

        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.add(infoLabel1);
        infoPanel.add(infoLabel2);
        infoPanel.add(infoLabel3);

        rightPanel.setLayout(new BorderLayout(70,55));
        rightPanel.add(totalCostLabel, BorderLayout.NORTH);
        rightPanel.add(infoPanel, BorderLayout.SOUTH);
        rightPanel.add(new JLabel(), BorderLayout.EAST);
        rightPanel.add(new JLabel(), BorderLayout.WEST);
        rightPanel.add(controlPanel, BorderLayout.CENTER);

        firstClassPanel.setBorder(BorderFactory.createTitledBorder("First Class: 50$"));
        secondClassPanel.setBorder(BorderFactory.createTitledBorder("Second Class: 15$"));
        thirdClassPanel.setBorder(BorderFactory.createTitledBorder("Third Class: 10$"));
        screenPanel.add(label1);
        screenPanel.setPreferredSize(new Dimension(leftPanel.getWidth(), 20));
        seatsPanel.add(firstClassPanel, BorderLayout.EAST);
        seatsPanel.add(secondClassPanel, BorderLayout.EAST);
        seatsPanel.add(thirdClassPanel, BorderLayout.EAST);
        leftPanel.add(screenPanel, BorderLayout.NORTH);
        leftPanel.add(seatsPanel, BorderLayout.CENTER);
        myWindow.add(leftPanel);
        myWindow.add(rightPanel);

        setContentPane(myWindow);
        setVisible(true);
    }
    int customerInvoice = 0;
    int totalRevenue = 0;
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Seat) {
            Seat seat = (Seat) e.getSource();
            if (seat.isReserved()) {
                JOptionPane.showMessageDialog(this, "Seat is already reserved", "Error !!", JOptionPane.ERROR_MESSAGE);
            }
            else if(seat.getBackground() == Color.GREEN){
                seat.setBackground(seatColor);
                selectedSeats.remove(seat);
            }
            else {
                seat.setBackground(Color.GREEN);
                selectedSeats.add(seat);
            }
        }
        else if (e.getSource() == reserveButton) {
            if(selectedSeats.size() == 0){
                JOptionPane.showMessageDialog(this, "Click on any seat you want to reserve first", "Error !!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            for (JButton seat : selectedSeats) {
                if (seat instanceof Seat) {
                    ((Seat) seat).reserveSeat();
                    seat.setBackground(Color.RED);
                    reservedSeats.add(seat);
                    totalRevenue = totalRevenue + ((Seat) seat).getSeatPrice();
                }
            }
            selectedSeats.clear();
        }
        else if(e.getSource() == invoiceButton){
            JOptionPane.showMessageDialog(this, "Total Invoice: " + totalRevenue, "Invoice", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == resetButton){
            for (JButton seat : reservedSeats) {
                if (seat instanceof Seat) {
                    ((Seat) seat).resetSeat();
                    seat.setBackground(seatColor);
                }
            }
            totalRevenue = 0;
        }
        else if(e.getSource() == cancelButton){
            for (JButton seat : selectedSeats) {
                if (seat instanceof Seat) {
                    ((Seat) seat).cancelSeat();
                    seat.setBackground(seatColor);
                }
            }
            selectedSeats.clear();

        }
        else if(e.getSource() == exitButton){
            System.exit(0);
        }
        customerInvoice = 0;
        for (JButton seat : selectedSeats) {
            if (seat instanceof Seat) {
                customerInvoice = customerInvoice + ((Seat) seat).getSeatPrice();
            }
        }
        totalCostLabel.setText("Total Cost: " + customerInvoice + "$");
    }
}
