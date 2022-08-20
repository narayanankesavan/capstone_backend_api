package com.iitr.gl.dashboardservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @PostMapping(value = "/runPy", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> runPythonScript(@RequestBody String body, @RequestHeader("Authorization") String token) {
        String pythonCode = "#!/usr/bin/python\n" +
                "import requests\n" +
                "import base64\n" +
                "def downloadXRayForNuemonia(patient_id):\n" +
                "    api_url = \"http://localhost:8080/patient_detail/downloadXray\"\n" +
                "    todo = {\"patientId\": patient_id}\n" +
                "    headers =  {\"Authorization\": \"" + token + "\"}\n" +
                "    response = requests.post(api_url, json=todo, headers=headers)\n" +
                "    cc = {\"imageByteArray\": base64.b64encode(response.content)}\n" +
                "\n" +
                "    return cc;";
        try {
            File tempPyhtonFile = new File("ttSample.py");
            String filePath = tempPyhtonFile.getAbsolutePath();
            System.out.println("FilePath : " + filePath);

            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(pythonCode + body);
            fileWriter.close();
            return executePython(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Object> executePython(String filePath) {
        List<Object> list = new ArrayList<>();
        try {
            String[] command = {"python", filePath};
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process exec = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String text = null;
            while ((text = br.readLine()) != null) {
                System.out.println(text);
                list.add(text);
            }

            System.out.println("Process exited with " + exec.waitFor());
        } catch (IOException | InterruptedException exp) {
            list.add(exp.getStackTrace());
        }

        return list;
    }

}
