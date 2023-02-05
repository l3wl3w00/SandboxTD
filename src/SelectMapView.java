import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * A pályaválasztó nézetért felelős osztály
 */
public class SelectMapView extends JPanel implements GameView{
    private JButton backButton = new JButton("Back");
    private JButton deleteButton = new JButton("Delete");
    private JPanel buttonPanel = new JPanel();
    private JList pathJList = new JList();
    private JScrollBar jScrollBar = new JScrollBar();
    private MapPanel mapPanel = new MapPanel();
    private PathHandler pathHandler;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel content = new JPanel();

    /**
     * Egy kisebb panel, amire az éppen kiválasztott pálya kicsiben kirajzolódik
     */
    private class MapPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            if (pathHandler.getActivePath() != null)
                pathHandler.getActivePath().render(g,0.2);
            g.dispose();
        }
    }

    /**
     * Konstruktor. Beállít minden gombot és panelt
     * @param game A játékkezelő osztály egy objektuma
     * @param _pathHandler A pályák listáját tartalmazó objektum
     */
    public SelectMapView(Game game,PathHandler _pathHandler){
        content.setLayout(new GridBagLayout());
        setLayout(new BorderLayout());
        setBackground(Color.pink);
        backButton.addActionListener((e)->{
            game.changeView("menu");
        });

        pathJList.addListSelectionListener((e) -> {
            pathHandler.setActivePath((Path) pathJList.getSelectedValue());
        });
        deleteButton.addActionListener((e)->{
            pathHandler.deleteActivePath();
            updateJList();
        });

        pathHandler = _pathHandler;
        pathJList.add(jScrollBar);
        placeComponents();

    }

    /**
     * A GUI elrendezését és kinézetét állítja be
     */
    public void placeComponents(){

        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        buttonPanel.setLayout(new GridLayout(8,1,0,20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        //buttonPanel.setBorder(new LineBorder(Color.BLACK,2));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        content.add(buttonPanel,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        content.add(pathJList,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        content.add(mapPanel,gbc);
        mapPanel.setBackground(new Color(179, 239, 93));
        int width = 300;
        int height = width / 16 * 9;
        mapPanel.setPreferredSize(new Dimension(width,height));
        add(content,BorderLayout.CENTER);

    }

    /**
     * frissíti a pathJlist nevű JList típusú objektumot az aktuális pályák alapján
     */
    public void updateJList(){
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < getPathArray().size(); i++)
        {
            listModel.addElement(getPathArray().get(i));
        }
        pathJList.setModel(listModel);
    }

    /**
     * Megadja a pályákat tartalmazó láncolt listát
     * @return a pályákat tartalmazó láncolt lista
     */
    private LinkedList<Path> getPathArray(){
        return pathHandler.getPaths();
    }

    /**
     * újratölti a listákat a háttértából, és ez alapján frissíti a JList-et
     */
    public void onAppearing(){
        pathHandler.clear();
        pathHandler.loadPaths();
        updateJList();
    }

}
