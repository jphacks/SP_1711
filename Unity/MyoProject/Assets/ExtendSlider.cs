using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ExtendSlider : UnityEngine.UI.Slider
{

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    void OnDrag()
    {
        Debug.Log("test_ondrug");
    }
}
