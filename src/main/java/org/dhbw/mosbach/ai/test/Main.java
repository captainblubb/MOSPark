package org.dhbw.mosbach.ai.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.dhbw.mosbach.ai.services.models.NotifyUsersObject;
import org.dhbw.mosbach.ai.tools.SQLKonverterTool;

import javax.json.Json;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {

    public static void main(String args[]){

        NotifyUsersObject notifyUsersObject = new NotifyUsersObject();
        notifyUsersObject.userID = 5l;
        notifyUsersObject.ids = new ArrayList<>();
        notifyUsersObject.ids.add(5l);
        notifyUsersObject.ids.add(10l);
        notifyUsersObject.ids.add(6l);
        notifyUsersObject.ids.add(7l);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(notifyUsersObject);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
