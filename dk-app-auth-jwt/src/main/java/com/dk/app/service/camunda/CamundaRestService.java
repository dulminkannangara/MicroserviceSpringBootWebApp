//package com.dk.app.service.camunda;
//
//import com.netflix.appinfo.InstanceInfo;
//import okhttp3.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.configurationprocessor.json.JSONArray;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class CamundaRestService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(CamundaRestService.class);
//    @Autowired
//    @LoadBalanced
//    private RestTemplate restTemplate;
//
//    public String startRegisterPerson(ResponseEntity<Object> response) {
//        String processInstanceId;
//        String url;
//        try {
//            JSONObject responseObj = new JSONObject(response.getBody().toString());
//            processInstanceId = responseObj.getString("id");
//            url = "http://CUSTOMER-CAMUNDA/engine-rest/history/variable-instance?processInstanceId=" + processInstanceId;
//            LOGGER.info("Send Person Registration Request : success");
//
//
//            if (response.getStatusCodeValue()==200) {
//                boolean condition = true;
//                do {
//                    LOGGER.info("Start Loop CHK");
////                    TimeUnit.SECONDS.sleep(1);
//                    Response response_response = response_client.newCall(response_request).execute();
//
//                    JSONArray dataArray = new JSONArray(response_response.body().string());
//                    for (int i = 0; i < dataArray.length(); i++) {
//                        JSONObject explrObject = null;
//                        explrObject = dataArray.getJSONObject(i);
//                        String field = explrObject.getString("name");
//
//                        if (field.equals(PersonConstant.PAYLOAD_CONTENT_VALIDATION_KEY) && explrObject.getString("value").equals("invalid")) {
//                            sendProcessCompleteRequest(uniqueID);
//                            return "{'error': 'invalid content'}";
//                        }
//
//                        if (field.equals(PersonConstant.MOBILE_NUMBER_AVAILABLE_KEY) && explrObject.getString("value").equals("yes")) {
//                            sendProcessCompleteRequest(uniqueID);
//                            return "{'error': 'mobile available'}";
//                        }
//                        if (field.equals(PersonConstant.OTP)) {
//                            condition = false;
//                        }
//
//                    }
//                    JSONObject incidentReport = getIncidentReport(processInstanceId);
//                    if(incidentReport!=null){
//                        LOGGER.info("Has Incident Report");
//                        if(incidentReport.getString("incident").equals("yes")){
//                            LOGGER.info("Error in Incident Report");
//                            return "{\"error\": \""+incidentReport.getString("message")+"\"}";
//                        }
//                    }
//                    LOGGER.info("End Loop CHK");
//
//                } while (condition);
//            }
//        } catch (JSONException | IOException | InterruptedException e) {
//            LOGGER.error(e.getMessage(), e);
//            return "{'error': 'request failed'}";
//        }
//        return "{\"processId\": \"" + processInstanceId + "\"}";
//    }
//
//    public Boolean startResendOTP(AuthRegisterDTO authRegisterDTO){
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\r\n    " +
//                "\"messageName\": \"StartSendOTP\",\r\n   " +
//                " \"processInstanceId\": \""+authRegisterDTO.getProcessInstanceId()+"\"\r\n\r\n}");
//        Request request = new Request.Builder()
//                .url(getClientURL("PERSON-SERVICE")+"engine-rest/message")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            return response.isSuccessful();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//    private JSONObject getIncidentReport(String processInstanceId) {
//        String json = "";
//        try {
//            String url = "http://CUSTOMER-CAMUNDA/engine-rest/history/incident?processInstanceId="+processInstanceId;
//            ResponseEntity<Object> response = restTemplate.getForEntity(url,Object.class);
//
//            JSONArray incidentArr = new JSONArray(response.getBody().toString());
//            if (incidentArr.length() > 0) {
//                json = "{\"incident\":\"yes\"," +
//                        "\"message\": \"" + incidentArr.getJSONObject(0).getString("incidentMessage") + "\" }";
//                return new JSONObject(json);
//            }
//            json = "{\"incident\": \"no\"}";
//            return new JSONObject(json);
//
//        } catch (JSONException e) {
//            LOGGER.error(e.getMessage(), e);
//            return null;
//        }
//    }
//
//    private Boolean isValidContent(SignupDTO signupDTO) {
//
//        if (signupDTO == null) {
//            return false;
//        }
//        if (signupDTO.getFirstName() == null) {
//            return false;
//        }
//        if (signupDTO.getLastName() == null) {
//            return false;
//        }
//        if (signupDTO.getMobile() == null) {
//            return false;
//        }
//        if (signupDTO.getPassword() == null) {
//            return false;
//        }
//        return true;
//    }
//
//
//
//}
