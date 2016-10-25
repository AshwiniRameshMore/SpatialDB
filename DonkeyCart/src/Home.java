import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

class ShapeDetails
{
    public int sId = 0;
    public String sType = null;
    public float sPoints[] = null;
    
    public String toString(){
        String coordinates = "";
        for(int i=0;i<sPoints.length;i++)
            coordinates += sPoints[i] + ", ";
         return "sId = " + sId + " sPoints = " + coordinates;
    }
}

@SuppressWarnings("serial")
public class Home extends JFrame {

	protected static Connection con = ConnectDatabase.getConnection();
    protected ArrayList<ShapeDetails> shapeList = null;
    protected static FrontPanel panel = null;
    
    protected void frameInit() {
        super.frameInit();                
        try{
            makeList();
            this.setBounds(0,0, 700, 800);
            JMenuBar menubar = new JMenuBar();
            this.setJMenuBar(menubar);
            panel = new FrontPanel();
            panel.refreshList(shapeList);
            panel.setBackground(Color.WHITE);
            
            JLabel label = new JLabel();
            label.setText("Welcome to Donkey Cart");
            label.setFont(new Font("Serif", Font.BOLD, 22));
            label.setForeground(Color.BLUE);
            label.setLocation(200, 100);
            panel.add(label);
            
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setPreferredSize(new Dimension(360,350));            
            topPanel.add(panel);
            
            JPanel bottomPanel = new JPanel(new FlowLayout());
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            bottomPanel.setBackground(Color.lightGray);
            
            JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitter.setBottomComponent(buildBottom(bottomPanel));
            splitter.setTopComponent(topPanel);
            splitter.setBackground(Color.BLUE);
            getContentPane().add(splitter, BorderLayout.CENTER);                 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void makeList(){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        java.sql.Struct geometryObject = null;
        oracle.sql.ARRAY coordinates = null;
        
        try{
            shapeList = new ArrayList<ShapeDetails>();
            String strQuery = "SELECT * FROM donkeycart order by shapeId";
            statement = con.prepareStatement(strQuery);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                ShapeDetails contentObj = new ShapeDetails();
                contentObj.sId = resultSet.getInt(1);
                 String type = resultSet.getString(2);
                if("donkeyLoad".equalsIgnoreCase(type) || "donkeyLoad1".equalsIgnoreCase(type) ||
                   "donkeyXbox".equalsIgnoreCase(type) || "donkeyMbox".equalsIgnoreCase(type) ||
                   "donkeyAsbox".equalsIgnoreCase(type) || "new_entry".equalsIgnoreCase(type))
                	contentObj.sType = "rectangle";
                else if("donkeyDigit".equalsIgnoreCase(type) || "donkeyDigit1".equalsIgnoreCase(type) || 
                		"donkeyDigit2".equalsIgnoreCase(type) || "donkeyDigit3".equalsIgnoreCase(type) || 
                		"donkeyDigit4".equalsIgnoreCase(type) || "donkeyDigit5".equalsIgnoreCase(type) || 
                		"donkeyDigit6".equalsIgnoreCase(type) || "donkeyDigit7".equalsIgnoreCase(type) || 
                		"donkeyDigit8".equalsIgnoreCase(type) || "donkeyTree".equalsIgnoreCase(type) || 
                		"donkeyPic4".equalsIgnoreCase(type) || "donkeyPic5".equalsIgnoreCase(type) || 
                		"donkeyPic7".equalsIgnoreCase(type) || "donkeyPic8".equalsIgnoreCase(type) || 
                		"donkeyPic10".equalsIgnoreCase(type) || "donkeyPic13".equalsIgnoreCase(type) || 
                		"donkeyPic14".equalsIgnoreCase(type) || "donkeyPic16".equalsIgnoreCase(type) || 
                		"donkeyPic17".equalsIgnoreCase(type) || "donkeyPic18".equalsIgnoreCase(type) || 
                		"donkeyPic19".equalsIgnoreCase(type) || "donkeyPic22".equalsIgnoreCase(type) || 
                		"donkeyPic24".equalsIgnoreCase(type) || "donkeyPic25".equalsIgnoreCase(type) || 
                		"donkeyPic28".equalsIgnoreCase(type))
                	contentObj.sType = "line";
                else if("donkeyDigit9".equalsIgnoreCase(type) || "donkeyBird1".equalsIgnoreCase(type) || 
                		"donkeyBird2".equalsIgnoreCase(type))
                	contentObj.sType = "bird";
                else if("donkeyTree1".equalsIgnoreCase(type) || "donkeyTree2".equalsIgnoreCase(type) ||
                        "donkeyTree3".equalsIgnoreCase(type) || "donkeyTree4".equalsIgnoreCase(type) ||
                        "donkeyTree5".equalsIgnoreCase(type) || "donkeyTree6".equalsIgnoreCase(type) ||
                        "donkeyTree7".equalsIgnoreCase(type) || "donkeyTree8".equalsIgnoreCase(type) ||
                        "donkeyPic1".equalsIgnoreCase(type) || "donkeyPic2".equalsIgnoreCase(type) ||
                        "donkeyPic3".equalsIgnoreCase(type) || "donkeyPic6".equalsIgnoreCase(type) ||
                        "donkeyPic11".equalsIgnoreCase(type) || "donkeyPic12".equalsIgnoreCase(type) ||
                        "donkeyPic9".equalsIgnoreCase(type) || "donkeyPic15".equalsIgnoreCase(type) ||
                        "donkeyPic20".equalsIgnoreCase(type) || "donkeyPic21".equalsIgnoreCase(type) ||
                        "donkeyPic23".equalsIgnoreCase(type) || "donkeyPic26".equalsIgnoreCase(type))
                	contentObj.sType = "arc";
                else if("donkeyCircle".equalsIgnoreCase(type) || "donkeyPic27".equalsIgnoreCase(type))
                 	contentObj.sType = "circle";
                else
                	contentObj.sType = resultSet.getString(2);
                System.out.print(resultSet.getString(2)+ " ");
                geometryObject = (java.sql.Struct) resultSet.getObject(3);                
                coordinates = (oracle.sql.ARRAY) geometryObject.getAttributes()[4];
                float[] l_tempArray = coordinates.getFloatArray();
                contentObj.sPoints = new float[l_tempArray.length];
                for(int i=0;i<l_tempArray.length;i+=2)
                {
                    contentObj.sPoints[i] = l_tempArray[i];
                    contentObj.sPoints[i+1] =100-l_tempArray[i+1];
                }
                if(contentObj.sType == "rect1")
                   	contentObj.sType = "rect";
               
                shapeList.add(contentObj);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{                
                if(resultSet != null)            
                    resultSet.close();
                if(statement != null)            
                    statement.close();
            } catch (SQLException e) {
                    e.printStackTrace();
            }
        }
    }
    private JPanel buildBottom(JPanel bottomPanel){
                
    	 JButton areaButton = new JButton("Area");
         areaButton.setBackground(Color.BLACK);
         areaButton.setForeground(Color.WHITE);
         bottomPanel.add(areaButton);
         areaButton.addActionListener(new AreaListener());
         areaButton.setSize(150, 30);
         areaButton.setLocation(160, 100);
         
         JButton containsButton = new JButton("Contains");
         containsButton.setBackground(Color.BLACK);
         containsButton.setForeground(Color.WHITE);
         bottomPanel.add(containsButton);
         containsButton.addActionListener(new ContainsListener());
         containsButton.setSize(150, 30);
         containsButton.setLocation(360, 100);
         
         JButton coversButton = new JButton("Distance Within");
         coversButton.setBackground(Color.BLACK);
         coversButton.setForeground(Color.WHITE);
         bottomPanel.add(coversButton);
         coversButton.addActionListener(new DistanceWithinListener());
         coversButton.setSize(150, 30);
         coversButton.setLocation(160, 200);
         
         JButton disjointButton = new JButton("Disjoint");
    	disjointButton.setBackground(Color.BLACK);
        disjointButton.setForeground(Color.WHITE);
        bottomPanel.setLayout(null);
        bottomPanel.add(disjointButton);
        disjointButton.addActionListener(new DisjointListener());
        disjointButton.setSize(150, 30);
        disjointButton.setLocation(160, 150);
        
        JButton insideButton = new JButton("Inside");
        insideButton.setBackground(Color.BLACK);
        insideButton.setForeground(Color.WHITE);
        bottomPanel.add(insideButton);
        insideButton.addActionListener(new InsideListener());
        insideButton.setSize(150, 30);
        insideButton.setLocation(160, 250);
        
        JButton touchButton = new JButton("Touch");
    	touchButton.setBackground(Color.BLACK);
        touchButton.setForeground(Color.WHITE);
        bottomPanel.add(touchButton);
        touchButton.addActionListener(new TouchListener());
        touchButton.setSize(150, 30);
        touchButton.setLocation(160, 300);
        
        JButton insertButton = new JButton("Insert");
    	insertButton.setBackground(Color.BLACK);
        insertButton.setForeground(Color.WHITE);
        bottomPanel.add(insertButton);
        insertButton.addActionListener(new InsertListener());
        insertButton.setSize(150, 30);
        insertButton.setLocation(160, 50);
        
        JButton deleteButton = new JButton("Delete");
    	deleteButton.setBackground(Color.BLACK);
        deleteButton.setForeground(Color.WHITE);
        bottomPanel.add(deleteButton);
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setSize(150, 30);
        deleteButton.setLocation(360, 50);
        
        JButton unionButton = new JButton("Union");
    	unionButton.setBackground(Color.BLACK);
        unionButton.setForeground(Color.WHITE);
        bottomPanel.add(unionButton);
        unionButton.addActionListener(new UnionListener());
        unionButton.setSize(150, 30);
        unionButton.setLocation(360, 300);
        
        JButton intersectButton = new JButton("Intersection");
        intersectButton.setBackground(Color.BLACK);
        intersectButton.setForeground(Color.WHITE);
        bottomPanel.add(intersectButton);
        intersectButton.addActionListener(new IntersectionListener());
        intersectButton.setSize(150, 30);
        intersectButton.setLocation(360, 250);
        
        JButton equalButton = new JButton("Equal");
        equalButton.setBackground(Color.BLACK);
        equalButton.setForeground(Color.WHITE);
        bottomPanel.add(equalButton);
        equalButton.addActionListener(new EqualListener());
        equalButton.setSize(150, 30);
        equalButton.setLocation(360, 200);
        
        JButton distanceButton = new JButton("Distance");
        distanceButton.setBackground(Color.BLACK);
        distanceButton.setForeground(Color.WHITE);
        bottomPanel.add(distanceButton);
        distanceButton.addActionListener(new DistanceListener());
        distanceButton.setSize(150, 30);
        distanceButton.setLocation(360, 150);
    
      return bottomPanel;
    }
    
    public void drawPoly(Graphics arg0, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        int[] x = new int[4];
        int[] y = new int[4];
        x[0] = x1;
        y[0] = y1;
        x[1] = x2;
        y[1] = y2;
        x[2] = x3;
        y[2] = y3;
        x[3] = x4;
        y[3] = y4;
        arg0.drawPolygon(x, y, x.length);
    }
    
    public static void main(String args[]){
        Home objHome = new Home();
        ClosingListener objAdaptor = objHome.new ClosingListener();
        try {
            objHome.addWindowListener(objAdaptor);
            objHome.setVisible(true);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - objHome.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - objHome.getHeight()) / 2);
            objHome.setLocation(x, y);
           }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void finalize(){
        try{
            if(con != null)
               con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public class ClosingListener extends WindowAdapter{
        public void windowClosing(WindowEvent we) {
             System.exit(0);
        }
    }
    
    private class InsertListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strQuery = "INSERT INTO donkeycart VALUES (56,'new_entry',MDSYS.SDO_GEOMETRY "
            		+ "(2003,null,null,MDSYS.SDO_ELEM_INFO_ARRAY(1,1003,3),MDSYS.SDO_ORDINATE_ARRAY(14,8, 21,12)))";

            PreparedStatement pstmt = null;
             try{                
                pstmt  = con.prepareStatement(strQuery);
                pstmt.execute();
                makeList();
                panel.refreshList(shapeList);
                panel.removeAll();
                panel.updateUI();                
            }catch(Exception ex){
                ex.printStackTrace();
            }           
        }
    }
    
    private class DeleteListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strQuery = "DELETE FROM donkeycart WHERE shapeId=56";
            PreparedStatement pstmt = null;
            try{
                    pstmt  = con.prepareStatement(strQuery);
                    pstmt.execute();
                    makeList();
                    panel.refreshList(shapeList);
                    panel.removeAll();
                    panel.updateUI();
            }catch(Exception ex){
                ex.printStackTrace();
            }   
        }     
    }
    
    private class DisjointListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strSelectQuery = "SELECT SDO_GEOM.RELATE(dc1.shape, 'Disjoint', dc2.shape, 0.005)"
            		+ "FROM donkeycart dc1, donkeycart dc2 WHERE  dc1.shapeId = 2 AND dc2.shapeId = 4";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                JLabel label = new JLabel();
                pstmt  = con.prepareStatement(strSelectQuery);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    String strContains = rs.getString(1);
                    if("DISJOINT".equalsIgnoreCase(strContains))
                        strContains = "ARE DISJOINT";
                    else
                        strContains = "ARE NOT DISJOINT";
                    label.setText("Box 'AS' and Box 'X' " + strContains);
                    label.setFont(new Font("Serif", Font.BOLD, 22));
                    label.setForeground(Color.BLUE);
                    panel.removeAll();
                    panel.add(label);                    
                    panel.updateUI();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }    
    }
    
    private class InsideListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strSelectQuery = "SELECT SDO_GEOM.RELATE(dc1.shape, 'Inside', dc2.shape, 0.005)"
            		+ "FROM donkeycart dc1, donkeycart dc2 WHERE  dc1.shapeId = 2 AND dc2.shapeId = 3";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                JLabel label = new JLabel();
                pstmt  = con.prepareStatement(strSelectQuery);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    String strContains = rs.getString(1);
                    if("TRUE".equalsIgnoreCase(strContains))
                        strContains = "is INSIDE";
                    else
                        strContains = "is NOT INSIDE";
                    label.setText("Rectangular Box X " + strContains + " Rectangular Box M.");
                    label.setFont(new Font("Serif", Font.BOLD, 22));
                    label.setForeground(Color.BLUE);
                    panel.removeAll();
                    panel.add(label);                    
                    panel.updateUI();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }      
    }
    
    private class UnionListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strSelectQuery = "Select SDO_GEOM.SDO_UNION(dc1.shape , dc2.shape , 0.005)"
            		+ "FROM donkeycart dc1,donkeycart dc2 WHERE dc1.shapeId = 2 AND dc2.shapeId = 3";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                Graphics g = getGraphics();
                pstmt  = con.prepareStatement(strSelectQuery);
                rs = pstmt.executeQuery();
                while(rs.next()){
                    java.sql.Struct o6 = (java.sql.Struct)rs.getObject(1);
                    oracle.sql.ARRAY oa = (oracle.sql.ARRAY)o6.getAttributes()[4];
                    int len = oa.length();
                    int[] ia = oa.getIntArray();
                    int[] x6 = new int[100];
                    int j6 = 0;
                    int k6 = 0;
                    int[] y6 = new int[100];
                    for (int i6 = 0 ; i6<len-2 ; i6+=2)
                    {
                      x6[j6] = 10*ia[i6]+29;
                      System.out.println(x6[j6]);
                      j6++;
                    }
                    for (int i6= 1; i6<len-2; i6+=2)
                    {
                      y6[k6] = (50 - ia[i6])*7-16;
                      System.out.println(y6[k6]);
                      k6++;
                    }
                    g.setColor(Color.red);
                    g.fillPolygon(x6, y6, (len - 2)/2);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
    }
    
    private class IntersectionListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strSelectQuery = "Select SDO_GEOM.SDO_INTERSECTION(dc1.shape , dc2.shape , 0.0005) "
            		+ "FROM donkeycart dc1,donkeycart dc2 WHERE dc1.shapeId = 4 AND dc2.shapeId = 56";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                Graphics g = getGraphics();
                pstmt  = con.prepareStatement(strSelectQuery);
                rs = pstmt.executeQuery();
                while(rs.next()){
                    java.sql.Struct o6 = (java.sql.Struct)rs.getObject(1);
                    oracle.sql.ARRAY oa = (oracle.sql.ARRAY)o6.getAttributes()[4];
                    int len = oa.length();
                    int[] ia = oa.getIntArray();
                    int[] x6 = new int[100];
                    int j6 = 0;
                    int k6 = 0;
                    int[] y6 = new int[100];
                    for (int i6 = 0 ; i6<len-2 ; i6+=2)
                    {
                      x6[j6] = 10*ia[i6]+29;
                      System.out.println(x6[j6]);
                      j6++;
                    }
                    for (int i6= 1; i6<len-2; i6+=2)
                    {
                      y6[k6] = (50 - ia[i6])*7-16;
                      System.out.println(y6[k6]);
                      k6++;
                    }
                    g.setColor(Color.green);
                    g.fillPolygon(x6, y6, (len - 2)/2);
               }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private class ContainsListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String query = "SELECT SDO_GEOM.RELATE(dc1.shape, 'Contains', dc2.shape, 0.005)"
            		+ "FROM donkeycart dc1, donkeycart dc2 WHERE  dc1.shapeId = 17 AND dc2.shapeId = 53";
            PreparedStatement stmt = null;
            ResultSet resultSet = null;
            try{
                JLabel label = new JLabel();
                stmt  = con.prepareStatement(query);
                resultSet = stmt.executeQuery();
                if(resultSet.next()){
                    String strContains = resultSet.getString(1);
                    if("TRUE".equalsIgnoreCase(strContains))
                        strContains = "CONTAINS";
                    else
                        strContains = "DOES NOT CONTAIN";
                    label.setText("Cart Wheel " + strContains + " Eye of the donkey.");
                    label.setFont(new Font("Serif", Font.BOLD, 22));
                    label.setForeground(Color.BLUE);
                    panel.removeAll();
                    panel.add(label);                    
                    panel.updateUI();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
     }
    
    private class DistanceWithinListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strSelectQuery = "SELECT SDO_GEOM.WITHIN_DISTANCE(dc1.shape,120,dc2.shape, 0.005)"
            		+ "FROM donkeycart dc1, donkeycart dc2 WHERE  dc1.shapeId = 17 AND dc2.shapeId = 53";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                JLabel label = new JLabel();
                pstmt  = con.prepareStatement(strSelectQuery);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    String strContains = rs.getString(1);
                    if("FALSE".equalsIgnoreCase(strContains))
                        strContains = "is NOT within distance 120";
                    else
                        strContains = "is within distance 120";
                    label.setText("Eye of the donkey " + strContains + " from cart wheel.");
                    label.setFont(new Font("Serif", Font.BOLD, 22));
                    label.setForeground(Color.BLUE);
                    panel.removeAll();
                    panel.add(label);                    
                    panel.updateUI();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private class DistanceListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strSelectQuery = "Select SDO_GEOM.SDO_DISTANCE(dc1.shape,dc2.shape,0.005) "
            		+ "FROM donkeycart dc1, donkeycart dc2 WHERE dc1.shapeId = 17 and dc2.shapeId = 53";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                JLabel label = new JLabel();
                pstmt  = con.prepareStatement(strSelectQuery);
                rs = pstmt.executeQuery();
                if(rs.next())
                {
                    double dblArea = rs.getDouble(1);
                    label.setText("Distance between cart wheel and eye of the donkey : " + Math.round(dblArea*5));
                    label.setFont(new Font("Serif", Font.BOLD, 22));
                    label.setForeground(Color.BLUE);
                    panel.removeAll();
                    panel.add(label);                    
                    panel.updateUI();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private class AreaListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String area_string = "Select SDO_GEOM.SDO_AREA(shape ,0.005) "
            		+ "FROM donkeycart WHERE shapeId=4";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                JLabel label = new JLabel();
                pstmt  = con.prepareStatement(area_string);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    double dblArea = rs.getDouble(1);
                    label.setText("Area of box containing 'AS' is " + (dblArea*8));
                    label.setFont(new Font("Serif", Font.BOLD, 22));
                    label.setForeground(Color.BLUE);
                    panel.removeAll();
                    panel.add(label);                    
                    panel.updateUI();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
     }
    
    private class TouchListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strSelectQuery = "SELECT SDO_GEOM.RELATE(dc1.shape, 'TOUCH', dc2.shape, 0.5)"
            		+ "FROM donkeycart dc1, donkeycart dc2 WHERE  dc1.shapeId=2 AND dc2.shapeId=3";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                JLabel label = new JLabel();
                pstmt  = con.prepareStatement(strSelectQuery);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    String strContains = rs.getString(1);
                    label.setText("Box X " + strContains + " Box M.");
                    label.setFont(new Font("Serif", Font.BOLD, 22));
                    label.setForeground(Color.BLUE);
                    panel.removeAll();
                    panel.add(label);                    
                    panel.updateUI();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
     }
    
    private class EqualListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String strSelectQuery = "SELECT SDO_GEOM.RELATE(dc1.shape, 'equal', dc2.shape, 0.005)"
            		+ "FROM donkeycart dc1, donkeycart dc2 WHERE  dc1.shapeId =2  AND dc2.shapeId = 3";
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try{
                JLabel label = new JLabel();
                pstmt  = con.prepareStatement(strSelectQuery);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    String strContains = rs.getString(1);
                    if("FALSE".equalsIgnoreCase(strContains))
                        strContains = "IS Not Equal to";
                    else
                        strContains = "IS Equal to";
                    label.setText("X Box " + strContains + " M Box.");
                    label.setFont(new Font("Serif", Font.BOLD, 22));
                    label.setForeground(Color.BLUE);
                    panel.removeAll();
                    panel.add(label);                    
                    panel.updateUI();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}