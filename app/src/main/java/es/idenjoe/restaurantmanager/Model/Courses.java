package es.idenjoe.restaurantmanager.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by idenjoe on 17/04/16.
 */
public class Courses {

    private static LinkedList<MainCourse> sCourses = new LinkedList<MainCourse>();
    private static final Object sLock = new Object(); // Lock to synchronize get courses
    
    private static final String JSON_URL = "http://www.mocky.io/v2/572b9dcc1300007016e2b895";

    public Courses(LinkedList<MainCourse> courses) {
        sCourses = courses;
    }

    public LinkedList<MainCourse> getCourses() {
        return sCourses;
    }

    public void setCourses(LinkedList<MainCourse> courses) {
        sCourses = courses;
    }

    /**
     * This method downloads, if needed, the list of restaurant courses.
     * When downloaded the image is cached for future access
     * @return the list of restaurant courses
     */
    public static LinkedList<MainCourse> getAllCourses() {
        return sCourses;
    }

    public static LinkedList<MainCourse> downloadCourses() {
        try {
            // Download JSON data into an StringBuilder
            URLConnection conn = new URL(JSON_URL).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            // Lock to avoid thread problems
            synchronized (sLock) {
                // Download and parse JSON data
                JSONArray jsonRoot = (new JSONObject(sb.toString())).getJSONArray("courses");
                for (int i = 0; i < jsonRoot.length(); i++) {
                    JSONObject jsonCourse = jsonRoot.getJSONObject(i);

                    String name = jsonCourse.getString("name");
                    String imageURL = jsonCourse.getString("image");
                    float price = (float) jsonCourse.getDouble("price");
                    String description = jsonCourse.getString("description");

                    MainCourse course = new MainCourse(name, description, price, new URL(imageURL));
                    sCourses.add(course);
                }

                Collections.sort(sCourses);

                return sCourses;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
