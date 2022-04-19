package com.melissadata.property.model;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

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
        try {
            URL url = new URL(request);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String responseBody = in.lines().collect(Collectors.joining());
            response = format.equals("JSON")
                ? getPrettyJSON(responseBody)
                : getPrettyXML(responseBody);

        } catch (Exception e){
            System.out.println("Error sending request: \n" + e);
        }
        return response;
    }

    private String getPrettyJSON(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject responseObject = gson.fromJson(json, JsonObject.class);
        return gson.toJson(responseObject);
    }

    private String getPrettyXML(String xml) {
        String prettyXML = "";
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            String indentSize = "{http://xml.apache.org/xslt}indent-amount";
            t.setOutputProperty(indentSize, "2");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            Writer writer = new StringWriter();
            t.transform(new StreamSource(new StringReader(xml)),
                        new StreamResult(writer));
            prettyXML = writer.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return prettyXML;
    }


    public String generateRequestString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getEndpoint())
            .append("&id=" + getIdentNumber())
            .append("&cols=" + getColumns())

            .append(generateRequestParam("fips", getCountyFIPS()))
            .append(generateRequestParam("apn", getAPN()))
            .append(generateRequestParam("account", getAccount()))
            .append(generateRequestParam("mak", getMAK()))
            .append(generateRequestParam("addresskey", getAddressKey()))
            .append(generateRequestParam("txid", getTransactionID()))
            .append(generateRequestParam("ff", getFreeForm()))

            .append(generateRequestParam("format", getFormat()));
        
        return sb.toString();
    }

    private String generateRequestParam(String arg, String value) {
        String encodedValue = "";
        try {
            encodedValue = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding Exception: " + e);
        }

        if(!encodedValue.equals("")) {
            return "&" + arg + "=" + encodedValue;
        }

        return "";
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
