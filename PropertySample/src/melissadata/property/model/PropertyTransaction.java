package melissadata.property.model;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.sling.commons.json.JSONObject;

public class PropertyTransaction {
    private String endpoint;
    private String columns;
    private String identNumber;
    private String countyFIPS, APN, account, MAK, addressKey, transactionID, freeForm;

    private boolean methodLookupProperty, methodLookupDeeds, methodLookupHomesByOwner;

    private String format;

    public PropertyTransaction() {
        endpoint                 = "https://property.melissadata.net/v4/WEB/LookupProperty?";
        columns                  = "";
        identNumber              = "";
        countyFIPS               = "";
        APN                      = "";
        account                  = "";
        MAK                      = "";
        addressKey               = "";
        transactionID            = "";
        freeForm                 = "";
        methodLookupProperty     = false;
        methodLookupDeeds        = false;
        methodLookupHomesByOwner = false;

        format                   = "";
    }

    public String processTransaction(String request) {
        String response = "";
        URI uri;
        URL url;
        try {
            uri = new URI(request);
            url = new URL(uri.toURL().toString());

            HttpURLConnection urlConn = (HttpURLConnection)(url.openConnection());

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringReader reader;
            StringWriter writer = new StringWriter();
            StringBuilder jsonResponse = new StringBuilder();
            String inputLine = "";


            if (format.equals("JSON"))
            {
                while ((inputLine = in.readLine()) != null) {
                    jsonResponse.append(inputLine);
                }
                @SuppressWarnings("deprecation")
                JSONObject test = new JSONObject(jsonResponse.toString());
                response = test.toString(10);
            } else {
                String xmlLine = "";
                String xmlString = "";

                while((xmlLine = in.readLine()) != null) {
                    xmlString += xmlLine + "\n";
                }

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
                t.setOutputProperty(OutputKeys.INDENT, "yes");

                reader = new StringReader(xmlString);
                try {
                    t.transform(new javax.xml.transform.stream.StreamSource(reader), new javax.xml.transform.stream.StreamResult(writer));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                response = writer.toString();
            }

//            if (format.equals("XML"))
//            {
//                String xmlLine = "";
//                String xmlString = "";
//
//                while((xmlLine = in.readLine()) != null) {
//                    xmlString += xmlLine + "\n";
//                }
//
//                TransformerFactory tf = TransformerFactory.newInstance();
//                Transformer t = tf.newTransformer();
//                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
//                t.setOutputProperty(OutputKeys.INDENT, "yes");
//
//                reader = new StringReader(xmlString);
//                try {
//                    t.transform(new javax.xml.transform.stream.StreamSource(reader), new javax.xml.transform.stream.StreamResult(writer));
//                } catch (TransformerException e) {
//                    e.printStackTrace();
//                }
//                response = writer.toString();
//
//            } else {
//                while ((inputLine = in.readLine()) != null) {
//                    jsonResponse.append(inputLine);
//                }
//                @SuppressWarnings("deprecation")
//                JSONObject test = new JSONObject(jsonResponse.toString());
//                response = test.toString(10);
//
//            }
        } catch (Exception e){
            System.out.println("Error sending request: \n" + e);
        }
        return response;
    }

    public String generateRequestString() {
        String request = "";
        request = getEndpoint();
        request += "&id=" + getIdentNumber();
        request += "&cols=" + getColumns();
        try {
            if(!getCountyFIPS().equals(""))
                request += "&fips=" + URLEncoder.encode(getCountyFIPS(), "UTF-8");

            if(!getAPN().equals(""))
                request += "&apn=" + URLEncoder.encode(getAPN(), "UTF-8");

            if(!getAccount().equals(""))
                request += "&account=" + URLEncoder.encode(getAccount(), "UTF-8");

            if(!getMAK().equals(""))
                request += "&mak=" + URLEncoder.encode(getMAK(), "UTF-8");

            if(!getAddressKey().equals(""))
                request += "&addresskey=" + URLEncoder.encode(getAddressKey(), "UTF-8");

            if(!getTransactionID().equals(""))
                request += "&txid=" + URLEncoder.encode(getTransactionID(), "UTF-8");

            if(!getFreeForm().equals(""))
                request += "&ff=" + URLEncoder.encode(getFreeForm(), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding Exception: " +e);
        }

        if(!getFormat().equals(""))
            request += "&format=" + getFormat();

        return request;
    }



    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getIdentNumber() {
        return identNumber;
    }

    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
    }

    public String getCountyFIPS() {
        return countyFIPS;
    }

    public void setCountyFIPS(String countyFIPS) {
        this.countyFIPS = countyFIPS;
    }

    public String getAPN() {
        return APN;
    }

    public void setAPN(String APN) {
        this.APN = APN;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMAK() {
        return MAK;
    }

    public void setMAK(String MAK) {
        this.MAK = MAK;
    }

    public String getAddressKey() {
        return addressKey;
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public boolean isMethodLookupProperty() {
        return methodLookupProperty;
    }

    public void setMethodLookupProperty(boolean methodLookupProperty) {
        this.methodLookupProperty = methodLookupProperty;
    }

    public boolean isMethodLookupDeeds() {
        return methodLookupDeeds;
    }

    public void setMethodLookupDeeds(boolean methodLookupDeeds) {
        this.methodLookupDeeds = methodLookupDeeds;
    }

    public boolean isMethodLookupHomesByOwner() {
        return methodLookupHomesByOwner;
    }

    public void setMethodLookupHomesByOwner(boolean methodLookupHomesByOwner) {
        this.methodLookupHomesByOwner = methodLookupHomesByOwner;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFreeForm() {
        return freeForm;
    }

    public void setFreeForm(String freeForm) {
        this.freeForm = freeForm;
    }
}
