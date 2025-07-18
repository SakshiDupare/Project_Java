package test;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Callable;
public class Calleble_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try 
		{
		
			Scanner sc = new Scanner(System.in);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","sak123");
			
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select cust_seq.nextval from dual");
			int cid =0;
			if(rs.next())
			{
				cid = rs.getInt(1);
			}
			
			System.out.println("\nEnter the following customer Details:");
			System.out.println("\nEnter your first name:");
			String fn= sc.nextLine();
			System.out.println("\nEnter your Last name:");
			String ln= sc.nextLine();
			System.out.println("\nEnter your House no.:");
			int hn = sc.nextInt();
			sc.nextLine();
			System.out.println("\n Enter the street name:");
			String st = sc.nextLine();
			System.out.println("\nEnter the name of City:");
			String ct = sc.nextLine();
			System.out.println("\nEnter pincode of your place:");
			int pc = sc.nextInt();
			sc.nextLine();
			System.out.println("\nEnter your mobile no.:");
			Long mbno = sc.nextLong();
			sc.nextLine();
			System.out.println("\nEnter your Email:");
			String el = sc.nextLine();
			
			CallableStatement cs = con.prepareCall("{call Customer(?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, cid);
			cs.setString(2, fn);
			cs.setString(3, ln);
			cs.setInt(4, hn);
			cs.setString(5, st);
			cs.setString(6, ct);
			cs.setInt(7, pc);
			cs.setLong(8, mbno);
			cs.setString(9, el);

			cs.execute();
			System.out.println("\nData inserted Successfully in the DB");
			
			ResultSet rs_detail = stm.executeQuery("select * from Customer_Details");
			System.out.println("\nCustomer Details Table");
			System.out.println("--------------------------------------------------------------");
			System.out.println("CustomerId\tFirstName\tLastName");
			while(rs_detail.next())
			{
				System.out.println(rs_detail.getInt(1)+"\t\t"+rs_detail.getString(2)+"\t\t"+rs_detail.getString(3));
			}
			rs_detail.close();
			
			ResultSet rs_add = stm.executeQuery("select * from Customer_add");
			System.out.println("\nCustomer Address Table");
			System.out.println("--------------------------------------------------------------");
			System.out.println("CustomerId\ttHouseNo\tStreet_name\tCity\tPincode\n");
		
			while(rs_add.next())
			{
				System.out.println(rs_add.getInt(1)+"\t\t"+rs_add.getInt(2)+"\t\t"+rs_add.getString(3)+"\t\t"+rs_add.getString(4)+"\t"+
			rs_add.getInt(5));
			}
			rs_add.close();
			
			ResultSet rs_cont = stm.executeQuery("select * from Cust_contact");
			System.out.println("\nCustomer contact Table");
			System.out.println("-------------------------------------------------------------");
			System.out.println("CustomerId\tMobile no.\tEmail Id\n");
			while(rs_cont.next())
			{
				System.out.println(rs_cont.getInt(1)+"\t\t"+rs_cont.getLong(2)+"\t"+rs_cont.getString(3));
			}
			rs_cont.close();
			
			//searching for the customer
			
			System.out.println("\nDo you want to search for the customer:say[Yes/No]");
			System.out.println("1.Yes");
			System.out.println("2.No");
			System.out.println("Enter your choice:");
			String choice = sc.nextLine();
			
			if(choice.equalsIgnoreCase("1")||choice.equalsIgnoreCase("yes")||choice.equalsIgnoreCase("y"))
			{
				
				System.out.println("By what detail you want to search for the customer:");
				System.out.println("I.Customer_id(just say/write id");
				System.out.println("II.First Name(just say 'fisrt name'");
				System.out.println("III.City(just say City.");
				System.out.println("Enter your choice:");
				String n = sc.nextLine();
				
				if(n.equalsIgnoreCase("I") || n.equalsIgnoreCase("id"))
				{
					System.out.println("\nPlz,enter your customer id:");
					cid = sc.nextInt();
					sc.nextLine();
					PreparedStatement ps = con.prepareStatement("select *from customer_details where custid = ?");
					ps.setInt(1, cid);
					int Verifyid = ps.executeUpdate();
					if(Verifyid>0)
					{
						System.out.println("Customer id found successfully....");
						
					}else
						System.out.println("\ncustomer Id not found....");
					
					System.out.println("\nShowing the details of customer with customer id:"+cid);
					PreparedStatement psid  =  con.prepareStatement("select cd.custid,cd.first_name,cd.last_name, "
																	+ "ca.houseno,ca.streetno,ca.City,ca.pincode, "
																	+ "cc.mobno,cc.Email "
																	+ "from customer_details cd "
																	+ "join customer_add ca on cd.custid = ca.custid "
																	+ "join cust_contact cc on cd.custid = cc.custid "
																	+ "where cd.custid=? ");
					psid.setInt(1,cid);
					ResultSet rsid = psid.executeQuery();
					if(rsid.next())
					{
						System.out.println("\nCustomer Id:"+rsid.getInt(1));
						System.out.println("Name:"+rsid.getString(2)+" "+rsid.getString(3));
						System.out.println("Address:"+rsid.getInt(4)+","+rsid.getString(5)+","+rsid.getString(6)+","+rsid.getInt(7));
						System.out.println("Contact details:\n");
						System.out.println("Mobile no:"+rsid.getLong(8));
						System.out.println("Email:"+rsid.getString(9));
					}
					else
						System.out.println("Customer ID with " +cid + "not found in DB");
					rsid.close();
				}
				else if(n.equalsIgnoreCase("II") || n.equalsIgnoreCase("first name")||n.equalsIgnoreCase("fn"))
				{
					System.out.println("\nPlz,enter your first name:");
					fn = sc.nextLine();
					PreparedStatement ps1 = con.prepareStatement("select *from customer_details where first_name = ? AND last_name=? ");
					ps1.setString(1, fn);
					ps1.setString(2, ln);
					int Verifyname = ps1.executeUpdate();
					if(Verifyname>0)
					{
						System.out.println("Customer Name found successfully....");
						
					}else
					{
						System.out.println("\ncustomer Name not found....");
					}
					
					System.out.println("\nShowing the details of customer with customer Name:" + fn + " " + ln);
					PreparedStatement psname  =  con.prepareStatement("select cd.custid,cd.first_name,cd.last_name, "
																	+ "ca.houseno,ca.streetno,ca.City,ca.pincode, "
																	+ "cc.mobno,cc.Email "
																	+ "from customer_details cd "
																	+ "join customer_add ca on cd.custid = ca.custid "
																	+ "join cust_contact cc on cd.custid = cc.custid "
																	+ "where cd.first_name=? ");
					psname.setString(1,fn);
					ResultSet rsname = psname.executeQuery();
					if(rsname.next())
					{
						System.out.println("\nCustomer Id:"+rsname.getInt(1));
						System.out.println("Name:"+rsname.getString(2)+" "+rsname.getString(3));
						System.out.println("Address:"+rsname.getInt(4)+","+rsname.getString(5)+","+rsname.getString(6)+","+rsname.getInt(7));
						System.out.println("Contact details:");
						System.out.println("Mobile no:"+rsname.getLong(8));
						System.out.println("Email:"+rsname.getString(9));
					}
					else
						System.out.println("Customer ID with " + fn + " " + ln + "not found in DB");
					rsname.close();
				}
				else if(n.equalsIgnoreCase("III") || n.equalsIgnoreCase("City")||n.equalsIgnoreCase("ct"))
				{
					System.out.println("\nPlz,enter your City name:");
					ct = sc.nextLine();
					PreparedStatement ps2 = con.prepareStatement("select *from customer_add where City = ? ");
					ps2.setString(1, ct);
					int VerifyCity = ps2.executeUpdate();
					if(VerifyCity>0)
					{
						System.out.println("Customer's entered City found successfully....");
						
					}else
					{
						System.out.println("\nCity name  not found....");
					}
					
					System.out.println("\nShowing the details of customer with customer City:"+ct);
					PreparedStatement pscity  =  con.prepareStatement("select cd.custid,cd.first_name,cd.last_name, "
																	+ "ca.houseno,ca.streetno,ca.City,ca.pincode, "
																	+ "cc.mobno,cc.Email "
																	+ "from customer_add ca "
																	+ "join customer_details cd on ca.custid = cd.custid "
																	+ "join cust_contact cc on ca.custid = cc.custid "
																	+ "where ca.City=? ");
					pscity.setString(1,ct);
					ResultSet rscity = pscity.executeQuery();
					if(rscity.next())
					{
						System.out.println("\nCustomer Id:"+rscity.getInt(1));
						System.out.println("Name:"+rscity.getString(2)+" "+rscity.getString(3));
						System.out.println("Address:"+rscity.getInt(4)+","+rscity.getString(5)+","+rscity.getString(6)+","+rscity.getInt(7));
						System.out.println("Contact details:");
						System.out.println("Mobile no:"+rscity.getLong(8));
						System.out.println("Email:"+rscity.getString(9));
					}
					else
						System.out.println("Customer City with " + ct + "not found in DB");
					rscity.close();
				}
				
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
