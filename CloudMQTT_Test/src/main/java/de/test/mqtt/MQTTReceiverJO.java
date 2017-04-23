package de.test.mqtt;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

//https://www.cloudmqtt.com/docs-java.html

public class MQTTReceiverJO {
	
	private static MqttClient mqttClient;
	
  public static void main(String[] args) throws ClassNotFoundException, IOException {
	  	Properties props =  XProperties.read("config.properties");
		 
		String broker = props.getProperty("broker");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		//String clientId = props.getProperty("clientId");
		
		System.out.println("broker:" + broker);	
		System.out.println("username:" + username);	
		System.out.println("password:" + password);	
		//System.out.println("clientId:" + clientId);	
				
		String topic        = "CloudMQTT";
	    String content      = "Hello CloudMQTT";
	    int qos             = 1;

	    String clientId = "";
	    
	    Scanner keyboard = new Scanner(System.in);
    
    MemoryPersistence persistence = new MemoryPersistence();
    try {
        mqttClient = new MqttClient(broker, clientId, persistence);
        mqttClient.setCallback(new MqttCallback() {
        public void messageArrived(String topic, MqttMessage msg) throws Exception {
                    System.out.println("Received_TOPIC:" + topic);
                    Person p = (Person)Serializer.deserialize(msg.getPayload());
                    String ps = p.surname + ";" + p.lastname;
                      System.out.println("Received_PL:" + ps);
                }

        public void deliveryComplete(IMqttDeliveryToken arg0) {
                    System.out.println("-> Delivary complete");
                }

        public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
        			System.out.println("the connection was broken");
                }
      });

      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      connOpts.setUserName(username);
      
      char[] charArray = password.toCharArray();
      connOpts.setPassword(charArray);
      
      System.out.println("-> connect to server");
      mqttClient.connect(connOpts);

      while(true){
    	  MqttMessage message = new MqttMessage(content.getBytes());
	      mqttClient.subscribe(topic, qos);
	      
	      String input = keyboard.nextLine();
	      if(input!=null){
	    	  break;
	      }
      }
      
      mqttClient.disconnect();
      System.out.println("-> server is disconnected");
      System.exit(0);
    } catch(MqttException me) {
      System.out.println("reason "+me.getReasonCode());
      System.out.println("msg "+me.getMessage());
      System.out.println("loc "+me.getLocalizedMessage());
      System.out.println("cause "+me.getCause());
      System.out.println("excep "+me);
      me.printStackTrace();
    }
  }
}