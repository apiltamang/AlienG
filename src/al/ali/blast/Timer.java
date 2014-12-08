package al.ali.blast;

public class Timer {
	long startTime;
	long endTime;
	int numHits;
	long totalTime;
	
	public Timer()
	{
		numHits=0;
		totalTime=0;
	}
	
	public void startTimer()
	{
		startTime=System.nanoTime();
	}
	
	public void stopTimer(String description)
	{
		endTime=System.nanoTime();
		long duration=(endTime-startTime)/1000;
		System.out.println("Time elapsed for "+description+" : "+duration+" 1E-6 s.");
		
		//keep record of aggregrate total time and number of times this was hit
		totalTime = totalTime+duration;
		numHits +=1;
		
	}
	
	public void printStatistics(String body)
	{
		System.out.println("Total time spent  in "+body+" : "+totalTime+" ms.");
		System.out.println("Total num of hits in "+body+" : "+numHits);
	}
	
}
