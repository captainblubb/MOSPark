package org.dhbw.mosbach.ai.servlets;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(displayName = "OccupyParkingSpotServlet", urlPatterns = { "/aloha/*" })
public class OccupyParkingSpotServlet extends HttpServlet {

    @Inject
    private OccupyParkingSpotBean occupyParkingSpotBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String userId = request.getParameter("userId");
        String parkingSpotId = request.getParameter("parkingSpotId");

        String functionResponse = occupyParkingSpotBean.execute(userId, parkingSpotId);

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
            .append("<html>\r\n");
        if (functionResponse.equals("success"))
        {
            writer.append(functionResponse);
        }
        else
        {
            writer.append(functionResponse);
        }
        writer.append("</html>\r\n");
    }
}
