package test;
import java.sql.*;
import java.util.*;

import com.sun.net.httpserver.Authenticator.Result;

public class Stud_details {
	
	//function 1
	public static int validateRollno()
	{
		Boolean isvalid1= false;
		int rollno1=0;
		Scanner sc = new Scanner(System.in);
		loop:
		while(true)
		{
			//try-catch
			try	
			{
				System.out.println("\nEnter Rollno.:");
				rollno1 = sc.nextInt();
				sc.nextLine();
				if(rollno1>0 && rollno1<=999)
				{
					isvalid1 =true;
					break;
				}
				else
					System.out.println("Rollno must be between 0 and  999 only");
					
			}catch(InputMismatchException ime)
			{
				
					System.out.println("Roll no. should be digit only having range from 0 to 999.");
					sc.nextLine();
					continue loop;
			
			}//catch ends here
			
		}//while ends here
		return rollno1;
	}//function ends

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","sak123");
			
			Scanner sc = new Scanner(System.in);
			while(true)
			{	//global declaration of variables
				int rollno=0;
				Double sub1=(double)0,sub2=(double)0,sub3=(double) 0,sub4=(double)0,sub5=(double)0,sub6=(double)0,newMarks=(double)0;	
				Double per=(double) 0,totMarks=(double)0;
				String Name=" ",branch=" ",grade =" ",Result="",Newname1="",Newbranch="",chmarks="";
				
				boolean isvalid =true;	//assumed that conditions are true before entering the loop
				//Main-Menu
				System.out.println("\n-------------Student Record--------------");
				System.out.println("\n1.Add Student details");
				System.out.println("\n2.View Details of all Student");
				System.out.println("\n3.View Student by rollNo.");
				System.out.println("\n4.Edit Student profile");
				System.out.println("\n5.Exit");
				System.out.println("\nEnter your choice:");
				int choice = sc.nextInt();
				sc.nextLine();	//clear the buffer line
				
				switch(choice)	//starts here
				{
				case 1:	System.out.println("How many Student's data do you need to insert?");
						int n =sc.nextInt();
						sc.nextLine();
						boolean StudData = true;
						while(true)
						{
							for(int i=0;i<n;i++)
							{
								System.out.println("\nStudent" +(i+1)+ ":");
								
								rollno = validateRollno();
								
								System.out.println("\nEnter Student Name:");
								Name = sc.nextLine();
								loop1:
								while(true)
								{
									System.out.println("\nEnter Branch");
									branch = sc.nextLine();
									if(branch.equalsIgnoreCase("CSE") || branch.equalsIgnoreCase("Computer Science & Engineering")||branch.equalsIgnoreCase("Computer Science"))
									{
										System.out.println("Accepted");
										break;
									}
									else if(branch.equalsIgnoreCase("MECH") || branch.equalsIgnoreCase("Mechanical Engineering")||branch.equalsIgnoreCase("ME"))
									{
										System.out.println("Accepted");
										break;
									}
									else if(branch.equalsIgnoreCase("AI") || branch.equalsIgnoreCase("Artificial Intelligence"))
									{
										System.out.println("Accepted");
										break;
									}

									else if((branch.equalsIgnoreCase("IT") || branch.equalsIgnoreCase("Information Technology")))
									{
										System.out.println("Accepted");
										break;
									}
									else
										System.out.println("This "+branch+" branch is not available");
										continue loop1;
								}
								
								System.out.println("\nEnter Student Marks:");
								
								//subject-1
								System.out.println("\nScience [100 Marks]:");
								do {
								    sub1 = sc.nextDouble();
								    if (sub1 > 100 || sub1 < 0) {
								        System.out.println("Marks should be between 0 and 100. Please re-enter:");
								    }
								} while (sub1 > 100 || sub1 < 0);
								
								//subject 2
								System.out.println("\nMathematics[100 Marks]:");
								do
								{
									sub2 = sc.nextDouble();
									if(sub2>100 || sub2<0)
									{
										System.out.println("Marks should be between 0 and 100. Please re-enter:");
									}
								}while(sub2>100 || sub2<0);
								
								//Subject 3
								System.out.println("\nSociology:[100 Marks]");
								do
								{
									sub3 = sc.nextDouble();
									if(sub3>100 || sub3<0)
									{
										System.out.println("Marks should be between 0 and 100. Please re-enter:");
									}
								}while(sub3>100 || sub3<0);
								
								//subject 4
								System.out.println("\nEnglish:[100 Marks]");
								do
								{
									sub4 = sc.nextDouble();
									if(sub4>100 || sub4<0)
									{
										System.out.println("Marks should be between 0 and 100. Please re-enter:");
									}
								}while(sub4>100 || sub4<0);
								
								//subject 5
								System.out.println("\nSports:[100 Marks]");
								do
								{
									sub5 = sc.nextDouble();
									if(sub5>100 || sub5<0)
									{
										System.out.println("Marks should be between 0 and 100. Please re-enter:");
									}
								}while(sub5>100 || sub5<0);
								
								//subject 6
								System.out.println("\nComputer Science:[100 Marks]");
								do
								{
									sub6 = sc.nextDouble();
									if(sub6>100 || sub6<0)
									{
										System.out.println("Marks should be between 0 and 100. Please re-enter:");
									}
								}while(sub6>100 || sub6<0);
								
								totMarks = sub1+sub2+sub3+sub4+sub5+sub6;
								
								per = (totMarks/600)*100;
								
								//grade A
								if(per>90)
								{
									grade ="A";
								}//Grade B
								else if(per<=90 && per>80)
								{
									grade ="B";
								}//Grade C
								else if(per<=80 && per>65)
								{
									grade ="C";
								}
								else if(per<=65 && per>40)//Grade D
								{
									grade ="D";
								}//Fail
								else
									grade="Fail";
								
								System.out.println("Grade:"+grade);
								System.out.println("\nResult:"+(grade.equals("Fails")?"Fail":"Pass with "+(String.format("%.2f%%",per))));

								
							}//for loop ends here
							
							PreparedStatement Stud = con.prepareStatement("insert into Student values(?,?,?,?,?,?)");
							Stud.setInt(1, rollno);
							Stud.setString(2, Name);
							Stud.setString(3, branch);
							Stud.setDouble(4, totMarks);
							Stud.setDouble(5, per);
							Stud.setString(6, grade);
							
							PreparedStatement Stud_marks = con.prepareStatement("insert into StuMarks values(?,?,?,?,?,?,?)");
							Stud_marks.setInt(1,rollno);
							Stud_marks.setDouble(2, sub1);
							Stud_marks.setDouble(3, sub2);
							Stud_marks.setDouble(4, sub3);
							Stud_marks.setDouble(5, sub4);
							Stud_marks.setDouble(6, sub5);
							Stud_marks.setDouble(7, sub6);
							
							int Stu_Details = Stud.executeUpdate();
							int Stu_Details1 = Stud_marks.executeUpdate();
							if(Stu_Details>0 && Stu_Details1>0)
							{
								System.out.println("Student details and Student marks added sucessfully");
								break;
							}
							else
								System.out.println("Updation fail!!");
							
							
							}//while loop ends here
							break;	//case 1 break;
				case 2: //PreparedStatement psView = con.prepareStatement("");
						Statement stmView = con.createStatement();
						ResultSet rsView = stmView.executeQuery("select * from Student");
						System.out.println("Rollno.\tName\tBranch\tTotalMarks\tPercentage\tGrade\n");
						while(rsView.next())
						{
							System.out.println(rsView.getInt(1)+"\t"+rsView.getString(2)+"\t"+rsView.getString(3)+"\t"+rsView.getDouble(4)+"\t\t"+rsView.getDouble(5)+"\t\t"+rsView.getString(6));
						}//end of while loop in case 21
						ResultSet rsView1 = stmView.executeQuery("select * from StuMarks");
						System.out.println("\n");
						System.out.println("Rollno.\tScience\tMathematics\tSociology\tEnglish\tSports\tComputer Science");
						while(rsView1.next())
						{
							System.out.println(rsView1.getInt(1)+"\t"+rsView1.getDouble(2)+"\t"+rsView1.getDouble(3)+"\t\t"+rsView1.getDouble(4)+"\t\t"+rsView1.getDouble(5)+"\t"+rsView1.getDouble(6)+"\t\t"+rsView1.getDouble(7));
						}//end of while loop in case 22
						break;	//break of case 2
						
				case 3:	System.out.println("Enter the Rollno.:");
						rollno = sc.nextInt();
						sc.nextLine();
						PreparedStatement psRoll = con.prepareStatement("select * from Student where rollno=?");    
						psRoll.setInt(1, rollno);
						ResultSet rsRoll = psRoll.executeQuery();
						while(rsRoll.next())
						{
							System.out.println("Name:"+rsRoll.getString(2));
							System.out.println("Branch:"+rsRoll.getString(3));
							System.out.println("Total Marks:"+rsRoll.getDouble(4));
							System.out.println("Percentage:"+rsRoll.getDouble(5)+"%");
							System.out.println("Grade:"+rsRoll.getString(6));
						}//while loop close here
						//for student marks
						System.out.println("\n");
						System.out.println("Marks of Student with roll no.:"+rollno);
						PreparedStatement psRoll1 = con.prepareStatement("select * from StuMarks where rollno=?");   
						psRoll1.setInt(1,rollno);
						ResultSet marks = psRoll1.executeQuery();
						while(marks.next())
						{
							System.out.println("Science:"+marks.getDouble(2));
							System.out.println("Mathematics:"+marks.getDouble(3));
							System.out.println("Sociology:"+marks.getDouble(4));
							System.out.println("English:"+marks.getDouble(5));
							System.out.println("Sports:"+marks.getDouble(6));
							System.out.println("Computer Science:"+marks.getDouble(7));
						}//while loop ends here
						
						break;//case 3 ends 

							
				case 4: while(true)
						{
							System.out.println("you can edit the followings:");

							System.out.println("\n1.Name");
							System.out.println("\n2.Branch:");
							System.out.println("\n3.subjects");
							System.out.println("\nEnter your choice:");
							int choice2 =sc.nextInt();
							sc.nextLine();
							switch(choice2)
							{
							
								case 1:		rollno=validateRollno();
											System.out.println("\nEnter New Name:");
											Newname1 = sc.nextLine();
											PreparedStatement psName = con.prepareStatement("update Student set name = ? where rollno=?");
											psName.setString(1,Newname1);
											psName.setInt(2, rollno);
											int updname = psName.executeUpdate();
											if(updname>0)
											{
												System.out.println("Name updated as" +Newname1+"");
												break;
											}else {
												System.out.println("Updation fails!!!");
											}
											break;
											
								//update Branch			
								case 2: 
										loop1:
											while(true)
											{
												rollno=validateRollno();
												System.out.println("\nEnter Branch");
												System.out.println("Branches available:");
												System.out.println("\n1.CSE(Computer Science & Engineeing");
												System.out.println("\n2.MECH(Mechanical Engineering");
												System.out.println("\n3.AI(Artificial Intelligence");
												System.out.println("\n4.IT(Information Technology)");
												Newbranch = sc.nextLine();
												if(Newbranch.equalsIgnoreCase("CSE") || Newbranch.equalsIgnoreCase("Computer Science & Engineering")||Newbranch.equalsIgnoreCase("Computer Science")||Newbranch.equalsIgnoreCase("1"))
												{
													System.out.println("Accepted");
													break;
												}
												else if(Newbranch.equalsIgnoreCase("MECH") || Newbranch.equalsIgnoreCase("Mechanical Engineering")|Newbranch.equalsIgnoreCase("ME")||Newbranch.equalsIgnoreCase("2"))
												{
													System.out.println("Accepted");
													break;
												}
												else if(Newbranch.equalsIgnoreCase("AI") || Newbranch.equalsIgnoreCase("Artificial Intelligence")||Newbranch.equalsIgnoreCase("3"))
												{
													System.out.println("Accepted");
													break;
												}

												else if((Newbranch.equalsIgnoreCase("IT") || Newbranch.equalsIgnoreCase("Information Technology"))||Newbranch.equalsIgnoreCase("4"))
												{
													System.out.println("Accepted");
													break;
												}
												else
													System.out.println("This "+Newbranch+" branch is not available");
												continue loop1;
											}
										
											PreparedStatement psBranch = con.prepareStatement("update student set branch =? where rollno=?");
							
											psBranch.setString(1,Newbranch);
											psBranch.setInt(2, rollno);
											int updBranch = psBranch.executeUpdate();
											if(updBranch>0)
											{
												System.out.println("Branch updated sucessfully to "+Newbranch+""); 
											}else
												System.out.println("Updation fails!!!");

											break;
											
								case 3:	while(true)
										{
											System.out.println("\nWhich subject marks do you want to change:");
											System.out.println("1.Science");
											//System.out.println("2.Mathematics");
									//		System.out.println("3.Sociology");
										//	System.out.println("4.English");
											//System.out.println("5.Sports");
			//								System.out.println("6.Computer Science");
											System.out.println("7.Exit");
											System.out.println("\nEnter your choice:");
											chmarks = sc.nextLine();
											switch(chmarks)
											{
											
												case "1" : 	
															while(true)
															{													
																    rollno=validateRollno();
																	boolean isvalidmarks = false;
																	marks:
																	while(true)
																	{
																		System.out.println("Enter new marks[Out of 100]:");
																			
																		newMarks = sc.nextDouble();
																		sc.nextLine();
																		if(newMarks <0 || newMarks>100)
																		{
																			System.out.println("Invalid marks!!Try again");
																		}else
																			break;
																	
																		PreparedStatement psmarks = con.prepareStatement("update StuMarks set sub1=? where rollno=?");
																		psmarks.setDouble(1, newMarks);
																		psmarks.setInt(2, rollno);
																		int updmarks = psmarks.executeUpdate();
																		if(updmarks>0)
																		{
																			System.out.println("Marks updated successfully to "+newMarks);
																		}else
																			System.out.println("updation fail!!!");
																}
							
																
																
																// Step 2: Fetch all subject marks for this rollno
																PreparedStatement psfetch = con.prepareStatement("SELECT * FROM StuMarks WHERE rollno = ?");
																psfetch.setInt(1, rollno);
																ResultSet rs = psfetch.executeQuery();

																if (rs.next()) {
																	double s1 = rs.getDouble(2);  // Assuming 2nd column = Science
																	double s2 = rs.getDouble(3);  // Maths
																	double s3 = rs.getDouble(4);  // Sociology
																	double s4 = rs.getDouble(5);  // English
																	double s5 = rs.getDouble(6);  // Sports
																	double s6 = rs.getDouble(7);  // Computer Science

																	// Step 3: Recalculate
																	double total = s1 + s2 + s3 + s4 + s5 + s6;
																	double percent = (total / 600.0) * 100;
												   
																	if (percent > 90) {
																		grade = "A";
																	} else if (percent > 80) {
																		grade = "B";
																	} else if (percent > 65) {
																		grade = "C";
																	} else if (percent > 40) {
																		grade = "D";
																	} else {
																		grade = "Fail";
																	}

																	// Step 4: Update Student table
																	PreparedStatement psupdate = con.prepareStatement(
																			"UPDATE Student SET totMarks = ?, per = ?, Grade = ? WHERE rollno = ?"
																			);
																	psupdate.setDouble(1, total);
																	psupdate.setDouble(2, percent);
																	psupdate.setString(3, grade);
																	psupdate.setInt(4, rollno);
																	
																	int updateStudent = psupdate.executeUpdate();
																	if (updateStudent > 0) {
																		System.out.println("✅ Student record updated: Total = " + total + ", % = " + String.format("%.2f", percent) + ", Grade = " + grade);
																	} else 
																		System.out.println("⚠️ Student table update failed!");
																}
																break;
															}
																
															case "7":
															    System.out.println("Exiting...");
															    System.exit(0);
															    break;
															default:System.out.println("Back to Menu");

															}//switch ends here
											}//while loop ends here
							
											
								}//switch ends here
						}//while ends 
						case 5:	System.out.println("Want to exit");
						System.out.println("1.Yes");
						System.out.println("2.No");
						System.out.println("\nEnter your choice:");
						String choice1 = sc.nextLine();
						if(choice1.equalsIgnoreCase("yes")||choice1.equalsIgnoreCase("1")||choice1.equalsIgnoreCase("Y"))
						{
							System.out.println("Exiting....");
							con.close();
							System.exit(0);
						}else
							System.out.println("Continue with the Main-Menu");
							break;
				default:System.out.println("Invalid choice");
							
					}//switch ends here

			}//while ends
			
		}catch(Exception e)
		{
			System.out.println("Error:"+e);
		}

	}

}
