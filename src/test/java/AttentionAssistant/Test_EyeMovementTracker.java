package AttentionAssistant;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test_EyeMovementTracker {
	
	
	EyeMovementTracker defaultEyeMovementTracker;
	EyeMovementTracker nonDefaultEyeMovementTracker;
	
	
	@BeforeEach
	void setup() {
		
		int testEyeMovementScore = 80;
		int totalGroupsOfFrames = 10;
		int numberOfTimesFaceDetected = 8;
		int weightedScore = 100;
		int thresholdScore = 80;
		defaultEyeMovementTracker= new EyeMovementTracker();
		nonDefaultEyeMovementTracker= new EyeMovementTracker(testEyeMovementScore, totalGroupsOfFrames, numberOfTimesFaceDetected, weightedScore, thresholdScore);
	}
	
	@Test
	@DisplayName("<EyeMovementTracker> DefaultConstructor Testing")
	void EyeMovementTrackerDefaultConstructor(){
		assertEquals(100, defaultEyeMovementTracker.getEyeMovementScore(), "Default constructor EyeMovementTracker.EyemovementScore should be 100. Returned: "
				+ Integer.toString(defaultEyeMovementTracker.getEyeMovementScore())); 
	}

	@Test
	@DisplayName("<EyeMovementTracker> NonDefaultConstructor Testing")
	void EyeMovementTrackernonDefaultConstructor(){
		assertEquals(80, nonDefaultEyeMovementTracker.getEyeMovementScore(), "nonDefault constructor EyeMovementTracker.EyemovementScore should be 80. Returned: "
				+ Integer.toString(nonDefaultEyeMovementTracker.getEyeMovementScore())); 
	}
	
	@Test
	@DisplayName("<EyeMovementTracker> setScore")
	void EyeMovementTrackerGetEMScore(){
		nonDefaultEyeMovementTracker.setEyeMovementScore(75);
		assertEquals(75, nonDefaultEyeMovementTracker.getEyeMovementScore(), "nonDefault constructor EyeMovementTracker.EyemovementScore should be 75. Returned: "
				+ Integer.toString(nonDefaultEyeMovementTracker.getEyeMovementScore())); 
		
	}

	@Test
	@DisplayName("<EyeMovementTracker> settotalGroupsOfFrame")
	void EyeMovementTrackerGetTotalGroupsOfFrames(){
		nonDefaultEyeMovementTracker.setTotalGroupsOfFrames(75);
		assertEquals(75, nonDefaultEyeMovementTracker.getTotalGroupsOfFrames(), "nonDefault constructor EyeMovementTracker.TotalGroupsOfFrames should be 75. Returned: "
				+ Integer.toString(nonDefaultEyeMovementTracker.getTotalGroupsOfFrames())); 
		
	}

	@Test
	@DisplayName("<EyeMovementTracker> setnumberOfTimesFaceDetected")
	void EyeMovementTrackerGetnumberOfTimesFaceDetected(){
		nonDefaultEyeMovementTracker.setnumberOfTimesFaceDetected(75);
		assertEquals(75, nonDefaultEyeMovementTracker.getnumberOfTimesFaceDetected(), "nonDefault constructor EyeMovementTracker.numberOfTimesFaceDetected should be 75. Returned: "
				+ Integer.toString(nonDefaultEyeMovementTracker.getnumberOfTimesFaceDetected())); 		
	}

	@Test
	@DisplayName("<EyeMovementTracker> setweightedScore")
	void EyeMovementTrackerGetweightedScore(){
		nonDefaultEyeMovementTracker.setweightedScore(75);
		assertEquals(75, nonDefaultEyeMovementTracker.getweightedScore(), "nonDefault constructor EyeMovementTracker.weightedScore should be 75. Returned: "
				+ Integer.toString(nonDefaultEyeMovementTracker.getweightedScore())); 
		
	}

	@Test
	@DisplayName("<EyeMovementTracker> setThresholdScore")
	void EyeMovementTrackerGetThresholdScore(){
		nonDefaultEyeMovementTracker.setThresholdScore(75);
		assertEquals(75, nonDefaultEyeMovementTracker.getThresholdScore(), "nonDefault constructor EyeMovementTracker.ThresholdScore should be 75. Returned: "
				+ Integer.toString(nonDefaultEyeMovementTracker.getThresholdScore())); 
		
	}

	@Test
	@DisplayName("<EyeMovementTracker> Setup Testing")
	void EyeMovementTrackerSetupTesting(){
		defaultEyeMovementTracker.startTracking();
	}
	
	@Test
	@DisplayName("<EyeMovementTracker> Profile Testing")
	void EyeMovementTrackerProfileTesting() {
		User_Account newUser = new User_Account();
		User_Account profileUser = new User_Account();
		Observer profileObserver = new Observer();
		Task profileTask= new Task();
		ArrayList<Task> profileTaskList = new ArrayList<Task>();
		int i =0, weightedScore =0, defaultScore= 60;
		EyeMovementTracker profileTracker = new EyeMovementTracker();
		
		//database setup
		DataBase db = new DataBase();
		db.DatabaseSetUp();
		
		//add a new account to the database
		newUser.setUsername("ProfileUser");
		newUser.setPassword("ProfilePassword");
		newUser.setName("Profile");
		db.AddUser_Account(newUser);
		
		//add a new user profile to the database
		profileUser= db.SearchUser_Account("ProfileUser", "ProfilePassword");
		
		//add 5 new tasks to the database
		profileTask.setTaskName("Profile Task 1");
		profileTask.setDescription("Profile Task 1 Description");
		profileTask.setObservable(true);
		profileTask.setDueDate(new Date(System.currentTimeMillis()));
		profileTask.setStatus(TaskStatus.CLOSED);
		profileTask.setPriority(true);
		db.AddTask(profileTask, profileUser.getUserID());
		
		profileTask.setTaskName("Profile Task 2");
		profileTask.setDescription("Profile Task 2 Description");
		db.AddTask(profileTask, profileUser.getUserID());
		
		profileTask.setTaskName("Profile Task 3");
		profileTask.setDescription("Profile Task 3 Description");
		db.AddTask(profileTask, profileUser.getUserID());
		
		profileTask.setTaskName("Profile Task 4");
		profileTask.setDescription("Profile Task 4 Description");
		db.AddTask(profileTask, profileUser.getUserID());
		
		profileTask.setTaskName("Profile Task 5");
		profileTask.setDescription("Profile Task 5 Description");
		db.AddTask(profileTask, profileUser.getUserID());
		
		//get all tasks that are assigned to the profileUser (should only be 5)
		profileTaskList = db.SelectAllTasks(profileUser.getUserID());
		
		//first task observers
		i=0;
		
		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		
		profileObserver.setDefaultEyeScore(70);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(70);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());
		
		//second task observers
		i=1;
		
		profileObserver.setDefaultEyeScore(60);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());
		
		profileObserver.setDefaultEyeScore(65);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(60);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(80);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		//Third task will have no Observers
		
		//Fourth task
		i=3;
		profileObserver.setDefaultEyeScore(60);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(65);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(70);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(80);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		
		//Fifth & Final Task
		i=4;
		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileObserver.setDefaultEyeScore(75);
		profileObserver.setDTGathered(new Date(1220227200L * 1000));
		profileObserver.setObserverScore(60);
		profileObserver.setThreshold(50);

		db.AddObserver(profileObserver, profileTaskList.get(i).getTaskID());

		profileTracker.setEyeMovementScore(defaultScore);
		
		weightedScore = profileTracker.calculateWeightedEyeMovementScore(profileTaskList.get(1), db);
		
		assertEquals(60, defaultScore, "defaultScore should be 60. Returned: "
				+ Integer.toString(defaultScore)); 
		assertEquals(84, weightedScore, "weighted Score should be 84. Returned: "
				+ Integer.toString(weightedScore)); 
				
		db.DeleteUser_Account(profileUser.getUserID());
		
	}

}