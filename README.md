# 🚂 The Train Company

A train management company needs your help implementing a solution to organize the route and seating arrangements of its suburban train. This company requires a system that organizes the routes the train must take as it travels through different provinces, considering the distances between the various stations it must visit.

In addition to this, it needs a system that allows it to organize the passengers who use this train, according to the number of seats available, also considering the seats that are reserved preferentially for people with some type of disability.

Regarding the route of the peripheral train, it must visit each of the stations located in each province, considering both distance and time. This must be done using a graph (using an adjacency matrix) represented as follows:

### Route graph (circular path):

Puntarenas → Guanacaste
- Distance: 100 km
- Time: 2 hours

Guanacaste → Alajuela
- Distance: 40 km
- Time: 25 minutes

Alajuela → Limón
- Distance: 10 km
- Time: 20 minutes

Limón → Cartago
- Distance: 50 km
- Time: 1 hour

Cartago → Heredia
- Distance: 30 km
- Time: 30 minutes

Heredia → San José
- Distance: 25 km
- Time: 35 minutes

San José → Puntarenas
- Distance: 40 km
- Time: 40 minutes

The system must simulate the train's movement as shown in the previous image. At each station, a queue of passengers will form, which must wait to board the train until it arrives at the station. Once the train arrives at the station, the system must attend to each passenger in the queue and validate available spaces according to passenger requirements. The use of wires is required to sum the train's movement at each station.

Once each trip is completed, the ticket cost must be recorded. This cost will be calculated by summing the cost of each kilometer traveled at a price specified in the configuration. The Dijkstra shortest path algorithm must be used for this calculation.

For passenger seating arrangements on the train, the following must be considered:
- There are 3 rows of seats in the train car. The train car should correspond to a list containing 3 stacks, one for each row.
- Row 1 is reserved for people with disabilities and has 2 seats. This corresponds to 1 stack of 2 nodes.
- Rows 2 and 3 each have 3 seats, corresponding to 2 stacks of 3 nodes each (In case they are full).
- There must be an attribute in the stack class that determines the maximum number of nodes allowed, but nodes with null data cannot be used.
- At each station, a certain number of people board the train and a certain number of people disembark. The disembarkation process must be executed first, followed by the boarding process.
- Two waiting lines will be established for passengers at each station, and the train will be filled according to available space. If the train is at maximum capacity, passengers will be left waiting until the next train arrives. One line will be for disabled passengers and the other for non-disabled passengers.
- The row of seats must be respected in a stacked configuration. If a passenger at the back of the stack needs to leave, all passengers in the stack must be de-stacked (popped) into an auxiliary stack and then re-stacked in the same order, always leaving available spaces at the top of the stack.
- Each person must have an attribute indicating whether or not they have a disability, so that they have priority in the seat in row 1.
- A person without a disability should not be allowed to be placed in row 1.
- Each stack must start empty (null), with capacity restrictions as per the points above.

## 🧩 Features

- ⚙️ **Configuration Module**
    - 📄 External libraries can be used to manage configuration files, such as ini files (flat files with KEY=VALUE format). The use of ini4j is recommended.
    - 🔒 The ini file will be read-only and will be accessed after the system starts.
    - The following files must be stored:
        a. ♿ Number of rows per car WITH disability (default 1).
        b. 👥 Number of rows in the car WITHOUT disability (default 2).
        c. 💺 Number of seats in the row WITH disability (default 1).
        d. 💺 Number of seats in the row WITHOUT disability (default 3).
        e. 🏢 Company name.
        f. ⏱️ Time in seconds that the train travels between stops (default 1 second).
        g. 💰 Cost per kilometer traveled: 100 colons.
    - 📂 The ini file will be stored in the root directory for modification.
- 👤 **Passenger Administration**
    - ➕ The user must be able to add passengers according to their needs and characteristics.
    - 📋 The user must be able to list all passengers both at stations and in train cars.
    - Passenger data:
        a. 🪪 First and last name.
        b. 🎂 Age.
        c. 📍 Origin station.
        d. 🎯 Destination station.
        e. ♿ Disabled YES/NO, using enum in Java.
        f. 🔄 Status: IN_QUEUE/EN_WAY/COMPLETED.
- 🖥️ **Station and Train Car Visualization**
    - 🪟 The system must display both stations and train cars on a single screen.
    - 🔄 The screen must refresh according to train movement, by default every second.
    - For each station, the following must be displayed:
        a. ⏳ Queues of passengers waiting to board (disabled and non-disabled), with their characteristics. Specify waiting times if space cannot be found for boarding.
        b. 🧾 Passengers who have completed their journey, with their characteristics, including cost. Additionally, a text summary (log) must be displayed if de-stacking and re-stacking is necessary to process a passenger's exit.
    - The train car must display:
        a. 💺 Seating arrangement, showing passenger data.
        b. 📍 Current status of its location (station).
    - 📢 Display the necessary messages on the console for each execution of the algorithms. For example, write the current train station, passengers who left the car, and passengers who entered the car.

## 🛠️ Technologies Used

- ☕ **Programming language:** Java
- 📚 **Libraries:**
    - java.io.File
    - java.io.IOException
    - java.time.format.DateTimeFormatter
    - java.time.LocalDateTime
    - javax.swing.JOptionPane
    - org.ini4j.Ini (imported externally)
    - org.ini4j.Profile (imported externally)
- 🌱 **Version Control:** Git

## ⚙️ Installation

### 📋 Prerequisites

- 💻 [Apache Netbeans](https://netbeans.apache.org/front/main/index.html) (recommended: Apache Netbeans 28)

---

### 🔧 Setup

Follow these steps to correctly configure and run the project:

1. 📥 **Clone the repository**

   ```bash
   git clone https://github.com/Crisrod0912/TheTrainCompany.git
   ```
    
2. 📂 **Open the project folder in Apache Netbeans**

3. ▶️ **Run the project**

   - Click on "Run Project".

> [!NOTE]
> **Project Owner / Developer** 👨🏻‍💻  
>- Cristopher Rodríguez Fernández 
***
