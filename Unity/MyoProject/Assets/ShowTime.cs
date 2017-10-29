using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System;

public class ShowTime : MonoBehaviour {

    public Slider timeSlider;
    Text timetext;
    DateTime time;
    public int hour;
    public int minute;
    public int second;
	// Use this for initialization
	void Start () {

        time = new DateTime(2017,10,29,hour,minute,second);
        timetext = GetComponent<Text>();
	}
	
	// Update is called once per frame
	void Update () {
        timetext.text = time.AddSeconds((double)timeSlider.value).ToLongTimeString();
	}
}
