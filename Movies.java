
import java.sql.Connection;        
import java.sql.DriverManager;     
import java.sql.PreparedStatement; 
import java.sql.ResultSet;        
import java.sql.SQLException;    
import java.sql.Statement;  




public class Movies {

	private Connection connect() { 
		Connection conn=null;
			
		try
        {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:movies.db");
            conn.setAutoCommit(false);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
        return conn;

			}
			
		
	public void createTable() {
		String sql= "CREATE TABLE MOVIES " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           VARCHAR(20)    NOT NULL, " + 
                " ACTOR          VARCHAR(20)     NOT NULL, " +
                " ACTRESS        VARCHAR(20)    NOT NULL, " + 
                " DIRECTOR        VARCHAR(20)     NOT NULL),"+
                " YEAROFRELEASE  CHAR(4))"; 
		   try
		   {
			   Connection conn=this.connect();
			   Statement st=conn.createStatement();
			   st.execute(sql);
		   }
		   catch(SQLException e) {
			   System.out.println(e.getMessage());
		   }
		}
	
	
	
	public void insert(int id,String name,String actor,String actress,String director,String year) {
		String info ="INSERT INTO MOVIES(ID,NAME,ACTOR,ACTRESS,DIRECTOR,YEAROFRELEASE) VALUES(?,?,?,?,?,?)"; 
		try {
			Connection conn=this.connect();
			
			PreparedStatement pst=conn.prepareStatement(info);
            pst.setInt(1,id);
            pst.setString(2,name);
            pst.setString(3,actor);
            pst.setString(4,actress);
            pst.setString(5,name);
            pst.setString(6,year);
            pst.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	
	public void selectAll() 
    {

        try
        {
            Connection conn =this.connect();
            Statement st = conn.createStatement();
            ResultSet rs= st.executeQuery("SELECT * FROM MOVIES;");
            while(rs.next())
            {
                int id=rs.getInt("ID");
                String name=rs.getString("NAME");
                String actor=rs.getString("ACTOR");
                String actress=rs.getString("ACTRESS");
                String year=rs.getString("YEAROFRELEASE");
                String director=rs.getString("DIRECTOR");
                
                System.out.println("MOVIE ID = "+id);
                System.out.println("MOVIE NAME = "+name);
                System.out.println("ACTOR NAME = "+actor);
                System.out.println("ACTRESS NAME = "+actress);
                System.out.println("DIRECTOR NAME = "+director);
                System.out.println("YEAR OF RELEASE = "+year);
                System.out.println();
            }
            rs.close();
            st.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
	
	

    public void selectparam() 
    {

        try
        {
            Connection conn =this.connect();
            Statement st = conn.createStatement();
            ResultSet rs= st.executeQuery("SELECT NAME FROM MOVIES WHERE ACTOR='YASH':");
            System.out.println("The movies in which Yash is a Actor: ");
            while(rs.next())
            {
                String name=rs.getString("NAME");
                System.out.println(name);
            }
            rs.close();
            st.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
	
	
	
	public static void main(String[] args) {
		Movies list =new Movies();
        list.createTable();
        list.insert(1,"SAAHO","PRABHAS","SHRADDHA","SUJEETH","2019");
        list.insert(2,"URI","VICKY","YAMI","ADITYA","2019");
        list.insert(3,"KGF2","YASH","SRINIDHI","PRASHANTH","2022");
        list.insert(4,"SPIDER MAN NO WAY HOME","TOM HOLLAND","ZENDAYA","JOHN WATTS","2021");
        list.selectAll();
        list.selectparam();
	}

}
