package com.sorapp.jowncms.jowncms.Service.Log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;

@Component
public class FileLoger
{

    @Value("${FileManagerRoot}")
    String FileManager;

    public void SaveLog(String Request)
    {
        try
        {
            //Get Current date
            LocalDate currentDate = LocalDate.now();

            //Get log file
            String LogFileAddress = FileManager + "/Log/log" + currentDate.getYear() + "-" + currentDate.getMonth() + "-" + currentDate.getDayOfMonth() + ".log";


            File Log = new File(LogFileAddress);

            // Create parent directories if they don't exist
            File parentDir = Log.getParentFile();
            if (!parentDir.exists())
            {
                parentDir.mkdirs();
            }

            // Append to file if it exists, create if it doesn't
            BufferedWriter BW = new BufferedWriter(new FileWriter(Log, true));

            // Write the log entry
            BW.append(Request + "\n\r\n---------------------------------------------------------------------------------------------------\n");
            BW.close();
        }
        catch (Exception e)
        {
            System.err.println("Log File Save Error: " + e.getMessage());
            e.printStackTrace(); // This will help with debugging
        }
    }

}
