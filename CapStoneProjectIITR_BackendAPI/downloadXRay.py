#!/usr/bin/python
import requests
import base64
def downloadXRayForNuemonia(patient_id):
    api_url = "http://localhost:8080/patient_detail/downloadXray"
    todo = {"patientId": patient_id}
    headers =  {"Authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiMjdiMDJhYy1lZDNhLTQ4MzgtYTc5Zi1jYzhkMTU0Njc0NDYiLCJleHAiOjE2NjEzMTY5Mjl9.aVZluU06B34eBBRrJ1RJn3fH7ZT0_dRbUReNU_CFjfiAOHZ-jdci8hGSLMVMvUSkM3_j7t0vDdEgJIWWA43dxw"}
    response = requests.post(api_url, json=todo, headers=headers)
    cc = {"imageByteArray": base64.b64encode(response.content)}

    return cc;