/*package test;
import java.sql.*;
import java.util.*;

public class Book_Records {
	private static final String Scrorable = null;

	public static void main(String args[])
	{
		try
		{
			
			int Issuer_Id=0,no_days=0;
			String Issuer="",Book_name=" ";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","sak123");
			
			Scanner sc = new Scanner(System.in);
			
			Statement stm;
			ResultSet rs ;
			
			
			//while starts
			while(true)
			{
				System.out.println("Choose one of the following:");
				System.out.println("\n1.Insert the data");
				System.out.println("\n2.Display Normal");
				System.out.println("\n3.Display Reverse");
				System.out.println("\n4.Display First row");
				System.out.println("\n5.Display Last row ");
				System.out.println("\n6.Display absolute row");
				System.out.println("\n7.Update Record");
				System.out.println("\n8.Exit");
				System.out.println("\nEnter  your choice:");
				int choice = sc.nextInt();
				sc.nextLine();
				switch(choice)
				{
				
				case 1:	System.out.println("\n How many book details do you want to enter:");
						int n = sc.nextInt();
						sc.nextLine();
						System.out.println("\n Enter the following details for borrowing the book:");
						for(int i=0;i<n;i++)
						{
							try
							{
								System.out.println("\nRecord "+(i+1)+" ");
				
								System.out.println("\nIssuer Id:");
								Issuer_Id = sc.nextInt();
								sc.nextLine();
				
								System.out.println("\n Issuer Name:");
								Issuer=sc.nextLine();
							
								System.out.println("\n Book name:");
								Book_name= sc.nextLine();
						
								System.out.println("\n No. of days:");
								no_days = sc.nextInt();
								sc.nextLine();
									
								PreparedStatement ps = con.prepareStatement("insert into Books values(?,?,?,?)");
								ps.setInt(1,Issuer_Id);
								ps.setString(2, Issuer);
								ps.setString(3, Book_name);
								ps.setInt(4, no_days);
				
								int inserted = ps.executeUpdate();
				
								if(inserted>0)
								{
									System.out.println("Record  updated sucessfully");
								}
								else
									System.out.println("Updation fails!!!");
							
							}catch(SQLException sqle)
							{
								System.out.println("\nError:"+sqle);
							}
						}
				
								break;
						
				case 2:	System.out.println("Normal Display");
						System.out.println("Issuer_Id\tIssuer\tBook Name\tDays\n");
						stm = con.createStatement();
						rs = stm.executeQuery("Select * from Books");
						while(rs.next())
						{
							System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4));
						}
						rs.close();
						break;
						
				case 3:	System.out.println("Reverse Display");
						System.out.println("Issuer_Id\tIssuer\tBook Name\tDays\n");
						stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						rs = stm.executeQuery("Select * from Books");
						rs.afterLast();
						while(rs.previous())
						{
							System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4));
						}
						rs.close();
						break;
				
				case 4:	System.out.println("Display First Record:");
						System.out.println("Issuer_Id\tIssuer\tBook Name\tDays\n");
						stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						rs = stm.executeQuery("Select * from Books");
						if(rs.first())
						{
							System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4));
						}
						rs.close();
						break;
						
				case 5:	System.out.println("Display Last Record:");
						System.out.println("Issuer_Id\tIssuer\tBook Name\tDays\n");
						stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						rs = stm.executeQuery("Select * from Books");
						if(rs.last())
						{
							System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4));
						}
						rs.close();
						break;
					
				case 6:	System.out.println("Display particular record:");
						System.out.println("\n Enter the row number:");
						int rownum = sc.nextInt();
						sc.nextLine();
						System.out.println("Issuer_Id\tIssuer\tBook Name\tDays\n");
						stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						rs = stm.executeQuery("Select * from Books");
						if(rs.absolute(rownum))
						{
							System.out.println(rs.getInt(1)+"\t\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4));
						}else
						{
							System.out.println("Such id=" +rownum+ "in our records do not exist");
						}
						rs.close();
						break;
						
				case 7: System.out.println("\nEnter the Issuer Id:");
						int search_Id = sc.nextInt();
						sc.nextLine();
						boolean validId = false;
						try
						{
							stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
							rs = stm.executeQuery("Select * from Books");	
							
							while(rs.next())
							{
								if(rs.getInt("Issuer_Id") == search_Id)
								{
									validId= true;
									System.out.println("Issuer Name:"+rs.getString("Issuer"));
									System.out.println("\nBook Name:"+rs.getString("Book_name"));
									System.out.println("\nNo. of Days required:"+rs.getInt("no_days"));		
									System.out.println("Enter choice to update the record:");
									System.out.println("1.Issuer Name");
									System.out.println("2.Book Name");
									System.out.println("3.Days you want to borrow for:");
									System.out.println("\nEnter the choice");
									String ch = sc.nextLine();
									
									if(ch.equalsIgnoreCase("1") || ch.equalsIgnoreCase("Issuer"))
										{
										System.out.println("\nUpdate the name of the issuer:");
											String NewIssuer = sc.nextLine();
											stm= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
											ResultSet rs1 = stm.executeQuery("Select * from Books");	
											rs1.updateString("Issuer",NewIssuer);
											rs1.updateRow();
											System.out.println("Issuer name updated successfully");
										}
									else if(ch.equalsIgnoreCase("2") || ch.equalsIgnoreCase("Book Name"))
									{
										System.out.println("\nUpdate the name of the book:");
										String NewBook= sc.nextLine();
										rs.updateString("Book_name",NewBook);
										rs.updateRow();
										System.out.println("Book name updated successfully");
									}
									else if(ch.equalsIgnoreCase("3") || ch.equalsIgnoreCase("no. of days"))
									{
										System.out.println("\nUpdate the no of days:");
										int updays= sc.nextInt();
										sc.nextLine();
										rs.updateInt("no_days",updays);
										rs.updateRow();
										System.out.println("No of days updated successfully");
									}else
										System.out.println("updation fails!!!");
										
								}else
									System.out.println("Record Not found");
							}
							}catch(SQLException sqle)
							{
								System.out.println("Error"+sqle);
							}
							break;
				
					
				case 8:	System.out.println("\nExiting...");
						System.exit(0);
						con.close();
			
						
				default:System.out.println("Invalid choice");
					}
			}
			
		}catch(Exception e)
		{
			System.out.println("Error:"+e);
		}
	}

}*/
package test;
import java.sql.*;

import java.util.*;

public class Book_Records {

    public static void main(String args[]) {
        try {
            int Issuer_Id = 0, no_days = 0;
            String Issuer = "", Book_name = "";

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "sak123");

            Scanner sc = new Scanner(System.in);
            
            Statement stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stm.executeQuery("SELECT * FROM Books");

        

            while (true) {
                System.out.println("\nChoose one of the following:");
                System.out.println("1. Insert the data");
                System.out.println("2. Display Normal");
                System.out.println("3. Display Reverse");
                System.out.println("4. Display First row");
                System.out.println("5. Display Last row");
                System.out.println("6. Display absolute row");
                System.out.println("7. Update Record");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("How many book details do you want to enter: ");
                        int n = sc.nextInt();
                        sc.nextLine();
                        for (int i = 0; i < n; i++) {
                            try {
                                System.out.println("\nRecord " + (i + 1));
                                System.out.print("Issuer Id: ");
                                Issuer_Id = sc.nextInt();
                                sc.nextLine();

                                System.out.print("Issuer Name: ");
                                Issuer = sc.nextLine();

                                System.out.print("Book name: ");
                                Book_name = sc.nextLine();

                                System.out.print("No. of days: ");
                                no_days = sc.nextInt();
                                sc.nextLine();

                                PreparedStatement ps = con.prepareStatement("insert into Books values(?,?,?,?)");
                                ps.setInt(1, Issuer_Id);
                                ps.setString(2, Issuer);
                                ps.setString(3, Book_name);
                                ps.setInt(4, no_days);

                                int inserted = ps.executeUpdate();

                                if (inserted > 0)
                                    System.out.println("Record inserted successfully.");
                                else
                                    System.out.println("Insertion failed.");
                            } catch (SQLException sqle) {
                                System.out.println("Error: " + sqle);
                            }
                        }
                        break;

                    case 2:
                        System.out.println("Normal Display:");
                        System.out.println("Issuer_Id\tIssuer\tBook Name\tDays");
                        stm = con.createStatement();
                        rs = stm.executeQuery("SELECT * FROM Books");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t\t" + rs.getInt(4));
                        }
                        rs.close();
                        break;

                    case 3:
                        System.out.println("Reverse Display:");
                        System.out.println("Issuer_Id\tIssuer\tBook Name\tDays");
                      
                        rs.afterLast();
                        while (rs.previous()) {
                            System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4));
                        }
                        rs.close();
                        break;

                    case 4:
                        System.out.println("Display First Record:");
                       
                        if (rs.first()) {
                            System.out.println("Issuer_Id\tIssuer\tBook Name\tDays");
                            System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4));
                        }
                        rs.close();
                        break;

                    case 5:
                        System.out.println("Display Last Record:");
                       
                        if (rs.last()) {
                            System.out.println("Issuer_Id\tIssuer\tBook Name\tDays");
                            System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4));
                        }
                        rs.close();
                        break;

                    case 6:
                        System.out.print("Enter the row number: ");
                        int rownum = sc.nextInt();
                        sc.nextLine();
                       
                        if (rs.absolute(rownum)) {
                            System.out.println("Issuer_Id\tIssuer\tBook Name\tDays");
                            System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4));
                        } else {
                            System.out.println("Row number " + rownum + " does not exist.");
                        }
                        rs.close();
                        break;

                   case 7:
                        System.out.print("Enter the Issuer Id to update: ");
                        int search_Id = sc.nextInt();
                        sc.nextLine();
                        boolean found = false;

                        while (rs.next()) {
                            if (rs.getInt("Issuer_Id") == search_Id) {
                                found = true;
                                System.out.println("Current Issuer: " + rs.getString("Issuer"));
                                System.out.println("Current Book: " + rs.getString("Book_name"));
                                System.out.println("Current Days: " + rs.getInt("no_days"));

                                System.out.println("\n1. Update Issuer Name");
                                System.out.println("2. Update Book Name");
                                System.out.println("3. Update No. of Days");
                                System.out.print("Enter your choice: ");
                                int updateChoice = sc.nextInt();
                                sc.nextLine();

                                switch (updateChoice) {
                                    case 1:
                                        System.out.print("Enter new Issuer name: ");
                                        String newIssuer = sc.nextLine();
                                        rs.updateString("Issuer", newIssuer);
                                        rs.updateRow();
                                        System.out.println("Issuer updated successfully.");
                                        break;
                                    case 2:
                                        System.out.print("Enter new Book name: ");
                                        String newBook = sc.nextLine();
                                        rs.updateString("Book_name", newBook);
                                        rs.updateRow();
                                        System.out.println("Book updated successfully.");
                                        break;
                                    case 3:
                                        System.out.print("Enter new No. of Days: ");
                                        int newDays = sc.nextInt();
                                        sc.nextLine();
                                        rs.updateInt("no_days", newDays);
                                        rs.updateRow();
                                        System.out.println("Days updated successfully.");
                                        break;
                                    default:
                                        System.out.println("Invalid update choice.");
                                }
                                break; // once updated, break out of while loop
                            }
                        }

                        if (!found) {
                            System.out.println("Record with Issuer Id " + search_Id + " not found.");
                        }

                    case 8:
                        System.out.println("Exiting...");
                        con.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}

