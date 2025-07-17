package test;
import java.sql.*;
import java.util.*;

public class Login_form {
//Login form
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","sak123");
			
			Scanner sc = new Scanner(System.in);
			
			String uname="",pwd="",fname="",lname="",addr="",mid="",phone="";	//declaring the global variable all here
			
			while(true)
			{
				System.out.println("---------------WELCOME TO OUR PORTAL-----------------");		//Main-menu
				System.out.println("1.Register(Signup)");
				System.out.println("2.Login(Signin)");
				System.out.println("3.Logout(Exit");
				System.out.println("\nEnter your choice:");
				
				int choice = sc.nextInt();
				sc.nextLine();	//to clear nextLine for input
				
				switch(choice)	//switch1
				{	
				case 1:	
							System.out.println("\nEnter your credentials");
					
							while(true)
							{
								boolean isvalid = true;
								System.out.println("Enter username:");
								uname =sc.nextLine();	//to clear nextLine for input
					
								for(int i=0;i<uname.length();i++)
								{
									char ch = uname.charAt(i);	//using charAt()-->to position each character in string and check letter one by one
									
									if(!(Character.isLetterOrDigit(ch ) || ch == '_' ||  ch == '.' || ch == '-'))		//Character.isletterOrDigit identifies letters and digit
									{
										isvalid = false;
										break;
									}//if ends
								}//for loop ends here
								if(isvalid && uname.length()>4 && uname.length()<=15)		//condition for valid username and length username between 4 and 15
								{
									System.out.println("Valid username");
									break;
								}//if ends
								else
									System.out.println("\n❌ valid username\nUsername should contain atleast \n->Letters(a/A)\n->Digits->0 to 9\n->['_'/'-'/'.']\n->No other special character allowed\n.Plz Try again!!\n");
									System.out.println("-----------------------------------------------------------------------------------\n");							
							}//username while loop ends here
							
							while(true)
							{
								System.out.println("\nEnter password :\n");
								pwd  = sc.nextLine();	//to clear nextLine for input
								
								String pwdregex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])\\S{8,20}$";	//regex->regular expression
																													//^->start of string,$->end of string
								
								if(pwd.matches(pwdregex))	//match method of String helps to match the correct condition
								{
									System.out.println("Strong Password");
									break;
								}//condn ends
								else
									System.out.println("Weak password "
											+ "[must contain alteast\n"
											+ "->8-20 charcters\n"
											+ "->1 lowercase alphabet\n"
											+ "->1 uppercase alphabet\n"
											+ "->no whitespace");
								}//password while loop ends here
				
							System.out.println("\nEnter First name:");
							fname = sc.nextLine();
				
							System.out.println("\nEnter Last name:");
							lname = sc.nextLine();
					
							System.out.println("\nEnter your Address :");
							addr = sc.nextLine();
					
							while(true)
							{
								System.out.println("\nEnter Mail_id :");
								mid = sc.nextLine();
					
								String midregex = "^[a-z0-9.#%_]+@(gmail|yahoo)\\.com$";	//regex for condition,here capital letters not allowed
					
								if(mid.matches(midregex))	//again use of matches method of String
								{
									System.out.println("Valid Mail id");
									break;
								}
								else
									System.out.println("Invalid Mail id.[should include\n->(@gmail|@yahoo) & .com\n->No Capital Letters]");
							}//mail-id loop ends here
					
							while(true)
							{
								System.out.println("\nEnter phno:");
								phone= sc.nextLine().trim();
						
								if(phone.matches("\\d{10}"))	//string method that's why we take phone as String and not int
								{	//\\d{10} used for exact 10 digits
									System.out.println("Valid phno");
									break;
								}
								else
									System.out.println("Phone number should have 10 digits");
								}//phone loop ends here
						
							PreparedStatement ps1 = con.prepareStatement("insert into Register values(?,?,?,?,?,?,?)");
						
							ps1.setString(1, uname);
							ps1.setString(2, pwd);
							ps1.setString(3, fname);
							ps1.setString(4, lname);
							ps1.setString(5, addr);
							ps1.setString(6, mid);
							ps1.setString(7, phone);
						
							int row = ps1.executeUpdate();
						
							if(row>0){
								System.out.println("Registration is sucessful");
								Statement stm = con.createStatement();
								ResultSet rs = stm.executeQuery("Select * from Register");
								while(rs.next())
								{
									System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
								}
								rs.close();
								break;
							}//if condn of row ends here
							else
								System.out.println("Registration fails!!!");
							break;
							
				case 2:
                    System.out.println("---------------------Login Page-----------------------");			//choice 2
                    boolean loginSuccess = false;	//assumed that login is false
               
                    for (int i = 0; i < 3; i++) 
                    {
                        System.out.println("Username:");
                        uname = sc.nextLine();

                        System.out.println("Password:");
                        pwd = sc.nextLine();

                        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM Register WHERE uname=? AND pwd=?");
                        ps2.setString(1, uname);
                        ps2.setString(2, pwd);

                        ResultSet rs1 = ps2.executeQuery();

                        if (rs1.next()) {
                            System.out.println("Login successful! Welcome " + rs1.getString("fname") + " " + rs1.getString("lname"));
                            loginSuccess = true;	//assumed false login is wrong so login is true
                            boolean inLoginMenu = true;	//submenu in login
                            while(inLoginMenu) {
                            System.out.println("1.View Profile");
                            System.out.println("2.Update Profile");
                            System.out.println("3.Back to HomePage");
                            System.out.println("\nEnter your choice:");
                            int n = sc.nextInt();
                            sc.nextLine();
                            switch(n)		//switch2
                            {
                            case 1:	PreparedStatement viewProf  =  con.prepareStatement("select *from Register where uname=?");		//View profile
                            		viewProf.setString(1, uname);
                            		ResultSet rsView = viewProf.executeQuery();
                            		System.out.println("Username\tPassword\tFirstName\t\tLastName\t\tAddress\t\tMailid\t\tPhone no.");
                            		while(rsView.next())
                            		{
                            			System.out.println(rsView.getString(1)+"\t"+rsView.getString(2)+"\t"+rsView.getString(3)+"\t"+rsView.getString(4)+"\t"+rsView.getString(5)+"\t"+rsView.getString(6)+"\t"+rsView.getString(7));
                            		}
                            		rsView.close();
                            		break;
                            case 2: System.out.println("What do you want to update:");		//update profile
                            		System.out.println("1.Address");
                            		System.out.println("2.Email-Id");
                            		System.out.println("3.Phone no.");
                            		System.out.println("\nEnter your choice");
                            		int choice2 = sc.nextInt();
                            		sc.nextLine();
                            		switch(choice2)		//switch 3
                            		{
                            		case 1:	System.out.println("\nEnter New Address:");			//update address
                            				String Newaddr = sc.nextLine();
                            				PreparedStatement updadd = con.prepareStatement("update Register set addr=? where uname =?");
                            				updadd.setString(1,Newaddr);
                            				updadd.setString(2, uname);
                            				int updateaddr = updadd.executeUpdate();
                            				if(updateaddr>0)	//if updated returns more than 1 else less than 1
                            				{
                            					System.out.println("Address updated sucessfully");
                            				}
                            				else
                            				{
                            					System.out.println("fail to update!!");
                            				}
                            				break;
                            		case 2:	System.out.println("\nEnter new Email Id:");	//Email id update
                            				String Newmid = sc.nextLine();
                            			    String midregex = "^[a-z0-9.#%_]+@(gmail|yahoo)\\.com$";
                            			    if(Newmid.matches(midregex))
                            			    {
                            			    	PreparedStatement updmid = con.prepareStatement("update Register set mid=? where uname =?");
                            			    	updmid.setString(1,Newmid);
                            			    	updmid.setString(2, uname);
                            			    	int updatemid = updmid.executeUpdate();
                            			    	if(updatemid>0)
                            			    	{
                            			    		System.out.println("EmailId updated sucessfully");
                            			    	}
                            			    	else
                            			    	{
                            			    		System.out.println("fail to update!!");
                            			    	}
                            			    }
                            				break;
                            		case 3:	System.out.println("Enter new phone number:");		//update phone
                            				String newPhone = sc.nextLine();
                            				if (newPhone.matches("\\d{10}")) {
                            				PreparedStatement updPhone = con.prepareStatement("UPDATE Register SET phone=? WHERE uname=?");
                            				updPhone.setString(1, newPhone);
                            				updPhone.setString(2, uname);
                            				int updated = updPhone.executeUpdate();
                            				if (updated > 0)
                            					System.out.println("Phone number updated successfully.");
                            				else
                            					System.out.println("Failed to update phone number.");
                            				} else {
                            					System.out.println("Invalid phone number. Must be 10 digits.");
                            				}
                            				break;

                            		
                            		default:System.out.println("Invalid choice");
                            		}	//switch 3 completes
                            	break;	//end of submenu->login
                            case 3:	//case 3 of sub menu
                            	    inLoginMenu =false;	//false the condition of while loop
                            		System.out.println("Back to HomePage");
                            		break;
                            		default:System.out.println("Invalid choice");
                            	}//switch 2 ends here
                            }//end of while(LoginMenu)
                            
                           
                            break;	// break if the above conditions are true otherwise follows else part
                        } 
                        else {
                            System.out.println("Incorrect username or password.\nTry again!!");
                        }
                    }//ends of for loop at the start of case 2(Login)
                    

                    if (!loginSuccess) {	//if Login do not succeed->ask for password change
                        System.out.println("You entered incorrect credentials 3 times.");
                        System.out.println("\n**Forgot password? Want to change password?");
                        System.out.println("1. Yes/Y");
                        System.out.println("2. No/N");
                        System.out.println("\nEnter your choice:");
                        String choice1 = sc.nextLine();

                        if (choice1.equalsIgnoreCase("yes") || choice1.equalsIgnoreCase("1") || choice1.equalsIgnoreCase("y")) {	//equalsIgnoreCase method of string class is to identify the correct string

                            System.out.println("\nEnter your registered username:");
                            uname = sc.nextLine().trim();		//use of trim to avoid whitespace

                            System.out.println("\nEnter your registered Email id:");
                            mid = sc.nextLine();
                            //checking whether user credentials are valid or invalid
                            PreparedStatement checkUser = con.prepareStatement("SELECT * FROM Register WHERE uname=? AND mid=?");
                            checkUser.setString(1, uname);
                            checkUser.setString(2, mid);

                            ResultSet rsCheck = checkUser.executeQuery();

                            if (rsCheck.next()) {
                                System.out.println("\n✔️ User found: " + rsCheck.getString("uname"));

                                String newPwd = "", conPwd = "";
                                while (true) {
                                    System.out.println("Enter your new password:");
                                    newPwd = sc.nextLine();

                                    System.out.println("Confirm your new password:");
                                    conPwd = sc.nextLine();
                                    //to confirm the password

                                    if (newPwd.equals(conPwd)) {
                                        String pwdRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])\\S{8,20}$";

                                        if (newPwd.matches(pwdRegex)) {
                                            PreparedStatement updatePwd = con.prepareStatement("UPDATE Register SET pwd=? WHERE uname=?");
                                            updatePwd.setString(1, newPwd);
                                            updatePwd.setString(2, uname);

                                            int updated = updatePwd.executeUpdate();

                                            if (updated > 0) {
                                                System.out.println("Password updated successfully ✔️.");
                                                System.out.println("Your new Password is:"+newPwd);
                                                System.out.println("Back to login...");
                                               
                                            } else {
                                                System.out.println("Password update failed.");
                                            }
                                            break;
                                        } else {
                                            System.out.println("Weak password. Follow password rules.");
                                        }
                                    } else {
                                        System.out.println("Passwords do not match. Try again.");
                                    }
                                }//ends of while loop
                            }// if (rsCheck.next()) 
                             else {
                                System.out.println("User not found. Cannot reset password.[*You may have not registered earlier.Plz register youself\n");
                            }
                        }
                    }//!loginSucess close
                    break;

                case 3:
                	System.out.println("Want to Logout.Are you sure?(yes/no)");		//case 3 of main menu
                	String logout = sc.nextLine();
                	if(logout.equalsIgnoreCase("yes")|| logout.equalsIgnoreCase("Y"))
                	{
                		System.out.println("Exiting... Thank you!");
                		sc.close();
                        con.close();
                        System.exit(0);	//exit from the system
                     }
                	break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }//end of switch1
        }//end of while loop(1st)

    } catch (Exception e) {
        System.out.println("Error: " + e);
    }
}
}