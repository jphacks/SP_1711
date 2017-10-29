using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class RotateCamera : MonoBehaviour {

    public GameObject cameraparent;
    Slider viewSlider;

	// Use this for initialization
	void Start () {
        viewSlider = GetComponent<Slider>();
        viewSlider.maxValue = 360;
        viewSlider.value = 0;
	}
	
	// Update is called once per frame
	void Update () {
        cameraparent.transform.rotation = Quaternion.Euler(0,viewSlider.value,0);
        
	}
}
