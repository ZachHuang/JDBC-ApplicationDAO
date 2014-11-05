import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class applicationDAO {

	public Connection getConnection() {
		Connection connection= null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionUrl = "jdbc:mysql://localhost:3306/onlineide";
			connection = DriverManager.getConnection(connectionUrl, "root", "");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;		
	}
	
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void create(Application entity){
		String sql = "insert into application(name,price) values(?,?)";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getName());
			statement.setDouble(2, entity.getPrice());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection(connection);
			
		}
		
	}
	
	
	public List<Application> selectAll(){
		List<Application> applications = new ArrayList<Application>();
		String sql = "select * from application";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			while(results.next()){
				int id = results.getInt("id");
				String name = results.getString("name");
				double price = results.getDouble("price");
				Application app = new Application(id, price,name);
				applications.add(app);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection(connection);
		}
		return applications;
	}
	
	public void remove(int id){
		String sql = "delete from application where id =?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1,id);
			statement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection(connection);
			
		}
		
	}
	public Application selectOne(int id){
		Application application = new Application();
		String sql = "select * from Application where id =?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet results = statement.executeQuery();
			if(results.next()){
				id = results.getInt("id");
				String name = results.getString("name");
				double price = results.getDouble("Price");
				application = new Application(id,price,name);			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection(connection);			
		}
		return application;
		
	}
	
	public void update(int id, Application entity){
		Connection connection = getConnection();
		String sql ="update application set name =?,price =? where id =?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getName());
			statement.setDouble(2,entity.getPrice());
			statement.setInt(3,entity.getId());
			statement.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
			
		}
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		applicationDAO dao = new applicationDAO();
		Application app1 = new Application(2.99,"tic tac toe");
		Application app2 = new Application(4.50,"zach");
		Application app3 = new Application(3.75,"Zach's previous GPA");
		Application app4 = new Application(3.80,"zach's future GPA");
		dao.remove(2);
		dao.remove(3);
		dao.remove(4);
		dao.create(app2);
		dao.create(app3);
		dao.create(app4);
		dao.create(app1);
		
	}

}
