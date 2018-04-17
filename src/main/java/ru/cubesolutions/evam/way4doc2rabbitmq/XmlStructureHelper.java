package ru.cubesolutions.evam.way4doc2rabbitmq;

/**
 * Created by Garya on 09.02.2018.
 */
public class XmlStructureHelper {

    public static String allContent() {
        return "<DocFile>\n" +
                "%s\n" +
                "</DocFile>";
    }

    public static String fileHeader() {
        return "<FileHeader>\n" +
                "        <FileLabel>PAYMENT</FileLabel>\n" +
                "        <FormatVersion>2.1</FormatVersion>\n" +
                "        <Sender>PAY0001</Sender>\n" +
                "        <CreationDate>%s</CreationDate>\n" +
                "        <CreationTime>%s</CreationTime>\n" +
                "        <FileSeqNumber>%s</FileSeqNumber>\n" +
                "        <Receiver>%s</Receiver>\n" +
                "    </FileHeader>\n";
    }

    public static String docList() {
        return "<DocList>\n" +
                "%s\n" +
                "</DocList>";
    }

    public static String doc() {
        return " <Doc>\n" +
                "            <TransType>\n" +
                "                <TransCode>\n" +
                "                    <MsgCode>PAYPER</MsgCode>\n" +
                "                </TransCode>\n" +
                "            </TransType>\n" +
                "            <DocRefSet>\n" +
                "                <Parm>\n" +
                "                    <ParmCode>DRN</ParmCode>\n" +
                "                    <Value></Value>\n" +
                "                </Parm>\n" +
                "            </DocRefSet>\n" +
                "            <LocalDt>%s</LocalDt>\n" +
                "            <Description>Cashback</Description>\n" +
                "            <SourceDtls></SourceDtls>\n" +
                "            <Originator>\n" +
                "                <ContractNumber>%s-CLIENT_FEE_KZT_%s</ContractNumber>\n" +
                "                <MemberId>PAY0001</MemberId>\n" +
                "            </Originator>\n" +
                "            <Destination>\n" +
                "                <ContractNumber>%s</ContractNumber>\n" +
                "                <MemberId>0001</MemberId>\n" +
                "            </Destination>\n" +
                "            <Transaction>\n" +
                "                <Currency>%s</Currency>\n" +
                "                <Amount>%s</Amount>\n" +
                "            </Transaction>\n" +
                "        </Doc>\n";
    }

    public static String fileTrailer() {
        return "<FileTrailer>\n" +
                "        <CheckSum>\n" +
                "            <RecsCount>%s</RecsCount>\n" +
                "            <HashTotalAmount>%s</HashTotalAmount>\n" +
                "        </CheckSum>\n" +
                "    </FileTrailer>";
    }

}
