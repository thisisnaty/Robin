package robin;
/**@author Robin Williams (Team 2)*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.sql.*;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
//Application class
public class Robin extends JFrame implements ActionListener{
    //User public structure
    public class User {
        //primary key
        public int userId;
        public String userFName;
        public String userLName;
        public String pass;
        public String bDate;
        public String address;
        public char sex;
        public String email;
        public String phone;
        public boolean admin;
        //constructor
        public User(int uI, String uFN, String uLN, String pa, 
                String bD, String add, char s, String e, String ph, boolean adm){
            userId = uI;
            userFName = uFN;
            userLName = uLN;
            pass = pa;
            bDate = bD;
            address = add;
            sex = s;
            email = e;
            phone = ph;
            admin = adm;
        }
        //constructor default
        public User( ){
            userId = -1;
            userFName = null;
            userLName = null;
            pass = null;
            bDate = null;
            address = null;
            sex = '*';
            email = null;
            phone = null;
            admin = false;
        }
    }
    //structure for category
    class Category {
        //primary key
        public int categoryId;
        public String categoryDesc;
        //constructor
        public Category(int cI, String cD){
            categoryId = cI;
            categoryDesc = cD;
        }
        //default constructor
        public Category( ){
            categoryId = -1;
            categoryDesc = null;
        }
    }
    //structure for subcategory
    class SubCategory {
        //primary key
        public int subCategoryId;
        public String subCategoryDesc;
        public int categoryId;
        //constructor
        public SubCategory(int sCI, String sCD, int cI){
            subCategoryId = sCI;
            subCategoryDesc = sCD;
            categoryId = cI;
        }
        //default constructor
        public SubCategory( ){
            subCategoryId = -1;
            subCategoryDesc = null;
            categoryId = -1;
        }
    }
    //structure for Product
    public class Product {
        //primary key
        public int productId;
        public String productName;
        public String productDesc;
        public String productKey;
        public int subCategoryId;
        public double unitPrice;
        public String userId;
        //constructor
        public Product(int pI, String pN, String pD, String pK, 
                int sCI, double uP, String uI){
            productId = pI;
            productName = pN;
            productDesc = pD;
            productKey = pK;
            subCategoryId = sCI;
            unitPrice = uP;
            userId = uI;
        }
        //default constructor
        public Product( ){
            productId = -1;
            productName = null;
            productDesc = null;
            productKey = null;
            subCategoryId = -1;
            unitPrice = -1;
            userId = null;
        }
    }
    /**
     * @param args the command line arguments
     */
    //login window
    JPanel window;
    //panel for home displays categories
    JPanel panelHome;
    //panel for subcategories
    JPanel panelSubCat;
    //container of panels, not window
    JPanel container;
    //panel for products
    JPanel panelProducts;
     //panel for top window logo and search bar, always present
    JPanel topWindow;
    //this is the logo that'll be on the top left corner and for text logo
    JLabel labelBird, labelRobinTxt;
    //account label
    JLabel labelAccl;
    //text field where user may choose to search
    JTextField txtFieldSearch;
    //linked list for subcategories
    LinkedList subcategories;
    //linked list for products
    LinkedList products;
    //linked list for wishlist
    LinkedList wishlistLinked;
    //jlabel for string displaying subcategory title in products page
    JLabel subCatTxt;
    //selection for category and subcategory
    int numCat;
    int numSub;
    //file directory for chosen category
    String fileNameCatChosen;
    //jlabel for background
    JLabel labelBackground;
    //jlabel for category name selected in subcategories page
    JLabel labelCatTxt;
    //jtable for subcategories, shown in subcategories page
    JTable tableSubCat;
    //scrollpane for subcategories's table
    JScrollPane spSubCat;
    //screen request
    String searchRequest = "";
    //panel for search results
    JPanel panelSearch;
    //scrollpane for products's table
    JScrollPane spProducts;
    //scrollpane for search's table
    JScrollPane spSearch;
    //scrollpane for wishlist's table
    JScrollPane spWishlist;
    //scrollpane for user data table
    JScrollPane spUserData;
    //scrollpane for user data table
    JScrollPane spUserDataUPDATE;
    //panel for account
    JPanel panelAccount;
    //scrollpane for products owned by user
    JScrollPane spOwnProduct;
    //username to login
    String username;
    //password to login
    String password;
    //where users type their username
    JTextField txtFieldUser;
    //password characters to be shown as "*"
    JPasswordField passFieldPassw;
    //current user for application
    User currentUser;
    //label for welcome message
    JLabel label;
    /*SQL connection global variables*/
    //connection variable
    Connection conn;
    //prepare statement for query
    PreparedStatement pst;
    //result of query
    ResultSet rs;
            
    JTextField updateName;
    JTextField updateBday;
    JTextField updateAddress;
    JTextField updatePhone;
    JTextField updateEmail;
        

    //Constructor
    public Robin(){
        //label for welcome message
        label = new JLabel("");
        label.setFont(new Font("Georgia", Font.PLAIN, 12));
        label.setBounds(585, 310, 200, 100);
        //Calls for login
        Login();
        //Adds window to jframe created in login
        add(window);
        //set size of jframe
        setSize(1300, 715);
        //default jframe configurations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setResizable(true);
        setTitle("Robin");
        //create panels and linkedlists
        container = new JPanel();
        panelHome = new JPanel();
        panelSubCat = new JPanel();
        panelProducts = new JPanel();
        panelSearch = new JPanel();
        subcategories = new LinkedList();
        products = new LinkedList();
        wishlistLinked = new LinkedList();
        labelCatTxt = new JLabel();
        tableSubCat = new JTable();
        spSubCat = new JScrollPane();
        spSearch = new JScrollPane();
        spProducts = new JScrollPane();
        spWishlist = new JScrollPane();
        spUserData = new JScrollPane();
        spUserDataUPDATE = new JScrollPane();
        spOwnProduct = new JScrollPane();
        panelAccount = new JPanel();

        username = "";
        //create text for subcategory label in products
        subCatTxt = new JLabel("");
        //add elements to the linked list
        subcategories.add(0,new SubCategory());
        products.add(0,new Product());
        //Declare top window
        topWindow = new JPanel();
        //generic variable to create icons in labels  
        Icon icons;
        /*file names*/
         //file name of the logo
        String fileNameBird = "robinTop.png";     
         //file name of the text of the logo
        String fileNameRobinTxt = "robinTxtTop.png";   
        //file name of the account button
        String fileNameAccount = "CategoryLabels/" + "Accl.png";
        //create icon for logo
        icons = new ImageIcon(getClass().getResource(fileNameBird));
        labelBird = new JLabel(icons);
        labelBird.setBounds(0, 0, 100, 100);
        //create icon for text logo
        icons = new ImageIcon(getClass().getResource(fileNameRobinTxt));
        labelRobinTxt = new JLabel(icons);
        labelRobinTxt.setBounds(87, 0, 100, 100);
        //create icon for accl
        icons = new ImageIcon(getClass().getResource(fileNameAccount));
        labelAccl = new JLabel(icons);
        labelAccl.setBounds(550, 630, icons.getIconWidth() + 20, icons.getIconHeight() + 10);
        labelAccl.setOpaque(true);
        labelAccl.setBackground(new Color(255, 255, 255, 0));
        labelAccl.setBorder(new LineBorder(new Color(255, 255, 255, 70), 50, true));
        //create search field
        txtFieldSearch = new JTextField("  Search", 1);
        txtFieldSearch.setFont(new Font("Helvetica", Font.PLAIN, 16));
        txtFieldSearch.setForeground(Color.GRAY);
        txtFieldSearch.setBounds(1100, 27, 150, 35);
        txtFieldSearch.setBorder(new LineBorder(new Color(255, 255, 255, 70), 10));

        //set default window settings
        topWindow.setSize(2560, 100);
        topWindow.setLayout(null);
        topWindow.setVisible(true);
        topWindow.setBackground(new Color(0, 0, 0, 70));
        //add logos and search field to top window
        topWindow.add(labelBird);
        topWindow.add(labelRobinTxt);
        topWindow.add(txtFieldSearch);
        //add window to container since it is always present
        container.add(topWindow);
        //background image
        Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("background1.png"));
        img = img.getScaledInstance(1300, img.getHeight(this)+190, java.awt.Image.SCALE_SMOOTH);
        Icon iconBackground = new ImageIcon(img);
        //label to show background image
        labelBackground = new JLabel(iconBackground);
        //setting label size
        labelBackground.setSize(1300, img.getHeight(this));
        //user at beginning
        currentUser = new User();
        //SQL stuff
        conn = javaconnection.ConnecrDB();
        rs = null;
        pst = null;
        
        updateName = new JTextField("");
        updateBday = new JTextField("");
        updateAddress = new JTextField("");
        updatePhone = new JTextField("");
        updateEmail= new JTextField("");


    }
    //window where users login to Robin
    public void Login(){
        //since Login is the first window shown, we need to create labelBackground here too
        JLabel labelBackgroundLog;
        //background image
        Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("background1.png"));
        img = img.getScaledInstance(1300, img.getHeight(this), java.awt.Image.SCALE_SMOOTH);
        Icon iconBackground = new ImageIcon(img);
        //label to show background image
        labelBackgroundLog = new JLabel(iconBackground);
        //setting label size
        labelBackgroundLog.setSize(1300, img.getHeight(this));
        //create window for login
        window = new JPanel();
        //file name of the logo that appears on top of Username
        String fileNameBird = "robinLogin.png";
         //button the user clicks to login
        JButton btonLogin;       
        //where the image of the logo will be stored
        JLabel labelBird; 
        //to create the image icon of the logo
        Icon icons;
        //icon for logo
        icons = new ImageIcon(getClass().getResource(fileNameBird));
        labelBird = new JLabel(icons);
        //setting bounds for the logo
        labelBird.setBounds(490, 100, 300, 233); 
        //message that appears below the logo
        label.setText("Welcome to Robin ©");
        //displays username text field
        txtFieldUser = new JTextField("Username", 1);
        txtFieldUser.setFont(new Font("Georgia", Font.ITALIC, 16));
        txtFieldUser.setForeground(Color.GRAY);
        txtFieldUser.setBounds(500, 390, 300, 30);
         //when the user clicks the text field
        txtFieldUser.addMouseListener(new MouseAdapter() {     
        public void mouseClicked(MouseEvent e) {                  
            txtFieldUser.setText("");
            txtFieldUser.setFont(new Font("Georgia", Font.PLAIN, 16));
            txtFieldUser.setForeground(Color.BLACK);
        }
        });
        //displays password text field
        passFieldPassw = new JPasswordField("Password", 1);
        passFieldPassw.setFont(new Font("Georgia", Font.ITALIC, 16));
        passFieldPassw.setForeground(Color.GRAY);
        passFieldPassw.setEchoChar('*');
        passFieldPassw.setBounds(500, 430, 300, 30);
        //"enter" = clicking "login" button
        passFieldPassw.addActionListener(this);
        //password is being typed
        passFieldPassw.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            passFieldPassw.setText("");
            passFieldPassw.setFont(new Font("Georgia", Font.PLAIN, 16));
            passFieldPassw.setForeground(Color.BLACK);
        }
        });
        //login button is created
        btonLogin = new JButton("Login");
        btonLogin.setFont(new Font("Georgia", Font.PLAIN, 16));
        btonLogin.setBounds(600, 480, 100, 30);
        //identifies button
        btonLogin.addActionListener(this);
        //adds all elements to window
        window.add(labelBird);
        window.add(label);
        window.add(txtFieldUser);
        window.add(passFieldPassw);
        window.add(btonLogin);
        window.add(labelBackgroundLog);
        //default settings for window
        window.setSize(2560, 1600);
        window.setLayout(null);
        window.setVisible(true);
    }
    //enters only after login is succesful
    public void home(){
        //home button listener declared in constructor
        //appears on all panels
        labelRobinTxt.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                        setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                        setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }            
                public void mouseClicked(MouseEvent e) {
                    //only clicked once
                    if(e.getClickCount() == 1) {
                            /*if other panels are shown*/
                            //hide other panels
                            panelSubCat.setVisible(false);
                            labelCatTxt.setVisible(false);
                            tableSubCat.setVisible(false);
                            spSubCat.setVisible(false);
                            panelProducts.setVisible(false);
                            panelSearch.setVisible(false);
                            spProducts.setVisible(false);
                            spSearch.setVisible(false);
                            panelAccount.setVisible(false);
                            subCatTxt.setVisible(false);
                            panelAccount.setVisible(false);
                            spUserData.setVisible(false);
                            spUserDataUPDATE.setVisible(false);
                            spWishlist.setVisible(false);
                            spOwnProduct.setVisible(false);
                            //show home panel
                            panelHome.setVisible(true);
                            container.remove(labelBackground);
                            container.add(labelBackground);
                    }
                 }
        });
        //search is clicked
        txtFieldSearch.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                        txtFieldSearch.setText("");
                        txtFieldSearch.setFont(new Font("Helvetica", Font.PLAIN, 16));
                        txtFieldSearch.setForeground(Color.BLACK);
                }
        });

        txtFieldSearch.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}
                    @Override
                    public void keyReleased(KeyEvent e) {}
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyChar()== '\n')
                        {
                            searchRequest = txtFieldSearch.getText();
                             /*if other panels are shown*/
                            //hide panel for subcategories
                            panelSubCat.setVisible(false);
                            labelCatTxt.setVisible(false);
                            tableSubCat.setVisible(false);
                            spSubCat.setVisible(false);
                            panelProducts.setVisible(false);
                            panelSearch.remove(spSearch);
                            spProducts.setVisible(false);
                            spSearch.setVisible(false);
                            spUserData.setVisible(false);
                            spUserDataUPDATE.setVisible(false);
                            spWishlist.setVisible(false);
                            subCatTxt.setVisible(false);
                            panelAccount.setVisible(false);
                            //show search panel
                            panelHome.setVisible(false);
                            //sql
                            search();
                            searchResults();
                        }
                    }
        });
        //This algorithm allows us to show the correct image for each table cell
        //This is the Categories table model
        TableModel dataModel = new AbstractTableModel() {
          public int getColumnCount() { return 4; }
          public int getRowCount() { return 2;}
          public ImageIcon getValueAt(int row, int col) { 
              if(row == 0)
                  numCat = col + 1;
              else
                  numCat = col + 5;
              //gets image corresponding to column
              Image catBird = Toolkit.getDefaultToolkit().getImage
                    (this.getClass().getResource("Categories/"+(numCat + "Cat.png")));
              catBird = catBird.getScaledInstance(200, 220, java.awt.Image.SCALE_SMOOTH);
              //displays image
              return new ImageIcon(catBird);
          }
          //allows images in table
          public Class getColumnClass(int col) { return getValueAt(0, col).getClass();}
        };
        //creates new table for categories
        JTable table = new JTable(dataModel);
        //configure table
        table.setSize(1000, 500);
        table.setLocation(150, 80);
        table.setLayout(null);
        table.setVisible(true);
        //sets row height
        table.setRowHeight(250);
        //set table background to transparent
        table.setBackground(new Color(0, 0 ,0 ,0));
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setTableHeader(null);
        table.setSelectionBackground(new Color(0, 0 , 0 , 0));
        //the listener will recognize the user clicking a cell and send the user to subcategories window
        table.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                        setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                        setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
                public void mouseClicked(MouseEvent e) {
                        //if clicked once
                        if(e.getClickCount() == 1){
                                //get selection from table
                                JTable target = (JTable)e.getSource();
                                int row = target.getSelectedRow();
                                int column = target.getSelectedColumn();
                                //gets selection
                                 if(row == 0) {
                                        numCat = column + 1;
                                 }
                                 else {
                                        numCat = column + 5;
                                 }
                                /*variable "numCat" identifies the category selected*/
                                 //user now is not able to see panel home
                                panelHome.setVisible(false);
                                //enters subCategory
                                getSubCategories();
                                subCategory();
                        }
                }
        });
        //Scroller for table with transparent background and border
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(150, 130, 1000, 500);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBorder(createEmptyBorder());
        //the user may click the account label to see the account panel
        labelAccl.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                        setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            public void mouseExited(MouseEvent e) {
                        setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
                public void mouseClicked(MouseEvent e) {
                            //show account panel
                            panelHome.setVisible(false);
                            userWishlist();
                            account();
                }
        });
        /*panel home contains all elements in home except top window*/
        //set panel home background to transparent
        panelHome.setOpaque(false);
        panelHome.setBackground(new Color(0, 0, 0,0));
        panelHome.add(sp);
        panelHome.add(labelAccl);
        //set panel home size and other settings
        panelHome.setSize(1300, 1000);
        panelHome.setBounds(0, 0, 1300, 1000);
        panelHome.setLayout(null);
        //user views panel home
        panelHome.setVisible(true);
        //create container 
        container.setSize(1300, 1000);
        container.setBounds(0, 0, 1300, 1000);
        container.setLayout(null);
        //container now visible to user
        container.setVisible(true);
        //add panel home to container
        container.add(panelHome);
        //add panel for subcat set visible to false
        panelSubCat.setVisible(false);
        container.add(panelSubCat);
        container.add(panelProducts);
        container.add(panelSearch);
        container.add(panelAccount);
        //add background to container
        container.add(labelBackground);
        //add container to jframe
        add(container);
    }
        //called after a category is picked
        public void subCategory(){
            Icon icons;
            fileNameCatChosen = "CategoryLabels/" + numCat + "Catl.png";
            //Name of the selected category
            Image catTxt = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(fileNameCatChosen));
            //creates image for category label to see in which category the user is
            icons = new ImageIcon(catTxt);
            labelCatTxt = new JLabel(icons);
            labelCatTxt.setBounds(50, container.getHeight()/2-catTxt.getHeight(this)/2, catTxt.getWidth(this), catTxt.getHeight(this));
            labelCatTxt.setHorizontalAlignment(SwingConstants.CENTER);
            labelCatTxt.setVisible(true);
            //data model for subcategory
            TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() { return 1; }
            public int getRowCount() { return subcategories.size() - 1;}
            public String getValueAt(int row, int col) { 
                String nombre;
                int numRow = row + 1;
                Object base = subcategories.get(numRow);
                SubCategory aux = (SubCategory) base;
                nombre = "\t" + aux.subCategoryDesc;
                return nombre;
            }
          };
            //creates subcategories table
            tableSubCat = new JTable(dataModel);
            //default settings for table with transparent background
            tableSubCat.setRowHeight(100);
            tableSubCat.setBackground(new Color(255, 255, 255 ,100));
            tableSubCat.setOpaque(false);
            tableSubCat.setShowGrid(false);
            tableSubCat.setTableHeader(null);
            tableSubCat.setSelectionBackground(new Color(0, 0 , 0 , 0));
            tableSubCat.setFont(new Font("Helvetica", Font.PLAIN, 20));
            tableSubCat.setLocation(500, 200);
            tableSubCat.setVisible(true);
            //selection in table for subcategory
            tableSubCat.addMouseListener(new MouseAdapter() {  
                public void mouseEntered(MouseEvent e) {
                        setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                        setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
                public void mouseClicked(MouseEvent e) {
                        if(e.getClickCount() == 1){
                                JTable target = (JTable)e.getSource();
                                int row = target.getSelectedRow();
                                int column = target.getSelectedColumn();
                                //get subcategory number
                                String nameSubcategory;
                                nameSubcategory = (String)tableSubCat.getValueAt(row, column);
                                String nameSub = getSubNum(nameSubcategory.replaceAll("\t", ""));
                                //clear table selection
                                tableSubCat.clearSelection();
                                panelSubCat.remove(labelCatTxt);
                                panelSubCat.setVisible(false);
                                getProducts();
                                products(nameSub);
                        }}
            });
            //Scroller for table
            spSubCat = new JScrollPane(tableSubCat);
            spSubCat.setBounds(450, 200, 750, 400);
            spSubCat.setOpaque(false);
            spSubCat.getViewport().setOpaque(false);
            spSubCat.setBorder(createEmptyBorder());
            spSubCat.setVisible(true);
            //settings for panel sub cat
            panelSubCat.setSize(1300, labelBackground.getHeight());
            panelSubCat.setLayout(null);
            //panel for subcategory adds image for category label
            panelSubCat.add(labelCatTxt);
            //adds scroller with table
            panelSubCat.add(spSubCat);
            //adds background
            panelSubCat.add(labelBackground);
            panelSubCat.setVisible(true);
        }
        public void products(String nameSub){
            //name of selected subcategory
            String subCatName = nameSub;
            //get width & height of string to adjust size of label
            AffineTransform affinetransform = new AffineTransform();     
            FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
            Font font = new Font("Helvetica", Font.PLAIN, 40);
            int textwidth = (int)(font.getStringBounds(subCatName, frc).getWidth());
            int textheight = (int)(font.getStringBounds(subCatName, frc).getHeight());
            //global variable subCatTxt settings
            subCatTxt.setText(subCatName);
            subCatTxt.setFont(new Font("Helvetica", Font.PLAIN, 30));
            subCatTxt.setBounds(50, 200, textwidth, textheight);
            subCatTxt.setVisible(true);
            //dataModel for name of products
            TableModel dataModel = new AbstractTableModel() {
            //setting number of columns
            public int getColumnCount() { return 2; }
            //number of rows
            public int getRowCount() { return products.size() - 1;}
            //fetching name of products from linked list
            public Object getValueAt(int row, int col) { 
                //first column
                if (col == 0){
                    String nombre = "";
                    int num = row + 1;
                    Object base = products.get(num);
                    Product aux = (Product) base;
                    nombre +="<html> &nbsp;" + aux.productName;
                        nombre +="<html> <br>&nbsp;" + aux.productDesc;
                        nombre +="<html> <br>&nbsp;Product Key: " + aux.productKey;
                        nombre +="<html> <br>&nbsp;Subcategory ID: " + aux.subCategoryId;
                        nombre +="<html> <br>&nbsp;$" + aux.unitPrice;
                        nombre +="<html> <br>&nbsp;Owner: " + aux.userId + "</html>";
                    return nombre;
                }
                if(col == 1) {
                    Object base = products.get(row+1);
                    Product aux = (Product) base;
                    Image star = Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource("starU.png"));
                    if (isOnWishlist(aux.productId)){
                        star = Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource("starS.png"));
                    }
                    return new ImageIcon(star);
                }
                return null;
            }
            public Class getColumnClass(int col) { return getValueAt(0, col).getClass();}
          };
            //create table to display name of products
            final JTable table = new JTable(dataModel);
            //table settings
            table.setRowHeight(170);
            table.getColumnModel().getColumn(0).setPreferredWidth(600);
            table.setBackground(new Color(225, 225, 225,120));
            table.setOpaque(false);
            table.setShowGrid(false);
            table.setTableHeader(null);
            table.setSelectionBackground(new Color(0, 0 , 0 , 0));
            table.setFont(new Font("Helvetica", Font.PLAIN, 20));
            table.setLocation(200, 200);
            table.setRowSelectionAllowed(false);
            table.setColumnSelectionAllowed(false);
            //the listener will recognize the user clicking a cell
            table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        //if clicked once
                        if(e.getClickCount() == 1){
                            //get selection from table
                            JTable target = (JTable)e.getSource();
                            int row = target.getSelectedRow();
                            int column = target.getSelectedColumn();
                            //gets selection
                            if(column == 1) {
                                Image starS = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource("starS.png"));
                                ImageIcon starSicon = new ImageIcon(starS);
                                Image starU = Toolkit.getDefaultToolkit().getImage
                                (this.getClass().getResource("starU.png"));                                        
                                ImageIcon starUicon = new ImageIcon(starU);

                                //find product
                                Object base = products.get(row+1);
                                Product aux = (Product) base;
                                //add product to wish list if not in wish list
                                if(!isOnWishlist(aux.productId)){
                                    addWishList(aux.productId);
                                }
                                //if not in wish list remove
                                else{
                                    removeWishList(aux.productId);
                                }
                                //refreshes table
                                table.getValueAt(row, column);
                                table.revalidate();
                                table.repaint();
                            }
                        }
                    }
            });
            //Scroll pane to be able to view all items in table
            spProducts = new JScrollPane(table);
            //scroll settings
            spProducts.setBounds(450, 200, 750, 400);
            spProducts.setOpaque(false);
            spProducts.getViewport().setOpaque(false);
            spProducts.setBorder(createEmptyBorder());
            spProducts.setVisible(true);
            //panel products settings
            panelProducts.setSize(1300, labelBackground.getHeight());
            panelProducts.setLayout(null);
            panelProducts.add(spProducts);
            panelProducts.add(subCatTxt);
            panelProducts.add(labelBackground);
            panelProducts.setVisible(true);
        }
        public void searchResults(){
            //string of search
            String strSearch = searchRequest;
            JLabel labelSearch = new JLabel();
            //get width & height of string to adjust size of label
            AffineTransform affinetransform = new AffineTransform();     
            FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
            Font font = new Font("Helvetica", Font.PLAIN, 40);
            int textwidth = (int)(font.getStringBounds(strSearch, frc).getWidth());
            int textheight = (int)(font.getStringBounds(strSearch, frc).getHeight());
            //global variable subCatTxt settings
            labelSearch.setText(strSearch);
            labelSearch.setFont(new Font("Helvetica", Font.PLAIN, 30));
            labelSearch.setBounds(50, 200, textwidth, textheight);
           //dataModel for name of products
            TableModel dataModel = new AbstractTableModel() {
            //setting number of columns
            public int getColumnCount() { return 2; }
            //number of rows
            public int getRowCount() { return products.size() - 1;}
            //fetching name of products from linked list
            public Object getValueAt(int row, int col) {
                //first column
                if (col == 0){
                    String nombre = "";
                    int num = row + 1;
                    Object base = products.get(num);
                    Product aux = (Product) base;
                    nombre +="<html> &nbsp;" + aux.productName;
                        nombre +="<html> <br>&nbsp;" + aux.productDesc;
                        nombre +="<html> <br>&nbsp;Product Key: " + aux.productKey;
                        nombre +="<html> <br>&nbsp;Subcategory ID: " + aux.subCategoryId;
                        nombre +="<html> <br>&nbsp;$" + aux.unitPrice;
                        nombre +="<html> <br>&nbsp;Owner: " + aux.userId + "</html>";
                    return nombre;
                }
                if(col == 1) {
                    Object base = products.get(row+1);
                    Product aux = (Product) base;
                    Image star = Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource("starU.png"));
                    if (isOnWishlist(aux.productId)){
                        star = Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource("starS.png"));
                    }
                    return new ImageIcon(star);
                }
                return null;
            }
            public Class getColumnClass(int col) { return getValueAt(0, col).getClass();}
          };
            //create table to display name of products
            final JTable table = new JTable(dataModel);
            //the listener will recognize the user clicking a cell
            table.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            //if clicked once
                            if(e.getClickCount() == 1){
                                    //get selection from table
                                    JTable target = (JTable)e.getSource();
                                    int row = target.getSelectedRow();
                                    int column = target.getSelectedColumn();
                                    //gets selection
                                    if(column == 1) {
                                        Image starS = Toolkit.getDefaultToolkit().getImage
                                        (this.getClass().getResource("starS.png"));
                                        ImageIcon starSicon = new ImageIcon(starS);
                                        Image starU = Toolkit.getDefaultToolkit().getImage
                                        (this.getClass().getResource("starU.png"));                                        
                                        ImageIcon starUicon = new ImageIcon(starU);
                                        //find product
                                        Object base = products.get(row+1);
                                        Product aux = (Product) base;
                                        //add product to wish list if not in wish list
                                        if (!isOnWishlist(aux.productId)){
                                            addWishList(aux.productId);
                                        }
                                        //if not in wish list remove
                                        else{
                                            removeWishList(aux.productId);
                                        }
                                        //refreshes table
                                        table.getValueAt(row, column);
                                        table.revalidate();
                                        table.repaint();
                                    }
                            }
                    }
            });
            //table settings
            table.setRowHeight(170);
            table.getColumnModel().getColumn(0).setPreferredWidth(600);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
            table.setBackground(new Color(225, 225, 225,120));
            table.setOpaque(false);
            table.setShowGrid(false);
            table.setTableHeader(null);
            table.setSelectionBackground(new Color(0, 0 , 0 , 0));
            table.setFont(new Font("Helvetica", Font.PLAIN, 20));
            table.setLocation(200, 200);
            table.setRowSelectionAllowed(false);
            //Scroll pane to be able to view all items in table
            spSearch = new JScrollPane(table);
            //scroll settings
            spSearch.setBounds(450, 200, 750, 400);
            spSearch.setOpaque(false);
            spSearch.getViewport().setOpaque(false);
            spSearch.setBorder(createEmptyBorder());
            spSearch.setVisible(true);
            panelSearch.setSize(1300, labelBackground.getHeight());
            panelSearch.setLayout(null);
            panelSearch.setVisible(true);
            panelSearch.add(spSearch);
            panelSearch.add(subCatTxt);
            panelSearch.add(labelBackground);
        }
        
    public void account(){ 
        //user info
        TableModel dataModel1 = new AbstractTableModel() {
            //public boolean isCellEditable(int row, int col)
            //{ return true; }
        //setting number of columns
        public int getColumnCount() { return 2; }
        //number of rows
        public int getRowCount() { return 5;}
        //fetching details of user from linked list
        public Object getValueAt(int row, int col) { 
            String strTable = "\t";
            if(col == 0){
            switch(row){
                case 0:
                    strTable = "<html> <b>" + "&nbsp;" + currentUser.userFName + " "  +  currentUser.userLName + "</html>";
                    break; 
                case 1:
                    strTable += "Address: " + currentUser.address;
                    break;
                case 2:
                    strTable += "Date of birth: " + currentUser.bDate;
                    break;
                case 3:
                    strTable += "Phone number: " +currentUser.phone;
                    break;
                case 4:
                    strTable += "Email address: " + currentUser.email;
                    break;
            }
            return strTable;
            }
            
            //save or edit buton
            if(col == 1 && row == 0) {
                ImageIcon first;
                Image modify = Toolkit.getDefaultToolkit().getImage
                    (this.getClass().getResource("modifyBtn.png"));
                first = new ImageIcon(modify);
                return first;
            }

            return null;
        }
        public Class getColumnClass(int col) { return getValueAt(0, col).getClass();}
      };
         //create table to display name of products
        final JTable table1 = new JTable(dataModel1);
        table1.setRowHeight(50);
        table1.getColumnModel().getColumn(0).setPreferredWidth(275);
        table1.getColumnModel().getColumn(1).setPreferredWidth(25);
        table1.setBackground(new Color(225, 225, 225,120));
        table1.setOpaque(false);
        table1.setShowGrid(true);
        table1.setShowVerticalLines(true);
        table1.setGridColor(new Color(225, 225, 225,120));
        table1.setTableHeader(null);
        table1.setSelectionBackground(new Color(0, 0 , 0 , 0));
        table1.setFont(new Font("Helvetica", Font.PLAIN, 20));
        table1.setLocation(100, 200);
        table1.setRowSelectionAllowed(false);
        table1.setVisible(true);

        //create text fields to edit
        updateName = new JTextField(currentUser.userFName + " " + currentUser.userLName, 1);
        updateName.setFont(new Font("Helvetica", Font.PLAIN, 16));
        updateName.setForeground(Color.BLACK);
        updateName.setBounds(53, 153, 319, 44);
        updateName.setBorder(new LineBorder(new Color(255, 255, 255, 70), 10));
        updateName.setVisible(false);
        
        //create text fields to edit
        updateAddress = new JTextField(currentUser.address, 1);
        updateAddress.setFont(new Font("Helvetica", Font.PLAIN, 16));
        updateAddress.setForeground(Color.BLACK);
        updateAddress.setBounds(53, 203, 319, 44);
        updateAddress.setBorder(new LineBorder(new Color(255, 255, 255, 70), 10));
        updateAddress.setVisible(false);
        
        //create text fields to edit
        updateBday = new JTextField(currentUser.bDate, 1);
        updateBday.setFont(new Font("Helvetica", Font.PLAIN, 16));
        updateBday.setForeground(Color.BLACK);
        updateBday.setBounds(53, 253, 319, 44);
        updateBday.setBorder(new LineBorder(new Color(255, 255, 255, 70), 10));
        updateBday.setVisible(false);
        
        //create text fields to edit
        updatePhone = new JTextField(currentUser.phone, 1);
        updatePhone.setFont(new Font("Helvetica", Font.PLAIN, 16));
        updatePhone.setForeground(Color.BLACK);
        updatePhone.setBounds(53, 303, 319, 44);
        updatePhone.setBorder(new LineBorder(new Color(255, 255, 255, 70), 10));
        updatePhone.setVisible(false);
        
        //create text fields to edit
        updateEmail = new JTextField(currentUser.email, 1);
        updateEmail.setFont(new Font("Helvetica", Font.PLAIN, 16));
        updateEmail.setForeground(Color.BLACK);
        updateEmail.setBounds(53, 353, 319, 44);
        updateEmail.setBorder(new LineBorder(new Color(255, 255, 255, 70), 10));
        updateEmail.setVisible(false);

        //user info
        TableModel dataModelUPDATE = new AbstractTableModel() {
            //public boolean isCellEditable(int row, int col)
            //{ return true; }
        //setting number of columns
        public int getColumnCount() { return 2; }
        //number of rows
        public int getRowCount() { return 5;}
        //fetching details of user from linked list
        public Object getValueAt(int row, int col) { 
            String strTable = "\t";
            if(col == 0){
            switch(row){
                case 0:
                    strTable = "<html> <b>" + "&nbsp;" + currentUser.userFName + " "  +  currentUser.userLName + "</html>";
                    break; 
                case 1:
                    strTable += "Address: " + currentUser.address;
                    break;
                case 2:
                    strTable += "Date of birth: " + currentUser.bDate;
                    break;
                case 3:
                    strTable += "Phone number: " +currentUser.phone;
                    break;
                case 4:
                    strTable += "Email address: " + currentUser.email;
                    break;
            }
            return strTable;
            }
            
            
            //save or edit buton
            if(col == 1 && row == 0) {
                ImageIcon first;
                Image modify = Toolkit.getDefaultToolkit().getImage
                (this.getClass().getResource("save1.png"));
                first = new ImageIcon(modify);
                return first;
            }
            
            //cancel button
            if(col == 1 && row == 1) {
                ImageIcon second;
                second = new ImageIcon(Toolkit.getDefaultToolkit().getImage
                    (this.getClass().getResource("cancel1.png")));
                return second;
            }
            
           
            
            return null;
        }
        
        public Class getColumnClass(int col) { return getValueAt(0, col).getClass();}
      };
        
         //create table to display name of products
        final JTable tableUPDATE = new JTable(dataModelUPDATE);
        tableUPDATE.setRowHeight(50);
        tableUPDATE.getColumnModel().getColumn(0).setPreferredWidth(275);
        tableUPDATE.getColumnModel().getColumn(1).setPreferredWidth(25);
        tableUPDATE.setBackground(new Color(225, 225, 225,120));
        tableUPDATE.setOpaque(false);
        tableUPDATE.setShowGrid(true);
        tableUPDATE.setShowVerticalLines(true);
        tableUPDATE.setGridColor(new Color(225, 225, 225,120));
        tableUPDATE.setTableHeader(null);
        tableUPDATE.setSelectionBackground(new Color(0, 0 , 0 , 0));
        tableUPDATE.setFont(new Font("Helvetica", Font.PLAIN, 20));
        tableUPDATE.setLocation(100, 200);
        tableUPDATE.setRowSelectionAllowed(false);
        tableUPDATE.setVisible(true);
        
        table1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    
                    //if clicked once
                    if(e.getClickCount() == 1){
                        //get selection from table
                        JTable target = (JTable)e.getSource();
                        int row = target.getSelectedRow();
                        int column = target.getSelectedColumn();

                        //clicked on save or edit
                        if(column == 1 && row == 0) {

                            //refreshes table
                            table1.getValueAt(row, column);
                            table1.getValueAt(row+1, column);
                            table1.revalidate();
                            table1.repaint();

                            //starts edit if modifying table
                            spUserData.setVisible(false);
                            spUserDataUPDATE.setVisible(true);
                        }                              
                    }
                }
        });
        
        tableUPDATE.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e2) {
                        //if clicked once
                        if(e2.getClickCount() == 1){
                            //get selection from table
                            JTable target = (JTable)e2.getSource();
                            int row = target.getSelectedRow();
                            int column = target.getSelectedColumn();
                            
                            //clicked on save or edit
                            if(column == 1 && row == 0) {
                                System.out.println("SAVE EDIT");
                                spUserDataUPDATE.setVisible(false);
                                spUserData.setVisible(true);
                                updateName.setVisible(false);
                                updateAddress.setVisible(false);
                                updateBday.setVisible(false);
                                updatePhone.setVisible(false);
                                updateEmail.setVisible(false);
                                
                                saveEdit();
                                
                                //refresh variable
                                updateName.setText(currentUser.userFName +
                                        " " + currentUser.userLName);
                                updateAddress.setText(currentUser.address);
                                updateBday.setText(currentUser.bDate);
                                updatePhone.setText(currentUser.phone);
                                updateEmail.setText(currentUser.email);
                                
                                //refreshes table
                                tableUPDATE.getValueAt(row, column);
                                tableUPDATE.getValueAt(row+1, column);
                                tableUPDATE.revalidate();
                                tableUPDATE.repaint();
                            }
                            
                            //clicked on cancel
                            if(column == 1 && row == 1) {
                                System.out.println("CANCEL EDIT");
                                spUserDataUPDATE.setVisible(false);
                                spUserData.setVisible(true);
                                updateName.setVisible(false);
                                updateAddress.setVisible(false);
                                updateBday.setVisible(false);
                                updatePhone.setVisible(false);
                                updateEmail.setVisible(false);
                                
                                //refresh variable
                                updateName.setText(currentUser.userFName +
                                        " " + currentUser.userLName);
                                updateAddress.setText(currentUser.address);
                                updateBday.setText(currentUser.bDate);
                                updatePhone.setText(currentUser.phone);
                                updateEmail.setText(currentUser.email);
                                
                                //refreshes table
                                tableUPDATE.getValueAt(row, column);
                                tableUPDATE.getValueAt(row+1, column);
                                tableUPDATE.revalidate();
                                tableUPDATE.repaint();
                            }
                            
                            if (column == 0 && row == 0) {
                                System.out.println("EDIT NAME");
                                updateName.setVisible(true); 
                            }
                            
                            if (column == 0 && row == 1) {
                                System.out.println("EDIT ADDRESS");
                                updateAddress.setVisible(true); 
                            }
                            
                            if (column == 0 && row == 2) {
                                System.out.println("EDIT BIRTHDAY");
                                updateBday.setVisible(true); 
                            }
                            
                            if (column == 0 && row == 3) {
                                System.out.println("EDIT PHONE");
                                updatePhone.setVisible(true); 
                            }
                            
                            if (column == 0 && row == 4) {
                                System.out.println("EDIT EMAIL");
                                updateEmail.setVisible(true); 
                            }
                            
                        }
                    }
            });

        //Scroll pane to be able to view all items in table
        spUserData = new JScrollPane(table1);
        //scroll settings
        spUserData.setBounds(50, 150, 400, 250);
        spUserData.setOpaque(false);
        spUserData.getViewport().setOpaque(false);
        spUserData.setBorder(createEmptyBorder());
        spUserData.setVisible(true);
        
        //Scroll pane to be able to view all items in table
        spUserDataUPDATE = new JScrollPane(tableUPDATE);
        //scroll settings
        spUserDataUPDATE.setBounds(50, 150, 400, 250);
        spUserDataUPDATE.setOpaque(false);
        spUserDataUPDATE.getViewport().setOpaque(false);
        spUserDataUPDATE.setBorder(createEmptyBorder());
        spUserDataUPDATE.setVisible(false);
        
        //WISHLIST
        //dataModel for name of products
        TableModel dataModel2 = new AbstractTableModel() {
        //setting number of columns
        public int getColumnCount() { return 2; }
        //number of rows
        public int getRowCount() { return wishlistLinked.size();}
        //fetching name of products from linked list
        public Object getValueAt(int row, int col) {
            //first column
            if(col == 0 && row == 0)
                return "<html><b>&nbsp;Your Wishlist: </html>";
            else{
            if(wishlistLinked.size() > 1){
            if (col == 0){
                String nombre = "";
                if(row == 0)
                    nombre = "<html><b>&nbsp;Your Wishlist: </html>";
                else{
                int num = row;
                Object base = wishlistLinked.get(num);
                Product aux = (Product) base;
                nombre +="<html> &nbsp;" + aux.productName;
                    nombre +="<html> <br>&nbsp;" + aux.productDesc;
                    nombre +="<html> <br>&nbsp;Product Key: " + aux.productKey;
                    nombre +="<html> <br>&nbsp;Subcategory ID: " + aux.subCategoryId;
                    nombre +="<html> <br>&nbsp;$" + aux.unitPrice;
                    nombre +="<html> <br>&nbsp;Owner: " + aux.userId + "</html>";
                }
                return nombre;
            }
            if(col == 1 && row != 0) {
                    Object base = wishlistLinked.get(row);
                    Product aux = (Product) base;
                    Image star;
                    if (isOnWishlist(aux.productId)){
                        star = Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource("starS.png"));
                    }
                    else{
                        star = Toolkit.getDefaultToolkit().getImage
                       (this.getClass().getResource("starU.png"));
                    }
                    return new ImageIcon(star);
            }
            }

            return "";
            }
        }
        public Class getColumnClass(int col) { return getValueAt(1, col).getClass();}
      };
        //create table to display name of products
        final JTable table2 = new JTable(dataModel2);
        //the listener will recognize the user clicking a cell
        table2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                        //if clicked once
                        if(e.getClickCount() == 1){
                                //get selection from table
                                JTable target = (JTable)e.getSource();
                                int row = target.getSelectedRow();
                                int column = target.getSelectedColumn();
                                //gets selection
                                if(column == 1) {
                                    Image starS = Toolkit.getDefaultToolkit().getImage
                                    (this.getClass().getResource("starS.png"));
                                    ImageIcon starSicon = new ImageIcon(starS);
                                    Image starU = Toolkit.getDefaultToolkit().getImage
                                    (this.getClass().getResource("starU.png"));                                        
                                    ImageIcon starUicon = new ImageIcon(starU);
                                    //find product
                                    Object base = wishlistLinked.get(row);
                                    Product aux = (Product) base;
                                    //add product to wish list if not in wish list
                                    if (!isOnWishlist(aux.productId)){
                                        addWishList(aux.productId);
                                    }
                                    //if not in wish list remove
                                    else{
                                        removeWishList(aux.productId);
                                    }
                                    //refreshes table
                                    table2.getValueAt(row, column);
                                    table2.revalidate();
                                    table2.repaint();
                                }}}});
        //table settings
        table2.setRowHeight(170);
        table2.setRowHeight(0, 50);
        table2.getColumnModel().getColumn(0).setPreferredWidth(600);
        table2.getColumnModel().getColumn(1).setPreferredWidth(100);
        table2.setBackground(new Color(225, 225, 225,120));
        table2.setOpaque(false);
        table2.setShowGrid(false);
        table2.setTableHeader(null);
        table2.setSelectionBackground(new Color(0, 0 , 0 , 0));
        table2.setFont(new Font("Helvetica", Font.PLAIN, 20));
        table2.setLocation(400, 200);
        table2.setRowSelectionAllowed(false);
        table2.setVisible(true);
        //Scroll pane to be able to view all items in table
        spWishlist = new JScrollPane(table2);
        //scroll settings
        spWishlist.setBounds(500, 150, 750, 250);
        spWishlist.setOpaque(false);
        spWishlist.getViewport().setOpaque(false);
        spWishlist.setBorder(createEmptyBorder());
        spWishlist.setVisible(true);
        //find uploaded products
        ProdUploaded();
        //PRODUCTS BY USER
         //dataModel for name of products
        TableModel dataModel3 = new AbstractTableModel() {
        //setting number of columns
        public int getColumnCount() { return 2; }
        //number of rows
        public int getRowCount() { return products.size() - 1;}
        //fetching name of products from linked list
        public Object getValueAt(int row, int col) { 
            String nombre = "";
            int num;
            if(col == 0){
                if(row == 0)
                    nombre = "<html><b>&nbsp;Your Products: </html>";
                else{
                    num = row+1;
                    Object base = products.get(num);
                    Product aux = (Product) base;
                    nombre +="<html> &nbsp;" + aux.productName;
                    nombre +="<html> <br>&nbsp;" + aux.productDesc;
                    nombre +="<html> <br>&nbsp;Product Key: " + aux.productKey;
                    nombre +="<html> <br>&nbsp;Subcategory ID: " + aux.subCategoryId;
                    nombre +="<html> <br>&nbsp;$" + aux.unitPrice;
                    nombre +="<html> <br>&nbsp;Owner: " + aux.userId + "</html>";
                }
                return nombre;
            }
            if(col == 1 && row != 0) {
                    Object base = products.get(row);
                    Product aux = (Product) base;
                    Image trash;
                        trash = Toolkit.getDefaultToolkit().getImage
                        (this.getClass().getResource("trash.png"));
                    return new ImageIcon(trash);
            }
            return null;
        }
        public Class getColumnClass(int col) { return getValueAt(1, col).getClass();}
      };
        //create table to display name of products
        final JTable table3 = new JTable(dataModel3);

        table3.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                        //if clicked once
                        if(e.getClickCount() == 1){
                                //get selection from table
                                JTable target = (JTable)e.getSource();
                                int row = target.getSelectedRow();
                                int column = target.getSelectedColumn();
                                //gets selection
                                if(column == 1) {
                                    //find product
                                    Object base = products.get(row);
                                    Product aux = (Product) base;
                                    //remove product;
                                        removeOwn(aux.productId);
                                        table3.getValueAt(row, column);
                                        table3.revalidate();
                                        table3.repaint();
                                }}}});
        //table settings
        table3.setRowHeight(170);
        table3.setRowHeight(0, 50);
        table3.getColumnModel().getColumn(0).setPreferredWidth(600);
        table3.getColumnModel().getColumn(1).setPreferredWidth(100);
        table3.setBackground(new Color(225, 225, 225,120));
        table3.setOpaque(false);
        table3.setShowGrid(false);
        table3.setTableHeader(null);
        table3.setSelectionBackground(new Color(0, 0 , 0 , 0));
        table3.setFont(new Font("Helvetica", Font.PLAIN, 20));
        table3.setLocation(400, 200);
        table3.setRowSelectionAllowed(false);
        table3.setVisible(true);
        //Scroll pane to be able to view all items in table
        spOwnProduct = new JScrollPane(table3);
        //scroll settings
        spOwnProduct.setBounds(500, 450, 750, 225);
        spOwnProduct.setOpaque(false);
        spOwnProduct.getViewport().setOpaque(false);
        spOwnProduct.setBorder(createEmptyBorder());
        spOwnProduct.setVisible(true);
        panelAccount.setSize(1300, labelBackground.getHeight());
        //add to panel account
        panelAccount.add(updateName);
        panelAccount.add(updateBday);
        panelAccount.add(updateAddress);
        panelAccount.add(updatePhone);
        panelAccount.add(updateEmail);
        
        panelAccount.add(spWishlist);
        panelAccount.add(spUserData);
        panelAccount.add(spUserDataUPDATE);
        panelAccount.add(spOwnProduct);
        panelAccount.add(labelBackground);
        
        panelAccount.setVisible(true);
        panelAccount.setBorder(BorderFactory.createLineBorder(Color.red));
        panelAccount.setLayout(null);
        
        
    }
        //user logs in
        public void actionPerformed(ActionEvent e){ 
        //validate login
        username = txtFieldUser.getText();
        char[] aux = passFieldPassw.getPassword();
        password = new String(aux);
        loginValidate();
        //Change screen
        if(currentUser.userId != -1)
        {
            //change screen of login to home
            window.setVisible(false);
            //configures new screen
            home();
        }
        else{
            //change content
            label.setText("Login failed, try again");
        }
    }

    //method that saves edit
    public void saveEdit(){
        //call query to update user with update variable
        
        //update current user
        
    } 

        
    public static void main(String[] args) {
        new Robin();
    }
    /**
     * login
     * 
     * This method is called when the button <code>JButton</code>
     * is clicked or when the <code>ActionListener</code> hears to login.
     * It modifies a global variable called currentUser
     * of type <code>User</code>.
     * 
     * @param username is username typed
     * @param password is password typed
     * 
    */
    public void loginValidate( ){
        //SQL query
        String sql = "SELECT * FROM Users WHERE email=? AND pass=?";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            //check username
            pst.setString(1,username);
            //check password
            pst.setString(2,password);
            //executes query
            rs = pst.executeQuery();
            //only checks first response
            if(rs.next()){
                //finds user with fitting description and modifies global variable
                currentUser.userId = rs.getInt("userId");
                currentUser.userFName = rs.getString("userFname");
                currentUser.userLName = rs.getString("userLname");
                currentUser.pass = rs.getString("pass");
                currentUser.bDate = rs.getString("bDate");
                currentUser.address = rs.getString("address");
                currentUser.sex = (rs.getString("sex")).toUpperCase().charAt(0);
                currentUser.email = rs.getString("email");
                currentUser.phone = rs.getString("phone");
                currentUser.admin = (rs.getBoolean("admin"));
                //while captura muchas respuestas
                //while(rs.next()){
                //encontro response
                //content += rs.getString("userFname");
                //content += "-";
                //}
            }
            //error in finding database
            else {
                //make username void
                User aux = new User();
                currentUser = aux;
            }
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
    }
    /**
     * getSubCategories
     * 
     * This method is called when login is completed and categories
     * must be displayed in JFrame
     * .
     * It modifies a global <code>ArrayList</code> categories
     * of type <code>Category</code>. 
     * 
    */
    public void getSubCategories(){
        //SQL query
        String sql = "SELECT * FROM SubCategory WHERE categoryId = ?";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,Integer.toString(numCat));            
            //executes query
            rs = pst.executeQuery();
            //checks all responses
            if(rs.next()){
                //reset categories array
                subcategories = new LinkedList();
                subcategories.add(0, new SubCategory());
                //adds first category
                SubCategory aux = new SubCategory(rs.getInt("subCategoryId"), rs.getString("subCategoryDesc"),
                    rs.getInt("categoryId"));
                subcategories.add(aux);
                //while there are more categories
                while(rs.next()){
                    //found a new category
                    aux = new SubCategory(rs.getInt("subCategoryId"), rs.getString("subCategoryDesc"),
                    rs.getInt("categoryId"));
                    subcategories.add(aux);
                }
            }
            //error in finding database
            else {
                //make username void
                subcategories = new LinkedList();
                subcategories.add(0, new Category());
            }
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
    }
    /**
     * getProducts
     * 
     * This method is called when a subCategory is picked and products
     * must be displayed in JFrame
     * .
     * It modifies a global <code>ArrayList</code> categories
     * of type <code>Product</code>. 
     * 
    */
    public void getProducts(){
        //SQL query
        String sql = "SELECT * FROM Product, Users WHERE subCategoryId = ?"
                + " AND Product.userId = Users.userId";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,Integer.toString(numSub));
            //executes query
            rs = pst.executeQuery();
            //checks all responses
            if(rs.next()){
                //reset categories array
                products = new LinkedList();
                products.add(0, new Product());
                //adds first category
                Product aux = new Product(rs.getInt("productId"), rs.getString("productName"),
                    rs.getString("productDesc"),rs.getString("productKey"),
                    rs.getInt("subCategoryId"), rs.getDouble("unitPrice"),
                    (rs.getString("userFName")+" " +rs.getString("userLName")));
                products.add(aux);
                //while there are more categories
                while(rs.next()){
                    //found a new category
                    aux = new Product(rs.getInt("productId"), rs.getString("productName"),
                    rs.getString("productDesc"),rs.getString("productKey"),
                    rs.getInt("subCategoryId"), rs.getDouble("unitPrice"),
                    (rs.getString("userFName")+" " +rs.getString("userLName")));
                    products.add(aux);
                }
            }
            //error in finding database
            else {
                //make username void
                products = new LinkedList();
                products.add(0, new Category());
            }
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
    }
    /**
     * isOnWishlist
     * 
     * This method is called when a subCategory is picked and products
     * must be displayed in JFrame
     * .
     * It modifies a global <code>ArrayList</code> categories
     * of type <code>Product</code>. 
     * 
    */
    public boolean isOnWishlist(int productId){
        //SQL query
        String sql = "SELECT * " +
                     "FROM Product, WishList, Users " +
                     "WHERE WishList.userId = ? AND WishList.productId = ?";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,Integer.toString(currentUser.userId)); 
            pst.setString(2,Integer.toString(productId));
            //executes query
            rs = pst.executeQuery();
            //checks all responses
            if(rs.next()){
                return true;
            }
            //error in finding database
            else {
                return false;
            }   
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
        return false;
    }
    public String getSubNum(String name){
        //SQL query
        String sql = "SELECT * FROM SubCategory WHERE subCategoryDesc = ? AND categoryId = ?";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            pst.setString(2,Integer.toString(numCat));            
            //executes query
            rs = pst.executeQuery();
            //checks all responses
            if(rs.next()){
                //reset categories array
                numSub = rs.getInt("subCategoryId");
                return rs.getString("subCategoryDesc");
            }
            //error in finding database
            else {
                //make username void
                numSub = 44;
                return "";
            }
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
        return "";
    }
    /**
     * search
     * 
     * This method is called when something is searched and modifies
     * product array
     * .
     * It modifies a global <code>ArrayList</code> categories
     * of type <code>Product</code>. 
     * 
    */
    public void search(){
        //SQL query
        String sql = "SELECT * " +
                     "FROM Product, Users " +
                     "WHERE Product.userId = Users.userId AND productName LIKE ?";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,'%'  + searchRequest + '%');
            //executes query
            rs = pst.executeQuery();
            //checks all responses
            if(rs.next()){
                //reset categories array
                products = new LinkedList();
                products.add(0, new Product());
                //adds first category
                Product aux = new Product(rs.getInt("productId"), rs.getString("productName"),
                    rs.getString("productDesc"),rs.getString("productKey"),
                    rs.getInt("subCategoryId"), rs.getDouble("unitPrice"),
                    (rs.getString("userFName")+" " +rs.getString("userLName")));
                products.add(aux);
                //while there are more categories
                while(rs.next()){
                    //found a new category
                    aux = new Product(rs.getInt("productId"), rs.getString("productName"),
                    rs.getString("productDesc"),rs.getString("productKey"),
                    rs.getInt("subCategoryId"), rs.getDouble("unitPrice"),
                    (rs.getString("userFName")+" " +rs.getString("userLName")));
                    products.add(aux);
                }
            }
            //error in finding database
            else {
                //make username void
                products = new LinkedList();
                products.add(0, new Category());
            }
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
    }
    public void ProdUploaded( ){
        //SQL query
        String sql = "SELECT * " +
                     "FROM Product, Users " +
                     "WHERE Users.userId = ? AND Users.userId = Product.UserId";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,Integer.toString(currentUser.userId));
            //executes query
            rs = pst.executeQuery();
            //checks all responses
            if(rs.next()){
                //reset categories array
                products = new LinkedList();
                products.add(0, new Product());
                //adds first category
                Product aux = new Product(rs.getInt("productId"), rs.getString("productName"),
                    rs.getString("productDesc"),rs.getString("productKey"),
                    rs.getInt("subCategoryId"), rs.getDouble("unitPrice"),
                    (rs.getString("userFName")+" " +rs.getString("userLName")));
                products.add(aux);
                //while there are more categories
                while(rs.next()){
                    //found a new category
                    aux = new Product(rs.getInt("productId"), rs.getString("productName"),
                    rs.getString("productDesc"),rs.getString("productKey"),
                    rs.getInt("subCategoryId"), rs.getDouble("unitPrice"),
                    (rs.getString("userFName")+" " +rs.getString("userLName")));
                    products.add(aux);
                }
            }
            //error in finding database
            else {
                //make username void
                products = new LinkedList();
                products.add(0, new Category());
            }
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
    }
    public void userWishlist( ){
        //SQL query
        String sql = "SELECT P.productId AS pId, \n" +
                            "P.productName AS pName, \n" +
                            "P.productDesc AS pDesc, \n" +
                            "P.productKey AS pKey, \n" +
                            "P.subCategoryId AS pSub, \n" +
                            "P.unitPrice AS unitP, \n" +
                            "U.userFName AS userF, \n" +
                            "U.userLName AS userL \n" +
                            "FROM WishList W, Product P, Users U\n" +
                            "WHERE W.productId = P.productId AND U.userId = P.userId AND W.userId = ? ";
                try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,Integer.toString(currentUser.userId));
            //executes query
            rs = pst.executeQuery();
            //checks all responses
            if(rs.next()){
                //reset products array
                wishlistLinked = new LinkedList();
                wishlistLinked.add(0, new Product());
                //adds first category
                Product aux = new Product(rs.getInt("pId"), rs.getString("pName"),
                    rs.getString("pDesc"),rs.getString("pKey"),
                    rs.getInt("pSub"), rs.getDouble("unitP"),
                    (rs.getString("userF")+" " +rs.getString("userL")));
                    wishlistLinked.add(aux);
                //while there are more categories
                while(rs.next()){
                    //found a new category
                    aux = new Product(rs.getInt("pId"), rs.getString("pName"),
                    rs.getString("pDesc"),rs.getString("pKey"),
                    rs.getInt("pSub"), rs.getDouble("unitP"),
                    (rs.getString("userF")+" " +rs.getString("userL")));
                    wishlistLinked.add(aux);
                }
            }
            //error in finding database
            else {
                //make username void
                wishlistLinked = new LinkedList();
                wishlistLinked.add(0, new Category());
            }
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
    }
    public void addWishList(int productKey){
        //SQL query
        String sql = "INSERT INTO WishList (productId, userId)" +
                     "VALUES(?, ?);";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,Integer.toString(productKey)); 
            pst.setString(2,Integer.toString(currentUser.userId)); 
            //executes query
            pst.executeUpdate();
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        }
    }
        public void removeWishList(int productKey){
        //SQL query
        String sql = "DELETE FROM WishList WHERE productId = ? AND userId = ?";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,Integer.toString(productKey)); 
            pst.setString(2,Integer.toString(currentUser.userId));  
            //executes query
            pst.executeUpdate();
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        } 
        userWishlist();
    }
        public void removeOwn(int productKey){
        //SQL query
        String sql = "DELETE FROM Product WHERE productId = ? AND userId = ?";
        try {
            //make sql script
            pst = conn.prepareStatement(sql);
            pst.setString(1,Integer.toString(productKey)); 
            pst.setString(2,Integer.toString(currentUser.userId));  
            //executes query
            pst.executeUpdate();
        }
        //error in database
        catch (SQLException ex){
            //displays error
            JOptionPane.showMessageDialog(null,ex);
        }
        ProdUploaded();
    }
}