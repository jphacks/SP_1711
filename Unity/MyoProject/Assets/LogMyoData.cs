using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

public class LogMyoData : MonoBehaviour {

    public GameObject targetCube;
    StreamWriter writer;
    float passed_time = 0f;
    public string filename;

	// Use this for initialization
	void Start () {
        FileInfo fi = new FileInfo(Application.dataPath + "/"+filename+".csv");
        writer = fi.AppendText();
	}
	
	// Update is called once per frame
	void Update () {
        
        string log = "";
        log += passed_time + ",";
        log += targetCube.transform.position.x + ",";
        log += targetCube.transform.position.y + ",";
        log += targetCube.transform.position.z + ",";
        log += targetCube.transform.rotation.x + ",";
        log += targetCube.transform.rotation.y + ",";
        log += targetCube.transform.rotation.z + ",";
        log += targetCube.transform.rotation.w + ",";

        writer.WriteLine(log);
        writer.Flush();

        passed_time += Time.deltaTime;


    }
}
