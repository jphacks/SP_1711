using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;
using UnityEngine.UI;

public class ReplayLogData : MonoBehaviour {

    public GameObject logtarget;
    StringReader sr;
    public float[,] logdata_array;
    public float time = 1f;
    public Slider slider;
    int k = 0;
    int col = 8;

    bool playflag = false;

    public string read_filename;

	// Use this for initialization
	void Start () {
        TextAsset csvFile = Resources.Load(read_filename) as TextAsset;
        sr = new StringReader(csvFile.text);

        string all_line = sr.ReadToEnd();
        Debug.Log(all_line);
        string[] lines = all_line.Split('\n');
        logdata_array = new float[lines.Length-1, col];
        for(int i = 0; i < lines.Length-1; i++)
        {
           // Debug.Log("i="+i+","+logdata_array.Length+","+lines.Length);
            string[] strs = lines[i].Split(',');
            for(int j = 0; j < strs.Length-1; j++)
            {
                //Debug.Log(strs[j]);
                logdata_array[i, j] = float.Parse(strs[j]);
                //Debug.Log(logdata_array[i,j]);
            }
        }

        slider.maxValue = logdata_array[lines.Length-2, 0];



        /*
        playflag = true;
        time = 15f;
        */
		
	}
	
	// Update is called once per frame
	void Update () {


        if (playflag)
        {
            slider.value = time;
            time += Time.deltaTime;
            // Debug.Log(k);
            for (int i = k; i < logdata_array.Length-1; i++)
            {
                //Debug.Log(k);
                if (time < logdata_array[i, 0])
                {
                    Vector3 new_position = new Vector3(logdata_array[i, 1], logdata_array[i, 2], logdata_array[i, 3]);
                    Quaternion new_quaternion = new Quaternion(logdata_array[i, 4], logdata_array[i, 5], logdata_array[i, 6], logdata_array[i, 7]);
                    logtarget.transform.position = new_position;
                    logtarget.transform.rotation = new_quaternion;
                    k = i;
                    break;
                }
            }
        }
		
	}

    public void startPlay()
    {
        playflag = true;
        time = slider.value;
        k = 0;
    }

    public void stopPlay()
    {
        playflag = false;
    }



    public void slide()
    {
        //Debug.Log("test");
        
    }
}
