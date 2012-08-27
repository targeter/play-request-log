package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.EventLog;
import models.MessageEntry;
import org.joda.time.DateTime;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.WebSocketController;
import util.DateTimeTypeConverter;

import java.util.Collections;
import java.util.List;

public class WebSocket extends Controller {

    public static void index() {
        final List<MessageEntry> received = Collections.emptyList();
        render(received);
    }

    public static class EventLogSocket extends WebSocketController {

        public static void list() {
            final F.EventStream<MessageEntry> stream = EventLog.get().getStream();

            while (inbound.isOpen()) {
                final MessageEntry await = await(stream.nextEvent());

                final Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                        .disableHtmlEscaping()
                        .create();
                final String json = gson.toJson(await);
                outbound.send(json);
            }
        }
    }
}
