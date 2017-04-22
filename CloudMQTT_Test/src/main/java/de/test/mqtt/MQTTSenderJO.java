package de.test.mqtt;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

//https://www.cloudmqtt.com/docs-java.html

public class MQTTSenderJO {

	public static void main(String[] args) throws IOException {

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

		Person p = new Person("Max","Mustermann");

		MemoryPersistence persistence = new MemoryPersistence();
		try {
			MqttClient mqttClient = new MqttClient(broker, clientId, persistence);

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName(username);
			char[] charArrayPasssword = password.toCharArray();
			connOpts.setPassword(charArrayPasssword);

			mqttClient.connect(connOpts);
			MqttMessage message = new MqttMessage(Serializer.serialize(p));
			message.setQos(qos); 
			System.out.println("-> Publish object...");
			//mqttClient.subscribe(topic, qos);
			mqttClient.publish(topic, message);
			mqttClient.disconnect();
			System.out.println("-> disconnected");
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