package ru.gb.money_transfer.logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Logger {
    private static Logger log = null;
    private final AtomicInteger numberMsg = new AtomicInteger(-1);

    private Logger() {
    }

    public static Logger getLog() {
        if (log == null) {
            log = new Logger();
        }
        return log;
    }

    public void log(String msg) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("(dd.MM.yy / HH:mm:ss)");
        //System.out.println(dateFormat.format(date));

        String str;
        if (numberMsg.get() <= 0) {
            str = msg + " " + dateFormat.format(date) + "\n";
        } else {
            str = "log № " + numberMsg + " " + dateFormat.format(date) + " " + msg + "\n";
        }
        numberMsg.getAndIncrement();

        try (FileOutputStream fos = new FileOutputStream("logFile.txt", true)) {
            byte[] bytes = str.getBytes();
            fos.write(bytes, 0, bytes.length);
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }


}
