package ru.cubesolutions.evam.way4doc2rabbitmq;

import com.intellica.evam.sdk.outputaction.AbstractOutputAction;
import com.intellica.evam.sdk.outputaction.IOMParameter;
import com.intellica.evam.sdk.outputaction.OutputActionContext;
import org.apache.log4j.Logger;
import ru.cubesolutions.rabbitmq.Producer;
import ru.cubesolutions.rabbitmq.RabbitConfig;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

/**
 * Created by Garya on 09.02.2018.
 */
public class Cashback extends AbstractOutputAction {

    private final static Logger log = Logger.getLogger(Cashback.class);

    private Producer producer;
    private String queue;

    public static void main(String[] args) {
        BigDecimal result = new BigDecimal("1.5780703E7".replace(",", "."));
        System.out.println(result.toPlainString());
    }

    @Override
    public synchronized void init() {
        isInited = false;
        try (InputStream input = new FileInputStream("./conf/way4Doc2rabbitMq.properties")) {
            Properties props = new Properties();
            props.load(input);
            String host = props.getProperty("host");
            int port = Integer.parseInt(props.getProperty("port"));
            String vHost = props.getProperty("v-host");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            this.queue = props.getProperty("queue");

            log.debug("host is " + host);
            log.debug("port is " + port);
            log.debug("v-host is " + vHost);
            log.debug("user is " + user);
            log.debug("password is " + password);
            log.debug("queue is " + queue);

            RabbitConfig rabbitConfig = new RabbitConfig(host, port, vHost, user, password);
            this.producer = new Producer(rabbitConfig);
        } catch (Throwable t) {
            log.error("failed to init Cashback action", t);
            isInited = false;
            return;
        }
        isInited = true;
    }

    @Override
    public int execute(OutputActionContext outputActionContext) throws Exception {
        String iban = (String) outputActionContext.getParameter("iban");
        log.debug("iban is " + iban);
        String amount = (String) outputActionContext.getParameter("amount");
        log.debug("amount is " + amount);
        String cashbackstr = (String) outputActionContext.getParameter("cashback");
        log.debug("cashback is " + cashbackstr);
        String res = (String) outputActionContext.getParameter("res");
        log.debug("res is " + res);
        int branch;
        try {
            branch = Integer.parseInt((String) outputActionContext.getParameter("branch"));
        } catch (Exception e) {
            branch = 0;
        }
        log.debug("branch is " + branch);

        BigDecimal resultCashback = BigDecimal.valueOf(200);

        BigDecimal cashbackPercent = new BigDecimal(cashbackstr);

        resultCashback = resultCashback.subtract(cashbackPercent);

        log.debug("used cashback percent: " + resultCashback);

        if (resultCashback.signum() == -1) {
            log.debug("result amount cashback is less then 0. Cashback won't be added");
            return 0;
        }

        BigDecimal amountCashback = new BigDecimal(amount.replace(",", "."))
                .multiply(resultCashback)
                .divide(BigDecimal.valueOf(1000000), 2, BigDecimal.ROUND_HALF_UP);

        log.debug("result amount cashback: " + amountCashback.toPlainString());

        boolean isRes = "Private Resident".equalsIgnoreCase(res);

        String doc = Way4DocFileCreator.createDoc(new Way4DocData(iban, 398, amountCashback, isRes, branch));
        log.debug("doc: " + doc);

        this.producer.sendMessage(Base64.getEncoder().encodeToString(doc.getBytes()), "", this.queue);
        log.debug("doc sent successfully");
        return 0;
    }

    @Override
    protected ArrayList<IOMParameter> getParameters() {
        ArrayList<IOMParameter> params = new ArrayList<>();
        params.add(new IOMParameter("iban", "iban"));
        params.add(new IOMParameter("amount", "amount"));
        params.add(new IOMParameter("cashback", "cashback"));
        params.add(new IOMParameter("res", "res"));
        params.add(new IOMParameter("branch", "branch"));

        return params;
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

}
