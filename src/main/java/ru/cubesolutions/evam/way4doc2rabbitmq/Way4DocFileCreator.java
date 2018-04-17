package ru.cubesolutions.evam.way4doc2rabbitmq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.cubesolutions.evam.way4doc2rabbitmq.XmlStructureHelper.doc;

public class Way4DocFileCreator {

    private static String generateDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String createDoc(Way4DocData data) {
        return String.format(Locale.forLanguageTag("ru"), doc(),
                generateDateTime(),
                data.getBranch(),
                data.getRes() ? "RES" : "NRES",
                data.getIban(),
                "" + data.getCurrency(),
                data.getAmount().toPlainString()
        );
    }


}
