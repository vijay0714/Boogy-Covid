package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class fetchWorldLatest
{
    public static void main(String[] args) throws IOException
    {
        String searchUrl = "https://api.apify.com/v2/key-value-stores/" +
                "tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true";

        // creating and writing to file
        try
        {
            File myFile = new File("worldLatestJSON.json");

            URL url = new URL(searchUrl);
            URLConnection urlcon = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));

            if(urlcon.getConnectTimeout()==0)  // to check if network established
            {
                int c;
                FileOutputStream fos = new FileOutputStream("worldLatestJSON.json");
                while ((c = br.read()) != -1) // write to file
                {
                    fos.write((char) c);
                }
                fos.close();
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred or not connected to internet");
            e.printStackTrace();
        }

        //now gson handling
        Gson gson = new GsonBuilder().create();
        try
        {
            URL url = new URL(searchUrl);
            URLConnection urlcon = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));

            worldLatest[] response = gson.fromJson(br, worldLatest[].class);

            // for entire world data, we can add all the stats from each country
            int number=0;
            for(worldLatest country : response)
            {
                System.out.println(++number);
                System.out.println("Country: " + country.country);
                System.out.println("Infected: " +country.infected);
                //System.out.println("Total recovered: " +country.getRecovered());
                //System.out.println("Total active: "+ country.getActive()); // getActive kaam ni kar raha abhi.
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
