package DeliveryManSystem.GraphicalInterfaceClientSystem;

import DeliveryManSystem.DeliveryMan;
import DeliveryManSystem.Exceptions.PickupPointServerUnavailableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.Font.ITALIC;
import static java.awt.Toolkit.getDefaultToolkit;

/**
 * This Class creates the main panel used in the PDA
 * @author Gruppo D19
 * @version 1.0.2
 */

public class StartingPanel extends JPanel {

    private DeliveryMan deliveryMan;
    private JButton viewPackage;
    private JButton logOut;
    private AlertLabel alertLabel;
    private JComboBox pickupPointIdSelector;
    private BackgroundPanel bgp;
    private int height;
    private JPanel subPanel;

    /**
     * The constructor
     * @param bgp
     * @param deliveryman
     * @param height
     */

    StartingPanel( BackgroundPanel bgp, DeliveryMan deliveryman, int height){
        setOpaque(false);
        this.bgp = bgp;
        this.height = height;
        this.deliveryMan = deliveryman;

        initPanel();

    }

    /**
     * This method builds part of the main panel.
     * It adds the pickup point login fields and the "View Packages" button
     */

    private void initPanel(){
        setLayout(new BorderLayout());
        add(buttonPanel(), BorderLayout.CENTER);
        createAndRefreshPickupPointsList();
    }

    /**
     * This method gets all the existing pickup points
     * and puts them in an interactive list
     * @return The JComboBox containing the pickup points list
     */

    private JComboBox pickupPoints(){

            ArrayList<String> strings = new ArrayList<>();
        try {
            strings.addAll(deliveryMan.getPickupPointsID());
        } catch (IOException e) {
            alertLabel.setTextAndIcon(SetDMLanguage.getInstance().setStartingPanel()[0], true);
        }
        pickupPointIdSelector = new JComboBox(strings.toArray(new String[0]));

            pickupPointIdSelector.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        if(pickupPointIdSelector.getSelectedItem()!=null) {
                            String piPoID = pickupPointIdSelector.getSelectedItem().toString();
                            deliveryMan.sendCredentials(piPoID);
                            refreshPickupPointsList();
                            alertLabel.showMessageForAFewSeconds(SetDMLanguage.getInstance().setStartingPanel()[1] + piPoID+"</html>", false);
                        }
                    } catch (IOException e) {
                        alertLabel.setTextAndIcon(SetDMLanguage.getInstance().setStartingPanel()[0], true);
                    } catch (PickupPointServerUnavailableException p){
                        alertLabel.showMessageForAFewSeconds(SetDMLanguage.getInstance().setStartingPanel()[2] + pickupPointIdSelector.getSelectedItem().toString()+ "</html>", true);
                    }
                }
            });

            return pickupPointIdSelector;


    }

    /**
     * This method creates the panel used to view
     * the packages carried by the deliveryman and
     * other details
     * @return The panel containing the button
     */

    private JPanel buttonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JPanel button = new JPanel();
        button.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2,1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(height/10,height/50,height/10,height/50));
        button.setLayout(new GridLayout(2 ,1));
        setMessage(buttonPanel);
        setButton(button);
        buttonPanel.add(button);
        return buttonPanel;

    }

    /**
     * This method builds the panel containing the
     * "View Packages" button
     * @param buttonPanel The panel itself
     */

    private void setButton(JPanel buttonPanel){

       viewPackage = new JButton(SetDMLanguage.getInstance().setStartingPanel()[3]);
       viewPackage.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               bgp.changePanel("packagePanel");
           }
       });
       viewPackage.setFont(new Font("",Font.BOLD,height/30));

       viewPackage.setBackground(new Color(255,153,0));
       viewPackage.setFocusable(false);

       logOut = new JButton(SetDMLanguage.getInstance().setStartingPanel()[6]);
       logOut.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               buttonPanel.remove(logOut);
               buttonPanel.add(getConfirmPanel(buttonPanel));
               revalidate();
           }
       });
       logOut.setFont(new Font("",Font.BOLD,height/30));

       logOut.setBackground(new Color(255,153,0));
       logOut.setFocusable(false);


        buttonPanel.add(viewPackage);
        buttonPanel.add(logOut);
    }

    /**
     * This method creates the panel containing the request for
     * confirmation of exit
     * @param panelCont the panel that will containing the confirm panel
     * @return the panel that has been created
     */

    public JPanel getConfirmPanel(JPanel panelCont){
        JPanel confirmPanel = new JPanel();
        confirmPanel.setOpaque(false);
        JLabel areYouSureLabel = new JLabel(SetDMLanguage.getInstance().setStartingPanel()[4]);
        areYouSureLabel.setForeground(Color.WHITE);
        areYouSureLabel.setFont(new Font("Arial", Font.BOLD, height/30));
        JButton buttonYes = new JButton(SetDMLanguage.getInstance().setStartingPanel()[5]);
        buttonYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bgp.changePanel("loginPanel");
            }
        });
        JButton buttonNo = new JButton("NO");
        buttonNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCont.remove(confirmPanel);
                panelCont.add(logOut);
                panelCont.repaint();
            }
        });
        buttonYes.setBackground(new Color(255,153,0));
        buttonNo.setBackground(new Color(255,153,0));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(1,2));
        buttonsPanel.add(buttonYes);
        buttonsPanel.add(buttonNo);
        confirmPanel.setLayout(new GridLayout(2,1));
        confirmPanel.add(areYouSureLabel);
        confirmPanel.add(buttonsPanel);
        return confirmPanel;

    }

    /**
     * This method builds the label that contains
     * information about the pickup points selector
     * @param buttonPanel The panel containing the confirmation button
     */

    private void setMessage(JPanel buttonPanel){
        alertLabel = new AlertLabel(SetDMLanguage.getInstance().setStartingPanel()[7], true);
        alertLabel.setDefaultTextAndIcon(SetDMLanguage.getInstance().setStartingPanel()[7], true);
        alertLabel.setForeground(Color.WHITE);
        Font font = new Font("Arial" ,ITALIC , height/25);
        alertLabel.setBorder(BorderFactory.createTitledBorder(alertLabel.getBorder(),SetDMLanguage.getInstance().setLoginPanel()[7] , ITALIC , 0, font, Color.red));
        buttonPanel.add(alertLabel);
    }



    /**
     * This method refresh the pickup point list
     */

    public void refreshPickupPointsList(){
        removeAll();
        initPanel();
        revalidate();
    }

    /**
     * This method create and refresh the pickup point list
     */

    private void createAndRefreshPickupPointsList() {
        subPanel = new JPanel();
        subPanel.setOpaque(false);
        subPanel.setLayout(new BorderLayout());

        JPanel centeredPanel = new JPanel();
        centeredPanel.setOpaque(false);
        JButton refreshButton = new JButton();
        refreshButton.setPreferredSize(new Dimension(height/11,height/11));
        Image backImage = getDefaultToolkit().createImage("src/DeliveryManSystem/GraphicalInterfaceClientSystem/Icons/update.jpg").getScaledInstance(height/12, height/12,Image.SCALE_DEFAULT);
        refreshButton.setIcon(new ImageIcon(backImage));
        refreshButton.setBackground(new Color(255,153,0));
        
        centeredPanel.add(refreshButton);

        subPanel.add(centeredPanel, BorderLayout.NORTH);

        Component piPoList = pickupPoints();
        subPanel.add(piPoList, BorderLayout.SOUTH);

        add(subPanel, BorderLayout.NORTH);

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refreshPickupPointsList();
            }
        };

        refreshButton.addActionListener(buttonListener);
    }

}
