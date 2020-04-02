# Cyanaura-Apps

Cyanaura is an App Ecosystem for the Post Pandemic Political Economy. The world need to adhere to the COVID-19 protocol for some years to come. The immediate world around us will be surrounded by people who are part of the SIR model. SIR model consists of Suspects, Infected and Removed people. 

On 31 December 2019, a cluster of pneumonia cases of unknown etiology was reported in Wuhan, Hubei Province, China. On 9 January 2020, China CDC reported a novel coronavirus as the causative agent of this outbreak, which is phylogenetically in the SARS-CoV clade. The disease associated with it is now referred to as novel coronavirus disease 2019 (COVID-19). The risk associated with COVID-19 infection for people in India is currently considered to be moderate to high, based on the probability of transmission and the impact of the disease. The first step in infectious disease control is detection and diagnosis. The data collection, data curation, and data analysis are also very important. 

It is important to create a contact tracing network of people who are infected by COVID -19 with comprehensive coverage on the transmission methods and traceability of their locations. COVID-19 can spread from imported methods and local transmission methods at the same time. Human body temperature sensors coupled with demographic, details will be an insightful mechanism to implement contract tracing.

# Cyanaura-Ecosystem

We are building a Cyanaura App Ecosystem as a collection of mobile apps that features privacy-preserving proximity. We will provide privacy-preserving proximity through leveraging the confluence of Bluetooth low energy services available in smartphones together with offline and online maps. These online and offline maps. 

# Why Bluetooth

Bluetooth is essentially a radio frequency technology. In Bluetooth, the transmitted signal energy decreases almost proportionally with the distance between stations and mobile terminals. Hence we can consider this relationship to determine the distance from a particular node. In the mobile terminal, when we have the signal levels from different nodes, we can apply different algorithms to estimate its location.

As we have seen, the location technique is based on the Received Signal Strength Indicator (RSSI) of Bluetooth nodes. The system works in a similar way than the RADAR system, where, first, a server must store a map of the RSSI at different coordinates. To build the map of the RSSI in a closed environment (i.e. a room), a fixed number of access points 1 will be considered.

To create and conform to the map, a mobile device should move through all the coordinates of interest. From each coordinate, this device will notify some parameters to store with the map: its location, information of the signal power that receives from each access point, and finally some information of interest

Our system considers three kinds of components: clients, Bluetooth access points and servers. Clients are mobile devices that want to be located. Bluetooth access points are the nodes to be used as references in the location algorithm and they are also used for communication. Servers will store the maps of signal strengths and they will run the location algorithms. 

# Etymology
Our Project is names as Cyanaura : Cyan is the synonym for 'Blue' colour. 'Aura' is the synonym for Light. Our project will be powered by Bluetooth Low Energy and Encrypted Maps Technology in essence. Cyanaura also sounds like Sayanora. Sayanora is the Japanense word for bidding adieu. Let us bid adieu to Covid19 through Cyanaura Apps

# Technology

## Android Application

## Bluetooth Low Energy Protocol
Bluetooth low energy supports very short data packets (8 octet minimum up to 27 octets maximum) that are transferred at 1 Mbps. All connections use advanced sniff-subrating to achieve ultra low duty cycles.

### Frequency Hopping
Bluetooth low energy uses the adaptive frequency hopping common to all versions of Bluetooth technology to minimize interference from other technologies in the 2.4 GHz ISM Band. Efficient multi-path benefits increase the link budgets and range.

### Host Control
Bluetooth low energy places a significant amount of intelligence in the controller, which allows the host to sleep for longer periods of time and be woken up by the controller only when the host needs to perform some action. This allows for the greatest current savings since the host is assumed to consume more power than the controller.

### Latency
Bluetooth low energy can support connection setup and data transfer as low as 3ms, allowing an application to form a connection and then transfer authenticated data in few milliseconds for a short communication burst before quickly tearing down the connection.

### Range
Increased modulation index provides a possible range for Bluetooth low energy of over 100 meters.

### Robustness
Bluetooth low energy uses a strong 24 bit CRC on all packets ensuring the maximum robustness against interference.

### Strong Security 
Full AES-128 encryption using CCM to provide strong encryption and authentication of data packets.

### Topology
Bluetooth low energy uses a 32 bit access address on every packet for each slave, allowing billions of devices to be connected. The technology is optimized for one-to-one connections while allowing one-to-many connections using a star topology. With the use of quick connections and disconnections, data can move in a mesh-like topology without the complexities of maintaining a mesh network.

## Encrypted Maps in Inter Planetary File System

## Storage of Encrypted Maps in Ethereum Blockchain
Hash based Address of Patient Location in Blockchain

## Zero Knowledge Proof of Location
Zero Knowledge Proof based Proof of Location using Zokrates

## Contact Tract Trace Network Analytics
Social Network Analytcs for Graph Database.

## Data Model

### Annotated Map Data Model

The data structure of the annotated map will consist of the following signals. 

Location of the suspect
Numeric Size of the Contact Network
Spatial Size of the Contact Network
Time Stamp of the event

### References
https://developer.android.com/guide/topics/connectivity/bluetooth-le
