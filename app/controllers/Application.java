package controllers;

import com.google.common.base.Strings;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonSerializer;
import models.EventLog;
import models.MessageEntry;
import org.apache.commons.io.IOUtils;
import play.*;
import play.libs.F;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

public class Application extends Controller {

    public static Set<MessageEntry> received = Sets.newTreeSet(Ordering.natural().reverse());

    public static void index() {
        try {
            final String message = IOUtils.toString(request.body);
            Logger.info(message);
            if(!Strings.isNullOrEmpty(message)) {
                final MessageEntry messageEntry = new MessageEntry(message);
                received.add(messageEntry);
                EventLog.get().put(messageEntry);
            }

        } catch (IOException e) {
            Logger.info("IOException", e);
        }

        Set<MessageEntry> received = Application.received;
        render(received);
    }



}
