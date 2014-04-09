package assignment3;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

public class Driver {

	public static void main(String[] args){
		final long startTime = System.currentTimeMillis();
		
		String queryFile = "Resource/Queries/Training-Data-2014";
		String outputFile = "Resource/Queries/Training-Data-2014-tweet-data";
		int numberOfDaysToCrawlBeforeActualMatch = 4;
		int numberOfDaysToCrawlAfterActualMatch = 3;

		try {
			FileHelper.theUltimate(queryFile, outputFile,
					numberOfDaysToCrawlBeforeActualMatch,
					numberOfDaysToCrawlAfterActualMatch);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final long endTime = System.currentTimeMillis();
		double timeTaken = (endTime - startTime)/ 1000;
		System.out.println("Total execution time: " + timeTaken );
	}
}