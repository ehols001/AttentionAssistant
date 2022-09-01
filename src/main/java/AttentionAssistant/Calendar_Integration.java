package AttentionAssistant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;	

public class Calendar_Integration {
	
	//Priority_Manager defaultPM;
	
	//*****************************************************************************************************************
	/*
	*  Pull Calendar File
	*/ 
	
	private File grabCalFile(){
		
		JFileChooser calendarDir = new JFileChooser(new File(System.getProperty("user.home"), "Pictures"));
		calendarDir.setFileSelectionMode(JFileChooser.FILES_ONLY);
		calendarDir.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("ICS Files Only", "ics");
		calendarDir.setFileFilter(fileFilter);
		int returnVal = calendarDir.showDialog(null, "Upload Google Calendar File");
		
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File CalendarFile = calendarDir.getSelectedFile();
			
			Path sourcePath = Paths.get(CalendarFile.getAbsolutePath());
			Path destinationPath = Paths.get("calendarInt/" + CalendarFile.getName());
			
			try {
				Files.copy(sourcePath, destinationPath);
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
		}
		
		return calendarDir.getSelectedFile();
	}
	
	//*****************************************************************************************************************
	/*
	 * Integrate Calendar
	 */
	
	void importCal(int userID, DataBase db, DefaultTableModel model, JTable table, JFrame frame, Priority_Manager pm) throws IOException, ParseException{
	
		FileInputStream fstream = new FileInputStream(grabCalFile().toString());
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		
		Task toAdd = new Task();
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			
			if (strLine.contains("BEGIN:VEVENT")) {
				toAdd = new Task();
			}
			else if (strLine.contains("DTSTART")) {
				
				String oldstr = strLine.substring(8, 16);
				String newstr = oldstr.substring(4,6) + "/" + oldstr.substring(6, 8) + "/" + oldstr.substring(0, 4);
				Date start = new SimpleDateFormat("MM/dd/yyyy").parse(newstr);
				
				toAdd.setDueDate(start);
			}
			else if (strLine.contains("DESCRIPTION")) {
				toAdd.setDescription(strLine.substring(12));
			}
			
			else if (strLine.contains("SUMMARY")) {
				toAdd.setTaskName(strLine.substring(8));
			}
			else if (strLine.contains("END:VEVENT")) {
				
				toAdd.setPriority(false);
				toAdd.setStatus(TaskStatus.OPEN);
				toAdd.setObservable(false);
				
				System.out.println(toAdd.toString());
				
				pm.taskWindow(userID, toAdd, false, db, model, table, frame, pm, true);
				
				//db.AddTask(toAdd, userID);
			}
			else {
			}
		}
		fstream.close();
	}	
}
