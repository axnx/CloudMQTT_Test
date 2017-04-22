package de.test.mqtt;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

//https://www.cloudmqtt.com/docs-java.html

public class MQTTSample {

	public static void main(String[] args) {
		
		Properties props = new XProperties().read("config.properties");

		String broker = props.getProperty("broker");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		// clientId = props.getProperty("clientId");

		System.out.println("broker:" + broker);	
		System.out.println("username:" + username);	
		System.out.println("password:" + password);	
		//System.out.println("clientId:" + clientId);	
		
		String topic        = "CloudMQTT";
		String content      = "Hello CloudMQTT";
		int qos             = 1;

		//MQTT client id to use for the device. "" will generate a client id automatically
		String clientId     = "";

		MemoryPersistence persistence = new MemoryPersistence();
		try {
			MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
			mqttClient.setCallback(new MqttCallback() {
				public void messageArrived(String topic, MqttMessage msg)
						throws Exception {
					System.out.println("Received:" + topic);
					System.out.println("Received:" + new String(msg.getPayload()));
				}

				public void deliveryComplete(IMqttDeliveryToken arg0) {
					System.out.println("Delivery complete");
				}

				public void connectionLost(Throwable arg0) {
					// TODO Auto-generated method stub
				}
			});

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

			connOpts.setUserName(username);
			char[] charArrayPassword = password.toCharArray();
			connOpts.setPassword(charArrayPassword);

			//connOpts.setUserName("cloudmqtt_username");
			//connOpts.setPassword(new char[]{'p', 'a', 's', 's', 'w', 'r', 'd'});
			mqttClient.connect(connOpts);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos); 
			System.out.println("Publish message: " + message);
			mqttClient.subscribe(topic, qos);
			mqttClient.publish(topic, message);
			mqttClient.disconnect();
			
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