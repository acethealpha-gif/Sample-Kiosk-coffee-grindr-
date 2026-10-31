package receipt;

import javax.swing.*;
import java.awt.*;

public class CoffeeKiosk extends JFrame {

    CardLayout cards = new CardLayout();
    JPanel screens = new JPanel(cards);
    Color brown = new Color(59, 36, 24);
    Color cream = new Color(245, 222, 179);

    // Menu at presyo galing sa Receipt.java
    String[] names = Receipt.allNames;
    int[][] prices = Receipt.allPrices;
    String[] sizeNames = {"Jutes", "Saks", "Daks"};

    // Data ng order
    int selectedItem = 0;
    int subtotal = 0;
    String orderType = "";

    // Cart
    DefaultListModel<String> cartModel = new DefaultListModel<>();
    JList<String> cartList = new JList<>(cartModel);
    java.util.ArrayList<Integer> cartTotals = new java.util.ArrayList<>();

    // Mga component na ina-update
    JLabel menuSubtotal = new JLabel("Subtotal: P0", SwingConstants.CENTER);
    JLabel custItem = new JLabel("", SwingConstants.CENTER);
    JComboBox<String> sizeBox = new JComboBox<>(sizeNames);
    JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
    JLabel summaryTotal = new JLabel("TOTAL: P0", SwingConstants.CENTER);
    JLabel payTotal = new JLabel("TOTAL: P0", SwingConstants.CENTER);
    JTextField cashField = new JTextField();
    JLabel changeLabel = new JLabel(" ", SwingConstants.CENTER);
    JButton finishBtn = new JButton("Finish");

    public CoffeeKiosk() {
        setTitle("Coffee Grindr");
        setSize(450, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(brown);

        // TOP BAR: title sa kaliwa, logo sa kanan
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(brown);
        top.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("COFFEE GRINDR");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(cream);

        JLabel logo = new JLabel();
        java.net.URL logoUrl = getClass().getResource("logo.png");
        if (logoUrl != null) {
            ImageIcon icon = new ImageIcon(logoUrl);
            Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(scaled));
        } else {
            logo.setText("LOGO");
            logo.setForeground(cream);
        }

        top.add(title, BorderLayout.WEST);
        top.add(logo, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // SCREENS
        screens.setBackground(brown);
        screens.add(welcomeScreen(), "welcome");
        screens.add(menuScreen(), "menu");
        screens.add(customizeScreen(), "customize");
        screens.add(summaryScreen(), "summary");
        screens.add(paymentScreen(), "payment");
        screens.add(thanksScreen(), "thanks");
        add(screens, BorderLayout.CENTER);
    }

    // ---------- HELPERS ----------
    JLabel makeLabel(String text, int size) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, size));
        l.setForeground(cream);
        return l;
    }

    JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        return b;
    }

    JPanel buttonRow(JButton... btns) {
        JPanel row = new JPanel(new GridLayout(1, btns.length, 15, 0));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(10, 30, 25, 30));
        for (JButton b : btns) {
            row.add(b);
        }
        return row;
    }

    // ---------- 1. WELCOME ----------
    JPanel welcomeScreen() {
        JPanel p = new JPanel(new GridLayout(2, 1, 20, 20));
        p.setBackground(brown);
        p.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        JButton dine = makeButton("DINE IN");
        JButton take = makeButton("TAKE OUT");
        dine.setFont(new Font("Arial", Font.BOLD, 22));
        take.setFont(new Font("Arial", Font.BOLD, 22));

        dine.addActionListener(e -> {
            orderType = "Dine In";
            cards.show(screens, "menu");
        });
        take.addActionListener(e -> {
            orderType = "Take Out";
            cards.show(screens, "menu");
        });

        p.add(dine);
        p.add(take);
        return p;
    }

    // ---------- 2. MENU ----------
    JPanel menuScreen() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(brown);
        p.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        header.add(makeLabel("MENU", 22));
        menuSubtotal.setFont(new Font("Arial", Font.BOLD, 16));
        menuSubtotal.setForeground(cream);
        header.add(menuSubtotal);
        p.add(header, BorderLayout.NORTH);

        JList<String> list = new JList<>(names);
        list.setFont(new Font("Arial", Font.PLAIN, 18));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        p.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton back = makeButton("Back");
        JButton select = makeButton("Select");
        JButton proceed = makeButton("Proceed");

        back.addActionListener(e -> cards.show(screens, "welcome"));
        select.addActionListener(e -> {
            selectedItem = list.getSelectedIndex();
            custItem.setText(names[selectedItem]);
            sizeBox.setEnabled(selectedItem < 8);
            sizeBox.setSelectedIndex(0);
            qtySpinner.setValue(1);
            cards.show(screens, "customize");
        });
        proceed.addActionListener(e -> {
            if (subtotal == 0) {
                JOptionPane.showMessageDialog(this, "Your cart is empty!");
            } else {
                cards.show(screens, "summary");
            }
        });

        p.add(buttonRow(back, select, proceed), BorderLayout.SOUTH);
        return p;
    }

    // ---------- 3. CUSTOMIZE ----------
    JPanel customizeScreen() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(brown);
        p.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));

        p.add(makeLabel("CUSTOMIZE", 22), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 1, 10, 10));
        form.setOpaque(false);
        custItem.setFont(new Font("Arial", Font.BOLD, 20));
        custItem.setForeground(cream);
        form.add(custItem);
        form.add(makeLabel("Size", 16));
        form.add(sizeBox);
        form.add(makeLabel("Quantity", 16));
        form.add(qtySpinner);
        p.add(form, BorderLayout.CENTER);

        JButton back = makeButton("Back");
        JButton add = makeButton("Add to Cart");

        back.addActionListener(e -> cards.show(screens, "menu"));
        add.addActionListener(e -> {
            int size = (selectedItem < 8) ? sizeBox.getSelectedIndex() : 0;
            int qty = (int) qtySpinner.getValue();

            int total = prices[selectedItem][size] * qty;
            subtotal = subtotal + total;

            String sizeText = (selectedItem < 8) ? " (" + sizeNames[size] + ")" : "";
            cartModel.addElement(names[selectedItem] + sizeText + " x" + qty + " = P" + total);
            cartTotals.add(total);

            menuSubtotal.setText("Subtotal: P" + subtotal);
            summaryTotal.setText("TOTAL: P" + subtotal);
            cards.show(screens, "summary");
        });

        p.add(buttonRow(back, add), BorderLayout.SOUTH);
        return p;
    }

    // ---------- 4. SUMMARY ----------
    JPanel summaryScreen() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(brown);
        p.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));

        p.add(makeLabel("SUMMARY", 22), BorderLayout.NORTH);

        cartList.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.setOpaque(false);
        center.add(new JScrollPane(cartList), BorderLayout.CENTER);
        summaryTotal.setFont(new Font("Arial", Font.BOLD, 22));
        summaryTotal.setForeground(cream);
        center.add(summaryTotal, BorderLayout.SOUTH);
        p.add(center, BorderLayout.CENTER);

        JButton remove = makeButton("Remove");
        JButton more = makeButton("Add More");
        JButton pay = makeButton("Pay");

        remove.addActionListener(e -> {
            int i = cartList.getSelectedIndex();
            if (i == -1) {
                JOptionPane.showMessageDialog(this, "Select an item to remove first!");
            } else {
                subtotal = subtotal - cartTotals.get(i);
                cartTotals.remove(i);
                cartModel.remove(i);
                summaryTotal.setText("TOTAL: P" + subtotal);
                menuSubtotal.setText("Subtotal: P" + subtotal);
            }
        });

        more.addActionListener(e -> cards.show(screens, "menu"));
        pay.addActionListener(e -> {
            if (subtotal == 0) {
                JOptionPane.showMessageDialog(this, "Your cart is empty!");
                return;
            }
            payTotal.setText("TOTAL: P" + subtotal);
            cashField.setText("");
            changeLabel.setText(" ");
            finishBtn.setEnabled(false);
            cards.show(screens, "payment");
        });

        p.add(buttonRow(remove, more, pay), BorderLayout.SOUTH);
        return p;
    }

    // ---------- 5. PAYMENT ----------
    JPanel paymentScreen() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(brown);
        p.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));

        p.add(makeLabel("PAYMENT", 22), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 1, 10, 10));
        form.setOpaque(false);
        payTotal.setFont(new Font("Arial", Font.BOLD, 24));
        payTotal.setForeground(cream);
        form.add(payTotal);
        form.add(makeLabel("Money Received", 16));
        cashField.setFont(new Font("Arial", Font.PLAIN, 20));
        cashField.setHorizontalAlignment(JTextField.CENTER);
        form.add(cashField);
        form.add(makeLabel("CHANGE", 16));
        changeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        changeLabel.setForeground(cream);
        form.add(changeLabel);
        p.add(form, BorderLayout.CENTER);

        JButton back = makeButton("Back");
        JButton payBtn = makeButton("Pay");
        finishBtn.setFont(new Font("Arial", Font.BOLD, 16));
        finishBtn.setEnabled(false);

        back.addActionListener(e -> cards.show(screens, "summary"));
        payBtn.addActionListener(e -> {
            try {
                int cash = Integer.parseInt(cashField.getText().trim());
                if (cash >= subtotal) {
                    int change = cash - subtotal;
                    changeLabel.setText("P" + change);
                    finishBtn.setEnabled(true);
                } else {
                    int missing = subtotal - cash;
                    changeLabel.setText("GAGA KULANG KA PA: P" + missing);
                    finishBtn.setEnabled(false);
                }
            } catch (NumberFormatException ex) {
                changeLabel.setText("Numbers only please!");
            }
        });
        finishBtn.addActionListener(e -> cards.show(screens, "thanks"));

        p.add(buttonRow(back, payBtn, finishBtn), BorderLayout.SOUTH);
        return p;
    }

    // ---------- 6. THANK YOU ----------
    JPanel thanksScreen() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(brown);
        p.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 30));

        JLabel msg = makeLabel("<html><center>THANK YOU<br>FOR YOUR ORDER!</center></html>", 28);
        p.add(msg, BorderLayout.CENTER);

        JButton newOrder = makeButton("New Order");
        newOrder.addActionListener(e -> {
            subtotal = 0;
            cartModel.clear();
            cartTotals.clear();
            menuSubtotal.setText("Subtotal: P0");
            summaryTotal.setText("TOTAL: P0");
            cards.show(screens, "welcome");
        });

        p.add(buttonRow(newOrder), BorderLayout.SOUTH);
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CoffeeKiosk().setVisible(true));
    }
}