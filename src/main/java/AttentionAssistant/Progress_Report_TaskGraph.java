package AttentionAssistant;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JFrame;  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;  
  
public class Progress_Report_TaskGraph extends JFrame {  
  
  private static final long serialVersionUID = 1L;  
  
  public Progress_Report_TaskGraph(String title) {  
    super(title);  
  }
  
  public void Make_TaskGraph(DataBase db, Task task)
  {
	  // Create dataset  
    DefaultCategoryDataset dataset = createDataset(db, task);  
    // Create chart
    JFreeChart lineChart = ChartFactory.createLineChart("Observer Score for: " + task.getTaskName(), "Date & Time", "Observer Score", dataset, PlotOrientation.VERTICAL, true ,false ,false);
//    JFreeChart chart = ChartFactory.createLineChart(  
//        "Observer Score for: " + task.getTaskName(), // Chart title  
//        "Date & Time", // X-Axis Label  
//        "Score", // Y-Axis Label  
//        dataset  
//        );
    CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
    plot.getRangeAxis().setRange(0,100);
    plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    plot.setDomainGridlinesVisible(true);
    plot.setRangeGridlinesVisible(true);
    ChartPanel panel = new ChartPanel(lineChart);  
    setContentPane(panel);  
    //pack the jframe and set it to visible so that is displays to the user
    pack();
    setVisible(true); 
  }  
  
  private DefaultCategoryDataset createDataset(DataBase db, Task task) {  
  
    String series1 = "Observer Score";  
    String series2 = "Threshold";
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
    ArrayList<Observer> observerlist = new ArrayList<Observer>();
    observerlist = db.SelectAllObservers(task.getTaskID());
    for (int i = 0; i < observerlist.size(); i++) {
	    String date1 = new SimpleDateFormat("MM-dd HH:mm").format(observerlist.get(i).getDTGathered());
    	dataset.addValue(observerlist.get(i).getObserverScore(), series1, date1);
    	dataset.addValue(observerlist.get(i).getThreshold(), series2, date1);    	
    }  
    return dataset;  
  }  
    
}  