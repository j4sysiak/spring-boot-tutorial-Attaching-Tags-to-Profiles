package com.caveofprogramming.tests;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.caveofprogramming.model.StatusUpdate;
import com.caveofprogramming.model.StatusUpdateDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
//@Transactional
public class StatusTest {
	
	@Autowired
	private StatusUpdateDao statusUpdateDao;

	@Test
	public void testSave() {

		StatusUpdate status = new StatusUpdate("This is a test status update.");
		
		statusUpdateDao.save(status);
		
		assertNotNull("Non-null ID", status.getId());
 		assertNotNull("Non-null Date", status.getAdded());
 		
 		Optional<StatusUpdate> retrievedOpt = statusUpdateDao.findById(status.getId()); //.findById(status.getId());
 		StatusUpdate retrieved = retrievedOpt.get();
 		
 	    //assertEquals("Matching StatusUpdate", status, retrievedOpt);
		
	}
	
	
	@Test
	public void testFindLatest() {
		
		Calendar calendar = Calendar.getInstance();
		
		StatusUpdate lastStatusUpdate = null;
		
		for(int i=0; i<10; i++) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			
			StatusUpdate status = new StatusUpdate("Status update " + i, calendar.getTime());
			
			statusUpdateDao.save(status);
			
			lastStatusUpdate = status;
		}
		
		StatusUpdate retrieved = statusUpdateDao.findFirstByOrderByAddedDesc();
		
		//assertEquals("Latest status update", lastStatusUpdate, retrieved);
	}
}

















