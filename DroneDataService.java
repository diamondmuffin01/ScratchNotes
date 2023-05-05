//updated 5/3/23

package com.dronerecon.ws;

        import javax.servlet.*;
        import javax.servlet.http.*;
        import java.io.*;
        import java.util.*;
        import java.security.SecureRandom;

/**
 *
 * @author Cesar Melero 
 */
public class DroneDataService extends HttpServlet{


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        
        String area = request.getParameter("area_id");
        String tilex = request.getParameter("tilex");
        String tiley = equest.getParameter("tiley");
        String totalcols = request.getParameter("totalcols");
        String totalrows = request.getParameter("totalrows");

        // ##############################
        // 2. Default value of beginning direction.

        // Set a string called sDirection to "right".
        // ##############################
        String sDirection = "right";


        // ##############################
        // 3. Calculate next drone move.

        // Determine next tile to move to.
        // Base this on current x and y.
        // Change sDirection if necessary.
        // Drone must serpentine from top left of grid back and forth down.
        // If rows are done, change sDirection to "stop".

        // ##############################

        int row = Integer.parseInt(tiley);
        int col = Integer.parseInt(tilex);
        int totalRows = Integer.parseInt(totalrows);
        int totalCols = Integer.parseInt(totalcols);

        if(row%2 == 0)
        {
            if(col == totalCols - 1 )
            {
                sDirection = "left";
                row++;
            }

            else 
            {
                col++;
            }
        }

        else 
        {
            if(col == 0)
            {
                row++;
            }

            else 
            {
                sDirection = "left";
                col--;
            }
        }

        if(row == totalRows)
        {
            sDirection = "stop";
        }

        // ##############################
        // 4. Format & Return JSON string to caller.

        /* Return via out.println() a JSON string like this:
        {"area_id":"[area id from above]", "nextTileX":"[next tile x]", "nextTileY":"[next tile y]", "direction":"[direction string from above]"}
        */
        // ##############################

        String Jstring = "{\"area_id\":\""+area
        +"\", \"nextTileX\":\""+col
        +"\", \"nextTileY\":\""+row
        +"\", \"direction\":\""+sDirection+"\"}";

        out.println(Jstring);


    }
}

