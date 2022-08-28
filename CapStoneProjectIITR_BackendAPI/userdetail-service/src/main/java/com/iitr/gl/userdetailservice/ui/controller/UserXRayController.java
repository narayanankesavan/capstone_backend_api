package com.iitr.gl.userdetailservice.ui.controller;

import com.iitr.gl.userdetailservice.service.XRayService;
import com.iitr.gl.userdetailservice.ui.model.GenericRequestModel;
import com.iitr.gl.userdetailservice.ui.model.UploadXRayFileRequestModel;
import com.iitr.gl.userdetailservice.ui.model.UploadXRayFileResponseModel;
import com.iitr.gl.userdetailservice.util.GetJwtSubject;
import com.iitr.gl.userdetailservice.util.XRayActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_detail/xray/")
public class UserXRayController {

    //Logger loggerFactory = LoggerFactory.getLogger(UserXRayController.class);
    @Autowired
    Environment environment;

    /*@Autowired
    LoginServiceClient loginServiceClient;*/

    @Autowired
    XRayService XRayService;

    @Autowired
    GetJwtSubject getJwtSubject;

    @Autowired
    XRayActions xRayActions;
    /*@GetMapping("/testRemoteMicroService")
    public String testRemoteMicroService() {
        loggerFactory.info("Before calling loginservice microservice");
        String result = loginServiceClient.testRemote();
        loginServiceClient.testRemote();
        loggerFactory.info("After calling loginservice microservice");
        return result;
    }*/

    @PostMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadXray(@RequestBody GenericRequestModel genericRequestModel,
                                                          @RequestHeader("Authorization") String token) {
        getJwtSubject.verifyIfAuthorized(token, genericRequestModel.getUserId(), environment, false);
        return xRayActions.downloadXray(genericRequestModel, XRayService);
    }

    @PostMapping("/view")
    public ResponseEntity<String> viewXray(@RequestBody GenericRequestModel genericRequestModel,
                                           @RequestHeader("Authorization") String token) {
        getJwtSubject.verifyIfAuthorized(token, genericRequestModel.getUserId(), environment, false);
        return xRayActions.viewXray(genericRequestModel, XRayService);
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadXRayFileResponseModel> uploadXray(@RequestBody UploadXRayFileRequestModel requestModel,
                                                                  @RequestHeader("Authorization") String token) {
        getJwtSubject.verifyIfAuthorized(token, requestModel.getUserId(), environment, false);
        return xRayActions.uploadXray(requestModel, XRayService);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteXray(@RequestBody GenericRequestModel genericRequestModel,
                                             @RequestHeader("Authorization") String token) {
        getJwtSubject.verifyIfAuthorized(token, genericRequestModel.getUserId(), environment, false);
        return xRayActions.deleteXray(genericRequestModel, XRayService);
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAllXray(@RequestBody GenericRequestModel requestModel,
                                                @RequestHeader("Authorization") String token) {
        getJwtSubject.verifyIfAuthorized(token, requestModel.getUserId(), environment, false);
        return xRayActions.deleteAllXray(requestModel, XRayService);
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateXray(@RequestBody UploadXRayFileRequestModel requestModel,
                                             @RequestHeader("Authorization") String token) {
        getJwtSubject.verifyIfAuthorized(token, requestModel.getUserId(), environment, false);
        return xRayActions.updateXray(requestModel, XRayService);
    }

   /* @PostMapping("/getToken")
    public void getToken(@RequestHeader("Authorization") String token) {
        System.out.println("Token : " + token);
    }*/
}
