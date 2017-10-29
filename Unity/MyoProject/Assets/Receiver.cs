using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityOSC;

public class Receiver : MonoBehaviour {
    public GameObject cube; 
    float lastTimeStamp;
    // Use this for initialization
    void Start()
    {
        OSCHandler.Instance.Init();
        lastTimeStamp = -1;
    }
    void Update()
    {
        listenToOSC();
    }

    void listenToOSC()
    {
        OSCHandler.Instance.UpdateLogs();
        Dictionary<string, ServerLog> servers = new Dictionary<string, ServerLog>();
        servers = OSCHandler.Instance.Servers;
        int count = 0;

        foreach (KeyValuePair<string, ServerLog> item in servers)
        {
            List<OSCPacket> packetlist = item.Value.packets;
            if (packetlist.Count == 25)
            {
                cube.transform.localScale = new Vector3(2, 2, 2);
            }else
            {
                cube.transform.localScale = new Vector3(1, 2, 2);
            }
           // Debug.Log(packetlist.Count);
            
            for(int i = 0; i < packetlist.Count; i++)
            {
                //Debug.Log(packetlist[i].Address);
                if(packetlist[i].Address == "/myo1/orientation/scaled")
                {

                    float yaw = (float)packetlist[i].Data[0]*180;
                    float pitch = (float)packetlist[i].Data[1]*180;
                    float roll = (float)packetlist[i].Data[2]*180;
                    cube.transform.rotation = Quaternion.Euler(yaw, pitch, roll);
                    Debug.Log(yaw+pitch+roll);
                }
            }
            
                /*
            for (int i = 0; i < item.Value.packets.Count; i++)
            {
                Debug.Log(item.Value.packets[i].Address);
            }
            Debug.Log(count);
            count++;
            */
            /*
            Debug.Log(item.Value.log.Count);
            Debug.Log(item.Value.packets.Count);
            if (item.Value.log.Count > 0)
            {
                Debug.Log("count is more than zero");
                int lastPacketIndex = item.Value.packets.Count - 1;

                string s1 = "123456789";
                string s2 = item.Value.packets[lastPacketIndex].Data[0].ToString();
                Debug.Log(item.Value.packets[lastPacketIndex].Data[0]);
                if (
                    (s1.Contains(s2)) &&
                    (item.Value.packets[lastPacketIndex].Address == "/Audio")
                )
                {
                    //RenderPitcher(s2);
                    // reset
                    item.Value.packets[lastPacketIndex].Address = "/reset";
                    Debug.Log("change view");
                }
            }
            */
        }
        OSCHandler.Instance.UpdateLogs();
    }
}
